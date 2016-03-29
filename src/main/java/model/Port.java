package model;


import model.message.Message;

public class Port {
    private Device device;
    private Line line;

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void handleMessage() {
        device.handleMessage(line.getMessage());
    }

    public void broadcastMessage(Message message){
        line.broadcastMessage(message);
    }
}
