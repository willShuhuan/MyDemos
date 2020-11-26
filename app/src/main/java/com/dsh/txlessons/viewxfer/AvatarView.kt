package com.dsh.txlessons.viewxfer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import com.dsh.mydemos.R
import com.dsh.txlessons.customview.view.px

/**
 * @author dongshuhuan
 * date 2020/11/25
 * version
 */
private val IMAGE_WIDTH = 200f.px
private val IMAGE_PADDING = 100f.px
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
class AvatarView(context: Context,attributeSet: AttributeSet): View(context,attributeSet) {

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG);
  private val bounds = RectF(IMAGE_PADDING,IMAGE_PADDING,IMAGE_PADDING+ IMAGE_WIDTH,IMAGE_PADDING+IMAGE_WIDTH)

  @RequiresApi(VERSION_CODES.LOLLIPOP)
  override fun onDraw(canvas: Canvas) {
    val count = canvas.saveLayer(bounds,null);
    canvas.drawOval(IMAGE_PADDING,IMAGE_PADDING,IMAGE_PADDING+ IMAGE_WIDTH,IMAGE_PADDING+IMAGE_WIDTH,paint)
    paint.xfermode = XFERMODE
    canvas.drawBitmap(getAvatar(IMAGE_WIDTH.toInt()),IMAGE_PADDING,IMAGE_PADDING,paint)
    paint.xfermode = null
    canvas.restoreToCount(count)
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