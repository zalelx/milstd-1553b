package model;

import java.util.ArrayList;

public class Line {
    TypeOfLine type;

    ArrayList<Listener> list = new ArrayList<>();

    public void addListener(Listener listener) throws Exception {
        int address = list.size();
        if (address >= 0 && address < 32)
            list.add(listener);
        else throw new Exception("Wrong amount of listeners!");
    }

    public void broadcastMessage(Message message) throws Exception {
        int address = message.getAddress();
        if (address >= 0 && address < 32)
            list.get(address).handleMessage(type, message);
        else throw new Exception("Wrong address!");
    }


    public TypeOfLine getType() {
        return type;
    }

    public void setType(TypeOfLine type) {
        this.type = type;
    }

    public Line(TypeOfLine type) {
        this.type = type;
    }
}
