package com.newlife.s4.common.constants;

//    业务类型 1交车审批 2.退款审批 3.装潢追加审批
public enum FlowBusinessType {
    preDeliverFlow(1,"交车审批"),
    refundFlow(2,"退订审批"),
    decorateFlow(3,"装潢追加审批");

    private int type;
    private String desc;

    FlowBusinessType(int _type,String _desc){
        type = _type;
        desc = _desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
