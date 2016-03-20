package model;

import java.util.HashMap;
import java.util.Map;

public class AddressBook {
    private Map<Integer,Line> book;
    private Line defaultLine;
    private Line reserveLine;

    public AddressBook(int amountOfEndDevices, Line lineA, Line lineB) {
        book = new HashMap<>();
        defaultLine = lineA;
        reserveLine = lineB;
        for (int i = 0; i < amountOfEndDevices; i++) {
            book.put(i, lineA);
        }
    }

    public void changeLine(int address){
        if (address < 0 && address >= 32)
            return;
        Line currentLine = book.get(address);
        book.put(address, (currentLine == defaultLine) ? defaultLine : reserveLine);
    }

    public Line getLine(int address) throws Exception {
        if (address < 0 && address >= 32)
            throw new Exception("Wrong address!");
        return book.get(address);
    }
}
