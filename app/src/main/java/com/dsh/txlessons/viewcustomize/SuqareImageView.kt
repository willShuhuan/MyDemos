package com.dsh.txlessons.viewcustomize

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import kotlin.math.min

/**
 * @author dongshuhuan
 * date 2020/12/7
 * version
 */
class SuqareImageView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

//  override fun layout(l: Int, t: Int, r: Int, b: Int) {
//    //取短边为方形边长
//    val width = r-l
//    val height = b-t;
//    val size = min(width,height)
//    super.layout(l, t, l+size, t+size)
//  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    val size = min(measuredWidth,measuredHeight);
    setMeasuredDimension(size,size)
  }

}

