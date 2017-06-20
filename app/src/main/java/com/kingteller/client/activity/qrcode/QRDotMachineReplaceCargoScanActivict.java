package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.QRDotMachineReplaceExpandableAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.db.QRDotMachineReplace;
import com.kingteller.client.bean.qrcode.QRBarCodeBean;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.modules.zxing.activity.CaptureActivity;

/**
 * 网点机器更换部件扫描
 */

public class QRDotMachineReplaceCargoScanActivict extends Activity implements OnClickListener {
	
	private Button mCancel, mPreservation, mOpenCameXwlBtn, mOpenCameJwlBtn;
	private TextView mGddh, mSsfwz, mWdsbjc, mJqbm, mWhgcs, mPdsj;
	private ExpandableListView mExpandableListView;
	private QRDotMachineReplaceExpandableAdapter mQRDotMachineReplaceAdapter;
	private List<CommonSelectData> mDotMachineRelaceGroupList = new ArrayList<CommonSelectData>();
	private List<List<QRCargoScanBean>> mDotMachineRelaceChildList = new ArrayList<List<QRCargoScanBean>>();
	private List<QRCargoScanBean> mDotMachineReplaceDataList = new ArrayList<QRCargoScanBean>();
	public QRDotMachineReplace SQldata;
	private User user;
	private Gson gson;
	private int mOpenCameType;
	private String mEwm_Number, mDot_gddh, mDot_gdid, mDot_jqid, mDot_wdsbjc, mDot_jqbm, mDot_pdsj, mDot_ssfwz;
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_qrcode_dotmachine_replace_cargoscan);
		KingTellerApplication.addActivity(this);
		
		mContext = QRDotMachineReplaceCargoScanActivict.this;
		gson = new Gson();
		
		KingTellerStaticConfig.QR_DOTMACHINEREPLACE_LISTDATA = false;
		
		getActivityData();
		initUI();
		initData();
	}
	private void getActivityData(){
		mDot_jqid = (String) getIntent().getSerializableExtra("JqId"); 
		mDot_jqbm = (String) getIntent().getSerializableExtra("JqBm"); 
		mDot_gddh = (String) getIntent().getSerializableExtra("Gddh"); 
		mDot_gdid = (String) getIntent().getSerializableExtra("Gdid"); 
		mDot_pdsj = (String) getIntent().getSerializableExtra("Pdsj"); 
		mDot_ssfwz = (String) getIntent().getSerializableExtra("SsFwz"); 
		mDot_wdsbjc = (String) getIntent().getSerializableExtra("WdMc"); 
	}
	
	private void initUI() {	
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_text.setText("部件更换扫描");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);

		mCancel = (Button) findViewById(R.id.ewm_cancel);
		mPreservation = (Button) findViewById(R.id.ewm_preservation);
		mOpenCameXwlBtn = (Button) findViewById(R.id.ewm_opencame_xwl);
		mOpenCameJwlBtn = (Button) findViewById(R.id.ewm_opencame_jwl);
		
		mGddh = (TextView) findViewById(R.id.wdjq_gh_gdh);
		mSsfwz = (TextView) findViewById(R.id.wdjq_gh_fwz);
		mWdsbjc = (TextView) findViewById(R.id.wdjq_gh_wdsbjc);
		mJqbm = (TextView) findViewById(R.id.wdjq_gh_jqbm);
		mWhgcs = (TextView) findViewById(R.id.wdjq_gh_whgcs);
		mPdsj = (TextView) findViewById(R.id.wdjq_gh_pdsj);
		
		mExpandableListView = (ExpandableListView) findViewById(R.id.wdjq_gh_expandableListView);
		

		mOpenCameXwlBtn.setOnClickListener(this);
		mOpenCameJwlBtn.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		mPreservation.setOnClickListener(this);
	}
	
	private void initData() {
		user = User.getInfo(mContext);
		
		mGddh.setText("工单单号：" + mDot_gddh);
		mSsfwz.setText("所属服务站：" + user.getOrgName());
		mWdsbjc.setText("网点设备简称：" + mDot_wdsbjc);
		mJqbm.setText("机器编码：" + mDot_jqbm);
		mWhgcs.setText("维护工程师：" + user.getName());
		mPdsj.setText("派单时间：" + mDot_pdsj);
			
		mQRDotMachineReplaceAdapter = new QRDotMachineReplaceExpandableAdapter(mContext, new ArrayList<CommonSelectData>(), new ArrayList<List<QRCargoScanBean>>(), 0);
		mExpandableListView.setAdapter(mQRDotMachineReplaceAdapter);
		
		//从数据库读取数据
		SQldata = KingTellerDbUtils.getDotMachineReplaceToDataBase(mContext, mDot_gdid);
		if (SQldata != null){
			List<QRCargoScanBean> nChildList = KingTellerJsonUtils.getPersons(SQldata.getDotMachineReplaceData(), QRCargoScanBean.class);
			addInfo(nChildList);
		}else{
			getQRDotMachineReplaceEwmData(); //网络获取
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
    		mDotMachineRelaceChildList = new ArrayList<List<QRCargoScanBean>>();
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
    	mDotMachineRelaceChildList.add(mChildJwlList);  
    	mDotMachineRelaceChildList.add(mChildXwlList);  
    	
    	//add Group
      	CommonSelectData mJwl = new CommonSelectData();
    	CommonSelectData mXwl = new CommonSelectData();
    	
    	mJwl.setText("旧物料"); 
		mJwl.setValue(Integer.toString(Jnum));
		mXwl.setText("新物料"); 
		mXwl.setValue(Integer.toString(Xnum));
    	
		mDotMachineRelaceGroupList = new ArrayList<CommonSelectData>();
    	mDotMachineRelaceGroupList.add(mJwl);  
    	mDotMachineRelaceGroupList.add(mXwl);
        
    	mQRDotMachineReplaceAdapter.setLists(mDotMachineRelaceGroupList, mDotMachineRelaceChildList, 0);
    	mExpandableListView.expandGroup(0);
    	mExpandableListView.expandGroup(1);
    }  
	

    /**
	 * 3.1扫描后结果处理
	 * @param data
	 * @param ewm
	 * @param type 2旧物料扫描   3新物料扫描
	 */
	public void setDotMachineReplaceOllData (QRCargoScanBean data, String ewm, int type){
		
		KingTellerStaticConfig.QR_DOTMACHINEREPLACE_LISTDATA = true;
		
		switch (type) {
			case 2:
				mDotMachineRelaceGroupList.get(0).setValue(Integer.toString(
						Integer.valueOf(mDotMachineRelaceGroupList.get(0).getValue()).intValue() + 1));//获取数量 + 1
				
				data.setReplaceType("0");
				data.setWlBarCode(ewm);
				data.setIsNewadd("yes");//是否新增
				mDotMachineRelaceChildList.get(0).add(0, data);

				mQRDotMachineReplaceAdapter.setLists(mDotMachineRelaceGroupList, mDotMachineRelaceChildList, 0);
				mExpandableListView.expandGroup(0);
				
				break;
			case 3:
				mDotMachineRelaceGroupList.get(1).setValue(Integer.toString(
						Integer.valueOf(mDotMachineRelaceGroupList.get(1).getValue()).intValue() + 1)); //获取数量 + 1
				
				data.setReplaceType("1");
				data.setWlBarCode(ewm);
				data.setIsNewadd("yes");//是否新增
				mDotMachineRelaceChildList.get(1).add(0, data);

				mQRDotMachineReplaceAdapter.setLists(mDotMachineRelaceGroupList, mDotMachineRelaceChildList, 0);	
		    	mExpandableListView.expandGroup(1);
			
				break;
			default:
				break;
		}
	}
	
	
	
	/**
	 * 3.2获取网点机器部件更换物料
	 */
	public void getQRDotMachineReplaceEwmData() {
		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqId", mDot_jqid);
		params.put("orderId", mDot_gdid);
	
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WdJQBjGHHQ),params, 
				new AjaxHttpCallBack<List<QRCargoScanBean>>(this,
						new TypeToken<List<QRCargoScanBean>>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						// 开始http请求的时候回调
						KingTellerProgressUtils.showProgress(mContext, "正在查询中...");
					}
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						
						KingTellerProgressUtils.closeProgress();
						Log.e("获取网点机器部件更换物料","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(List<QRCargoScanBean> data) {
						KingTellerProgressUtils.closeProgress();
						
						if(data.size() > 0 && data.get(0).getCode().equals("")){
							addInfo(data);
						}else{
							String msg = data.get(0).getCode();
							if(msg.contains("查询结果无数据")){
								msg = msg + "如要添加请扫描！";
								addInfo(new ArrayList<QRCargoScanBean>());
							}
							OpenDialog(msg, 1);	
						}
					};
				});
	}
	
	
	
	/**
	 * 3.3获取物料二维码信息
	 */
	private void getDotMachineReplaceEwmMessage(final String barcode) {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("barcode", barcode);//二维码号
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.AtmWlXinXi), params,
			new AjaxHttpCallBack<QRBarCodeBean>(this,
					new TypeToken<QRBarCodeBean>() {
					}.getType(), true) {
		
				@Override
				public void onError(int errorNo, String strMsg) {
					super.onError(errorNo, strMsg);
					
					KingTellerProgressUtils.closeProgress();
					Log.e("ATMReplaceSearchWl","onError" + errorNo + ":" + strMsg);
					T.showShort(mContext, "数据访问超时!");
				}
				
				@Override
				public void onStart() {
					KingTellerProgressUtils.showProgress(mContext, "查询二维码信息中...");
				}

				@Override
				public void onDo(QRBarCodeBean data) { 
					KingTellerProgressUtils.closeProgress();
					
					if("".equals(data.getCode())){
						Log.e("设部件更换物料信息", data.getWlName() + ": " + data.getNewCode() + ": " + data.getWlDotType());
						
						QRCargoScanBean wldata = new QRCargoScanBean();
						wldata.setNewCode(data.getNewCode());
						wldata.setWlName(data.getWlName());
						
						setDotMachineReplaceOllData(wldata, barcode, mOpenCameType);
						
						openCame(mOpenCameType);

					}else{
						T.showShort(mContext, data.getCode());
					}
				};
			});
	}
	
	

	
	
	/**
	 * 3.4保存网点机器部件更换物料
	 */
	private void setQRDotMachineReplaceEwmData(){

		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		//获取保存列表与新增数量
		mDotMachineReplaceDataList = new ArrayList<QRCargoScanBean>();
		mDotMachineReplaceDataList.addAll(mDotMachineRelaceChildList.get(0));
		mDotMachineReplaceDataList.addAll(mDotMachineRelaceChildList.get(1));
		

		if(getcontainsnum() <= 0 && KingTellerStaticConfig.QR_DOTMACHINEREPLACE_LISTDATA == false){//保存必须为1个以上
			OpenDialog("没有所更新的物料, 请扫描！", 1);
			return;
		}
		
		
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqId", mDot_jqid);
		params.put("orderId", mDot_gdid);
		params.put("wlList", gson.toJson(mDotMachineReplaceDataList));
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WdJQBjGHBC),params, 
				new AjaxHttpCallBack<QRCargoScanBean>(this,
						new TypeToken<QRCargoScanBean>() {
						}.getType(), true) {
			
					@Override
					public void onStart() {
						// 开始http请求的时候回调
						KingTellerProgressUtils.showProgress(mContext, "正在保存中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						// TODO Auto-generated method stub
						super.onError(errorNo, strMsg);
					
						KingTellerProgressUtils.closeProgress();
						Log.e("保存机器更换物料","onError" + errorNo + ":" + strMsg);	
						T.showShort(mContext, "数据访问超时!");
						
						setBaoCunData(0);
					}
					
					@Override
					public void onDo(QRCargoScanBean data) {
						KingTellerProgressUtils.closeProgress();
						
						if(data.getCode().equals("")){
							OpenDialog("保存成功！",1);

							//删除暂存数据
							if(SQldata != null){
								KingTellerDbUtils.deleteDotMachineReplaceToDataBase(mContext, mDot_jqid + "," + mDot_gdid);
							}
							
							for(int j = 0; j<mDotMachineReplaceDataList.size(); j++){
								mDotMachineReplaceDataList.get(j).setIsNewadd(null);
							}
							
							KingTellerStaticConfig.QR_DOTMACHINEREPLACE_LISTDATA = false;
									
							addInfo(mDotMachineReplaceDataList);
	
						}else{
							OpenDialog("保存失败："+data.getCode(),1);	
						}
					};
				});
	}
	
    
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			isOpenDialog();
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
			isOpenDialog();
			break;
		case R.id.ewm_preservation:
			setQRDotMachineReplaceEwmData();
			break;
		default:
			break;
		}
		
	}
	

	//捕捉返回键
	public void onBackPressed() {
		isOpenDialog();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.QRCODE_NUM:
			if (resultCode == RESULT_OK) {
				mEwm_Number = (String) data.getSerializableExtra("ewm_num");
				if(getcontains(mEwm_Number)){//判断是否已经扫描
					OpenDialog("条码已被扫描！", 1);
				}else{
					getDotMachineReplaceEwmMessage(mEwm_Number);
				}
			}
			break;

		default:
			break;
		}
	}
	
	
	

	

	/**
	 * 判断二维码是否在当前列表
	 * @param codenum
	 * @return
	 */
	private boolean getcontains(String codenum){ 
		for(int i = 0; i<mDotMachineRelaceChildList.size(); i++){
			for(int l = 0; l<mDotMachineRelaceChildList.get(i).size(); l++){
				if(mDotMachineRelaceChildList.get(i).get(l).getWlBarCode().equals(codenum))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断当前列表有多少个新增物料
	 * @param codenum
	 * @return
	 */
	private int getcontainsnum(){ 
		int n = 0;
		for(int i = 0; i<mDotMachineRelaceChildList.size(); i++){
			for(int l = 0; l<mDotMachineRelaceChildList.get(i).size(); l++){
				if("yes".equals(mDotMachineRelaceChildList.get(i).get(l).getIsNewadd()))
					n ++;
			}
		}
		return n;
	}
	
	/**
	 * 判断当前列表有多少个物料
	 * @param codenum
	 * @return
	 */
	private int getollnum(){ 
		int m = 0;
		for(int i = 0; i<mDotMachineRelaceChildList.size(); i++){
			for(int l = 0; l<mDotMachineRelaceChildList.get(i).size(); l++){
				m ++;
			}
		}
		return m;
	}
	
	/**
	 * 判断保存列表是否空而选择提示
	 */
	public void isOpenDialog(){
		if(getcontainsnum() != 0 || KingTellerStaticConfig.QR_DOTMACHINEREPLACE_LISTDATA){
			if(getollnum() == 0){
				OpenDialog("您确定要退出扫描吗？", 2);
			}else{
				OpenDialog("当前数据已更改且未保存, 是否离线保存？", 3);
			}
		}else{
			OpenDialog("您确定要退出扫描吗？", 2);
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
			KingTellerPromptDialogUtils.showSaveTwoPromptDialog(dialog, msg, "不保存", "保存",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
							finish();
						}
                    }, new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
							setBaoCunData(0);
							finish();
						}
                    });
		}
	}
	
	/**
	 * 保存数据到数据库
	 * @param isSusser
	 */
	public void setBaoCunData(int isSusser){
		List<QRCargoScanBean> BaoCunList= new ArrayList<QRCargoScanBean>();
		BaoCunList.addAll(mDotMachineRelaceChildList.get(0));
		BaoCunList.addAll(mDotMachineRelaceChildList.get(1));
		//保存数据库
		KingTellerDbUtils.saveDotMachineReplaceToDataBase(
				mContext, 
				mDot_gdid, //工单id
				"dotmachinereplace",
				gson.toJson(BaoCunList),
				mDot_gdid + "," + mDot_gddh + "," + mDot_jqid + "," + mDot_jqbm, //工单id,工单单号,机器id,机器编号
				KingTellerTimeUtils.getNowTime(), 
				isSusser);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
	
}
