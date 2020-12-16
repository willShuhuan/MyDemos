package com.dsh.txlessons.viewanimation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
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
private val BITMAP_PADDING = 0.dp
class CameraView (context: Context,attributeSet: AttributeSet): View(context,attributeSet) {

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val bitmap = getAvatar(BITMAP_SIZE.toInt())
  private val camera = Camera()
  var topFlip = 0f
    set(value) {
      field=value
      invalidate()
    }
  var bottomFlip = 30f
    set(value) {
      field=value
      invalidate()
    }
  var flipRotation = 0f
    set(value) {
      field=value
      invalidate()
    }

  init {
    camera.setLocation(0f,0f,-6*resources.displayMetrics.density)
  }

  @RequiresApi(VERSION_CODES.LOLLIPOP)
  override fun onDraw(canvas: Canvas) {
    //翻页效果
    //上半部分
    canvas.save()
    canvas.translate(BITMAP_PADDING+ BITMAP_SIZE/2,BITMAP_PADDING+ BITMAP_SIZE/2);
    canvas.rotate(-flipRotation)
    camera.save()
    camera.rotateX(topFlip)
    camera.applyToCanvas(canvas)
    camera.restore()
    canvas.clipRect(-BITMAP_SIZE, -BITMAP_SIZE, BITMAP_SIZE, 0f)
    canvas.rotate(flipRotation)
    canvas.translate(-(BITMAP_PADDING+ BITMAP_SIZE/2),-(BITMAP_PADDING+ BITMAP_SIZE/2))
    canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING,paint)
    canvas.restore()


    //下半部分
    canvas.save()
    canvas.translate(BITMAP_PADDING+ BITMAP_SIZE/2,BITMAP_PADDING+ BITMAP_SIZE/2);
    canvas.rotate(-flipRotation)
    camera.save()
    camera.rotateX(bottomFlip)
    camera.applyToCanvas(canvas)
    camera.restore()
    canvas.clipRect(-BITMAP_SIZE, 0f, BITMAP_SIZE, BITMAP_SIZE)
    canvas.rotate(flipRotation)
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