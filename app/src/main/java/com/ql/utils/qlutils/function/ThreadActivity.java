package com.ql.utils.qlutils.function;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ql.utils.qlutils.R;
import com.ql.view.Layout;
import com.ql.view.base.QLBindLayoutActivity;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Layout(R.layout.activity_thread)
public class ThreadActivity extends QLBindLayoutActivity {


    /**
     * 参数名	作用
     * corePoolSize	核心线程池大小
     * maximumPoolSize	最大线程池大小
     * keepAliveTime	线程池中超过corePoolSize数目的空闲线程最大存活时间；可以allowCoreThreadTimeOut(true)使得核心线程有效时间
     * TimeUnit	keepAliveTime时间单位
     * workQueue	阻塞任务队列
     * threadFactory	新建线程工厂
     * RejectedExecutionHandler	当提交任务数超过maxmumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
     */
    private void createThread() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return null;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });

    }
}
