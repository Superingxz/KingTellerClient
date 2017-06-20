package com.kingteller.client.bean.workorder;

import java.io.Serializable;
import java.util.Date;

public class AssignWorkerNameBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;            // 用户id
	private String userIdLike;
	private String userAccount;       // 登录系统账号
	private String userAccountLike;
	private String userName;          // 姓名
	private String userNameLike;
	private String userPassword;      // 登录系统密码
	private String userPasswordLike;
	private Long sexflag;             // 性别：0 男 1女
	private String userOrgid;         // 用户所属机构id
	private String userOrgidLike;
	private Long userStatus;          // 用户状态
	private String userType;          // 用户类别: Y  表示御银人员   YW 表示御银维护工程师(客服维护部)    O 表示公司外部用户   B 表示银行用户   A 表示代理商用户
	private String userTypeLike;
	private String linkPhone;         // 工作手机号码
	private String linkPhoneLike;
	private String email;             // email
	private String emailLike;
	private Long pwdStatus;           // 密码状态
	private String userDesc;          // 说明
	private String userDescLike;
	private Date lastlogindate;       // 最近一次登录时间
	private Date lastlogindateRange1;
	private Date lastlogindateRange2;
	private  String lastlogindateStr;
	private String userArea;          // 用户所属的区域id
	private String userAreaLike;
	private String linkPhoneType;     // 手机类型：(采用配置字典值： SMART_PHONE,  NOT_SMART_PHONE)
	private String linkPhoneTypeLike;
	private String telNo;             // 个人电话
	private String telNoLike;
	private String chargeBankId;     //驻点银行id
	private Date inDate;                // 入职日期
	private Date inDateRange1;
	private Date inDateRange2;
	private  String inDateStr;
	private Date outDate;               
	private Date outDateRange1;
	private Date outDateRange2;
	private  String outDateStr;
	private String idCardNo;
	private String idCardNoLike;
	private String userPost;            // 用户岗位
	private String userPostLike;
	private Date toDate;                // 转正日期
	private Date toDateRange1;
	private Date toDateRange2;
	private  String toDateStr;
	private Date separationDate;        // 离职日期
	private Date separationDateRange1;
	private Date separationDateRange2;
	private  String separationDateStr;
	private Long deptMoveFlag;          // 跨部门调动标识:1代表跨部门调动，2代表非跨部门调动
	private Date deptMoveDate;          // 跨部门调动生效日期
	private Date deptMoveDateRange1;
	private Date deptMoveDateRange2;
	private  String deptMoveDateStr;
	private Long navtiveMoveFlag;       // 本部门调动标识:1代表本部门内调动，2代表非本部门内调动
	private Date navtiveMoveDate;       // 本部门调动生效日期
	private Date navtiveMoveDateRange1;
	private Date navtiveMoveDateRange2;
	private  String navtiveMoveDateStr;
	private String originalOrgid;       // 原未调动部门ID
	private String originalOrgidLike;
	private String imeiCode;          //手机终端的IMEI编码
	private String imeiCodeLike;
    private String ofDepartmentGroup;   //所属部门分组
    private String ofDepartmentGroupLike;
   
    private String chatId;                // 微信id
	private String chatIdLike;
	private String chatName;              // 微信昵称
	private String chatNameLike;
	private String chatCode;              // 微信号
	private String chatCodeLike;
	
	private String wxId;					//微信ID
	private String wxIdLike;
	private String wxVerifyPhone;         // 微信认证手机号
	private String wxVerifyPhoneLike;	

/**
	 * 用户当前时刻的工作/空闲状态  0 表示空闲  1 表示工作中
	 */
	private int curWorkFlag;      
	/**
	 * 用户当天的工作/休假状态           0 表示休假   1 表示上班  2 表示请假
	 */
	private int workFlag;   
	/**
	 * 该用户是否为机器归属人
	 */
	private int isResponsiblePerson; 
	
	private String checkFlag ;        //表示输出到页面上时，是否选中该用户(checkbox 选中)
	private String systemProposeFlag; //是否是系统推荐维护工程师
	private String userOrgName; //用户所属的机构名称
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserIdLike() {
		return userIdLike;
	}
	public void setUserIdLike(String userIdLike) {
		this.userIdLike = userIdLike;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserAccountLike() {
		return userAccountLike;
	}
	public void setUserAccountLike(String userAccountLike) {
		this.userAccountLike = userAccountLike;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNameLike() {
		return userNameLike;
	}
	public void setUserNameLike(String userNameLike) {
		this.userNameLike = userNameLike;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserPasswordLike() {
		return userPasswordLike;
	}
	public void setUserPasswordLike(String userPasswordLike) {
		this.userPasswordLike = userPasswordLike;
	}
	public Long getSexflag() {
		return sexflag;
	}
	public void setSexflag(Long sexflag) {
		this.sexflag = sexflag;
	}
	public String getUserOrgid() {
		return userOrgid;
	}
	public void setUserOrgid(String userOrgid) {
		this.userOrgid = userOrgid;
	}
	public String getUserOrgidLike() {
		return userOrgidLike;
	}
	public void setUserOrgidLike(String userOrgidLike) {
		this.userOrgidLike = userOrgidLike;
	}
	public Long getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Long userStatus) {
		this.userStatus = userStatus;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserTypeLike() {
		return userTypeLike;
	}
	public void setUserTypeLike(String userTypeLike) {
		this.userTypeLike = userTypeLike;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getLinkPhoneLike() {
		return linkPhoneLike;
	}
	public void setLinkPhoneLike(String linkPhoneLike) {
		this.linkPhoneLike = linkPhoneLike;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailLike() {
		return emailLike;
	}
	public void setEmailLike(String emailLike) {
		this.emailLike = emailLike;
	}
	public Long getPwdStatus() {
		return pwdStatus;
	}
	public void setPwdStatus(Long pwdStatus) {
		this.pwdStatus = pwdStatus;
	}
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	public String getUserDescLike() {
		return userDescLike;
	}
	public void setUserDescLike(String userDescLike) {
		this.userDescLike = userDescLike;
	}
	public Date getLastlogindate() {
		return lastlogindate;
	}
	public void setLastlogindate(Date lastlogindate) {
		this.lastlogindate = lastlogindate;
	}
	public Date getLastlogindateRange1() {
		return lastlogindateRange1;
	}
	public void setLastlogindateRange1(Date lastlogindateRange1) {
		this.lastlogindateRange1 = lastlogindateRange1;
	}
	public Date getLastlogindateRange2() {
		return lastlogindateRange2;
	}
	public void setLastlogindateRange2(Date lastlogindateRange2) {
		this.lastlogindateRange2 = lastlogindateRange2;
	}
	public String getLastlogindateStr() {
		return lastlogindateStr;
	}
	public void setLastlogindateStr(String lastlogindateStr) {
		this.lastlogindateStr = lastlogindateStr;
	}
	public String getUserArea() {
		return userArea;
	}
	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}
	public String getUserAreaLike() {
		return userAreaLike;
	}
	public void setUserAreaLike(String userAreaLike) {
		this.userAreaLike = userAreaLike;
	}
	public String getLinkPhoneType() {
		return linkPhoneType;
	}
	public void setLinkPhoneType(String linkPhoneType) {
		this.linkPhoneType = linkPhoneType;
	}
	public String getLinkPhoneTypeLike() {
		return linkPhoneTypeLike;
	}
	public void setLinkPhoneTypeLike(String linkPhoneTypeLike) {
		this.linkPhoneTypeLike = linkPhoneTypeLike;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getTelNoLike() {
		return telNoLike;
	}
	public void setTelNoLike(String telNoLike) {
		this.telNoLike = telNoLike;
	}
	public String getChargeBankId() {
		return chargeBankId;
	}
	public void setChargeBankId(String chargeBankId) {
		this.chargeBankId = chargeBankId;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public Date getInDateRange1() {
		return inDateRange1;
	}
	public void setInDateRange1(Date inDateRange1) {
		this.inDateRange1 = inDateRange1;
	}
	public Date getInDateRange2() {
		return inDateRange2;
	}
	public void setInDateRange2(Date inDateRange2) {
		this.inDateRange2 = inDateRange2;
	}
	public String getInDateStr() {
		return inDateStr;
	}
	public void setInDateStr(String inDateStr) {
		this.inDateStr = inDateStr;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public Date getOutDateRange1() {
		return outDateRange1;
	}
	public void setOutDateRange1(Date outDateRange1) {
		this.outDateRange1 = outDateRange1;
	}
	public Date getOutDateRange2() {
		return outDateRange2;
	}
	public void setOutDateRange2(Date outDateRange2) {
		this.outDateRange2 = outDateRange2;
	}
	public String getOutDateStr() {
		return outDateStr;
	}
	public void setOutDateStr(String outDateStr) {
		this.outDateStr = outDateStr;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getIdCardNoLike() {
		return idCardNoLike;
	}
	public void setIdCardNoLike(String idCardNoLike) {
		this.idCardNoLike = idCardNoLike;
	}
	public String getUserPost() {
		return userPost;
	}
	public void setUserPost(String userPost) {
		this.userPost = userPost;
	}
	public String getUserPostLike() {
		return userPostLike;
	}
	public void setUserPostLike(String userPostLike) {
		this.userPostLike = userPostLike;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Date getToDateRange1() {
		return toDateRange1;
	}
	public void setToDateRange1(Date toDateRange1) {
		this.toDateRange1 = toDateRange1;
	}
	public Date getToDateRange2() {
		return toDateRange2;
	}
	public void setToDateRange2(Date toDateRange2) {
		this.toDateRange2 = toDateRange2;
	}
	public String getToDateStr() {
		return toDateStr;
	}
	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}
	public Date getSeparationDate() {
		return separationDate;
	}
	public void setSeparationDate(Date separationDate) {
		this.separationDate = separationDate;
	}
	public Date getSeparationDateRange1() {
		return separationDateRange1;
	}
	public void setSeparationDateRange1(Date separationDateRange1) {
		this.separationDateRange1 = separationDateRange1;
	}
	public Date getSeparationDateRange2() {
		return separationDateRange2;
	}
	public void setSeparationDateRange2(Date separationDateRange2) {
		this.separationDateRange2 = separationDateRange2;
	}
	public String getSeparationDateStr() {
		return separationDateStr;
	}
	public void setSeparationDateStr(String separationDateStr) {
		this.separationDateStr = separationDateStr;
	}
	public Long getDeptMoveFlag() {
		return deptMoveFlag;
	}
	public void setDeptMoveFlag(Long deptMoveFlag) {
		this.deptMoveFlag = deptMoveFlag;
	}
	public Date getDeptMoveDate() {
		return deptMoveDate;
	}
	public void setDeptMoveDate(Date deptMoveDate) {
		this.deptMoveDate = deptMoveDate;
	}
	public Date getDeptMoveDateRange1() {
		return deptMoveDateRange1;
	}
	public void setDeptMoveDateRange1(Date deptMoveDateRange1) {
		this.deptMoveDateRange1 = deptMoveDateRange1;
	}
	public Date getDeptMoveDateRange2() {
		return deptMoveDateRange2;
	}
	public void setDeptMoveDateRange2(Date deptMoveDateRange2) {
		this.deptMoveDateRange2 = deptMoveDateRange2;
	}
	public String getDeptMoveDateStr() {
		return deptMoveDateStr;
	}
	public void setDeptMoveDateStr(String deptMoveDateStr) {
		this.deptMoveDateStr = deptMoveDateStr;
	}
	public Long getNavtiveMoveFlag() {
		return navtiveMoveFlag;
	}
	public void setNavtiveMoveFlag(Long navtiveMoveFlag) {
		this.navtiveMoveFlag = navtiveMoveFlag;
	}
	public Date getNavtiveMoveDate() {
		return navtiveMoveDate;
	}
	public void setNavtiveMoveDate(Date navtiveMoveDate) {
		this.navtiveMoveDate = navtiveMoveDate;
	}
	public Date getNavtiveMoveDateRange1() {
		return navtiveMoveDateRange1;
	}
	public void setNavtiveMoveDateRange1(Date navtiveMoveDateRange1) {
		this.navtiveMoveDateRange1 = navtiveMoveDateRange1;
	}
	public Date getNavtiveMoveDateRange2() {
		return navtiveMoveDateRange2;
	}
	public void setNavtiveMoveDateRange2(Date navtiveMoveDateRange2) {
		this.navtiveMoveDateRange2 = navtiveMoveDateRange2;
	}
	public String getNavtiveMoveDateStr() {
		return navtiveMoveDateStr;
	}
	public void setNavtiveMoveDateStr(String navtiveMoveDateStr) {
		this.navtiveMoveDateStr = navtiveMoveDateStr;
	}
	public String getOriginalOrgid() {
		return originalOrgid;
	}
	public void setOriginalOrgid(String originalOrgid) {
		this.originalOrgid = originalOrgid;
	}
	public String getOriginalOrgidLike() {
		return originalOrgidLike;
	}
	public void setOriginalOrgidLike(String originalOrgidLike) {
		this.originalOrgidLike = originalOrgidLike;
	}
	public String getImeiCode() {
		return imeiCode;
	}
	public void setImeiCode(String imeiCode) {
		this.imeiCode = imeiCode;
	}
	public String getImeiCodeLike() {
		return imeiCodeLike;
	}
	public void setImeiCodeLike(String imeiCodeLike) {
		this.imeiCodeLike = imeiCodeLike;
	}
	public String getOfDepartmentGroup() {
		return ofDepartmentGroup;
	}
	public void setOfDepartmentGroup(String ofDepartmentGroup) {
		this.ofDepartmentGroup = ofDepartmentGroup;
	}
	public String getOfDepartmentGroupLike() {
		return ofDepartmentGroupLike;
	}
	public void setOfDepartmentGroupLike(String ofDepartmentGroupLike) {
		this.ofDepartmentGroupLike = ofDepartmentGroupLike;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public String getChatIdLike() {
		return chatIdLike;
	}
	public void setChatIdLike(String chatIdLike) {
		this.chatIdLike = chatIdLike;
	}
	public String getChatName() {
		return chatName;
	}
	public void setChatName(String chatName) {
		this.chatName = chatName;
	}
	public String getChatNameLike() {
		return chatNameLike;
	}
	public void setChatNameLike(String chatNameLike) {
		this.chatNameLike = chatNameLike;
	}
	public String getChatCode() {
		return chatCode;
	}
	public void setChatCode(String chatCode) {
		this.chatCode = chatCode;
	}
	public String getChatCodeLike() {
		return chatCodeLike;
	}
	public void setChatCodeLike(String chatCodeLike) {
		this.chatCodeLike = chatCodeLike;
	}
	public String getWxId() {
		return wxId;
	}
	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	public String getWxIdLike() {
		return wxIdLike;
	}
	public void setWxIdLike(String wxIdLike) {
		this.wxIdLike = wxIdLike;
	}
	public String getWxVerifyPhone() {
		return wxVerifyPhone;
	}
	public void setWxVerifyPhone(String wxVerifyPhone) {
		this.wxVerifyPhone = wxVerifyPhone;
	}
	public String getWxVerifyPhoneLike() {
		return wxVerifyPhoneLike;
	}
	public void setWxVerifyPhoneLike(String wxVerifyPhoneLike) {
		this.wxVerifyPhoneLike = wxVerifyPhoneLike;
	}
	public int getCurWorkFlag() {
		return curWorkFlag;
	}
	public void setCurWorkFlag(int curWorkFlag) {
		this.curWorkFlag = curWorkFlag;
	}
	public int getWorkFlag() {
		return workFlag;
	}
	public void setWorkFlag(int workFlag) {
		this.workFlag = workFlag;
	}
	public int getIsResponsiblePerson() {
		return isResponsiblePerson;
	}
	public void setIsResponsiblePerson(int isResponsiblePerson) {
		this.isResponsiblePerson = isResponsiblePerson;
	}
	public String getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	public String getSystemProposeFlag() {
		return systemProposeFlag;
	}
	public void setSystemProposeFlag(String systemProposeFlag) {
		this.systemProposeFlag = systemProposeFlag;
	}
	public String getUserOrgName() {
		return userOrgName;
	}
	public void setUserOrgName(String userOrgName) {
		this.userOrgName = userOrgName;
	}

	
}
