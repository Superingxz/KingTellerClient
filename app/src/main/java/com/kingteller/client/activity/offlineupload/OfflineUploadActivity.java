package com.kingteller.client.activity.offlineupload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.workorder.BaseReportActivity;
import com.kingteller.client.adapter.OfflineUploadAdapter;
import com.kingteller.client.adapter.OfflineUploadAdapter.ChangeOfflineDataClickL;
import com.kingteller.client.bean.db.QRDotDelivery;
import com.kingteller.client.bean.db.QRDotMachine;
import com.kingteller.client.bean.db.QRDotMachineReplace;
import com.kingteller.client.bean.db.QROfflineDotMachine;
import com.kingteller.client.bean.db.QROfflineDotMachineReplace;
import com.kingteller.client.bean.db.Report;
import com.kingteller.client.bean.offlineupload.OfflineUploadBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;


/**
 * 缓存数据列表
 * @author Administrator
 */
public class OfflineUploadActivity extends Activity implements 
	OnClickListener, OnItemClickListener {
	
	private Button mOffline_Up, mOffline_Delete, mOffline_SelectAll;
	private ListView mOfflineListView;
	private OfflineUploadAdapter listadapter;
	
	private TextView title_text;
	private Button title_left_btn;
	
	private Context mContext;
	
	public static Class<?>[] mDataClass = new Class<?>[] {
			Report.class,	// 报告 缓存表
			QRDotMachine.class, //设备录入 缓存表
			QRDotMachineReplace.class, //设备部件更换	缓存表
			QRDotDelivery.class,	//网点发货 缓存表
			QROfflineDotMachine.class,//离线设备录入	缓存表
			QROfflineDotMachineReplace.class}; //离线部件更换	缓存表
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_offlineupload);
		KingTellerApplication.addActivity(this);
		
		mContext = OfflineUploadActivity.this;
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_text.setText("离线任务");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		mOffline_Up = (Button)findViewById(R.id.offlineupload_btn_up);
		mOffline_Delete = (Button)findViewById(R.id.offlineupload_btn_delete);
		mOffline_SelectAll = (Button)findViewById(R.id.offlineupload_btn_selectall);
		
		mOfflineListView = (ListView) findViewById(R.id.offlineupload_listview);
		listadapter = new OfflineUploadAdapter(mContext, new ArrayList<OfflineUploadBean>());
		mOfflineListView.setAdapter(listadapter);
		
		listadapter.setChangeOfflineDataClickL( new ChangeOfflineDataClickL() {
			@Override
			public void ChangeOfflineDataClick() {
				initData();
			}
		});
		
		mOffline_Up.setOnClickListener(this);
		mOffline_Delete.setOnClickListener(this);
		mOffline_SelectAll.setOnClickListener(this);
	}
	
	private void initData() {
		List<OfflineUploadBean> demoDatas = new ArrayList<OfflineUploadBean>();
		
		for(int i = 0; i < mDataClass.length; i++){
			List<?> mDataList = KingTellerDbUtils.getCacheDataAll(this, mDataClass[i]);
			if(mDataList.size() > 0){
				for (Object data :  mDataList) {
					switch (i) {
						case 0:// 从数据库读取报告数据 
//							Report mReportData = (Report) data;
//							demoDatas.add(new OfflineUploadBean(mReportData.getOrderId(), 
//									mReportData.getReportType(),
//									mReportData.getReportData(), 
//									mReportData.getReportOtherData(), 
//									mReportData.getReportTime(), 
//									mReportData.getIsSuccess()));
							break;
						case 1:// 从数据库读取网点设备录入数据 
							QRDotMachine mQRDotMachineData = (QRDotMachine) data;
							demoDatas.add(new OfflineUploadBean(mQRDotMachineData.getDotMachineId(), 
									mQRDotMachineData.getDotMachineType(),
									mQRDotMachineData.getDotMachineData(), 
									mQRDotMachineData.getDotMachineOtherData(), 
									mQRDotMachineData.getDotMachineTime(), 
									mQRDotMachineData.getIsSuccess()));
							break;
						case 2:// 从数据库读取网点设备部件更换数据 
							QRDotMachineReplace mQRDotMachineReplaceData = (QRDotMachineReplace) data;
							demoDatas.add(new OfflineUploadBean(mQRDotMachineReplaceData.getDotMachineReplaceId(), 
									mQRDotMachineReplaceData.getDotMachineReplaceType(),
									mQRDotMachineReplaceData.getDotMachineReplaceData(), 
									mQRDotMachineReplaceData.getDotMachineReplaceOtherData(), 
									mQRDotMachineReplaceData.getDotMachineReplaceTime(), 
									mQRDotMachineReplaceData.getIsSuccess()));
							break;
						case 3:// 从数据库读取网点发货数据
//							QRDotDelivery mQRDotDeliveryData = (QRDotDelivery) data;
//							demoDatas.add(new OfflineUploadBean(mQRDotDeliveryData.getDotDeliveryId(), 
//									mQRDotDeliveryData.getDotDeliveryType(),
//									mQRDotDeliveryData.getDotDeliveryData(), 
//									mQRDotDeliveryData.getDotDeliveryOtherData(), 
//									mQRDotDeliveryData.getDotDeliveryTime(), 
//									mQRDotDeliveryData.getIsSuccess()));
							break;
						case 4:// 从数据库读取 离线设备录入 数据
							QROfflineDotMachine mQROfflineDotMachineData = (QROfflineDotMachine) data;
							demoDatas.add(new OfflineUploadBean(mQROfflineDotMachineData.getOfflinedotMachineId(), 
									mQROfflineDotMachineData.getOfflinedotMachineType(),
									mQROfflineDotMachineData.getOfflinedotMachineData(), 
									mQROfflineDotMachineData.getOfflinedotMachineOtherData(), 
									mQROfflineDotMachineData.getOfflinedotMachineTime(), 
									mQROfflineDotMachineData.getIsSuccess()));
							break;
						case 5:// 从数据库读取 离线部件更换 数据
							QROfflineDotMachineReplace mQROfflineDotMachineReplaceData = (QROfflineDotMachineReplace) data;
							demoDatas.add(new OfflineUploadBean(mQROfflineDotMachineReplaceData.getOfflinedotMachineReplaceId(), 
									mQROfflineDotMachineReplaceData.getOfflinedotMachineReplaceType(),
									mQROfflineDotMachineReplaceData.getOfflinedotMachineReplaceData(), 
									mQROfflineDotMachineReplaceData.getOfflinedotMachineReplaceOtherData(), 
									mQROfflineDotMachineReplaceData.getOfflinedotMachineReplaceTime(), 
									mQROfflineDotMachineReplaceData.getIsSuccess()));
							break;
						default:
							break;
					}
				}
			}
		}
		
		listadapter.setLists(demoDatas);
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() != R.id.layout_main_left_btn && listadapter.getCount() == 0){
			T.showShort(mContext, "暂无数据!");
			return;
		}
		
		// 获取当前的数据数量
		int count = listadapter.getCount();
		HashMap<Integer, Boolean> map = listadapter.getCheckMap();
		
		if(v.getId() == R.id.offlineupload_btn_up || v.getId() == R.id.offlineupload_btn_delete){
			//被选中数量
			int inSelect = 0;
			for (int i = 0; i < count; i++) {
				if (map.get(i) != null && map.get(i)) {
					inSelect ++;
				}
			}
			if(inSelect == 0){
				T.showShort(mContext, "没有选择数据!");
				return;
			}
		}

		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				finish();
				break;
			case R.id.offlineupload_btn_up:
				UploadCeacheData();
				break;
			case R.id.offlineupload_btn_delete:
				
				final NormalDialog dialog_sc = new NormalDialog(mContext);
		    	KingTellerPromptDialogUtils.showSaveTwoPromptDialog(dialog_sc, "是否删除当前选择的数据？", "取消", "删除",
						new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog_sc.dismiss();
							}
		                }, new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog_sc.dismiss();
								
								DeleteCeacheData();
		        				T.showShort(mContext, "成功删除!");
							}
		                });
		
				break;
			case R.id.offlineupload_btn_selectall:
				
				if (mOffline_SelectAll.getText().toString().trim().equals("全选")) {
					Log.e("缓存数据","全选");
					// 所有项目全部选中
					listadapter.configCheckMap(true);
					listadapter.notifyDataSetChanged();

					mOffline_SelectAll.setText("全不选");
				} else {
					Log.e("缓存数据","全不选");
					// 所有项目全部不选中
					listadapter.configCheckMap(false);
					listadapter.notifyDataSetChanged();

					mOffline_SelectAll.setText("全选");
				}
				break;
		}
		
	}

	/**
	 * 上传数据
	 */
	public void UploadCeacheData(){
		//获取选中list
		List<OfflineUploadBean> updata = new ArrayList<OfflineUploadBean>();
		
		for (int i = 0; i < listadapter.getCount(); i++) {
			if (listadapter.getCheckMap().get(i) != null && listadapter.getCheckMap().get(i)) {
				updata.add((OfflineUploadBean) listadapter.getItem(i));
			}
		}
		
		//判断当前选中的list中是否 还没选中重要信息
		for(int l = 0; l<updata.size(); l++){
			String [] other = updata.get(l).getCacheOtherData().split(",");

			if("dotmachine_offline".equals(updata.get(l).getCacheType())){
				if("0".equals(other[0]) && "0".equals(other[1])){
					T.showShort(mContext, "请选择机器编号!");
					return;
				}
			}
			
			if("dotmachinereplace_offline".equals(updata.get(l).getCacheType())){
				if("0".equals(other[0]) && "0".equals(other[1])){
					T.showShort(mContext, "请选择工单单号!");
					return;
				}
			}
		}
		
		//上传
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("offlineList", new Gson().toJson(updata));
		
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.OfflineUpData),
				params, new AjaxHttpCallBack<List<OfflineUploadBean>>(mContext,
						new TypeToken<List<OfflineUploadBean>>() {}.getType(), true) {
			
			@Override
			public void onStart() {
				KingTellerProgressUtils.showProgress(mContext, "正在提交中...");
			}

			@Override
			public void onFinish() {
				KingTellerProgressUtils.closeProgress();
			}
			
			@Override
			public void onDo(List<OfflineUploadBean> data) {
				if("".equals(data.get(0).getCode())){
					T.showShort(mContext, "成功");
					
					DeleteCeacheData();
					listadapter.notifyDataSetChanged();
					
				}else{
					T.showShort(mContext, data.get(0).getCode());
				}
			};
		});
		
		
	}
	
	/**
	 * 删除数据
	 */
	public void DeleteCeacheData(){
		// 获取当前的数据数量
		int count = listadapter.getCount();
		HashMap<Integer, Boolean> map = listadapter.getCheckMap();
		
		// 进行遍历
		for (int i = 0; i < count; i++) {
			// 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
			int position = i - (count - listadapter.getCount());
			if (map.get(i) != null && map.get(i)) {
				OfflineUploadBean bean = (OfflineUploadBean) listadapter.getItem(position);
				//做本地数据删除
				deletCeacheData(bean.getCacheType(), bean.getCacheDataId());
				
				listadapter.getCheckMap().remove(i);
				listadapter.remove(position);
			}
		}
		listadapter.notifyDataSetChanged();
	}
	
	public void deletCeacheData(String type, String id){
		if(!KingTellerJudgeUtils.isEmpty(type)){
			switch (KingTellerDbUtils.getTypeValue(type)) {
				case 0:	break;
				case 1:	KingTellerDbUtils.deleteReportFromDataBase(mContext, id);break;
				case 2: KingTellerDbUtils.deleteReportFromDataBase(mContext, id);break;
				case 3: KingTellerDbUtils.deleteReportFromDataBase(mContext, id);break;
				case 4: break;
				case 5: KingTellerDbUtils.deleteDotMachineToDataBase(mContext, id);break;
				case 6: KingTellerDbUtils.deleteDotMachineReplaceToDataBase(mContext, id);break;
				case 7: KingTellerDbUtils.deleteDotDeliveryToDataBase(mContext, id);break;
				case 8: KingTellerDbUtils.deleteOfflineDotMachineToDataBase(mContext, id);break;
				case 9: KingTellerDbUtils.deleteOfflineDotMachineReplaceToDataBase(mContext, id);break;
				default: T.showShort(mContext, "缓存数据类型不存在!");break;
			}
		}else{
			T.showShort(mContext, "缓存数据为空!");
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
