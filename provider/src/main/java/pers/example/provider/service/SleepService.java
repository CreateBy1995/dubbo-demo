package pers.example.provider.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import pers.example.common.facade.SleepFacade;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dongcx
 * @CreateTime: 2024-07-08
 * @Description:
 */
@Slf4j
@Service
public class SleepService implements SleepFacade {
    @Override
    public void sleep(Long milliseconds) {
        log.info("sleepService start sleep, milliseconds:{}", milliseconds);
        if (milliseconds.compareTo(0L) > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException e) {
                log.error("sleepService error", e);
            }
        }
        log.info("sleepService finish sleep, milliseconds:{}", milliseconds);

    }

    @Override
    public CompletableFuture<String> asyncInvoke(Long milliseconds) {
        return CompletableFuture.supplyAsync(() -> {
            if (milliseconds.compareTo(0L) > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(milliseconds);
                } catch (InterruptedException e) {
                    log.error("sleepService error", e);
                }
            }
            return "done";
        });
    }
}
