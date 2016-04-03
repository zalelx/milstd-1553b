package model;

import model.message.Message;

import java.util.HashMap;
import java.util.Map;

public class AddressBook {
    private Map <Address, Port> book;
    private Port defaultLine;
    private Port reserveLine;

    public AddressBook(int amountOfEndDevices, Port portA, Port portB) {
        book = new HashMap<>();
        defaultLine = portA;
        reserveLine = portB;
        for (int i = Address.MIN_ADDRESS; i <= amountOfEndDevices; i++) {
            try {
                book.put(new Address(i), portA);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void changeLine(Address address) {
        book.put(address, (book.get(address).equals(defaultLine)) ? defaultLine : reserveLine);
    }

    private Port getPort(Address address) {
        return book.get(address);
    }

    void sendMessage(Message message) {
        getPort(message.getAddress()).broadcastMessage(message);
    }
}
