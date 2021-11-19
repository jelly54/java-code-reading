package com.jelly.thread;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhangguodong
 * @since 2021/10/29 13:33
 */
//@Configuration
//@EnableAsync
//@ConditionalOnProperty(prefix = "async.executor", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ExecutorConfig {
//    private static final Logger log = LoggerFactory.getLogger(ExecutorConfig.class);

//    @Value("${async.executor.thread.core_pool_size:5}")
    private int corePoolSize;
//    @Value("${async.executor.thread.max_pool_size:10}")
    private int maxPoolSize;
//    @Value("${async.executor.thread.queue_capacity:200}")
    private int queueCapacity;
//    @Value("${async.executor.thread.keep_alive_seconds:60}")
    private int keepAliveSeconds;
//    @Value("${async.executor.thread.name.prefix:async_}")
    private String namePrefix;

//    @Bean(name = "asyncServiceExecutor")
//    public Executor asyncServiceExecutor() {
//        if (log.isDebugEnabled()) {
//            log.debug("inject asyncServiceExecutor bean: {}", this);
//        }
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(corePoolSize);
//        executor.setMaxPoolSize(maxPoolSize);
//        executor.setQueueCapacity(queueCapacity);
//        executor.setKeepAliveSeconds(keepAliveSeconds);
//        executor.setThreadNamePrefix(namePrefix);
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        executor.setAwaitTerminationSeconds(60);
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.initialize();
//        return executor;
//    }

    @Override
    public String toString() {
        return "{" +
                "core_pool_size=" + corePoolSize +
                ", max_pool_size=" + maxPoolSize +
                ", queue_capacity=" + queueCapacity +
                ", keep_alive_seconds=" + keepAliveSeconds +
                ", name_prefix=" + namePrefix +
                '}';
    }
}
