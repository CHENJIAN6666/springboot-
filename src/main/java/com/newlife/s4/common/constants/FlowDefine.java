package com.newlife.s4.common.constants;

public abstract class FlowDefine {
    private int step;
    private long roleID;

    protected FlowDefine(int step, long roleID) {
        this.step = step;
        this.roleID = roleID;
    }

    public int getStep() {
        return step;
    }

    public long getRoleID() {
        return roleID;
    }

    public abstract boolean isEndStep();
}
