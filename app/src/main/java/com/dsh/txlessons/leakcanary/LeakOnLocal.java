package com.dsh.txlessons.leakcanary;

import android.os.SystemClock;
import android.util.Log;

/**
 * @author dongshuhuan
 * date 2020/11/21
 * version
 */
class LeakOnLocal extends Thread {

    private String str;

    public LeakOnLocal(String s) {
        this.str = s;
    }

    @Override public void run() {
        super.run();
        String local = str;
        str = null;
        SystemClock.sleep(10000);
        Log.e("LeakOnLocal run: ", local);
    }

    public void leak(){
        start();
    }

}
