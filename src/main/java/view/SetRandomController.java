package view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SetRandomController {

    @FXML
    TextField amountOfTestsField;

    @FXML
    TextField generationProbabilityField;

    @FXML
    TextField faultProbabilityField;

    @FXML
    TextField denialProbabilityField;

    @FXML
    TextField probabilityField;

    @FXML
    TextField amountOfDataMessagesField;

    @FXML
    CheckBox shortLogs;

    @FXML
    TextField busyProbabilityField;


    private Stage stage;

    private int amountOfTests;
    private double generationProbability;
    private double faultProbability;
    private double denialProbability;
    private double busyProbability;
    private double probability;
    private int amountOfDataMessages;
    private boolean flag = true;


    private MetaController metaController;

    void setMetaController(MetaController metaController) {
        this.metaController = metaController;
//        generationProbabilityField.setText("0.05");`
//        faultProbabilityField.setText("0.7");
//        denialProbabilityField.setText("0.25");
//        probabilityField.setText("0.5");
//        amountOfDataMessagesField.setText("4");
//        amountOfTestsField.setText("100");
//        shortLogs.setSelected(true);
    }


    @FXML
    void okClicked() {
        String s1 = amountOfTestsField.getText();
        try {
            amountOfTests = Integer.parseInt(s1);
            if (amountOfTests < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            amountOfTestsField.setText("Неверно!");
            flag = false;
        }

        String s2 = generationProbabilityField.getText();
        try {
            generationProbability = Double.parseDouble(s2);
            if (generationProbability > 1 || generationProbability < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException f) {
            generationProbabilityField.setText("Неверно!");
            flag = false;
        }

        try {
            busyProbability = Math.abs(Double.parseDouble(busyProbabilityField.getText()));
            if (busyProbability > 1 || busyProbability < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            busyProbabilityField.setText("Неверно!");
            flag = false;
        }

        String s3 = faultProbabilityField.getText();
        try {
            faultProbability = Double.parseDouble(s3);
            if (generationProbability + faultProbability > 1 || faultProbability < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException d) {
            faultProbabilityField.setText("Неверно!");
            flag = false;
        }

        String s4 = denialProbabilityField.getText();
        try {
            denialProbability = Double.parseDouble(s4);
            if (generationProbability + faultProbability + denialProbability != 1 || denialProbability < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException g) {
            denialProbabilityField.setText("Неверно!");
            flag = false;
        }

        String s5 = probabilityField.getText();
        try {
            probability = Double.parseDouble(s5);
            if (probability > 1 || probability < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException h) {
            probabilityField.setText("Неверно!");
            flag = false;
        }

        String s6 = amountOfDataMessagesField.getText();
        try {
            amountOfDataMessages = Integer.parseInt(s6);
            if (amountOfDataMessages > 32 || amountOfDataMessages < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException h) {
            amountOfDataMessagesField.setText("Неверно!");
            flag = false;
        }

        if (flag) {
            metaController.setAmountOfDataMessages(amountOfDataMessages);
            metaController.performTests(amountOfTests, generationProbability, faultProbability, denialProbability, busyProbability, probability, shortLogs.isSelected());
            stage.close();
        }
        flag = true;
    }


    @FXML
    void cancelClicked() {
        stage.close();
    }


    void setStage(Stage stage) {
        this.stage = stage;
    }

}
