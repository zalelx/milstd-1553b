package model;


import java.util.Map;

public class Controller {
    Line defaultLine;
    Line reserveLine;

    Map<TypeOfLine,>

    void SendMassege(Message message){

    }
    void handleMessage(Message message){

    }
    void getAddress(){

    }



    void ChangeTheLine(){
        if (Line==TypeOfLine.A) Line=TypeOfLine.B;
        else Line=TypeOfLine.A;
        return;
    }



    void Block(){
        Massage.command=NumberOfCommand.BLOCK;
    }

    void Unblock(){

        Massage.command=NumberOfCommand.UNBLOCK;
    }

    void TestMKO(){
        int c=0;
        for(i=0;i<31;i++){
            int k=0;
            if(!SendMassage()){
                k++;                            //если один раз не прошло
                if(!SendMassage())              // пробуем еще раз
                    k++;
            }
            // у нас сбой,если k==1 как раз решим, повторно если уйдет


            // у нас отказ или генерация
            if (k==2)



                int GetAnsWord();
    void FindGenerationObject();



}
