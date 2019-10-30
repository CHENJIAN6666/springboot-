package com.newlife.s4.common.constants;

//销售单操作枚举
public enum CarSalesOrderOperationEnum {

//    1提交订单；2支付订单；
//            11确认受理
//6订单生效（审核订单）；
//            7待配车；
//            8已配车，待订单结算审核；
//            12已订单结算审核，待装潢；
//            9已装潢，待交车；
//            13已申请交车，待审核；
//            14已审核交车，待交车；
//            10交割完成；
//    3申请退款；4退款成功；5取消订单；
    CREATE(1,"提交订单","生成订单号"),
    PAY(2,"支付订单","客户已支付订金"),
    CONFIRM(11,"受理订单","销售顾问已受理订单"),
    AUDIT(6,"订单生效","销售经理已审核订单"),
    //ASSIGN_ORDER(7,"待配车"),
    SETTLEMENT(12,"订单结算","销售经理已确认财务结算"),
    ASSIGN(8,"订单配车","车辆已配备"),
    DECORATE(9,"精品加装","精品已加装完毕"),
    PREDELIVER_APPLY(13,"交车申请","销售顾问已提交车申请"),
    PREDELIVER_FINANCE_AUDIT(14,"财务审核交车","财务已审核申请"),
    PREDELIVER_SALEMANAGER_AUDIT(15,"销售经理审核交车","销售经理已审核申请"),
    PREDELIVER_GM_AUDIT(16,"总经理审核交车","总经理已审核申请"),
    DELIVER(10,"交车成功","交车完毕，交易完成"),

    REFUND_APPLY(3,"申请退订","客户申请退订"),
    REFUUND_SALE_AUDIT(17,"销售顾问确认受理","销售顾问确认受理退订"),
    REFUUND_SALEMANAGER_AUDIT(18,"销售经理审核","销售经理审核退订"),
    REFUUND_GM_AUDIT(19,"总经理审核","总经理审核退订"),
    REFUUND_FINANCE_AUDIT(20,"财务操作","财务操作退订"),
    REFUND_SUCCESS(4,"退订成功","财务已处理客户退订"),

    CANCEL(5,"取消订单","销售订单已被取消");



    private Integer operationID;
    private String operationName;
    private String operationDesc;
    private long operationRole;

    private CarSalesOrderOperationEnum(int operationID, String operationName, String operationDesc){
        this.operationID = operationID;
        this.operationName = operationName;
        this.operationDesc = operationDesc;

    }
    private CarSalesOrderOperationEnum(int operationID, String operationName, String operationDesc,long operationRole){
     this.operationID = operationID;
     this.operationName = operationName;
     this.operationDesc = operationDesc;
     this.operationRole = operationRole;

    }

    public Integer getOperationID() {
        return operationID;
    }

    public String getOperationName() {
        return operationName;
    }

    public String getOperationDesc(){
        return operationDesc;
    }

    public long getOperationRole(){
        return operationRole;
    }

}
