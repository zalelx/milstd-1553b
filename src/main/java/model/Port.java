package model;


import model.message.Message;
import view.TimeLogger;

public class Port {
    private Device device;
    private Line line;
    private PortStatus status = PortStatus.OK;
    private boolean isGenerator = false;
    private Address myAddress;
    private boolean isBlocked = false;

    Port(Line line, Device device) {
        this.line = line;
        this.device = device;
    }

    public Port(Line line) {
        this.line = line;
    }

    public void setMyAddress(Address myAddress) {
        this.myAddress = myAddress;
    }

    public void setGenerator(boolean generator) {
        isGenerator = generator;
        status = PortStatus.GENERATION;
        TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, status);
    }

    public PortStatus getStatus() {
        return status;
    }

    public void setStatus(PortStatus status) {
        this.status = status;
        TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, status);
    }

    public Line getLine() {
        return line;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    boolean handleMessage() {
        if (!isBlocked) {
            if (line.getMessage().getAddress().equals(myAddress)
                    || line.getMessage().getAddress().getValue() == Address.BROADCAST_ADDRESS) {
                switch (status) {
                    case OK:
                        TimeLogger.logHandleMessage(myAddress.getValue(), line.lineNumber);
                        device.handleMessage(line.getMessage(), this);
                        break;
                    case FAILURE:
                        status = PortStatus.OK;
                        TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, status);
                        TimeLogger.delay(Message.MESSAGE_TIME);
                        break;
                    default:
                        TimeLogger.delay(Message.MESSAGE_TIME);
                        break;
                }
                return true;
            }
        }
        return false;
    }

    void broadcastMessage(Message message) {
        TimeLogger.logSendMessage(myAddress.getValue(), line.lineNumber);
        line.broadcastMessage(message);
    }

    void unblock() {
        isBlocked = false;
        TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, status);
        if (isGenerator) {
            line.hasGeneration(true, myAddress.getValue());
        }
    }

    void block() {
        isBlocked = true;
        TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, PortStatus.BLOCK);
        if (isGenerator) {
            line.hasGeneration(false, myAddress.getValue());
        }
    }
}