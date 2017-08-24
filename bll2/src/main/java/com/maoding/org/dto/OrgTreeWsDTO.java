package com.maoding.org.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：DepartDTO
 * 类描述：部门 DTO
 * 作    者：MaoSF
 * 日    期：2016年12月8日-上午10:39:23
 */
public class OrgTreeWsDTO extends BaseDTO{

	 /**
     * 公司名称
     */
    private String companyName;

     /**
      * 公司ID
      */
    private Map<String,Object> state = new HashMap<String,Object>();

    /**
     * 子节点
     */
    private List<OrgTreeWsDTO> children = new ArrayList<OrgTreeWsDTO>();


	public Map<String, Object> getState() {
		return state;
	}

	public void setState(Map<String, Object> state) {
		this.state = state;
	}

	public List<OrgTreeWsDTO> getChildren() {
		return children;
	}

	public void setChildren(List<OrgTreeWsDTO> children) {
		this.children = children;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 根据id获取子节点
	 * @param id
	 * @return
     */
	public OrgTreeWsDTO getChildById(String id){
		for(OrgTreeWsDTO tree:children){
			if(tree.getId().equals(id)){
				return tree;
			}
		}
		return null;
	}
}
