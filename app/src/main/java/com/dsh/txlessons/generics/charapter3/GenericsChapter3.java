package com.dsh.txlessons.generics.charapter3;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.TextView;
import com.dsh.mydemos.R;
import com.dsh.txlessons.generics.basicuse.fruit.Apple;
import java.util.List;

/**
 * 泛型方法和类型推断
 * @author dongshuhuan
 * date 2020/11/13
 * version
 */
class GenericsChapter3 {

    public static void main(String[] args) {
        //1. 泛型类型的实例化
        //Shop<Phone>尖括号内的Phone就是泛型类型的实例化
        Shop<Phone> phoneShop = null;
        AndroidPhone androidPhone = phoneShop.tradeIn(new AndroidPhone(), 100f);
        //完整写法,因为有类型推断，所以尖括号<>可以省略
        AndroidPhone androidPhone2 = phoneShop.<AndroidPhone>tradeIn(new AndroidPhone(), 100f);

        //2. 泛型方法的实例化，
        // 调用方法返回尖括号内的WinPhone就是泛型方法的实例化
        //3. 类型推断，即<WinPhone>可以省略
        WinPhone winPhone = phoneShop.<WinPhone>tradeIn(new WinPhone(), 200f);

        //4.如果是无参方法,通过等号左边的值推断出返回值
        ApplePhone applePhone = phoneShop.take();
        ApplePhone applePhone2 = phoneShop.<ApplePhone>take();//完整写法
        ApplePhone applePhone3 = phoneShop.<WinPhone>take();//类型错误,编译错误

        //安卓开发中的泛型方法 <TextView>泛型可以省略
        //TextView textView = <TextView>findViewById(R.id.et_name);
        //public <T extends View > T findViewById(@IdRes int id) {
        //    return this.getDelegate().findViewById(id);
        //}

    }

    //泛型方法调用与对象本身无关，泛型方法不局限于非静态方法，静态方法也可以写成泛型方法
    static <C> void filter (List<C> list){
        for (C c:list) {
            //do something
        }
    }

}
