package model.message;

public interface Message {
    int MESSAGE_TIME = 20;

    int getTime();
    int getAddress();
    void setAddress(int address);
}
