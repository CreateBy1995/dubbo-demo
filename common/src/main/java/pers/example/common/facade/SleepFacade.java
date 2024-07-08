package pers.example.common.facade;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dongcx
 * @CreateTime: 2024-07-08
 * @Description:
 */
public interface SleepFacade {
    void sleep(Long milliseconds);

    CompletableFuture<String> asyncInvoke(Long milliseconds);
}
