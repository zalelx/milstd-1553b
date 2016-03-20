package model;

import model.message.Message;

public interface Listener {
    void handleMessage(TypeOfLine typeOfLine, Message message);
}
