package com.dsh.kotlinpractice.example.http

/**
 * @author dongshuhuan
 * date 2020/10/28
 * version
 */
interface EntityCallback<T> {
  fun onSuccess(entity:T)
  fun onFailure(message:String?)
}