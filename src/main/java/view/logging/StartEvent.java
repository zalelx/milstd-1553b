package view.Logging;

/**
 * Created by Зфслфкв Иудд on 21.05.2016.
 */
public class StartEvent implements Log {
    String message = "Start new test";
    int currentTime = 0;
    public StartEvent(int currentTime) {
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getTime() {
        return 0;
    }
}
