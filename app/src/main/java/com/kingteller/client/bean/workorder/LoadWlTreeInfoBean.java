package com.kingteller.client.bean.workorder;

import java.io.Serializable;

/**
 * CREATE TABLE tb_sm_wl_tree_info(_
id varchar2(32) primary key,new_code varchar2(30),wl_index integer,parent_id var
char2(32),pathid varchar2(1000),node_level integer,pathname varchar2(1000),wl_in
fo_id varchar2(32) not null)
 * */

public class LoadWlTreeInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;           // 数据标识
	private String idLike;
	private String wlInfoId;     // 所属物料类别id
	private String wlInfoIdLike;
	private String newCode;      // 新物料编码
	private String newCodeLike;
	private String parentId;     // 父级物料id
	private String parentIdLike;
	private String pathid;       // 物料层次路径ID
	private String pathidLike;
	private Long nodeLevel;      // 层次
	private String pathname;     // 物料层次路径名
	private String pathnameLike;
	private Long wlIndex;        // 顺序号
	//private String modifyDate;         // 修改时间
	private String modifyDateRange1;
	private String modifyDateRange2;
	private String modifyDateStr;
	private String modifyUserid;     // 修改人id
	private String modifyUseridLike;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdLike() {
		return idLike;
	}
	public String getNewCode() {
		return newCode;
	}
	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}
	public String getNewCodeLike() {
		return newCodeLike;
	}
	public void setNewCodeLike(String newCodeLike) {
		this.newCodeLike = newCodeLike;
	}
	public void setIdLike(String idLike) {
		this.idLike = idLike;
	}
	public String getWlInfoId() {
		return wlInfoId;
	}
	public void setWlInfoId(String wlInfoId) {
		this.wlInfoId = wlInfoId;
	}
	public String getWlInfoIdLike() {
		return wlInfoIdLike;
	}
	public void setWlInfoIdLike(String wlInfoIdLike) {
		this.wlInfoIdLike = wlInfoIdLike;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentIdLike() {
		return parentIdLike;
	}
	public void setParentIdLike(String parentIdLike) {
		this.parentIdLike = parentIdLike;
	}
	public String getPathid() {
		return pathid;
	}
	public void setPathid(String pathid) {
		this.pathid = pathid;
	}
	public String getPathidLike() {
		return pathidLike;
	}
	public void setPathidLike(String pathidLike) {
		this.pathidLike = pathidLike;
	}
	public Long getNodeLevel() {
		return nodeLevel;
	}
	public void setNodeLevel(Long nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	public String getPathname() {
		return pathname;
	}
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	public String getPathnameLike() {
		return pathnameLike;
	}
	public void setPathnameLike(String pathnameLike) {
		this.pathnameLike = pathnameLike;
	}
	public Long getWlIndex() {
		return wlIndex;
	}
	public void setWlIndex(Long wlIndex) {
		this.wlIndex = wlIndex;
	}
	/*public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}*/
	public String getModifyDateRange1() {
		return modifyDateRange1;
	}
	public void setModifyDateRange1(String modifyDateRange1) {
		this.modifyDateRange1 = modifyDateRange1;
	}
	public String getModifyDateRange2() {
		return modifyDateRange2;
	}
	public void setModifyDateRange2(String modifyDateRange2) {
		this.modifyDateRange2 = modifyDateRange2;
	}
	public String getModifyDateStr() {
		return modifyDateStr;
	}
	public void setModifyDateStr(String modifyDateStr) {
		this.modifyDateStr = modifyDateStr;
	}
	public String getModifyUserid() {
		return modifyUserid;
	}
	public void setModifyUserid(String modifyUserid) {
		this.modifyUserid = modifyUserid;
	}
	public String getModifyUseridLike() {
		return modifyUseridLike;
	}
	public void setModifyUseridLike(String modifyUseridLike) {
		this.modifyUseridLike = modifyUseridLike;
	}
	
	
}
