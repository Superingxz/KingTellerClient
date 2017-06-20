package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.QRDotMachineReplaceExpandableAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.db.QROfflineDotMachine;
import com.kingteller.client.bean.db.QROfflineDotMachineReplace;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.bean.qrcode.QRDotMachineBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.modules.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class QROfflineDotMachineReplaceCargoScanActivity extends Activity implements OnClickListener{
	
	private Context mContext;
	private TextView title_text, mSsfwz;
	private Button title_left_btn;

	private Gson gson;
	private User user;
	private Button mCancel, mPreservation, mOpenCameXwlBtn, mOpenCameJwlBtn;
	private ExpandableListView mExpandableListView;
	private QRDotMachineReplaceExpandableAdapter mQRDotMachineReplaceAdapter;
	private int mOpenCameType, OfflineDotMachineReplaceId;
	private String mEwm_Number, mBJGN_LX_GDID, mBJGN_LX_GDDH, mBJGN_LX_JQID, mBJGN_LX_JQBH;
	
	private List<CommonSelectData> mOfflineDotMachineRelaceGroupList = new ArrayList<CommonSelectData>();
	private List<List<QRCargoScanBean>> mOfflineDotMachineRelaceChildList = new ArrayList<List<QRCargoScanBean>>();
	private List<QRCargoScanBean> mDotMachineReplaceDataList = new ArrayList<QRCargoScanBean>();
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_qrcode_dotmachine_replace_cargoscan);
		KingTellerApplication.addActivity(this);
		
		mContext = QROfflineDotMachineReplaceCargoScanActivity.this;
		gson = new Gson();
		
		KingTellerStaticConfig.QR_DOTMACHINEREPLACE_OFFLINE_LISTDATA = false;
		
		OfflineDotMachineReplaceId = (int) getIntent().getSerializableExtra("OfflineDotMachineReplaceId");
		mBJGN_LX_GDID = (String) getIntent().getSerializableExtra("mGdid"); 
		mBJGN_LX_GDDH = (String) getIntent().getSerializableExtra("mGddh"); 
		mBJGN_LX_JQID = (String) getIntent().getSerializableExtra("mJqid"); 
		mBJGN_LX_JQBH = (String) getIntent().getSerializableExtra("mJqbh"); 
		
		initUI();
		initData();
	}

	private void initUI() {	
		findViewById(R.id.bjghsm_rl_1).setVisibility(View.GONE);
		findViewById(R.id.bjghsm_rl_2).setVisibility(View.GONE);
		findViewById(R.id.bjghsm_rl_4).setVisibility(View.GONE);
		findViewById(R.id.bjghsm_rl_5).setVisibility(View.GONE);
		
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_text.setText("离线部件更换扫描");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);

		mCancel = (Button) findViewById(R.id.ewm_cancel);
		
		mPreservation = (Button) findViewById(R.id.ewm_preservation);
		mPreservation.setText("缓存");
		
		mOpenCameXwlBtn = (Button) findViewById(R.id.ewm_opencame_xwl);
		mOpenCameJwlBtn = (Button) findViewById(R.id.ewm_opencame_jwl);
		
		mSsfwz = (TextView) findViewById(R.id.wdjq_gh_fwz);

		mExpandableListView = (ExpandableListView) findViewById(R.id.wdjq_gh_expandableListView);
		
		mOpenCameXwlBtn.setOnClickListener(this);
		mOpenCameJwlBtn.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		mPreservation.setOnClickListener(this);
	}
	
	private void initData() {
		user = User.getInfo(mContext);
		mSsfwz.setText("所属服务站：" + user.getOrgName());
		
		mQRDotMachineReplaceAdapter = new QRDotMachineReplaceExpandableAdapter(mContext, new ArrayList<CommonSelectData>(), new ArrayList<List<QRCargoScanBean>>(), 2);
		mExpandableListView.setAdapter(mQRDotMachineReplaceAdapter);
		
		QROfflineDotMachineReplace data = KingTellerDbUtils.getOfflineDotMachineReplaceToDataBase(mContext,  String.valueOf(OfflineDotMachineReplaceId));
		if(data == null){
			addInfo(new ArrayList<QRCargoScanBean>());
		}else{
			List<QRCargoScanBean> nChildList = KingTellerJsonUtils.getPersons(data.getOfflinedotMachineReplaceData(), QRCargoScanBean.class);
			addInfo(nChildList);
		}

	}
	
	/**  
     * 初始化给组、子列表添加数据  
     * @param mGroup  
     * @param mChildList
     */  
    private void addInfo(List<QRCargoScanBean> mChildList){
    	int Jnum = 0;
    	int Xnum = 0;

    	//add Child  
    	List<QRCargoScanBean> mChildJwlList = new ArrayList<QRCargoScanBean>();
    	List<QRCargoScanBean> mChildXwlList = new ArrayList<QRCargoScanBean>();
    	if(mChildList.size()>0){
    		mOfflineDotMachineRelaceChildList = new ArrayList<List<QRCargoScanBean>>();
	    	for(int j = 0; j<mChildList.size(); j++){
	    		//0：旧物料 ,1：新物料
	    		if(mChildList.get(j).getReplaceType().equals("0")){
	    			mChildJwlList.add(mChildList.get(j));
	    			Jnum ++;
	    		}else if(mChildList.get(j).getReplaceType().equals("1")){
	    			mChildXwlList.add(mChildList.get(j));
	    			Xnum ++;
	    		}
	    	}
    	}
    	mOfflineDotMachineRelaceChildList.add(mChildJwlList);  
    	mOfflineDotMachineRelaceChildList.add(mChildXwlList);  
    	
    	//add Group
      	CommonSelectData mJwl = new CommonSelectData();
    	CommonSelectData mXwl = new CommonSelectData();
    	
    	mJwl.setText("旧物料"); 
		mJwl.setValue(Integer.toString(Jnum));
		mXwl.setText("新物料"); 
		mXwl.setValue(Integer.toString(Xnum));
    	
		mOfflineDotMachineRelaceGroupList = new ArrayList<CommonSelectData>();
		mOfflineDotMachineRelaceGroupList.add(mJwl);  
		mOfflineDotMachineRelaceGroupList.add(mXwl);
        
    	mQRDotMachineReplaceAdapter.setLists(mOfflineDotMachineRelaceGroupList, mOfflineDotMachineRelaceChildList, 2);
    	mExpandableListView.expandGroup(0);
    	mExpandableListView.expandGroup(1);
    }  
	
    /**
	 * 3.1扫描后结果处理
	 * @param ewm
	 * @param type 2旧物料扫描   3新物料扫描
	 */
	public void setDotMachineReplaceOllData (String ewm, int type){
		
		KingTellerStaticConfig.QR_DOTMACHINEREPLACE_OFFLINE_LISTDATA = true;
		
		QRCargoScanBean data = new QRCargoScanBean();
		switch (type) {
			case 2:
				mOfflineDotMachineRelaceGroupList.get(0).setValue(Integer.toString(
						Integer.valueOf(mOfflineDotMachineRelaceGroupList.get(0).getValue()).intValue() + 1));//获取数量 + 1
				
				data.setReplaceType("0");
				data.setWlBarCode(ewm);
				mOfflineDotMachineRelaceChildList.get(0).add(0, data);
				
				mQRDotMachineReplaceAdapter.setLists(mOfflineDotMachineRelaceGroupList, mOfflineDotMachineRelaceChildList, 2);
				mExpandableListView.expandGroup(0);
				
				break;
			case 3:
				mOfflineDotMachineRelaceGroupList.get(1).setValue(Integer.toString(
						Integer.valueOf(mOfflineDotMachineRelaceGroupList.get(1).getValue()).intValue() + 1)); //获取数量 + 1
				
				data.setReplaceType("1");
				data.setWlBarCode(ewm);
				mOfflineDotMachineRelaceChildList.get(1).add(0, data);
				
				mQRDotMachineReplaceAdapter.setLists(mOfflineDotMachineRelaceGroupList, mOfflineDotMachineRelaceChildList, 2);	
		    	mExpandableListView.expandGroup(1);
			
				break;
			default:
				break;
		}
	}
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.QRCODE_NUM:
			if (resultCode == RESULT_OK) {
				mEwm_Number = (String) data.getSerializableExtra("ewm_num");
				if(getcontains(mEwm_Number)){//判断是否已经扫描
					OpenDialog("条码已被扫描!", 1);
				}else{
					setDotMachineReplaceOllData(mEwm_Number, mOpenCameType);
					
					openCame(mOpenCameType);
				}
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 打开扫描界面
	 * @param munCame
	 */
	public void openCame(final int munCame){
		new Thread(new Runnable() {  
			@Override  
			public void run() {
				try {
					Thread.sleep(1500);
					Intent intentck = new Intent(mContext, CaptureActivity.class);
					intentck.putExtra("cameType", munCame + "");
					startActivityForResult(intentck, KingTellerStaticConfig.QRCODE_NUM);
		
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}).start(); 
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			
			if(KingTellerStaticConfig.QR_DOTMACHINEREPLACE_OFFLINE_LISTDATA && getIsDataNum(mOfflineDotMachineRelaceChildList) > 0){
				OpenDialog("缓存数据已更改, 是否继续退出？", 2);
			}else{
				finish();
			}
			
			break;
		case R.id.ewm_opencame_jwl:
			mOpenCameType = 2;
			Intent intentck = new Intent(mContext, CaptureActivity.class);
			intentck.putExtra("cameType", mOpenCameType + "");
			startActivityForResult(intentck, KingTellerStaticConfig.QRCODE_NUM);
			break;
		case R.id.ewm_opencame_xwl:
			mOpenCameType = 3;
			Intent intentrk = new Intent(mContext, CaptureActivity.class);
			intentrk.putExtra("cameType", mOpenCameType + "");
			startActivityForResult(intentrk, KingTellerStaticConfig.QRCODE_NUM);
			break;
		case R.id.ewm_cancel:
			
			if(KingTellerStaticConfig.QR_DOTMACHINEREPLACE_OFFLINE_LISTDATA && getIsDataNum(mOfflineDotMachineRelaceChildList) > 0){
				OpenDialog("缓存数据已更改, 是否继续退出？", 2);
			}else{
				finish();
			}
			
			break;
		case R.id.ewm_preservation:
			setBaoCunOfflineDotMachineData();
			break;
		default:
			break;
		}
		
	}
	
	//捕捉返回键
	public void onBackPressed() {
		if(KingTellerStaticConfig.QR_DOTMACHINEREPLACE_OFFLINE_LISTDATA && getIsDataNum(mOfflineDotMachineRelaceChildList) > 0){
			OpenDialog("缓存数据已更改, 是否继续退出？", 2);
		}else{
			finish();
		}
	}
	
	/**
	 * 保存数据到数据库
	 * @param isSusser
	 */
	public void setBaoCunOfflineDotMachineData(){
		
		//获取保存列表与新增数量
		mDotMachineReplaceDataList = new ArrayList<QRCargoScanBean>();
		mDotMachineReplaceDataList.addAll(mOfflineDotMachineRelaceChildList.get(0));
		mDotMachineReplaceDataList.addAll(mOfflineDotMachineRelaceChildList.get(1));
		
		if(mDotMachineReplaceDataList.size() == 0){//保存必须为1个以上
			OpenDialog("没有二维码信息, 请扫描!", 1);
			return;
		}
		
		QROfflineDotMachineReplace data = KingTellerDbUtils.getOfflineDotMachineReplaceToDataBase(mContext, String.valueOf(OfflineDotMachineReplaceId));
		if (data == null){
			//保存数据库
			KingTellerDbUtils.saveOfflineDotMachineReplaceToDataBase(
					mContext, 
					String.valueOf(OfflineDotMachineReplaceId), 
					"dotmachinereplace_offline",
					gson.toJson(mDotMachineReplaceDataList), 
					mBJGN_LX_GDID + "," + mBJGN_LX_GDDH  + "," +  mBJGN_LX_JQID + "," + mBJGN_LX_JQBH, //工单id,工单单号,机器id,机器编号
					KingTellerTimeUtils.getNowTime(),
					0);
			
			T.showShort(mContext, "保存成功!");
		}else{
			OpenDialog("已有缓存数据, 是否更新数据？", 3);
		}
		
		KingTellerStaticConfig.QR_DOTMACHINEREPLACE_OFFLINE_LISTDATA = false;
	}
	
	
	//打开提示dialog
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
							KingTellerDbUtils.saveOfflineDotMachineReplaceToDataBase(
	            					mContext, 
	            					String.valueOf(OfflineDotMachineReplaceId),
	            					"dotmachinereplace_offline",
	            					gson.toJson(mDotMachineReplaceDataList), 
	            					mBJGN_LX_GDID + "," + mBJGN_LX_GDDH  + "," +  mBJGN_LX_JQID + "," + mBJGN_LX_JQBH, //工单id,工单单号,机器id,机器编号
	            					KingTellerTimeUtils.getNowTime(),
	            					0);
	            			T.showShort(mContext, "更新成功!");
						}
                    });
		}
	}
	
	/**
	 * 判断二维码是否在当前列表
	 * @param codenum
	 * @return
	 */
	private boolean getcontains(String codenum){ 
		for(int i = 0; i<mOfflineDotMachineRelaceChildList.size(); i++){
			for(int l = 0; l<mOfflineDotMachineRelaceChildList.get(i).size(); l++){
				if(mOfflineDotMachineRelaceChildList.get(i).get(l).getWlBarCode().equals(codenum))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取当前列表中二维码总数
	 * @param codenum
	 * @return
	 */
	private int getIsDataNum(List<List<QRCargoScanBean>> data){ 
		int num = 0;
		for(int i = 0; i<data.size(); i++){
			for(int l = 0; l<data.get(i).size(); l++){
				num ++;
			}
		}
		return num;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
