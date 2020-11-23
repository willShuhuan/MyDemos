package com.dsh.txlessons.mvvm

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.dsh.txlessons.mvvm.StringAttr.OnchangeListener

/**
 * @author dongshuhuan
 * date 2020/11/20
 * version
 */
class ViewBinder {
  companion object{
    private const val TAG = "ViewBinder"
    //双向绑定
    fun bind(editText: EditText,stringAttr: StringAttr){
      editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
          if (!TextUtils.equals(stringAttr.value,s)){
            stringAttr.value = s.toString();
            Log.d(TAG,"view通知model改变数据了:${s}")
          }
        }

        override fun beforeTextChanged(
          s: CharSequence?,
          start: Int,
          count: Int,
          after: Int
        ) {
        }

        override fun onTextChanged(
          s: CharSequence?,
          start: Int,
          before: Int,
          count: Int
        ) {
        }
      })
      stringAttr.onChangeListener = object : OnchangeListener {
        override fun onChange(newVal: String?) {
          if (!TextUtils.equals(newVal,editText.text)) {
            editText.setText(newVal)
            Log.d(TAG,"model通知view改变文字了:${newVal}")
          }
        }
      }
    }
  }
}