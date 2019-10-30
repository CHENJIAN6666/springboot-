package com.newlife.s4.common.constants;

import com.newlife.s4.util.StringTools;

public enum  OrderEnum {

    O1(1,"待支付"),
    O2(2,"已支付"),
    O3(3,"待提车"),
    O4(4,"交易完成"),
    O5(5,"退订中"),
    O6(6,"退订成功"),
    O7(7,"已取消");


    private Integer id;
    private String name;

    OrderEnum(){

    }

    OrderEnum(Integer id ,String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
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

    public static Integer getOrderState(String desc){
        if(!StringTools.isNullOrEmpty(desc)){
            for (OrderEnum o : OrderEnum.values()) {
                if(o.getName().equals(desc)){
                    return o.getId();
                }
            }
        }
        return null;
    }
}
