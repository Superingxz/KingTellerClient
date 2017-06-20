package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.QRCargoScanAdapter;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.bean.qrcode.QRDeliveryBean;
import com.kingteller.client.bean.qrcode.QRGuaranteeBean;
import com.kingteller.client.bean.qrcode.QRPreservationDataBean;
import com.kingteller.client.bean.qrcode.QRPreservationDataEwmListBean;
import com.kingteller.client.bean.qrcode.QRReceiptBean;
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

public class QRCargoScanActivity extends Activity implements OnClickListener{
	private TextView mFH_ckbh, mFH_fhck, mFH_shck, mFH_lczt, mFH_zdr, mFH_zdsj, mFH_djlx, mFH_wlzs, mFH_ytms,mFH_mtms,
			mSH_shbh, mSH_fhck, mSH_jsck, mSH_fhr, mSH_fhsj,mSH_wlzs, mSH_tmzs, mSH_wyztms,
			mBX_bxdh, mBX_bxcw, mBX_bxry, mBX_bxsj;
	
	private Button mOpenCameBtn, mCancel, mPreservation;
	private TextView title_text;
	private Button title_left_btn;
	private List<QRCargoScanBean> mCargoScanList = new ArrayList<QRCargoScanBean>();
	private List<QRCargoScanBean> mShowCargoScanList = new ArrayList<QRCargoScanBean>();
	private QRPreservationDataBean  mPreservationData = new QRPreservationDataBean();
	private List<QRPreservationDataEwmListBean> mPreservationEwmList = new ArrayList<QRPreservationDataEwmListBean>();
	private List<QRCargoScanBean> mGuaranteeCargoScanList = new ArrayList<QRCargoScanBean>();
	private List<String> mNewCodeLsit = new ArrayList<String>();
	private List<String> mShowNewCodeLsit = new ArrayList<String>();
	private List<String> mEwmSearchList = new ArrayList<String>();
	private ListView mCargoScanListView;
	private Context mContext;
	private QRCargoScanAdapter listadapter;
	private QRDeliveryBean mDelivertBean;
	
	private QRReceiptBean mReceiptBean;
	private QRGuaranteeBean mGuaranteeBean;
	private String mType;
	private String mEwm_Number;
	
	private Gson gson;
	private Boolean isError;//是否出错
	
	
	private int mFHwlNum = 0;//当前发货物料总数
	private int mFHYtmsNum = 0;//当前发货条码总数
	
	private int mFH_bcwlNum = 0;//保存当前发货物料总数
	private int mFH_bctmsNum = 0;//保存当前发货条码总数
	
	private Boolean isBaoCun;//是否保存
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_qrcode_cargoscan);
		KingTellerApplication.addActivity(this);
		
		mContext = QRCargoScanActivity.this;
		isError = false;
		isBaoCun = false;
		
		gson = new Gson();
		getActivityData();
		initUI();
		initData();
	}

	
	private void getActivityData(){
		mType = (String) getIntent().getSerializableExtra("Type"); 
		if(mType.equals("1")){
			mDelivertBean = (QRDeliveryBean) getIntent().getSerializableExtra("DelivertBean"); 
			mPreservationData.setRecDeliversWsId(mDelivertBean.getRecDeliversWsId());
		}else if(mType.equals("2")){
			mReceiptBean = (QRReceiptBean) getIntent().getSerializableExtra("ReceiptBean"); 
		}else if(mType.equals("3")){
			mGuaranteeBean = (QRGuaranteeBean) getIntent().getSerializableExtra("GuaranteeBean"); 
		}
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		if(mType.equals("1")){
			title_text.setText("发货扫描");
			findViewById(R.id.fh_invoice_information).setVisibility(View.VISIBLE);
			findViewById(R.id.sh_receipt_information).setVisibility(View.GONE);
			findViewById(R.id.bx_guarantee_information).setVisibility(View.GONE);
			mFH_ckbh = (TextView) findViewById(R.id.fh_ckbh);
			mFH_fhck = (TextView) findViewById(R.id.fh_fhck);
			mFH_shck = (TextView) findViewById(R.id.fh_shck);
			mFH_lczt = (TextView) findViewById(R.id.fh_lczt);
			mFH_zdr = (TextView) findViewById(R.id.fh_zdr);
			mFH_zdsj = (TextView) findViewById(R.id.fh_zdsj);
			mFH_djlx = (TextView) findViewById(R.id.fh_djlx);
			mFH_wlzs = (TextView) findViewById(R.id.fh_wlzs);
			mFH_ytms = (TextView) findViewById(R.id.fh_ytms);
			mFH_mtms = (TextView) findViewById(R.id.fh_mtms);
			
			mCancel = (Button) findViewById(R.id.ewm_cancel);
			mPreservation = (Button) findViewById(R.id.ewm_preservation);
			
			findViewById(R.id.ewm_cancel).setVisibility(View.VISIBLE);
			findViewById(R.id.ewm_preservation).setVisibility(View.VISIBLE);
			
		}else if(mType.equals("2")){
			title_text.setText("收货物料扫描");
			findViewById(R.id.fh_invoice_information).setVisibility(View.GONE);
			findViewById(R.id.sh_receipt_information).setVisibility(View.VISIBLE);
			findViewById(R.id.bx_guarantee_information).setVisibility(View.GONE);
			mSH_shbh = (TextView) findViewById(R.id.sh_shbh);
			mSH_fhck = (TextView) findViewById(R.id.sh_fhck);
			mSH_jsck = (TextView) findViewById(R.id.sh_jsck);
			mSH_fhr = (TextView) findViewById(R.id.sh_fhr);
			mSH_fhsj = (TextView) findViewById(R.id.sh_fhsj);
			mSH_wlzs = (TextView) findViewById(R.id.sh_wlzs);
			mSH_tmzs = (TextView) findViewById(R.id.sh_tmzs);
			mSH_wyztms = (TextView) findViewById(R.id.sh_wyztms);
			
			mCancel = (Button) findViewById(R.id.ewm_cancel);
			mPreservation = (Button) findViewById(R.id.ewm_preservation);
			
			findViewById(R.id.ewm_cancel).setVisibility(View.VISIBLE);
			findViewById(R.id.ewm_preservation).setVisibility(View.GONE);
			
		}else if(mType.equals("3")){
			title_text.setText("报修物料扫描");
			findViewById(R.id.fh_invoice_information).setVisibility(View.GONE);
			findViewById(R.id.sh_receipt_information).setVisibility(View.GONE);
			findViewById(R.id.bx_guarantee_information).setVisibility(View.VISIBLE);
			mBX_bxdh = (TextView) findViewById(R.id.bx_bxdh);
			mBX_bxcw = (TextView) findViewById(R.id.bx_bxcw);
			mBX_bxry = (TextView) findViewById(R.id.bx_bxry);
			mBX_bxsj = (TextView) findViewById(R.id.bx_bxsj);
			
			mCancel = (Button) findViewById(R.id.ewm_cancel);
			mPreservation = (Button) findViewById(R.id.ewm_preservation);

			findViewById(R.id.ewm_cancel).setVisibility(View.VISIBLE);
			findViewById(R.id.ewm_preservation).setVisibility(View.VISIBLE);
			
		}
		
		mOpenCameBtn = (Button) findViewById(R.id.ewm_opencame);
		mOpenCameBtn.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		mPreservation.setOnClickListener(this);
		mCargoScanListView = (ListView) findViewById(R.id.qrcode_cargoscan_listview);
		
	
	}

	private void initData() {
		if(mType.equals("1")){
			mFH_ckbh.setText("出库编号：" + mDelivertBean.getFbillNo());
			mFH_fhck.setText("发货仓库：" + mDelivertBean.getFdcstockName());
			mFH_shck.setText("收货仓库：" + mDelivertBean.getFdeptName());
			mFH_lczt.setText(mDelivertBean.getFlowStatusName());//流程状态
			mFH_zdr.setText("制单人员：" + mDelivertBean.getFbillerName());
			mFH_zdsj.setText("制单时间：" + mDelivertBean.getCreateDateStr());
			mFH_djlx.setText("单据类型：" + mDelivertBean.getFbillCaptionName());
			mFH_wlzs.setText("物料总数：" + mDelivertBean.getWlTotal());
			mFH_ytms.setText("有条码数：" + mDelivertBean.getBarcodeCount());
			mFH_mtms.setText("没条码数：" + mDelivertBean.getNullBarcodeCount());
			
			mFHwlNum = Integer.parseInt(mDelivertBean.getWlTotal());//当前发货物料总数
			mFHYtmsNum = Integer.parseInt(mDelivertBean.getBarcodeCount());//当前发货有条码数
			
			listadapter = new QRCargoScanAdapter(mContext, new ArrayList<QRCargoScanBean>(), 1);
			mCargoScanListView.setAdapter(listadapter);
	
			getQRDelivertyDetailedData();//获取出库单明细列表
		}else if(mType.equals("2")){
			mSH_shbh.setText("收货编号：" + mReceiptBean.getBillNo());
			mSH_fhck.setText("发货仓库：" + mReceiptBean.getFdcstockName());
			mSH_jsck.setText("接收仓库：" + mReceiptBean.getFdeptName());
			mSH_fhr.setText("发货人员：" + mReceiptBean.getFsmanagerName());
			mSH_fhsj.setText("发货时间：" + mReceiptBean.getFdateStr());
			mSH_wlzs.setText("物料总数：" + mReceiptBean.getWlTotal());
			mSH_tmzs.setText("有条码数：" + mReceiptBean.getBarcodeCount());
			mSH_wyztms.setText("未扫描数：" + mReceiptBean.getUnscanBarcodeCount());
			
			listadapter = new QRCargoScanAdapter(mContext, new ArrayList<QRCargoScanBean>(), 2);
			mCargoScanListView.setAdapter(listadapter);
			
			getQRReceiptDetailedData();//获取收货单明细列表
		}else if(mType.equals("3")){
			mBX_bxdh.setText("报修单号：" + mGuaranteeBean.getRepairReceiptNo());
			mBX_bxcw.setText("报修仓库：" + mGuaranteeBean.getFillinWrName());
			mBX_bxry.setText("报修人员：" + mGuaranteeBean.getFillinPeopleName());
			mBX_bxsj.setText("报修时间：" + mGuaranteeBean.getFillinDateStr());

			
			listadapter = new QRCargoScanAdapter(mContext, new ArrayList<QRCargoScanBean>(), 3);
			mCargoScanListView.setAdapter(listadapter);
			
			getQRGuaranteeDetailedData();//获取报修单明细列表
		}
	}

	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			OpenDialog("您确定要退出扫描吗？", 2);
			break;
		case R.id.ewm_opencame:
			
			if(isError == true){
				T.showShort(mContext, "数据出错!");
				return;
			}
			Intent intent = new Intent(this, CaptureActivity.class);
			startActivityForResult(intent, KingTellerStaticConfig.QRCODE_NUM);
			break;
		case R.id.ewm_cancel:
			OpenDialog("您确定要退出扫描吗？", 2);
			break;
		case R.id.ewm_preservation:
			if(mType.equals("1")){
				setQRDelivertEwmData();
			}else if(mType.equals("3")){
				setQRGuaranteeEwmData();
			}

			break;
		default:
			break;
		}
		
	}
	
	//捕捉返回键
	public void onBackPressed() {
		OpenDialog("您确定要退出扫描吗？",2);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.QRCODE_NUM:
			if (resultCode == RESULT_OK) {
				mEwm_Number = (String) data.getSerializableExtra("ewm_num");
				if(mEwmSearchList.contains(mEwm_Number)){//判断是否已经扫描
					OpenDialog("条码已被扫描！", 1);
				}else{
					if(mType.equals("1")){
						getQRDelivertyEwmData(mEwm_Number, mDelivertBean.getRecDeliversWsId());		
					}else if(mType.equals("2")){
						getQRReceiptEwmData(mEwm_Number);
					}else if(mType.equals("3")){
						getQRGuaranteeEwmData(mEwm_Number, mGuaranteeBean.getRepairReceiptId());
					}
					
				}
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 3.1 获取报修单明细列表
	 */
	private void getQRGuaranteeDetailedData() {
		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("repairReceiptId", mGuaranteeBean.getRepairReceiptId());//保修单id

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.BXdMxLists),params, 
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
						Log.e("获取报修单明细列表","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(List<QRCargoScanBean> data) {
						mCargoScanList = data;
						KingTellerProgressUtils.closeProgress();
						if(mCargoScanList.size() > 0 && mCargoScanList.get(0).getCode().equals("")){
							listadapter.setLists(mCargoScanList);
						}else{
							String msg = mCargoScanList.get(0).getCode();
							if(msg.contains("查询结果无数据")){
								msg  = msg + "如要添报修物料请扫描！";
								mCargoScanList = new ArrayList<QRCargoScanBean>();
								listadapter.setLists(mCargoScanList);
							}else{
								isError = true;
							}
							OpenDialog(msg, 1);	
						}
					};
				});

	}
	
	
	/**
	 * 3.2报修单验证二维码
	 */
	private void getQRGuaranteeEwmData(String barcode, String repairReceiptId){
		
		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}
		
		if(mCargoScanList.size()>0){
			if(mCargoScanList.get(0).getWlBarCode().equals(barcode)){
				OpenDialog("所扫物料为同一个物料,请重新扫描！", 1);
				return;
			}
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("repairReceiptId", repairReceiptId);//保修单id
		params.put("barcode", barcode);//二维码

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.BXdMxEwmYz),params, 
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
						Log.e("报修单验证二维码","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(QRCargoScanBean data) {
						KingTellerProgressUtils.closeProgress();
						if(data.getCode().equals("")){
							setGuaranteeOllData(data, mEwm_Number);
						}else{
							OpenDialog(data.getCode() + " 请重新扫描！", 1);
						}
					};
				});
	}
	
	/**
	 * 3.2.1报修单验证后数据处理
	 * @param data
	 * @param barcode
	 */
	private void setGuaranteeOllData(QRCargoScanBean data ,String barcode){
		mGuaranteeCargoScanList = new ArrayList<QRCargoScanBean>();
		if(mCargoScanList.size()>0){
			mCargoScanList.get(0).setWlId(data.getWlId());
			mCargoScanList.get(0).setNewCode(data.getNewCode());
			mCargoScanList.get(0).setWlName(data.getWlName());
			mCargoScanList.get(0).setWlBarCode(barcode);
			mCargoScanList.get(0).setIsNewadd("2");
			
			mEwmSearchList.add(barcode);//记录扫描记录
			mGuaranteeCargoScanList.add(mCargoScanList.get(0));
			listadapter.setLists(mCargoScanList);

		}else{
			mCargoScanList.add(data);
			mCargoScanList.get(0).setWlBarCode(barcode);
			mCargoScanList.get(0).setIsNewadd("1");
			
			mEwmSearchList.add(barcode);//记录扫描记录
			mGuaranteeCargoScanList.add(mCargoScanList.get(0));
			listadapter.setLists(mCargoScanList);
		}
	
	}
	
	/**
	 * 3.3保存报修单
	 */
	private void setQRGuaranteeEwmData(){

		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		if(mGuaranteeCargoScanList.size() <= 0){
			T.showShort(mContext, "没有所更新的报修物料, 请扫描!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("repairReceiptId",mGuaranteeBean.getRepairReceiptId());
		params.put("barcode", mGuaranteeCargoScanList.get(0).getWlBarCode());
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.BXdBC),params, 
				new AjaxHttpCallBack<QRDeliveryBean>(this,
						new TypeToken<QRDeliveryBean>() {
						}.getType(), true) {
			
					@Override
					public void onStart() {
						// 开始http请求的时候回调
						KingTellerProgressUtils.showProgress(mContext, "正在保存中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						
						KingTellerProgressUtils.closeProgress();
						Log.e("保存出库单","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(QRDeliveryBean data) {
						KingTellerProgressUtils.closeProgress();
						if(data.getCode().equals("")){
							isBaoCun = true;
							OpenDialog("保存成功！", 1);	
							//重设数据
							mEwmSearchList = new ArrayList<String>();
							mCargoScanList = new ArrayList<QRCargoScanBean>();
							mGuaranteeCargoScanList = new ArrayList<QRCargoScanBean>();
							
							getQRGuaranteeDetailedData();//获取报修列表
							
						}else{
							OpenDialog("保存失败：" + data.getCode(), 1);	
						}
					};
				});
	}
	
	/**
	 * 2.1获取收货单明细列表
	 */
	private void getQRReceiptDetailedData() {
	
		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("deliversWarehouseId", mReceiptBean.getDeliversWarehouseId());//收货单id

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SHdMxLists),params, 
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
						Log.e("获取收货单明细列表","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(List<QRCargoScanBean> data) {
						mCargoScanList = data;
						KingTellerProgressUtils.closeProgress();
						if(mCargoScanList.size() > 0 && mCargoScanList.get(0).getCode().equals("")){
							listadapter.setLists(mCargoScanList);
						}else{
							String msg = mCargoScanList.get(0).getCode();
							if(msg.contains("查询结果无数据")){
								mCargoScanList = new ArrayList<QRCargoScanBean>();
								listadapter.setLists(mCargoScanList);
							}else{
								isError = true;
							}
							OpenDialog(msg, 1);	
						}
					};
				});
	}
	
	/**
	 * 2.2收货单验证二维码
	 */
	public void getQRReceiptEwmData(String barcode) {
		
		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}
		
		if("0".equals(mReceiptBean.getUnscanBarcodeCount())){
			OpenDialog("没有需要验证的的收货物料，请检查收货单内容！", 1);	
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("deliversWarehouseId", mReceiptBean.getDeliversWarehouseId());//收货单id
		params.put("barcode", barcode);//二维码
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SHdMxEwmYz),params, 
				new AjaxHttpCallBack<QRCargoScanBean>(this,
						new TypeToken<QRCargoScanBean>() {
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
						Log.e("收货单验证二维码","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(QRCargoScanBean data) {
						KingTellerProgressUtils.closeProgress();
						if(data.getCode().equals("")){
							for(int i = 0; i<mCargoScanList.size(); i++){
								if(mCargoScanList.get(i).getRecDeliversWsFormsId().equals(data.getRecDeliversWsFormsId())){
									//获取当前验证的物料 未扫描数 -1
									mCargoScanList.get(i).setIsScaned("是");
									int wsms = Integer.parseInt(mCargoScanList.get(i).getUnscanBarcodeCount()) - 1;
									mCargoScanList.get(i).setUnscanBarcodeCount(Integer.toString(wsms));
									//更新明细
									listadapter.setLists(mCargoScanList);
									mEwmSearchList.add(mEwm_Number);
									
									//头信息未扫描总数 -1
									int t_wsms = Integer.parseInt(mReceiptBean.getUnscanBarcodeCount()) - 1;
									mReceiptBean.setUnscanBarcodeCount(Integer.toString(t_wsms));
									mSH_wyztms.setText("未扫描数：" + mReceiptBean.getUnscanBarcodeCount());
									
									break;
								}	
							}
							
							T.showShort(mContext, "验证成功!");
							openCame();

						}else{
							OpenDialog(data.getCode() + " 请重新扫描！", 1);	
						}
					};
				});
	}
	
	/**
	 * 1.1获取出库单明细列表
	 */
	public void getQRDelivertyDetailedData() {

		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("recDeliversWsId", mDelivertBean.getRecDeliversWsId());//出库单id


		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CkdMxLists),params, 
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
						Log.e("获取出库单明细列表","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(List<QRCargoScanBean> data) {
						mCargoScanList = data;
						KingTellerProgressUtils.closeProgress();
						if(mCargoScanList.size() > 0 && mCargoScanList.get(0).getCode().equals("")){
							//获取明细列表物料编码数组
							for(int i = 0; i<mCargoScanList.size(); i++){
								mNewCodeLsit.add(mCargoScanList.get(i).getNewCode());
							}
							//获取少数据列表
							if(mCargoScanList.size() <= 1000){
								for(int g = 0; g < mCargoScanList.size(); g++){
									mShowCargoScanList.add(mCargoScanList.get(g));
									mShowNewCodeLsit.add(mCargoScanList.get(g).getNewCode());
								}
							}else{
								T.showShort(mContext, "出库物料过多，暂不显示!");
							}
							listadapter.setLists(mShowCargoScanList);
							
						}else{
							String msg = mCargoScanList.get(0).getCode();
							if(msg.contains("查询结果无数据")){
								msg = msg + "如要添加出库物料请扫描！";
								mCargoScanList = new ArrayList<QRCargoScanBean>();
								mShowCargoScanList = new ArrayList<QRCargoScanBean>();
								listadapter.setLists(mShowCargoScanList);
							}else{
								isError = true;
							}

							OpenDialog(msg, 1);	
							
						}
					};
				});
	}
	
	/**
	 * 1.2出库单验证二维码
	 */
	public void getQRDelivertyEwmData(String barcode, String recDeliversWsId) {

		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("recDeliversWsId", recDeliversWsId);//出库单id
		params.put("barcode", barcode);//二维码

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CkdMxEwmYz),params, 
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
							if("报修出库".equals(mDelivertBean.getFbillCaptionName()) && KingTellerJudgeUtils.isEmpty(data.getFsourceBillNo())){
								OpenDialog("本单单据类型为 报修出库 , 所验证的物料信息不能没有报修源单号!", 1);	
								return;
							}
							setDelivertyOllData(data, mEwm_Number);
							openCame();
						}else{
							OpenDialog(data.getCode() + " 请重新扫描！", 1);	
						}
					};
				});
	}
	
	/**
	 * 1.2.1出库单验证后数据处理
	 * @param data mFHwlNum mFHtmsNum	
	 */
	public void setDelivertyOllData(QRCargoScanBean data ,String barcode){
		Log.e("ondata","onuse:" + mNewCodeLsit.contains(data.getNewCode()));
		if(!KingTellerJudgeUtils.isEmpty(data.getFsourceBillNo())){
			if(data.getFsourceBillNo().contains("BX")){//判断是否含有报修源单号 
				QRCargoScanBean AddNewData = new QRCargoScanBean();
				AddNewData.setNewCode(data.getNewCode());
				AddNewData.setWlName(data.getWlName());
				AddNewData.setQuantity("1");
				AddNewData.setBarcodeCount("1");
				AddNewData.setNullBarcodeCount("0");
				AddNewData.setIsNewaddSl("yes");
				AddNewData.setIsNewaddTms("yes");
				AddNewData.setFsourceBillNo(data.getFsourceBillNo());
				AddNewData.setIsNewadd("1");
				mCargoScanList.add(AddNewData);
				
				mNewCodeLsit.add(AddNewData.getNewCode());
				
				mFHwlNum ++;
				mFHYtmsNum ++;
				
				setQRDelivertCKEwmData(AddNewData, barcode);
				
				QRDelivertyMxShowUser(AddNewData);
			}
		}else{
			if(mNewCodeLsit.contains(data.getNewCode())){
				//已经获取的物料
				for(int i = 0; i<mCargoScanList.size(); i++){
					if(mCargoScanList.get(i).getNewCode().equals(data.getNewCode())){
						
						int SlInt = Integer.valueOf(mCargoScanList.get(i).getQuantity()).intValue(); //获取总数量
						int YTmsInt = Integer.valueOf(mCargoScanList.get(i).getBarcodeCount()).intValue(); //获取有条码数
						int MTmsInt = Integer.valueOf(mCargoScanList.get(i).getNullBarcodeCount()).intValue(); //获取没条码数
						
						if(mCargoScanList.get(i).getIsNewadd() != null){//判断是否新增
							SlInt ++;
							YTmsInt ++;
							
							mFHwlNum ++;
							mFHYtmsNum ++;
							
							mCargoScanList.get(i).setIsNewaddSl("yes");
						}else{
							if(SlInt - MTmsInt > YTmsInt){//判断条码数是否小于等于数量
								YTmsInt ++;
								mFHYtmsNum ++;
							}else{
								if(!KingTellerJudgeUtils.isEmpty(mCargoScanList.get(i).getFsourceBillNo())){//判断是否含有源单号
									T.showShort(mContext, "所扫物料正关联源单号,无法新增数量!");
									break;
								}else{
									SlInt ++;
									YTmsInt ++;
									
									mFHwlNum ++;
									mFHYtmsNum ++;
									
									mCargoScanList.get(i).setIsNewaddSl("yes");
								}
							}
						}
						mCargoScanList.get(i).setQuantity(Integer.toString(SlInt));
						mCargoScanList.get(i).setBarcodeCount(Integer.toString(YTmsInt));
						mCargoScanList.get(i).setIsNewaddTms("yes");
						
						setQRDelivertCKEwmData(mCargoScanList.get(i), barcode);
						
						QRDelivertyMxShowUser(mCargoScanList.get(i));
						break;
					}
				}
			}else{
			//未经获取的物料
				QRCargoScanBean AddNewData = new QRCargoScanBean();
				AddNewData.setNewCode(data.getNewCode());
				AddNewData.setWlName(data.getWlName());
				AddNewData.setQuantity("1");
				AddNewData.setBarcodeCount("1");
				AddNewData.setNullBarcodeCount("0");
				AddNewData.setIsNewaddSl("yes");
				AddNewData.setIsNewaddTms("yes");
				AddNewData.setIsNewadd("1");
				mCargoScanList.add(AddNewData);
				
				mNewCodeLsit.add(AddNewData.getNewCode());
				
				mFHwlNum ++;
				mFHYtmsNum ++;
				
				setQRDelivertCKEwmData(AddNewData, barcode);
				
				QRDelivertyMxShowUser(AddNewData);
			}
		}
	}
	
	/**
	 * 1.2.1.1出库单展示给用户的list
	 */
	public void QRDelivertyMxShowUser(QRCargoScanBean data){
		if(!KingTellerJudgeUtils.isEmpty(data.getFsourceBillNo())){
			if(data.getFsourceBillNo().contains("BX")){//判断是否含有报修源单号 
				mShowNewCodeLsit.add(data.getNewCode());
				mShowCargoScanList.add(data);
				mEwmSearchList.add(mEwm_Number);//记录扫描记录
			}
		}else{
			if(mShowNewCodeLsit.contains(data.getNewCode())){
				for(int n = 0; n < mShowCargoScanList.size(); n++){
					if(mShowCargoScanList.get(n).getNewCode().equals(data.getNewCode())){
						mShowCargoScanList.set(n, data);
						mEwmSearchList.add(mEwm_Number);//记录扫描记录 
						break;
					}
				}
			}else{
				mShowNewCodeLsit.add(data.getNewCode());
				mShowCargoScanList.add(data);
				mEwmSearchList.add(mEwm_Number);//记录扫描记录
			}
		}
		listadapter.setLists(mShowCargoScanList);
	}
	
	/**
	 * 1.2.1.2获取成功出库的二维码
	 * @param data
	 * @param barcode
	 */
	public void setQRDelivertCKEwmData(QRCargoScanBean data, String barcode){
		
		mFH_wlzs.setText("物料总数：" + mFHwlNum);
		mFH_ytms.setText("有条码数：" + mFHYtmsNum);

		QRPreservationDataEwmListBean TiaoMa = new QRPreservationDataEwmListBean();
		if(data.getRecDeliversWsFormsId() == null){
			TiaoMa.setRecDeliversWsFormsId("");
		}else{
			TiaoMa.setRecDeliversWsFormsId(data.getRecDeliversWsFormsId());
		}
		TiaoMa.setBarcode(barcode);
		TiaoMa.setNewCode(data.getNewCode());
		mPreservationEwmList.add(TiaoMa);
	}
	
	/**
	 * 1.3保存出库单
	 */
	public void setQRDelivertEwmData(){
		mPreservationData.setRecDeliversWsFormsList(mPreservationEwmList);
		
		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		if(mPreservationData.getRecDeliversWsFormsList().size() <= 0){
			T.showShort(mContext, "没有保存的物料条码, 请重新扫描!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("recDeliversWsId",mPreservationData.getRecDeliversWsId());
		params.put("recDeliversWsFormsList", gson.toJson(mPreservationData.getRecDeliversWsFormsList()));

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CkdBC),params, 
				new AjaxHttpCallBack<QRDeliveryBean>(this,
						new TypeToken<QRDeliveryBean>() {
						}.getType(), true) {
			
					@Override
					public void onStart() {
						// 开始http请求的时候回调
						KingTellerProgressUtils.showProgress(mContext, "正在保存中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						
						KingTellerProgressUtils.closeProgress();
						Log.e("保存出库单","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(QRDeliveryBean data) {
						KingTellerProgressUtils.closeProgress();
						if(data.getCode().equals("")){
							OpenDialog("保存成功！", 1);	
							isBaoCun = true;
							//如果成功保存 把当前值返回
							mFH_bcwlNum = mFHwlNum;
							mFH_bctmsNum = mFHYtmsNum;

							//重设数据
							mEwmSearchList = new ArrayList<String>();
							mCargoScanList = new ArrayList<QRCargoScanBean>();
							mNewCodeLsit = new ArrayList<String>();
							mPreservationEwmList = new ArrayList<QRPreservationDataEwmListBean>();
							mShowCargoScanList = new ArrayList<QRCargoScanBean>();
							mShowNewCodeLsit = new ArrayList<String>();
						
							
							getQRDelivertyDetailedData();//获取明细列表
							
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
	 * 自动启动扫描界面
	 */
	public void openCame(){
		
		new Thread(new Runnable() {  
			@Override  
			public void run() {
				try {
					Thread.sleep(1500);
					Intent intent = new Intent(QRCargoScanActivity.this, CaptureActivity.class);
					startActivityForResult(intent, KingTellerStaticConfig.QRCODE_NUM);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}).start(); 
	}
	
	
	/**
	 * 返回修改值
	 */
	public void ComeBackData(){
		if(mType.equals("1")){
			if(isBaoCun){
				Intent intent = new Intent();
				intent.putExtra("mFH_wlzs", Integer.toString(mFH_bcwlNum));
				intent.putExtra("mFH_tmzs", Integer.toString(mFH_bctmsNum));
				setResult(RESULT_OK, intent);
			}
		}else if(mType.equals("2")){
			Intent intent = new Intent();
			intent.putExtra("mSH_wyztms", mReceiptBean.getUnscanBarcodeCount());
			setResult(RESULT_OK, intent);
		}else if(mType.equals("3")){
			if(isBaoCun){
				Intent intent = new Intent();
				intent.putExtra("mBX_zt", "yes");
				setResult(RESULT_OK, intent);
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
