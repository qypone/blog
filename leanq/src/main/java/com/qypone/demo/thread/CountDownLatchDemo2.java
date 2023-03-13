package com.qypone.demo.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class CountDownLatchDemo2 {

  private static int threadCount = 200;
  public static void main(String[] args) throws InterruptedException {

    ExecutorService executorService = newCachedThreadPool();

    CountDownLatch countDownLatch = new CountDownLatch(threadCount);

    for (int i = 0; i < threadCount; i++) {
      final int threadNum = i;
      executorService.execute(() -> {
        try {
          work(threadNum);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          countDownLatch.countDown();
        }
      });
    }
    countDownLatch.await();
    System.out.println("所有工人都完成任务...");
    executorService.shutdown();
  }

  private static void work(int threadNum) throws InterruptedException {
    Thread.sleep(100);
    System.out.println("工人" + threadNum + "完成任务");
    Thread.sleep(100);
  }
}
