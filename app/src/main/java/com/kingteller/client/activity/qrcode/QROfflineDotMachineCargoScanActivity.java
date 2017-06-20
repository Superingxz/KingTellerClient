package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.QRCodeInputAdapter;
import com.kingteller.client.adapter.QROfflineDotMachineAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.db.QRDotMachine;
import com.kingteller.client.bean.db.QROfflineDotMachine;
import com.kingteller.client.bean.db.QROfflineDotMachineReplace;
import com.kingteller.client.bean.qrcode.QRBarCodeBean;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.bean.qrcode.QRDotMachineBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.KingTellerDb;
import com.kingteller.modules.zxing.activity.CaptureActivity;

/**
 * 离线设备录入
 * @author Administrator
 */
public class QROfflineDotMachineCargoScanActivity extends Activity implements OnClickListener{
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn;
	private Button mOk, mCancel, mEwm_Opencame;
	private Gson gson;
	private User user;
	private TextView mSsfwz;
	private String mEwm_Number, mSBLR_LX_JQID, mSBLR_LX_JQBH;
	private int OfflineDotMachineId;
	private ListView mDotMachineListView;
	private QROfflineDotMachineAdapter listadapter; 
	private List<QRDotMachineBean> mBaoCunList = new ArrayList<QRDotMachineBean>();
	 
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_qrcode_dotmachine_cargoscan);
		KingTellerApplication.addActivity(this);
		
		mContext = QROfflineDotMachineCargoScanActivity.this;
		gson = new Gson();
		
		KingTellerStaticConfig.QR_DOTMACHINE_OFFLINE_LISTDATA = false;
		
		OfflineDotMachineId = (int) getIntent().getSerializableExtra("OfflineDotMachineId"); 
		mSBLR_LX_JQID = (String) getIntent().getSerializableExtra("mJqid");
		mSBLR_LX_JQBH = (String) getIntent().getSerializableExtra("mJqbh");
		
		
		initUI();
		initData();
	}
	
	private void initUI() {	
		findViewById(R.id.wdjq_hand_2).setVisibility(View.GONE);
		
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);

		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_text.setText("离线设备录入");
		
		mSsfwz = (TextView) findViewById(R.id.wdjq_ssfwz);
		
		mOk = (Button) findViewById(R.id.ewm_preservation);
		mOk.setText("缓存");
		
		mCancel = (Button) findViewById(R.id.ewm_cancel);
		mEwm_Opencame = (Button) findViewById(R.id.ewm_opencame);
		
		mDotMachineListView = (ListView) findViewById(R.id.wdjq_listview);
		
		listadapter = new QROfflineDotMachineAdapter(mContext, new ArrayList<QRDotMachineBean>());
		mDotMachineListView.setAdapter(listadapter);
		
		mEwm_Opencame.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		mOk.setOnClickListener(this);
		KingTellerConfigUtils.hideInputMethod(this);
	}

	private void initData() {	
		user = User.getInfo(mContext);
		mSsfwz.setText("所属服务站：" + user.getOrgName());
		
		QROfflineDotMachine data = KingTellerDbUtils.getOfflineDotMachineToDataBase(mContext,  String.valueOf(OfflineDotMachineId));
		if(data == null){
			mBaoCunList = new ArrayList<QRDotMachineBean>();
			listadapter.setLists(mBaoCunList);
		}else{
			mBaoCunList = KingTellerJsonUtils.getPersons(data.getOfflinedotMachineData(), QRDotMachineBean.class);
			listadapter.setLists(mBaoCunList);
		}
	}
	
	@Override
	public void onClick(View v) {
			switch (v.getId()) {
			case R.id.layout_main_left_btn:
				if(KingTellerStaticConfig.QR_DOTMACHINE_OFFLINE_LISTDATA && mBaoCunList.size() > 0){
					OpenDialog("缓存数据已更改, 是否继续退出？", 2);
				}else{
					finish();
				}
				break;
			case R.id.ewm_opencame:
				Intent intent = new Intent(QROfflineDotMachineCargoScanActivity.this, CaptureActivity.class);
				startActivityForResult(intent, KingTellerStaticConfig.QRCODE_NUM);
				break;
			case R.id.ewm_preservation:
				setBaoCunOfflineDotMachineData();
				break;
			case R.id.ewm_cancel:
				if(KingTellerStaticConfig.QR_DOTMACHINE_OFFLINE_LISTDATA && mBaoCunList.size() > 0){
					OpenDialog("缓存数据已更改, 是否继续退出？", 2);
				}else{
					finish();
				}
				break;
			default:
				break;
			}
	}
	
	//捕捉返回键
	public void onBackPressed() {
		if(KingTellerStaticConfig.QR_DOTMACHINE_OFFLINE_LISTDATA && mBaoCunList.size() > 0){
			OpenDialog("缓存数据已更改,是否继续退出？", 2);
		}else{
			finish();
		}
	}
	
	// TODO 扫描返回函数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.QRCODE_NUM:
			if (resultCode == RESULT_OK) {
				mEwm_Number = (String) data.getSerializableExtra("ewm_num");
				if(getcontains(mEwm_Number)){//判断是否已经扫描
					OpenDialog("条码已被扫描！", 1);
				}else if(mEwm_Number.length() < 8 && getZJEWMcontains(mBaoCunList)){
					OpenDialog("整机条码已被扫描！", 1);
				}else{
					setEwmXinxi(mEwm_Number);
				}
			}
			break;
		default:
			break;
		}
	}
	
	
	public void setEwmXinxi(String ewm){
		QRDotMachineBean data = new QRDotMachineBean(ewm);
		mBaoCunList.add(0,data);
		listadapter.setLists(mBaoCunList);
		KingTellerStaticConfig.QR_DOTMACHINE_OFFLINE_LISTDATA = true;
	}
	
	/**
	 * 保存数据到数据库
	 * @param isSusser
	 */
	public void setBaoCunOfflineDotMachineData(){
		
		if(!getZJEWMcontains(mBaoCunList)){
			OpenDialog("请扫描整机二维码！", 1);
			return;
		}
		
		QROfflineDotMachine data = KingTellerDbUtils.getOfflineDotMachineToDataBase(mContext, String.valueOf(OfflineDotMachineId));
		if (data == null){
			//保存数据库
			KingTellerDbUtils.saveOfflineDotMachineToDataBase(
					mContext, 
					String.valueOf(OfflineDotMachineId), 
					"dotmachine_offline",
					gson.toJson(mBaoCunList), 
					mSBLR_LX_JQID + "," + mSBLR_LX_JQBH, //整机二维码, 机器id, 机器编号
					KingTellerTimeUtils.getNowTime(),
					0);
			
			T.showShort(mContext, "保存成功!");
		}else{
			OpenDialog("已有缓存数据, 是否更新数据？", 3);
		}
		KingTellerStaticConfig.QR_DOTMACHINE_OFFLINE_LISTDATA = false;
	}
	
	/**
	 * 打开提示dialog
	 * @param context
	 * @param msg
	 * @param type
	 */
	public void OpenDialog (String msg, int type){
		if(type == 1){
			
			dialog = new NormalDialog(mContext);
        	KingTellerPromptDialogUtils.showOnePromptDialog(dialog, msg,
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    });

		}else if(type == 2){
			
			dialog = new NormalDialog(mContext);
        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, msg,
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    }, new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
							finish();
						}
                    });
        	
		}else if(type == 3){
			
			dialog = new NormalDialog(mContext);
        	KingTellerPromptDialogUtils.showSaveTwoPromptDialog(dialog, msg, "取消", "更新",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    }, new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
							//保存数据库
							KingTellerDbUtils.saveOfflineDotMachineToDataBase(
	            					mContext, 
	            					String.valueOf(OfflineDotMachineId),
	            					"dotmachine_offline",
	            					gson.toJson(mBaoCunList), 
	            					mSBLR_LX_JQID + "," + mSBLR_LX_JQBH, //整机二维码, 机器id, 机器编号
	            					KingTellerTimeUtils.getNowTime(),
	            					0);
	            			T.showShort(mContext, "更新成功!");
						}
                    });
		}
	}
	
	
	/**
	 * 判断物料条码是否在当前列表
	 * @param codenum
	 * @return
	 */
	private boolean getcontains(String ewm){ 
		for(int i = 0; i<mBaoCunList.size(); i++){
			String stringArr = mBaoCunList.get(i).getWlBarCode();
			if(ewm.equals(stringArr))
			return true;
		}
		return false;
	}
	
	
	/**
	 * 判断当前列表否含有整机二维码
	 * @param codenum
	 * @return
	 */
	private boolean getZJEWMcontains(List<QRDotMachineBean> list){ 
		for(int i = 0; i<list.size(); i++){
			String stringArr = list.get(i).getWlBarCode();
			if(stringArr.length() < 8){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
