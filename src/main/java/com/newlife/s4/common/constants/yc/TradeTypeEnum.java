package com.newlife.s4.common.constants.yc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.s4.common.converter.IBaseEnum;
import com.newlife.s4.util.StringTools;

/**
 * 充电类型：状态
 *
 * @author hxl  充电类型:0:扫码充电;1:密码充电
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TradeTypeEnum implements IBaseEnum {
    /**
     * 消费
     */
    Consume(1, "消费"),
    /**
     * 充值
     */
    Recharge(2, "充值"),
    /**
     * 充值
     */
    Withdraw(3, "提现"),
    /**ive
     * 充值
     */
    Give(4, "赠送");

    private Integer id;

    private String name;

    TradeTypeEnum() {

    }

    TradeTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String toString() {
        return String.valueOf(this.id);
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Integer getOrderState(String desc) {
        if (!StringTools.isNullOrEmpty(desc)) {
            for (TradeTypeEnum o : TradeTypeEnum.values()) {
                if (o.getName().equals(desc)) {
                    return o.getId();
                }
            }
        }
        return null;
    }

    public static String getOrderStateDesc(Integer id) {
        if (!StringTools.isNullOrEmpty(id)) {
            for (TradeTypeEnum o : TradeTypeEnum.values()) {
                if (o.getId().equals(id)) {
                    return o.getName();
                }
            }
        }
        return null;
    }

}
