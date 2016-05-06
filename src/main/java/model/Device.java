package model;

import model.message.Message;

public interface Device {
    void handleMessage(Message message, Port port);
    void sendMessage(Message message);

    Port getDefaultPort();

    Port getReservePort();
}
