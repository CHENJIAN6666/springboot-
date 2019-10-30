package com.newlife.s4.common.constants;

/**
 * 描述: 订单状态枚举
 *
 * @author withqianqian@163.com
 * @create 2019-09-06 15:58
 */
public enum  OrderStatusEnum {
    NOT_PAY(0),PAID(1);
    private Integer status;

    OrderStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
