package view;

import model.*;
import view.logging.TimeLogger;

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
        target.getLine().hasGeneration(true, numberOfDevice);
    }

    void setGeneratorLineB(int numberOfDevice) {
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

    public ArrayList<Integer> initTest(double generationProbability, double faultProbability, double probability) {
        double rand;
        ArrayList<Integer> resultList = new ArrayList<>(3);
        resultList.add(0);
        resultList.add(0);
        resultList.add(0);
        boolean wasGeneration = false;
        PortStatus status;
        for (int i = 1; i <= this.amountOfEd; i++) {
            rand = Math.random();
            if (rand < probability) {
                rand = Math.random();
                if (rand < generationProbability && !wasGeneration) {
                    setGeneratorLineA(i);
                    wasGeneration = true;
                    resultList.set(0, resultList.get(0) + 1);
                } else {
                    if (rand >= generationProbability && rand < (generationProbability + faultProbability)) {
                        status = PortStatus.FAILURE;
                        resultList.set(1, resultList.get(1) + 1);
                    } else {
                        status = PortStatus.DENIAL;
                        resultList.set(2, resultList.get(2) + 1);
                    }
                    setPortStatusLineA(i, status);
//                    TimeLogger.logChangePortStatus(i, 1, status);
                }
            }
        }
        return resultList;
    }

    public void performTests(int amountOfTests, double generationProbability, double faultProbability, double probability, boolean isShortLogs) {
        for (int j = 1; j <= amountOfTests; j++) {
            ArrayList<Integer> result = initTest(generationProbability, faultProbability, probability);
            connectToAll();
            TimeLogger.logStart(amountOfEd, result.get(0), result.get(1), result.get(2));
            init(amountOfEd);
        }
        if (!isShortLogs){
            TimeLogger.showLogs();
        }
        TimeLogger.endTest();
    }
    void setAmountOfDataMessages(int value){
        controller.setAmountOfDataMessages(value);
    }
}