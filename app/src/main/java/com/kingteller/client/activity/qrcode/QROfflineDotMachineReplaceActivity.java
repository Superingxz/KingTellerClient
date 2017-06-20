package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.QRDeliveryReceiptAdapter;
import com.kingteller.client.bean.db.QROfflineDotMachine;
import com.kingteller.client.bean.db.QROfflineDotMachineReplace;
import com.kingteller.client.bean.offlineupload.OfflineDotMachineBean;
import com.kingteller.client.bean.offlineupload.OfflineDotMachineReplaceBean;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
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

public class QROfflineDotMachineReplaceActivity extends Activity implements
	OnClickListener, OnItemClickListener, OnItemLongClickListener, IXListViewListener{
	
	private QRDeliveryReceiptAdapter adpater;
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	private Context mContext;
	
	private List<OfflineDotMachineReplaceBean> Lists;
	private List<Integer> mALLIdLists;
	private List<?> mALLDataList;
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_alllist_view);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		mContext = QROfflineDotMachineReplaceActivity.this;
		initUI();
		initData();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);

		adpater = new QRDeliveryReceiptAdapter(mContext, null, null, null, null, null, new ArrayList<OfflineDotMachineReplaceBean>(), 6);
		
		getListviewObj().getListview().setAdapter(adpater);
		getListviewObj().getListview().setRefreshEnabled(false);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemLongClickListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
	}
	
	private void initData() {
		title_text.setText("离线部件更换扫描列表");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_add);
		title_righttwo_btn.setOnClickListener(this);
		
		getQROfflineDotMachineReplaceData();
		
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	public void getQROfflineDotMachineReplaceData() {
		
		mALLDataList = KingTellerDbUtils.getCacheDataAll(this, QROfflineDotMachineReplace.class);
		Lists = new ArrayList<OfflineDotMachineReplaceBean>();
		mALLIdLists = new ArrayList<Integer>();
		
		if(mALLDataList.size() > 0){
			for (Object data :  mALLDataList) {
				QROfflineDotMachineReplace mOfflineDotMachineReplaceData = (QROfflineDotMachineReplace) data;
				Lists.add(new OfflineDotMachineReplaceBean(
						mOfflineDotMachineReplaceData.getOfflinedotMachineReplaceId(),
						KingTellerJsonUtils.getPersons(mOfflineDotMachineReplaceData.getOfflinedotMachineReplaceData(), QRCargoScanBean.class),
						mOfflineDotMachineReplaceData.getOfflinedotMachineReplaceOtherData(),
						mOfflineDotMachineReplaceData.getOfflinedotMachineReplaceTime()));
				
				mALLIdLists.add(Integer.parseInt(mOfflineDotMachineReplaceData.getOfflinedotMachineReplaceId()));
			}
		}
		adpater.setListsdotmachinereplace_offline(Lists);
		getListviewObj().setStatus(LoadingEnum.LISTSHOW);
	}
	
	@Override  
	public void onRestart() {  
        super.onRestart();  
        getQROfflineDotMachineReplaceData();
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		OfflineDotMachineReplaceBean OfflineDotMachineReplaceBean = (OfflineDotMachineReplaceBean) parent.getAdapter().getItem(position);
		
		String [] otherDataList = OfflineDotMachineReplaceBean.getCacheOtherData().split(","); //机器id,机器编号,工单id,工单单号
		
		Intent intent = new Intent(mContext, QROfflineDotMachineReplaceCargoScanActivity.class);
		intent.putExtra("OfflineDotMachineReplaceId", Integer.parseInt(OfflineDotMachineReplaceBean.getCacheDataId()));
		intent.putExtra("mGdid", otherDataList[0]);
		intent.putExtra("mGddh", otherDataList[1]);
		intent.putExtra("mJqid", otherDataList[2]);
		intent.putExtra("mJqbh", otherDataList[3]);

		mContext.startActivity(intent);
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
		
		final OfflineDotMachineReplaceBean data = (OfflineDotMachineReplaceBean) parent.getAdapter().getItem(position);
		
		final NormalDialog dialog_bjgh_sc = new NormalDialog(mContext);
    	KingTellerPromptDialogUtils.showSaveTwoPromptDialog(dialog_bjgh_sc, "是否确定删除该数据？", "取消", "删除",
				new OnBtnClickL() {
					@Override
					public void onBtnClick() {
						dialog_bjgh_sc.dismiss();
					}
                }, new OnBtnClickL() {
					@Override
					public void onBtnClick() {
						dialog_bjgh_sc.dismiss();
						
						Lists.remove(position-1);
                    	adpater.setListsdotmachinereplace_offline(Lists);
                    	
                    	KingTellerDbUtils.deleteOfflineDotMachineReplaceToDataBase(mContext, 
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
				Intent intent = new Intent(mContext, QROfflineDotMachineReplaceCargoScanActivity.class);
				if(mALLIdLists.size() == 0){
					intent.putExtra("OfflineDotMachineReplaceId", 0);
				}else{
					Collections.sort(mALLIdLists);
					int id = mALLIdLists.get(mALLIdLists.size() - 1) + 1;
					intent.putExtra("OfflineDotMachineReplaceId", id);
				}
				intent.putExtra("mGdid", "0");
				intent.putExtra("mGddh", "0");
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
