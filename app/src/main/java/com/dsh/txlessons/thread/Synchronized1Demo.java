package com.dsh.txlessons.thread;

import java.util.concurrent.atomic.AtomicBoolean;

public class Synchronized1Demo implements TestDemo {
  //private AtomicBoolean running = new AtomicBoolean(true);
  private volatile Boolean running = true;

  private void stop() {
    running = false;
    //running.set(false);
  }

  @Override
  public void runTest() {
    new Thread() {
      @Override
      public void run() {
        //while (running.get()) {
        while (running) {
        }
      }
    }.start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    stop();
  }
}
