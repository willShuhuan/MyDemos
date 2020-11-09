package com.dsh.lib_reflection;

import android.app.Activity;
import android.view.View;
import java.lang.reflect.Field;

/**
 * @author dongshuhuan
 * date 2020/11/9
 * version
 */
public class Binding {
    public static void bind(Activity activity){
        //通过反射获取到activity内的view
        for (Field field : activity.getClass().getDeclaredFields()) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView!=null){
                try {
                    //通过反射扩大访问权限->public
                    field.setAccessible(true);
                    View view = activity.findViewById(bindView.value());
                    field.set(activity,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
