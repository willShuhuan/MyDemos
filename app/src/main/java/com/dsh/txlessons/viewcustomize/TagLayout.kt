package com.dsh.txlessons.viewcustomize

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * @author dongshuhuan
 * date 2020/12/7
 * version
 */
class TagLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

  private val childrenBounds = mutableListOf<Rect>()

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    //先遍历所有子view 计算尺寸
    val count: Int = getChildCount()//子view数量
    var widthUsed = 0 //
    var lineWidthUsed = 0 //每行已用的宽度
    var heightUsed = 0
    var lineMaxHeight = 0;
    val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
    val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
    for (index in 0 until count) {
      val child: View = getChildAt(index)

      //以下注释代码可以使用measureChildWithMargins一句代码替代
//      val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
//      val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
//      var childWidthSpecMode = 0;
//      var childWidthSpecSize = 0;
//      when (childParams.width) {
//        LayoutParams.MATCH_PARENT -> {
//          when (widthSpecMode) {
//            MeasureSpec.EXACTLY,MeasureSpec.AT_MOST -> {
//              childWidthSpecMode = MeasureSpec.EXACTLY
//              childWidthSpecSize = widthSpecSize - widthUsed
//            }
//            MeasureSpec.UNSPECIFIED -> {
//              childWidthSpecMode = MeasureSpec.UNSPECIFIED
//              childWidthSpecSize = 0
//            }
//          }
//        }
//        LayoutParams.WRAP_CONTENT -> {
//          when (widthSpecMode) {
//            MeasureSpec.EXACTLY,MeasureSpec.AT_MOST -> {
//              childWidthSpecMode = MeasureSpec.AT_MOST
//              childWidthSpecSize = widthSpecSize - widthUsed
//            }
//            MeasureSpec.UNSPECIFIED -> {
//              childWidthSpecMode = MeasureSpec.UNSPECIFIED
//              childWidthSpecSize = 0
//            }
//          }
//        }
//        else -> {
//          childWidthSpecMode = MeasureSpec.EXACTLY
//          childWidthSpecSize = childParams.width
//        }
//      }

      //第一次测量（每一个子view）
      measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,heightUsed)
      //折行处理, 重置下一行参数
      if (specWidthMode!=MeasureSpec.UNSPECIFIED &&
          lineWidthUsed+child.measuredWidth>specWidthSize){
        lineWidthUsed = 0
        heightUsed +=lineMaxHeight
        lineMaxHeight = 0
        measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,heightUsed)
      }
      //childrenBounds 添加元素
      if (index >= childrenBounds.size){
        childrenBounds.add(Rect())
      }
      val childBounds = childrenBounds[index];
      childBounds.set(lineWidthUsed,heightUsed,lineWidthUsed+child.measuredWidth,heightUsed+child.measuredHeight)
      lineWidthUsed += child.measuredWidth
      //保证tagLayout宽度为最宽那一行的宽度
      widthUsed = Math.max(widthUsed,lineWidthUsed)
      //每一行的最大高度
      lineMaxHeight = Math.max(lineMaxHeight,child.measuredHeight)
    }

    //再算出自己的尺寸
    val selfWidth = widthUsed//最宽行的宽度
    val selfHeight = lineMaxHeight+heightUsed//之前所有行的高度加上当前行的最大高度
    setMeasuredDimension(selfWidth,selfHeight)
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    val count: Int = getChildCount()
    for (index in 0 until count) {
      val view: View = getChildAt(index)
      val childBounds = childrenBounds[index]
      view.layout(childBounds.left,childBounds.top,childBounds.right,childBounds.bottom);
    }
  }

  override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
    return MarginLayoutParams(context,attrs)
  }

}