package com.academy.lock;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * https://www.youtube.com/watch?v=Jk47yG8bUbA
 * https://classes.engineering.wustl.edu/cse539/web/lectures/locks.pdf
 */
public class CLHLock implements Lock {

    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode, myPred;

    public CLHLock() {
        tail = new AtomicReference<QNode>(new QNode());
        myNode = ThreadLocal.withInitial(QNode::new);
        myPred = ThreadLocal.withInitial(() -> null);
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;

        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        while (pred.locked) {}
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;

        myNode.set(myPred.get());
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {
        return false;
    }



    @NotNull
    @Override
    public Condition newCondition() {
        return null;
    }
}

class QNode {
    public volatile boolean locked = false;
}