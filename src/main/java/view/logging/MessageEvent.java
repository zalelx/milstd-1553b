package view.logging;

public class MessageEvent implements Log {
    private final int EdNumber;
    private final String message;
    private final int lineNumber;
    private final int time;

    public MessageEvent(int EdNumber, int lineNumber, String suffix, int time) {
        this.message = "Port #" + EdNumber + " line #" + lineNumber + " message" + suffix;
        this.time = time;
        this.lineNumber = lineNumber;
        this.EdNumber = EdNumber;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getTime() {
        return time;
    }

    public int getEdNumber() {
        return EdNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
