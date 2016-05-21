package view.logging;

class MessageEvent implements Log {
    private final int EdNumber;
    private final String message;
    private final int lineNumber;
    private final int time;

    MessageEvent(int EdNumber, int lineNumber, String suffix, int time) {
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

    int getEdNumber() {
        return EdNumber;
    }

    int getLineNumber() {
        return lineNumber;
    }
}
