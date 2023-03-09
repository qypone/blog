package com.qypone.demo.lambda;

public class LambdaDemo {

  public static void main(String[] args) {
    MathOperator add = (int a, int b) -> a + b;
  }

  interface MathOperator {
    int operate(int a, int b);
  }

  private int operate(int a, int b, MathOperator mathOperator) {
    return mathOperator.operate(a, b);
  }
}
