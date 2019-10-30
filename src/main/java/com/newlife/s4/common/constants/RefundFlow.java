package com.newlife.s4.common.constants;

import java.util.ArrayList;
import java.util.List;

public class RefundFlow extends FlowDefine {

    private static List<RefundFlow> pl;
    private long operationID;

    static {
        pl = new ArrayList<>();
        pl.add(new RefundFlow(1,RoleConstants.SALECONSULTANT,
                CarSalesOrderOperationEnum.REFUUND_SALE_AUDIT.getOperationID()));
        pl.add(new RefundFlow(2,RoleConstants.SALEMANAGER,
                CarSalesOrderOperationEnum.REFUUND_SALEMANAGER_AUDIT.getOperationID()));
        pl.add(new RefundFlow(3,RoleConstants.GM,
                CarSalesOrderOperationEnum.REFUUND_GM_AUDIT.getOperationID()));
        pl.add(new RefundFlow(4,RoleConstants.FINANCE,
                CarSalesOrderOperationEnum.REFUUND_FINANCE_AUDIT.getOperationID()));
    }

    private RefundFlow(int step, long roleID) {
        super(step, roleID);
    }
    private RefundFlow(int step, long roleID, long operationID) {
        super(step, roleID);
        this.operationID = operationID;
    }



    public static List<RefundFlow> getRefundFlowFlow(){
        return pl;
    }

    @Override
    public boolean isEndStep() {
        if(this == pl.get(pl.size()-1) || this.getStep() == pl.get(pl.size()-1).getStep()) {
            return true;
        }
        return false;
    }

    public static Integer getCarSalesOrderOperationByRole (long roleID) {

        for (RefundFlow e : pl){
            if (e.getRoleID() == roleID) {
                return (int)e.getOperationID();
            }
        }
        return null;
    }


    public long getOperationID() {
        return operationID;
    }
}
