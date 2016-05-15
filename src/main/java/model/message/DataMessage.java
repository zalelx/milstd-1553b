package model.message;

import model.Address;

public class DataMessage implements Message {
    private Address address;
    private boolean isEndMessage = false;

    public DataMessage(Address address) {
        this.address = address;
    }

    @Override
    public int getTime() {
        return MESSAGE_TIME;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isEndMessage() {
        return isEndMessage;
    }

    public void setEndMessage(boolean endMessage) {
        isEndMessage = endMessage;
    }
}
