package com.kingteller.client.bean.onlinelearning;

import java.io.Serializable;

public class FileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TreeNodeId
	private String genreId;
	@TreeNodePid
	private String genrePid;
	@TreeNodeLabel
	private String genreName;
	
	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}

	public String getGenrePid() {
		return genrePid;
	}

	public void setGenrePid(String genrePid) {
		this.genrePid = genrePid;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public FileBean() {
		// TODO Auto-generated constructor stub
	}

	public FileBean(String genreId, String genrePid, String genreName) {
		super();
		this.genreId = genreId;
		this.genrePid = genrePid;
		this.genreName = genreName;
	}
}
