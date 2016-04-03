package view;

public class TimeLogger {
    private static int currentTime = 0;

    public static void log(String string, int time){
        currentTime += time;
        System.out.println(string + "   Time: " + currentTime);
    }

    public static void log(String string) {
        System.out.println(string + "   Time: " + currentTime);
    }
}
