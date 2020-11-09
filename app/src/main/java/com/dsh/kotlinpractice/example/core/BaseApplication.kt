package com.dsh.kotlinpractice.example.core

import android.app.Application
import android.content.Context

/**
 * @author dongshuhuan
 * date 2020/10/28
 * version
 */
class BaseApplication : Application(){

  companion object{
    lateinit var currentApplication: Context;
    @JvmStatic
    fun currentApplication():Context{
      return currentApplication;
    }
  }


  override fun onCreate() {
    super.onCreate()
    currentApplication = this;
  }


}

