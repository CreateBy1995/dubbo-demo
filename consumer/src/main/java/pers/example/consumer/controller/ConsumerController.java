package pers.example.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.example.common.dto.OrderDTO;
import pers.example.common.facade.SleepFacade;

import java.util.concurrent.TimeUnit;

/**
 * @Author: dongcx
 * @CreateTime: 2024-07-08
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "/consumer")
public class ConsumerController {

    /**
     * 方法级别的的配置优先级大于接口级别，此处生效的actives = 5
     */
    @Reference(check = false, timeout = 600000, actives = 2, methods =
    @Method(name = "sleep", actives = 5))
    private SleepFacade sleepFacade;

    /**
     * 客户端并发测试
     */
    @GetMapping("active")
    public void active(@RequestParam Long sleepTime) throws InterruptedException {
        log.info("consumerController start active");
        Runnable runnable = () -> {
            sleepFacade.sleep(sleepTime);
        };
        for (int i = 0; i < 3; i++) {
            new Thread(runnable).start();
        }
        TimeUnit.MILLISECONDS.sleep(200);
        sleepFacade.sleep(sleepTime);
        log.info("consumerController finish active");
    }

    @GetMapping("sleep")
    public void sleep(@RequestParam Long sleepTime) {
        log.info("consumerController start sleep");
        sleepFacade.sleep(sleepTime);
        log.info("consumerController finish sleep");
    }

    @GetMapping("async")
    public void async(@RequestParam Long sleepTime) {
        log.info("consumerController start sleep");
        sleepFacade.asyncInvoke(sleepTime);
        log.info("consumerController finish sleep");
    }

    /**
     * 正常调用
     */
    @GetMapping("normal")
    public void normal() {
        log.info("consumerController start normal");
        sleepFacade.sleep(-1L);
        log.info("consumerController finish normal");
    }
}
