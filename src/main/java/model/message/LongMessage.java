package model.message;

public class LongMessage implements Message {
    int infoWords;
    messageCommand command;
    int address;
    boolean state;

    @Override
    public int getTime() {
        return COMMAND_TIME + PAUSE_TIME + infoWords * ANSWER_TIME;
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

    public int getInfoWords() {
        return infoWords;
    }

    public LongMessage(int infoWords, messageCommand command, int address) {
        this.infoWords = infoWords;
        this.command = command;
        this.address = address;
    }
}
