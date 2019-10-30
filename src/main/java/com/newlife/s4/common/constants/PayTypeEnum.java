package com.newlife.s4.common.constants;

/**
 * 描述: 支付类型枚举
 *
 * @author withqianqian@163.com
 * @create 2019-09-06 15:58
 */
public enum PayTypeEnum {
    WECHAT(1),ZHI_FU_BAO(2),OTHER(3),REMAINING_SUM(4);
    private Integer status;

    PayTypeEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
