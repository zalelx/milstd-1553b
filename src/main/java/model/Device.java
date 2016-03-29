package model;

import model.message.Message;

public interface Device {
    void handleMessage(Message message);
    void sendMessage(Message message);
}
