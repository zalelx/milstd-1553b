package view;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class newmenuController {
    @FXML
    public void SetStatus(){
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("/fxml/SetStatus.fxml"));

    }
}