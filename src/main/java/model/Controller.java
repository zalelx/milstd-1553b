package model;

import model.message.Answer;
import model.message.Command;
import model.message.CommandMessage;
import model.message.Message;

import java.util.ArrayList;


public class Controller implements Device {
    private AddressBook addressBook;
    private Answer lastAnswer;
    private Address myAddress = new Address(Address.CONTROLLER_ADDRESS);

    public Controller(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public void sendMessage(Message message) {
        addressBook.sendMessage(message);
    }

    @Override
    public void handleMessage(Message message) {
        if (message.getAddress().equals(myAddress)  || message.getAddress().getValue() == Address.BROADCAST_ADDRESS)
            lastAnswer = (Answer) message.getStatus();
    }

    void changeLine(Address address) {
        addressBook.changeLine(address);
    }

    void block(int address) {
        try {
            sendMessage(new CommandMessage(new Address(address), Command.BLOCK));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void unblock(int address) {
        try {
            sendMessage(new CommandMessage(new Address(address), Command.UNBLOCK));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void testMKO(int amountOfEndDevices) {
        ArrayList<Address> ar = new ArrayList<>(32);//сюда адреса устрйоств помещаем, которые не отвечают
        int c = 0;
        int j = 0;
        int k;//счетчик
        for (int i = Address.MIN_ADDRESS; i <= amountOfEndDevices; i++) {
            sendMessage(new CommandMessage(new Address(i), Command.GIVE_ANSWER));
            k = 0;
            switch (lastAnswer) {
                case BUSY: {//если занят, просто посылаем второй раз, флажок занятости должен сниматься
                    sendMessage(new CommandMessage(new Address(i), Command.GIVE_ANSWER));
                    break;
                }
                case READY:
                    sendMessage(new CommandMessage(new Address(i), Command.GIVE_INFORMATION));
                    break;

                default:// если не приходит ответного слова
                    k++;
                    if (k == 2) {// отказ или генерация
                        ar.set(j,new Address(i));
                        c++;
                        j++;
                    } else {
                        sendMessage(new CommandMessage(new Address(i), Command.GIVE_ANSWER));
                    }
            }
        }
        if (c >= 3) findGenerationObject(amountOfEndDevices);// если 3 ОУ в отказе, то признак генерации
        // меньше 3, то сбой и переходим на резервную линию
        else {
            for (int l = 0; l < j; l++) {
                Address num = ar.get(l);
                changeLine(num);
                sendMessage(new CommandMessage(num, Command.GIVE_ANSWER));
            }

        }
    }


    private void findGenerationObject(int amountOfDevices){
        int numberofgen;
        //нужно заблокировать все ОУ,для этого меняем линию и шлем БЛОКИ
        for (int i=Address.MIN_ADDRESS;i<=amountOfDevices;i++)
        {
            changeLine(new Address(i));
            sendMessage(new CommandMessage(new Address(i),Command.BLOCK));
        }

        //включаем поочередно ОУ, причем по первоначальной линии
        for (int i=Address.MIN_ADDRESS;i<=amountOfDevices;i++)
        {
            changeLine(new Address(i));
            sendMessage(new CommandMessage(new Address(i),Command.UNBLOCK));
            sendMessage(new CommandMessage(new Address(i),Command.GIVE_ANSWER));
            switch(lastAnswer)
            {
                case BUSY:break;
                case READY:break;
                default:
                    numberofgen=i;// Нашли генерящее ОУ
                    System.out.println("Генератор найден!Его номер:" + numberofgen);
                    sendMessage(new CommandMessage(new Address(i),Command.BLOCK));
            }

        }

    }
}

