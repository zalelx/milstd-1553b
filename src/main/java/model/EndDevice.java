package model;

import model.message.*;
import view.TimeLogger;
import java.util.Random;

public class EndDevice implements Device {
    private Port defaultPort;
    private Port reservePort;
    private Port current;
    private boolean isPreparedToSendInfo = false;
    private Address controllerAddress = new Address(0);

    public EndDevice(Address address, Line lineA, Line lineB) {
        this.defaultPort = new Port(lineA, this, "Port A " + address.toString());
        this.reservePort = new Port(lineB, this, "Port B " + address.toString());
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
        TimeLogger.delay((new Random()).nextInt(8) + 4);
        switch ((Command) message.getStatus()) {
            case BLOCK:
                block();
                break;
            case UNBLOCK:
                unblock();
                break;
            case GIVE_ANSWER:
                if (isPreparedToSendInfo)
                    sendMessage(new AnswerMessage(controllerAddress, Answer.READY));
                else
                    sendMessage(new AnswerMessage(controllerAddress, Answer.BUSY));
                break;
            case GIVE_INFORMATION:
                if (isPreparedToSendInfo) {
                    int amountOfInfoMessages = (new Random()).nextInt(31) + 1;
                    sendMessage(new AnswerMessage(controllerAddress, Answer.READY));
                    for (int i = 0; i < amountOfInfoMessages; i++) sendMessage(new DataMessage(controllerAddress));
                }
                break;

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

    public void setPreparedToSendInfo(boolean preparedToSendInfo) {
        isPreparedToSendInfo = preparedToSendInfo;
    }
}
