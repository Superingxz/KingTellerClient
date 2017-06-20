package com.kingteller.client.bean.db;

import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;

@Table(name = "atmgroupv2")
public class AtmGroup {
	@Id(column = "atmGroupId")

    private String atmGroupId; // 缓存id
    private String atmGroupType;// 报告类型			值  = atmgroupuppic
    private String atmGroupJqBh;// 机器编号
    private String atmGroupPicList;// 图片路径
    private String atmGroupStrData;// 图片水印
    private String reportTime;// 缓存时间

    public String getAtmGroupId() {
        return atmGroupId;
    }

    public void setAtmGroupId(String atmGroupId) {
        this.atmGroupId = atmGroupId;
    }

    public String getAtmGroupType() {
        return atmGroupType;
    }

    public void setAtmGroupType(String atmGroupType) {
        this.atmGroupType = atmGroupType;
    }

    public String getAtmGroupJqBh() {
        return atmGroupJqBh;
    }

    public void setAtmGroupJqBh(String atmGroupJqBh) {
        this.atmGroupJqBh = atmGroupJqBh;
    }

    public String getAtmGroupPicList() {
        return atmGroupPicList;
    }

    public void setAtmGroupPicList(String atmGroupPicList) {
        this.atmGroupPicList = atmGroupPicList;
    }

    public String getAtmGroupStrData() {
        return atmGroupStrData;
    }

    public void setAtmGroupStrData(String atmGroupStrData) {
        this.atmGroupStrData = atmGroupStrData;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
}

