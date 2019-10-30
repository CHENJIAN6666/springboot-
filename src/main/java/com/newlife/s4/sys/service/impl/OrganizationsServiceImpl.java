package com.newlife.s4.sys.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.newlife.s4.config.storage.FileStorageConfig;
import com.newlife.s4.util.QrcodeUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.Sessions;
import com.newlife.s4.common.TreeBuilder;
import com.newlife.s4.common.model.Node;
import com.newlife.s4.sys.dao.OrganizationsDao;
import com.newlife.s4.sys.service.OrganizationsService;
import com.newlife.s4.util.CommonUtil;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author: newlife
 * @description:
 * @date: 2017/10/24 16:07
 */
@Service
public class OrganizationsServiceImpl implements OrganizationsService {

	@Autowired
	private OrganizationsDao OrganizationsDao;

	@Autowired
	private FileStorageConfig fileStorageConfig;

	@Value("${wx.apiDomain}")
	private String apiDomain;

	@Value("${jetty.path}")
	private String jettyPath;

	/**
	 * 新增文章
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addOrganizations(JSONObject jsonObject) {
		OrganizationsDao.addOrganizations(jsonObject);
		//更新树形ID
		if(null !=jsonObject.get("Parent_Org_ID") && !"".equals(jsonObject.get("Parent_Org_ID"))) {

			Integer parentOrgId = jsonObject.getInteger("Parent_Org_ID");
			JSONObject json2 = new JSONObject();
			json2.put("Parent_Org_ID", parentOrgId);
			String treePath = OrganizationsDao.getOrgIdTreePath(json2);
			StringBuffer sb = new StringBuffer(treePath);
			sb.append(jsonObject.get("Org_Id"));
			sb.append(".");
			json2.put("Org_ID_Tree_Path", sb.toString());
			json2.put("Org_Id", jsonObject.get("Org_Id"));
			OrganizationsDao.updateOrgIdTreePath(json2);
			json2.put("Org_Id", parentOrgId);
			Integer orgLevel = OrganizationsDao.getOrgLevel(json2);
			orgLevel +=1;
			json2.put("Org_Id", jsonObject.get("Org_Id"));
			json2.put("Org_Level", orgLevel);
			OrganizationsDao.updateOrgLevel(json2);
			
			String namePath = OrganizationsDao.getOrgNameTreePath(json2);
			StringBuffer sb2 = new StringBuffer(namePath);
			sb2.append(jsonObject.get("Org_Name"));
			sb2.append(">");
			json2.put("Org_Name_Tree_Path", sb2.toString());
			OrganizationsDao.updateOrgNameTreePath(json2);
			
		}else {
			
			JSONObject json = new JSONObject();
			StringBuffer sb = new StringBuffer();
			sb.insert(0, ".");
			sb.append(jsonObject.get("Org_Id"));
			sb.append(".");
			json.put("Org_ID_Tree_Path", sb.toString());
			json.put("Org_Id", jsonObject.get("Org_Id"));
			OrganizationsDao.updateOrgIdTreePath(json);
			json.put("Org_Level", 0);
			OrganizationsDao.updateOrgLevel(json);
			
			StringBuffer sb2 = new StringBuffer();
			sb2.append(jsonObject.get("Org_Name"));
			sb2.append(">");
			json.put("Org_Name_Tree_Path", sb2.toString());
			OrganizationsDao.updateOrgNameTreePath(json);
		}
		
		return CommonUtil.successJson();
	}

	/**
	 * 组织列表
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	public JSONObject listOrganizations(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		jsonObject.put("orgIdTreePath", Sessions.getOrgIdTreePath());
		int count = OrganizationsDao.countOrganizations(jsonObject);
		List<JSONObject> list = OrganizationsDao.listOrganizations(jsonObject);
		List<Node> nodeList = new ArrayList<Node>();
		for (JSONObject json : list) {
			Node n =  new Node();
			n.setId(json.getString("id"));
			n.setLabel(json.getString("label"));
			n.setPid(json.getString("pid"));
			JSONObject nodeData = new JSONObject();
			nodeData.put("orgCode", json.getString("orgCode"));
			nodeData.put("orgFullName", json.getString("orgFullName"));
			nodeData.put("orgIdTreePath", json.getString("orgIdTreePath"));
			nodeData.put("orgLogo", json.getString("orgLogo"));
			nodeData.put("longitude", json.getString("longitude"));
			nodeData.put("latitude", json.getString("latitude"));
			nodeData.put("address", json.getString("address"));
			nodeData.put("telephone", json.getString("telephone"));
			nodeData.put("orgQRCode", json.getString("orgQRCode"));
			nodeData.put("telephoneAfterSale", json.getString("telephoneAfterSale"));
			n.setNodeData(nodeData);
			nodeList.add(n);
		}
		 List<JSONObject> list2 = new TreeBuilder().buildTree(nodeList);
		return CommonUtil.successPage(jsonObject, list2, count);
	}
	

	/**
	 * 更新文章
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateOrganizations(JSONObject jsonObject) {
		OrganizationsDao.updateOrganizations(jsonObject);
		jsonObject.put("orgID",jsonObject.getString("Org_Id"));
		JSONObject organization = OrganizationsDao.getOrganizations(jsonObject);
		updateOrgNameTreePath(organization,jsonObject.getString("Org_Name"));
		return CommonUtil.successJson();
	}

	/**
	 *
	 * @param organization
	 * @param postStoreName
	 */
	private void updateOrgNameTreePath(JSONObject organization,String postStoreName){
		String orgNameTreePath = organization.getString("orgNameTreePath");
		String[] namePath = orgNameTreePath.split(">");
		int orgLevel = organization.getIntValue("orgLevel");
		String orgName =  namePath[orgLevel - 1];
		if(!StringUtils.equals(orgName,postStoreName)) {
			String newOrgNameTreePath = orgNameTreePath.replace(orgName,postStoreName);
			JSONObject updateOrgName =	new JSONObject();
//			if (orgLevel <= 3) {
				updateOrgName.put("oldName",orgNameTreePath);
				updateOrgName.put("newName",newOrgNameTreePath);
				OrganizationsDao.updateStoreOrgNameTreePath(updateOrgName);
//			} else if (orgLevel > 3) {
//				updateOrgName.put("Org_Id",organization.getString("orgID"));
//				updateOrgName.put("Org_Name_Tree_Path",newOrgNameTreePath);
//				OrganizationsDao.updateOrgNameTreePath(updateOrgName);
//			}
		}
	}

	/**
	 * 更新文章
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject deleteOrganizations(JSONObject jsonObject) {
		String temp = jsonObject.get("Org_ID_Tree_Path").toString()+"%";
		jsonObject.put("Org_ID_Tree_Path", temp);
		OrganizationsDao.deleteOrganizations(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject getOrganizations(JSONObject jsonObject) {
        JSONObject userOrg = OrganizationsDao.getOrganizations(jsonObject);
		return CommonUtil.successJson(userOrg);
	}


	@Override
	public JSONObject createQRCode(JSONObject jsonObject,HttpServletRequest request) {
		String orgID = jsonObject.getString("Org_Id");
		String content = apiDomain + "/wechat/entrance?type=Shop&id=" + orgID;
		String logoUrl = jsonObject.getString("Org_Logo");
		String logoPath = request.getSession().getServletContext().getRealPath("/")
							+ logoUrl.substring(fileStorageConfig.getFileStorageAddress().length()-1);
		String relativelyPath =  request.getSession().getServletContext().getRealPath("/");
		String orgQRCodePath = fileStorageConfig.getFileStorageAddress() +
								QrcodeUtil.createQRCodeWithLogo(content,logoPath,relativelyPath);

		jsonObject.put("orgQRCode",orgQRCodePath);
		OrganizationsDao.updateOrganizations(jsonObject);
		return CommonUtil.successJson(jsonObject);
	}

	@Override
	public void downloadQRCode(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) {
		try {
			String orgID = jsonObject.getString("Org_Id");
			String content = apiDomain + "/wechat/entrance?type=Shop&id=" + orgID;
			String logoUrl = jsonObject.getString("Org_Logo");
			String logoPath = request.getSession().getServletContext().getRealPath("/")
					+ logoUrl.substring(fileStorageConfig.getFileStorageAddress().length() - 1);
			String relativelyPath = request.getSession().getServletContext().getRealPath("/");

			int logoSize = jsonObject.getIntValue("Logo_Size");
			if(logoSize == 0){
				logoSize = 300;
			}
			response.setHeader("Content-Disposition", "attachment; filename=" +orgID+"-"+logoSize+"-logo.png");
			OutputStream ros = response.getOutputStream();
			QrcodeUtil.createQRCodeWithLogo(content, logoSize, logoSize, logoPath, relativelyPath, ros);
		}catch (IOException e){
			e.printStackTrace();
		}
//		return CommonUtil.successJson(jsonObject);
	}

	/**
	 * 列表
	 * @param jsonObject
	 * @return
	 */
	@Override
	public JSONObject getOrganizationsList(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		List<JSONObject> list = OrganizationsDao.getOrganizationsList(jsonObject);
		return CommonUtil.successJson(list);
	}
}
