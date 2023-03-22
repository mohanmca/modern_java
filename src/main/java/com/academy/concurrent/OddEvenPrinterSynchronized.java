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
