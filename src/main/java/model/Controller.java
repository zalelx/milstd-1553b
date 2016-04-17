package model;

import model.message.Answer;
import model.message.Command;
import model.message.CommandMessage;
import model.message.Message;
import view.TimeLogger;

import java.util.ArrayList;


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
        addressBook.sendMessage(message);
    }

    @Override
    public void handleMessage(Message message, Port port) {
        lastAnswer = (Answer) message.getStatus();
        TimeLogger.delay(1000);
    }

    private void changeLine(Address address) {
        addressBook.changeLine(address);
    }

    void block(int address) {
        sendMessage(new CommandMessage(new Address(address), Command.BLOCK));
    }

    void unblock(int address) {
        sendMessage(new CommandMessage(new Address(address), Command.UNBLOCK));
    }

    Answer getLastAnswer() {
        Answer ret = this.lastAnswer;
        this.lastAnswer = null;
        return ret;
    }

    public void testMKO(int amountOfEndDevices) {
        ArrayList<Address> ar = new ArrayList<>(32);//сюда адреса устрйоств помещаем, которые не отвечают
        int j = 0;
        for (int i = Address.MIN_ADDRESS; i <= amountOfEndDevices; i++) {
            sendMessage(new CommandMessage(new Address(i), Command.GIVE_ANSWER));
            Answer answer = getLastAnswer();

            if (answer == null) {
                sendMessage(new CommandMessage(new Address(i), Command.GIVE_ANSWER));
                if (getLastAnswer() == null) {// отказ или генерация
                    ar.add(j, new Address(i));
                    j++;
                }
            } else {
                switch (answer) {
                    case BUSY: {//если занят, просто посылаем второй раз, флажок занятости должен сниматься
                        sendMessage(new CommandMessage(new Address(i), Command.GIVE_ANSWER));
                        //lastAnswer = null;
                        break;
                    }
                    case READY:
                        sendMessage(new CommandMessage(new Address(i), Command.GIVE_INFORMATION));
                        //lastAnswer = null;
                        break;
                }
            }
        }

        if (j >= 3) {
            TimeLogger.log("START SEARCHING GENERATOR");
            findGenerationObject(amountOfEndDevices);// если 3 ОУ в отказе, то признак генерации
        }
        // меньше 3, то сбой и переходим на резервную линию
        else {
            for (int l = 0; l <= j; l++) {
                Address num = ar.get(l);
                changeLine(num);
                sendMessage(new CommandMessage(num, Command.GIVE_ANSWER));
            }
        }
    }

    private void findGenerationObject(int amountOfDevices) {
        int numberOfGen;
        //нужно заблокировать все ОУ,для этого меняем линию и шлем БЛОКИ
        for (int i = Address.MIN_ADDRESS; i <= amountOfDevices; i++) {
            changeLine(new Address(i));
            sendMessage(new CommandMessage(new Address(i), Command.BLOCK));
        }

        //включаем поочередно ОУ, причем по первоначальной линии
        for (int i = Address.MIN_ADDRESS; i <= amountOfDevices; i++) {
            sendMessage(new CommandMessage(new Address(i), Command.UNBLOCK));
            changeLine(new Address(i));
            sendMessage(new CommandMessage(new Address(i), Command.GIVE_ANSWER));

            Answer answer = getLastAnswer();
            if (answer == null) {
                numberOfGen = i;// Нашли генерящее ОУ
                TimeLogger.log("GENERATOR FOUND. ED#" + numberOfGen);
                sendMessage(new CommandMessage(new Address(i), Command.BLOCK));
            } else {
                switch (answer) {
                    case BUSY:
                        break;
                    case READY:
                        break;

                }
            }

        }
    }
}

