package model;

import model.message.Message;
import java.util.HashMap;
import java.util.Map;

public class AddressBook {
    private Map<Integer, Port> book;
    private Port defaultLine;
    private Port reserveLine;

    public AddressBook(int amountOfEndDevices, Port portA, Port portB) {
        book = new HashMap<>();
        defaultLine = portA;
        reserveLine = portB;
        for (int i = 0; i < amountOfEndDevices; i++) {
            book.put(i, portA);
        }
    }

    public void changeLine(int address){
        if (address < 0 && address >= 32)
            return;
        book.put(address, (book.get(address) == defaultLine) ? defaultLine : reserveLine);
    }

    private Port getPort(int address){ //throws Exception {
        //if (address < 0 && address >= 32)
          //  throw new Exception("Wrong address!");
        return book.get(address);
    }

    public void sendMessage(Message message) {// throws Exception {
        getPort(message.getAddress()).broadcastMessage(message);
    }
}
