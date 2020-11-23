package com.dsh.txlessons.leakcanary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.dsh.mydemos.R;


public class LeakCanaryActivity extends AppCompatActivity {

    private static final String TAG = "LeakCanaryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);
        Button leak = findViewById(R.id.btnLeak);


        //LeakOnLocal leakOnLocal = new LeakOnLocal("LeakCanary开源解码");

        leak.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                new LeakOnLocal("LeakCanary开源解码").leak();
                finish();
            }
        });

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.e(TAG, "finalize: ");
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        gc();
    }

    private void gc(){
        try {
            Thread.sleep(2000);
            Runtime.getRuntime().gc();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}