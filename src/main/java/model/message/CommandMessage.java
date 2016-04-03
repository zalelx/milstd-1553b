package model.message;

import model.Address;

public class CommandMessage implements Message {
    private Command command;
    private Address address;

    public CommandMessage(Address address, Command command) {
        this.address = address;
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
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
        return command;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
