package com.dsh.kotlinpractice.example.lesson

/**
 * @author dongshuhuan
 * date 2020/10/28
 * version
 */
class Lesson {
  enum class State {
    PLAYBACK {
      override fun stateName(): String {
        return "有回放"
      }
    },
    LIVE {
      override fun stateName(): String {
        return "正在直播"
      }
    },
    WAIT {
      override fun stateName(): String {
        return "等待中"
      }
    };

    abstract fun stateName(): String?
  }

  private var date: String? = null
  private var content: String? = null
  private var state: State? = null

  constructor(
    date: String?,
    content: String?,
    state: State?
  ) {
    this.date = date
    this.content = content
    this.state = state
  }

  fun getState(): State? {
    return state
  }

  fun setState(state: State?) {
    this.state = state
  }

  fun getDate(): String? {
    return date
  }

  fun setDate(date: String?) {
    this.date = date
  }

  fun getContent(): String? {
    return content
  }

  fun setContent(content: String?) {
    this.content = content
  }
}