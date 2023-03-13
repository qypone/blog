package com.qypone.demo.thread;

public class DaemonThreadDemo {

  public static void main(String[] args) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Thread thread = Thread.currentThread();
        System.out.println("当前线程" + thread.getName());
      }
    };
    Thread thread = new Thread(task);
    thread.setName("test-thread-1");
    // 如果是daemon线程将不会打印东西到控制台
    thread.setDaemon(true);
    thread.start();
  }
}
