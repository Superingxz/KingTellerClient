package com.kingteller.client.bean.account;

import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;
/**
 * 通知读取
 * @author 王定波
 *
 */
@Table(name = "notice_read")
public class Notice {
	@Id(column = "id")
	private String id;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
