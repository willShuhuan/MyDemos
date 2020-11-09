package com.dsh.kotlinpractice.example

/**
 * @author dongshuhuan
 * date 2020/10/28
 * version
 */
interface BaseView<T> {
  fun getPresenter():T
}