package com.kingteller.client.bean.attendance;

import java.io.File;

public class LeavePicListBean {
	private String picId;				//图片id
	private String picPath;				//图片路径id
	private File picFlie;				//图片文件
	private String picName;				//图片名称
	
	
	public LeavePicListBean(String picId, String picPath, File picFlie, String picName) { 
		this.picId = picId;
		this.picPath = picPath;
		this.picFlie = picFlie;
		this.picName = picName;
	}
	
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public File getPicFlie() {
		return picFlie;
	}
	public void setPicFlie(File flie) {
		this.picFlie = flie;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}

	
}
