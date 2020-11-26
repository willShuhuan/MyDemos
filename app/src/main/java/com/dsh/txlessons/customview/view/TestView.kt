package com.dsh.txlessons.customview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * @author dongshuhuan
 * date 2020/11/24
 * version
 */
//val RADIUS = 200F;
//使用kotlin扩展属性
private val RADIUS = 50f.px
private const val TAG = "TestView"

class TestView(context:Context?,attrs:AttributeSet):View(context,attrs) {
  private val parint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val path = Path()
  lateinit var pathMeasure:PathMeasure

  //画圆
//  override fun onDraw(canvas: Canvas) {
//    canvas.drawLine(100f,100f,200f,200f,parint)
//    canvas.drawCircle(width/2f,200f,
//        RADIUS,parint);
//  }

  //path使用
  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    path.reset()
    //画圆
    path.addCircle(width/2f,200f, RADIUS,Path.Direction.CCW)
    //画方
    path.addRect(width/2f- RADIUS,200f,width/2f+ RADIUS,200f+2*RADIUS,Path.Direction.CW)
    path.addCircle(width/2f,200f, RADIUS*1.5f,Path.Direction.CCW)
    pathMeasure = PathMeasure(path,false)
    Log.d(TAG, "onSizeChanged: "+pathMeasure.length)//100f*4 * 2.75 = 1100
    //相交 基数填充 偶数留空
    path.fillType = Path.FillType.EVEN_ODD;
  }

  override fun onDraw(canvas: Canvas) {
    canvas.drawPath(path,parint)
  }

}