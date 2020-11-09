package com.dsh.txlessons.threadrxjava;

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.dsh.mydemos.R
import com.dsh.txlessons.threadrxjava.network.Api
import com.dsh.threadrxjava.model.Repo
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

class MainActivity : AppCompatActivity() {
  private var disposable: Disposable? = null
  private lateinit var textView:TextView;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_rxjava)

    textView = findViewById(R.id.textView);

    //rxjava网络请求示例
    rxJavaNetRequest();

    //Single使用
    single();

    //操作符
    map();
    interval();
    delay();


    Single.just(1)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())



  }

  private fun delay() {
    Single.just("1").delay(1,SECONDS);
  }

  private fun interval() {
    Observable.interval(0, 1, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<Long?> {
          override fun onComplete() {
          }

          override fun onSubscribe(d: Disposable) {
          }

          override fun onNext(t: Long) {
            textView.text = t.toString()
          }

          override fun onError(e: Throwable) {
          }
        })
  }

  private fun map() {
    var singleInt:Single<Int> = Single.just(1)
    val singleMap:Single<String> = singleInt.map(object :Function<Int,String>{
      override fun apply(t: Int): String {
        return t.toString();
      }
    })
    singleMap.subscribe(object :SingleObserver<String>{
      override fun onSuccess(t: String) {
        textView.text = t;
      }
      override fun onSubscribe(d: Disposable) {}
      override fun onError(e: Throwable) {}
    })
  }

  private fun rxJavaNetRequest() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RXJava支持
        .build()

    val api = retrofit.create(Api::class.java)


    api.getRepos("willA")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : SingleObserver<List<Repo?>?> {
          override fun onSubscribe(disposable: Disposable) {
            textView.text = "正在请求"
            this@MainActivity.disposable = disposable
          }

          override fun onSuccess(repos: List<Repo?>) {
            textView.text = repos[0]?.name
          }

          override fun onError(e: Throwable) {
            textView.text = e.message ?: e.javaClass.name
          }


        });
  }

  private fun single() {
    var single = Single.just("1")
    single.subscribe(object :SingleObserver<String>{
      override fun onSuccess(t: String) {
        textView.text = t;
      }

      override fun onSubscribe(d: Disposable) {}

      override fun onError(e: Throwable) {}

    })
  }




  override fun onDestroy() {
    disposable?.dispose()
    super.onDestroy()
  }

}





