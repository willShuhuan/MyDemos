package com.dsh.kotlinpractice.example.lesson

import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.Toolbar.OnMenuItemClickListener
import android.view.MenuItem
import android.widget.LinearLayout
import com.dsh.kotlinpractice.example.BaseView
import com.dsh.mydemos.R

/**
 * @author dongshuhuan
 * date 2020/10/28
 * version
 */
@RequiresApi(VERSION_CODES.LOLLIPOP)
class LessonActivity : AppCompatActivity(), BaseView<LessonPresenter?>, OnMenuItemClickListener {
  private val lessonPresenter: LessonPresenter = LessonPresenter(this)
  override fun getPresenter(): LessonPresenter {
    return lessonPresenter
  }

  private val lessonAdapter: LessonAdapter = LessonAdapter()
  private var refreshLayout: SwipeRefreshLayout? = null
  protected override fun onCreate(@Nullable savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_lesson)
    val toolbar: Toolbar = findViewById(R.id.toolbar)
    toolbar.inflateMenu(R.menu.menu_lesson)
    toolbar.setOnMenuItemClickListener(this)
    val recyclerView: RecyclerView = findViewById(R.id.list)
    recyclerView.setLayoutManager(LinearLayoutManager(this))
    recyclerView.setAdapter(lessonAdapter)
    recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
    refreshLayout = findViewById(R.id.swipe_refresh_layout)
    refreshLayout!!.setOnRefreshListener(object : OnRefreshListener {
      override fun onRefresh() {
        getPresenter().fetchData()
      }
    })
    refreshLayout!!.setRefreshing(true)
    getPresenter().fetchData()
  }

  fun showResult(lessons: List<Lesson?>?) {
    lessonAdapter.updateAndNotify(lessons as List<Lesson>)
    refreshLayout?.setRefreshing(false)
  }

  override fun onMenuItemClick(item: MenuItem?): Boolean {
    getPresenter().showPlayback()
    return false
  }
}