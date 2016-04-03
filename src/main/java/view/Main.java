package view;

import model.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int amountOfEndDevices = 4;
        ArrayList<EndDevice> devices = new ArrayList<>(amountOfEndDevices);
        Line lineA = new Line("Line A");
        Line lineB = new Line("Line B");
        for (int i = 1; i <= amountOfEndDevices; i++) {
            devices.add(new EndDevice(new Address(i), lineA, lineB));
        }
        Port portA = new Port(lineA, "Control. Port A");
        Port portB = new Port(lineB, "Control. Port B");
        Controller controller = new Controller(new AddressBook(amountOfEndDevices, portA, portB));
        portA.setDevice(controller);
        portB.setDevice(controller);


    }
}
