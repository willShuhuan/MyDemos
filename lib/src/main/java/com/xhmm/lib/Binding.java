package com.xhmm.lib;

import android.app.Activity;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author dongshuhuan
 * date 2020/11/9
 * version
 */
public class Binding {

    private static final String TAG = "Binding";
    
    public static void bind(Activity activity){

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
