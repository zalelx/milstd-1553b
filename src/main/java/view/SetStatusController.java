package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;


public class SetStatusController{

    @FXML
    public ComboBox<String> LineA;
    @FXML
    public ComboBox<String> LineB;

    private ObservableList<String> Status = FXCollections.observableArrayList("Исправен", "Генерация", "Отказ", "Сбой");
    private MetaController metaController;

    @FXML
    public void init() {
        LineA.setItems(Status);
        LineB.setItems(Status);
        LineA.setValue("Исправен");
        LineB.setValue("Исправен");
    }

    public void comboLineA(){}
    public void comboLineB(){}
    public void OKbutton(){}
    public void Back(){}

    void setMetaController(MetaController metaController) {
        this.metaController = metaController;
    }
}
