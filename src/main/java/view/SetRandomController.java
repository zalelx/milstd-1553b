package view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SetRandomController {

    @FXML
    TextField number;

    @FXML
    TextField generationProbability;

    @FXML
    TextField faultProbability;

    @FXML
    TextField denialProbability;

    @FXML
    TextField probability;

    @FXML
    TextField AmountOfDataMessages;

    @FXML
    CheckBox ShortLogs;


    Stage stage;

    int num;
    double generationProb;
    double faultProb;
    double denialProb;
    double prob;
    int amountofdev;
    boolean flag=true;


    public MetaController metaController;

    void setMetaController(MetaController metaController) {
        this.metaController = metaController;
        generationProbability.setText("0.05");
        faultProbability.setText("0.7");
        denialProbability.setText("0.25");
        probability.setText("0.5");
        AmountOfDataMessages.setText("4");
        number.setText("100");
    }


    @FXML
    void OkClicked() {
        String s1 = number.getText();
        try {
            num = Integer.parseInt(s1);
            if (num < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            number.setText("Неверно!");
            flag=false;
        }

        String s2 = generationProbability.getText();
        try {
            generationProb = Double.parseDouble(s2);
            if(generationProb>1)
                throw new NumberFormatException();
        } catch (NumberFormatException f) {
            generationProbability.setText("Неверно!");
            flag=false;
        }


        String s3 = faultProbability.getText();
        try {
            faultProb = Double.parseDouble(s3);
            if(generationProb+faultProb>1)
                throw new NumberFormatException();
        } catch (NumberFormatException d) {
            faultProbability.setText("Неверно!");
            flag=false;
        }

        String s4 = denialProbability.getText();
        try {
            denialProb = Double.parseDouble(s4);
            if(generationProb+faultProb+denialProb!=1)
                throw new NumberFormatException();
        } catch (NumberFormatException g) {
            denialProbability.setText("Неверно!");
            flag=false;
        }

        String s5 = probability.getText();
        try {
            prob = Double.parseDouble(s5);
            if(prob>1)
                throw new NumberFormatException();
        } catch (NumberFormatException h) {
            probability.setText("Неверно!");
            flag=false;
        }

        String s6 = AmountOfDataMessages.getText();
        try {
            amountofdev = Integer.parseInt(s6);
            if(amountofdev>32 || amountofdev<0)
                throw new NumberFormatException();
        } catch (NumberFormatException h) {
            AmountOfDataMessages.setText("Неверно!");
            flag=false;
        }

        if (flag==true){
            if (ShortLogs.isSelected()){
                metaController.setAmountOfDataMessages(amountofdev);
                metaController.performTests(num, generationProb, faultProb,denialProb, prob, true);
            }
            else{
                metaController.setAmountOfDataMessages(amountofdev);
                metaController.performTests(num, generationProb, faultProb,denialProb, prob, false);
            }

            stage.close();
        }
        flag=true;

    }


    @FXML
    void CancelClicked() {
        stage.close();
    }


    void setStage(Stage stage) {
        this.stage = stage;
    }

}
