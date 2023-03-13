package com.qypone.demo.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {

  public static void main(String[] args) {
    FutureTask futureTask = new FutureTask(new Callable() {
      @Override
      public Integer call() throws Exception {
        return new Random().nextInt(10);
      }
    });

    new Thread(futureTask).start();

    try {
      Integer result = (Integer) futureTask.get();
      System.out.println(result);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }
}
