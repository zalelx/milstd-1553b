package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.PortStatus;


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
        metaController.PerformTests(num,generationProb,faultProb,denialProb,prob);
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
