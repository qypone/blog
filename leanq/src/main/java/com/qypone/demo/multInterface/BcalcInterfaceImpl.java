package com.qypone.demo.multInterface;

import org.springframework.stereotype.Component;

@Component
public class BcalcInterfaceImpl implements CalcInterface {
    @Override
    public void test() {
        System.out.println("BcalcInterfaceImpl");
    }
}
