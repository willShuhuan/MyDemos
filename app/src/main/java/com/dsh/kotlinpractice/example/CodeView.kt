package com.dsh.kotlinpractice.example

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import com.dsh.mydemos.R
import com.dsh.mydemos.utils.UIUtil
import java.util.Random

/**
 * @author dongshuhuan
 * date 2020/10/27
 * version
 */
class CodeView : AppCompatTextView {

  constructor(context:Context):this(context,null){

  }

  @SuppressLint("NewApi")
  constructor(context:Context,attr:AttributeSet?):super(context,attr){
    setTextSize(TypedValue.COMPLEX_UNIT_SP,18f)
    gravity = Gravity.CENTER
    setBackgroundColor(getContext().getColor(R.color.colorPrimary))
    setTextColor(Color.WHITE)

    paint.isAntiAlias = true
    paint.style = Paint.Style.STROKE
    paint.color = getContext().getColor(R.color.colorAccent)
    paint.strokeWidth = UIUtil.dip2px(getContext(),6f).toFloat();

    updateCode();

  }

  private val paint = Paint();

  private val codeList = arrayOf(
    "kotlin",
    "android",
    "java",
    "http",
    "https",
    "okhttp",
    "retrofit",
    "tcp/ip"
  )

  fun updateCode() {
    val random:Int = Random().nextInt(codeList.size)
    var code = codeList[random];
    text = code;
  }

  override fun onDraw(canvas: Canvas?) {
    canvas?.drawLine(0f,height.toFloat(),width.toFloat(),0f,paint)
    super.onDraw(canvas)

  }

}