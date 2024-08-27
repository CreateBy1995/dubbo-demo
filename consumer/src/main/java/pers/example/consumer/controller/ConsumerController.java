package pers.example.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.service.GenericService;
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

    /**
     * 泛化调用
     */
    @GetMapping("generic")
    public void generic() {
        // 创建服务引用配置
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("springboot-dubbo-provicer"));
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setPort(8848);
        registryConfig.setAddress("39.104.70.173");
        registryConfig.setProtocol("nacos");
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface("pers.example.provider.service.GenericSleepFacade");  // 设置接口名
        referenceConfig.setGeneric("true");           // 启用泛化调用

        // 获取泛化服务实例
        GenericService genericService = referenceConfig.get();

        // 调用服务
        // 参数类型数组，方法的参数类型
        String[] parameterTypes = new String[]{"java.lang.Long"};

        // 参数值数组，传入的实际参数
        Object[] args = new Object[]{1000};

        log.info("consumerController start generic");
        Object result = genericService.$invoke("sleep", parameterTypes, args);
        log.info("consumerController finish generic, result:{}", result);
    }
}
