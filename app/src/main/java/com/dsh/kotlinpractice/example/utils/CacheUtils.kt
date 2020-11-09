package com.dsh.kotlinpractice.example.utils

import android.content.Context
import com.dsh.kotlinpractice.example.core.BaseApplication
import com.dsh.mydemos.R

/**
 * @author dongshuhuan
 * date 2020/10/28
 * version
 */

//object修饰 静态
object CacheUtils {

  private val context = BaseApplication.currentApplication();

  private val SP = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);

  fun save(key: String?, value: String?) {
    SP.edit().putString(key, value).apply();
  }

  fun get(key: String?): String? {
      return SP.getString(key, null);
  }

}