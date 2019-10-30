package com.newlife.s4.util.excel;

//import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.newlife.s4.util.excel.model.RentCarOffsetExcelEntity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class ExcelFileUtil {

//    //每行数据是List<String>无表头
//    public void writeRent(OutputStream out,List<List<String>> datas) throws FileNotFoundException {
//            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,false);
//            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
//            Sheet sheet1 = new Sheet(1, 0);
//            writer.write0(datas, sheet1);
//            writer.finish();
//    }

    /**
     * 生成车桩、汽服车辆合同收租情况汇总表
     * @param out 输出位置
//     * @param tempFile 模板文件路径
     * @param datas 数据
     */
    public static void writeRentCarExcel(OutputStream out,List<RentCarOffsetExcelEntity> datas){
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS);
        Sheet sheet1 = new Sheet(1, 2,RentCarOffsetExcelEntity.class);
        writer.write(datas, sheet1);
        writer.finish();

    }

    /**
     * 生成车桩、汽服车辆合同收租情况汇总表
     * @param out 输出位置
    //     * @param tempFile 模板文件路径
     * @param datas 数据
     */
    public static void writeRentCarExcel(OutputStream out, List<? extends BaseRowModel> datas, Class s){
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS);
        Sheet sheet1 = new Sheet(1, 2,s);
        writer.write(datas, sheet1);
        writer.finish();

    }

    public static void main(String[] args) {
        FileOutputStream out = null;
        try{
            File file = Files.createFile(Paths.get("test.xls")).toFile();
            out = new FileOutputStream(file);
            List<RentCarOffsetExcelEntity> datas = new LinkedList<>();

            writeRentCarExcel(out,datas);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
