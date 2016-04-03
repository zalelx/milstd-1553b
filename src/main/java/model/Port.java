package model;


import model.message.Message;
import view.TimeLogger;

public class Port {
    private Device device;
    private Line line;
    private PortStatus status;
    private boolean isGenerator = false;
    private String name;

    Port(Line line, Device device, String name) {
        this.line = line;
        this.device = device;
        this.status = PortStatus.OK;
        this.name = name;
    }

    public Port(Line line, String name) {
        this.name = name;
        this.line = line;
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

    void handleMessage() {
        switch (status) {
            case FAILURE:
                status = PortStatus.OK;
                break;
            case OK:
                device.handleMessage(line.getMessage());
                TimeLogger.log(name + " message handle");
                break;
            default:
                break;
        }

    }

    void broadcastMessage(Message message) {
        switch (status) {
            case OK:
                line.broadcastMessage(message);
                TimeLogger.log(name + " message send");
                break;
            default:
                break;
        }
    }

    void unblock() {
        if (isGenerator)
            status = PortStatus.GENERATION;
        else status = PortStatus.OK;
        TimeLogger.log(name + " unblocked");
    }

    void block() {
        if (status != PortStatus.DENIAL)
            status = PortStatus.BLOCK;
        TimeLogger.log(name + " blocked");
    }
}
