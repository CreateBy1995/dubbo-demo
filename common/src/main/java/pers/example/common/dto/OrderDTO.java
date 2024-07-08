package pers.example.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dongcx
 * @CreateTime: 2024-07-08
 * @Description:
 */
@Data
public class OrderDTO implements Serializable {
    private Long orderId;
    private Long userId;
}
