package model;

import model.message.*;
import view.TimeLogger;

import java.util.ArrayList;
import java.util.List;


public class Controller implements Device {
    private final AddressBook addressBook;
    private Answer lastAnswer;
    private final Address myAddress = new Address(Address.CONTROLLER_ADDRESS);
    private List<Address> notResponseAddresses = new ArrayList<>();
    private int amountOfDataMessages = 4;
    private final static int ED_DELAY = EndDevice.ED_DELAY;
    private final static int CTRL_DELAY = 50;
    private final static int NOT_RESPONSE_LIMIT = 3;

    public Controller(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public Address getMyAddress() {
        return myAddress;
    }

    public void sendMessage(Message message) {
        addressBook.sendMessage(message);
    }

    @Override
    public Port getDefaultPort() {
        return addressBook.getDefaultPort();
    }

    @Override
    public Port getReservePort() {
        return addressBook.getReservePort();
    }

    @Override
    public void handleMessage(Message message, Port port) {
        lastAnswer = (Answer) message.getStatus();
    }

    private void changeLine(Address address) {
        addressBook.changeLine(address);
    }

    private void block(Address address) {
        sendMessage(new CommandMessage(address, Command.BLOCK));
    }

    private void unblock(Address address) {
        sendMessage(new CommandMessage(address, Command.UNBLOCK));
    }

    private Answer sendAndHandleMessage(Message message) {
        sendMessage(message);
        Answer ret = this.lastAnswer;
        addressBook.getDefaultPort().getLine().setMessage(null);
        addressBook.getReservePort().getLine().setMessage(null);
        this.lastAnswer = null;
        TimeLogger.delay(CTRL_DELAY);
        return ret;
    }

    public boolean testMKO(int amountOfEndDevices) {
        TimeLogger.log("START TEST_MKO", 0);
        int startIndex = notResponseAddresses.size() != 0 ? notResponseAddresses.get(notResponseAddresses.size() - 1).getValue() + 1 : Address.MIN_ADDRESS;
        boolean wasGeneration = false;

        for (int i = startIndex; i <= amountOfEndDevices; i++) {
            Address address = new Address(i);
            Answer answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));
            if (answer == null) {
                TimeLogger.log("NOT RESPONSE ED#" + i, ED_DELAY);
                answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));

                if (answer == null) {
                    notResponseAddresses.add(address);
                    TimeLogger.log("NOT RESPONSE ED#" + i, ED_DELAY);
                    if (notResponseAddresses.size() >= amountOfEndDevices) {
                        notResponseAddresses.clear();
                        wasGeneration = true;
                        TimeLogger.log("START SEARCHING GENERATOR", 0);
                        findGenerationObject(amountOfEndDevices);
                    }
                }
            }
        }
        restore();
        return wasGeneration;
    }

    public void connectToAll(int amountOfEndDevices) {
        TimeLogger.log("START TO BROADCAST", 0);
        boolean wasTest = false;
        for (int i = Address.MIN_ADDRESS; i <= amountOfEndDevices; i++) {
            Address address = new Address(i);

            Answer answer = sendData(address);
            if (answer == null) {
                TimeLogger.log("NOT RESPONSE ED#" + i, ED_DELAY);
                answer = sendData(address);

                if (answer == null) {
                    notResponseAddresses.add(address);
                    TimeLogger.log("NOT RESPONSE ED#" + i, ED_DELAY);
                    if (notResponseAddresses.size() >= NOT_RESPONSE_LIMIT) {
                        if (testMKO(amountOfEndDevices)) {
                            i = Address.MIN_ADDRESS - 1;
                            notResponseAddresses.clear();
                        }
                        wasTest = true;
//                        break;
                    }
                } else {
                    handleData(answer, address);
                }
            } else {
                handleData(answer, address);
            }
        }
        if (!wasTest) {
            restore();
        }
        for (Address address : notResponseAddresses) {
            handleData(sendData(address), address);
        }
    }

    private void restore() {
        for (Address address : notResponseAddresses) {
            changeLine(address);
            Answer answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));
            if (answer == null) {
                TimeLogger.log("NOT RESPONDING AT RESERVE LINE ED#" + address.getValue(), ED_DELAY);
                changeLine(address);
            } else {
                TimeLogger.log("ONLINE ED#" + address.getValue(), 0);
            }
        }
    }

    private void findGenerationObject(int amountOfDevices) {
        //нужно заблокировать все ОУ,для этого меняем линию и шлем БЛОКИ
        for (int i = Address.MIN_ADDRESS; i <= amountOfDevices; i++) {
            Address address = new Address(i);
            changeLine(address);
            block(address);
        }
        for (int i = Address.MIN_ADDRESS; i <= amountOfDevices; i++) {
            Address address = new Address(i);
            unblock(address);
            changeLine(address);

            Answer answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));
            if (answer == null) {

                TimeLogger.log("NOT RESPONSE ED#" + i, ED_DELAY);
                answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));
                if (answer == null) {
                    TimeLogger.log("GENERATOR FOUND. ED#" + i, ED_DELAY);
                    changeLine(address);
                    sendMessage(new CommandMessage(address, Command.BLOCK));
                    changeLine(address);
                    notResponseAddresses.add(address);
                }
            }
        }
    }

    private boolean isEdReady(Answer answer) {
        switch (answer) {
            case BUSY: {
                return false;
            }
            case READY:
                return true;
        }
        return false;
    }

    public void setAmountOfDataMessages(int amountOfDataMessages) {
        this.amountOfDataMessages = amountOfDataMessages;
    }

    private Answer sendData(Address address) {
        Answer result;
        DataMessage dataMessage = new DataMessage(address, amountOfDataMessages, Command.GIVE_ANSWER);
        result = sendAndHandleMessage(dataMessage);
        return result;
    }

    private void handleData(Answer answer, Address address) {
        if (isEdReady(answer)) {
            TimeLogger.log("DATA SEND ED#" + address.getValue(), 0);
        } else {
            TimeLogger.log("BUSY ED#" + address.getValue(), -45);
            handleData(sendData(address), address);
        }
    }
}