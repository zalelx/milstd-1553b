package model;

import model.message.Answer;
import model.message.AnswerMessage;
import model.message.Command;
import model.message.Message;

public class EndDevice implements Device {
    private Port defaultLine;
    private Port reserveLine;
    private Port current;
    private Address myAddress;

    private boolean isPreparedToSendInfo = true;

    public EndDevice(Address address, Line lineA, Line lineB) {
        this.defaultLine = new Port(lineA, this, "Port A " + address.toString());
        this.reserveLine = new Port(lineB, this, "Port B " + address.toString());
        this.current = defaultLine;
        this.myAddress = address;
    }

    public void sendMessage(Message message) {
        current.broadcastMessage(message);
    }

    @Override
    public void handleMessage(Message message) {
        if (message.getAddress().equals(myAddress) || message.getAddress().getValue() == Address.BROADCAST_ADDRESS) {
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
    }

    private void unblock() {
        current.block();
        current = (current == defaultLine) ? reserveLine : defaultLine;
    }

    private void block() {
        current.unblock();
        current = (current == defaultLine) ? reserveLine : defaultLine;
    }

    public void setPreparedToSendInfo(boolean preparedToSendInfo) {
        isPreparedToSendInfo = preparedToSendInfo;
    }
}
