package model.message;

import model.message.messageCommand;

public interface Message {
    int PAUSE_TIME = 12;
    int COMMAND_TIME = 20;
    int ANSWER_TIME = 20;

    int getTime();
    int getAddress();
    messageCommand getCommand();
    boolean getState();

}
