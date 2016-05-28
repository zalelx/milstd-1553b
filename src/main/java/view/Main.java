package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/SetVal.fxml"));
        try {
            Stage mainMenu = new Stage();
            mainMenu.setTitle("Начало работы");
            mainMenu.setScene(new Scene(loader.load()));
            mainMenu.setResizable(false);
            SetValueController controller = loader.getController();
            controller.stage = mainMenu;
            mainMenu.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
