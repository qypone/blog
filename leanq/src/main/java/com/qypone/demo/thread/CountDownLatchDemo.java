package com.qypone.demo.thread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

  public static void main(String[] args) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(5);
    for (int i = 0; i < 5; i++) {
      new Thread(new ReadNum(countDownLatch, i)).start();
    }
    countDownLatch.await();
    System.out.println("各子线程结束...");
    System.out.println("主线程结束...");
  }

  static class ReadNum implements Runnable {

    private CountDownLatch countDownLatch;
    private int num;

    ReadNum(CountDownLatch countDownLatch, int i) {
      this.countDownLatch = countDownLatch;
      this.num = i;
    }

    @Override
    public void run() {
      synchronized (this) {
        System.out.println("当前为:" + num);
        System.out.println("当前线程为" + Thread.currentThread().getName());
        countDownLatch.countDown();
      }
    }
  }
}
