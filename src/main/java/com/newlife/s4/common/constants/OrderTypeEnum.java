package com.newlife.s4.common.constants;

/**
 * 描述: 订单类型枚举
 *
 * @author withqianqian@163.com
 * @create 2019-09-06 15:58
 */
public enum OrderTypeEnum {
    ONLINE(0),OFFLINE(1);
    private Integer status;

    OrderTypeEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
