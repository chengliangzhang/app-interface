package com.maoding.system.constant;

import java.util.HashMap;
import java.util.Map;

import com.maoding.core.util.SpringContextUtil;
import com.maoding.system.service.DataDictionaryService;

/**深圳市设计同道技术有限公司
 * 类    名：WsConstant
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2016年1月28日-上午10:09:31
 */
public class WsConstant {
	private static DataDictionaryService dataDictionaryService=SpringContextUtil.getBean("dataDictionaryService", DataDictionaryService.class);
	
	private static Map<String, Map<String, String>>constantMap=new HashMap<String, Map<String,String>>();
	
	public static Map<String, String>employeeTypeMap(){
		return getDataMap("yg-lb");
	}
	public static Map<String, String>technicalLevelMap(){
		return getDataMap("zc-dj");
	}
//	public static Map<String, String>ddMap(String code){
//		Map<String, String>map=new HashMap<String, String>();
//		SQLMaker sm=new SQLMaker();
//		sm.SELECT("d.id")
//		.SELECT("d.name")
//		.FROM(new DataDictionaryEntity(), "d")
//		.LEFT_JOIN(new DataDictionaryEntity(), "dd", "d.pid=dd.id")
//		.WHERE("dd.code=?",code);
//		List<Map<String, Object>>ls=dataDictionaryDao.findBySQLGod(sm);
//		if(ls.size()>0){
//			for(Map<String, Object>m:ls){
//				map.put((String)m.get("id"), (String)m.get("name"));
//			}
//		}
//		return map;
//	}
	public static Map<String, String>technicalNameMap(){
		return getDataMap("js-zg-zy");
	}
	public static Map<String, String>regCertificateLevelMap(){
		return getDataMap("jz-zy");
	}
	public static Map<String, String>employeeStatusMap(){
		return getDataMap("yg-zt");
	}
	public static Map<String, String>companyMajorTypeMap(){
		return getDataMap("companyMajorType");
	}
	private static Map<String, String>getDataMap(String code){
		if(!constantMap.containsKey(code)){
			constantMap.put(code, dataDictionaryService.ddMap(code));
		}
		return constantMap.get(code);
	}
}
