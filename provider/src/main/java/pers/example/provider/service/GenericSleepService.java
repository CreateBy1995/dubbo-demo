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
public class GenericSleepService implements GenericSleepFacade {
    @Override
    public String sleep(Long milliseconds) {
        log.info("GenericSleepService start sleep, milliseconds:{}", milliseconds);
        if (milliseconds.compareTo(0L) > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException e) {
                log.error("GenericSleepService error", e);
            }
        }
        log.info("GenericSleepService finish sleep, milliseconds:{}", milliseconds);
        return "sleep" + milliseconds;
    }
}
