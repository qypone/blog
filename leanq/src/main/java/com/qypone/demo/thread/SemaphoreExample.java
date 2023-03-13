package com.qypone.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreExample {
  // 请求的数量
  private static final int threadCount = 550;

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    Semaphore semaphore = new Semaphore(20);
    IntStream.rangeClosed(0, threadCount)
       .boxed()
       .forEach(index -> {
         executorService.execute(() -> {
           try {
             semaphore.acquire();
             test(index);
             semaphore.release();
           } catch (InterruptedException e) {
             e.printStackTrace();
           }
         });
       });
  }

  public static void test(Integer num) {
    try {
      Thread.sleep(1000);
      System.out.println(Thread.currentThread().getName() + ": " + (num * num));
      for (int i = 0; i < 100000; i++) {
        int a =0;
        a++;
      }
      System.out.println("----");
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
