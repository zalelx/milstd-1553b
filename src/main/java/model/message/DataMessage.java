package model.message;

public class DataMessage implements Message {
    private int address;

    public DataMessage(int address) {
        this.address = address;
    }

    @Override
    public int getTime() {
        return MESSAGE_TIME;
    }

    @Override
    public int getAddress() {
        return address;
    }

    @Override
    public void setAddress(int address) {
        this.address = address;
    }
}
