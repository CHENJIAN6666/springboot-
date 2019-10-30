package com.newlife.s4.common.constants;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//销售单操作枚举
@Component
public class CarSalesOrderOperationList {

    public static List<CarSalesOrderOperationEnum> ol = new ArrayList<>();

    static {
        ol.add(CarSalesOrderOperationEnum.CREATE);
        ol.add(CarSalesOrderOperationEnum.PAY);
        ol.add(CarSalesOrderOperationEnum.CONFIRM);
        ol.add(CarSalesOrderOperationEnum.AUDIT);
        ol.add(CarSalesOrderOperationEnum.SETTLEMENT);
        ol.add(CarSalesOrderOperationEnum.ASSIGN);
        ol.add(CarSalesOrderOperationEnum.DECORATE);
        ol.add(CarSalesOrderOperationEnum.PREDELIVER_APPLY);
        ol.add(CarSalesOrderOperationEnum.PREDELIVER_FINANCE_AUDIT);
        ol.add(CarSalesOrderOperationEnum.PREDELIVER_SALEMANAGER_AUDIT);
        ol.add(CarSalesOrderOperationEnum.PREDELIVER_GM_AUDIT);
        ol.add(CarSalesOrderOperationEnum.DELIVER);

    }

    private CarSalesOrderOperationList(){}

    //查询 当前操作 ，还剩下
    public static List<CarSalesOrderOperationEnum> queryOtherOperation(CarSalesOrderOperationEnum current){
        int index = ol.indexOf(current);
        List otherOperation = ol.subList(index+1,ol.size());
        return otherOperation;
    }





//    public static void main(String[] args){
//       System.out.println(getCarSalesOrderOperationByRole(RoleConstants.SALEMANAGER));
//    }

}
