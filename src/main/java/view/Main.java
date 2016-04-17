package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main /*extends Application*/{

    public void start(Stage primaryStage) throws Exception {
        /*Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Таблица состояний");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        */
    }

    public static void main(String[] args) {
        //launch(args);
        MetaController metaController = new MetaController();
        metaController.init(4);
    }
}
