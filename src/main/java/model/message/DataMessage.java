package model.message;

import model.Address;

public class DataMessage implements Message {
    private Address address;
    private int amountOfData = 1;
    private Status status;

    public DataMessage(Address address, int amountOfData, Status status) {
        this.address = address;
        this.amountOfData = (amountOfData > 0 && amountOfData < 33) ? amountOfData : amountOfData % 32 + 1;
        this.status = status;
    }

    @Override
    public int getTime() {
        return MESSAGE_TIME * (amountOfData + 1);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Status getStatus() {
        return status;
    }

}
