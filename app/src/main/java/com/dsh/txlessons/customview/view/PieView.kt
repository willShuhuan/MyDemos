package com.dsh.txlessons.customview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View

/**
 * @author dongshuhuan
 * date 2020/11/24
 * version
 */
//1. 半径，角度，颜色设置
private val RADIUS = 150f.px
private val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)
private val COLORS = listOf(Color.parseColor("#C2185B"), Color.parseColor("#00ACC1"), Color.parseColor("#558B2F"), Color.parseColor("#5D4037"))
private val OFFSET_LENGTH = 20f.px

class PieView  (context: Context?,attrs: AttributeSet): View(context,attrs){
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

  @RequiresApi(VERSION_CODES.LOLLIPOP)
  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

  }

  @RequiresApi(VERSION_CODES.LOLLIPOP)
  override fun onDraw(canvas: Canvas) {
    //画弧
    var startAngle = 0f;
    //2 for循环绘制
    for ((index,angle)in ANGLES.withIndex()){
      paint.color = COLORS[index];
      //3.1 偏移某个扇形
      if (index==1){
        canvas.save()
        canvas.translate(
            (OFFSET_LENGTH*Math.cos(Math.toRadians((startAngle+angle/2).toDouble()))).toFloat(),
            (OFFSET_LENGTH*Math.sin(Math.toRadians((startAngle+angle/2).toDouble()))).toFloat()
        )
      }
      canvas.drawArc(width/2f-RADIUS,height/2-RADIUS,width/2f+RADIUS,height/2f+RADIUS,
          startAngle,angle,true,paint)
      startAngle += angle;
      //3.2 重置canvas
      if (index==1){
        canvas.restore()
      }
    }

  }
}