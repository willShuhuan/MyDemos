package com.dsh.txlessons.retrofit

import com.dsh.retrofit.Repo
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path

/**
 * @author dongshuhuan
 * date 2020/10/29
 * version
 */
interface GitHubService {
  @GET("users/{user}/repos")
  fun listRepos(@Path("user") user: String?): Call<List<Repo>>

  @GET("users/{user}/repos")
  fun listReposRx(@Path("user") user: String?): Single<List<Repo>>

  //使用http注解
  @HTTP(method = "get",path = "users/{user}/repos",hasBody = false)
  fun httpRepos(@Path("user") user: String?): Call<List<Repo>>

}