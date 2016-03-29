package model.message;

public class AnswerMessage implements Message {
    private int address;
    private Answer answer;

    public AnswerMessage(int address, Answer answer) {
        this.address = address;
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
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
