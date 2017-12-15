package com.ql.utils.qlutils;

import org.junit.Test;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testMapIntegerKey() {
        ArrayList a;
    }

    @Test
    public void testThread() {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 10, 60, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                System.out.println("创建线程 --->" + t.getName() + ", threadId = " + t.getId());
                return t;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });

        System.out.println("************************START*****************************");
        for (int i = 0; i < 10000; i++) {
            final int finalI = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    long id = Thread.currentThread().getId();
                    String name = Thread.currentThread().getName();
                    System.out.println("当前线程 --->" + id + ", " + name);
                    System.out.println("内容 --->" + finalI);
                    if (finalI == 9999){
                        System.out.println("终于都输出完了");
                    }
                }

            };
            threadPoolExecutor.execute(runnable);
        }

        System.out.println("************************END*****************************");
    }
}