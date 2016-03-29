package model;

import model.message.Message;
import java.util.ArrayList;

public class Line {
    private ArrayList<Port> list = new ArrayList<>();
    private Message message;


    public void addPort(Port port) {
        list.add(port);
    }

    public void broadcastMessage(Message message) {
        this.message = message;
        list.forEach(Port::handleMessage);
    }

    public Message getMessage() {
        return message;
    }
}
