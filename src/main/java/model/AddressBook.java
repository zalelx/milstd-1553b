package model;

import model.message.Message;

import java.util.HashMap;
import java.util.Map;

public class AddressBook {
    private Map <Address, Port> book;
    private Port defaultPort;
    private Port reservePort;

    public AddressBook(int amountOfEndDevices, Port portA, Port portB) {
        book = new HashMap<>();
        defaultPort = portA;
        reservePort = portB;
        for (int i = Address.MIN_ADDRESS; i <= amountOfEndDevices; i++) {
            try {
                book.put(new Address(i), portA);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Port getReservePort() {
        return reservePort;
    }

    public Port getDefaultPort() {
        return defaultPort;
    }

    void changeLine(Address address) {
        book.put(address,
                (book.get(address).equals(defaultPort)) ? reservePort : defaultPort);
    }

    private Port getPort(Address address) {
        return book.get(address);
    }

    void sendMessage(Message message) {
        Port port = getPort(message.getAddress());
        port.broadcastMessage(message);
    }
}
