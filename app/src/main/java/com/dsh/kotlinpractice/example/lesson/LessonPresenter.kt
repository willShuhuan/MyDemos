package com.dsh.kotlinpractice.example.lesson

import android.annotation.SuppressLint
import com.dsh.kotlinpractice.example.http.EntityCallback
import com.dsh.kotlinpractice.example.http.HttpClient.get
import com.dsh.kotlinpractice.example.lesson.Lesson.State.PLAYBACK
import com.dsh.kotlinpractice.example.utils.Utils
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

/**
 * @author dongshuhuan
 * date 2020/10/28
 * version
 */
class LessonPresenter {
  private val LESSON_PATH = "lessons"

  private var activity: LessonActivity? = null

  constructor(activity: LessonActivity?) {
    this.activity = activity
  }

  private var lessons: List<Lesson?> = ArrayList()

  private val type = object : TypeToken<List<Lesson?>?>() {}.type

  fun fetchData() {
    get(
        LESSON_PATH, type, object : EntityCallback<List<Lesson>> {
      @SuppressLint("NewApi")
      override fun onSuccess(lessons: List<Lesson>) {
        this@LessonPresenter.lessons = lessons
        activity!!.runOnUiThread { activity!!.showResult(lessons) }
      }

      override fun onFailure(message: String?) {
         activity!!.runOnUiThread { Utils.toast(message) }
      }

    })
  }

  @SuppressLint("NewApi") fun showPlayback() {
    val playbackLessons: MutableList<Lesson?> = ArrayList()
    for (lesson in lessons) {
      if (lesson!!.getState() === PLAYBACK) {
        playbackLessons.add(lesson)
      }
    }
    activity!!.showResult(playbackLessons)
  }
}