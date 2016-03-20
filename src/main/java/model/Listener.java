package model;

public interface Listener {
    void handleMessage(TypeOfLine typeOfLine, Message message);
}
