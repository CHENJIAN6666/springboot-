package com.newlife.s4.util.excel.model;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class RentCarOffsetDaoEntity {
    private String rentContactName;
    private String rentContactPhone;
    private String rentCustomerName;
    private String periods;
    private String periodYear;
    private String rentType;
    private String rentTime;
    private String rentEndTime;
    private String plateNumber;
    private String rental;
    private String deposit;
    private String providerName;
    private String carFullName;
    private String realName;
    private String isInvoice;
    private String pastArrear;
    private String pastPaid;
    private String arrearTotal;
    private String remark;
    private String rentState;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRentState() {
        return rentState;
    }

    public void setRentState(String rentState) {
        this.rentState = rentState;
    }

    public String getCarFullName() {
        return carFullName;
    }

    public void setCarFullName(String carFullName) {
        this.carFullName = carFullName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getPastArrear() {
        return pastArrear;
    }

    public void setPastArrear(String pastArrear) {
        this.pastArrear = pastArrear;
    }

    public String getPastPaid() {
        return pastPaid;
    }

    public void setPastPaid(String pastPaid) {
        this.pastPaid = pastPaid;
    }

    public String getArrearTotal() {
        return arrearTotal;
    }

    public void setArrearTotal(String arrearTotal) {
        this.arrearTotal = arrearTotal;
    }

    public String getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(String periodYear) {
        this.periodYear = periodYear;
    }

    private List<JSONObject> offset;

    public String getRentContactName() {
        return rentContactName;
    }

    public void setRentContactName(String rentContactName) {
        this.rentContactName = rentContactName;
    }

    public String getRentContactPhone() {
        return rentContactPhone;
    }

    public void setRentContactPhone(String rentContactPhone) {
        this.rentContactPhone = rentContactPhone;
    }

    public String getRentCustomerName() {
        return rentCustomerName;
    }

    public void setRentCustomerName(String rentCustomerName) {
        this.rentCustomerName = rentCustomerName;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getRentEndTime() {
        return rentEndTime;
    }

    public void setRentEndTime(String rentEndTime) {
        this.rentEndTime = rentEndTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public List<JSONObject> getOffset() {
        return offset;
    }

    public void setOffset(List<JSONObject> offset) {
        this.offset = offset;
    }
}
