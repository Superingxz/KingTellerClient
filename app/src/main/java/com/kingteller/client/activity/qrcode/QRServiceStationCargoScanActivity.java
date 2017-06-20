package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.kingteller.client.adapter.QRServiceStationExpandableAdapter;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.bean.qrcode.QRPreservationDataEwmListBean;
import com.kingteller.client.bean.qrcode.QRServiceCheckBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.modules.zxing.activity.CaptureActivity;

/**
 * 出入库物料登记
 * @author Administrator
 */
public class QRServiceStationCargoScanActivity extends Activity implements OnClickListener{ 
	
	private TextView mDJ_djdh, mDJ_djck, mDj_djry, mDj_djsj, mDJ_lczt, mDJ_ckxz, mDJ_rkxz;
	private Button mCancel, mPreservation, mOpenCameRKBtn, mOpenCameCKBtn;
	private ExpandableListView mExpandableListView;
	private Gson gson;
	private String mType;
	private int mOpenCameType;
	private QRServiceStationExpandableAdapter mQRServiceAdapter;
	private List<QRCargoScanBean> mGetServiceDataList = new ArrayList<QRCargoScanBean>();
	private List<QRServiceCheckBean> mServiceGroupList = new ArrayList<QRServiceCheckBean>();
	private List<List<QRCargoScanBean>> mServiceChildList = new ArrayList<List<QRCargoScanBean>>();
	private List<QRPreservationDataEwmListBean> mPreservationEwmList;
	private QRServiceCheckBean mServiceCheckBean;
	private Boolean isError;//是否出错
	private String mEwm_Number;
	
	private Boolean isBaoCun;//是否保存
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_qrcode_service_cargoscan);
		KingTellerApplication.addActivity(this);
		
		mContext = QRServiceStationCargoScanActivity.this;
		isError = false;
		isBaoCun = false;
		gson = new Gson();
		getActivityData();
		initUI();
		initData();
	}
	
	private void getActivityData(){
		mType = (String) getIntent().getSerializableExtra("Type"); 
		mServiceCheckBean = (QRServiceCheckBean) getIntent().getSerializableExtra("ServiceCheckBean"); 
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_text.setText("出入库扫描");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		mCancel = (Button) findViewById(R.id.ewm_cancel);
		mPreservation = (Button) findViewById(R.id.ewm_preservation);
		mOpenCameRKBtn = (Button) findViewById(R.id.ewm_opencamerk);
		mOpenCameCKBtn = (Button) findViewById(R.id.ewm_opencameck);
		
		mDJ_djdh = (TextView) findViewById(R.id.dj_djdh);
		mDJ_djck = (TextView) findViewById(R.id.dj_djcw);
		mDj_djry = (TextView) findViewById(R.id.dj_djry);
		mDj_djsj = (TextView) findViewById(R.id.dj_djsj);
		mDJ_lczt = (TextView) findViewById(R.id.dj_djlczt);
		mDJ_ckxz = (TextView) findViewById(R.id.dj_djckxz);
		mDJ_rkxz = (TextView) findViewById(R.id.dj_djrkxz);
		
		mExpandableListView = (ExpandableListView) findViewById(R.id.dj_expandableListView);
		
		mOpenCameRKBtn.setOnClickListener(this);
		mOpenCameCKBtn.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		mPreservation.setOnClickListener(this);
	}
	
	private void initData() {
		
		mDJ_djdh.setText("登记编号：" + mServiceCheckBean.getRegFbillNo());
		mDJ_djck.setText("登记仓库：" + mServiceCheckBean.getWrName());
		mDj_djry.setText("登记人员：" + mServiceCheckBean.getFbillerName());
		mDJ_lczt.setText(mServiceCheckBean.getAuditFlowStatusName());//流程状态
		mDj_djsj.setText("登记时间：" + mServiceCheckBean.getRegDate());
		
		if(KingTellerJudgeUtils.isEmpty(mServiceCheckBean.getRegisterPropertyName())){
			mDJ_ckxz.setText("出库性质：");
			mDJ_rkxz.setText("入库性质：");
		}else{
			String [] Crkxz = mServiceCheckBean.getRegisterPropertyName().split(","); //Crkxz[0] 出库性质 Crkxz[1]入库性质
			mDJ_ckxz.setText("出库性质：" + Crkxz[0]);
			mDJ_rkxz.setText("入库性质：" + Crkxz[1]);
		}
		
		mQRServiceAdapter = new QRServiceStationExpandableAdapter(mContext, new ArrayList<QRServiceCheckBean>(),
				new ArrayList<List<QRCargoScanBean>>());
		mExpandableListView.setAdapter(mQRServiceAdapter);

		getQRServiceStationData();
	}

      
    /**  
     * 初始化给组、子列表添加数据  
     * @param mGroup  
     * @param mChildList
     */  
    private void addInfo(QRServiceCheckBean mGroup, List<QRCargoScanBean> mChildList){  
    	//add Group
    	for(int i = 0; i<2; i++){
    		QRServiceCheckBean mAddGroup = new QRServiceCheckBean();
    		switch (i) {
				case 0:
					mAddGroup.setTypeName("登记物料出库"); 
					mAddGroup.setTotalOutQuantity(mGroup.getTotalOutQuantity());
					mAddGroup.setTotalOutBarcodeQuantity(mGroup.getTotalOutBarcodeQuantity());
					mAddGroup.setTotalOutNullBarcodeQuantity(mGroup.getTotalOutNullBarcodeQuantity());
		        	mServiceGroupList.add(mAddGroup);  
					break;
				case 1:
					mAddGroup.setTypeName("登记物料入库");
					mAddGroup.setTotalInQuantity(mGroup.getTotalInQuantity());
					mAddGroup.setTotalInBarcodeQuantity(mGroup.getTotalInBarcodeQuantity());
					mAddGroup.setTotalInNullBarcodeQuantity(mGroup.getTotalInNullBarcodeQuantity());
		        	mServiceGroupList.add(mAddGroup); 
					break;
				default:
					break;
			}
    	}
    	
    	//add Child  
    	List<QRCargoScanBean> mChildCKList = new ArrayList<QRCargoScanBean>();//出库list
    	List<QRCargoScanBean> mChildRKList = new ArrayList<QRCargoScanBean>();//入库list
    	if(mChildList.size()>0){
	    	for(int j = 0; j<mChildList.size(); j++){
	    		//0：出库 ,1：入库
	    		if(mChildList.get(j).getRegFormsType().equals("0")){
	    			mChildCKList.add(mChildList.get(j));
	    		}else if(mChildList.get(j).getRegFormsType().equals("1")){
	    			mChildRKList.add(mChildList.get(j));
	    		}
	    	}
    	}
        mServiceChildList.add(mChildCKList);  
        mServiceChildList.add(mChildRKList);  
        
        mQRServiceAdapter.setLists(mServiceGroupList, mServiceChildList);
     	mExpandableListView.expandGroup(0);
    	mExpandableListView.expandGroup(1);
    }  
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			OpenDialog("您确定要退出扫描吗？", 2);
			break;
		case R.id.ewm_opencameck:
			if(isError == true){
				T.showShort(mContext, "数据出错!");
				return;
			}
			mOpenCameType = 0;
			Intent intentck = new Intent(this, CaptureActivity.class);
			intentck.putExtra("cameType", mOpenCameType + "");
			startActivityForResult(intentck, KingTellerStaticConfig.QRCODE_NUM);
			break;
		case R.id.ewm_opencamerk:
			if(isError == true){
				T.showShort(mContext, "数据出错!");
				return;
			}
			mOpenCameType = 1;
			Intent intentrk = new Intent(this, CaptureActivity.class);
			intentrk.putExtra("cameType", mOpenCameType + "");
			startActivityForResult(intentrk, KingTellerStaticConfig.QRCODE_NUM);
			break;
		case R.id.ewm_cancel:
			OpenDialog("您确定要退出扫描吗？", 2);
			break;
		case R.id.ewm_preservation:
			getQRServiceEwmBxData();
			setQRServiceEwmData();
			break;
		default:
			break;
		}
		
	}
	
	//捕捉返回键
	public void onBackPressed() {
		OpenDialog("您确定要退出扫描吗？", 2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.QRCODE_NUM:
			if (resultCode == RESULT_OK) {
				mEwm_Number = (String) data.getSerializableExtra("ewm_num");
				if(getcontains(mEwm_Number)){//判断是否已经扫描
					OpenDialog("条码已被扫描！", 1);
				}else{
					getQRServiceEwmData(mEwm_Number, mServiceCheckBean.getRegId());	
				}
			}
			break;

		default:
			break;
		}
	}

	
	/**
	 * 1.1服务站登记物料出入库明细列表
	 */
	public void getQRServiceStationData() {

		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("regId", mServiceCheckBean.getRegId());//服务站登记物料出入库单id


		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.FwzCRKMxList),params, 
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
						Log.e("服务站登记物料出入库明细列表","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(List<QRCargoScanBean> data) {
						KingTellerProgressUtils.closeProgress();
						
						mGetServiceDataList = data;
						if(mGetServiceDataList.size() > 0 && mGetServiceDataList.get(0).getCode().equals("")){
							addInfo(mServiceCheckBean, mGetServiceDataList);
						}else{
							String msg = mGetServiceDataList.get(0).getCode();
							if(msg.contains("查询结果无数据")){
								msg = msg + "如要添加出入库物料请扫描！";
								mGetServiceDataList = new ArrayList<QRCargoScanBean>();
								addInfo(mServiceCheckBean, mGetServiceDataList);
							}else{
								isError = true;
							}
							OpenDialog(msg, 1);	
						}
					};
				});
	}
	
	/**
	 * 1.2服务站登记物料入出库二维码验证
	 */
	public void getQRServiceEwmData(String barcode, String regid){
		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("regId", regid);//出库单id
		params.put("barcode", barcode);//二维码
		
		String url = "";
		if(mOpenCameType == 0){//出库
			url = KingTellerUrlConfig.FwzCKMxEwmYz;
		}else if(mOpenCameType == 1){//入库
			url = KingTellerUrlConfig.FwzRKMxEwmYz;
		}

		fh.post(KingTellerConfigUtils.CreateUrl(this, url),params, 
				new AjaxHttpCallBack<QRCargoScanBean>(this,
						new TypeToken<QRCargoScanBean>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						// 开始http请求的时候回调
						KingTellerProgressUtils.showProgress(mContext, "正在验证中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						
						KingTellerProgressUtils.closeProgress();
						Log.e("出库单验证二维码","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(QRCargoScanBean data) {
						KingTellerProgressUtils.closeProgress();
						
						if(data.getCode().equals("")){
							
							setServiceOllData(data, mEwm_Number, mOpenCameType);
							
							//登记出入库单 成功添加后自动启动扫描界面
							new Thread(new Runnable() {  
								@Override  
								public void run() { 
								 try {
										Thread.sleep(1000);
									 	Intent nextintent = new Intent(QRServiceStationCargoScanActivity.this, 
									 				CaptureActivity.class);
									 	nextintent.putExtra("cameType", mOpenCameType + "");
										startActivityForResult(nextintent, KingTellerStaticConfig.QRCODE_NUM);
									} catch (InterruptedException e) {
										e.printStackTrace();
									} 
								}
							}).start(); 
							
						}else{
							OpenDialog(data.getCode() + " 请重新扫描！", 1);	
						}
					};
				});
	}
	
	/**
	 * 1.3扫描后结果处理
	 * @param data
	 * @param ewm
	 * @param type 0出库扫描 1入库扫描
	 */
	public void setServiceOllData (QRCargoScanBean data,String ewm, int type){
			switch (type) {
				case 0:
					int SlCkInt = Integer.valueOf(mServiceGroupList.get(0).getTotalOutQuantity()).intValue(); //获取数量
					int TmsCkInt = Integer.valueOf(mServiceGroupList.get(0).getTotalOutBarcodeQuantity()).intValue(); //获取条码数
					SlCkInt ++;
					TmsCkInt ++;
					
					mServiceGroupList.get(0).setTotalOutQuantity(Integer.toString(SlCkInt));
					mServiceGroupList.get(0).setTotalOutBarcodeQuantity(Integer.toString(TmsCkInt));
					
					data.setRegFormsType("0");
					data.setWlBarCode(ewm);
					data.setIsNewadd("1");
					mServiceChildList.get(0).add(0, data);
					
					mQRServiceAdapter.setLists(mServiceGroupList, mServiceChildList);
					mExpandableListView.expandGroup(0);
					break;
				case 1:
					int SlRkInt = Integer.valueOf(mServiceGroupList.get(1).getTotalInQuantity()).intValue(); //获取数量
					int TmsRkInt = Integer.valueOf(mServiceGroupList.get(1).getTotalInBarcodeQuantity()).intValue(); //获取条码数
					SlRkInt ++;
					TmsRkInt ++;
					
					mServiceGroupList.get(1).setTotalInQuantity(Integer.toString(SlRkInt));
					mServiceGroupList.get(1).setTotalInBarcodeQuantity(Integer.toString(TmsRkInt));
					
					data.setRegFormsType("1");
					data.setWlBarCode(ewm);
					data.setIsNewadd("1");
					mServiceChildList.get(1).add(0, data);
					
					mQRServiceAdapter.setLists(mServiceGroupList, mServiceChildList);
					mExpandableListView.expandGroup(1);
					break;
				default:
					break;
			}
	}
	
	
	/**
	 * 1.4.1获取所保存的二维码
	 * @param data
	 * @param barcode
	 */
	public void getQRServiceEwmBxData(){
		mPreservationEwmList = new ArrayList<QRPreservationDataEwmListBean>(); 
		List<QRCargoScanBean> bclist = new ArrayList<QRCargoScanBean>(); 
		bclist.addAll(mServiceChildList.get(0));
		bclist.addAll(mServiceChildList.get(1));
		
		for(int i = 0; i<bclist.size(); i++){
			QRPreservationDataEwmListBean TiaoMa = new QRPreservationDataEwmListBean();
			TiaoMa.setRegFormsType(bclist.get(i).getRegFormsType());
			TiaoMa.setBarcode(bclist.get(i).getWlBarCode());
			
			if("1".equals(bclist.get(i).getIsNewadd())){
				TiaoMa.setIsNewadd(bclist.get(i).getIsNewadd());
			}else{
				TiaoMa.setIsNewadd("0");
			}
			
			mPreservationEwmList.add(TiaoMa);
		}
		
	}
	
	/**
	 * 1.4.2保存服务站物料登记出入库单
	 */
	public void setQRServiceEwmData(){
		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		if(mPreservationEwmList.size() <= 0 || getBaoCunCount() <=0){
			OpenDialog("没有保存的服务站物料条码，请重新扫描！", 1);	
			return;
		}
		

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("regId", mServiceCheckBean.getRegId());
		params.put("recDeliversRegFormList", gson.toJson(mPreservationEwmList));

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.FwzCRKBC),params, 
				new AjaxHttpCallBack<QRServiceCheckBean>(this,
						new TypeToken<QRServiceCheckBean>() {
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
						Log.e("保存出库单","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(QRServiceCheckBean data) {
						KingTellerProgressUtils.closeProgress();
						
						if(data.getCode().equals("")){
							OpenDialog("保存成功!", 1);	
							isBaoCun = true;
							
							//重设数据
							mServiceCheckBean.setTotalOutQuantity(mServiceGroupList.get(0).getTotalOutQuantity());
							mServiceCheckBean.setTotalOutBarcodeQuantity(mServiceGroupList.get(0).getTotalOutBarcodeQuantity());
							mServiceCheckBean.setTotalInQuantity(mServiceGroupList.get(1).getTotalInQuantity());
							mServiceCheckBean.setTotalInBarcodeQuantity(mServiceGroupList.get(1).getTotalInBarcodeQuantity());
							
							mGetServiceDataList = new ArrayList<QRCargoScanBean>();
							mServiceGroupList = new ArrayList<QRServiceCheckBean>();
							mServiceChildList = new ArrayList<List<QRCargoScanBean>>();
							
							getQRServiceStationData();//重新获取明细
						}else{
							OpenDialog("保存失败：" + data.getCode(), 1);	
						}
					};
				});
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
							ComeBackData();
							finish();
						}
                    });
		}
	}
	
	/**
	 * 获取当前新增保存数量
	 * @return
	 */
	public int getBaoCunCount(){
		int cn = 0;
		for(int i = 0; i<mPreservationEwmList.size(); i++){
			if("1".equals(mPreservationEwmList.get(i).getIsNewadd())){
				cn ++;
			}
		}
		return cn;
	}
	
	/**
	 * 判断二维码是否在当前列表
	 * @param codenum
	 * @return
	 */
	private boolean getcontains(String codenum){ 
		for(int i = 0; i<mServiceChildList.size(); i++){
			for(int l = 0; l<mServiceChildList.get(i).size(); l++){
				if(mServiceChildList.get(i).get(l).getWlBarCode().equals(codenum))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 返回修改值
	 */
	public void ComeBackData(){
		if(isBaoCun){
			Intent intent = new Intent();
			intent.putExtra("mDJ_ck_sl", mServiceCheckBean.getTotalOutQuantity());
			intent.putExtra("mDJ_ck_tms", mServiceCheckBean.getTotalOutBarcodeQuantity());
			intent.putExtra("mDJ_rk_sl", mServiceCheckBean.getTotalInQuantity());
			intent.putExtra("mDJ_rk_tms", mServiceCheckBean.getTotalInBarcodeQuantity());
			setResult(RESULT_OK, intent);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}

}
