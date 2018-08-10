package com.dsh.mydemos.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by dongshuhuan
 * createDate 2018/8/9
 * Email 785434788@qq.com
 * Description
 * Change Log
 */

public class RxJavaOperators {


    public static void main (String[] args){
        //create();//创建操作
        //filterAndDistinct();//过滤操作
        //mapAndFlatmap();//变换操作
        //mergeWithAndConcatWith();//组合操作
        //zipWith();//聚合操作
        allOperators();//所有操作符 综合运用
    }



    private static void create() {

        Observable.just("hello world");//发送一个字符串"hello world"
        Observable.just(1,2,3,4);//逐一发送1,2,3,4这四个整数

        Observable.range(0,5)
                .subscribe(new Consumer<Integer>() {
                    @Override public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });

    }

    private static void filterAndDistinct() {
        //filter 过滤
        Observable.range(0,10)
                .filter(new Predicate<Integer>() {
                    @Override public boolean test(Integer integer) throws Exception {
                        return integer%3==0;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
        //distinct 去重
        Observable.just(1,1,2,3,4,5,6,5,2)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
        //filter && distinct 去重取偶数
        Observable.just(1,1,2,3,4,5,6,5,2)
                .distinct()
                .filter(new Predicate<Integer>() {
                    @Override public boolean test(Integer integer) throws Exception {
                        return integer%2==0;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });

    }

    private static void mapAndFlatmap() {
        //map
        Observable.range(0,5)
                .map(new Function<Integer, String>() {
                    @Override public String apply(Integer integer) throws Exception {
                        return integer+"^2 = " + integer*integer ;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
        //flapmap
        Integer nums1[] = new Integer[]{1,2,3,4};
        Integer nums2[] = new Integer[]{5,6,7};
        Integer nums3[] = new Integer[]{8,9,0};
        Observable.just(nums1,nums2,nums3)
                .flatMap(new Function<Integer[], ObservableSource<Integer>>() {
                    @Override public ObservableSource<Integer> apply(Integer[] integers)
                            throws Exception {
                        return Observable.fromArray(integers);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
    }

    private static void mergeWithAndConcatWith() {
        Integer nums2[] = new Integer[]{4,5,6,7,8};
        //mergeWith
        Observable.just(1,2,3,4,5)
                .mergeWith(Observable.fromArray(nums2))
                .subscribe(new Consumer<Integer>() {
                    @Override public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
        //concatWith
        Observable.just(1,2,3,4,5)
                .concatWith(Observable.fromArray(nums2))
                .subscribe(new Consumer<Integer>() {
                    @Override public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });

    }

    private static void zipWith() {
        String names[] = new String[]{"红","橙","黄","绿","蓝","靛","紫"};
        Observable.just(1,2,3,4,5,6,7,8)
                .zipWith(Observable.fromArray(names),new BiFunction<Integer,String,String>(){
                    @Override public String apply(Integer integer, String s) throws Exception {
                        return integer+s;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    private static void allOperators() {
        Integer nums1[] = new Integer[]{1,3,7,8,9};
        Integer nums2[] = new Integer[]{3,4,5,6};
        String  names[] = new String[]{"红","橙","黄","绿","蓝","靛","紫"};
        Observable.just(nums1)
                .flatMap(new Function<Integer[], Observable<Integer>>() {
                    @Override public Observable<Integer> apply(Integer[] integers)
                            throws Exception {
                        return Observable.fromArray(integers);
                    }
                })
                .mergeWith(Observable.fromArray(nums2))
                .concatWith(Observable.just(1,2))
                .distinct()
                .filter(new Predicate<Integer>() {
                    @Override public boolean test(Integer integer) throws Exception {
                        return integer<5;
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override public String apply(Integer integer) throws Exception {
                        return integer+":";
                    }
                })
                .zipWith(Observable.fromArray(names), new BiFunction<String, String, String>() {
                    @Override public String apply(String s, String s2) throws Exception {
                        return s+s2;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

}
