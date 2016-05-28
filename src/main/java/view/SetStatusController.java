package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.PortStatus;


public class SetStatusController{

    @FXML
    public ComboBox<String> LineA;
    @FXML
    public ComboBox<String> LineB;
    @FXML
    TextField EdNumberField;

    @FXML
    CheckBox busy;

    private int portNumber;
    private Stage stage;

    private ObservableList<String> statuses = FXCollections.observableArrayList("Исправен", "Заблокирован", "Отказ", "Сбой", "Генерация");
    private MetaController metaController;

    void init() {
        this.LineA.setItems(statuses);
        this.LineB.setItems(statuses);
        this.LineA.setValue("Исправен");
        this.LineB.setValue("Исправен");
    }

    void setMetaController(MetaController metaController) {
        this.metaController = metaController;
    }

    private boolean isValid() {
        String a = EdNumberField.getText();
        try {
            portNumber = Integer.parseInt(a);
            if (portNumber > MetaController.amountOfEd || portNumber < 0) {
                throw new NumberFormatException();
            }
            return true;
        } catch (NumberFormatException e) {
            EdNumberField.setText("Неверное значение");
            return false;
        }
    }

    @FXML
    void OkClicked(){
        if (isValid()) {
            metaController.setPortStatusLineA(portNumber, parseStatus(LineA));
            metaController.setPortStatusLineB(portNumber, parseStatus(LineB));
//            if(busy.isSelected())
//                metaController.setPreparedToSendInfo(portNumber,false);
//            else metaController.setPreparedToSendInfo(portNumber,true);
            stage.close();
        }
    }

    @FXML
    void CancelClicked(){
        stage.close();
    }

    private PortStatus parseStatus(ComboBox<String> input){
        switch (input.getValue()){
            case "Исправен":
                return PortStatus.OK;
            case "Генерация":
                return PortStatus.GENERATION;
            case "Заблокирован":
                return PortStatus.BLOCK;
            case "Отказ":
                return PortStatus.DENIAL;
            case "Сбой":
                return PortStatus.FAILURE;
            default:
                return PortStatus.OK;
        }
    }

    void setStage(Stage stage){
        this.stage = stage;
    }

}
