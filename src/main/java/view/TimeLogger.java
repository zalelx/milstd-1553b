package view;

import com.sun.javafx.binding.StringFormatter;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

import java.util.Formatter;

import static java.lang.Thread.sleep;

public class TimeLogger {
    private static int currentTime = 0;
    private static TextArea textArea;

    public static void log(String string, int time){
        delay(time);
        log(string);
    }

    public static void log(String string) {
        Formatter formatter = new Formatter();
        formatter.format("%-30s %s %d\n", string, "Time:", currentTime);
        textArea.insertText(textArea.getText().length(), formatter.toString());
        System.out.print(formatter.toString());
    }

    public static void delay(int delay){

        currentTime += delay;
    }

    public void setTextArea(TextArea textArea){
        TimeLogger.textArea = textArea;
        log("Start...");
    }
}
