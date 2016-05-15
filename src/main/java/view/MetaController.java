package view;

import javafx.fxml.FXML;
import model.*;
import view.Logging.TimeLogger;

import java.util.ArrayList;

class MetaController {
    private Controller controller;
    private ArrayList<Device> devices;
    private int amountOfEd;

    void init(int amountOfEndDevices) {
        this.amountOfEd = amountOfEndDevices;
        this.devices = new ArrayList<>();
        Line lineA = new Line(1);
        Line lineB = new Line(2);
        Port portA = new Port(lineA);
        Port portB = new Port(lineB);

        AddressBook addressBook = new AddressBook(amountOfEndDevices, portA, portB);
        this.controller = new Controller(addressBook);

        portA.setDevice(controller);
        portB.setDevice(controller);
        portB.setMyAddress(controller.getMyAddress());
        portA.setMyAddress(controller.getMyAddress());
        lineA.addPort(addressBook.getDefaultPort());
        lineB.addPort(addressBook.getReservePort());
        devices.add(controller);
        for (int i = 1; i <= amountOfEndDevices; i++) {
            EndDevice newDevice = new EndDevice(new Address(i), lineA, lineB);
            lineA.addPort(newDevice.getDefaultPort());
            lineB.addPort(newDevice.getReservePort());
            devices.add(newDevice);
        }

    }

    void testMKO() {
        controller.testMKO(amountOfEd);
    }

    void setGeneratorLineA(int numberOfDevice, boolean isGenerator) {
//        numberOfDevice--;
        Port target = devices.get(numberOfDevice).getDefaultPort();
        target.setGenerator(isGenerator);
        for (int i = 1; i < devices.size(); i++) {
            devices.get(i).getDefaultPort().setStatus(PortStatus.GENERATION);
            TimeLogger.logChangePortStatus(i, 1, PortStatus.GENERATION);
        }
    }

    void setGeneratorLineB(int numberOfDevice, boolean isGenerator) {
//        numberOfDevice--;
        Port target = devices.get(numberOfDevice).getReservePort();
        target.setGenerator(isGenerator);
        for (int i = 1; i < devices.size(); i++) {
            devices.get(i).getReservePort().setStatus(PortStatus.GENERATION);
            TimeLogger.logChangePortStatus(numberOfDevice, 2, PortStatus.GENERATION);
        }
    }

    void setPreparedToSendInfo(int numberOfDevice, boolean status) {
        numberOfDevice--;
        ((EndDevice)devices.get(numberOfDevice)).setPreparedToSendInfo(status);
    }

    void setPortStatusLineA(int numberOfDevice, PortStatus status) {
        switch (status) {
            case GENERATION:
                setGeneratorLineA(numberOfDevice, true);
                break;
            default:
//                numberOfDevice--;
                devices.get(numberOfDevice).getDefaultPort().setStatus(status);
                TimeLogger.logChangePortStatus(numberOfDevice, 1, status);
        }
    }

    void setPortStatusLineB(int numberOfDevice, PortStatus status) {
        switch (status) {
            case GENERATION:
                setGeneratorLineB(numberOfDevice, true);
                break;
            default:
//                numberOfDevice--;
                devices.get(numberOfDevice).getReservePort().setStatus(status);
                TimeLogger.logChangePortStatus(numberOfDevice, 2, status);
        }
    }

    PortStatus GenofStatus(){
        int max=6;
        PortStatus status;
        int rand=(int)(Math.random()*max);
        switch(rand) {
            case (0):
                status=PortStatus.DENIAL;
                return status;
            case (1):
                status=PortStatus.FAILURE;
                return status;
            case (2):
                status=PortStatus.GENERATION;
                return status;
            default:
                status=PortStatus.OK;
                return status;
        }
    }


     void randomFault(int amountofDevices) {
            double porog = 0.8;

         // для линии А
            for (int i = 0; i < amountofDevices; i++) {
                PortStatus status=GenofStatus();
                if (status != PortStatus.OK) {
                     double rand = Math.random();
                     if (rand > porog)
                         devices.get(i).getDefaultPort().setStatus(status);
                         ChangeColor.SetColor(i+1,1,status);
            }
        }

         // для линии B
              for (int i = 0; i < amountofDevices; i++) {
                 PortStatus status=GenofStatus();
                 if (status != PortStatus.OK) {
                    double rand = Math.random();
                    if (rand > porog)
                         devices.get(i).getReservePort().setStatus(status);
                         ChangeColor.SetColor(i+1,2,status);
             }
         }
    }





}
