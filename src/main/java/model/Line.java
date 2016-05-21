package model;

import model.message.Message;
import view.logging.TimeLogger;

import java.util.ArrayList;

public class Line {
    private ArrayList<Port> list = new ArrayList<>();
    private Message message;
    int lineNumber;
    boolean hasGeneration = false;

    public Line(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void addPort(Port port) {
        list.add(port);
    }

    void broadcastMessage(Message message) {
        if (!hasGeneration) {
            this.message = message;

            TimeLogger.logBroadcast(lineNumber, message.getTime());
            for (Port port : list) {
                if (port.handleMessage())
                    break;
            }
        } else {
            TimeLogger.delay(message.getTime());
        }
    }

    public Message getMessage() {
        return message;
    }

    public ArrayList<Port> getPorts() {
        return list;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void hasGeneration(boolean b, int numberOfGenerator) {
        hasGeneration = b;
        TimeLogger.logGeneration(lineNumber, hasGeneration, numberOfGenerator);
    }
}
