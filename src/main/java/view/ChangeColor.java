package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.PortStatus;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.List;

//* Created by Danny on 05.05.2016.
//*/
public class ChangeColor {

    private static List<Pane> ED;
    private static List<Line> lineA;
    private static List<Line> lineB;

    static int prevLine2 = 1;
    static int prevEdNumber = 1;


    public static void SetColor(int number_ED, int number_Line, PortStatus status) {
        AnchorPane numED = (AnchorPane) ED.get(number_ED - 1);
        List<Node> children = numED.getChildren();
        AnchorPane ED = (AnchorPane) children.get(number_Line);

        List<Node> children1 = ED.getChildren();
        Rectangle rec = (Rectangle) children1.get(0);

        switch (status) {
            case OK:
                rec.setFill(Color.LIGHTGREEN);
                break;
            case DENIAL:
                rec.setFill(Color.ORANGE);
                break;
            case FAILURE:
                rec.setFill(Color.RED);
                break;
            case BLOCK:
                rec.setFill(Color.GRAY);
                break;
            case GENERATION:
                rec.setFill(Color.MAGENTA);
                break;
        }
    }


    public static void SetColor(int lineNumber) {

    }

    public static void SetColor(int lineNumber, int edNumber) {

        if (edNumber != 0) {
            AnchorPane numEDbc = (AnchorPane) ED.get(prevEdNumber - 1);
            List<Node> children = numEDbc.getChildren();
            AnchorPane ED = (AnchorPane) children.get(prevLine2);
            List<Node> children1 = ED.getChildren();
            Rectangle rec = (Rectangle) children1.get(0);
            rec.setStroke(Color.BLACK);
            rec.setStrokeWidth(1);

            prevLine2 = lineNumber;
            prevEdNumber = edNumber;

            AnchorPane numED = (AnchorPane) ChangeColor.ED.get(edNumber - 1);
            children = numED.getChildren();
            ED = (AnchorPane) children.get(lineNumber);
            children1 = ED.getChildren();
            rec = (Rectangle) children1.get(0);
            rec.setStroke(Color.BLACK);
            rec.setStrokeWidth(3);
        }
    }


    public static void setED(List<Pane> ED) {
        ChangeColor.ED = ED;
    }

    public static void setLineA(List<Line> lineA) {
        ChangeColor.lineA = lineA;
    }

    public static void setLineB(List<Line> lineB) {
        ChangeColor.lineB = lineB;
    }
}





















