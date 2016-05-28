package view;

import javafx.fxml.FXML;
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


    Stage stage;

    int num;
    double generationProb;
    double faultProb;
    double denialProb;
    double prob;
    boolean flag=true;


    public MetaController metaController;

    void setMetaController(MetaController metaController) {
        this.metaController = metaController;
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

        if (flag==true){
             metaController.performTests(num, generationProb, faultProb, prob, false);
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
