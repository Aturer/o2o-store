package com.xinke.edu.o2ostore.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Component
public class ThreadFactoryBuilder {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(99)
    );
    public ThreadPoolExecutor newThread() {
        return executor;
    }
}
