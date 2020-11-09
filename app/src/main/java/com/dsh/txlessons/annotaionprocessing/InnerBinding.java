package com.dsh.txlessons.annotaionprocessing;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import com.dsh.mydemos.R;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author dongshuhuan
 * date 2020/11/9
 * version
 */
public class InnerBinding {

    private static final String TAG = "InnerBinding";
    
    public static void bind(AptActivity activity){
        //1
        //activity.textView = activity.findViewById(R.id.textView);

        //2
        //通过反射获取到activity内的view
        //for (Field field : activity.getClass().getDeclaredFields()) {
        //    BindView bindView = field.getAnnotation(BindView.class);
        //    if (bindView!=null){
        //        try {
        //            View view = activity.findViewById(bindView.value());
        //            field.set(activity,view);
        //        } catch (IllegalAccessException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //}

        //3
        String bindingClassName = activity.getClass().getCanonicalName()+"Binding";//com.dsh.txlessons.annotaionprocessing.AptActivityBinding

        try {
            Class bindingClass = Class.forName(bindingClassName);
            Constructor constructor = bindingClass.getDeclaredConstructor(activity.getClass());
            constructor.newInstance(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
