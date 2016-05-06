package view;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Logging.TimeLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewMenuController {
    private int amountOfED;

    @FXML
    Pane mainPane;
    @FXML
    TextArea textArea;
    @FXML
    AnchorPane textPane;

    private MetaController metaController;
    private List<Pane> EDPanes = new ArrayList<>();
    private List<Line> LineA = new ArrayList<>();
    private List<Line> LineB = new ArrayList<>();
    private TimeLogger timeLogger = new TimeLogger();
    private Stage stage;

    @FXML
    public void setStatus() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/SetStatus.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setResizable(false);
        stage.initOwner(this.stage);
        stage.initModality(Modality.WINDOW_MODAL);

        SetStatusController controller = loader.getController();
        controller.setMetaController(metaController);
        stage.showAndWait();
    }

    @FXML
    public void testMKO() {
        metaController.testMKO();
        TimeLogger.showLogs();
    }

    @FXML
    public void setAmountOfED(int amountOfED) {
        this.amountOfED = amountOfED;
        metaController = new MetaController();
        metaController.init(amountOfED);
        textArea.setEditable(false);
        TimeLogger.setTextArea(textArea);

        List<Node> children = mainPane.getChildren();
        boolean isA = true;
        for (Node node : children) {
            if (node instanceof AnchorPane) {
                if (EDPanes.size() < amountOfED)
                    EDPanes.add((Pane) node);
                else
                    node.setVisible(false);
            }
            if (node instanceof Line) {
                if (isA) {
                    LineA.add((Line) node);
                    isA = false;
                } else {
                    LineB.add((Line) node);
                    isA = true;
                }
            }
        }

        for (int i = 0; i < LineA.size(); i++) {
            Line line = LineA.get(i);

            if (i + 1 > amountOfED){
                line.setVisible(false);
            }
            if (line.getId() == null){
                line.setVisible(true);
            }
        }
        for (int i = 0; i < LineB.size(); i++) {
            Line line = LineB.get(i);

            if (i + 1 > amountOfED){
                line.setVisible(false);
            }
            if (line.getId() == null){
                line.setVisible(true);
            }
        }
        ChangeColor.setED(EDPanes);
        ChangeColor.setLineA(LineA);
        ChangeColor.setLineB(LineB);
        textArea.setVisible(true);
        textPane.setVisible(true);
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }
}