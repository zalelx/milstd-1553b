package model;

import model.message.Message;
import view.TimeLogger;

import java.util.ArrayList;

public class Line {
    private ArrayList<Port> list = new ArrayList<>();
    private Message message;
    private boolean hasGeneration = false;


    public void addPort(Port port) {
        list.add(port);
    }

    public void broadcastMessage(Message message) {
        if (!hasGeneration) {
            this.message = message;
            TimeLogger.log(message.getTime());
            list.forEach(Port::handleMessage);
        }
    }

    public Message getMessage() {
        return message;
    }
}
