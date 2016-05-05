package view;

import com.sun.javafx.binding.StringFormatter;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

import java.util.Formatter;

import static java.lang.Thread.sleep;

public class TimeLogger {
    private static int currentTime = 0;
    private static TextArea textArea1;
    private static TextArea textArea2;

    public static void log(String string, int time){
        delay(time);
        log(string);
    }

    public static void log(String string) {
        Formatter formatter1 = new Formatter();
        Formatter formatter2 = new Formatter();
        formatter1.format("%s\n", string);
        textArea1.insertText(textArea1.getText().length(), formatter1.toString());
        formatter2.format("%s %d\n", "Time:", currentTime);
        textArea2.insertText(textArea2.getText().length(), formatter2.toString());
        System.out.print(formatter1.toString());
        System.out.print(formatter2.toString());
    }

    public static void delay(int delay){

        currentTime += delay;
    }

    public void setTextArea(TextArea textArea1,TextArea textArea2){
        TimeLogger.textArea1 = textArea1;
        TimeLogger.textArea2 = textArea2;

    }
}
