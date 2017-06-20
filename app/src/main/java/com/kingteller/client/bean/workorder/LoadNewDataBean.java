package com.kingteller.client.bean.workorder;

import java.io.Serializable;
import java.util.List;

public class LoadNewDataBean implements Serializable{

	/**
	 *{
    "handleSubByList": [
        {
            "createDate": {
                "date": 30,
                "day": 2,
                "hours": 0,
                "minutes": 0,
                "month": 8,
                "seconds": 0,
                "time": 1412006400000,
                "timezoneOffset": -480,
                "year": 114
            },
            "createDateRange1": null,
            "createDateRange2": null,
            "createDateStr": "2014-09-30",
            "createUserid": "",
            "createUseridLike": "",
            "handleSub": "其他",
            "handleSubLike": "",
            "id": "T201409300919other",
            "idLike": "",
            "reId": "201409291609a",
            "reIdLike": "",
            "remark": "",
            "remarkLike": "",
            "status": 0
        }
    ],
    "treeInfoAll": [],
    "troubleRemarkAllInfo": [
        {
            "createDate": {
                "date": 29,
                "day": 1,
                "hours": 0,
                "minutes": 0,
                "month": 8,
                "seconds": 0,
                "time": 1411920000000,
                "timezoneOffset": -480,
                "year": 114
            },
            "createDateRange1": null,
            "createDateRange2": null,
            "createDateStr": "2014-09-29",
            "createUserid": "",
            "createUseridLike": "",
            "id": "201409291609a",
            "idLike": "",
            "indexnum": 7,
            "parentId": "",
            "parentIdLike": "",
            "pathId": "",
            "pathIdLike": "",
            "pathName": "其他",
            "pathNameLike": "",
            "remark": "",
            "remarkLike": "",
            "status": 0,
            "threeLevelId": "",
            "threeLevelIdLike": "",
            "troubleRemark": "其他",
            "troubleRemarkLike": "",
            "twoLevelId": "",
            "twoLevelIdLike": "",
            "updateFlag": 0
        }
    ],
    "wlInfoAll": [],
    "workTypeHandleSubInfo": [],
    "status": "",
    "msg": ""
}
	 */
	private static final long serialVersionUID = 1L;
	private List<LoadWlInfoBean> wlInfoAll;//基础物料信息
	private List<LoadWlTreeInfoBean> treeInfoAll;//物料树信息
	private List<LoadTroubleRemarkBean> troubleRemarkAllInfo;//故障描述信息
	private List<LoadHandleSubBean>  handleSubByList;//故障维护的处理过程信息
	private List<LoadClgcWorktypeBean> workTypeHandleSubInfo;//工作类别的处理过程信息
	private List<LoadWlGeneralRelationBean> wlCompatibleTempList;//物料通用关系表信息
	private List<LoadBjglAtmpartsBean> atmPartsConfigList;//工作类别的处理过程信息
	private List<LoadBjglAtmpartsWlBean> atmPartsConfigWlList;//物料通用关系表信息
	

	public List<LoadBjglAtmpartsBean> getAtmPartsConfigList() {
		return atmPartsConfigList;
	}
	public void setAtmPartsConfigList(List<LoadBjglAtmpartsBean> atmPartsConfigList) {
		this.atmPartsConfigList = atmPartsConfigList;
	}
	public List<LoadBjglAtmpartsWlBean> getAtmPartsConfigWlList() {
		return atmPartsConfigWlList;
	}
	public void setAtmPartsConfigWlList(
			List<LoadBjglAtmpartsWlBean> atmPartsConfigWlList) {
		this.atmPartsConfigWlList = atmPartsConfigWlList;
	}
	public List<LoadWlGeneralRelationBean> getWlCompatibleTempList() {
		return wlCompatibleTempList;
	}
	public void setWlCompatibleTempList(
			List<LoadWlGeneralRelationBean> wlCompatibleTempList) {
		this.wlCompatibleTempList = wlCompatibleTempList;
	}
	public List<LoadWlInfoBean> getWlInfoAll() {
		return wlInfoAll;
	}
	public void setWlInfoAll(List<LoadWlInfoBean> wlInfoAll) {
		this.wlInfoAll = wlInfoAll;
	}
	public List<LoadWlTreeInfoBean> getTreeInfoAll() {
		return treeInfoAll;
	}
	public void setTreeInfoAll(List<LoadWlTreeInfoBean> treeInfoAll) {
		this.treeInfoAll = treeInfoAll;
	}
	public List<LoadTroubleRemarkBean> getTroubleRemarkAllInfo() {
		return troubleRemarkAllInfo;
	}
	public void setTroubleRemarkAllInfo(
			List<LoadTroubleRemarkBean> troubleRemarkAllInfo) {
		this.troubleRemarkAllInfo = troubleRemarkAllInfo;
	}
	public List<LoadHandleSubBean> getHandleSubByList() {
		return handleSubByList;
	}
	public void setHandleSubByList(List<LoadHandleSubBean> handleSubByList) {
		this.handleSubByList = handleSubByList;
	}
	public List<LoadClgcWorktypeBean> getWorkTypeHandleSubInfo() {
		return workTypeHandleSubInfo;
	}
	public void setWorkTypeHandleSubInfo(
			List<LoadClgcWorktypeBean> workTypeHandleSubInfo) {
		this.workTypeHandleSubInfo = workTypeHandleSubInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
