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


    Stage stage;

    public MetaController metaController;
    void setMetaController(MetaController metaController) {
        this.metaController = metaController;
    }



    @FXML
    void OkClicked(){
        String s1 = number.getText();
        int num = Integer.parseInt(s1);
        String s2 = generationProbability.getText();
        double generationProb = Double.parseDouble(s2);
        String s3 = faultProbability.getText();
        double faultProb = Double.parseDouble(s3);
        String s4 = denialProbability.getText();
        double denialProb = Double.parseDouble(s4);
        String s5 = probability.getText();
        double prob = Double.parseDouble(s5);
        metaController.performTests(num,generationProb,faultProb, prob, false);
        stage.close();
    }


    @FXML
    void CancelClicked(){
        stage.close();
    }


    void setStage(Stage stage){
        this.stage = stage;
    }

}
