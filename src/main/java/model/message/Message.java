package model.message;

import model.Address;

public interface Message {
    int MESSAGE_TIME = 20;

    int getTime();
    Address getAddress();
    void setAddress(Address address);
}
