package com.dsh.txlessons.viewclip

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Path.Direction.CCW
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import com.dsh.mydemos.R
import com.dsh.txlessons.viewtext.dp

/**
 * @author dongshuhuan
 * date 2020/11/25
 * version
 */
private val BITMAP_SIZE = 200.dp
private val BITMAP_PADDING = 100.dp
class CameraView (context: Context,attributeSet: AttributeSet): View(context,attributeSet) {

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val bitmap = getAvatar(BITMAP_SIZE.toInt())
  @RequiresApi(VERSION_CODES.LOLLIPOP)
  private val clipped = Path().apply {
    addOval(BITMAP_PADDING, BITMAP_PADDING, BITMAP_PADDING+ BITMAP_SIZE, BITMAP_PADDING+ BITMAP_SIZE,CCW)
  }
  private val camera = Camera()

  init {
    camera.rotateX(30f)
    camera.setLocation(0f,0f,-3*resources.displayMetrics.density)
  }

  @RequiresApi(VERSION_CODES.LOLLIPOP)
  override fun onDraw(canvas: Canvas) {
    //1. Canvas范围裁切
//    //椭圆裁切
//    canvas.clipPath(clipped)
//    //矩形裁切，左上角切四分之一
//    canvas.clipRect(BITMAP_PADDING, BITMAP_PADDING, BITMAP_PADDING+ BITMAP_SIZE/2, BITMAP_PADDING+ BITMAP_SIZE/2)
//    canvas.drawBitmap(bitmap, BITMAP_PADDING,BITMAP_PADDING,paint)

    //2. Canvas几何变换
//    canvas.save();
    //平移
//    canvas.translate(200.dp, 0.dp);
    //旋转
//    canvas.rotate(45f, 100.dp, 100.dp);
    //等比放大1.3倍
//    canvas.scale(1.3f, 1.3f, BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2);
    //不等比放大
//    canvas.scale(1.3f, 0.7f, BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2);
    //参数里的 sx 和 sy 是 x 方向和 y 方向的错切系数。
//    canvas.skew(0f, 0.5f);
//    canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint);
//    canvas.restore();

    //3. Matrix 几何变换
    //投影效果
//    canvas.translate(BITMAP_PADDING+ BITMAP_SIZE/2,BITMAP_PADDING+ BITMAP_SIZE/2);
//    camera.applyToCanvas(canvas)
//    canvas.translate(-(BITMAP_PADDING+ BITMAP_SIZE/2),-(BITMAP_PADDING+ BITMAP_SIZE/2))
//    canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING,paint)

    //翻页效果
    //上半部分 反着看，绘制->移动->裁切->移动
    //移动
    canvas.save()
    canvas.translate(BITMAP_PADDING+ BITMAP_SIZE/2,BITMAP_PADDING+ BITMAP_SIZE/2);
    //裁切
    canvas.clipRect(-BITMAP_SIZE/2, -BITMAP_SIZE/2, BITMAP_SIZE/2, BITMAP_SIZE/2)
    canvas.translate(-(BITMAP_PADDING+ BITMAP_SIZE/2),-(BITMAP_PADDING+ BITMAP_SIZE/2))
    canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING,paint)
    canvas.restore()

    //画一条线
//    canvas.save()
    paint.color = Color.parseColor("#FF0000")
    canvas.drawLine(BITMAP_SIZE/2,BITMAP_PADDING+ BITMAP_SIZE/2, BITMAP_PADDING+BITMAP_SIZE,BITMAP_PADDING+ BITMAP_SIZE/2,paint)
//    canvas.restore()

    //下半部分，反着看，绘制->移动->裁切->camera旋转->再移动
    //移动
    canvas.save()
    canvas.translate(BITMAP_PADDING+ BITMAP_SIZE/2,BITMAP_PADDING+ BITMAP_SIZE/2);
    camera.applyToCanvas(canvas)
    //裁切
    canvas.clipRect(-BITMAP_SIZE/2, 2f, BITMAP_SIZE/2, BITMAP_SIZE/2)
    canvas.translate(-(BITMAP_PADDING+ BITMAP_SIZE/2),-(BITMAP_PADDING+ BITMAP_SIZE/2))
    canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING,paint)
    canvas.restore()

  }

  fun getAvatar(width: Int): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, R.mipmap.slmh, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width
    return BitmapFactory.decodeResource(resources, R.mipmap.slmh, options)
  }
}