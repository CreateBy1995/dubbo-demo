package pers.example.common.facade;

import pers.example.common.dto.OrderDTO;

/**
 * @Author: dongcx
 * @CreateTime: 2024-07-08
 * @Description:
 */
public interface OrderFacade {
    Boolean addOrder(OrderDTO orderDTO);

    Boolean deleteOrder(OrderDTO orderDTO);
}
