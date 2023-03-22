package com.academy.concurrent;

public class Printer implements Runnable {

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
        while (currentValue <= maxValue) {
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

