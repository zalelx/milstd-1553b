package model;

public class LongMessage implements Message{
    int infoWords;

    @Override
    public int getTime() {
        return COMMAND_TIME + PAUSE_TIME + infoWords * ANSWER_TIME;
    }

    public int getInfoWords() {
        return infoWords;
    }

    public LongMessage(int infoWords) throws Exception {
        if (infoWords > 0 && infoWords <= 32)
            this.infoWords = infoWords;
        else throw new Exception("Invalid number of info words!");
    }
}
