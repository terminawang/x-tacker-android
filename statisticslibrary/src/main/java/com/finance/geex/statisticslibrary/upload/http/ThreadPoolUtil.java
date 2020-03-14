package com.finance.geex.statisticslibrary.upload.http;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created on 2019/8/21 13:15.
 * 线程池工具类
 * @author Geex302
 */
public class ThreadPoolUtil {

    private static ExecutorService singleThreadPool; //单线程,专门负责埋点数据的上传
    public static boolean threadPoolIsWork; //线程池是否正在工作（实际上当上传任务并且删除数据库后才认为线程池已没有工作）,用于埋点线程
    private static ExecutorService fixedThreadPool; //普通的调用其他接口是用到的线程池
    public static boolean fixedThreadPoolIsWork; //网络日志、错误日志是否在上传

    //埋点上传时 线程池
    public static final int GEEX_EVENTS_THREAD_POOL = 1;
    //普通接口上传时 线程池
    public static final int GEEX_NORMAL_THREAD_POOL = 2;


    /**
     * 无返回值
     * @param runnable
     */
    public synchronized  static void execute(int type,Runnable runnable){
        if(type == GEEX_EVENTS_THREAD_POOL){
            getSingleThreadPool().execute(runnable);
        }else if(type == GEEX_NORMAL_THREAD_POOL){
            getFixedThreadPool().execute(runnable);
        }

    }

    /**
     * 返回值
     * @param callable
     */
    public synchronized static <T> Future<T> submit(int type,Callable<T> callable){
        Future<T> submit = null;
        if(type == GEEX_EVENTS_THREAD_POOL){
            submit = getSingleThreadPool().submit(callable);
        }else if(type == GEEX_NORMAL_THREAD_POOL){
            submit = getFixedThreadPool().submit(callable);
        }
        return submit;
    }


    /**
     * 用于埋点网络线程池
     * @return 线程池对象
     */
    private static ExecutorService getSingleThreadPool() {
        if (singleThreadPool != null) {
            return singleThreadPool;
        } else {
            synchronized (ThreadPoolUtil.class) {


                if (singleThreadPool == null) {

                    /**
                     * corePoolSize:核心线程数
                     * maximumPoolSize：线程池所容纳最大线程数(workQueue队列满了之后才开启)
                     * keepAliveTime：非核心线程闲置时间超时时长
                     * unit：keepAliveTime的单位
                     * workQueue：等待队列，存储还未执行的任务
                     * threadFactory：线程创建的工厂
                     * handler：异常处理机制
                     *
                     */
//                    executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
//                            KEEP_ALIVE, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20),
//                            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

//                    threadPool = new ThreadPoolExecutor(8, 16, 60, TimeUnit.SECONDS,
//                            new LinkedBlockingQueue<>(32), new ThreadPoolExecutor.CallerRunsPolicy());

                    //暂时创建一个核心线程，不用管线程同步问题(始终只有一个线程工作)
                    singleThreadPool = Executors.newSingleThreadExecutor();
                }
                return singleThreadPool;
            }
        }
    }

    /**
     * 用于其他网络请求的线程池
     * @return
     */
    private static ExecutorService getFixedThreadPool() {
        if (fixedThreadPool != null) {
            return fixedThreadPool;
        } else {
            synchronized (ThreadPoolUtil.class) {


                if (fixedThreadPool == null) {
                    fixedThreadPool = Executors.newFixedThreadPool(3);
                }
                return fixedThreadPool;
            }
        }
    }


}
