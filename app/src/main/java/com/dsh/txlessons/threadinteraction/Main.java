package com.dsh.txlessons.threadinteraction;

import com.dsh.txlessons.threadrxjava.network.Api;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {
  public static void main(String[] args) {
    //runThreadInteractionDemo();
    //runWaitDemo();
    runWaitImproveDemo();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RXJava支持
            .build();

    Api api = retrofit.create(Api.class);


  }



  static void runThreadInteractionDemo() {
    new ThreadInteractionDemo().runTest();
  }

  static void runWaitDemo() {
    new WaitDemo().runTest();
  }

  private static void runWaitImproveDemo() {
    new WaitImproveDemo().runTest();
  }

}
