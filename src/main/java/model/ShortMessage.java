package model;

public class ShortMessage implements Message {
    @Override
    public int getTime() {
        return COMMAND_TIME + PAUSE_TIME + ANSWER_TIME;
    }
}
