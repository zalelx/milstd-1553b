package model;


import model.message.Message;
import view.logging.TimeLogger;

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
                        break;
                    default:
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
        if (isGenerator)
            line.hasGeneration(true, myAddress.getValue());
//        if (isGenerator) {
//            status = PortStatus.GENERATION;
//            List<Port> ports = line.getPorts();
//            for (Port port : ports) {
//                port.setStatus(PortStatus.GENERATION);
////                TimeLogger.logChangePortStatus(i, line.lineNumber, PortStatus.GENERATION);
//            }
//        } else if (isDenial) {
//            status = PortStatus.DENIAL;
//            TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, status);
//        } else {
//            status = PortStatus.OK;
//            TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, status);
//        }
    }

    void block() {
        isBlocked = true;
        TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, PortStatus.BLOCK);
        if (isGenerator)
            line.hasGeneration(false, myAddress.getValue());

//
//        if (status != PortStatus.DENIAL) {
//            if (isGenerator) {
//                List<Port> ports = line.getPorts();
//                for (Port port : ports) {
//                    port.setPrevStatus();
////                TimeLogger.logChangePortStatus(i, line.lineNumber, PortStatus.GENERATION);
//                }
//
//                status = PortStatus.BLOCK;
//                TimeLogger.logChangePortStatus(myAddress.getValue(), line.lineNumber, status);
//            }
//        }
    }
}
