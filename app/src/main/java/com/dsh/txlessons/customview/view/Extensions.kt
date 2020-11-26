package com.dsh.txlessons.customview.view

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author dongshuhuan
 * date 2020/11/24
 * version
 */
val Float.px
  get() = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP,
      this,
      Resources.getSystem().displayMetrics
  )