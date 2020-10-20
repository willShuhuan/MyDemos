package com.dsh.mydemos.mvp.prestener;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import com.dsh.mydemos.mvp.view.IBaseView;
import java.lang.ref.WeakReference;

/**
 * @author dongshuhuan
 * date 2020/10/19
 * version
 */
public class BasePresenter <T extends IBaseView> implements LifecycleObserver {
    public WeakReference<T> mView;
    /**
     * 绑定
     * @param view
     */
    public void attachView(T view){
        this.mView = new WeakReference<>(view);
    }

    /**
     *
     */
    public void detachView() {
        if (mView!=null){
            mView.clear();
            mView = null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void oncreate(LifecycleOwner owner){
        //当activity生命周期变化时 就会执行这里面的方法

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart(LifecycleOwner owner){
        //当activity生命周期变化时 就会执行这里面的方法

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(LifecycleOwner owner){
        //当activity生命周期变化时 就会执行这里面的方法

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause(LifecycleOwner owner){
        //当activity生命周期变化时 就会执行这里面的方法

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop(LifecycleOwner owner){
        //当activity生命周期变化时 就会执行这里面的方法

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner){
        //当activity生命周期变化时 就会执行这里面的方法

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner){
        //当activity生命周期变化时 就会执行这里面的方法

    }


}
