package com.qypone.demo.equals;

import java.security.SecureRandom;

public class VerifyCode {

    public static void main(String[] args) {
        System.out.println(String.valueOf(Math.random()).substring(2, 8));
        System.out.println(String.valueOf(new SecureRandom().nextInt()).substring(2, 8));
    }
}
