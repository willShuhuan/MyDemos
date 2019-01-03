package com.dsh.mydemos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dsh.mydemos.R;
import com.dsh.mydemos.base.BaseActivity;

import com.dsh.mydemos.base.DBaseActivity;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/27.
 */

public class RxJavaActivity extends DBaseActivity {

    @BindView(R.id.io)
    Button mIo;
    @BindView(R.id.newThread)
    Button mNewThread;
    @BindView(R.id.trampoline)
    Button mTrampoline;
    @BindView(R.id.single)
    Button mSingle;
    @BindView(R.id.tv_main)
    TextView mTvMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        setListener();
        initData();

    }



    @Override
    public void initView() {

    }

    @Override
    public void setListener() {
        mIo.setOnClickListener(this);
        mNewThread.setOnClickListener(this);
        mTrampoline.setOnClickListener(this);
        mSingle.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    private void IOSchedulers() {
        Scheduler.Worker worker = Schedulers.io().createWorker();
        worker.schedule(new Runnable() {
            @Override
            public void run() {
                //保存图片操作
                System.out.println("保存图片");
            }
        }, 500, TimeUnit.MILLISECONDS);//延迟500ms执行
        //1.io()//非阻塞I/O操作，图片保存等
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 5; i++) {
                    System.out.println("开始保存:" + Thread.currentThread().getName() + "---->" + "发送:" + i);
                    Thread.sleep(1000);
                    e.onNext(i);
                }
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())   //设置可观察对象在Schedulers.io()的线程中发射数据
                .observeOn(
                        AndroidSchedulers.mainThread())//设置观察者在AndroidSchedulers.mainThread()
                // 的线程中处理数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer i) throws Exception {
                        mTvMain.setText("io\n"+Thread.currentThread().getName() + "接收完成进度，完成了--->"+ i);//io线程通知textView更新，类似handler

                    }
                });


    }

    private void newThreadSchedulers() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("发射线程:" + Thread.currentThread().getName() + "---->" + "发射:" + i);
                            Thread.sleep(1000);
                            e.onNext(i);
                        }
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())//设置可观察对象在Schedulers.io()的线程中发射数据
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer i) throws Exception {
                        System.out.println("处理线程:" + Thread.currentThread().getName() + "---->" + "处理:" + i);
                        return i;
                    }
                })
                .subscribeOn(Schedulers.newThread())//指定map操作符在Schedulers.newThread()的线程中处理数据
                .observeOn(AndroidSchedulers.mainThread())//设置观察者在AndroidSchedulers.mainThread()的线程中处理数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer i) throws Exception {
                        mTvMain.setText("newThread\n"+Thread.currentThread().getName() + "接收完成进度，完成了--->"+ i);//io线程通知textView更新，类似handler
                    }
                });


    }

    private void trampolineSchedulers() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("发射线程:" + Thread.currentThread().getName() + "---->" + "发射:" + i);
                            Thread.sleep(1000);
                            e.onNext(i);
                        }
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())//设置可观察对象在Schedulers.io()的线程中发射数据
                .observeOn(Schedulers.trampoline())//设置观察者在当前线程中j接收数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer i) throws Exception {
                        Thread.sleep(2000);//休息2s后再处理数据
                        System.out.println("接收线程:trampoline" + Thread.currentThread().getName() + "---->" + "接收:" + i);
                    }
                });


    }

    private void singleSchedulers() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i = 0; i < 3; i++) {
                            System.out.println("发射线程:" + Thread.currentThread().getName() + "---->" + "发射:" + i);
                            Thread.sleep(1000);
                            e.onNext(i);
                        }
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.single())//设置可观察对象在Schedulers.single()的线程中发射数据
                .observeOn(Schedulers.single())//指定map操作符在Schedulers.single()的线程中处理数据
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer i) throws Exception {
                        System.out.println("处理线程:" + Thread.currentThread().getName() + "---->" + "处理:" + i);
                        return i;
                    }
                })
                .observeOn(Schedulers.single())//设置观察者在Schedulers.single()的线程中j接收数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer i) throws Exception {
                        System.out.println("接收线程:single" + Thread.currentThread().getName() + "---->" + "接收:" + i);
                    }
                });


    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
        		case R.id.io:
                    IOSchedulers();
        			break;
                case R.id.newThread:
                    newThreadSchedulers();
                    break;
                case R.id.trampoline:
                    trampolineSchedulers();
                    break;
                case R.id.single:
                    singleSchedulers();
                    break;

        		default:
        			break;
        		}
    }
}
