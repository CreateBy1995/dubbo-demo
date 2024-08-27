package pers.example.provider.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: dongcx
 * @CreateTime: 2024-07-08
 * @Description:
 */
public interface GenericSleepFacade {
    String sleep(Long milliseconds);
}
