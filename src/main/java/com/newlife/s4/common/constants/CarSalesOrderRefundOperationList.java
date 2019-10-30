package com.newlife.s4.common.constants;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//销售单退款操作列表
@Component
public class CarSalesOrderRefundOperationList {

    public static List<CarSalesOrderOperationEnum> ol = new ArrayList<>();

    static {
        ol.add(CarSalesOrderOperationEnum.REFUND_APPLY);
        ol.add(CarSalesOrderOperationEnum.REFUUND_SALE_AUDIT);
        ol.add(CarSalesOrderOperationEnum.REFUUND_SALEMANAGER_AUDIT);
        ol.add(CarSalesOrderOperationEnum.REFUUND_GM_AUDIT);
        ol.add(CarSalesOrderOperationEnum.REFUUND_FINANCE_AUDIT);
        ol.add(CarSalesOrderOperationEnum.CANCEL);
    }

    private CarSalesOrderRefundOperationList(){}

    //查询 当前操作 ，还剩下
    public static List<CarSalesOrderOperationEnum> queryOtherOperation(CarSalesOrderOperationEnum current){
        int index = ol.indexOf(current);
        List otherOperation = ol.subList(index+1,ol.size());
        return otherOperation;
    }

//    public static void main(String[] args){
//       System.out.println(queryOtherOperation(CarSalesOrderOperationEnum.REFUND_APPLY));
//    }

}
