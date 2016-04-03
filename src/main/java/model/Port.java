package model;


import model.message.Message;

public class Port {
    private Device device;
    private Line line;
    private PortStatus status;
    private boolean isGenerator = false;

    public Port(Line line, Device device) {
        this.line = line;
        this.device = device;
        this.status = PortStatus.OK;
    }

    public void setGenerator(boolean generator) {
        isGenerator = generator;
    }

    public PortStatus getStatus() {
        return status;
    }

    public void setStatus(PortStatus status) {
        this.status = status;
    }

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
