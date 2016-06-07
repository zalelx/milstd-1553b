package model.message;

import model.Address;

public class AnswerMessage implements Message {
    private Address address;
    private Answer answer;

    public AnswerMessage(Address address, Answer answer) {
        this.address = address;
        this.answer = answer;
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
        return answer;
    }

}
