package com.dsh.txlessons.viewcustomize

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.dsh.txlessons.viewtext.dp
import java.util.Random

private val COLORS = intArrayOf(
  Color.parseColor("#E91E63"),
  Color.parseColor("#673AB7"),
  Color.parseColor("#3F51B5"),
  Color.parseColor("#2196F3"),
  Color.parseColor("#009688"),
  Color.parseColor("#FF9800"),
  Color.parseColor("#FF5722"),
  Color.parseColor("#795548")
)
private val TEXT_SIZES = intArrayOf(16, 22, 28)
private val CORNER_RADIUS = 4.dp
private val X_PADDING = 16.dp.toInt()
private val Y_PADDING = 8.dp.toInt()

/**
 * 随机字体大小和颜色的TextView
 */
class ColoredTextView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
  private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val random = Random()

  init {
    setTextColor(Color.WHITE)
    textSize = TEXT_SIZES[random.nextInt(3)].toFloat()
    paint.color = COLORS[random.nextInt(COLORS.size)]
    setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING)
  }

  @RequiresApi(VERSION_CODES.LOLLIPOP)
  override fun onDraw(canvas: Canvas) {
    canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), CORNER_RADIUS, CORNER_RADIUS, paint)
    super.onDraw(canvas)
  }
}