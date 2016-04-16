package model;


import model.message.Message;
import view.TimeLogger;

public class Port {
    private Device device;
    private Line line;
    private PortStatus status = PortStatus.OK;

    private boolean isGenerator = false;
    private String name;
    private Address myAddress;

    Port(Line line, Device device, String name) {
        this.line = line;
        this.device = device;
        this.name = name;
    }

    public Port(Line line, String name) {
        this.name = name;
        this.line = line;
    }

    public void setMyAddress(Address myAddress) {
        this.myAddress = myAddress;
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
        if (line.getMessage().getAddress().equals(myAddress)
                || line.getMessage().getAddress().getValue() == Address.BROADCAST_ADDRESS) {
            switch (status) {
                case OK:
                    TimeLogger.log(name + " message handle");
                    device.handleMessage(line.getMessage(), this);
                    break;
                case FAILURE:
                    status = PortStatus.OK;
                    break;
                default:
                    break;
            }
        }
    }

    void broadcastMessage(Message message) {
        switch (status) {
            case OK:
                TimeLogger.log(name + " message send");
                line.broadcastMessage(message);
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
