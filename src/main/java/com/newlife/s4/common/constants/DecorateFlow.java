package com.newlife.s4.common.constants;

import java.util.ArrayList;
import java.util.List;

public class DecorateFlow extends FlowDefine {

    private static List<FlowDefine> df;

    static {
        df = new ArrayList<>();
        df.add(new DecorateFlow(1,RoleConstants.SALECONSULTANT));
        df.add(new DecorateFlow(2,RoleConstants.SALEMANAGER));
        df.add(new DecorateFlow(3,RoleConstants.AFTERSALES));
    }

    private DecorateFlow(int step, long roleID) {
        super(step, roleID);
    }

    public static List<FlowDefine> getDecorateFlow(){
        return df;
    }

    @Override
    public boolean isEndStep() {
        if(this == df.get(df.size()-1)){
            return true;
        }
        return false;
    }
}
