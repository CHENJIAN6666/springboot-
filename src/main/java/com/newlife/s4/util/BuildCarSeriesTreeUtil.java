package com.newlife.s4.util;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.TreeBuilder;
import com.newlife.s4.common.model.Node;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BuildCarSeriesTreeUtil {

    public static List<JSONObject> build(List<JSONObject> list){
        List<Node> nodeList = new ArrayList<Node>();
        for (JSONObject json : list) {
            Node n =  new Node();
            n.setId(json.getString("id"));
            n.setLabel(json.getString("name"));
            n.setPid(json.getString("pid"));
            JSONObject nodeData = new JSONObject();
            nodeData.put("id", json.getString("id"));
            n.setNodeData(nodeData);
            nodeList.add(n);
        }

        List<JSONObject> list1 = new TreeBuilder().buildTree(nodeList);
        //删除第一层pid不为空的节点，因为品牌节点的pid是为空的
        Iterator<JSONObject> it = list1.iterator();
        while (it.hasNext()){
            JSONObject node = it.next();
            if(StringUtils.isNotEmpty( node.getString("pid") )){
                it.remove();
            }
        }
        return list1;
    }
}
