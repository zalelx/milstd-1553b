package view;

import model.*;

import java.util.ArrayList;

class MetaController {
    private Controller controller;
    private ArrayList <EndDevice> devices;
    private int amountOfEd;

    void init(int amountOfEndDevices){
        this.amountOfEd = amountOfEndDevices;
        this.devices = new ArrayList<>(amountOfEndDevices);
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

        for (int i = 1; i <= amountOfEndDevices; i++) {
            EndDevice newDevice = new EndDevice(new Address(i), lineA, lineB);
            lineA.addPort(newDevice.getDefaultPort());
            lineB.addPort(newDevice.getReservePort());
            devices.add(newDevice);
        }

    }

    void testMKO(){
        controller.testMKO(amountOfEd);
    }

    void setGeneratorLineA(int numberOfDevice, boolean isGenerator){
        numberOfDevice --;
        Port target = devices.get(numberOfDevice).getDefaultPort();
        target.setGenerator(isGenerator);
        for (EndDevice d:
             devices) {
            d.getDefaultPort().setStatus(PortStatus.GENERATION);
        }
    }

    void setGeneratorLineB(int numberOfDevice, boolean isGenerator){
        numberOfDevice --;
        Port target = devices.get(numberOfDevice).getReservePort();
        target.setGenerator(isGenerator);
        for (EndDevice d:
                devices) {
            d.getReservePort().setStatus(PortStatus.GENERATION);
        }
    }

    void setPreparedToSendInfo (int numberOfDevice, boolean status){
        numberOfDevice --;
        devices.get(numberOfDevice).setPreparedToSendInfo(status);
    }

    void setPortStatusLineA(int numberOfDevice, PortStatus status){
        numberOfDevice --;
        devices.get(numberOfDevice).getDefaultPort().setStatus(status);
    }

    void setPortStatusLineB(int numberOfDevice, PortStatus status){
        numberOfDevice --;
        devices.get(numberOfDevice).getReservePort().setStatus(status);
    }
}
