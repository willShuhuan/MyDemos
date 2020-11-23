package com.dsh.txlessons.mvvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.dsh.mydemos.R

/**
 * @author dongshuhuan
 * date 2020/11/20
 * version
 */
class MvvmActivity:AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.layout_mvvm)

    var mvvmText: EditText = findViewById(R.id.mvvmText);

    ViewModel(mvvmText).init();
  }

}