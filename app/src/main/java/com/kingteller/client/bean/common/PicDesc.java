package com.kingteller.client.bean.common;


/**
 * 上传图片的信息
 * @author 王定波
 *
 */
public class PicDesc {
	private String path;
	private String desc;
	private int status;//1.上传成功 2.待上传 3.上传失败
	public final static int UPLOAD_SUCCESS=1;
	public final static int UPLOAD_WAITING=2;
	public final static int UPLOAD_FAILED=3;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
