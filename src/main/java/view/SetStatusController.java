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

    public void init() {
        this.LineA.setItems(this.Status);
        this.LineB.setItems(this.Status);
        this.LineA.setValue("Исправен");
        this.LineB.setValue("Исправен");
    }

    void setMetaController(MetaController metaController) {
        this.metaController = metaController;
    }
}
