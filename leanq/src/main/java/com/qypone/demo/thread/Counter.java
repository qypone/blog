package com.qypone.demo.thread;

public class Counter {
  private int sum = 0;

  // 1.结果不相等
//  public void incr() {
//    sum = sum + 1;
//  }

  // 2.解决方案一
  private Object lock = new Object();

  public void incr() {
    synchronized (lock) {
      sum = sum + 1;
    }
  }

//3.解决方案二
//  public synchronized void incr() {
//      sum = sum + 1;
//  }

  public int getSum() {
    return sum;
  }

  public static void main(String[] args) throws InterruptedException {
    int loop = 10_0000;

    // test single thread
    Counter counter = new Counter();
    for (int i = 0; i < loop; i++) {
      counter.incr();
    }

    System.out.println("single thread: " + counter.getSum());

    // test multiple threads
    final Counter counter2 = new Counter();
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < loop / 2; i++) {
        counter2.incr();
      }
    });
    Thread t2 = new Thread(() -> {
      for (int i = 0; i < loop / 2; i++) {
        counter2.incr();
      }
    });
    t1.start();
    t2.start();
    Thread.sleep(1000);

    System.out.println("multiple threads: " + counter2.getSum());

  }
}
