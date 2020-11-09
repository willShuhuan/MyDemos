package com.dsh.txlessons.threadandroid;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThread extends Thread {
  private Runnable task;
  private AtomicBoolean quit = new AtomicBoolean(false);

  synchronized void setTask(Runnable task) {
    this.task = task;
  }

  void quit(){
    quit.set(true);
  }

  @Override
  public void run() {
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
