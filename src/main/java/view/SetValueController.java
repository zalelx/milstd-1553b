package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class SetValueController {
    @FXML
    private TextField amountOfEDTextField = new TextField();

    private int amountOfED;
    Stage stage;

    @FXML
    public void getValue() {
        String a = amountOfEDTextField.getText();
        try {
            amountOfED = Integer.parseInt(a);
            if (amountOfED > 32 || amountOfED < 0) {
                throw new NumberFormatException();
            }
            showNewMenu();
        } catch (NumberFormatException e) {
            amountOfEDTextField.setText("Неверное значение");
        }
    }



    private void showNewMenu() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/newmenu.fxml"));
        try {
            Stage mainMenu = new Stage();
            mainMenu.setTitle("MILSTD-1553b");
            mainMenu.setScene(new Scene(loader.load()));
            mainMenu.setResizable(false);
            NewMenuController controller = loader.getController();
            controller.setAmountOfED(amountOfED);
            controller.setStage(mainMenu);
            mainMenu.show();
            this.stage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

