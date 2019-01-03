package com.dsh.mydemos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.dsh.mydemos.R;
import com.dsh.mydemos.base.BaseActivity;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dongshuhuan
 * createDate 2018/11/27
 * Email 785434788@qq.com
 * Description
 * Update Log
 */

public class OKHTTPActivity extends BaseActivity {

    private static final String TAG = "OKHTTPActivity";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override public int getContentView() {
        return R.layout.activity_okhttp;
    }

    @Override public void initView() {

    }

    @Override public void setListener() {

    }

    @Override public void initData() {

        syncRequest();

    }

    private void syncRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            Log.d(TAG, "syncRequest: "+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void onClick(View arg0) {

    }


}
