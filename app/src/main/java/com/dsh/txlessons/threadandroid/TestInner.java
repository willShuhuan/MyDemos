package com.dsh.txlessons.threadandroid;

/**
 * @author dongshuhuan
 * date 2020/11/5
 * version
 */
public class TestInner {

    TestInner(){

    }

    public static class Inner {
        public void print() {
            System.out.println("xxx");
        }
    }
}
