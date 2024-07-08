package pers.example.provider.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import pers.example.common.dto.OrderDTO;
import pers.example.common.facade.OrderFacade;

/**
 * @Author: dongcx
 * @CreateTime: 2024-07-08
 * @Description:
 */
@Slf4j
@Service
public class OrderService implements OrderFacade {
    @Override
    public Boolean addOrder(OrderDTO orderDTO) {
        log.info("OrderService execute addOrder");
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteOrder(OrderDTO orderDTO) {
        log.info("OrderService execute deleteOrder");
        return Boolean.TRUE;
    }
}
