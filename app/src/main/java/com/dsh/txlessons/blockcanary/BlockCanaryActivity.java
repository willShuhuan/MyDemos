package com.dsh.txlessons.blockcanary;

import android.os.Debug;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.dsh.mydemos.R;

public class BlockCanaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Debug.startMethodTracing("sample");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_canary);

        Debug.stopMethodTracing();
    }

    public void evilMethod(View view) {
        //SystemClock.sleep(1000);
        a();
        b();
        c();
    }

    public void a() {
        SystemClock.sleep(780);
    }

    public void b() {
        SystemClock.sleep(21);
    }

    public void c() {
        SystemClock.sleep(200);
    }

}