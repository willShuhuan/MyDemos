package com.dsh.txlessons.constrainlayout;

import android.content.Context
import android.support.constraint.ConstraintHelper
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import kotlin.math.hypot

class CircularRevealHelper(context: Context, attrs: AttributeSet) : ConstraintHelper(context, attrs) {

  override fun updatePostLayout(container: ConstraintLayout) {
    super.updatePostLayout(container)

    referencedIds.forEach {
      val view = container.getViewById(it)
      val radius = hypot(view.width.toDouble(), view.height.toDouble()).toInt()

      ViewAnimationUtils.createCircularReveal(view, 0, 0, 0f, radius.toFloat())
        .setDuration(2000L)
        .start()
    }
  }
}
