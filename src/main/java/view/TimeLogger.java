package view;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import javax.swing.text.TabExpander;
import java.util.*;

import static java.lang.Thread.sleep;

public class TimeLogger {
    private static int currentTime = 0;
    private static TextArea textArea;
    private static Queue<String> logs = new LinkedList<>();

    public static void log(String string, int time) {
        delay(time);
        log(string);
    }

    private static void log(String string) {
        Formatter formatter = new Formatter();
        formatter.format("%-30s %s %d\n", string, "Time:", currentTime);
        logs.offer(formatter.toString());
    }

    public static void delay(int delay) {
        currentTime += delay;
    }

    void setTextArea(TextArea textArea) {
        TimeLogger.textArea = textArea;
        log("Start...");
    }

    void showLogs() {
        Thread t = new Thread(() -> {
            for (String s : logs) {
                Platform.runLater(() -> textArea.insertText(textArea.getText().length(), s));
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            logs = new LinkedList<>();
        });
        t.start();
    }
}
