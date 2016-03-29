package model;

import model.message.Message;

public class EndDevice implements Device{
    Port portA;
    Port portB;
    Port current;

    public void sendMessage(Message message){
        current.broadcastMessage(message);
    }

    @Override
    public void handleMessage(Message message) {
        //todo добавить обработку пришедшего сообщения
    }


    void AskController(){}
    void Status(){}
}
