package com.kingteller.client.view;

import java.util.List;

import android.content.Context;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 通用组视图基类
 * 
 * @author 王定波
 * 
 */
public abstract class GroupViewBase extends LinearLayout {

	protected boolean isdel;
	protected int type;
	private KingTellerEditText currEditText;

	public GroupViewBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public GroupViewBase(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public GroupViewBase(Context context, boolean isdel) {
		super(context);
		// TODO Auto-generated constructor stub
		this.isdel = isdel;
		initView();
	}
	
	public GroupViewBase(Context context, boolean isdel, int type) {
		super(context);
		// TODO Auto-generated constructor stub
		this.isdel = isdel;
		this.type = type;
		initView();
	}

	
	protected abstract void initView();
	/**
	 * 检查数据输入
	 * @return
	 */
	public abstract boolean checkData();
	/**
	 * 获取数据
	 * @return
	 */
	public abstract Object getData();

	public KingTellerEditText getCurrEditText() {
		return currEditText;
	}

	/**
	 * 点击设置当前被点击的View
	 * 
	 * @param currEditText
	 */
	public void setCurrEditText(KingTellerEditText currEditText) {
		this.currEditText = currEditText;
	}

	/**
	 * 获取输入的数据
	 * @param classofT
	 * @return
	 */
	public <T> List<T> getDataLists(Class<T> classofT) {
		return null;
	}
}
