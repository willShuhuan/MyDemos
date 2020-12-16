package com.dsh.txlessons.setview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.dsh.mydemos.R
import kotlinx.android.synthetic.main.activity_set_view.iv
import kotlinx.android.synthetic.main.activity_set_view.surface
import kotlin.concurrent.thread

class SetViewActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_set_view)

    //可以更新
//    thread{
//      Looper.prepare()
//      Toast.makeText(this,"sss",0).show();
//      tv_setview.text = "${Thread.currentThread().name}:${SystemClock.uptimeMillis()}"
//      Looper.loop()
//    }
    //不可以更新(实际运行时可以的 很奇怪)
//    thread{
//      sleep(2000)
//      tv_setview.text = "${Thread.currentThread().name}:${SystemClock.uptimeMillis()}"
//    }

    //子线程吐司不报错写法
//    thread{
//      Looper.prepare()
//      Toast.makeText(this,"sss",0).show();
//      Looper.loop()
//    }

    //方式1 主线程申请成功后子线程申请
//    tv_setview.setOnClickListener(View.OnClickListener {
//      it.requestLayout()
//      thread{
//        tv_setview.text = "${Thread.currentThread().name}:${SystemClock.uptimeMillis()}"
//      }
//    })


    //方式2 在子线程中创建 ViewRootImpl
//      thread {
//        Looper.prepare()
//        val button = Button(this)
//        windowManager.addView(button, WindowManager.LayoutParams())
//        button.setOnClickListener(OnClickListener {
//          button.text = "${Thread.currentThread().name}:${SystemClock.uptimeMillis()}"
//        })
//        Looper.loop()
//      }

    //方式3 硬件加速

    //方式4 SurfaceView 通过 holder 获得 Canvas 对象，可以直接在子线 程中更新 UI
    surface.holder.addCallback(object : Callback {
      override fun surfaceChanged(
        holder: SurfaceHolder?,
        format: Int,
        width: Int,
        height: Int
      ) {

      }

      override fun surfaceDestroyed(holder: SurfaceHolder?) {
      }

      override fun surfaceCreated(holder: SurfaceHolder?) {
        thread {
          while (true){
//            val canvas = holder!!.lockCanvas()
//            Log.d("TAG", "surfaceCreated: "+canvas)
//            val random = Random()
//            val r = random.nextInt(255)
//            val g = random.nextInt(255)
//            val b = random.nextInt(255)
//            canvas.drawColor(Color.rgb(r,g,b))
          }
        }
      }
    })

    val options:RequestOptions = RequestOptions()
        .error(R.mipmap.ic_launcher)	//加载错误之后的错误图
        .override(300,300)	//指定图片的尺寸
        .fitCenter()   //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于 ImageView的宽或者是高。是指其中一个满足即可不会一定铺满 imageview）
        .centerCrop()//指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的宽高都 大于等于ImageView的宽度，然后截取中间的显示。）
        .diskCacheStrategy(DiskCacheStrategy.ALL)
//        .skipMemoryCache(true)	//不使用内存缓存
//        .diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
//        .diskCacheStrategy(DiskCacheStrategy.NONE)	//不使用硬盘本地缓存
//        .diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
//        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片;



    Glide.with(this).asGif().load("https://img-xhpfm-test.xinhuaxmt.com/2020/12/11/e8tlb9ld1qc.gif").apply(options).into(iv);

  }
}