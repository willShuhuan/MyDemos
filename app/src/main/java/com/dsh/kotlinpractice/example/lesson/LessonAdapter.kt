package com.dsh.kotlinpractice.example.lesson

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList
import com.dsh.kotlinpractice.example.BaseViewHolder
import com.dsh.kotlinpractice.example.lesson.Lesson.State.LIVE
import com.dsh.kotlinpractice.example.lesson.Lesson.State.PLAYBACK
import com.dsh.kotlinpractice.example.lesson.Lesson.State.WAIT
import com.dsh.kotlinpractice.example.lesson.LessonAdapter.LessonViewHolder
import com.dsh.mydemos.R

/**
 * @author dongshuhuan
 * date 2020/10/28
 * version
 */
class LessonAdapter : RecyclerView.Adapter<LessonViewHolder?>() {

  private var list: List<Lesson> = ArrayList()

  internal fun updateAndNotify(list: List<Lesson>) {
    this.list = list
    notifyDataSetChanged()
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): LessonViewHolder {
    return LessonViewHolder.onCreate(parent)
  }

  override fun onBindViewHolder(
    holder: LessonViewHolder,
    position: Int
  ) {
    holder.onBind(list[position])
  }

  /**
   * 静态内部类
   */
  class LessonViewHolder internal constructor(itemView: View) : BaseViewHolder(
      itemView
  ) {
    @SuppressLint("NewApi")
    internal fun onBind(lesson: Lesson) {
      var date = lesson.getDate()
      if (date == null) {
        date = "日期待定"
      }
      setText(R.id.tv_date, date)
      setText(R.id.tv_content, lesson.getContent())
      val state = lesson.getState()
      if (state != null) {
        setText(R.id.tv_state, state.stateName())
        var colorRes = R.color.playback
        colorRes = when (state) {
          PLAYBACK -> {

            // 即使在 {} 中也是需要 break 的。
            R.color.playback
          }
          LIVE -> R.color.live
          WAIT -> R.color.wait
        }
        val backgroundColor = itemView.context.getColor(colorRes)
        getView<View>(R.id.tv_state)!!.setBackgroundColor(backgroundColor)
      }
    }

    companion object {
      fun onCreate(parent: ViewGroup): LessonViewHolder {
        return LessonViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_lesson, parent, false)
        )
      }
    }
  }


}
