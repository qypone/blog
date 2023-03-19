package com.qypone.demo.leetcode;

/**
 * 斐波那契的递归、循环实现
 */
public class Fibonacci {

  public static void main(String[] args) {

    long begin = System.currentTimeMillis();
    int result_1 = fibonacci_1(40);
    long end = System.currentTimeMillis();
    System.out.println(result_1);
    System.out.println("测试1耗时："+ (end - begin));

    long begin2 = System.currentTimeMillis();
    int result_2 = fibonacci_2(40);
    long end2 = System.currentTimeMillis();
    System.out.println(result_2);
    System.out.println("测试2耗时："+ (end2 - begin2));
  }

  public static int fibonacci_2(int n) {
    if(n == 0) {
      return 0;
    }
    if(n == 1) {
      return 1;
    }
    return fibonacci_2(n-1) + fibonacci_2(n-2);
  }

  public static int fibonacci_1(int n) {
    int[] res = {0, 1};
    if(n < 2) {
      return res[n];
    }
    int first = 0;
    int second = 1;
    int fibn = 0;
    for(int i = 2; i <= n; i++) {
      fibn = first + second;
      first = second;
      second = fibn;
    }
    return fibn;
  }

}
