package model.message;

public class CommandMessage implements Message {
    private Command command;
    private int address;

    public CommandMessage(int address, Command command) {
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
    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
