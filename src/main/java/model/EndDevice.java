package model;

import model.message.Answer;
import model.message.AnswerMessage;
import model.message.Command;
import model.message.Message;

public class EndDevice implements Device {
    private Port defaultPort;
    private Port reservePort;
    private Port current;
    private boolean isPreparedToSendInfo = true;

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
    public void handleMessage(Message message) {
        switch ((Command) message.getStatus()) {
            case BLOCK:
                block();
                break;
            case UNBLOCK:
                unblock();
                break;
            case GIVE_ANSWER:
                if (isPreparedToSendInfo)
                    sendMessage(new AnswerMessage(new Address(0), Answer.READY));
                else
                    sendMessage(new AnswerMessage(new Address(0), Answer.BUSY));
                break;
            case GIVE_INFORMATION:
                // todo добавить выдачу информации
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
        current.block();
        current = (current == defaultPort) ? reservePort : defaultPort;
    }

    private void block() {
        current.unblock();
        current = (current == defaultPort) ? reservePort : defaultPort;
    }

    public void setPreparedToSendInfo(boolean preparedToSendInfo) {
        isPreparedToSendInfo = preparedToSendInfo;
    }
}
