package com.dsh.txlessons.threadandroid;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.dsh.mydemos.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Looper.myLooper();
        Looper.getMainLooper();

        HandlerThread handlerThread = new HandlerThread("Name");
        //handler创建方式1
        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //自定义callBack处理消息
                return false;
            }
        });
        //handler创建方式2
        Handler handler2 = new Handler(handlerThread.getLooper());

        MyAsyncTask task = new MyAsyncTask();
        task.execute("Hello AsyncTask");

    }

    class MyAsyncTask extends AsyncTask{

        @Override protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override protected Object doInBackground(Object[] objects) {
            System.out.println(objects[0]);
            return null;
        }

        @Override protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
}