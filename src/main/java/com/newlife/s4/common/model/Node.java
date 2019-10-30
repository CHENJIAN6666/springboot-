package com.newlife.s4.common.model;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class Node {

	private String id;
	
	private String label;
	private List<Node> children;
	private String pid;
	private JSONObject nodeData;

	public JSONObject getNodeData() {
		return nodeData;
	}

	public void setNodeData(JSONObject nodeData) {
		this.nodeData = nodeData;
	}

	public Node() {
	}

	public Node(String id, String label) {
		super();
		this.id = id;
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
}
