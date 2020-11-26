package com.dsh.txlessons.viewxfer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import com.dsh.txlessons.customview.view.px

/**
 * @author dongshuhuan
 * date 2020/11/25
 * version
 */

private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
class XfermodeView(context: Context,attributeSet: AttributeSet): View(context,attributeSet) {

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG);
  private val bounds = RectF(50f.px,50f.px,300f.px,200f.px)
  private val circleBitmap = Bitmap.createBitmap(150f.px.toInt(),150f.px.toInt(),Bitmap.Config.ARGB_8888)
  private val squareBitmap = Bitmap.createBitmap(150f.px.toInt(),150f.px.toInt(),Bitmap.Config.ARGB_8888)


  init {
    val canvas = Canvas(circleBitmap)
    paint.color = Color.parseColor("#D81B60")
    //1 目标图形红色圆形
    canvas.drawOval(50f.px, 0f.px, 150f.px, 100f.px, paint)
    paint.color = Color.parseColor("#2196F3")
    //2 源图形蓝色方形
    canvas.setBitmap(squareBitmap)
    canvas.drawRect(0f.px, 50f.px, 100f.px, 150f.px, paint)
  }

  @RequiresApi(VERSION_CODES.LOLLIPOP)
  override fun onDraw(canvas: Canvas) {
    //3 绘制的时候变换xFermode
    val count = canvas.saveLayer(bounds,null);
    canvas.drawBitmap(circleBitmap,150f.px,50f.px,paint)
    paint.xfermode = XFERMODE
    canvas.drawBitmap(squareBitmap,150f.px,50f.px,paint)
    paint.xfermode = null
    canvas.restoreToCount(count)

  }



}