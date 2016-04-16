package view;

public class TimeLogger {
    private static int currentTime = 0;

    public static void log(String string, int time){
        currentTime += time;
        log(string);
    }

    public static void log(String string) {
        System.out.printf("%-30s %s %d\n", string, "Time:", currentTime);
    }

    public static void delay(int delay){
        currentTime += delay;
    }
}
