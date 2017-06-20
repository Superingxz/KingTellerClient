package com.kingteller.client.bean.onlinelearning;

import java.io.Serializable;

public class VideoGenreBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 分类ID */
	private String genreId;
	/** 分类名 */
	private String genreName;
	/** 上一级的id表示 */
	private String genrePid;
	/** 深度 */
	private Long genreLevel;
	/** path */
	private String genrePath;
	/** 是否是父节点 */
	private boolean isParent;
	
	public VideoGenreBean() {
		// TODO Auto-generated constructor stub
	}

	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public String getGenrePid() {
		return genrePid;
	}

	public void setGenrePid(String genrePid) {
		this.genrePid = genrePid;
	}

	public Long getGenreLevel() {
		return genreLevel;
	}

	public void setGenreLevel(Long genreLevel) {
		this.genreLevel = genreLevel;
	}

	public String getGenrePath() {
		return genrePath;
	}

	public void setGenrePath(String genrePath) {
		this.genrePath = genrePath;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

}
