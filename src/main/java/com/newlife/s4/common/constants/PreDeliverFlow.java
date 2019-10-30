package com.newlife.s4.common.constants;

import java.util.ArrayList;
import java.util.List;

public class PreDeliverFlow extends FlowDefine {

    private static List<PreDeliverFlow> pl;
    private long operationID;

    static {
        pl = new ArrayList<>();
        pl.add(new PreDeliverFlow(1,RoleConstants.SALECONSULTANT));
        pl.add(new PreDeliverFlow(2,RoleConstants.FINANCE,
                CarSalesOrderOperationEnum.PREDELIVER_FINANCE_AUDIT.getOperationID()));
        pl.add(new PreDeliverFlow(3,RoleConstants.SALEMANAGER,
                CarSalesOrderOperationEnum.PREDELIVER_SALEMANAGER_AUDIT.getOperationID()));
        pl.add(new PreDeliverFlow(4,RoleConstants.GM,
                CarSalesOrderOperationEnum.PREDELIVER_GM_AUDIT.getOperationID()));
    }

    private PreDeliverFlow(int step, long roleID) {
        super(step, roleID);
    }
    private PreDeliverFlow(int step, long roleID,long operationID) {
        super(step, roleID);
        this.operationID = operationID;
    }

    public static List<PreDeliverFlow> getPreDeliverFlow(){
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

        for (PreDeliverFlow e : pl){
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
