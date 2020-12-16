package com.dsh.txlessons.viewcustomize

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.dsh.txlessons.viewtext.dp

/**
 * @author dongshuhuan
 * date 2020/12/7
 * version
 */
private val RADIUS = 100.dp;
private val PADDING = 100.dp;
class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)


  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawCircle(PADDING+RADIUS,PADDING+RADIUS,RADIUS,paint)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val size = ((PADDING+ RADIUS)*2).toInt();
//    setMeasuredDimension(size,size)
    //以下被注释的代码，官方为我们提供了API->resolveSize()
//    val specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
//    val specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
//    val width = when(specWidthMode){
//      MeasureSpec.EXACTLY -> specWidthSize
//      MeasureSpec.AT_MOST -> if (size>specWidthSize) specWidthSize else size
//      else -> size
//    }
    val width = View.resolveSize(size,widthMeasureSpec);
    val height = View.resolveSize(size,heightMeasureSpec);
    setMeasuredDimension(width,height)

  }

}