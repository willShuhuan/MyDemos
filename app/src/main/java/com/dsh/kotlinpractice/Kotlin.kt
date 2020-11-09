package com.dsh.kotlinpractice

import java.sql.DriverManager.println

/**
 * @author dongshuhuan
 * date 2020/10/27
 * version
 */

//方法可以直接写在文件中

//无返回值 java -> void,kotlin->Unit 可以省略
fun main():Unit{
  println("Hello World!")

  //变量声明
  //var 可读可写
  //Int 对应 java中int
  var age:Int = 18;

  //不可修改变量 对应java中的final关键字
  val name:String = "kotlin";
  age = 19;
//  name = "java"//编译不通过

  //对象创建
  var java: JavaUseKotlin = JavaUseKotlin();

  //类型可以省略，kotlin也是静态变量类型，但是kotlin可以推断出变量类型
  var java2 = JavaUseKotlin();


}

//有返回值、有传入参数
fun doubleNumber(x:Int):Int{
  return x*2;
}