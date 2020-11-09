package com.dsh.kotlinpractice.example

/**
 * @author dongshuhuan
 * date 2020/10/27
 * version
 */
class User:Any {
  var name:String? = null
  var pwd:String? = null
  var code:String? = null

  //构造函数
  constructor(){}

  constructor(name:String,pwd:String,code:String){
    this.name=name;
    this.pwd=pwd;
    this.code=code;
  }

}