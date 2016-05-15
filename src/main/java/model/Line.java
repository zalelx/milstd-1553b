package model;

import model.message.Message;
import view.Logging.TimeLogger;

import java.util.ArrayList;

public class Line {
    private ArrayList<Port> list = new ArrayList<>();
    private Message message;
    int lineNumber;

    public Line(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void addPort(Port port) {
        list.add(port);
    }

    void broadcastMessage(Message message) {
        this.message = message;
        TimeLogger.logBroadcast(lineNumber, message.getTime());
        for (Port port : list) {
            if (port.handleMessage())
                break;
        }
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
