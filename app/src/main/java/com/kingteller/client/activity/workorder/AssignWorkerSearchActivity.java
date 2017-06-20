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
import com.kingteller.client.adapter.AssignWorkerSearchAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.workorder.AssignWorkerNameBean;
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

public class AssignWorkerSearchActivity extends Activity implements
		OnClickListener, IXListViewListener, OnItemClickListener {

	private ConditionView assignWorkerName;
	private AssignWorkerSearchAdapter adapter;
	private int currentPage = 1;
	private Button btn_submit;
	private Button btn_cancel;
	private List<AssignWorkerNameBean> assignWorkerNameList = new ArrayList<AssignWorkerNameBean>();
	private String ssbscid;
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	
	private Context mContext;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_assign_worker_name);
		KingTellerApplication.addActivity(this);
		
		mContext = AssignWorkerSearchActivity.this;
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		initUI();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("选择人员");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		
		ssbscid = getIntent().getStringExtra("ssbscid");
		assignWorkerName = (ConditionView) findViewById(R.id.assignWorkerName);

		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);

		btn_submit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		adapter = new AssignWorkerSearchAdapter(this, assignWorkerNameList);

		getListviewObj().getListview().setAdapter(adapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		getListviewObj().setStatus(LoadingEnum.NODATA, "请输入条件进行搜索");

		
		User user = User.getInfo(this);
		if(!user.getRoleCode().contains("QYJL_COMMON") && !user.getRoleCode().contains("QYWY_JX")){
			getServicePeople();
		}
		
		KingTellerConfigUtils.hideInputMethod(this);
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.layout_main_righttwo_btn:
			if(KingTellerJudgeUtils.isEmpty(assignWorkerName.getFieldValue())){
				T.showShort(mContext, "您的搜索条件不能为空!");
				return;
			}
			currentPage = 1;
			getMachineinfoSimples();
			break;
		case R.id.btn_submit:
			if(adapter.getWorkerNameCheckedList().size() == 1){
				Intent intent = new Intent();
				AssignWorkerNameBean awnb = (AssignWorkerNameBean)adapter.getWorkerNameCheckedList().get(0);
				CommonSelectData commonSelectData = new CommonSelectData();
				String workFlag = "";
				if(awnb.getWorkFlag() == 0){
					workFlag = "休假";
				}else if(awnb.getWorkFlag() == 2){
					workFlag = "请假";
				}else{
					workFlag = "空闲";
				}
				commonSelectData.setText( awnb.getUserName()+"\t\t"+awnb.getSystemProposeFlag()+"\n"+awnb.getUserAccount()
						+"\t\t"+awnb.getLinkPhone()+"\t\t"+workFlag);
				commonSelectData.setValue(awnb.getUserId());
				intent.putExtra("workerNameData",commonSelectData);
				setResult(RESULT_OK, intent);
				finish();
			}else{
				T.showShort(mContext, "您只能选择一个维护人员!");
			}
			break;
		case R.id.btn_cancel:
			finish();
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
		// TODO Auto-generated method stub
		currentPage ++ ;
		getMachineinfoSimples();
	}

	public void getServicePeople(){
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("ssbscid", ssbscid);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CxfwzryUrl), params,
				new AjaxHttpCallBack<BasePageBean<AssignWorkerNameBean>>(this,
						new TypeToken<BasePageBean<AssignWorkerNameBean>>() {
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
					public void onDo(BasePageBean<AssignWorkerNameBean> basePageBean) {
						List<AssignWorkerNameBean> data = basePageBean.getList();
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
	
	public void getMachineinfoSimples() {

		KTHttpClient fh = new KTHttpClient(true);
		// 获取condition实例
		LinearLayout conditionLayout = (LinearLayout) findViewById(R.id.workorder_condition);
		Condition cond = ConditionUtils.getCondition(this, conditionLayout);
		AjaxParams params = ConditionUtils.getAjaxParams(cond, currentPage);
		params.put("user", assignWorkerName.getFieldValue());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WhryUrl), params,
				new AjaxHttpCallBack<BasePageBean<AssignWorkerNameBean>>(this,
						new TypeToken<BasePageBean<AssignWorkerNameBean>>() {
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
					public void onDo(BasePageBean<AssignWorkerNameBean> basePageBean) {
						List<AssignWorkerNameBean> data = basePageBean.getList();
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
