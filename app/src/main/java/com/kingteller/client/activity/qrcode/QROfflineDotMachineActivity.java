package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.QRDeliveryReceiptAdapter;
import com.kingteller.client.bean.db.QROfflineDotMachine;
import com.kingteller.client.bean.offlineupload.OfflineDotMachineBean;
import com.kingteller.client.bean.qrcode.QRDotMachineBean;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;

public class QROfflineDotMachineActivity  extends Activity implements
	OnClickListener, OnItemClickListener, OnItemLongClickListener, IXListViewListener{
	
	private QRDeliveryReceiptAdapter adpater;
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	private Context mContext;
	
	private List<OfflineDotMachineBean> Lists;
	private List<Integer> mALLIdLists;
	private List<?> mALLDataList;
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_alllist_view);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		mContext = QROfflineDotMachineActivity.this;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);

		adpater = new QRDeliveryReceiptAdapter(mContext, null, null, null, null, new ArrayList<OfflineDotMachineBean>(), null, 5);
		
		getListviewObj().getListview().setAdapter(adpater);
		getListviewObj().getListview().setRefreshEnabled(false);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemLongClickListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
	}
	
	private void initData() {
		title_text.setText("离线设备录入列表");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_add);
		title_righttwo_btn.setOnClickListener(this);
		
		getQROfflineDotMachineData();
		
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	public void getQROfflineDotMachineData() {
		
		mALLDataList = KingTellerDbUtils.getCacheDataAll(this, QROfflineDotMachine.class);
		Lists = new ArrayList<OfflineDotMachineBean>();
		mALLIdLists = new ArrayList<Integer>();
		
		if(mALLDataList.size() > 0){
			for (Object data :  mALLDataList) {
				QROfflineDotMachine mOfflineDotMachineData = (QROfflineDotMachine) data;
				Lists.add(new OfflineDotMachineBean(
						mOfflineDotMachineData.getOfflinedotMachineId(),
						KingTellerJsonUtils.getPersons(mOfflineDotMachineData.getOfflinedotMachineData(), QRDotMachineBean.class), 
						mOfflineDotMachineData.getOfflinedotMachineOtherData(),
						mOfflineDotMachineData.getOfflinedotMachineTime()));
				
				mALLIdLists.add(Integer.parseInt(mOfflineDotMachineData.getOfflinedotMachineId()));
			}
		}
		adpater.setListsdotmachine_offline(Lists);
		getListviewObj().setStatus(LoadingEnum.LISTSHOW);
	}
	
	
	@Override  
	public void onRestart() {  
        super.onRestart();  
        getQROfflineDotMachineData();
    }  
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//修改缓存
		OfflineDotMachineBean OfflineDotMachineBean = (OfflineDotMachineBean) parent.getAdapter().getItem(position);
		
		String [] otherDataList = OfflineDotMachineBean.getCacheOtherData().split(","); //机器id,机器编号
		
		Intent intent = new Intent(mContext, QROfflineDotMachineCargoScanActivity.class);
		intent.putExtra("OfflineDotMachineId", Integer.parseInt(OfflineDotMachineBean.getCacheDataId())); 
		intent.putExtra("mJqid", otherDataList[0]);
		intent.putExtra("mJqbh", otherDataList[1]);
		
		mContext.startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
		
		final OfflineDotMachineBean data = (OfflineDotMachineBean) parent.getAdapter().getItem(position);
		
		final NormalDialog dialog_sblr_sc = new NormalDialog(mContext);
    	KingTellerPromptDialogUtils.showSaveTwoPromptDialog(dialog_sblr_sc, "是否确定删除该数据？", "取消", "删除",
				new OnBtnClickL() {
					@Override
					public void onBtnClick() {
						dialog_sblr_sc.dismiss();
					}
                }, new OnBtnClickL() {
					@Override
					public void onBtnClick() {
						dialog_sblr_sc.dismiss();
						
						Lists.remove(position-1);
                    	adpater.setListsdotmachine_offline(Lists);
                    	
                    	KingTellerDbUtils.deleteOfflineDotMachineToDataBase(mContext, 
                    			data.getCacheDataId());
                    	
        				T.showShort(mContext, "删除成功!");
					}
                });
    	
		return true;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				finish();
				break;
			case R.id.layout_main_righttwo_btn: //新建缓存
				
				Intent intent = new Intent(mContext, QROfflineDotMachineCargoScanActivity.class);
				if(mALLIdLists.size() == 0){
					intent.putExtra("OfflineDotMachineId", 0);
				}else{
					Collections.sort(mALLIdLists);
					int id = mALLIdLists.get(mALLIdLists.size() - 1) + 1;
					intent.putExtra("OfflineDotMachineId", id);
				}
				intent.putExtra("mJqid", "0");
				intent.putExtra("mJqbh", "0");
				
				mContext.startActivity(intent);
				break;
			default:
				break;
		}
		
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
