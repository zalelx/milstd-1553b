package view.logging;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import model.PortStatus;
import view.ChangeColor;

import java.util.Formatter;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class TimeLogger {
    private static final long DELAY = 500;
    private static int currentTime = 0;
    private static TextArea textArea;
    private static Queue<Log> logs = new LinkedList<>();

    public static void log(String string, int time) {
        delay(time);
        logs.offer(new ControllerEvent(string, currentTime));
    }

    public static void delay(int delay) {
        currentTime += delay;
    }

    public static void logBroadcast(int lineNumber, int time){
        delay(time);
        logs.offer(new BroadcastEvent(lineNumber, currentTime));
    }

    public static void logHandleMessage(int value, int lineNumber) {
        logs.offer(new MessageEvent(value, lineNumber, " handle.", currentTime));
    }

    public static void logSendMessage(int value, int lineNumber) {
        logs.offer(new MessageEvent(value, lineNumber, " send.", currentTime));
    }

    public static void logChangePortStatus(int EdNumber, int lineNumber, PortStatus status) {
        logs.offer(new ChangePortStatusEvent(EdNumber, lineNumber, status, currentTime));
    }

    public static void setTextArea(TextArea textArea) {
        TimeLogger.textArea = textArea;
    }

    public static void showLogs() {
        Thread t = new Thread(() -> {
            for (Log log : logs) {
                Platform.runLater(() -> {
                    Formatter formatter = new Formatter();
                    formatter.format("%-30s %s %d\n", log.getMessage(), "Time:", log.getTime());
                    textArea.insertText(textArea.getText().length(), formatter.toString());

                    if (log instanceof BroadcastEvent){
                        ChangeColor.SetColor(((BroadcastEvent) log).getLineNumber());
                    }
                    if (log instanceof MessageEvent){
                        ChangeColor.SetColor(
                                ((MessageEvent) log).getLineNumber(),
                                ((MessageEvent) log).getEdNumber());
                    }
                    if (log instanceof ChangePortStatusEvent){
                        ChangeColor.SetColor(
                                ((ChangePortStatusEvent) log).getEdNumber(),
                                ((ChangePortStatusEvent) log).getLineNumber(),
                                ((ChangePortStatusEvent) log).getPortStatus());
                    }
                    if (log instanceof StartEvent) {
                        ChangeColor.decolor();
                    }

                    if (log instanceof GenerationEvent){
                        ChangeColor.SetColorGeneration(
                                ((GenerationEvent) log).getLineNumber(),
                                ((GenerationEvent) log).isHasGeneration()
                        );
                    }
                });
                try {
                    sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            logs.clear();
        });
        t.start();
    }

    public static void logGeneration(int lineNumber, boolean hasGeneration) {
        logs.offer(new GenerationEvent(lineNumber, hasGeneration, currentTime));
    }

    public static void logStart(int amountOfEd) {
        currentTime = 0;
        logs.offer(new StartEvent(currentTime));
        for (int i = 1; i <= amountOfEd; i++) {
            logChangePortStatus(i, 1, PortStatus.OK);
        }
    }
}
