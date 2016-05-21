package view.logging;

class GenerationEvent implements Log {
    private final int lineNumber;
    private final boolean hasGeneration;
    private String message;
    private int time;


    GenerationEvent(int lineNumber, boolean hasGeneration, int numberOfGenerator, int currentTime) {
        this.time = currentTime;
        this.lineNumber = lineNumber;
        this.hasGeneration = hasGeneration;
        this.message = "Generation " + (hasGeneration ? "appeared" : "disappeared") + " at line #" + lineNumber + " ED# " + numberOfGenerator;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getTime() {
        return time;
    }

    int getLineNumber() {
        return lineNumber;
    }

    boolean isHasGeneration() {
        return hasGeneration;
    }
}
