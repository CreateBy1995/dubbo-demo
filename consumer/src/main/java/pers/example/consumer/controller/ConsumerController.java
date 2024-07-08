package pers.example.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.example.common.dto.OrderDTO;
import pers.example.common.facade.OrderFacade;

/**
 * @Author: dongcx
 * @CreateTime: 2024-07-08
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping(value = "/consumer")
public class ConsumerController {
    @Reference(check = false, timeout = 5000, actives = 5)
    private OrderFacade orderFacade;

    @GetMapping("create")
    public Boolean create() {
        log.info("ConsumerController invoke create");
        return orderFacade.addOrder(new OrderDTO());
    }
}
