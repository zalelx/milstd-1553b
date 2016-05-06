package view;

import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.PortStatus;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.List;

import javafx.scene.paint.Color;

//* Created by Danny on 05.05.2016.
//*/
public class ChangeColor {


    private static List<Pane> ED;
    private static List<Line> lineA;
    private static List<Line> lineB;


    static int prevLine;

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
        if (prevLine == 1) {
            for (Line line : lineA) {
                line.setStroke(Color.BLACK);
            }
        } else {
            for (Line line : lineB) {
                line.setStroke(Color.BLACK);
            }
        }

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





















