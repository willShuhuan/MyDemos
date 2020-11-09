package com.dsh.kotlinpractice.example

import android.content.Intent
import android.graphics.Canvas.EdgeType
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dsh.kotlinpractice.example.lesson.LessonActivity
import com.dsh.kotlinpractice.example.utils.CacheUtils
import com.dsh.mydemos.R
import kotlin.reflect.KClass

/**
 * @author dongshuhuan
 * date 2020/10/27
 * version
 */
//冒号继承 逗号分割实现
class MainActivity : AppCompatActivity(), View.OnClickListener{

  //私有常量
  private val userName:String = "name";
  private val passWord:String = "pwd";

//  private val et_username:EditText=null;//编译错误（Null can not be a value of a non-null type EditText），kotlin空安全设计
//  private var et_username:EditText?=null;//加？表示变量可能为null，不加默认表示不可空
//  private var et_pwd:EditText?=null;

  //lateinit 延迟初始化
  private lateinit var et_username:EditText;
  private lateinit var et_pwd:EditText;
  private lateinit var et_code:EditText;
  private lateinit var code_view:CodeView;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.layout_kt_main);

    initView();
    initData();

  }

  private fun initView() {
    et_username = findViewById(R.id.et_name);
    et_pwd = findViewById(R.id.et_pwd);
    et_code = findViewById(R.id.et_code);
    //平台类型： Button -> java平台在类型后面面加上一个感叹号的类型是「平台类型」
    val btnLogin = findViewById<Button>(R.id.btn_login);
    btnLogin.setOnClickListener(this);
//    et_username?.setText("dsh");//?安全调用符
//    et_username!!.setText("dsh");//!! 强制调用符
    et_username.setText("dsh");//由于声明时使用lateinit关键字，这里不需要加调用符
  }

  private fun initData() {
    //java中使用NonNull注解表示不可空，Nullable表示可空
    var user:User?=null
    var user2:User?=User();
  }



  override fun onClick(v: View?) {
    if (v is CodeView){
      v.updateCode();
    }else if (v is Button){
      login();
    }
  }

  private fun login() {
    val name = et_username.text.toString();
    val pwd = et_pwd.text.toString();
    val code = et_code.text.toString();

    val user = User(name,pwd,code);
    if (verify(user)) {
//      CacheUtils.save("name",user.name);
//      CacheUtils.save("pwd",user.pwd);
      //如果ContentActivity是java编写  需要写成ContentActivity::class.java
      var activity = LessonActivity::class.java;
//      var activity = ContentActivity().javaClass;
      startActivity(Intent(this,activity))
    }else{
      Toast.makeText(this,"请输入合法的用户信息",Toast.LENGTH_SHORT).show();
    }



  }

  private fun verify(user: User):Boolean {
    return !TextUtils.isEmpty(user.name)&&!TextUtils.isEmpty(user.pwd)&&!TextUtils.isEmpty(user.code)
  }

}