package model;

import view.TimeLogger;

public class Address {
    final static int MIN_ADDRESS = 1;
    private final static int MAX_ADDRESS = 32;
    final static int BROADCAST_ADDRESS = MAX_ADDRESS + 1;
    final static int CONTROLLER_ADDRESS = MIN_ADDRESS - 1;

    private int value;

    public Address(int value) {
        if (value < CONTROLLER_ADDRESS && value > BROADCAST_ADDRESS)
            TimeLogger.log("Bad index"  + value, 0);
        this.value = value;
    }

    int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return value == address.value;
        //another useless commit

    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public int hashCode() {
        return value;
    }
}
