package com.kingteller.client.activity.workorder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.MachineSearchAdapter;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.workorder.MachineinfoSimpleBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ConditionView;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.condition.Condition;
import com.kingteller.framework.http.AjaxParams;

public class MachineSearchActivity extends Activity implements
		OnClickListener, IXListViewListener, OnItemClickListener {

	private ConditionView atmorjqbh;
	private MachineSearchAdapter adapter;
	private int currentPage = 1;
	private Button btn_submit;
	private Button btn_cancel;
	public static final int ATMHJQBH = 603;
	private List<MachineinfoSimpleBean> machineInfolist = new ArrayList<MachineinfoSimpleBean>();
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	
	private Context mContext;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_machine_choose);
		KingTellerApplication.addActivity(this);
		
		mContext = MachineSearchActivity.this;
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		
		initUI();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("选择机器");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		
		atmorjqbh = (ConditionView) findViewById(R.id.atmorjqbh);
		
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_submit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		
		adapter = new MachineSearchAdapter(this, machineInfolist);

		getListviewObj().getListview().setAdapter(adapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		getListviewObj().setStatus(LoadingEnum.NODATA, "请输入条件进行搜索");
		
		KingTellerConfigUtils.hideInputMethod(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.layout_main_left_btn:		
			finish();
			break;
		case R.id.layout_main_righttwo_btn:
			if(KingTellerJudgeUtils.isEmpty(atmorjqbh.getFieldValue())){
				T.showShort(mContext, "您的搜索条件不能为空!");
				return;
			}
			currentPage = 1;
			getMachineinfoSimples();
			break;
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.btn_submit:
			if(adapter.getMachineInfoCheckedList().size() == 1){
				Intent intent = new Intent();
				intent.putExtra("machineData", adapter.getMachineInfoCheckedList().get(0));
				setResult(RESULT_OK, intent);
				finish();
			}else{
				T.showShort(mContext, "您只能选择一台机器!");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		currentPage = 1;
		getMachineinfoSimples();
	}

	@Override
	public void onLoadMore() {
		currentPage++;
		getMachineinfoSimples();
	}
	
	public void getMachineinfoSimples(){

		KTHttpClient fh = new KTHttpClient(true);
		// 获取condition实例
		LinearLayout conditionLayout = (LinearLayout) findViewById(R.id.workorder_condition);
		Condition cond = ConditionUtils.getCondition(this, conditionLayout);
		AjaxParams params = ConditionUtils.getAjaxParams(cond, currentPage);
		params.put("jqbh", atmorjqbh.getFieldValue());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.JqcxUrl), params,
				new AjaxHttpCallBack<BasePageBean<MachineinfoSimpleBean>>(this,
						new TypeToken<BasePageBean<MachineinfoSimpleBean>>() {
						}.getType(), true) {

			
					@Override
					public void onStart() {
						if (currentPage == 1){
							getListviewObj().setStatus(LoadingEnum.LOADING);
						}
					}
					
					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}

					@Override
					public void onDo(BasePageBean<MachineinfoSimpleBean> basePageBean) {
						List<MachineinfoSimpleBean> data = basePageBean.getList();
						if (data.size() > 0) {
							if (currentPage == 1){
								adapter.setLists(data);
								getListviewObj().setStatus(LoadingEnum.LISTSHOW);
								getListviewObj().getListview().setLoadMoreEnabled(true);
							}else{
								adapter.addLists(data);
							}
							// 如果当前页等于总页数，则不需要先是load more按钮
							if (currentPage == basePageBean.getTotalPage())
								getListviewObj().getListview().setLoadMoreEnabled(false);

						} else {
							if(currentPage == 1){
								getListviewObj().setStatus(LoadingEnum.NODATA,"没有数据");
							}else{
								getListviewObj().getListview().setLoadMoreEnabled(false);
							}
							T.showShort(mContext, "没有数据");
						}
					};
				});

	}

	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
