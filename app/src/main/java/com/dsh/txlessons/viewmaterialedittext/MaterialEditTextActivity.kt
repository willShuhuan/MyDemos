package com.dsh.txlessons.viewmaterialedittext

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dsh.mydemos.R
import kotlinx.android.synthetic.main.activity_material_edit_text.met

class MaterialEditTextActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_material_edit_text)
    met.postDelayed(Runnable {
      met.useFloatingLabel = true
    },3000)

  }
}