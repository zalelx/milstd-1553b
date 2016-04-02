package model;

import model.message.Command;
import model.message.CommandMessage;
import model.message.Message;

import java.util.ArrayList;


public class Controller implements Device {
    private AddressBook addressBook;

    public Controller(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public void sendMessage(Message message) {
        addressBook.sendMessage(message);
    }

    @Override
    public void handleMessage(Message message) {
        //todo добавить обработку пришедшего сообщения
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

    void testMKO() {
        ArrayList<Integer> ar = new ArrayList<>(32);//сюда номера устрйоств помещаем, которые не отвечают
        int c = 0;
        int j = 0;
        for (i = 0; i < 32; i++) {
            int k = 0;
            if (!SendMassage()) {
                k++;                            //если один раз не прошло
                if (!SendMassage())              // пробуем еще раз
                    k++;
            }
            // у нас сбой,если k==1 как раз решим, повторно если уйдет

            // у нас отказ или генерация
            if (k == 2) {
                ar[j] = i;
                c++;
                j++;
                if (c == 15) {
                    findGenerationObject();
                } else continue;

            }
        }
        // обработка отказавших
        for (int l = 0; l < j; l++)
            int num = ar[l];
        ChangeTheLine();
        SendMassage(); // здесь как-то номер num должен учитываться в функции отсылки
    }

    private void findGenerationObject() {
        int gennumb;

        //блокируем все ОУ
        ChangeTheLine();
        for (int i = 0; i < 32; i++) {
            SendMassage();   // сообщение massege.command=messageCommand.BLOCK;
        }

        // поочередно включаем и пытаемся проветси обмен
        ChangeTheLine();
        SendMassage();// сообщение massege.command=messageCommand.GIVE_ANSWORD;

        EndDevice.handleMassage();// как то обработается на ОУ
        if () continue;
        else {
            gennumb = i;
            System.out.println("Генератор найден!Его номер:" + gennumb);
            SendMassage(); // // сообщение massege.command=messageCommand.BLOCK;
        }

    }
}