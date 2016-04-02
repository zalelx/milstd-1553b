package model;

public class Address {
    final static int MIN_ADDRESS = 1;
    final static int MAX_ADDRESS = 32;
    final static int BROADCAST_ADDRESS = 33;
    final static int CONTROLLER_ADDRESS = 0;

    private int value;

    public Address(int value) throws Exception {
        if (value < CONTROLLER_ADDRESS && value > BROADCAST_ADDRESS) throw new Exception("Bad index" + value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
