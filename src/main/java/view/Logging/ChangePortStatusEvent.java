package view.Logging;

import model.PortStatus;

class ChangePortStatusEvent implements Log {
    private final int time;
    private final String message;
    private final PortStatus portStatus;
    private final int EdNumber;
    private final int lineNumber;

    public ChangePortStatusEvent(int EdNumber, int lineNumber, PortStatus status, int currentTime) {
        this.message = "Port #" + EdNumber + " line #" + lineNumber + " status changed " + status.name();
        this.time = currentTime;
        this.EdNumber = EdNumber;
        this.lineNumber = lineNumber;
        this.portStatus = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getTime() {
        return time;
    }

    public PortStatus getPortStatus() {
        return portStatus;
    }

    public int getEdNumber() {
        return EdNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
