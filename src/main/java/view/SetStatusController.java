package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;



public class SetStatusController{

    @FXML
    public ComboBox<String> LineA;
    @FXML
    public ComboBox<String> LineB;

    ObservableList<String> Status = FXCollections.observableArrayList(new String[]{"Исправен", "Генерация", "Отказ", "Сбой"});
    private MetaController metaController;

    public void initialize(URL location, ResourceBundle resources) {
        this.LineA.setItems(this.Status);
        this.LineB.setItems(this.Status);
        this.LineA.setValue("Исправен");
        this.LineB.setValue("Исправен");
    }

    public void setMetaController(MetaController metaController) {
        this.metaController = metaController;
    }
}
