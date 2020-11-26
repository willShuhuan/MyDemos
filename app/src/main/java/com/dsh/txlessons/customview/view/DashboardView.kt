package com.dsh.txlessons.customview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.graphics.PathDashPathEffect.Style.ROTATE
import android.graphics.PathMeasure
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author dongshuhuan
 * date 2020/11/24
 * version
 */
const val OPEN_ANGLE = 120F
//扇形半径
val CIRCLE_RADIUS = 150f.px
val LENGTH = 120f.px
//3.1 虚线宽高
val DASH_WIDTH = 2f.px
val DASH_LENGTH = 10f.px


class DashboardView (context: Context?,attrs: AttributeSet): View(context,attrs){
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val dash = Path();
  private val path = Path();
  private lateinit var pathEffect: PathDashPathEffect;

  init {
    //2.画笔风格设置为空心有边框
    paint.strokeWidth = 3f.px
    paint.style = Paint.Style.STROKE
    //3.2 添加刻度效果 矩形刻度条
    dash.addRect(0f,0f, DASH_WIDTH, DASH_LENGTH,Path.Direction.CCW)

  }

  @RequiresApi(VERSION_CODES.LOLLIPOP)
  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    //3.3 设置刻度间隔 20个间隔
    path.reset()
    path.addArc(width/2f-CIRCLE_RADIUS,height/2-CIRCLE_RADIUS,width/2f+CIRCLE_RADIUS,height/2f+CIRCLE_RADIUS,
        90 + OPEN_ANGLE/2,360- OPEN_ANGLE)
    val pathMessure = PathMeasure(path,false)
    pathEffect = PathDashPathEffect(dash,(pathMessure.length - DASH_WIDTH)/20,0f, ROTATE)
  }

  @RequiresApi(VERSION_CODES.LOLLIPOP)
  override fun onDraw(canvas: Canvas) {
    //1.首先画一个开口为120度的圆形（第一遍绘制）
//    canvas.drawArc(width/2f-150f.px,height/2-150f.px,width/2f+150f.px,height/2f+150f.px,
//        90 + OPEN_ANGLE/2,360- OPEN_ANGLE,false,paint)
    canvas.drawPath(path,paint);

    //3.4 画刻度（第二遍绘制）
    paint.setPathEffect(pathEffect)
    canvas.drawPath(path,paint);
    paint.pathEffect = null;

    //4 画指针  指向第5格（第六个刻度）
    canvas.drawLine(width / 2f, height / 2f,
        width / 2f + LENGTH * cos(markToRadians(5)).toFloat(),
        height / 2f + LENGTH * sin(markToRadians(5)).toFloat(), paint)
  }

  private fun markToRadians(mark: Int) =
    Math.toRadians((90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20f * mark).toDouble())
}