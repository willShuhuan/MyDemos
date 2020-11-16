package com.dsh.txlessons.gradle

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup

/**
 * @author dongshuhuan
 * date 2020/11/15
 * version
 */
fun Activity.drawBadge() {
  val badge = View(this)
  badge.setBackgroundColor(Color.YELLOW)
  val decorView = window.decorView as ViewGroup
  decorView.addView(badge, 200, 200)
}