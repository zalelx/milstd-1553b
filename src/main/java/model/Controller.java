package model;

import model.message.ShortMessage;
import model.message.messageCommand;

import java.util.ArrayList;


public class Controller implements Device{
    AddressBook addressBook;

    void sendShortMessage(int address, messageCommand command) throws Exception {
        Line line = addressBook.getLine(address);
        line.broadcastMessage(new ShortMessage());
    }

    void getAddress() {

    }


    void ChangeTheLine() {
        if (Line == TypeOfLine.A) Line = TypeOfLine.B;
        else Line = TypeOfLine.A;
        return;
    }


    void Block() {
        Massage.command = messageCommand.BLOCK;
    }

    void Unblock() {

        Massage.command = messageCommand.UNBLOCK;
    }

    void TestMKO() {
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
                    FindGenerationObject();
                } else continue;

            }
        }
        // обработка отказавших
        for (int l = 0; l < j; l++)
            int num = ar[l];
        ChangeTheLine();
        SendMassage(); // здесь как-то номер num должен учитываться в функции отсылки
    }


    void FindGenerationObject() {
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
            system.out.println('Генератор найден!Его номер:' + gennumb);
            SendMassage(); // // сообщение massege.command=messageCommand.BLOCK;
        }

    }
}





