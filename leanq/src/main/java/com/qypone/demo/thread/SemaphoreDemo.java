package com.qypone.demo.thread;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

  public static void main(String[] args) {
    // 8个工人
    int n = 8;
    // 5台机器
    Semaphore semaphore = new Semaphore(3);
    for (int i = 0; i < n; i++) {
      new Woker(i, semaphore).start();
    }
  }

  static class Woker extends Thread {

    private int num;
    private Semaphore semaphore;

    Woker(int num, Semaphore semaphore) {
      this.num = num;
      this.semaphore = semaphore;
    }

    @Override
    public void run() {
      try {
        semaphore.acquireUninterruptibly();
        System.out.println("工人"+ num + "占用一个机器，正在工作...");
        Thread.sleep(2000);
        System.out.println("工人"+ num + "工作完毕，释放机器。");
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        semaphore.release();
      }
    }
  }
}
