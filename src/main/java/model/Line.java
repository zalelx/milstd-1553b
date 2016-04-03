package model;

import model.message.Message;
import view.TimeLogger;

import java.util.ArrayList;

public class Line {
    private ArrayList<Port> list = new ArrayList<>();
    private Message message;
    private String name;
    private boolean hasGeneration = false;

    public Line(String name) {
        this.name = name;
    }

    public void setHasGeneration(boolean hasGeneration) {
        this.hasGeneration = hasGeneration;
    }

    public void addPort(Port port) {
        list.add(port);
    }

    public void broadcastMessage(Message message) {
        if (!hasGeneration) {
            this.message = message;
            TimeLogger.log(name + " Message sent" ,message.getTime());
            list.forEach(Port::handleMessage);
        }
    }

    public Message getMessage() {
        return message;
    }
}
