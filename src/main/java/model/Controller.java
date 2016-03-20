package model;


public class Controller {
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
        ArrayList <Integer> ar=new ArrayList<>(32);//сюда номера устрйоств помещаем, которые не отвечают
        int c=0;
        int j=0;
        for(i=0;i<31;i++){
            int k=0;
            if(!SendMassage()){
                k++;                            //если один раз не прошло
                if(!SendMassage())              // пробуем еще раз
                    k++;
            }
            // у нас сбой,если k==1 как раз решим, повторно если уйдет

            // у нас отказ или генерация
            if (k==2) {
                ar[j]=i;
                c++;
                j++;
                if (c==15){
                     FindGenerationObject();
                }
                else continue;

            }
            }
        // обработка отказавших
        for (int l=0;l<j;l++){
            int num=ar[l];
            ChangeTheLine();
            SendMassage(); // здесь как-то номер num должен учитываться в функции отсылки
        }

        }


    void FindGenerationObject();
    int GetAnsWord();

}
