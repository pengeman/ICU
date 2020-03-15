/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peng.icu.pmq;


import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PQueue {

    Logger log = Logger.getLogger(PQueue.class);

    //创建一个装队列的容器
    private LinkedList<Object> list = new LinkedList();
    //创建一个计数器
    private AtomicInteger count = new AtomicInteger(0);
    //创建容器的最大值和最小值
    private final int minSize = 0;

    private final int maxSize;

    private PQueue() {
        maxSize = 0;
    }

    public PQueue(int size) {
        this.maxSize = size;
    }

    private final Object lock = new Object();

    public void put(Object obj) {
        log.debug("put 消息进入队列中" + obj);
        synchronized (lock) {
            if (count.get() == this.maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //把对象加入容器
            list.add(obj);
            //计算容器的数值
            count.incrementAndGet();
            //唤醒等待的线程
            lock.notify();
//            System.out.println("新加入的元素为:" + obj);
        }
    }

    public Object take(long timeout) {
        Object ret = null;
        synchronized (lock) {
            if (count.get() == this.minSize) {
                try {
                    lock.wait(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (list.size() == 0) {
                ret = null;
            } else {
                ret = list.removeFirst();
                count.decrementAndGet();
                lock.notify();
            }

        }

        return ret;
    }

    public Object take() {
        Object ret = null;
        synchronized (lock) {
            int i = count.get();
            int j = this.minSize;
//            System.out.println("i=" + i + "  j=" + j);
            if (i == j) {
                try {
                    lock.wait();
                    i = count.get();
                    j = this.minSize;
//            System.out.println("2: i=" + i + "  j=" + j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ret = list.removeFirst();
            count.decrementAndGet();
            lock.notify();
        }

        return ret;
    }

    public int Size() {
        return this.count.get();
    }

    public static void main(String[] args) {
        final PQueue mq = new PQueue(5);
        mq.put(1);
        mq.put(2);
        mq.put(3);
        mq.put(4);
        mq.put(5);

        System.out.println("当前容器的长度:" + mq.Size());

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                mq.put(6);
                mq.put(7);
            }
        }, "t1");

        t1.start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Object o1 = mq.take();
                System.out.println("移除的元素为:" + o1);
                Object o2 = mq.take();
                System.out.println("移除的元素为:" + o2);
            }
        }, "t2");
        t2.start();

    }
}
