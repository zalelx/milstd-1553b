package view;

import javafx.fxml.FXML;
import javax.swing.JTextField;
import javafx.fxml.FXMLLoader;

public class SetValueController {
    JTextField jt= new JTextField(10);
    @FXML
    public void GetValue(){
       String a=jt.getText();
       int value=Integer.parseInt(a);
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("/fxml/newmenu.fxml"));
    }



}

