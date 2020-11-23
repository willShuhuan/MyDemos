package com.dsh.mydemos.app;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;
import android.view.Choreographer;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.squareup.leakcanary.LeakCanary;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * @author dongshuhuan
 * date 2020/11/19
 * version
 */
public class HotfixApp extends Application {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //加载热更新插件
        //全量替换
        //loadHotFix();
        //增量替换
        loadDexFix();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        BlockCanary.install(this,new BlockCanaryContext()).start();
        loggingPrint();

    }

    private void loggingPrint() {
        Looper.getMainLooper().setMessageLogging(new Printer() {
            boolean start = true;
            long startTime = 0;
            @Override
            public void println(String x) {
                if (start){
                    start = false;
                    startTime = System.currentTimeMillis();
                }else {
                    start = true;
                    //Log.e("Monitor", "dispatchMessage 耗时: "+(System.currentTimeMillis()-startTime) );
                }
            }
        });

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            long lastTimeNano = 0;
            @Override public void doFrame(long frameTimeNanos) {
                //两次绘制之间的耗时
                //Log.e("frame", "frameTimeNanos: "+(frameTimeNanos - lastTimeNano)/(1024*1024));//16-17ms之间
                lastTimeNano = frameTimeNanos;
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }



    /**
     * 增量替换，替换dex
     */
    private void loadDexFix() {
        //1.复制文件
        File apk = new File(getCacheDir() + "hotfix.dex");

        //2.替换classLoader，里面的dex文件
        try {
            ClassLoader originalLoader = getClassLoader();
            DexClassLoader
                    classLoader = new DexClassLoader(apk.getPath(),getCacheDir().getPath(),null,null);
            Class loaderClass = BaseDexClassLoader.class;
            Field pathListField = loaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathListObject = pathListField.get(classLoader);

            Class pathListClass = pathListObject.getClass();
            Field dexElementsField = pathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            Object dexElementsObject = dexElementsField.get(pathListObject);

            Object originalPathListObject = pathListField.get(originalLoader);
            Object originalDexElementsObject = dexElementsField.get(originalPathListObject);

            //数组操作，把最新的补丁dex文件插入到最前面
            int oldLength = Array.getLength(originalDexElementsObject);
            int newLength = Array.getLength(dexElementsObject);
            Object concatDexElementsObject = Array.newInstance(dexElementsObject.getClass().getComponentType(), oldLength + newLength);
            for (int i = 0; i < newLength; i++) {
                Array.set(concatDexElementsObject, i, Array.get(dexElementsObject, i));
            }
            for (int i = 0; i < oldLength; i++) {
                Array.set(concatDexElementsObject, newLength + i, Array.get(originalDexElementsObject, i));
            }
            dexElementsField.set(originalPathListObject, concatDexElementsObject);

            //全量替换伪代码 -> originalLoader.pathList.dexElements = classLoader.pathList.dexElement
            //增量替换伪代码 -> originalLoader.pathList.dexElements += classLoader.pathList.dexElement
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 全量更新，整包替换
     * 更新apk
     */
    private void loadHotFix() {
        //1.复制文件
        File apk = new File(getCacheDir() + "hotfix.apk");
        if (!apk.exists()) {
            try (Source source = Okio.source(getAssets().open("apk/hotfix.apk"));
                 BufferedSink sink = Okio.buffer(Okio.sink(apk));) {
                sink.writeAll(source);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //2.替换classLoader，里面的dex文件
        try {
            ClassLoader originalLoader = getClassLoader();
            DexClassLoader
                    classLoader = new DexClassLoader(apk.getPath(),getCacheDir().getPath(),null,null);
            Class loaderClass = BaseDexClassLoader.class;
            Field pathListField = loaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathListObject = pathListField.get(classLoader);

            Class pathListClass = pathListObject.getClass();
            Field dexElementsField = pathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            Object dexElementsObject = dexElementsField.get(pathListObject);

            Object originalPathListObject = pathListField.get(originalLoader);
            dexElementsField.set(originalPathListObject,dexElementsObject);

            //全量替换伪代码 -> originalLoader.pathList.dexElements = classLoader.pathList.dexElement
            //增量替换伪代码 -> originalLoader.pathList.dexElements += classLoader.pathList.dexElement
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
