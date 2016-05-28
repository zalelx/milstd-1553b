package view;

import model.*;

import java.util.ArrayList;

class MetaController {
    private Controller controller;
    private ArrayList<Device> devices;
    static int amountOfEd;
    static double faultProbability;
    static double deviceProbability;
    static double generationProbability;
    static double denialProbability;
    private int amountOfGenerations;
    private int amountOfDenials;
    private int amountOFFaults;

    void init(int amountOfEndDevices) {
        amountOfEd = amountOfEndDevices;
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

    private void setGeneratorLineA(int numberOfDevice) {
        Port target = devices.get(numberOfDevice).getDefaultPort();
        target.setGenerator(true);
        target.getLine().hasGeneration(true, numberOfDevice);

    }

    private void setGeneratorLineB(int numberOfDevice) {
        Port target = devices.get(numberOfDevice).getReservePort();
        target.setGenerator(true);
        target.getLine().hasGeneration(true, numberOfDevice);
    }

    void setPreparedToSendInfo(int numberOfDevice, boolean status) {
        if (numberOfDevice > 0)
            ((EndDevice) devices.get(numberOfDevice)).setReady(status);
    }

    void setPortStatusLineA(int numberOfDevice, PortStatus status) {
        switch (status) {
            case GENERATION:
                setGeneratorLineA(numberOfDevice);
                break;
            default:
                devices.get(numberOfDevice).getDefaultPort().setStatus(status);
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

    void connectToAll() {
        controller.connectToAll(amountOfEd);
    }

    private void setDeviceProbability(double deviceProbability) {
        if (Math.abs(deviceProbability) > 1)
            MetaController.deviceProbability = 1 / Math.abs(deviceProbability);
        else
            MetaController.deviceProbability = deviceProbability;
    }

    private void initTest(double generationProbability, double faultProbability, double denialProbability, double probability) {
        double rand;
        MetaController.faultProbability = faultProbability;
        MetaController.generationProbability = generationProbability;
        MetaController.denialProbability = denialProbability;
        this.amountOFFaults = 0;
        this.amountOfDenials = 0;
        this.amountOfGenerations = 0;
        setDeviceProbability(probability);

        boolean wasGeneration = false;
        PortStatus status;
        for (int i = 1; i <= amountOfEd; i++) {
            rand = Math.random();
            if (rand < probability) {
                rand = Math.random();
                if (rand < generationProbability && !wasGeneration) {
                    setGeneratorLineA(i);
                    wasGeneration = true;
                    amountOfGenerations ++;
                } else {
                    if (rand >= generationProbability && rand < (generationProbability + faultProbability)) {
                        status = PortStatus.FAILURE;
                        amountOFFaults ++;
                    } else {
                        status = PortStatus.DENIAL;
                        amountOfDenials ++;
                    }
                    setPortStatusLineA(i, status);
                }
            }
        }
    }

    void performTests(int amountOfTests, double generationProbability, double faultProbability, double denialProbability, double probability, boolean isShortLogs) {
        for (int j = 1; j <= amountOfTests; j++) {
            initTest(generationProbability, faultProbability, denialProbability, probability);
            connectToAll();
            TimeLogger.logStart(amountOfEd, amountOfGenerations, amountOfDenials, amountOFFaults);
            init(amountOfEd);
        }
        if (!isShortLogs) {
            TimeLogger.showLogs();
        }
        TimeLogger.endTest();
    }
    void setAmountOfDataMessages(int value){
        controller.setAmountOfDataMessages(value);
    }
}