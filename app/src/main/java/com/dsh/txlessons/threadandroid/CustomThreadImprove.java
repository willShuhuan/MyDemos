package com.dsh.txlessons.threadandroid;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * HandlerThread雏形
 */

public class CustomThreadImprove extends Thread {
  Looper looper = new Looper();

  public Looper getLooper() {
    return looper;
  }

  @Override
  public void run() {
    looper.loop();
  }

  public void quit(){
    looper.quit();
  }

  class Looper {
    private Runnable task;
    private AtomicBoolean quit = new AtomicBoolean(false);

    synchronized void setTask(Runnable task) {
      this.task = task;
    }

    void quit() {
      System.out.println("looper.quit()");
      quit.set(true);
    }

    void loop() {
      System.out.println("looper.loop()");
      while (!quit.get()) {
        synchronized (this) {
          if (task != null) {
            task.run();
            task = null;
          }
        }
      }
    }
  }
}
