package com.dsh.txlessons.viewmaterialedittext

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import com.dsh.mydemos.R
import com.dsh.txlessons.viewtext.dp

/**
 * @author dongshuhuan
 * date 2020/12/4
 * version
 */

private val TEXT_SIZE = 12.dp
private val TEXT_MARGIN = 8.dp
private val HORIZONTAL_OFFSET = 5.dp
private val VERTICAL_OFFSET = 23.dp
private val EXTRA_VERTICAL_OFFSET = 16.dp //上下移动偏移量

public class MaterialEditText(context: Context, attrs: AttributeSet
) : android.support.v7.widget.AppCompatEditText(context, attrs) {

  private  val TAG = "MaterialEditText"
  //画笔
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  //悬浮hint是否显示
  private var floatingLabelShown = false
  var floatingLabelFraction = 0f
    set(value) {
      field = value
      invalidate()
    }

  //显示动画 延迟初始化
  private val animator by lazy {
    ObjectAnimator.ofFloat(this,"floatingLabelFraction",0f,1f)
  }
  //消失动画 延迟初始化
  private val animatorReverse by lazy {
    ObjectAnimator.ofFloat(this,"floatingLabelFraction",0f)
  }
  //8 java,kotlin方式开启FloatingLabel
  var useFloatingLabel = false
    set(value) {
      if (field!=value) {
        field = value
        if (field) {
          setPadding(paddingLeft, (paddingTop+ TEXT_SIZE+ TEXT_MARGIN).toInt(),paddingRight,paddingBottom)
        }else{
          setPadding(paddingLeft, (paddingTop- TEXT_SIZE- TEXT_MARGIN).toInt(),paddingRight,paddingBottom)
        }
      }
    }

  init {
    //2.hint 字体文字
    paint.textSize = TEXT_SIZE;
    //1. 设置padding，给hint文字留有空间，比普通EditText要大
    if (useFloatingLabel) {
      setPadding(paddingLeft, (paddingTop+ TEXT_SIZE+ TEXT_MARGIN).toInt(),paddingRight,paddingBottom)
    }
    // 9 xml中useFloatingLabel属性处理
    // 过滤并取值
//    val typeArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
//    useFloatingLabel = typeArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel,true)
    //kotlin优化写法，自定义取值法
    val typeArray = context.obtainStyledAttributes(attrs, intArrayOf(R.attr.useFloatingLabel))
    useFloatingLabel = typeArray.getBoolean(0,true)
    typeArray.recycle()

    //10 打印探究一下attrs中包含哪些信息，指向xml中添加的属性
    for (index in 0 until attrs.attributeCount){
      Log.d(TAG, "key：${attrs.getAttributeName(index)},value:${attrs.getAttributeValue(index)}")
    }

  }

  override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
    super.onTextChanged(text, start, lengthBefore, lengthAfter)
    //5 浮动hint处理逻辑
    if (floatingLabelShown&&text.isNullOrEmpty()){
      //5.1 消失 1->0
      floatingLabelShown = false;
//      animatorReverse.start()
      //使用反向动画替代animatorReverse
      animator.reverse()
    }else if (!floatingLabelShown && !text.isNullOrEmpty()){
      //5.2 显示 0->1
      floatingLabelShown = true;
      animator.start()
    }
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
//    4.当输入文字不为空时，显示上方hint文字,这一步做演示，忽略
//    if (!text.isNullOrEmpty()){
      //6. 绘制时画笔透明度变化
      paint.alpha = (floatingLabelFraction*0xff).toInt()
      //7.上下移动动画
      val currentVerticalValue = VERTICAL_OFFSET+EXTRA_VERTICAL_OFFSET*(1-floatingLabelFraction)
      //3.把hint字体画出来
      canvas.drawText(hint.toString(),HORIZONTAL_OFFSET,currentVerticalValue,paint)
//    }

  }


}