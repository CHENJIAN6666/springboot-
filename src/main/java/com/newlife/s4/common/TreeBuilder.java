package com.newlife.s4.common;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.model.Node;

//import net.sf.json.JSONArray;



public class TreeBuilder {

    List<Node> nodes = new ArrayList<>();

    public List<JSONObject> buildTree(List<Node> nodes) {

        TreeBuilder treeBuilder = new TreeBuilder(nodes);

        return treeBuilder.buildJSONTree();
    }
    

    public TreeBuilder() {
    }

    public TreeBuilder(List<Node> nodes) {
        super();
        this.nodes = nodes;
    }

    // 构建JSON树形结构
    public List<JSONObject> buildJSONTree() {
	
		List<Node> nodeTree = buildTree();
		
//		JSONArray jsonArray = JSONArray.fromObject(nodeTree);
		   
		List<JSONObject> list = new ArrayList();
//		for(int i=0;i<jsonArray.size();i++) {
//			JSONObject j = JSONObject.parseObject(jsonArray.get(i).toString());
//			list.add(j);
//		}
		for(int i=0;i<nodeTree.size();i++){
			list.add((JSONObject)JSONObject.toJSON(nodeTree.get(i)));
		}
		return list;
    }
    
    
    

    // 构建树形结构
    public List<Node> buildTree() {
        List<Node> treeNodes = new ArrayList<>();
        List<Node> rootNodes = getRootNodes();
        for (Node rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    // 递归子节点
    public void buildChildNodes(Node node) {
        List<Node> children = getChildNodes(node);
        if (!children.isEmpty() && children.size()>0) {
        	node.setChildren(children);
            for (Node child : children) {
                buildChildNodes(child);
            }
            
        }
    }

    // 获取父节点下所有的子节点
    public List<Node> getChildNodes(Node pnode) {
        List<Node> childNodes = new ArrayList<>();
        for (Node n : nodes) {
            if (pnode.getId().equals(n.getPid())) {
                childNodes.add(n);
            }
        }
        return childNodes;
    }

    // 判断是否为根节点
    public boolean rootNode(Node node) {
        boolean isRootNode = true;
        for (Node n : nodes) {
        	if(null !=node.getPid()) {
        		  if (node.getPid().equals(n.getId())) {
                      isRootNode = false;
                      break;
                  }
        	}
        }
        return isRootNode;
    }

    // 获取集合中所有的根节点
    public List<Node> getRootNodes() {
        List<Node> rootNodes = new ArrayList<>();
        for (Node n : nodes) {
            if (rootNode(n)) {
                rootNodes.add(n);
            }
        }
        return rootNodes;
    }
}