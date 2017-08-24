package com.maoding.system.bean.response;//package com.maoding.system.bean.response;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.maoding.core.common.bean.BaseBean;
//import com.maoding.personal.user.entity.UserAttachEntity;
//import com.maoding.personal.user.entity.UserContinueeducationEntity;
//import com.maoding.personal.user.entity.UserEducationbgEntity;
//import com.maoding.personal.user.entity.UserQualificationsEntity;
//import com.maoding.personal.user.entity.UserTechnicalEntity;
//import com.maoding.personal.user.entity.UserWorkexperienceEntity;
//import com.maoding.personal.user.entity.UserWorkperformanceEntity;
//import com.maoding.system.constant.WsConstant;
//
///**
// * 深圳市设计同道技术有限公司
// * 类    名：PeopleDetailBean
// * 类描述：人员详情
// * 作    者：Chenxj
// * 日    期：2015年8月10日-上午8:50:45
// */
//public class PeopleDetailBean extends BaseBean{
//	private static final long serialVersionUID = -1196810348154930777L;
//	/**人员ID*/
//	private String id;
//	/**姓名*/
//	private String username;
//	/**性别*/
//	private String sex;
//	/**所属部门ID*/
//	private String departId;
//	/**所属部门*/
//	private String departName;
//	/**入职时间*/
//	private String entryTime;
//	/**从事专业*/
//	private String technSpecialty;
//	/**员工类别*/
//	private String employeeType;
//	/**担任职位*/
//	private String serverStation;
//	/**籍贯*/
//	private String nativeplace;
//	/**名族*/
//	private String nation;
//	/**户口所在地*/
//	private String residencyLocation;
//	/**出生年月*/
//	private String birthdate;
//	/**现居住地*/
//	private String address;
//	/**婚姻状况0已1未2密*/
//	private String marriagestatus;
//	/**身份证号码*/
//	private String idcard;
//	/**政治面貌*/
//	private String politicalStatus;
//	/**毕业院校*/
//	private String graduationSchool;
//	/**毕业时间*/
//	private String graduationTime;
//	/**所学专业*/
//	private String major;
//	/**最高学历*/
//	private String lastDegree;
//	/**参加工作时间*/
//	private String workBegindate;
//	/**电子邮箱*/
//	private String email;
//	/**手机号码*/
//	private String cellphone;
//	/**社保号*/
//	private String insuranceNo;
//	/**英语水平*/
//	private String foreignLanLevel;
//	/**紧急联系人*/
//	private String emergencyContact;
//	/**联系电话*/
//	private String emergencyPhone;
//	/**爱好及专长*/
//	private String hobby;
//	/**自我评价*/
//	private String selfBrif;
//	/**员工状态（在职或离职）*/
//	private String employeeStatus;
//	/**离职时间*/
//	private String departureTime;
//	/**最高学位*/
//	private String highestDegree;
//	/**社保参保地*/
//	private String insuranceAddress;
//	/**公积金账号*/
//	private String accumulationFund;
//	/**教育背景*/
//	private List<Map<String, Object>> educationbg=new ArrayList<Map<String,Object>>();
//	/**工资经历*/
//	private List<Map<String, Object>> workexperience=new ArrayList<Map<String,Object>>();
//	/**继续教育经历*/
//	private List<Map<String, Object>> continueeducation=new ArrayList<Map<String,Object>>();
//	/**项目经验*/
//	private List<Map<String, Object>> workperformance=new ArrayList<Map<String,Object>>();
//	/**职业资格*/
//	private List<Map<String, Object>> qualifications=new ArrayList<Map<String,Object>>();
//	/**技术资格*/
//	private List<Map<String, Object>> technical=new ArrayList<Map<String,Object>>();
//	/**附件列表*/
//	private List<Map<String, Object>> attachs=new ArrayList<Map<String,Object>>();
//	/**
//	 * 获取：id
//	 */
//	public String getId() {
//		return id;
//	}
//	/**
//	 * 设置：id
//	 */
//	public void setId(String id) {
//		this.id = id;
//	}
//	/**
//	 * 获取：username
//	 */
//	public String getUsername() {
//		return username;
//	}
//	/**
//	 * 设置：username
//	 */
//	public void setUsername(String username) {
//		this.username = username;
//	}
//	/**
//	 * 获取：sex
//	 */
//	public String getSex() {
//		return sex;
//	}
//	/**
//	 * 设置：sex
//	 */
//	public void setSex(String sex) {
//		this.sex = sex;
//	}
//	/**
//	 * 获取：departid
//	 */
//	public String getDepartId() {
//		return departId;
//	}
//	/**
//	 * 设置：departid
//	 */
//	public void setDepartId(String departId) {
//		this.departId = departId;
//	}
//
//	/**
//	 * 获取：departName
//	 */
//	public String getDepartName() {
//		return departName;
//	}
//	/**
//	 * 设置：departName
//	 */
//	public void setDepartName(String departName) {
//		this.departName = departName;
//	}
//	/**
//	 * 获取：entryTime
//	 */
//	public String getEntryTime() {
//		return entryTime;
//	}
//	/**
//	 * 设置：entryTime
//	 */
//	public void setEntryTime(String entryTime) {
//		this.entryTime = entryTime;
//	}
//	/**
//	 * 获取：employeeType
//	 */
//	public String getEmployeeType() {
//		return employeeType;
//	}
//	/**
//	 * 设置：employeeType
//	 */
//	public void setEmployeeType(String employeeType) {
//		this.employeeType = employeeType;
//	}
//	/**
//	 * 获取：serverStation
//	 */
//	public String getServerStation() {
//		return serverStation;
//	}
//	/**
//	 * 设置：serverStation
//	 */
//	public void setServerStation(String serverStation) {
//		this.serverStation = serverStation;
//	}
//	/**
//	 * 获取：nativeplace
//	 */
//	public String getNativeplace() {
//		return nativeplace;
//	}
//	/**
//	 * 设置：nativeplace
//	 */
//	public void setNativeplace(String nativeplace) {
//		this.nativeplace = nativeplace;
//	}
//	/**
//	 * 获取：nation
//	 */
//	public String getNation() {
//		return nation;
//	}
//	/**
//	 * 设置：nation
//	 */
//	public void setNation(String nation) {
//		this.nation = nation;
//	}
//	/**
//	 * 获取：residencyLocation
//	 */
//	public String getResidencyLocation() {
//		return residencyLocation;
//	}
//	/**
//	 * 设置：residencyLocation
//	 */
//	public void setResidencyLocation(String residencyLocation) {
//		this.residencyLocation = residencyLocation;
//	}
//	/**
//	 * 获取：birthdate
//	 */
//	public String getBirthdate() {
//		return birthdate;
//	}
//	/**
//	 * 设置：birthdate
//	 */
//	public void setBirthdate(String birthdate) {
//		this.birthdate = birthdate;
//	}
//	/**
//	 * 获取：address
//	 */
//	public String getAddress() {
//		return address;
//	}
//	/**
//	 * 设置：address
//	 */
//	public void setAddress(String address) {
//		this.address = address;
//	}
//	/**
//	 * 获取：marriagestatus
//	 */
//	public String getMarriagestatus() {
//		return marriagestatus;
//	}
//	/**
//	 * 设置：marriagestatus
//	 */
//	public void setMarriagestatus(String marriagestatus) {
//		this.marriagestatus = marriagestatus;
//	}
//	/**
//	 * 获取：idcard
//	 */
//	public String getIdcard() {
//		return idcard;
//	}
//	/**
//	 * 设置：idcard
//	 */
//	public void setIdcard(String idcard) {
//		this.idcard = idcard;
//	}
//	/**
//	 * 获取：graduationSchool
//	 */
//	public String getGraduationSchool() {
//		return graduationSchool;
//	}
//	/**
//	 * 设置：graduationSchool
//	 */
//	public void setGraduationSchool(String graduationSchool) {
//		this.graduationSchool = graduationSchool;
//	}
//	/**
//	 * 获取：major
//	 */
//	public String getMajor() {
//		return major;
//	}
//	/**
//	 * 设置：major
//	 */
//	public void setMajor(String major) {
//		this.major = major;
//	}
//	/**
//	 * 获取：lastDegree
//	 */
//	public String getLastDegree() {
//		return lastDegree;
//	}
//	/**
//	 * 设置：lastDegree
//	 */
//	public void setLastDegree(String lastDegree) {
//		this.lastDegree = lastDegree;
//	}
//	/**
//	 * 获取：selfBrif
//	 */
//	public String getSelfBrif() {
//		return selfBrif;
//	}
//	/**
//	 * 设置：selfBrif
//	 */
//	public void setSelfBrif(String selfBrif) {
//		this.selfBrif = selfBrif;
//	}
//	/**
//	 * 获取：employeeStatus
//	 */
//	public String getEmployeeStatus() {
//		return employeeStatus;
//	}
//	/**
//	 * 设置：employeeStatus
//	 */
//	public void setEmployeeStatus(String employeeStatus) {
//		this.employeeStatus = employeeStatus;
//	}
//	/**
//	 * 获取：departureTime
//	 */
//	public String getDepartureTime() {
//		return departureTime;
//	}
//	/**
//	 * 设置：departureTime
//	 */
//	public void setDepartureTime(String departureTime) {
//		this.departureTime = departureTime;
//	}
//	/**
//	 * 获取：highestDegree
//	 */
//	public String getHighestDegree() {
//		return highestDegree;
//	}
//	/**
//	 * 设置：highestDegree
//	 */
//	public void setHighestDegree(String highestDegree) {
//		this.highestDegree = highestDegree;
//	}
//	/**
//	 * 获取：insuranceAddress
//	 */
//	public String getInsuranceAddress() {
//		return insuranceAddress;
//	}
//	/**
//	 * 设置：insuranceAddress
//	 */
//	public void setInsuranceAddress(String insuranceAddress) {
//		this.insuranceAddress = insuranceAddress;
//	}
//	/**
//	 * 获取：accumulationFund
//	 */
//	public String getAccumulationFund() {
//		return accumulationFund;
//	}
//	/**
//	 * 设置：accumulationFund
//	 */
//	public void setAccumulationFund(String accumulationFund) {
//		this.accumulationFund = accumulationFund;
//	}
//	/**
//	 * 获取：educationbg
//	 */
//	public List<Map<String, Object>> getEducationbg() {
//		return educationbg;
//	}
//	/**
//	 * 设置：educationbg
//	 */
//	public void setEducationbg(List<Map<String, Object>> educationbg) {
//		this.educationbg = educationbg;
//	}
//	/**
//	 * 设置：educationbg
//	 */
//	public void setEducationbgByEntityList(List<UserEducationbgEntity> educationbgs) {
//		for(UserEducationbgEntity educationbg:educationbgs){
//			Map<String, Object>m=new HashMap<String, Object>();
//			m.put("educationBGStartDate", educationbg.getEducationbgStartDate());
//			m.put("educationBGEndDate", educationbg.getEducationbgEndDate());
//			m.put("educationBGSchool", educationbg.getEducationbgSchool());
//			m.put("educationBGMajor", educationbg.getEducationbgMajor());
//			this.educationbg.add(m);
//		}
//	}
//	/**
//	 * 获取：workexperience
//	 */
//	public List<Map<String, Object>> getWorkexperience() {
//		return workexperience;
//	}
//	/**
//	 * 设置：workexperience
//	 */
//	public void setWorkexperience(List<Map<String, Object>> workexperience) {
//		this.workexperience = workexperience;
//	}
//	/**
//	 * 设置：workexperience
//	 */
//	public void setWorkexperienceByEntityList(List<UserWorkexperienceEntity> workexperiences) {
//		for(UserWorkexperienceEntity workexperience:workexperiences){
//			Map<String, Object>m=new HashMap<String, Object>();
//			m.put("workStartDate", workexperience.getWorkStartdate());
//			m.put("workEndDate", workexperience.getWorkEnddate());
//			m.put("workCompany", workexperience.getWorkCompany());
//			m.put("workStation", workexperience.getWorkStation());
//			m.put("workWitness", workexperience.getWorkWitness());
//			m.put("witnessPhone", workexperience.getWitnessPhone());
//			this.workexperience.add(m);
//		}
//	}
//	/**
//	 * 获取：continueeducation
//	 */
//	public List<Map<String, Object>> getContinueeducation() {
//		return continueeducation;
//	}
//	/**
//	 * 设置：continueeducation
//	 */
//	public void setContinueeducation(List<Map<String, Object>> continueeducation) {
//		this.continueeducation = continueeducation;
//	}
//	/**
//	 * 设置：continueeducation
//	 */
//	public void setContinueeducationByEntityList(List<UserContinueeducationEntity> continueeducations) {
//		for(UserContinueeducationEntity continueeducation:continueeducations){
//			Map<String, Object>m=new HashMap<String, Object>();
//			m.put("continuingEducationTrainingName", continueeducation.getContinuingeducationTrainingName());
//			m.put("continuingEducationStartDate", continueeducation.getContinuingeducationStartdate());
//			m.put("continuingEducationEndDate", continueeducation.getContinuingeducationEnddate());
//			m.put("continuingEducationCardNum", continueeducation.getContinuingeducationCardnum());
//			m.put("continuingEducationPeriod", continueeducation.getContinuingeducationPeriod());
//			m.put("continuingEducationCompany", continueeducation.getContinuingeducationCompany());
//			m.put("continuingEducationTrainingContent", continueeducation.getContinuingeducationTrainingContent());
//			this.continueeducation.add(m);
//		}
//	}
//	/**
//	 * 获取：workperformance
//	 */
//	public List<Map<String, Object>> getWorkperformance() {
//		return workperformance;
//	}
//	/**
//	 * 设置：workperformance
//	 */
//	public void setWorkperformance(List<Map<String, Object>> workperformance) {
//		this.workperformance = workperformance;
//	}
//	/**
//	 * 设置：workperformance
//	 */
//	public void setWorkperformanceByEntityList(List<UserWorkperformanceEntity> workperformances) {
//		for(UserWorkperformanceEntity workperformance:workperformances){
//			Map<String, Object>m=new HashMap<String, Object>();
//			m.put("workPerformanceProjectName", workperformance.getWorkperformanceProjectName());
//			m.put("workPerformanceMain", workperformance.getWorkperformanceMain());
//			m.put("workPerformanceStartDate", workperformance.getWorkperformanceStartdate());
//			m.put("workPerformanceEndDate", workperformance.getWorkperformanceEnddate());
//			m.put("selfEffect", workperformance.getWorkperformanceMain());
//			m.put("qualifications", workperformance.getQualifications());
//			m.put("workWitness", workperformance.getWorkperformanceWitness());
//			m.put("witnessPhone", workperformance.getWitnessPhone());
//			this.workperformance.add(m);
//		}
//	}
//	/**
//	 * 获取：qualifications
//	 */
//	public List<Map<String, Object>> getQualifications() {
//		return qualifications;
//	}
//	/**
//	 * 设置：qualifications
//	 */
//	public void setQualifications(List<Map<String, Object>> qualifications) {
//		this.qualifications = qualifications;
//	}
//	/**
//	 * 设置：qualifications
//	 */
//	public void setQualificationsByEntityList(List<UserQualificationsEntity> qualifications) {
//		for(UserQualificationsEntity qualification:qualifications){
//			Map<String, Object>m=new HashMap<String, Object>();
//			m.put("certificateMajor", WsConstant.technicalNameMap().get(qualification.getCertificateMajor()));
//			m.put("regCertificateLevel", WsConstant.regCertificateLevelMap().get(qualification.getRegCertificateLevel()));
//			m.put("certificateNo", qualification.getCertificateNo());
//			m.put("practiceSealNo", qualification.getPracticeSealNo());
//			m.put("practiceSealFndate", qualification.getPracticeSealFndate());
//			this.qualifications.add(m);
//		}
//	}
//	/**
//	 * 获取：technical
//	 */
//	public List<Map<String, Object>> getTechnical() {
//		return technical;
//	}
//	/**
//	 * 设置：technical
//	 */
//	public void setTechnical(List<Map<String, Object>> technical) {
//		this.technical = technical;
//	}
//	/**
//	 * 设置：technical
//	 */
//	public void setTechnicalByEntityList(List<UserTechnicalEntity> technicals) {
//		for(UserTechnicalEntity technial:technicals){
//			Map<String, Object>m=new HashMap<String, Object>();
//			m.put("technicalLevel",WsConstant.technicalLevelMap().get(technial.getTechnicalLevel()));
//			m.put("technicalMajor",WsConstant.technicalNameMap().get(technial.getTechnicalName()));
//			m.put("technicalNo",technial.getTechnicalNo());
//			m.put("technicalAuthority",technial.getTechnicalAuthority());
//			this.technical.add(m);
//		}
//	}
//	/**
//	 * 获取：attachs
//	 */
//	public List<Map<String, Object>> getAttachs() {
//		return attachs;
//	}
//	/**
//	 * 设置：attachs
//	 */
//	public void setAttachs(List<UserAttachEntity> attachs) {
//		for(int i=0;i<attachs.size();i++){
//			Map<String,Object> map=new HashMap<String,Object>();
//			map.put("type", attachs.get(i).getAttachType());
//			map.put("path", attachs.get(i).getAttachPath());
//			this.attachs.add(map);
//		}
//	}
//	//新添加字段
//	/**
//	 * 获取：technSpecialty
//	 */
//	public String getTechnSpecialty() {
//		return technSpecialty;
//	}
//	/**
//	 * 设置：technSpecialty
//	 */
//	public void setTechnSpecialty(String technSpecialty) {
//		this.technSpecialty = technSpecialty;
//	}
//	/**
//	 * 获取：politicalStatus
//	 */
//	public String getPoliticalStatus() {
//		return politicalStatus;
//	}
//	/**
//	 * 设置：politicalStatus
//	 */
//	public void setPoliticalStatus(String politicalStatus) {
//		this.politicalStatus = politicalStatus;
//	}
//	/**
//	 * 获取：graduationTime
//	 */
//	public String getGraduationTime() {
//		return graduationTime;
//	}
//	/**
//	 * 设置：graduationTime
//	 */
//	public void setGraduationTime(String graduationTime) {
//		this.graduationTime = graduationTime;
//	}
//	/**
//	 * 获取：workBegindate
//	 */
//	public String getWorkBegindate() {
//		return workBegindate;
//	}
//	/**
//	 * 设置：workBegindate
//	 */
//	public void setWorkBegindate(String workBegindate) {
//		this.workBegindate = workBegindate;
//	}
//	/**
//	 * 获取：email
//	 */
//	public String getEmail() {
//		return email;
//	}
//	/**
//	 * 设置：email
//	 */
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	/**
//	 * 获取：cellphone
//	 */
//	public String getCellphone() {
//		return cellphone;
//	}
//	/**
//	 * 设置：cellphone
//	 */
//	public void setCellphone(String cellphone) {
//		this.cellphone = cellphone;
//	}
//	/**
//	 * 获取：insuranceNo
//	 */
//	public String getInsuranceNo() {
//		return insuranceNo;
//	}
//	/**
//	 * 设置：insuranceNo
//	 */
//	public void setInsuranceNo(String insuranceNo) {
//		this.insuranceNo = insuranceNo;
//	}
//	/**
//	 * 获取：foreignLanLevel
//	 */
//	public String getForeignLanLevel() {
//		return foreignLanLevel;
//	}
//	/**
//	 * 设置：foreignLanLevel
//	 */
//	public void setForeignLanLevel(String foreignLanLevel) {
//		this.foreignLanLevel = foreignLanLevel;
//	}
//	/**
//	 * 获取：emergencyContact
//	 */
//	public String getEmergencyContact() {
//		return emergencyContact;
//	}
//	/**
//	 * 设置：emergencyContact
//	 */
//	public void setEmergencyContact(String emergencyContact) {
//		this.emergencyContact = emergencyContact;
//	}
//	/**
//	 * 获取：emergencyPhone
//	 */
//	public String getEmergencyPhone() {
//		return emergencyPhone;
//	}
//	/**
//	 * 设置：emergencyPhone
//	 */
//	public void setEmergencyPhone(String emergencyPhone) {
//		this.emergencyPhone = emergencyPhone;
//	}
//	/**
//	 * 获取：hobby
//	 */
//	public String getHobby() {
//		return hobby;
//	}
//	/**
//	 * 设置：hobby
//	 */
//	public void setHobby(String hobby) {
//		this.hobby = hobby;
//	}
//
//}
