package view;

import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.PortStatus;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.List;

import static java.awt.Color.*;

//* Created by Danny on 05.05.2016.
//*/
public class ChangeColor {

   /* @FXML
    AnchorPane ED1;
    @FXML
    AnchorPane ED2;
    @FXML
    AnchorPane ED3;
    @FXML
    AnchorPane ED4;
    @FXML
    AnchorPane ED5;
    @FXML
    AnchorPane ED6;
    @FXML
    AnchorPane ED7;
    @FXML
    AnchorPane ED8;
    @FXML
    AnchorPane ED9;
    @FXML
    AnchorPane ED10;
    @FXML
    AnchorPane ED11;
    @FXML
    AnchorPane ED12;
    @FXML
    AnchorPane ED13;
    @FXML
    AnchorPane ED14;
    @FXML
    AnchorPane ED15;
    @FXML
    AnchorPane ED16;
    @FXML
    AnchorPane ED17;
    @FXML
    AnchorPane ED18;
    @FXML
    AnchorPane ED19;
    @FXML
    AnchorPane ED20;
    @FXML
    AnchorPane ED21;
    @FXML
    AnchorPane ED22;
    @FXML
    AnchorPane ED23;
    @FXML
    AnchorPane ED24;
    @FXML
    AnchorPane ED25;
    @FXML
    AnchorPane ED26;
    @FXML
    AnchorPane ED27;
    @FXML
    AnchorPane ED28;
    @FXML
    AnchorPane ED29;
    @FXML
    AnchorPane ED30;
    @FXML
    AnchorPane ED31;
    @FXML
    AnchorPane ED32;

    PortStatus status;
    @FXML

    private static AnchorPane[] ED = {ED1, ED2, ED3, ED4, ED5, ED6, ED7, ED8, ED9, ED10, ED11, ED12, ED13, ED14, ED15, ED16,
            ED17, ED18, ED19, ED20, ED21, ED22, ED23, ED24, ED25, ED26, ED27, ED28, ED29, ED30, ED31, ED32};
*/
    private static List<Pane> ED;
    private static List<Line> lineA;
    private static List<Line> lineB;


    public static void SetColor(int number_ED, int number_Line, PortStatus status) {
        AnchorPane numED = (AnchorPane) ED.get(number_ED - 1);
        List<Node> children = numED.getChildren();
        Rectangle Bigrec = (Rectangle) children.get(0);
        //Bigrec.setStroke(Color.CYAN); пока хуйня
        AnchorPane ED = (AnchorPane) children.get(number_Line);
        List<Node> children1 = ED.getChildren();
        Rectangle rec = (Rectangle) children1.get(0);
        switch (status) {
            case OK:
                break;
            case DENIAL:
                rec.setUserData(orange);
                break;
            case FAILURE:
                rec.setUserData(RED);
                break;
            case BLOCK:
                rec.setUserData(GRAY);
                break;
            case GENERATION:
                rec.setUserData(MAGENTA);
                break;
        }
    }


    public static void SetColor(int lineNumber) {

    }

    public static void SetColor(int lineNumber, int edNumber) {
    }

    public static void setED(List<Pane> ED) {
        ChangeColor.ED = ED;
    }

    public static List<Pane> getED() {
        return ED;
    }

    public static void setLineA(List<Line> lineA) {
        ChangeColor.lineA = lineA;
    }

    public static void setLineB(List<Line> lineB) {
        ChangeColor.lineB = lineB;
    }
}





















