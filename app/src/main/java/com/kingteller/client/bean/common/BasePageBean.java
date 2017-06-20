package com.kingteller.client.bean.common;

import java.io.Serializable;
import java.util.List;

/**
 * 所有分页的基类Bean
 * 
 * @author 王定波
 * 
 */
public class BasePageBean<T> extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6454171082220612041L;
	private int totalNumber;// 总条数
	private int totalPage;// 总页数
	private int currentPage;// 当前页
	private List<T> list;// 数据

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}


}
