package com.newlife.s4.crm.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Date;
public class MaintenanceRecordTemplate extends BaseRowModel {
    @ExcelProperty(index = 0)
    private	 String seq	;	//	序号

    @ExcelProperty(index = 1)
    private	 String	 workNumber	;	//	作 业 单 号

    @ExcelProperty(index = 2)
    private	 String	 meetCarDate	;	//	接车日期

    @ExcelProperty(index = 3)
    private	 String	 deliverDate	;	//	交车日期

    @ExcelProperty(index = 4)
    private	 String	 deliverPersion	;	//	接车人

    @ExcelProperty(index = 5)
    private	 String	 plateNumber	;	//	车牌号

    @ExcelProperty(index = 6)
    private	 String	 contactState	;	//	联系状态

    @ExcelProperty(index = 7)
    private	 String	 Complain	;	//	是否投诉

    @ExcelProperty(index = 8)
    private	 String	 carMakeName	;	//	厂牌

    @ExcelProperty(index = 9)
    private	 String	 carBrandName	;	//	车名

    @ExcelProperty(index = 10)
    private	 String	 carSeriesName	;	//	车   型

    @ExcelProperty(index = 11)
    private	 String	 customerName	;	//	客户名称

    @ExcelProperty(index = 12)
    private	 String	 customerPhone	;	//	客户电话

    @ExcelProperty(index = 14)
    private	 String	 contactName	;	//	联系人

    @ExcelProperty(index = 15)
    private	 String	 contactPhone	;	//	联系电话

    @ExcelProperty(index = 17)
    private	 String	 maintenanceType	;	//	维修类别

    @ExcelProperty(index = 18)
    private	 String	 maintenanceNature	;	//	维修性质

    @ExcelProperty(index = 19)
    private	 String	 vINCode	;	//	车架号

    @ExcelProperty(index = 20)
    private	 String	 purchaseDate	;	//	购车日期

    @ExcelProperty(index = 21)
    private	 String	 customerAddress	;	//	客户地址

    @ExcelProperty(index = 22)
    private	 String	 contactType	;	//	回访方式

    @ExcelProperty(index = 23)
    private	 String	 meetCarRemark	;	//	接车备注

    @ExcelProperty(index = 24)
    private	 String	 repairGroup	;	//	班组

    @ExcelProperty(index = 27)
    private	 String	 firstServiceDate	;	//	首次回访时间

    @ExcelProperty(index = 28)
    private	 String	 firstServiceResult	;	//	首次回访结果

    @ExcelProperty(index = 29)
    private	 String	 firstFailReason	;	//	不成功原因

    @ExcelProperty(index = 30)
    private	 String	 secondServiceDate	;	//	第二次回访时间

    @ExcelProperty(index = 31)
    private	 String	 secondServiceResult	;	//	第二次回访结果

    @ExcelProperty(index = 32)
    private	 String	 secondFailReason	;	//	不成功原因

    @ExcelProperty(index = 33)
    private	 String	 thirdServiceDate	;	//	第三次回访时间

    @ExcelProperty(index = 34)
    private	 String	 thirdServiceReason	;	//	第三次回访结果

    @ExcelProperty(index = 35)
    private	 String	 thirdFailReason	;	//	不成功原因

    @ExcelProperty(index = 36)
    private	 String	 customerOpinion	;	//	客户反馈意见

    @ExcelProperty(index = 37)
    private	 String	 verySatisfied 	;	//	很满意

    @ExcelProperty(index = 38)
    private	 String	 satisfied 	;	//	满意

    @ExcelProperty(index = 39)
    private	 String	 normal	;	//	一般

    @ExcelProperty(index = 40)
    private	 String	 qualityDissatisfied	;	//	维修质量不满意

    @ExcelProperty(index = 41)
    private	 String	 priceDissatisfied 	;	//	维修价格不满意

    @ExcelProperty(index = 42)
    private	 String	 timeDissatisfied 	;	//	维修时间不满意

    @ExcelProperty(index = 43)
    private	 String	 comPriceDissatisfied 	;	//	配件价格不满意

    @ExcelProperty(index = 44)
    private	 String	 comtimeDissatisfied	;	//	配件时间不满意

    @ExcelProperty(index = 45)
    private	 String	 serviceDissatisfied	;	//	服务态度不满意

    @ExcelProperty(index = 46)
    private	 String	 returnVisitSuccessDate	;	//	回访成功日期

    @ExcelProperty(index = 47)
    private	 String	 returnVisitPersion	;	//	回访人

    @ExcelProperty(index = 48)
    private	 String	 researchScore	;	//	调查项目分数

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getMeetCarDate() {
        return meetCarDate;
    }

    public void setMeetCarDate(String meetCarDate) {
        this.meetCarDate = meetCarDate;
    }


    public String getDeliverPersion() {
        return deliverPersion;
    }

    public void setDeliverPersion(String deliverPersion) {
        this.deliverPersion = deliverPersion;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getContactState() {
        return contactState;
    }

    public void setContactState(String contactState) {
        this.contactState = contactState;
    }

    public String getComplain() {
        return Complain;
    }

    public void setComplain(String complain) {
        Complain = complain;
    }

    public String getCarMakeName() {
        return carMakeName;
    }

    public void setCarMakeName(String carMakeName) {
        this.carMakeName = carMakeName;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getMaintenanceNature() {
        return maintenanceNature;
    }

    public void setMaintenanceNature(String maintenanceNature) {
        this.maintenanceNature = maintenanceNature;
    }

    public String getvINCode() {
        return vINCode;
    }

    public void setvINCode(String vINCode) {
        this.vINCode = vINCode;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getMeetCarRemark() {
        return meetCarRemark;
    }

    public void setMeetCarRemark(String meetCarRemark) {
        this.meetCarRemark = meetCarRemark;
    }

    public String getRepairGroup() {
        return repairGroup;
    }

    public void setRepairGroup(String repairGroup) {
        this.repairGroup = repairGroup;
    }

    public String getFirstServiceDate() {
        return firstServiceDate;
    }

    public void setFirstServiceDate(String firstServiceDate) {
        this.firstServiceDate = firstServiceDate;
    }

    public String getFirstServiceResult() {
        return firstServiceResult;
    }

    public void setFirstServiceResult(String firstServiceResult) {
        this.firstServiceResult = firstServiceResult;
    }

    public String getFirstFailReason() {
        return firstFailReason;
    }

    public void setFirstFailReason(String firstFailReason) {
        this.firstFailReason = firstFailReason;
    }

    public String getSecondServiceDate() {
        return secondServiceDate;
    }

    public void setSecondServiceDate(String secondServiceDate) {
        this.secondServiceDate = secondServiceDate;
    }

    public String getSecondServiceResult() {
        return secondServiceResult;
    }

    public void setSecondServiceResult(String secondServiceResult) {
        this.secondServiceResult = secondServiceResult;
    }

    public String getSecondFailReason() {
        return secondFailReason;
    }

    public void setSecondFailReason(String secondFailReason) {
        this.secondFailReason = secondFailReason;
    }

    public String getThirdServiceDate() {
        return thirdServiceDate;
    }

    public void setThirdServiceDate(String thirdServiceDate) {
        this.thirdServiceDate = thirdServiceDate;
    }

    public String getThirdServiceReason() {
        return thirdServiceReason;
    }

    public void setThirdServiceReason(String thirdServiceReason) {
        this.thirdServiceReason = thirdServiceReason;
    }

    public String getThirdFailReason() {
        return thirdFailReason;
    }

    public void setThirdFailReason(String thirdFailReason) {
        this.thirdFailReason = thirdFailReason;
    }

    public String getCustomerOpinion() {
        return customerOpinion;
    }

    public void setCustomerOpinion(String customerOpinion) {
        this.customerOpinion = customerOpinion;
    }

    public String getVerySatisfied() {
        return verySatisfied;
    }

    public void setVerySatisfied(String verySatisfied) {
        this.verySatisfied = verySatisfied;
    }

    public String getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(String satisfied) {
        this.satisfied = satisfied;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getQualityDissatisfied() {
        return qualityDissatisfied;
    }

    public void setQualityDissatisfied(String qualityDissatisfied) {
        this.qualityDissatisfied = qualityDissatisfied;
    }

    public String getPriceDissatisfied() {
        return priceDissatisfied;
    }

    public void setPriceDissatisfied(String priceDissatisfied) {
        this.priceDissatisfied = priceDissatisfied;
    }

    public String getTimeDissatisfied() {
        return timeDissatisfied;
    }

    public void setTimeDissatisfied(String timeDissatisfied) {
        this.timeDissatisfied = timeDissatisfied;
    }

    public String getComPriceDissatisfied() {
        return comPriceDissatisfied;
    }

    public void setComPriceDissatisfied(String comPriceDissatisfied) {
        this.comPriceDissatisfied = comPriceDissatisfied;
    }

    public String getComtimeDissatisfied() {
        return comtimeDissatisfied;
    }

    public void setComtimeDissatisfied(String comtimeDissatisfied) {
        this.comtimeDissatisfied = comtimeDissatisfied;
    }

    public String getServiceDissatisfied() {
        return serviceDissatisfied;
    }

    public void setServiceDissatisfied(String serviceDissatisfied) {
        this.serviceDissatisfied = serviceDissatisfied;
    }

    public String getReturnVisitSuccessDate() {
        return returnVisitSuccessDate;
    }

    public void setReturnVisitSuccessDate(String returnVisitSuccessDate) {
        this.returnVisitSuccessDate = returnVisitSuccessDate;
    }

    public String getReturnVisitPersion() {
        return returnVisitPersion;
    }

    public void setReturnVisitPersion(String returnVisitPersion) {
        this.returnVisitPersion = returnVisitPersion;
    }

    public String getResearchScore() {
        return researchScore;
    }

    public void setResearchScore(String researchScore) {
        this.researchScore = researchScore;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }
}
