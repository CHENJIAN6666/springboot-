package com.newlife.s4.common.constants.cell;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.util.StringTools;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

public enum CellLevel {
    A(0,"A"),
    B(1,"B"),
    C(2,"C"),
    D(3,"D");

    CellLevel(){
    }

    CellLevel(int id,String name){
        this.id = id;
        this.name = name;
    }

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<JSONObject> levelList(){
        List<JSONObject> list = new ArrayList<>();
        for(CellLevel cl : CellLevel.values()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("levelID",cl.getId());
            jsonObject.put("levelName",cl.getName());
            list.add(jsonObject);
        }
        return list;
    }

    public static Integer getOrderState(String desc) {
        if (!StringTools.isNullOrEmpty(desc)) {
            for (CellLevel o : CellLevel.values()) {
                if (o.getName().equals(desc)) {
                    return o.getId();
                }
            }
        }
        return null;
    }

    public static String getOrderStateDesc(Integer id) {
        if (!StringTools.isNullOrEmpty(id)) {
            for (CellLevel o : CellLevel.values()) {
                if (o.getId().equals(id)) {
                    return o.getName();
                }
            }
        }
        return null;
    }
}
