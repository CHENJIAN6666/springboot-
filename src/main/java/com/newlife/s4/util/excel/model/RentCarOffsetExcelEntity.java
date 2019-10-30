package com.newlife.s4.util.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RentCarOffsetExcelEntity extends BaseRowModel {

    @ExcelProperty(value = {"供应商", "供应商"}, index = 0)
    private String providerName;

    @ExcelProperty(value = {"客户名", "客户名"}, index = 1)
    private String rentCustomerName;

    @ExcelProperty(value = {"联系人", "联系人"}, index = 2)
    private String rentContactName;

    @ExcelProperty(value = {"联系电话", "联系电话"}, index = 3)
    private String rentContactPhone;


    @ExcelProperty(value = {"车型", "车型"}, index = 4)
    private String carFullName;

    @ExcelProperty(value = {"车牌", "车牌"}, index = 5)
    private String plateNumber;

    @ExcelProperty(value = {"业务类型", "业务类型"}, index = 6)
    private String rentType;

    @ExcelProperty(value = {"现业务经理", "现业务经理"}, index = 7)
    private String realName;

    @ExcelProperty(value = {"租期开始时间", "租期开始时间"}, index = 8)
    private String rentTime;

    @ExcelProperty(value = {"租期结束时间", "租期结束时间"}, index = 9)
    private String rentEndTime;

    @ExcelProperty(value = {"押金", "押金"}, index = 10)
    private String deposit;

//    @ExcelProperty(value = {"方案", "方案"}, index = 9)
//    private String periods;


    @ExcelProperty(value = {"租期/月/台", "租期/月/台"}, index = 11)
    private String periodYear;

    @ExcelProperty(value = {"是否开票", "是否开票"}, index = 12)
    private String isInvoice;

    @ExcelProperty(value = {"租金/台", "租金/台"}, index = 13)
    private String rental;

    @ExcelProperty(value = {"承前累计欠租", "承前累计欠租"}, index = 14)
    private String pastArrear;

    @ExcelProperty(value = {"承前累计缴租", "承前累计缴租"}, index = 15)
    private String pastPaid;

    @ExcelProperty(value = {"2018年", "1月"}, index = 16)
    private String rent201801;
    @ExcelProperty(value = {"2018年", "2月"}, index = 17)
    private String rent201802;
    @ExcelProperty(value = {"2018年", "3月"}, index = 18)
    private String rent201803;
    @ExcelProperty(value = {"2018年", "4月"}, index = 19)
    private String rent201804;
    @ExcelProperty(value = {"2018年", "5月"}, index = 20)
    private String rent201805;
    @ExcelProperty(value = {"2018年", "6月"}, index = 21)
    private String rent201806;
    @ExcelProperty(value = {"2018年", "7月"}, index = 22)
    private String rent201807;
    @ExcelProperty(value = {"2018年", "8月"}, index = 23)
    private String rent201808;
    @ExcelProperty(value = {"2018年", "9月"}, index = 24)
    private String rent201809;
    @ExcelProperty(value = {"2018年", "10月"}, index = 25)
    private String rent201810;
    @ExcelProperty(value = {"2018年", "11月"}, index = 26)
    private String rent201811;
    @ExcelProperty(value = {"2018年", "12月"}, index = 27)
    private String rent201812;

    @ExcelProperty(value = {"2019年", " 1月 "}, index = 28)
    private String rent201901;
    @ExcelProperty(value = {"2019年", " 2月 "}, index = 29)
    private String rent201902;
    @ExcelProperty(value = {"2019年", " 3月 "}, index = 30)
    private String rent201903;
    @ExcelProperty(value = {"2019年", " 4月 "}, index = 31)
    private String rent201904;
    @ExcelProperty(value = {"2019年", " 5月 "}, index = 32)
    private String rent201905;
    @ExcelProperty(value = {"2019年", " 6月 "}, index = 33)
    private String rent201906;
    @ExcelProperty(value = {"2019年", " 7月 "}, index = 34)
    private String rent201907;
    @ExcelProperty(value = {"2019年", " 8月 "}, index = 35)
    private String rent201908;
    @ExcelProperty(value = {"2019年", " 9月 "}, index = 36)
    private String rent201909;
    @ExcelProperty(value = {"2019年", " 10月 "}, index = 37)
    private String rent201910;
    @ExcelProperty(value = {"2019年", " 11月 "}, index = 38)
    private String rent201911;
    @ExcelProperty(value = {"2019年", " 12月 "}, index = 39)
    private String rent201912;

    @ExcelProperty(value = {"合计欠租", "合计欠租"}, index = 40)
    private String arrearTotal;


    @ExcelProperty(value = {"退车", "退车"}, index = 41)
    private String rentState;



    @ExcelProperty(value = {"备注", "备注"}, index = 42)
    private String remark;


    public String getRentState() {
        return rentState;
    }

    public void setRentState(String rentState) {
        this.rentState = rentState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    /**
     * 租金字段到当前月份前的设置成‘/’
     */
    public RentCarOffsetExcelEntity() {
        String currYM = DateUtils.getNowDateText("yyyyMM");
        String targetMethodName = "setRent" + currYM;
        Class clazz = RentCarOffsetExcelEntity.class;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("setRent")) {
                try {
                    if (methodName.compareTo(targetMethodName) < 1) {
                        method.invoke(this, "/");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getRentCustomerName() {
        return rentCustomerName;
    }

    public void setRentCustomerName(String rentCustomerName) {
        this.rentCustomerName = rentCustomerName;
    }

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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

//    public String getPeriods() {
//        return periods;
//    }
//
//    public void setPeriods(String periods) {
//        this.periods = periods;
//    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public String getRent201801() {
        return rent201801;
    }

    public void setRent201801(String rent201801) {
        this.rent201801 = rent201801;
    }

    public String getRent201802() {
        return rent201802;
    }

    public void setRent201802(String rent201802) {
        this.rent201802 = rent201802;
    }

    public String getRent201803() {
        return rent201803;
    }

    public void setRent201803(String rent201803) {
        this.rent201803 = rent201803;
    }

    public String getRent201804() {
        return rent201804;
    }

    public void setRent201804(String rent201804) {
        this.rent201804 = rent201804;
    }

    public String getRent201805() {
        return rent201805;
    }

    public void setRent201805(String rent201805) {
        this.rent201805 = rent201805;
    }

    public String getRent201806() {
        return rent201806;
    }

    public void setRent201806(String rent201806) {
        this.rent201806 = rent201806;
    }

    public String getRent201807() {
        return rent201807;
    }

    public void setRent201807(String rent201807) {
        this.rent201807 = rent201807;
    }

    public String getRent201808() {
        return rent201808;
    }

    public void setRent201808(String rent201808) {
        this.rent201808 = rent201808;
    }

    public String getRent201809() {
        return rent201809;
    }

    public void setRent201809(String rent201809) {
        this.rent201809 = rent201809;
    }

    public String getRent201810() {
        return rent201810;
    }

    public void setRent201810(String rent201810) {
        this.rent201810 = rent201810;
    }

    public String getRent201811() {
        return rent201811;
    }

    public void setRent201811(String rent201811) {
        this.rent201811 = rent201811;
    }

    public String getRent201812() {
        return rent201812;
    }

    public void setRent201812(String rent201812) {
        this.rent201812 = rent201812;
    }

    public String getRent201901() {
        return rent201901;
    }

    public void setRent201901(String rent201901) {
        this.rent201901 = rent201901;
    }

    public String getRent201902() {
        return rent201902;
    }

    public void setRent201902(String rent201902) {
        this.rent201902 = rent201902;
    }

    public String getRent201903() {
        return rent201903;
    }

    public void setRent201903(String rent201903) {
        this.rent201903 = rent201903;
    }

    public String getRent201904() {
        return rent201904;
    }

    public void setRent201904(String rent201904) {
        this.rent201904 = rent201904;
    }

    public String getRent201905() {
        return rent201905;
    }

    public void setRent201905(String rent201905) {
        this.rent201905 = rent201905;
    }

    public String getRent201906() {
        return rent201906;
    }

    public void setRent201906(String rent201906) {
        this.rent201906 = rent201906;
    }

    public String getRent201907() {
        return rent201907;
    }

    public void setRent201907(String rent201907) {
        this.rent201907 = rent201907;
    }

    public String getRent201908() {
        return rent201908;
    }

    public void setRent201908(String rent201908) {
        this.rent201908 = rent201908;
    }

    public String getRent201909() {
        return rent201909;
    }

    public void setRent201909(String rent201909) {
        this.rent201909 = rent201909;
    }

    public String getRent201910() {
        return rent201910;
    }

    public void setRent201910(String rent201910) {
        this.rent201910 = rent201910;
    }

    public String getRent201911() {
        return rent201911;
    }

    public void setRent201911(String rent201911) {
        this.rent201911 = rent201911;
    }

    public String getRent201912() {
        return rent201912;
    }

    public void setRent201912(String rent201912) {
        this.rent201912 = rent201912;
    }


    public String getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(String periodYear) {
        this.periodYear = periodYear;
    }

    /**
     *  RentCarOffsetDaoEntity对象的属性值设置到RentCarOffsetExcelEntity对象中
     *
     */
    public void setProperty(RentCarOffsetDaoEntity daoEntity) throws InvocationTargetException, IllegalAccessException {
        Class clazz = this.getClass();
        String currYM = DateUtils.getNowDateText("yyyyMM");
        String currYMD = DateUtils.getNowDateText(DateUtils.YMD);
        Field[] declaredFields = daoEntity.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            String fname = field.getName();
            String targetMethodName = "set" + StringUtils.capitalize(fname);

            try {
                if(!StringUtils.equals("offset",fname)) {
                    Method method = clazz.getMethod(targetMethodName, String.class);
                    method.setAccessible(true);
                    String arg = (String) field.get(daoEntity);
                    method.invoke(this, arg);
                }else {
                    List<JSONObject> offsets = (List<JSONObject>) field.get(daoEntity);
                    for (JSONObject offset: offsets){
                        String payDate = offset.getString("payDate");
                        String payDateYM = payDate.substring(0,"yyyyMM".length());
                        String arg = "";
                        //应交租日期 比 当前日期小
                        if(payDateYM.compareTo(currYM) == -1
                                || payDate.compareTo(currYMD) < 1) {
                            int isFinish = offset.getIntValue("isFinish");
                            if(isFinish == 1){
                                double renal = offset.getDoubleValue("renal");
                                arg = String.valueOf(renal) ;
                            }else {
                                arg = "欠";
                            }
                            Method method = clazz.getMethod("setRent"+payDateYM, String.class);
                            method.setAccessible(true);
                            method.invoke(this, arg);
                        }
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        final RentCarOffsetExcelEntity rentCarOffsetEntity = new RentCarOffsetExcelEntity();
//        System.out.println(rentCarOffsetEntity);
//    }
}
