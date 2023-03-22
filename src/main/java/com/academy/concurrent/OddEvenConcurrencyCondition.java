package com.academy.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

enum StateType {
    ODD, EVEN
}

public class OddEvenConcurrencyCondition {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition oddCondition = lock.newCondition();
        Condition evenCondition = lock.newCondition();
        int[] state = new int[1];
        state[0] = 0;
        new Thread(new NumberPrinter(state, 2, lock, oddCondition, evenCondition, StateType.ODD), "oddThread").start();
        new Thread(new NumberPrinter(state, 2, lock, oddCondition, evenCondition, StateType.EVEN), "evenThread").start();
    }

}

class NumberPrinter implements Runnable {
    int[] state;
    int step;
    int odd;
    StateType type;
    ReentrantLock lock;
    Condition evenCondition, oddCondition;

    public NumberPrinter(int[] state, int step, ReentrantLock lock, Condition evenCondition, Condition oddCondition, StateType type) {
        this.state = state;
        this.step = step;
        this.lock = lock;
        this.type = type;
        this.evenCondition = evenCondition;
        this.oddCondition = oddCondition;
    }

    @Override
    public void run() {
        while(true) {
            try {
                if (type == StateType.ODD) {
                    while (state[0] > 0 && state[0] % 2 == 1)
                        oddCondition.wait();
                } else {
                    while (state[0] > 0 && state[0] % 2 == 0)
                        evenCondition.wait();
                }
                lock.lock();


                state[0] += 1;
                System.out.println(state[0]);

                if (type == StateType.ODD) {
                    evenCondition.signal();
                } else {
                    oddCondition.signal();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

}