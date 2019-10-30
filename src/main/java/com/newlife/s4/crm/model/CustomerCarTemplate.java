package com.newlife.s4.crm.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class CustomerCarTemplate extends BaseRowModel {
    @ExcelProperty(index = 0,value = {"供应商"})
    private	 String providerName	;	//	供应商

    @ExcelProperty(index = 1,value = {"承保险种"})
    private	 String	 underwritingInsurance	;	//	承保险种

    @ExcelProperty(index = 2,value = {"车牌"})
    private	 String	 plateNumber	;	//	车牌

    @ExcelProperty(index = 3,value = {"车架码"})
    private	 String	 vINCode	;	//	车架码

    @ExcelProperty(index = 4,value = {"发动机码"})
    private	 String	 engineNumber	;	//	发动机码

    @ExcelProperty(index = 5,value = {"年审"})
    private	 String	 yearlyReviewDueDate	;	//	年审

    @ExcelProperty(index = 6,value = {"交强险"})
    private	 String	 compulsoryInsuranceDueDate	;	//	交强险

    @ExcelProperty(index = 7,value = {"商业险"})
    private	 String	 businessInsuranceDueDate	;	//	商业险

    @ExcelProperty(index = 8,value = {"品牌"})
    private	 String	 carBrandName	;	//	品牌

    @ExcelProperty(index = 9,value = {"车型"})
    private	 String	 carSeriesName	;	//	车型

    @ExcelProperty(index = 10,value = {"车辆状态"})
    private	 String	 rentStatus	;	//	车辆状态

    @ExcelProperty(index = 11,value = {"租赁模式"})
    private	 String	 customerCarType	;	//	租赁模式

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getUnderwritingInsurance() {
        return underwritingInsurance;
    }

    public void setUnderwritingInsurance(String underwritingInsurance) {
        this.underwritingInsurance = underwritingInsurance;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getvINCode() {
        return vINCode;
    }

    public void setvINCode(String vINCode) {
        this.vINCode = vINCode;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getYearlyReviewDueDate() {
        return yearlyReviewDueDate;
    }

    public void setYearlyReviewDueDate(String yearlyReviewDueDate) {
        this.yearlyReviewDueDate = yearlyReviewDueDate;
    }

    public String getCompulsoryInsuranceDueDate() {
        return compulsoryInsuranceDueDate;
    }

    public void setCompulsoryInsuranceDueDate(String compulsoryInsuranceDueDate) {
        this.compulsoryInsuranceDueDate = compulsoryInsuranceDueDate;
    }

    public String getBusinessInsuranceDueDate() {
        return businessInsuranceDueDate;
    }

    public void setBusinessInsuranceDueDate(String businessInsuranceDueDate) {
        this.businessInsuranceDueDate = businessInsuranceDueDate;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public String getCarSeriesName() {
        return carSeriesName;
    }

    public void setCarSeriesName(String carSeriesName) {
        this.carSeriesName = carSeriesName;
    }

    public String getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }

    public String getCustomerCarType() {
        return customerCarType;
    }

    public void setCustomerCarType(String customerCarType) {
        this.customerCarType = customerCarType;
    }
}
