package com.kingteller.client.activity.logisticmonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.activity.more.FeedBackActivity;
import com.kingteller.client.adapter.NzyTxBgAdapter;
import com.kingteller.client.adapter.YtjTxBgAdapter;
import com.kingteller.client.adapter.ZyTxBgAdapter;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.logisticmonitor.AloneTaskAndConsignBean;
import com.kingteller.client.bean.logisticmonitor.FareDetailParam;
import com.kingteller.client.bean.logisticmonitor.HwDataBean;
import com.kingteller.client.bean.logisticmonitor.LogisticConsignBasicBean;
import com.kingteller.client.bean.logisticmonitor.LogisticConsignMobileBean;
import com.kingteller.client.bean.logisticmonitor.LogisticObjectBean;
import com.kingteller.client.bean.logisticmonitor.LogisticTaskBean;
import com.kingteller.client.bean.logisticmonitor.TydDataBean;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ConditionView;
import com.kingteller.client.view.GroupListView;
import com.kingteller.client.view.GroupListView.AddViewCallBack;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.ListViewForScrollView;
import com.kingteller.client.view.LogisticTaskFareGroupView;
import com.kingteller.framework.http.AjaxParams;

public class WriteLogisticTaskActivity extends BaseActivity implements
		OnClickListener {

	private String rwdId;
	private KingTellerEditText tylx;
	private KingTellerEditText thrqStr;
	private KingTellerEditText psddrqStr;
	private KingTellerEditText sfeczy;
	private KingTellerEditText cl;
	private KingTellerEditText scry;
	private KingTellerEditText zydw;
	private KingTellerEditText zylxr;
	private KingTellerEditText zylxdh;
	private KingTellerEditText zydwdz;
	private KingTellerEditText bz;
	private KingTellerEditText qcqlcs;
	private KingTellerEditText fhckhlcs;
	private GroupListView costType_group_list;
	private ListViewForScrollView listView;
	private Button btn_dial;
	protected int optType = 0;
	private LogisticConsignBasicBean basicBean = new LogisticConsignBasicBean();
	private LogisticTaskBean taskBean = new LogisticTaskBean();
	private LogisticConsignMobileBean consignBean = new LogisticConsignMobileBean();
	private List<LogisticConsignMobileBean> tydlist = new ArrayList<LogisticConsignMobileBean>();
	private ZyTxBgAdapter zyTxBgAdapter;
	private NzyTxBgAdapter nzyTxBgAdapter;
	private YtjTxBgAdapter ytjTxBgAdapter;
	private String pdzLike;

	private Context mContext;
	@Override
	public void onCreate(Bundle paramBundle) {
		// TODO Auto-generated method stub
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_write_logistic);
		mContext = WriteLogisticTaskActivity.this;
		initUI();
		initData();
	}

	private void initUI() {
		title_title.setText("任务内容");
		title_left.setOnClickListener(this);
		
		//KingTellerProgress.showProgress(this, "加载中");

		tylx = (KingTellerEditText) findViewById(R.id.tylx);
		thrqStr = (KingTellerEditText) findViewById(R.id.thrqStr);
		psddrqStr = (KingTellerEditText) findViewById(R.id.psddrqStr);
		sfeczy = (KingTellerEditText) findViewById(R.id.sfeczy);
		cl = (KingTellerEditText) findViewById(R.id.cl);
		scry = (KingTellerEditText) findViewById(R.id.scry);
		zydw = (KingTellerEditText) findViewById(R.id.zydw);
		zylxr = (KingTellerEditText) findViewById(R.id.zylxr);
		zylxdh = (KingTellerEditText) findViewById(R.id.zylxdh);
		zydwdz = (KingTellerEditText) findViewById(R.id.zydwdz);
		bz = (KingTellerEditText) findViewById(R.id.bz);
		qcqlcs = (KingTellerEditText) findViewById(R.id.qcqlcs);
		fhckhlcs = (KingTellerEditText) findViewById(R.id.fhckhlcs);
		listView = (ListViewForScrollView) findViewById(R.id.hwxx_list);
		
		costType_group_list = (GroupListView) findViewById(R.id.costType_group_list);
		costType_group_list.setAddViewCallBack(new AddViewCallBack() {

			@Override
			public void addView(GroupListView view) {
				view.addItem(new LogisticTaskFareGroupView(
						WriteLogisticTaskActivity.this, true));
			}
		});

		costType_group_list.addItem(new LogisticTaskFareGroupView(WriteLogisticTaskActivity.this,
				false));
		
		costType_group_list.setAddBtnHidden(true);
		
		/*LogisticTaskFareGroupView bjView = new LogisticTaskFareGroupView(this, false);
		bjView.setOnClickListener(this);
		costType_group_list.addItem(bjView);*/

		rwdId = getIntent().getStringExtra("rwdId");
		
		btn_dial = (Button) findViewById(R.id.btn_dial);
		btn_dial.setOnClickListener(this);
		
		qcqlcs.setFouces(false);
		fhckhlcs.setFouces(false);
		
		zylxdh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(WriteLogisticTaskActivity.this, ((Button) arg0)
						.getText().toString());
			}
		});
		
		KingTellerConfigUtils.hideInputMethod(this);
	}

	public void initData() {
		
		getLogisticTask();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.button_left:
			back();
			break;
		case R.id.btn_submit:
			if(checkNet(true)){
				submitReport("s");
			}
			getAllData();
			break;
		case R.id.btn_temp_save:
			if (checkNet(false)){
				submitReport("z");
			}
			break;
		case R.id.btn_dial:
			dial(pdzLike);
			break;
		default:
			break;
		}
	}
	
	private void dial(String telephone){
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+telephone));    
        startActivity(intent);  
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.SELECT_FYLX:
			if (resultCode == RESULT_OK) {
				costType_group_list.setItemData((CommonSelectData) data
						.getSerializableExtra(CommonSelectDataActivity.DATA));
			}
			break;
		default:
			break;
		}
	}
	
	// 得到界面数据
	private void getLogisticTask() {
		// TODO Auto-generated method stub
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("rwdId", rwdId);
		params.put("type", "tx");

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WljkrwdUrl), params,
				new AjaxHttpCallBack<AloneTaskAndConsignBean>(this,
						new TypeToken<AloneTaskAndConsignBean>() {
						}.getType(), true) {
					
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						KingTellerProgressUtils.closeProgress();
					}
			
					@Override
					public void onDo(AloneTaskAndConsignBean data) {
						setInfoDate(data);
					};
				});

	}

	private void setInfoDate(AloneTaskAndConsignBean data) {
		basicBean = data.getBasic();
		taskBean = data.getTask();
		tydlist = data.getTydlist();
		consignBean = tydlist.get(0);
		
		pdzLike = data.getTydlist().get(0).getConsign().getPdzLike();

		tylx.setFieldTextAndValue(basicBean.getTylxLike());
		thrqStr.setFieldTextAndValue(basicBean.getThrqStr());
		
		qcqlcs.setFieldTextAndValue(taskBean.getQcqlcs()+" km");
		fhckhlcs.setFieldTextAndValue(taskBean.getFhckhlcs()+" km");

		psddrqStr.setFieldTextAndValue(basicBean.getPsddrqStr());
		// thdd.setFieldTextAndValue(basicBean.get);
		sfeczy.setFieldTextAndValue(basicBean.getSfeczy());
		if (basicBean.getTylx().equals("Y") || basicBean.getTylx().equals("T")) {
			ytjTxBgAdapter = new YtjTxBgAdapter(this, tydlist);
			listView.setAdapter(ytjTxBgAdapter);
		} else {
			if (basicBean.getSfeczy().equals("是")) {
				findViewById(R.id.zymk).setVisibility(View.VISIBLE);
				zydw.setFieldTextAndValue(consignBean.getConsign().getZydwLike());
				zylxr.setFieldTextAndValue(consignBean.getConsign().getZylxr());
				zylxdh.setFieldTextAndValue(consignBean.getConsign().getZylxdh());
				zydwdz.setFieldTextAndValue(consignBean.getConsign().getZydwdz());

				zyTxBgAdapter = new ZyTxBgAdapter(this, tydlist);
				listView.setAdapter(zyTxBgAdapter);
			} else if (basicBean.getSfeczy().equals("否")) {
				nzyTxBgAdapter = new NzyTxBgAdapter(this, tydlist);
				listView.setAdapter(nzyTxBgAdapter);
			}
		}
		cl.setFieldTextAndValue(taskBean.getClLike());
		scry.setFieldTextAndValue(taskBean.getGcy1Like() + "  "+ taskBean.getGcy2Like());
		bz.setFieldTextAndValue(taskBean.getBz());
		int size = 0;
		if (data.getFareDetailList() != null){
			size =data.getFareDetailList().size();
			if (size > 0){
				costType_group_list.getLayoutList().removeAllViews();
				for (int i = 0; i < size; i++) {
					LogisticTaskFareGroupView fview;
					fview = new LogisticTaskFareGroupView(WriteLogisticTaskActivity.this, false);
					((KingTellerEditText)fview.findViewById(R.id.fylx)).setFieldCheckoutEnabled(false);
					((KingTellerEditText)fview.findViewById(R.id.je)).setFieldCheckoutEnabled(false);
					FareDetailParam  fdata = new FareDetailParam();
					fdata.setFylx(data.getFareDetailList().get(i).getFylx());
					fdata.setFylxLike(data.getFareDetailList().get(i).getFylxLike());
					fdata.setJe(data.getFareDetailList().get(i).getJe());
					fview.setData(fdata);
					costType_group_list.addItem(fview);
				}
			}else{
				costType_group_list.getLayoutList().removeAllViews();
			}
		}
	}
	
	
	private String getFromData(){
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		List<FareDetailParam>  fareList = costType_group_list.getListData();
		
		params.put("rwdId", rwdId);
		params.put("tylx", basicBean.getTylx());
		params.put("tydList", getRdbList(params));
		params.put("feeList", fareList);
		params.put("qclcs", qcqlcs.getFieldValue());
		params.put("fhlcs", fhckhlcs.getFieldValue());
		
		Log.e("111111111111", ConditionUtils.getJsonFromHashMap(params));
		return ConditionUtils.getJsonFromHashMap(params);
	}
	
	public String getAllData(){
		AloneTaskAndConsignBean  atacb = new AloneTaskAndConsignBean();//整体
		LogisticTaskBean task = new LogisticTaskBean();//任务单
		LogisticConsignBasicBean basic = new LogisticConsignBasicBean();//基本信息
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		List<LogisticConsignMobileBean> tydist = new ArrayList<LogisticConsignMobileBean>();//托运单列表
		List<LogisticObjectBean> hwList; //货物名称和数量list
		LogisticObjectBean lob;
		
		List<FareDetailParam>  fareList = costType_group_list.getListData();//费用列表
		
		task = taskBean;
		basic = basicBean;
		task.setQcqlcs(qcqlcs.getFieldValue());
		task.setFhckhlcs(fhckhlcs.getFieldValue());
		
		tydist = tydlist;
		List<TydDataBean> tydDataBeanList = getRdbList(params);
		
		
		for(int i =0 ; i < tydDataBeanList.size(); i ++){
			for(int j =0 ; j < tydist.size(); j ++){
				if(tydDataBeanList.get(i).getTydId().equals(tydist.get(j).getConsign().getId())){
					hwList = new ArrayList<LogisticObjectBean>();
					for(int k =0;k < tydDataBeanList.get(i).getHdbList().size(); k ++){
						lob = new LogisticObjectBean();
						lob.setHwId(tydDataBeanList.get(i).getHdbList().get(k).getHwId());
						lob.setJqbm(tydDataBeanList.get(i).getHdbList().get(k).getJqbm());
						lob.setJqhwid(tydDataBeanList.get(i).getHdbList().get(k).getJqhwId());
						lob.setPsdz(tydDataBeanList.get(i).getHdbList().get(k).getPsdz());
						lob.setQsdlsh(tydDataBeanList.get(i).getHdbList().get(k).getQsdlsh());
						lob.setSjpsrq(tydDataBeanList.get(i).getHdbList().get(k).getSjpsrq());
						lob.setSqxxz(tydDataBeanList.get(i).getHdbList().get(k).getSqxz());
						lob.setName(tydDataBeanList.get(i).getHdbList().get(k).getName());
						lob.setSl(tydDataBeanList.get(i).getHdbList().get(k).getSl());
						hwList.add(lob);
					}
					tydist.get(j).setHwList(hwList);
				}
			}
		}
		atacb.setTask(task);
		atacb.setBasic(basic);
		atacb.setFareDetailList(fareList);
		atacb.setTydlist(tydist);
		return KingTellerJsonUtils.convertJson(atacb);
		
	}
	
	private List<TydDataBean> getRdbList(HashMap<String, Object> params){
		List<TydDataBean>  rdbList = null;
		List<HwDataBean> hdbList;
		TydDataBean rdb;
		HwDataBean hdb;
		
		if(basicBean.getTylx().equals("Y") || basicBean.getTylx().equals("T")){
			rdbList = new ArrayList<TydDataBean>();
			for(int i = 0; i < tydlist.size(); i ++){
				List<LogisticObjectBean> hwList = tydlist.get(i).getHwList();
				hdbList = new ArrayList<HwDataBean>();
				rdb = new TydDataBean();
				ListViewForScrollView lvfsv = (ListViewForScrollView)listView.getChildAt(i).findViewById(R.id.listViewData);
				for(int j = 0; j <  hwList.size(); j ++ ){
					hdb = new HwDataBean();
					hdb.setPsdz(((KingTellerEditText)lvfsv.getChildAt(j).findViewById(R.id.sjps)).getFieldText());
					hdb.setSqxz(((KingTellerEditText)lvfsv.getChildAt(j).findViewById(R.id.sqxz)).getFieldValue());
					hdb.setQsdlsh(((KingTellerEditText)lvfsv.getChildAt(j).findViewById(R.id.qsdlsh)).getFieldText());
					hdb.setSjpsrq(((ConditionView) lvfsv.getChildAt(j).findViewById(R.id.sjpsrq)).getFieldValue());
					hdb.setName(((TextView)lvfsv.getChildAt(j).findViewById(R.id.hwmc)).getText().toString());
					hdb.setSl(((TextView)lvfsv.getChildAt(j).findViewById(R.id.hwsl)).getText().toString());
					hdb.setHwId(hwList.get(j).getHwId());
					hdb.setJqhwId(hwList.get(j).getJqhwid());
					
					hdbList.add(hdb);
				}
				rdb.setHdbList(hdbList);
				rdb.setTydId(tydlist.get(i).getConsign().getId());
				rdbList.add(rdb);
			}
		}else{
			if (basicBean.getSfeczy().equals("否")) {
				rdbList = new ArrayList<TydDataBean>();
				for(int i = 0; i < tydlist.size(); i ++){
					List<LogisticObjectBean> hwList = tydlist.get(i).getHwList();
					hdbList = new ArrayList<HwDataBean>();
					rdb = new TydDataBean();
					ListViewForScrollView lvfsv = (ListViewForScrollView)listView.getChildAt(i).findViewById(R.id.listViewData);
					for(int j = 0; j <  hwList.size(); j ++ ){
						hdb = new HwDataBean();
						hdb.setHwId(hwList.get(j).getHwId());
						hdb.setPsdz(((KingTellerEditText)lvfsv.getChildAt(j).findViewById(R.id.sjps)).getFieldText());
						hdb.setSqxz(((KingTellerEditText)lvfsv.getChildAt(j).findViewById(R.id.sqxz)).getFieldValue());
						hdb.setJqbm(((KingTellerEditText)lvfsv.getChildAt(j).findViewById(R.id.jqbm)).getFieldText());
						hdb.setQsdlsh(((KingTellerEditText)lvfsv.getChildAt(j).findViewById(R.id.qsdlsh)).getFieldText());
						hdb.setSjpsrq(((ConditionView) lvfsv.getChildAt(j).findViewById(R.id.sjpsrq)).getFieldValue());
						hdb.setName(((TextView)lvfsv.getChildAt(j).findViewById(R.id.hwmc)).getText().toString());
						hdb.setSl(((TextView)lvfsv.getChildAt(j).findViewById(R.id.hwsl)).getText().toString());
						hdbList.add(hdb);
					}
					rdb.setHdbList(hdbList);
					rdb.setTydId(tydlist.get(i).getConsign().getId());
					rdbList.add(rdb);
				}
				params.put("sfeczy", "0");
			}else if(basicBean.getSfeczy().equals("是")){
				rdbList = new ArrayList<TydDataBean>();
				for(int i = 0; i < tydlist.size(); i ++){
					List<LogisticObjectBean> hwList = tydlist.get(i).getHwList();
					hdbList = new ArrayList<HwDataBean>();
					rdb = new TydDataBean();
					ListViewForScrollView lvfsv = (ListViewForScrollView)listView.getChildAt(i).findViewById(R.id.listViewData);
					for(int j = 0; j <  hwList.size(); j ++ ){
						hdb = new HwDataBean();
						hdb.setQsdlsh(((KingTellerEditText)lvfsv.getChildAt(j).findViewById(R.id.qsdlsh)).getFieldText());
						//hdb.setSjpsrq(((ConditionView) lvfsv.getChildAt(j).findViewById(R.id.sjpsrq)).getFieldValue());
						hdb.setHwId(hwList.get(j).getHwId());
						hdb.setName(((TextView)lvfsv.getChildAt(j).findViewById(R.id.hwmc)).getText().toString());
						hdb.setSl(((TextView)lvfsv.getChildAt(j).findViewById(R.id.hwsl)).getText().toString());
						hdbList.add(hdb);
					}
					rdb.setHdbList(hdbList);
					rdb.setTydId(tydlist.get(i).getConsign().getId());
					rdbList.add(rdb);
				}
				params.put("sfeczy", "1");
			}
			
		}
		return rdbList;
	}
	
	private void submitReport(final String flag){
		
		if(flag.equals("s")){
			if(KingTellerJudgeUtils.isEmpty(qcqlcs.getFieldValue())){
				T.showShort(mContext, "启程里程数必须填写!");
				return;
			}
			if(KingTellerJudgeUtils.isEmpty(fhckhlcs.getFieldValue())){
				T.showShort(mContext, "返回里程数必须填写!");
				return;
			}
			
			List<FareDetailParam>  list = costType_group_list.getListData();
			if(list.size() == 0){
				T.showShort(mContext, "费用信息至少填写一项!");
				return;
			}else{
				for (int j = 0; j < list.size(); j++) {
					if(KingTellerJudgeUtils.isEmpty(list.get(j).getFylx())){
						T.showShort(mContext, "费用类型必须填写!");
						return;
					}
					if(KingTellerJudgeUtils.isEmpty(list.get(j).getJe())){
						T.showShort(mContext, "费用必须要填写!");
						return;
					}
				}
			}
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			List<TydDataBean>  tyddbList = getRdbList(params);
			for(int i =0; i < tyddbList.size();i ++){
				for(int j =0;j < tyddbList.get(i).getHdbList().size(); j ++){
					if(basicBean.getTylx().equals("Y") || basicBean.getTylx().equals("T")){
						if(KingTellerJudgeUtils.isEmpty(tyddbList.get(i).getHdbList().get(j).getPsdz())){
							T.showShort(mContext, "实际配送必须填写!");
							return;
						}
						if(KingTellerJudgeUtils.isEmpty(tyddbList.get(i).getHdbList().get(j).getQsdlsh())){
							T.showShort(mContext, "签收单流水号必须填写!");
							return;
						}
						if(KingTellerJudgeUtils.isEmpty(tyddbList.get(i).getHdbList().get(j).getSqxz())){
							T.showShort(mContext, "市区/县乡镇必须填写!");
							return;
						}
					}else{
						if (basicBean.getSfeczy().equals("否")){
							if(KingTellerJudgeUtils.isEmpty(tyddbList.get(i).getHdbList().get(j).getPsdz())){
								T.showShort(mContext, "实际配送必须填写!");
								return;
							}
							if(KingTellerJudgeUtils.isEmpty(tyddbList.get(i).getHdbList().get(j).getQsdlsh())){
								T.showShort(mContext, "签收单流水号必须填写!");
								return;
							}
							if(KingTellerJudgeUtils.isEmpty(tyddbList.get(i).getHdbList().get(j).getSqxz())){
								T.showShort(mContext, "市区/县乡镇必须填写!");
								return;
							}
							if(KingTellerJudgeUtils.isEmpty(tyddbList.get(i).getHdbList().get(j).getJqbm())){
								T.showShort(mContext, "机器编码必须填写!");
								return;
							}
						}else if(basicBean.getSfeczy().equals("是")){
							if(KingTellerJudgeUtils.isEmpty(tyddbList.get(i).getHdbList().get(j).getQsdlsh())){
								T.showShort(mContext, "签收单流水号必须填写!");
								return;
							}
						}
					}
				}
			}
		
		}
		
		final String assign = getFromData();
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("data", assign);
		ajaxParams.put("method", flag);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.TjZcBgUrl),
				ajaxParams, new AjaxHttpCallBack<ReturnBackStatus>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(WriteLogisticTaskActivity.this, "正在保存数据...");
					}

					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onError(int errorNo, String strMsg) {
						KingTellerDbUtils.saveLogsticReportToDataBase(WriteLogisticTaskActivity.this, rwdId,0,getAllData());
					}

					@Override
					public void onDo(ReturnBackStatus data) {
						// TODO Auto-generated method stub

						if (data.getResult().equals("success")) {
							if("z".equals(flag)){
								KingTellerDbUtils.saveLogsticReportToDataBase(WriteLogisticTaskActivity.this, rwdId,1,getAllData());
								
								T.showShort(mContext, "保存成功!");
							}else if("s".equals(flag)){
								KingTellerDbUtils.deleteLogsticReportFromDataBase(
										WriteLogisticTaskActivity.this,
										rwdId);
								T.showShort(mContext, data.getMessage());
								finish();
							}
						}else{
							KingTellerDbUtils.saveLogsticReportToDataBase(WriteLogisticTaskActivity.this, rwdId,0,getAllData());
							T.showShort(mContext, data.getMessage());
						}
					}
				});
	}
	
	public boolean checkNet(boolean isSubmit) {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			new KTAlertDialog.Builder(this)
					.setTitle("提醒")
					.setMessage("网络不可用，是否离线保存？")
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface, int pos) {
									KingTellerDbUtils.saveLogsticReportToDataBase(
											WriteLogisticTaskActivity.this,
											rwdId, 0,getAllData());
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

			return false;
		} /*else if (!CommonUtils.isWifi(this) && !isSubmit) {
			new KTAlertDialog.Builder(this)
					.setTitle("提醒")
					.setMessage("你的网络不是WIFI网络，是否上传附件？")
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface, int pos) {
									finish();
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
			return false;
		}*/

		return true;
	}

	private void back(){
		String data = KingTellerDbUtils.getLogsticReportDataFromDataBase(this, rwdId);
		try {
			final String fromdata = getAllData();
			
			if (!data.equals(fromdata))
				new KTAlertDialog.Builder(this)
						.setTitle("提醒")
						.setMessage("您的数据尚未提交，是否保存本地？")
						.setPositiveButton("保存",
								new KTAlertDialog.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialogInterface,
											int pos) {
										KingTellerDbUtils.saveLogsticReportToDataBase(
												WriteLogisticTaskActivity.this,
												rwdId, 0,fromdata);
										dialogInterface.dismiss();
										finish();

									}
								})
						.setNegativeButton("不保存",
								new KTAlertDialog.OnClickListener() {
									public void onClick(
											DialogInterface dialogInterface,
											int paramAnonymousInt) {
										dialogInterface.dismiss();
										finish();
									}
								}).create().show();
			else
				finish();
		} catch (Exception e) {
			// TODO: handle exception
			finish();
		}
	}
	
}
