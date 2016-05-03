package view;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class NewMenuController {
    private int amountOfED;

    @FXML
    Pane mainPane;

    MetaController metaController;
    List<Pane> EDPanes = new ArrayList<>();
    List<Line> LineA = new ArrayList<>();
    List<Line> LineB = new ArrayList<>();
    private Stage stage;

    @FXML
    public void SetStatus() {
    }

    @FXML
    public void setAmountOfED(int amountOfED) {
        this.amountOfED = amountOfED;
        metaController = new MetaController();
        metaController.init(amountOfED);
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
                    if (LineA.size() < amountOfED)
                        LineA.add((Line) node);
                    else
                        node.setVisible(false);
                    isA = false;
                } else {
                    if (LineB.size() < amountOfED)
                        LineB.add((Line) node);
                    else
                        node.setVisible(false);
                    isA = false;
                }
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}