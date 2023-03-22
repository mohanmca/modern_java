package com.academy.concurrent;

public class OddEvenPrinterSynchronized {
    public static void main(String[] args) {
        State s = new State(PrinterType.ODD);
        Printer oddPrinter = new Printer(1, 2, s, PrinterType.ODD, PrinterType.EVEN, 100);
        Printer evenPrinter = new Printer(2, 2, s, PrinterType.EVEN, PrinterType.ODD, 100);
        new Thread(oddPrinter).start();
        new Thread(evenPrinter).start();
    }
}
enum PrinterType {
    ODD, EVEN
}

class State {
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


class Printer implements Runnable {

    private final int step;
    private Integer currentValue;
    private final State state;
    private final PrinterType currentPrinterType;
    private final PrinterType nextPrinterType;
    private final Integer maxValue;

    public Printer(final Integer startValue, final int step, final State state, final PrinterType currentPrinterType, final PrinterType nextPrinterType, final Integer maxValue) {

        this.currentValue = startValue;
        this.step = step;
        this.state = state;
        this.currentPrinterType = currentPrinterType;
        this.nextPrinterType = nextPrinterType;
        this.maxValue = maxValue;
    }



    public void run() {
        while (currentValue < maxValue-1) {
            synchronized (state) {
                while (this.currentPrinterType != state.getNextToPrint()) {
                    try {
                        state.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                currentValue += step;
                System.out.println(currentPrinterType.toString() + ":" + currentValue);
                state.setNextToPrint(this.nextPrinterType);
                state.notifyAll();
            }

        }
    }

}

