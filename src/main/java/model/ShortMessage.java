package model;

public class ShortMessage implements Message {
    int command;
    int address;
    boolean state;


    @Override
    public int getTime() {
        return COMMAND_TIME + PAUSE_TIME + ANSWER_TIME;
    }

    @Override
    public int getAddress() {
        return address;
    }

    @Override
    public int getCommand() {
        return command;
    }

    @Override
    public boolean getState() {
        return state;
    }
}
