package com.dsh.txlessons.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dongshuhuan
 * date 2020/11/2
 * version
 */
public class Main {
    public static void main(String[] args) {
        //thread();
        //runnable();
        //threadFactory();
        //executor();
        //callable();
        //runSynchronized1Demo();
        //runSynchronized2Demo();
        runSynchronized3Demo();
        //runReadWriteLockDemo();
    }



    /**
     * 使用 Thread 类来定义工作
     * （不推荐）
     */
    static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread started!");
            }
        };
        thread.start();
    }

    /**
     * 这种写法可以重用runnable
     * 不推荐（线程管理性较低）
     */
    private static void runnable() {
        Runnable runnable = new Runnable() {
            @Override public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * 工厂方式创建
     */
    private static void threadFactory() {
        ThreadFactory factory = new ThreadFactory() {
            AtomicInteger count = new AtomicInteger(0); // int

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Thread-" + count.incrementAndGet()); // ++count
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " started!");
            }
        };

        Thread thread = factory.newThread(runnable);
        thread.start();
        Thread thread1 = factory.newThread(runnable);
        thread1.start();
    }

    /**
     * 线程池
     */
    static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };

        Executor executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        // 参数1 核心线程数 核心线程不会被回收，如100个线程 等执行完毕后  会保留5个
        // 参数2 最大线程数 若超出最大值，则其他线程会排队
        // 参数3 线程等待时间（也是等待被回收的时间）
        // 参数4 时间单位
        // 参数5
        ExecutorService myExecutor = new ThreadPoolExecutor(5, 100,
                5, TimeUnit.MINUTES, new SynchronousQueue<Runnable>());

        myExecutor.execute(runnable);

        //单个线程 用的很少
        //Executors.newSingleThreadExecutor();
        //固定线程数量的线程池
        //Executors.newFixedThreadPool(20);
        //延迟执行
        //Executors.newScheduledThreadPool(20);
        //Executors.newSingleThreadScheduledExecutor();

    }

    /**
     * 可理解为相当于一个有返回值的runnable
     */
    static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Done!";
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable);
        while (true) {
            //future.isDone()检查线程内的事是否做完了
            if (future.isDone()) {
                try {
                    String result = future.get();
                    System.out.println("result: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    static void runSynchronized1Demo() {
        new Synchronized1Demo().runTest();
    }

    static void runSynchronized2Demo() {
        new Synchronized2Demo().runTest();
    }

    static void runSynchronized3Demo() {
        new Synchronized3Demo().runTest();
    }

    static void runReadWriteLockDemo() {
        new ReadWriteLockDemo().runTest();
    }

}
