package com.dsh.txlessons.retrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dsh.mydemos.R
import com.dsh.retrofit.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author dongshuhuan
 * date 2020/10/29
 * version
 */
class RetrofitActivity:AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.layout_retrofit);

    //1 构建一个retrofit对象
    val retrofit = Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RXJava支持
        .build()

    //2. 获取接口的代理对象
    val service = retrofit.create(GitHubService::class.java)

    //3 获取具体的请求业务方法
    val repos: Call<List<Repo>> = service.listRepos("octocat")

    //4 发起异步请求
    repos.enqueue(object : Callback<List<Repo>?> {
      override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {
        println("Response: ${t.message}")
      }

      override fun onResponse(call: Call<List<Repo>?>, response: Response<List<Repo>?>) {
        println("Response: ${response.body()!![0].name}")
      }
    })
    //同步的，不切换线程(安卓中不使用)
//    repos.execute()

    val listReposRx = service.listReposRx("octocat")
    listReposRx.subscribe()

  }
}


