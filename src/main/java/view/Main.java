package view;

import model.*;
import model.message.Command;
import model.message.CommandMessage;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int amountOfEndDevices = 4;
        ArrayList <EndDevice> devices = new ArrayList<>(amountOfEndDevices);
        Line lineA = new Line("Line A");
        Line lineB = new Line("Line B");
        Port portA = new Port(lineA, "Control. Port A");
        Port portB = new Port(lineB, "Control. Port B");

        AddressBook addressBook = new AddressBook(amountOfEndDevices, portA, portB);
        Controller controller = new Controller(addressBook);

        portA.setDevice(controller);
        portB.setDevice(controller);
        portA.setMyAddress(controller.getMyAddress());
        portB.setMyAddress(controller.getMyAddress());
        lineA.addPort(addressBook.getDefaultPort());
        lineB.addPort(addressBook.getReservePort());

        for (int i = 1; i <= amountOfEndDevices; i++) {
            EndDevice newDevice = new EndDevice(new Address(i), lineA, lineB);
            lineA.addPort(newDevice.getDefaultPort());
            lineB.addPort(newDevice.getReservePort());
            devices.add(newDevice);
        }

        controller.testMKO(amountOfEndDevices);
    }
}
