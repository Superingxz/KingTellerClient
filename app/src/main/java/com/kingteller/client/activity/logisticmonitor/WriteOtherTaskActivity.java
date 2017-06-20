package com.kingteller.client.activity.logisticmonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.base.BaseFragmentActivity;
import com.kingteller.client.adapter.OtherTaskConsignAdapter;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.logisticmonitor.FareDetailParam;
import com.kingteller.client.bean.logisticmonitor.OtherTaskConsignBean;
import com.kingteller.client.bean.logisticmonitor.ResultStatus;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.GroupListView;
import com.kingteller.client.view.GroupListView.AddViewCallBack;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.client.view.GroupViewBase;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.ListViewForScrollView;
import com.kingteller.client.view.OtherTaskFareGroupView;
import com.kingteller.framework.http.AjaxParams;

public class WriteOtherTaskActivity extends BaseFragmentActivity implements
		OnClickListener {

	private ListViewForScrollView lvfsv;
	private GroupListView costType_group_list;
	private OtherTaskConsignAdapter otherTaskConsignAdapter;
	private List<OtherTaskConsignBean> otherTaskConsignlist = new ArrayList<OtherTaskConsignBean>();
	private Button btn_submit;
	private KingTellerEditText qcqlcs;
	private KingTellerEditText fhckhlcs;
	private Button btn_lx;
	private Context mContext;
	//private ProgressBar progressBar;
	

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_other_task_write);
		mContext = WriteOtherTaskActivity.this;
		initUI();
		initData();
	}

	public void initUI() {
		
		title_title.setText("填写报告");
		title_left.setOnClickListener(this);

		//btn_back = (Button) findViewById(R.id.btn_back);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		lvfsv = (ListViewForScrollView) findViewById(R.id.otherconsign_li);
		lvfsv.setDivider(null);

		costType_group_list = (GroupListView) findViewById(R.id.costType_group_list);
		costType_group_list.setAddViewCallBack(new AddViewCallBack() {

			@Override
			public void addView(GroupListView view) {
				// TODO Auto-generated method stub
				view.addItem(new OtherTaskFareGroupView(
						WriteOtherTaskActivity.this, true));
			}
		});

		OtherTaskFareGroupView bjView = new OtherTaskFareGroupView(this, false);
		bjView.setOnClickListener(this);
		costType_group_list.addItem(bjView);
		
		qcqlcs = (KingTellerEditText) findViewById(R.id.qcqlcs);
		fhckhlcs = (KingTellerEditText) findViewById(R.id.fhckhlcs);
		btn_lx = (Button) findViewById(R.id.btn_lx);
		//btn_back.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		btn_lx.setOnClickListener(this);

		KingTellerConfigUtils.hideInputMethod(this);
	}

	public void initData() {
		otherTaskConsignAdapter = new OtherTaskConsignAdapter(this, otherTaskConsignlist);
		getOtherTaskConsign();
		lvfsv.setAdapter(otherTaskConsignAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_left:
			finish();
			break;
		/*case R.id.btn_back:
			finish();
			break;*/
		case R.id.btn_submit:
			if(checkSubmit()){
				new KTAlertDialog.Builder(this)
				.setTitle("提示")
				.setMessage("提交后不可以修改，你确定提交？")
				.setPositiveButton("确定",
						new KTAlertDialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface,int pos) {
								submit();
								dialogInterface.dismiss();
							}
						})
				.setNegativeButton("取消",
						new KTAlertDialog.OnClickListener() {
							public void onClick(
									DialogInterface dialogInterface,
									int paramAnonymousInt) {
								dialogInterface.dismiss();
							}
						}).create().show();
			}
			break;
		case R.id.btn_lx:
			String tel = getIntent().getStringExtra("tel");
			if(tel.trim().length()!=0)
		    {
		     Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:" + tel));
		     //启动
		     startActivity(phoneIntent); 
		    }else{
		    	T.showShort(mContext, "您拨打电话不存在!");
		    }
			break;

		default:
			break;
		}

	}
	public boolean checkSubmit(){
		
		int count = costType_group_list.getLayoutList().getChildCount();
		if(count > 0){			
			for(int i = 0; i < count;i ++){
				GroupViewBase listView = (GroupViewBase)costType_group_list.getLayoutList().getChildAt(i);
				String fylx = ((KingTellerEditText)listView.findViewById(R.id.fylx)).getFieldValue();
				String je = ((KingTellerEditText)listView.findViewById(R.id.je)).getFieldValue();
				if(KingTellerJudgeUtils.isEmpty(fylx)){
					T.showShort(mContext, "费用类型不能为空!");
					return false;
				}
				if(KingTellerJudgeUtils.isEmpty(je)){
					T.showShort(mContext, "费用不能为空!");
					return false;
				}
			}
		}else{
			T.showShort(mContext, "费用信息必须填写!");
			return false;
		}
		
		if(KingTellerJudgeUtils.isEmpty(fhckhlcs.getFieldValue())){
			T.showShort(mContext, "请填写返回里程数!");
			return false;
		}
		if(Integer.parseInt(qcqlcs.getFieldValue())>Integer.parseInt(fhckhlcs.getFieldValue())){
			T.showShort(mContext, "返回里程数必须大于起程里程数!");
			return false;
		}
		return true;
	}
	
	public String getFromData(){
		HashMap<String, Object> params = ConditionUtils.getHashMapForm(this,
				(LinearLayout) findViewById(R.id.layout_from), false);
		
		List<FareDetailParam> fareList = new ArrayList<FareDetailParam>();
		int count = costType_group_list.getLayoutList().getChildCount();		
		for(int i = 0; i < count;i ++){
			FareDetailParam fd = new FareDetailParam();
			GroupViewBase listView = (GroupViewBase)costType_group_list.getLayoutList().getChildAt(i);
			String fylx = ((KingTellerEditText)listView.findViewById(R.id.fylx)).getFieldValue();
			String je = ((KingTellerEditText)listView.findViewById(R.id.je)).getFieldValue();
			fd.setFylx(fylx);
			fd.setJe(je);
			fareList.add(fd);
		}
		params.put("fareList", fareList);
		params.put("swdh", getIntent().getStringExtra("swdh"));
		return ConditionUtils.getJsonFromObj(params);
	}
	
	public void submit(){
		
		KTHttpClient fh = new KTHttpClient(true);
		// 获取condition实例
		AjaxParams params = new AjaxParams();
		params.put("params", this.getFromData());
		params.put("id", getIntent().getStringExtra("id"));

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WljkOtSubmitUrl), params,
				new AjaxHttpCallBack<ResultStatus>(this,
						new TypeToken<ResultStatus>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						
					}

					@Override
					public void onDo(ResultStatus data) {
						if (data.getResult().equals("1")) {
							T.showShort(mContext, "填写成功!");
							finish();
						}else{
							T.showShort(mContext, data.getMessage());
						}
					};
				});
	}
	
	public void getOtherTaskConsign() {	
		
		Intent intent = this.getIntent(); 
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("swdh", intent.getStringExtra("swdh"));
		params.put("cl", intent.getStringExtra("cl"));
		params.put("id", intent.getStringExtra("id"));

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WljkcUrl),
				params, new AjaxHttpCallBack<BasePageBean<OtherTaskConsignBean>>(this,
						new TypeToken<BasePageBean<OtherTaskConsignBean>>() {
						}.getType(), true) {
					
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}
					
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在加载数据中...");
					}
				
					@Override
					public void onDo(BasePageBean<OtherTaskConsignBean> data) {
						if("".equals(data.getStatus()) ){
							if (data.getList().size() > 0) {
								otherTaskConsignlist = data.getList();
								otherTaskConsignAdapter.setLists(data.getList());
								//qcqlcs.setTextAndValue(data.getList().get(0).getExpand1().toString(),data.getList().get(0).getExpand1().toString());
							} else {
								
							}
						}else{
							T.showShort(mContext, data.getMsg());
						}
					};
				});
	}
}
