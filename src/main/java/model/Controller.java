package model;

import model.message.*;
import view.Logging.TimeLogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Controller implements Device {
    private final AddressBook addressBook;
    private Answer lastAnswer;
    private final Address myAddress = new Address(Address.CONTROLLER_ADDRESS);
    private List<Address> notResponseAddresses = new ArrayList<>();
    private int amountOfDataMessages = 2;

    public Controller(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public Address getMyAddress() {
        return myAddress;
    }

    public void sendMessage(Message message) {
        TimeLogger.delay(50);
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
        return ret;
    }

    public void testMKO(int amountOfEndDevices) {
        TimeLogger.log("START TEST_MKO", 0);
        notResponseAddresses = new ArrayList<>();

        for (int i = Address.MIN_ADDRESS; i <= amountOfEndDevices; i++) {
            Address address = new Address(i);
            Answer answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));

            if (answer == null) {
                TimeLogger.log("NOT RESPONSE ED#" + i, 10);
                answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));

                if (answer == null) {
                    notResponseAddresses.add(address);
                    TimeLogger.log("NOT RESPONSE ED#" + i, 0);
                    if (notResponseAddresses.size() >= amountOfEndDevices) {
                        TimeLogger.log("START SEARCHING GENERATOR", 0);
                        findGenerationObject(amountOfEndDevices);
                        break;
                    }
                } else {
                    caseAnswer(answer);
                }
            } else {
                caseAnswer(answer);
            }
        }

        for (Address address : notResponseAddresses) {
            changeLine(address);
            Answer answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));
            if (answer == null) {
                TimeLogger.log("ED NOT RESPONDING AT RESERVE LINE #" + address.getValue(), 0);
            } else {
                changeLine(address);
            }
        }
        notResponseAddresses.clear();
    }

    public void connectToAll(int amountOfEndDevices) {
        TimeLogger.log("START TO BROADCAST", 0);

        for (int i = Address.MIN_ADDRESS; i <= amountOfEndDevices; i++) {
            Address address = new Address(i);
            Answer answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));

            if (answer == null) {
                TimeLogger.log("NOT RESPONSE ED#" + i, 10);
                answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));

                if (answer == null) {
                    notResponseAddresses.add(address);
                    TimeLogger.log("NOT RESPONSE ED#" + i, 0);
                    if (notResponseAddresses.size() >= 3) {
                        testMKO(amountOfEndDevices);
                        notResponseAddresses.clear();
                    }

                    changeLine(address);
                }
            } else {
                if (caseAnswer(answer)){
                    sendMessage(new CommandMessage(address, Command.PREPARE_TO_RECIEVE));
                    for (int j = 0; j < amountOfDataMessages; j++) {
                        DataMessage dataMessage = new DataMessage(address);
                        if (j++ == amountOfDataMessages)
                            dataMessage.setEndMessage(true);
                        sendMessage(dataMessage);
                    }
                }
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

        //включаем поочередно ОУ, причем по первоначальной линии
        for (int i = Address.MIN_ADDRESS; i <= amountOfDevices; i++) {
            Address address = new Address(i);
            unblock(address);
            changeLine(address);

            Answer answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));
            if (answer == null) {
                answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));
                if (answer == null) {
                    TimeLogger.log("GENERATOR FOUND. ED#" + i, 0);
                    changeLine(address);
                    sendMessage(new CommandMessage(address, Command.BLOCK));
                }
            } else {
                caseAnswer(answer);
            }
        }
    }

    private boolean caseAnswer(Answer answer) {
        switch (answer) {
            case BUSY: {
                return false;
            }
            case READY:
                return true;
        }
        return false;
    }
}





