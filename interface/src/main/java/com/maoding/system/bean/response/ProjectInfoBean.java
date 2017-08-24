package com.maoding.system.bean.response;//package com.maoding.system.bean.response;
//
//import static com.maoding.core.util.MapUtil.stringMap;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import com.maoding.core.common.bean.BaseBean;
//import com.maoding.project.project.entity.DesignBasisEntity;
//import com.maoding.project.project.entity.DesignRangeEntity;
//import com.maoding.project.project.entity.ProjectAttachEntity;
//import com.maoding.project.project.entity.ProjectConstructEntity;
//import com.maoding.project.project.entity.ProjectDcontentEntity;
//import com.maoding.project.project.entity.ProjectEntity;
//import com.maoding.system.entity.DataDictionaryEntity;
//
///**深圳市设计同道技术有限公司
// * 类    名：ProjectInfoBean
// * 类描述：
// * 作    者：Chenxj
// * 日    期：2016年4月26日-下午5:30:12
// */
//public class ProjectInfoBean extends BaseBean{
//	private static final long serialVersionUID = -6340225785679390236L;
//	/**项目名称*/
//	private String projectName;
//	/**建设单位*/
////	private List<Map<String, String>>companyName=new ArrayList<Map<String,String>>();
//	/**项目编号*/
//	private String projectNo;
//	/**建筑功能*/
//	private String builtType;
//	/**建筑功能ID*/
//	private String builtTypeId;
//	/**设计范围*/
//	private List<String>designRange=new ArrayList<String>();
//	/**自定义设计范围*/
//	private List<String>desingRangeSelf=new ArrayList<String>();
//	/**设计阶段*/
//	private List<Map<String, String>>dcontent=new ArrayList<Map<String,String>>();
//	/**设计依据*/
//	private List<Map<String, String>>designBasis=new ArrayList<Map<String,String>>();
//	/**自定义设计依据*/
//	private List<Map<String, String>>designBasisSefl=new ArrayList<Map<String,String>>();
//	/**基地面积*/
//	private Float baseArea;
//	/**总建筑面积*/
//	private Float totalConstructionArea;
//	/**计算面积*/
//	private Float capacityArea;
//	/**覆盖率*/
//	private Float coverage;
//	/**绿化率*/
//	private Float greeningRate;
//	/**增核面积*/
//	private Float increasingArea;
//	/**建筑高度*/
//	private String builtHeight;
//	/**地上层数*/
//	private String builtFloorUp;
//	/**地下层数*/
//	private String builtFloorDown;
//	/**投资估算*/
//	private BigDecimal investmentEstimation;
//	/**签订日期*/
//	private String signDate;
//	/**合同附件*/
////	private List<Map<String, String>>contractAttachment=new ArrayList<Map<String,String>>();
//	private String fileName;
//	private String filePath;
//	/**备案日期*/
//	private String recordDate;
//	/**合同金额*/
//	private BigDecimal totalContractAmount;
//
//    private String companyBid;
//	 private String companyBName;
//	 private String projectTypeName;
//
//     private String projectType;;
//	 /**
//     * 建设单位
//     */
//    private String constructCompany;
//    private String constructName;//建设单位名称
//
//	/**
//	 * 方法描述：设置项目信息
//	 * 作        者：Chenxj
//	 * 日        期：2016年4月26日-下午6:09:38
//	 * @param project
//	 */
//	public void setProject(ProjectEntity project){
//		this.projectName=project.getProjectName();
//		this.projectNo=project.getProjectNo();
//		this.builtType=project.getBuiltType();
//		this.baseArea=project.getBaseArea();
//		this.totalConstructionArea=project.getTotalConstructionArea();
//		this.capacityArea=project.getCapacityArea();
//		this.coverage=project.getCoverage();
//		this.greeningRate=project.getGreeningRate();
//		this.increasingArea=project.getIncreasingArea();
//		this.builtHeight=project.getBuiltHeight();
//		this.builtFloorUp=project.getBuiltFloorUp();
//		this.builtFloorDown=project.getBuiltFloorDown();
//		this.investmentEstimation=project.getInvestmentEstimation();
//		this.signDate=project.getSignDate();
//		this.totalContractAmount=project.getTotalContractAmount();
//		this.constructCompany=project.getConstructCompany();
//		this.constructName=project.getConstructName();
//		this.recordDate=project.getRecordDate();
//		this.companyBid=project.getCompanyBid();
//		this.companyBName=project.getCompanyBName();
//		this.projectType=project.getProjectType();
//		this.projectTypeName=project.getProjectTypeName();
//	}
//	/**
//	 * 方法描述：设置设计依据
//	 * 作        者：Chenxj
//	 * 日        期：2016年4月26日-下午6:09:55
//	 * @param dbs
//	 */
//	public void setDesignBasisList(List<DesignBasisEntity> dbs){
//		for(DesignBasisEntity dbe:dbs){
//			this.designBasis.add(stringMap("designBasis",dbe.getDesignBasis(),
//					"basisDate",dbe.getBasisDate(),
//					"fileName",dbe.getFileName(),
//					"filePath",dbe.getFilePath()));
//		}
//	}
//	/**方法描述：设置自定义设计依据
//	 * 作        者：Chenxj
//	 * 日        期：2016年5月16日-下午6:09:48
//	 * @param dds_dbs
//	 * @param dbs
//	 */
//	public void setDesignBasisSelfList(List<DataDictionaryEntity> dds_dbs,List<DesignBasisEntity> dbs) {
//		Set<String>cdbNames=new HashSet<String>();
//		for(DataDictionaryEntity dd:dds_dbs){
//			cdbNames.add(dd.getName());
//		}
//		for(DesignBasisEntity dbe:dbs){
//			if(!cdbNames.contains(dbe.getDesignBasis())){
//				this.designBasisSefl.add(stringMap("designBasis",dbe.getDesignBasis(),
//						"basisDate",dbe.getBasisDate(),
//						"fileName",dbe.getFileName(),
//						"filePath",dbe.getFilePath()));
//			}
//		}
//	}
//	/**
//	 * 方法描述：设置设计范围
//	 * 作        者：Chenxj
//	 * 日        期：2016年4月26日-下午6:10:06
//	 * @param drs
//	 */
//	public void setDesignRangeList(List<DesignRangeEntity> drs){
//		for(DesignRangeEntity dre:drs){
//			this.designRange.add(dre.getDesignRange());
//		}
//	}
//	/**
//	 * 方法描述：设置自定义设计范围
//	 * 作        者：Chenxj
//	 * 日        期：2016年4月26日-下午6:10:16
//	 * @param cdrs
//	 * @param drs
//	 */
//	public void setDesignRangeSelfList(List<DataDictionaryEntity> dds,List<DesignRangeEntity> drs){
//		Set<String>cdrNames=new HashSet<String>();
//		for(DataDictionaryEntity dd:dds){
//			cdrNames.add(dd.getName());
//		}
//		for(DesignRangeEntity dre:drs){
//			if(!cdrNames.contains(dre.getDesignRange())){
//				this.desingRangeSelf.add(dre.getDesignRange());
//			}
//		}
//	}
//	/**
//	 * 方法描述：设置设计阶段
//	 * 作        者：Chenxj
//	 * 日        期：2016年4月26日-下午6:10:35
//	 * @param pds
//	 */
//	public void setDesignDcontentList(List<ProjectDcontentEntity> pds){
//		for(ProjectDcontentEntity pde:pds){
//			this.dcontent.add(stringMap("contentName",pde.getContentName(),
//					"planProgressStarttime",pde.getPlanProgressStarttime(),
//					"planProgressEndtime",pde.getPlanProgressEndtime(),
//					"memo",pde.getMemo(),
//					"contentId",pde.getId()));
//		}
//	}
//	/**
//	 * 方法描述：设置合同附件
//	 * 作        者：Chenxj
//	 * 日        期：2016年4月26日-下午6:10:44
//	 * @param pas
//	 */
//	public void setContractAttach(ProjectAttachEntity contractAtttch){
//		if(contractAtttch!=null){
//			this.fileName=contractAtttch.getFileName();
//			this.filePath=contractAtttch.getFilePath();
////			this.contractAttachment.add(stringMap("fileName",contractAtttch.getFileName(),
////					"filePath",contractAtttch.getFilePath()));
//		}
//	}
//	/**
//	 * 方法描述：设置建设单位
//	 * 作        者：Chenxj
//	 * 日        期：2016年4月26日-下午6:19:18
//	 * @param pcs
//	 */
//	public void setProjectConstructList(List<ProjectConstructEntity> pcs){
//		for(ProjectConstructEntity pce:pcs){
//			this.constructCompany=pce.getId();
//			this.constructName=pce.getCompanyName();
//			return;
////			this.companyName.add(stringMap("id",pce.getId(),
////					"name",pce.getCompanyName()));
//		}
//	}
//	/**
//	 * 获取：项目名称
//	 */
//	public String getProjectName() {
//		return projectName;
//	}
//	/**
//	 * 设置：项目名称
//	 */
//	public void setProjectName(String projectName) {
//		this.projectName = projectName;
//	}
//	/**
//	 * 获取：项目编号
//	 */
//	public String getProjectNo() {
//		return projectNo;
//	}
//	/**
//	 * 设置：项目编号
//	 */
//	public void setProjectNo(String projectNo) {
//		this.projectNo = projectNo;
//	}
//	/**
//	 * 获取：建筑功能
//	 */
//	public String getBuiltType() {
//		return builtType;
//	}
//	/**
//	 * 设置：建筑功能
//	 */
//	public void setBuiltType(String builtType) {
//		this.builtType = builtType;
//	}
//	/**
//	 * 获取：建筑功能ID
//	 */
//	public String getBuiltTypeId() {
//		return builtTypeId;
//	}
//	/**
//	 * 设置：建筑功能ID
//	 */
//	public void setBuiltTypeId(String builtTypeId) {
//		this.builtTypeId = builtTypeId;
//	}
//	/**
//	 * 获取：设计范围
//	 */
//	public List<String> getDesignRange() {
//		return designRange;
//	}
//	/**
//	 * 设置：设计范围
//	 */
//	public void setDesignRange(List<String> designRange) {
//		this.designRange = designRange;
//	}
//	/**
//	 * 获取：自定义设计范围
//	 */
//	public List<String> getDesingRangeSelf() {
//		return desingRangeSelf;
//	}
//	/**
//	 * 设置：自定义设计范围
//	 */
//	public void setDesingRangeSelf(List<String> desingRangeSelf) {
//		this.desingRangeSelf = desingRangeSelf;
//	}
//	/**
//	 * 获取：设计阶段
//	 */
//	public List<Map<String, String>> getDcontent() {
//		return dcontent;
//	}
//	/**
//	 * 设置：设计阶段
//	 */
//	public void setDcontent(List<Map<String, String>> dcontent) {
//		this.dcontent = dcontent;
//	}
//	/**
//	 * 获取：设计依据
//	 */
//	public List<Map<String, String>> getDesignBasis() {
//		return designBasis;
//	}
//	/**
//	 * 设置：设计依据
//	 */
//	public void setDesignBasis(List<Map<String, String>> designBasis) {
//		this.designBasis = designBasis;
//	}
//	/**
//	 * 获取：基地面积
//	 */
//	public Float getBaseArea() {
//		return baseArea;
//	}
//	/**
//	 * 设置：基地面积
//	 */
//	public void setBaseArea(Float baseArea) {
//		this.baseArea = baseArea;
//	}
//	/**
//	 * 获取：总建筑面积
//	 */
//	public Float getTotalConstructionArea() {
//		return totalConstructionArea;
//	}
//	/**
//	 * 设置：总建筑面积
//	 */
//	public void setTotalConstructionArea(Float totalConstructionArea) {
//		this.totalConstructionArea = totalConstructionArea;
//	}
//	/**
//	 * 获取：计算面积
//	 */
//	public Float getCapacityArea() {
//		return capacityArea;
//	}
//	/**
//	 * 设置：计算面积
//	 */
//	public void setCapacityArea(Float capacityArea) {
//		this.capacityArea = capacityArea;
//	}
//	/**
//	 * 获取：覆盖率
//	 */
//	public Float getCoverage() {
//		return coverage;
//	}
//	/**
//	 * 设置：覆盖率
//	 */
//	public void setCoverage(Float coverage) {
//		this.coverage = coverage;
//	}
//	/**
//	 * 获取：绿化率
//	 */
//	public Float getGreeningRate() {
//		return greeningRate;
//	}
//	/**
//	 * 设置：绿化率
//	 */
//	public void setGreeningRate(Float greeningRate) {
//		this.greeningRate = greeningRate;
//	}
//	/**
//	 * 获取：增核面积
//	 */
//	public Float getIncreasingArea() {
//		return increasingArea;
//	}
//	/**
//	 * 设置：增核面积
//	 */
//	public void setIncreasingArea(Float increasingArea) {
//		this.increasingArea = increasingArea;
//	}
//	/**
//	 * 获取：建筑高度
//	 */
//	public String getBuiltHeight() {
//		return builtHeight;
//	}
//	/**
//	 * 设置：建筑高度
//	 */
//	public void setBuiltHeight(String builtHeight) {
//		this.builtHeight = builtHeight;
//	}
//	/**
//	 * 获取：地上层数
//	 */
//	public String getBuiltFloorUp() {
//		return builtFloorUp;
//	}
//	/**
//	 * 设置：地上层数
//	 */
//	public void setBuiltFloorUp(String builtFloorUp) {
//		this.builtFloorUp = builtFloorUp;
//	}
//	/**
//	 * 获取：地下层数
//	 */
//	public String getBuiltFloorDown() {
//		return builtFloorDown;
//	}
//	/**
//	 * 设置：地下层数
//	 */
//	public void setBuiltFloorDown(String builtFloorDown) {
//		this.builtFloorDown = builtFloorDown;
//	}
//	/**
//	 * 获取：投资估算
//	 */
//	public BigDecimal getInvestmentEstimation() {
//		return investmentEstimation;
//	}
//	/**
//	 * 设置：投资估算
//	 */
//	public void setInvestmentEstimation(BigDecimal investmentEstimation) {
//		this.investmentEstimation = investmentEstimation;
//	}
//	/**
//	 * 获取：签订日期
//	 */
//	public String getSignDate() {
//		return signDate;
//	}
//	/**
//	 * 设置：签订日期
//	 */
//	public void setSignDate(String signDate) {
//		this.signDate = signDate;
//	}
////	/**
////	 * 获取：合同附件
////	 */
////	public List<Map<String, String>> getContractAttachment() {
////		return contractAttachment;
////	}
////	/**
////	 * 设置：合同附件
////	 */
////	public void setContractAttachment(List<Map<String, String>> contractAttachment) {
////		this.contractAttachment = contractAttachment;
////	}
//
//	/**
//	 * 获取：合同金额
//	 */
//	public BigDecimal getTotalContractAmount() {
//		return totalContractAmount;
//	}
//	/**
//	 * 获取：fileName
//	 */
//	public String getFileName() {
//		return fileName;
//	}
//	/**
//	 * 设置：fileName
//	 */
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}
//	/**
//	 * 获取：filePath
//	 */
//	public String getFilePath() {
//		return filePath;
//	}
//	/**
//	 * 设置：filePath
//	 */
//	public void setFilePath(String filePath) {
//		this.filePath = filePath;
//	}
//	/**
//	 * 获取：recordDate
//	 */
//	public String getRecordDate() {
//		return recordDate;
//	}
//	/**
//	 * 设置：recordDate
//	 */
//	public void setRecordDate(String recordDate) {
//		this.recordDate = recordDate;
//	}
//	/**
//	 * 设置：合同金额
//	 */
//	public void setTotalContractAmount(BigDecimal totalContractAmount) {
//		this.totalContractAmount = totalContractAmount;
//	}
//	public String getConstructCompany() {
//		return constructCompany;
//	}
//	public void setConstructCompany(String constructCompany) {
//		this.constructCompany = constructCompany;
//	}
//	public String getConstructName() {
//		return constructName;
//	}
//	public void setConstructName(String constructName) {
//		this.constructName = constructName;
//	}
//	public String getCompanyBid() {
//		return companyBid;
//	}
//	public void setCompanyBid(String companyBid) {
//		this.companyBid = companyBid;
//	}
//	public String getCompanyBName() {
//		return companyBName;
//	}
//	public void setCompanyBName(String companyBName) {
//		this.companyBName = companyBName;
//	}
//	public String getProjectTypeName() {
//		return projectTypeName;
//	}
//	public void setProjectTypeName(String projectTypeName) {
//		this.projectTypeName = projectTypeName;
//	}
//	public String getProjectType() {
//		return projectType;
//	}
//	public void setProjectType(String projectType) {
//		this.projectType = projectType;
//	}
//}
