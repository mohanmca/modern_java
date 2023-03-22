package com.academy.concurrent;

public class State {
    private PrinterType nextToPrint;

    public State(final PrinterType nextToPrint) {
        this.nextToPrint = nextToPrint;
    }

    public PrinterType getNextToPrint() {
        return nextToPrint;
    }

    public void setNextToPrint(final PrinterType nextToPrint) {
        this.nextToPrint = nextToPrint;
    }

}
