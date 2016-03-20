package model.message;

public class ShortMessage implements Message {
    messageCommand command;
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
    public messageCommand getCommand() {
        return command;
    }

    @Override
    public boolean getState() {
        return state;
    }
}
