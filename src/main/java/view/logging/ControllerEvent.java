package view.logging;

class ControllerEvent implements Log {
    private final int time;
    private final String message;

    public ControllerEvent(String message, int time) {
        this.message = message;
        this.time = time;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getTime() {
        return time;
    }
}
