package com.dsh.txlessons.threadinteraction;

public class WaitImproveDemo implements TestDemo {
  private String sharedString;

  private synchronized void initString() {
    sharedString = "rengwuxian";
    notifyAll();
  }

  private synchronized void printString() {
    while (sharedString == null) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }
    System.out.println("String: " + sharedString);
  }

  @Override
  public void runTest() {
    final Thread thread1 = new Thread() {
      @Override
      public void run() {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        printString();
      }
    };
    thread1.start();
    Thread thread2 = new Thread() {
      @Override
      public void run() {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        //yield();//暂时让出自己的时间片给同优先级的线程
        initString();
      }
    };
    thread2.start();

    //try {
    //  thread1.join();
    //} catch (InterruptedException e) {
    //  e.printStackTrace();
    //}
    //System.out.println("xxx");
  }
}