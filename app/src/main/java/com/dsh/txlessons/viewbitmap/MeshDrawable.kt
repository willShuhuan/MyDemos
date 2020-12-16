package com.dsh.txlessons.viewbitmap

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import com.dsh.txlessons.viewtext.dp

/**
 * @author dongshuhuan
 * date 2020/11/28
 * version
 */
class MeshDrawable:Drawable() {
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    color = Color.parseColor("#F9A825")
    strokeWidth = 5.dp
  }
  private val INTERVAL = 50.dp

  override fun draw(canvas: Canvas) {
    //网格竖线
    var x = bounds.left.toFloat()
    while (x < bounds.right){
      canvas.drawLine(x,bounds.top.toFloat(),x, bounds.bottom.toFloat(),paint)
      x += INTERVAL
    }
    //网格横线
    var y = bounds.top.toFloat()
    while (y < bounds.bottom){
      canvas.drawLine(bounds.left.toFloat(),y, bounds.right.toFloat(),y,paint)
      y += INTERVAL
    }
  }

  override fun setAlpha(alpha: Int) {
    paint.alpha = alpha
  }

  override fun getAlpha(): Int {
    return paint.alpha
  }

  override fun getOpacity(): Int {
    return when(paint.alpha){
      0 -> PixelFormat.TRANSPARENT
      0xff -> PixelFormat.OPAQUE
      else -> PixelFormat.TRANSLUCENT
    }
  }

  override fun setColorFilter(colorFilter: ColorFilter?) {
    paint.colorFilter = colorFilter
  }

  override fun getColorFilter(): ColorFilter? {
    return paint.colorFilter
  }

}