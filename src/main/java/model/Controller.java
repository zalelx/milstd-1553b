package model;

import model.message.Answer;
import model.message.Command;
import model.message.CommandMessage;
import model.message.Message;
import view.Logging.TimeLogger;

import java.util.ArrayList;
import java.util.List;


public class Controller implements Device {
    private final AddressBook addressBook;
    private Answer lastAnswer;
    private final Address myAddress = new Address(Address.CONTROLLER_ADDRESS);

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
        List<Address> notResponseAddresses = new ArrayList<>();//сюда адреса устрйоств помещаем, которые не отвечают

        for (int i = Address.MIN_ADDRESS; i <= amountOfEndDevices; i++) {
            Address address = new Address(i);
            Answer answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));

            if (answer == null) {
                TimeLogger.log("NOT RESPONSE ED#" + i, 0);
                answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));

                if (answer == null) {
                    notResponseAddresses.add(address);
                    TimeLogger.log("NOT RESPONSE ED#" + i, 0);
                    if (notResponseAddresses.size() >= 3) {
                        TimeLogger.log("START SEARCHING GENERATOR", 0);
                        findGenerationObject(amountOfEndDevices);
                        break;
                    }
                } else {
                    caseAnswer(address, answer);
                }
            } else {
                caseAnswer(address, answer);
            }
        }

        for (Address address : notResponseAddresses) {
            changeLine(address);
            Answer answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));
            if (answer == null) {
                TimeLogger.log("ED NOT RESPONDING AT RESERVE LINE #" + address.getValue(), 0);
            } else {
                caseAnswer(address, answer);
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
                caseAnswer(address, answer);
            }
        }
    }

    private void caseAnswer(Address address, Answer answer) {
        switch (answer) {
            case BUSY: {
                break;
            }
            case READY:
                sendMessage(new CommandMessage(address, Command.GIVE_INFORMATION));
                break;
        }

    }
}

