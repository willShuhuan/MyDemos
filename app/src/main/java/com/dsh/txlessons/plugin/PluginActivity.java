package com.dsh.txlessons.plugin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dsh.mydemos.R;
import com.dsh.txlessons.plugin.utils.Plugin;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class PluginActivity extends AppCompatActivity {

    private static final String TAG = "PluginActivity";

    @BindView(R.id.loadPlugin) Button loadPlugin;
    @BindView(R.id.showText) Button showText;
    @BindView(R.id.hotFix) Button hotFix;
    @BindView(R.id.text) TextView textView;
    @BindView(R.id.tv_hotfix) TextView tv_hotfix;
    File apk ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        ButterKnife.bind(this);
        apk = new File(getCacheDir() + "hotfix.dex");

    }

    @OnClick({R.id.loadPlugin,R.id.hotFix,R.id.showText,R.id.removehotFix,R.id.killSelf})
    public void onViewClicked(View view) {
        switch(view.getId()){
            case R.id.loadPlugin:
                loadPlugin();
                break;
            case R.id.showText:
                tv_hotfix.setText(Plugin.getTitle());
                break;
            case R.id.hotFix:
                //Log.d(TAG, "onViewClicked: "+getClassLoader().getClass().getName());
                loadHotFix();
                break;
            case R.id.removehotFix:
                if (apk.exists()){
                    apk.delete();
                }
                break;
            case R.id.killSelf:
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
             default:
                break;

        }
    }

    private void loadHotFix() {
        //1.复制文件
        File apk = new File(getCacheDir() + "hotfix.dex");
        if (!apk.exists()) {
            try (Source source = Okio.source(getAssets().open("apk/hotfix.dex"));
                 BufferedSink sink = Okio.buffer(Okio.sink(apk));) {
                sink.writeAll(source);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ////2.替换classLoader，里面的dex文件
        //try {
        //    ClassLoader originalLoader = getClassLoader();
        //    DexClassLoader classLoader = new DexClassLoader(apk.getPath(),getCacheDir().getPath(),null,null);
        //    Class loaderClass = BaseDexClassLoader.class;
        //    Field pathListField = loaderClass.getDeclaredField("pathList");
        //    pathListField.setAccessible(true);
        //    Object pathListObject = pathListField.get(classLoader);
        //
        //    Class pathListClass = pathListObject.getClass();
        //    Field dexElementsField = pathListClass.getDeclaredField("dexElements");
        //    dexElementsField.setAccessible(true);
        //    Object dexElementsObject = dexElementsField.get(pathListObject);
        //
        //    Object originalPathListObject = pathListField.get(originalLoader);
        //    dexElementsField.set(originalPathListObject,dexElementsObject);
        //
        //    //originalLoader.pathList.dexElements = classLoader.pathList.dexElement
        //} catch (NoSuchFieldException e) {
        //    e.printStackTrace();
        //} catch (IllegalAccessException e) {
        //    e.printStackTrace();
        //}
    }

    private void loadPlugin() {
        //使用反射
        try {
            // ------------------  基础使用  ------------------
            ////1.拿到类
            //Class utilsClass = Class.forName("com.dsh.txlessons.plugin.utils.Utils");
            ////2.拿到第一个构造方法
            //Constructor utilsConstructor = utilsClass.getDeclaredConstructors()[0];
            ////3. 扩大访问性 默认default包权限 -> public
            //utilsConstructor.setAccessible(true);
            ////4. 创建类实例（通过构造方法实例）
            //Object utils=utilsConstructor.newInstance();
            ////5. 获取方法
            //Method shoutMethod = utilsClass.getDeclaredMethod("shout");
            ////6. 扩大方法访问权限
            //shoutMethod.setAccessible(true);
            ////7. 方法执行
            //shoutMethod.invoke(utils);

            //------------------  插件化使用  ------------------
            //1. 将插件apk复制到缓存目录
            File apk = new File(getCacheDir() + "plugin.apk");
            if (!apk.exists()) {
                try (Source source = Okio.source(getAssets().open("apk/pluginapp-debug.apk"));
                     BufferedSink sink = Okio.buffer(Okio.sink(apk));) {
                    sink.writeAll(source);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //2. 创建类加载器实例
            DexClassLoader classLoader =
                    new DexClassLoader(apk.getPath(), getCacheDir().getPath(), null, null);

            if (apk.exists()) {
                //3. 反射调用
                //1.拿到类
                Class utilsClass = classLoader.loadClass("com.dsh.pluginapp.utils.Utils");
                //2.拿到第一个构造方法
                Constructor utilsConstructor = utilsClass.getDeclaredConstructors()[0];
                //3. 扩大访问性 默认default包权限 -> public
                utilsConstructor.setAccessible(true);
                //4. 创建类实例（通过构造方法实例）
                Object utils = utilsConstructor.newInstance();
                //5. 获取方法
                Method shoutMethod = utilsClass.getDeclaredMethod("shout");
                //6. 扩大方法访问权限
                shoutMethod.setAccessible(true);
                //7. 方法执行
                shoutMethod.invoke(utils);

                //Intent intent = new Intent();
                //intent.setClassName("com.dsh.pluginapp", "com.dsh.pluginapp.PluginMainActivity");
                //startActivity(intent);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}