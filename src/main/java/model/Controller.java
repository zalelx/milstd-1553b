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

    public ArrayList<Answer> testMKO(int amountOfEndDevices) {
        TimeLogger.log("START TEST_MKO", 0);
        notResponseAddresses.clear();

        for (int i = Address.MIN_ADDRESS; i <= amountOfEndDevices; i++) {
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
                        TimeLogger.log("START SEARCHING GENERATOR", 0);
                        findGenerationObject(amountOfEndDevices);
                    }
                } else {
                    isEdReady(answer);
                }
            } else {
                isEdReady(answer);
            }
        }

        if (notResponseAddresses.size() > 0) {
            return restore();
        }
        return null;
    }

    public void connectToAll(int amountOfEndDevices) {
        TimeLogger.log("START TO BROADCAST", 0);
        ArrayList<Answer> answers = null;

        for (int i = Address.MIN_ADDRESS; i <= amountOfEndDevices; i++) {
            Address address = new Address(i);
//            sendData(address);

            Answer answer = sendData(address);
            if (answer == null) {
                TimeLogger.log("NOT RESPONSE ED#" + i, ED_DELAY);
                answer = sendData(address);

                if (answer == null) {
                    notResponseAddresses.add(address);
                    TimeLogger.log("NOT RESPONSE ED#" + i, ED_DELAY);
                    if (notResponseAddresses.size() >= 3) {
                        answers = testMKO(amountOfEndDevices);
                    }
                } else {
//                    sendData(/*answer,*/ address);
                }
            } else {
                 if (!isEdReady(answer)) {
                     sendData(address); // занятость
                 }

//                sendData(/*answer,*/ address);
            }
        }

        if (answers == null) {
            answers = restore();
        }

        for (int i = 0; i < notResponseAddresses.size(); i++) {
            sendData(/*answers.get(i),*/ notResponseAddresses.get(i));
        }
    }

    private ArrayList<Answer> restore() {
        ArrayList<Answer> answers = new ArrayList<>(notResponseAddresses.size());
        for (Address address : notResponseAddresses) {
            changeLine(address);
            Answer answer = sendAndHandleMessage(new CommandMessage(address, Command.GIVE_ANSWER));
            answers.add(answer);
            if (answer == null) {
                TimeLogger.log("ED NOT RESPONDING AT RESERVE LINE ED#" + address.getValue(), ED_DELAY);
                changeLine(address);
            }
        }
        return answers;
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
                } else {
                    isEdReady(answer);
                }
            } else {
                isEdReady(answer);
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

    private Answer sendData(/*Answer answer,*/ Address address) {
//        if (isEdReady(answer)) {
        Answer result = null;
        sendMessage(new CommandMessage(address, Command.PREPARE_TO_RECIEVE));
        for (int j = 0; j < amountOfDataMessages; j++) {
            DataMessage dataMessage = new DataMessage(address);
            if (j + 1 == amountOfDataMessages) {
                dataMessage.setEndMessage(true);
                result = sendAndHandleMessage(dataMessage);
            } else {
                sendMessage(dataMessage);
            }
        }
        return result;
//        }
    }
}