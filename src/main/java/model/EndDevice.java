package model;

import model.message.*;
import view.Logging.TimeLogger;

public class EndDevice implements Device {
    private Port defaultPort;
    private Port reservePort;
    private Port current;
    private boolean isReady = false;
    private Address controllerAddress = new Address(0);
    private int dataMessageAmount = 0;


    public EndDevice(Address address, Line lineA, Line lineB) {
        this.defaultPort = new Port(lineA, this);
        this.reservePort = new Port(lineB, this);
        this.current = defaultPort;
        defaultPort.setMyAddress(address);
        reservePort.setMyAddress(address);
    }

    @Override
    public void sendMessage(Message message) {
        current.broadcastMessage(message);
    }

    @Override
    public void handleMessage(Message message, Port port) {
        if (message.getStatus() instanceof Command) {
            TimeLogger.delay(12);
            switch ((Command) message.getStatus()) {
                case BLOCK:
                    block();
                    break;
                case UNBLOCK:
                    unblock();
                    break;
                case GIVE_ANSWER:
                    if (isReady)
                        sendMessage(new AnswerMessage(controllerAddress, Answer.READY));
                    else
                        sendMessage(new AnswerMessage(controllerAddress, Answer.BUSY));
                    break;
                case GIVE_INFORMATION:
                    if (isReady) {
                        sendMessage(new AnswerMessage(controllerAddress, Answer.READY));
                        for (int i = 0; i < dataMessageAmount; i++) sendMessage(new DataMessage(controllerAddress));
                    }
                    break;
                case PREPARE_TO_RECIEVE:
                    return;
            }
            TimeLogger.delay(12);
        }
        if (message instanceof DataMessage && ((DataMessage) message).isEndMessage()) {
            TimeLogger.delay(12);
            sendMessage(new AnswerMessage(controllerAddress, isReady ? Answer.READY : Answer.BUSY));
        }
    }

    public Port getReservePort() {
        return reservePort;
    }

    public Port getDefaultPort() {
        return defaultPort;
    }

    private void unblock() {
        current = (current == defaultPort) ? reservePort : defaultPort;
        current.unblock();
    }

    private void block() {
        current.block();
        current = (current == defaultPort) ? reservePort : defaultPort;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public void setDataMessageAmount(int dataMessageAmount) {
        this.dataMessageAmount = dataMessageAmount;
    }
}
