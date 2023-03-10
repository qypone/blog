package com.qypone.demo.multInterface;

import org.springframework.stereotype.Component;

@Component
public class AcalcInterfaceImpl implements CalcInterface {
    @Override
    public void test() {
        System.out.println("AcalcInterfaceImpl");
    }
}
