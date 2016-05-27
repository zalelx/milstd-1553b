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
    private static int prevLine;
    private static int prevLine2 = 1;
    private static int prevEdNumber = 1;
    private static Color defaultColorLine1 = Color.BLACK;
    private static Color defaultColorLine2 = Color.BLACK;


    public static void SetColor(int number_ED, int number_Line, PortStatus status) {
        AnchorPane numED = (AnchorPane) ED.get(number_ED);
        List<Node> children = numED.getChildren();
        AnchorPane ED = (AnchorPane) children.get(number_Line);

        List<Node> children1 = ED.getChildren();
        Rectangle rec = (Rectangle) children1.get(0);

        switch (status) {
            case OK:
                rec.setFill(Color.LIGHTGREEN);
                break;
            case DENIAL:
                rec.setFill(Color.RED);
                break;
            case FAILURE:
                rec.setFill(Color.ORANGE);
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
        decolor();
        prevLine = lineNumber;
        if (lineNumber == 1) {
            for (Line line : lineA) {
                line.setStroke(Color.MAROON);
            }
        } else {
            for (Line line : lineB) {
                line.setStroke(Color.MAROON);
            }
        }
    }

    public static void SetColor(int lineNumber, int edNumber) {
            decolor();
            prevLine2 = lineNumber;
            prevEdNumber = edNumber;

            AnchorPane numED = (AnchorPane) ChangeColor.ED.get(edNumber);
            List<Node> children = numED.getChildren();
            AnchorPane ED = (AnchorPane) children.get(lineNumber);
            List<Node> children1 = ED.getChildren();
            Rectangle rec = (Rectangle) children1.get(0);
            rec.setStroke(Color.BLUEVIOLET);
            rec.setStrokeWidth(3);

    }

    public static void decolor() {
        AnchorPane numEDbc = (AnchorPane) ED.get(prevEdNumber);
        List<Node> children = numEDbc.getChildren();
        AnchorPane ED = (AnchorPane) children.get(prevLine2);
        List<Node> children1 = ED.getChildren();
        Rectangle rec = (Rectangle) children1.get(0);
        rec.setStroke(Color.BLACK);
        rec.setStrokeWidth(1);

        if (prevLine == 1) {
            for (Line line : lineA) {
                line.setStroke(defaultColorLine1);
            }
        } else {
            for (Line line : lineB) {
                line.setStroke(defaultColorLine2);
            }
        }


    }

    static void setED(List<Pane> ED) {
        ChangeColor.ED = ED;
    }

    static void setLineA(List<Line> lineA) {
        ChangeColor.lineA = lineA;
    }

    static void setLineB(List<Line> lineB) {
        ChangeColor.lineB = lineB;
    }

    public static void SetColorGeneration(int lineNumber, boolean hasGeneration) {
        if (!hasGeneration) {
            if (lineNumber == 1){
                defaultColorLine1 = Color.BLACK;
            } else {
                defaultColorLine2 = Color.BLACK;
            }
        } else {
            if (lineNumber == 1){
                defaultColorLine1 = Color.PURPLE;
            } else {
                defaultColorLine2 = Color.PURPLE;
            }
        }
        prevLine = lineNumber;
        decolor();
    }

}