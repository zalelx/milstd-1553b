package view;

import model.*;
import view.Logging.TimeLogger;

import java.util.ArrayList;

class MetaController {
    private Controller controller;
    private ArrayList<Device> devices;
    public int amountOfEd;
    private double faultProbability = 0.8;
    private double deviceProbability = 0.5;

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

    void setGeneratorLineA(int numberOfDevice) {
        Port target = devices.get(numberOfDevice).getDefaultPort();
        target.setGenerator(true);
        target.getLine().hasGeneration(true);
        ChangeColor.SetColorGeneration(1, true);
    }

    void setGeneratorLineB(int numberOfDevice) {
        Port target = devices.get(numberOfDevice).getReservePort();
        target.setGenerator(true);
        target.getLine().hasGeneration(true);
//        for (int i = 0; i < devices.size(); i++) {
//            devices.get(i).getReservePort().setStatus(PortStatus.GENERATION);
//            if (i > 0)
//                ChangeColor.SetColor(i, 2, PortStatus.GENERATION);
//        }
    }

    void setPreparedToSendInfo(int numberOfDevice, boolean status) {
        //numberOfDevice--;
        ((EndDevice) devices.get(numberOfDevice)).setReady(status);
    }

    void setPortStatusLineA(int numberOfDevice, PortStatus status) {
        switch (status) {
            case GENERATION:
                setGeneratorLineA(numberOfDevice);
                break;
            default:
                devices.get(numberOfDevice).getDefaultPort().setStatus(status);
                ChangeColor.SetColor(numberOfDevice, 1, status);
        }
    }

    void setPortStatusLineB(int numberOfDevice, PortStatus status) {
        switch (status) {
            case GENERATION:
                setGeneratorLineB(numberOfDevice);
                break;
            default:
                devices.get(numberOfDevice).getReservePort().setStatus(status);
                ChangeColor.SetColor(numberOfDevice, 2, status);
        }
    }

    PortStatus GenofStatus(PortStatus faultStatus) {
        if (Math.random() >= faultProbability)
            return faultStatus;
        else
            return PortStatus.OK;
    }

    void randomFault(PortStatus faultStatus) {
        if (faultStatus.equals(PortStatus.GENERATION)) {
            if (Math.random() < faultProbability) {
                setGeneratorLineA((int) (Math.random() * (amountOfEd - 1)));
            }
        } else {
            // для линии А
            for (int i = 1; i <= this.amountOfEd; i++) {
                PortStatus status = GenofStatus(faultStatus);
//                if (Math.random() > deviceProbability) {
                setPortStatusLineA(i, status);
//                }
            }

        }
    }

    void connectToAll() {
        controller.connectToAll(amountOfEd);
    }

    public void setDeviceProbability(double deviceProbability) {
        if (Math.abs(deviceProbability) > 1)
            this.deviceProbability = 1 / Math.abs(deviceProbability);
        else
            this.deviceProbability = deviceProbability;
    }

    public void setFaultProbability(double faultProbability) {
        if (Math.abs(faultProbability) > 1)
            this.faultProbability = 1 / Math.abs(faultProbability);
        else
            this.faultProbability = faultProbability;
    }

    public void initTest(double generationProbability, double faultProbability, double denialProbability, double probability) {
        double rand = Math.random();
        if (rand < generationProbability) {
            setGeneratorLineA((int) (Math.random() * (amountOfEd - 1)));
        }
        PortStatus status;
        if (rand > generationProbability && rand < (generationProbability + faultProbability)) {
            status = PortStatus.FAILURE;
        } else /*(rand > (generationProbability + faultProbability))*/ {
            status = PortStatus.DENIAL;
        }

        for (int i = 1; i <= this.amountOfEd; i++) {
            double edRand = Math.random();
            if (edRand < probability) {
                TimeLogger.logChangePortStatus(i, 1, status);
            }
        }
    }

    public void PerformTests(int amountOfTests, double generationProbability, double faultProbability, double denialProbability, double probability) {
        for (int j = 1; j <= amountOfTests; j++) {
            initTest(generationProbability, faultProbability, denialProbability, probability);
            connectToAll();
            //for (int i = 1; i <= amountOfEd; i++) {
            //     setPortStatusLineA(i, PortStatus.OK);
            // }
            TimeLogger.logStart(amountOfEd);
            init(amountOfEd);
        }
        TimeLogger.showLogs();
    }

}

