package com.dsh.mydemos.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by dongshuhuan
 * createDate 2018/8/9
 * Email 785434788@qq.com
 * Description
 * Change Log
 */

public class HelloRxjava  {

    private static ArrayList<String> list = new ArrayList<>();
    public static void main (String[] args){

        initList();
        //rx1();//普通方式
        //rx2();//步骤简化
        //rx3();//函数响应式编程
        //rx4();//函数响应式编程可接收异常信息
        //rx5(list);//发送数据序列
        //rx7(list);//Disposable
        //rx6(list);//通过fromIterable操作符对其进行简化
        rx8();//Disposable

    }



    private static void initList() {
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
    }



    private static void rx1() {
        //第一步 创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello RxJava");
                e.onComplete();
            }
        });
        //创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override public void onSubscribe(Disposable d) {

            }

            @Override public void onNext(String s) {
                System.out.println(s);
            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onComplete() {
                System.out.println("接收完成");
            }
        };
        //订阅事件
        observable.subscribe(observer);
    }

    private static void rx2() {
        //步骤简化
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello Rxjava");
                e.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override public void onSubscribe(Disposable d) {

            }

            @Override public void onNext(String s) {
                System.out.println(s);
            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onComplete() {
                System.out.println("接收完成");
            }
        });

    }

    private static void rx3() {
        Observable.just("Hello Rxjava")
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    private static void rx4() {
        Observable.just("Hello World")
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                },new Consumer<Throwable>(){
                    @Override public void accept(Throwable throwable) throws Exception {

                    }
                },new Action(){
                    @Override public void run() throws Exception {
                        System.out.println("接收完成");
                    }
            });
    }




    private static void rx5(final ArrayList<String> list) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    for (String str:list){
                        e.onNext(str);
                    }
                } catch (Exception e1) {
                    e.onError(e1);
                }
                e.onComplete();

            }
        }).subscribe(new Observer<String>() {
            @Override public void onSubscribe(Disposable d) {

            }

            @Override public void onNext(String s) {
                System.out.println(s);
            }

            @Override public void onComplete() {
                System.out.println("接收完成");
            }

            @Override public void onError(Throwable e) {
                e.printStackTrace();
            }
        });

    }

    private static void rx6(ArrayList<String> list) {
        Observable.fromIterable(list)
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    private static void rx7(ArrayList<String> list) {
        Disposable disposable1 = Observable.just("Hello World")
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
        Disposable disposable2 = Observable.fromIterable(list)
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    private static void rx8() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 10; i++) {
                    System.out.println("发送"+i);
                    e.onNext(i);
                }
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;
            @Override public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override public void onNext(Integer integer) {
                System.out.println("接收"+integer);
                if (integer>4) {disposable.dispose();}
            }

            @Override public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override public void onComplete() {
                System.out.println("数据接收完成");
            }
        });
    }


}
