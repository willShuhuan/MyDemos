package com.dsh.txlessons.threadandroid;

import android.os.AsyncTask;

/**
 * @author dongshuhuan
 * date 2020/11/4
 * version
 */
class Main {
    public static void main(String[] args) {
        //CustomThread thread = new CustomThread();
        //thread.start();
        //try {
        //    Thread.sleep(3000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        //thread.setTask(new Runnable() {
        //    @Override public void run() {
        //        System.out.println("hahahaha");
        //    }
        //});
        //thread.quit();

        CustomThreadImprove thread = new CustomThreadImprove();

        thread.getLooper().setTask(new Runnable() {
            @Override public void run() {
                System.out.println("run baby");
            }
        });

        thread.start();
        try {
            Thread.sleep(1000);
            System.out.println("one seconds later");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.quit();

    }



}
