package com.newlife.s4.util.excel;


import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.newlife.s4.stock.model.StockTemplate;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener<T> extends AnalysisEventListener {

    //自定义用于暂时存储data。
    //可以通过实例获取该值
    private List<T> datas = new ArrayList<T>();
    public void invoke(Object object, AnalysisContext context) {
        // System.out.println("当前行："+context.getCurrentRowNum());
        // System.out.println(object);
        datas.add((T)object);//数据存储到list，供批量处理，或后续自己业务逻辑处理。
        doSomething(object);//根据自己业务做处理
    }
    private void doSomething(Object object) {
        //1、入库调用接口
    }
    public void doAfterAllAnalysed(AnalysisContext context) {
        // datas.clear();//解析结束销毁不用的资源
    }
    public List<T> getDatas() {
        return datas;
    }
    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}

