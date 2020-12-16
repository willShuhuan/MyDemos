package com.dsh.txlessons.viewanimation

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dsh.mydemos.R
import kotlinx.android.synthetic.main.activity_view_animation.proviceView

class ViewAnimationActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_view_animation)

    //1. 属性动画ViewPropertyAnimator
//    view.animate()
//        .translationX(200.dp)//横向移动
//        .translationY(100.dp)//纵向移动
//        .alpha(0.5f)//透明度
//        .scaleX(2f)//横向放大
//        .scaleY(2f)//纵向放大
//        .rotation(90f)//旋转90度
//        .setStartDelay(1000)//延迟一秒执行


    //2.ObjectAnimator
    // 1 圆放大效果
//    val animator = ObjectAnimator.ofFloat(circleView, "radius", 150.dp)
//    animator.startDelay = 1000
//    animator.start()

    // 2 自定义动画效果
      //2.1 单一效果演示
//    val topFlipAnimator = ObjectAnimator.ofFloat(cameraView, "topFlip", 60f)
//    topFlipAnimator.startDelay = 1000
//    topFlipAnimator.duration = 1500
//    topFlipAnimator.start()
//
//    val bottomFlipAnimator = ObjectAnimator.ofFloat(cameraView, "bottomFlip", 60f)
//    bottomFlipAnimator.startDelay = 1000
//    bottomFlipAnimator.duration = 1500
//    bottomFlipAnimator.start()
//
//    val flipRotationAnimator = ObjectAnimator.ofFloat(cameraView, "flipRotation", 60f)
//    flipRotationAnimator.startDelay = 1000
//    flipRotationAnimator.duration = 1500
//    flipRotationAnimator.start()

    //2.2 AnimatorSet组合动画
//    val bottomFlipAnimator = ObjectAnimator.ofFloat(cameraView, "bottomFlip", 60f)
//    bottomFlipAnimator.startDelay = 1000
//    bottomFlipAnimator.duration = 1000
//
//    val flipRotationAnimator = ObjectAnimator.ofFloat(cameraView, "flipRotation", 270f)
//    flipRotationAnimator.startDelay = 200
//    flipRotationAnimator.duration = 1000
//
//    val topFlipAnimator = ObjectAnimator.ofFloat(cameraView, "topFlip", -60f)
//    topFlipAnimator.startDelay = 200
//    topFlipAnimator.duration = 1000
//
//    val animatorSet = AnimatorSet()
//    animatorSet.playSequentially(bottomFlipAnimator,flipRotationAnimator,topFlipAnimator)
//    animatorSet.start()

    //2.3 PropertyValuesHolder
//    val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 60f)
//    val flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f)
//    val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", - 60f)
//    val holderAnimator = ObjectAnimator.ofPropertyValuesHolder(cameraView, bottomFlipHolder,  flipRotationHolder,  topFlipHolder)
//    holderAnimator.startDelay = 1000
//    holderAnimator.duration = 2000
//    holderAnimator.start()

    //2.4 PropertyValuesHolder 配合Keyframe 细化动画
//    val length = 200.dp
//    val keyframe1 = Keyframe.ofFloat(0f, 0f)
//    val keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * length)
//    val keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length)
//    val keyframe4 = Keyframe.ofFloat(1f, 1f * length)
//    val keyframeHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4)
//    val animator = ObjectAnimator.ofPropertyValuesHolder(cameraView, keyframeHolder)
//    animator.startDelay = 1000
//    animator.duration = 2000
//    animator.start()

    //2.5 Interpolator 插值器
//    val animator = ObjectAnimator.ofFloat(cameraView,"translationX", 360.dp)
//    animator.interpolator = AccelerateDecelerateInterpolator()//先加速再减速
////    animator.interpolator = AccelerateInterpolator()//一直加速
////    animator.interpolator = LinearInterpolator()//匀速
//    animator.startDelay = 1000
//    animator.duration = 1000
//    animator.start()

    //2.6 TypeEvaluator 估值器
//    val animator = ObjectAnimator.ofObject(
//        pointView, "point", pointFEvaluator(), PointF(100.dp, 200.dp)
//    )


    //TypeEvaluator 文字滚动效果
    val animator = ObjectAnimator.ofObject(proviceView,"province",ProvinceEvaluator(),"澳门特别行政区")
    animator.startDelay = 1000
    animator.duration = 10000
    animator.start()
  }

  //自定义估值器
  class pointFEvaluator:TypeEvaluator<PointF> {
    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
      val startX= startValue.x
      val endX = endValue.x
      val currentX = startX + (endX-startX)*fraction
      val startY= startValue.y
      val endY = endValue.y
      val currentY = startY + (endY-startY)*fraction
      return PointF(currentX,currentY)
    }
  }

}