package view;

import static java.lang.Thread.sleep;

public class TimeLogger {
    private static int currentTime = 0;

    public static void log(String string, int time){
        delay(time);

        log(string);
    }

    public static void log(String string) {

        System.out.printf("%-30s %s %d\n", string, "Time:", currentTime);
    }

    public static void delay(int delay){
        try {
            sleep(50 * delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentTime += delay;
    }
}
