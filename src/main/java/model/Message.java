package model;

public interface Message {
    int PAUSE_TIME = 12;
    int COMMAND_TIME = 20;
    int ANSWER_TIME = 20;

    int getTime();
    int getAddress();
    int getCommand();
    boolean getState();

}
