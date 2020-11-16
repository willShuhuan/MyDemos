package com.dsh.txlessons.gradle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dsh.glib2.GradleLib2
import com.dsh.mydemos.R

/**
 * @author dongshuhuan
 * date 2020/11/15
 * version
 */
class GradleMainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gradle)

    drawBadge()

    val g2:GradleLib2 = GradleLib2();
  }
}