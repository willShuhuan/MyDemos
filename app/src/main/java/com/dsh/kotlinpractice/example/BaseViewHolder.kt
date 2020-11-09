package com.dsh.kotlinpractice.example

import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import java.util.HashMap

/**
 * @author dongshuhuan
 * date 2020/10/28
 * version
 */
abstract class BaseViewHolder: RecyclerView.ViewHolder {

  constructor(itemView: View):super(itemView){
  }

  @SuppressLint("UseSparseArrays")
  private val viewHashMap: MutableMap<Int, View?> = HashMap()

  protected open fun <T : View?> getView(@IdRes id: Int): T? {
    var view = viewHashMap[id]
    if (view == null) {
      view = itemView.findViewById(id)
      viewHashMap[id] = view
    }
    return view as T?
  }

  protected open fun setText(
    @IdRes id: Int,
    text: String?
  ) {
    (getView<View>(id) as TextView).text = text
  }


}