package com.kingteller.client.bean.common;

public class ToolBar {
	private String title;
	private int nomal_img;
	private int pressed_img;
	private boolean isnew = false;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNomalImg() {
		return nomal_img;
	}

	public void setNomalImg(int nomal_img) {
		this.nomal_img = nomal_img;
	}

	public int getPressedImg() {
		return pressed_img;
	}

	public void setPressedImg(int pressed_img) {
		this.pressed_img = pressed_img;
	}

	public boolean isIsNew() {
		return isnew;
	}

	public void setIsNew(boolean isnew) {
		this.isnew = isnew;
	}
}
