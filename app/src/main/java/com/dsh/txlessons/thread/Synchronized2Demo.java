package com.dsh.txlessons.thread;

public class Synchronized2Demo implements TestDemo {

  private int x = 0;
  //private volatile int x = 0;

  private void count() {
    x++;
    //x++等价于如下两步操作，这不是一个原子操作，所以用volatile修饰x并不管用
    //int temp = x+1;
    //x = temp;
  }

  //synchronized关键字
  //private synchronized void count() {
  //  x++;
  //}

  @Override
  public void runTest() {
    new Thread() {
      @Override
      public void run() {
        for (int i = 0; i < 1_000_000; i++) {
          count();
        }
        System.out.println("final x from 1: " + x);
      }
    }.start();
    new Thread() {
      @Override
      public void run() {
        for (int i = 0; i < 1_000_000; i++) {
          count();
        }
        System.out.println("final x from 2: " + x);
      }
    }.start();
  }
}
