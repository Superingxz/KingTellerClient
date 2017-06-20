package com.kingteller.client.view.groupview.listview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.view.GroupViewBase;



/**
 * 自定义添加删除视图view
 * 
 * @author 王定波
 * 
 */
public class OverTimePeopleGroupListView extends LinearLayout implements
		android.view.View.OnClickListener {

	private AddViewCallBack addViewCallBack;
	private LinearLayout layout_list;
	private Button btn_add;
	private TextView text_select, text_one, text_two, text_three, text_four;

	public LinearLayout getLayoutList() {
		return layout_list;
	}

	public OverTimePeopleGroupListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public OverTimePeopleGroupListView(Context context) {
		super(context);
		initView();
	}
	
	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.group_overtimepeople_list_view, this);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		setLayoutParams(lp);
		
		layout_list = (LinearLayout) findViewById(R.id.layout_list);
		text_select = (TextView) findViewById(R.id.layout_list_one_text_select);
		text_one = (TextView) findViewById(R.id.layout_list_two_text_one);
		text_two = (TextView) findViewById(R.id.layout_list_two_text_two);
		text_three = (TextView) findViewById(R.id.layout_list_two_text_three);
		text_four = (TextView) findViewById(R.id.layout_list_two_text_four);
		
		btn_add = (Button) findViewById(R.id.btn_add);
		btn_add.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_add:
			if (addViewCallBack != null) {
				addViewCallBack.addView(OverTimePeopleGroupListView.this);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 添加一个视图
	 * @param view
	 */
	public void addItem(View view) {
		layout_list.addView(view);
	}

	/**
	 * 得到所有视图的数据，视图必须为GroupViewBase的扩展类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListData() {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < layout_list.getChildCount(); i++) {
			list.add((T) ((GroupViewBase) layout_list.getChildAt(i)).getData());
		}
		return list;
	}

	public LinearLayout getLinearLayout(int index){
		return (LinearLayout)layout_list.getChildAt(index);
	}
	
	/**
	 * 设置人员值
	 * @param data
	 */
	public void setItemTextData(String id, String name, String account) {
		((TextView) layout_list.getTag(R.id.overtimePeopleId)).setText(id);
		((TextView) layout_list.getTag(R.id.overtimePeopleName)).setText(name + "\n(" + account + ")");

	}
	
	/**
	 * 设置  已选择文字
	 * @param data
	 */
	public void setSelectData(String str) {
		text_select.setText(str);
	}
	
	
	public void setAddViewCallBack(AddViewCallBack addViewCallBack) {
		this.addViewCallBack = addViewCallBack;
	}

	/**
	 * 添加视图回调接口
	 * @author 王定波
	 */
	public interface AddViewCallBack {
		public void addView(OverTimePeopleGroupListView view);
	}

	/**
	 * 设置添加按钮是否隐藏显示，默认显示
	 * @param hidden   0：显示	1：隐藏 占空间	2：隐藏  不占空间
	 */
	public void setAddBtnHidden(int type) {
		switch (type) {
		case 0:
			btn_add.setVisibility(View.VISIBLE);
			break;
		case 1:
			btn_add.setVisibility(View.INVISIBLE);
			break;
		case 2:
			btn_add.setVisibility(View.GONE);
			break;
		default:
			break;
		}
		
	}

	/**
	 * 设置控件否隐藏显示 默认显示
	 * @param hidden true：隐藏   false：显示
	 */
	public void setGroupListViewHidden(boolean hidden) {
		findViewById(R.id.layout_list_one).setVisibility(hidden ? View.GONE : View.VISIBLE);
		findViewById(R.id.layout_list_two).setVisibility(hidden ? View.GONE : View.VISIBLE);
	}
	
	/**
	 * 设置控件two 否隐藏显示 默认显示
	 * @param hidden true：隐藏   false：显示
	 */
	public void setGroupListTwoViewHidden(boolean hidden) {
		findViewById(R.id.layout_list_two).setVisibility(hidden ? View.GONE : View.VISIBLE);
	}
	
	/**
	 * 设置头部样式
	 * @param type : 0:多人加班单填写	1:多人加班单查看与审批		2:领导信息头
	 */
	public void setTitleStart(int mun) {
		text_one.setVisibility(View.VISIBLE);
		findViewById(R.id.view_one).setVisibility(View.VISIBLE);
		text_two.setVisibility(View.VISIBLE);
		findViewById(R.id.view_two).setVisibility(View.VISIBLE);
		text_three.setVisibility(View.VISIBLE);
		findViewById(R.id.view_three).setVisibility(View.VISIBLE);
		text_four.setVisibility(View.VISIBLE);
		switch (mun) {
			case 0:
				text_one.setText("加班人");
				text_two.setText("补贴方式");
				text_three.setText("加班时长");
				text_four.setText("操作");
				break;
			case 1:
				text_one.setText("加班人");
				text_two.setText("补贴方式");
				text_three.setText("加班时长");
				text_four.setText("打卡记录");
				break;
			case 2:
				text_one.setText("审批时间");
				text_two.setText("审批人员");
				text_three.setText("审批意见");
				findViewById(R.id.view_three).setVisibility(View.GONE);
				text_four.setVisibility(View.GONE);
				break;
			default:
				break;
		}
		
	}
}