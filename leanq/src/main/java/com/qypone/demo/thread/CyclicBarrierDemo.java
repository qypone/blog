package com.qypone.demo.thread;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

  public static void main(String[] args) {

    CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
      @Override
      public void run() {
        System.out.println("回调线程：" + Thread.currentThread().getName());
      }
    });

    for (int i = 0; i < 5; i++) {
      new Thread(new ReadNum(i, cyclicBarrier)).start();
    }
    System.out.println("主线程结束.");
  }

  static class ReadNum implements Runnable {

    private int id;
    private CyclicBarrier cyclicBarrier;

    public ReadNum(int i, CyclicBarrier cyclicBarrier) {
      this.cyclicBarrier = cyclicBarrier;
      this.id = i;
    }

    @Override
    public void run() {
      System.out.println("当前id为:" + id + "，" + Thread.currentThread().getName());
      synchronized (this) {

        try {
          System.out.println("线程任务id：" + id + "结束.");
          cyclicBarrier.await();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    }
  }
}
