package model;


import model.message.Message;
import view.ChangeColor;
import view.Logging.TimeLogger;

import java.util.List;

public class Port {
    private Device device;
    private Line line;
    private PortStatus status = PortStatus.OK;
    private boolean isDenial = false;
    private boolean isGenerator = false;
    private Address myAddress;

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
    }

    public PortStatus getStatus() {
        return status;
    }

    public void setStatus(PortStatus status) {
        if (status.equals(PortStatus.DENIAL))
            isDenial = true;
        this.status = status;
    }

    Line getLine() {
        return line;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    boolean handleMessage() {
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
                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    void broadcastMessage(Message message) {
        switch (status) {
            case OK:
                TimeLogger.logSendMessage(myAddress.getValue(), line.lineNumber);
                line.broadcastMessage(message);
                break;
            default:
                break;
        }
    }

    void unblock() {
        if (isGenerator) {
            status = PortStatus.GENERATION;
            List<Port> ports = line.getPorts();
            for (int i = 0; i < ports.size(); i++) {
                ports.get(i).setStatus(PortStatus.GENERATION);
//                TimeLogger.logChangePortStatus(i, line.lineNumber, PortStatus.GENERATION);
            }
        } else if (isDenial) {
            status = PortStatus.DENIAL;
            TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, status);
        } else {
            status = PortStatus.OK;
            TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, status);
        }
    }

    void block() {
        if (status != PortStatus.DENIAL) {
            if (isGenerator) {
                List<Port> ports = line.getPorts();
                for (int i = 0; i < ports.size(); i++) {
                    ports.get(i).setStatus(PortStatus.GENERATION);
//                TimeLogger.logChangePortStatus(i, line.lineNumber, PortStatus.GENERATION);
                }


                status = PortStatus.BLOCK;
                TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, status);
            }
        }
    }
}
