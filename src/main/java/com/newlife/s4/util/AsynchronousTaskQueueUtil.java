package com.newlife.s4.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * 描述: 接受异步任务队列 工具类
 *
 * @author withqianqian@163.com
 * @create 2018-10-17 11:56
 */
public class AsynchronousTaskQueueUtil {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("generate poster task-%d").build();
    private static ExecutorService pool = Executors.newFixedThreadPool(2,threadFactory);

    /**
     * 提交任务
     *
     * @param runnable
     */
    public static void execute(Runnable runnable){
        pool.execute(runnable);
    }

    /**
     * 获取返回值
     *
     * @param runnable
     */
    public static Future getFuture(Runnable runnable){
       return pool.submit(runnable);
    }
}
