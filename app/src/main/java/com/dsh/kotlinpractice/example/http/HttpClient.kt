package com.dsh.kotlinpractice.example.http

import android.support.annotation.NonNull
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Type

/**
 * @author dongshuhuan
 * date 2020/10/28
 * version
 */
object HttpClient : OkHttpClient() {

  private val gson = Gson()

  @NonNull
  fun <T> convert(
    json: String?,
    type: Type
  ): T {
    return gson.fromJson(json, type)
  }

  fun <T> get(
    path: String,
    type: Type,
    entityCallback: EntityCallback<T>
  ) {
    val request = okhttp3.Request.Builder()
        .url("https://api.hencoder.com/$path")
        .build()
    val call: Call = this.newCall(request)
    call.enqueue(object : Callback {
      override fun onFailure(
        call: Call,
        e: IOException
      ) {
        entityCallback.onFailure("网络异常")
      }

      override fun onResponse(
        call: Call,
        response: Response
      ) {
        val code = response.code()
        if (code >= 200 && code < 300) {
          val body = response.body()
          var json: String? = null
          try {
            json = body!!.string()
          } catch (e: IOException) {
            e.printStackTrace()
          }
          entityCallback.onSuccess(convert<Any>(json, type) as T)
        } else if (code >= 400 && code < 500) {
          entityCallback.onFailure("客户端错误")
        } else if (code > 500 && code < 600) {
          entityCallback.onFailure("服务器错误")
        } else {
          entityCallback.onFailure("未知错误")
        }
      }
    })
  }

}