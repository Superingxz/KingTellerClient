package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.QRCodeInputAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.qrcode.QRBarCodeBean;
import com.kingteller.client.bean.qrcode.QRWRBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.calendar.CalendarDay;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.NormalListDialog;
import com.kingteller.client.view.dialog.entity.NormalListMenuItem;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSelectDateAndTimeClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSelectDateClickL;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;
import com.kingteller.client.view.dialog.use.KingTellerDateTimeDialogUtils;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.modules.zxing.activity.CaptureActivity;
/**
 * 二维码扫描录入
 * @author Administrator
 *
 */
public class QRCodeInputActivity extends Activity implements OnClickListener{
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn;
	
	private EditText mFwzName;
	private Button mEwm_Opencame, mEwm_search, mEwm_Ok, mEwm_Cancel;

	private QRWRBean qrWrbean = new QRWRBean();
	private List<QRWRBean> ListqrWrbean;
	private QRBarCodeBean qrBarCodebean;
	private List<QRBarCodeBean> qrBarCodeList = new ArrayList<QRBarCodeBean>();
	private ListView mCodeInPutListView;
	private QRCodeInputAdapter listadapter;
	
	private User user;
	private Gson gson;
	
	private String mEwm_Number;
	
	private String[] mStringItem;
	private List<NormalListMenuItem> mStringItemList;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	private Handler handler = new Handler(){
         @Override
         public void handleMessage(Message msg){
             super.handleMessage(msg);
             //UI更新操作
             switch (msg.what) {
	             case 1:
	            	 mFwzName.setText(msg.getData().getString("WrName"));
					break;
	             case 2:
	            	 mFwzName.setText("");
					break;
	             default:
					break;
             }
             
             KingTellerProgressUtils.closeProgress();
         }
     };
	 @Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_qrcode_input);
		KingTellerApplication.addActivity(this);
		
		mContext = QRCodeInputActivity.this;
		gson = new Gson();
		initUI();
		initData();
	}
	 


	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		findViewById(R.id.LinearLayout26).setVisibility(View.GONE);
		
		mFwzName = (EditText) findViewById(R.id.ewm_fwz_edit);
		
		mEwm_search = (Button) findViewById(R.id.ewm_search);
		mEwm_Opencame = (Button) findViewById(R.id.ewm_opencame);
		mEwm_Ok = (Button) findViewById(R.id.ewm_preservation);
		mEwm_Cancel = (Button) findViewById(R.id.ewm_cancel);
		
		mCodeInPutListView = (ListView) findViewById(R.id.qrcode_input_listview);
		
		listadapter = new QRCodeInputAdapter(mContext, new ArrayList<QRBarCodeBean>());
		mCodeInPutListView.setAdapter(listadapter);
		
		
		mEwm_Opencame.setOnClickListener(this);
		mEwm_search.setOnClickListener(this);
		mEwm_Ok.setOnClickListener(this);
		mEwm_Cancel.setOnClickListener(this);
		
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	private void initData() {
		title_text.setText("二维码扫描");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		user = User.getInfo(mContext);
		if(KingTellerJudgeUtils.isEmpty(user.getWrId()) || KingTellerJudgeUtils.isEmpty(user.getWrName())){
			getSsck(user.getOrgId());
		}else{
			mFwzName.setText(user.getWrName());
		}

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				OpenDialog("您确定要退出扫描吗？", 2);
				break;
			case R.id.ewm_search:
				getSearchSscw();
				break;
			case R.id.ewm_opencame:
				if(KingTellerJudgeUtils.isEmpty(mFwzName.getText().toString())){
					T.showShort(mContext, "服务站名不能为空!");
				}else if(qrBarCodeList.size() >= 10 ){
					T.showShort(mContext, "已扫描物料条码数已达到10条, 请保存!");
				}else{
					Intent intent = new Intent(QRCodeInputActivity.this, CaptureActivity.class);
					startActivityForResult(intent, KingTellerStaticConfig.QRCODE_NUM);
				}
				break;
			case R.id.ewm_preservation:
				setEwmMessage();
				break;
			case R.id.ewm_cancel:
				OpenDialog("您确定要退出扫描吗？", 2);
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
		// TODO 扫描返回函数
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.QRCODE_NUM:
			if (resultCode == RESULT_OK) {
				mEwm_Number = (String) data.getSerializableExtra("ewm_num");
				if(getcontains(mEwm_Number)){//判断是否已经扫描
					OpenDialog("条码已被扫描！", 1);
				}else{
					getEwmMessage(mEwm_Number);
				}
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 1.1根据服务站id查询所属仓位
	 * @param orgId
	 */
	private void getSsck(String orgId) {
			// TODO 获取保存所属仓位
			if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
				T.showShort(mContext, "没有网络，请检查网络是否可用!");
				return;
			}
			
			KTHttpClient fh = new KTHttpClient(true);
			AjaxParams params = new AjaxParams();
			params.put("orgId", orgId);//服务站ID
		
			
			fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CxsscwUrl), params,
				new AjaxHttpCallBack<QRWRBean>(this,
						new TypeToken<QRWRBean>() {
						}.getType(), true) {
			
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						
						KingTellerProgressUtils.closeProgress();
						Log.e("ATMsearch","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "查询所属仓位中...");
					}

					@Override
					public void onDo(QRWRBean data) { 
						qrWrbean = data;
						if(qrWrbean.getCode().equals("")){
							new Thread(new Runnable() {  
								@Override  
								public void run() {  
								//数据更新
								User.saveWr(mContext, qrWrbean.getWrId(), qrWrbean.getWrName());
								user = User.getInfo(mContext);
								
								//更新ui
								Message message = new Message();
								message.what = 1;
								Bundle bundleData = new Bundle();  
								bundleData.putString("WrName", qrWrbean.getWrName());
								message.setData(bundleData);  
								handler.sendMessage(message);
								
							    }  
							}).start();  
						}else{
							KingTellerProgressUtils.closeProgress();
							new Thread(new Runnable() {
								 public void run() {  
									 handler.sendEmptyMessage(2);
								 }
							 }).start();
							
							T.showShort(mContext, qrWrbean.getCode());
						}
					};
				});
	}
	
	/**
	 * 1.2查询仓库
	 */
	private void getSearchSscw(){
		String Searchck = mFwzName.getText().toString();
		if(Searchck.length() >= 3){
			getSsckLists(Searchck);
		}else{
			T.showShort(mContext, "请输入不少于3个字符!");
		}
	}
	
	
	/**
	 * 1.3获取仓库列表
	 * @param orgId
	 */
	private void getSsckLists(String ckname) {
			// TODO 获取仓库列表
			if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
				T.showShort(mContext, "没有网络，请检查网络是否可用!");
				return;
			}
			
			KTHttpClient fh = new KTHttpClient(true);
			AjaxParams params = new AjaxParams();
			params.put("wrName", ckname);//仓库名

			fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.MhCxsscwUrl), params,
				new AjaxHttpCallBack<List<QRWRBean>>(this,
						new TypeToken<List<QRWRBean>>() {
						}.getType(), true) {
			
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						
						KingTellerProgressUtils.closeProgress();
						Log.e("ATMsearch","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "查询所属仓库中...");
					}

					@Override
					public void onDo(List<QRWRBean> data) { 
						KingTellerProgressUtils.closeProgress();
						
						if(data.get(0).getCode() == null){
							ListqrWrbean = new ArrayList<QRWRBean>();
							ListqrWrbean = data;
							
							mStringItem = new String[ListqrWrbean.size()];
							mStringItemList = new ArrayList<NormalListMenuItem>();
							for(int i = 0; i < ListqrWrbean.size(); i++){
								NormalListMenuItem mSelectData = new NormalListMenuItem();
								mSelectData.setResName(ListqrWrbean.get(i).getWrName());
								mSelectData.setResId(ListqrWrbean.get(i).getWrId());
								
								mStringItem [i] = ListqrWrbean.get(i).getWrName();
								mStringItemList.add(mSelectData);
							}
							
							final NormalListDialog dialog_ssck = new NormalListDialog(mContext, mStringItem);
							dialog_ssck.title("请选择所属仓库")//
						                .layoutAnimation(null)
						                .titleBgColor(Color.parseColor("#409ED7"))//
						                .itemPressColor(Color.parseColor("#85D3EF"))//
						                .itemTextColor(Color.parseColor("#303030"))//
						                .show();
							dialog_ssck.setOnOperItemClickL(new OnOperItemClickL() {
						            @Override
						            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
						            	dialog_ssck.dismiss();
						            	
						            	NormalListMenuItem data = mStringItemList.get(position);
						            	
						            	String WrName = data.getResName();
										String WrId = data.getResId();
										
										//数据更新
										User.saveWr(mContext, WrId, WrName);
										user = User.getInfo(mContext);
										
										mFwzName.setText(WrName);
						            }
						        });
						}else{
							T.showShort(mContext, data.get(0).getCode());
						}
						
						
					};
				});
	}
	/**
	 * 2.1获取二维码信息
	 * @param orgId
	 */
	private void getEwmMessage(String barcode) {
			// TODO 获取二维码信息
			if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
				T.showShort(mContext, "没有网络，请检查网络是否可用!");
				return;
			}
			
//			if(KingTellerJudgeUtils.isEmpty(user.getWrId())){
//			T.showShort(mContext, "所属仓位ID不能为空!");
//				return;
//			}
		
			KTHttpClient fh = new KTHttpClient(true);
			AjaxParams params = new AjaxParams();
			params.put("barcode", barcode);//二维码号
			params.put("wrId", user.getWrId());//仓位id
			
			fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CxEwmMsg), params,
				new AjaxHttpCallBack<QRBarCodeBean>(this,
						new TypeToken<QRBarCodeBean>() {
						}.getType(), true) {
			
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						
						KingTellerProgressUtils.closeProgress();
						Log.e("ATMsearch","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "查询二维码信息中...");
					}

					@Override
					public void onDo(QRBarCodeBean data) { 
						KingTellerProgressUtils.closeProgress();
						qrBarCodebean = new QRBarCodeBean();
						qrBarCodebean = data;

					    addInputEwm(qrBarCodebean);
					    
					    if(qrBarCodeList.size() < 10){ 
					    	//二维码录入 成功添加后自动启动扫描界面
							new Thread(new Runnable() {  
								@Override  
								public void run() {
									try {
										Thread.sleep(1500);
										Intent intent = new Intent(QRCodeInputActivity.this, CaptureActivity.class);
										startActivityForResult(intent, KingTellerStaticConfig.QRCODE_NUM);	 
									} catch (InterruptedException e) {
										e.printStackTrace();
									} 
								}
							}).start(); 
					    }else{
					    	T.showShort(mContext, "已扫描物料条码数已达到10条, 请保存!");
						}
					};
				});
	}
	
	
	
	/**
	 * 2.2添加二维码录入扫描列表
	 * @param data
	 */
	private void addInputEwm(QRBarCodeBean data){
		
		QRBarCodeBean mdata = new QRBarCodeBean();
		mdata.setBarCode(mEwm_Number);
		mdata.setNewCode(data.getNewCode());
		mdata.setWlName(data.getWlName());
		mdata.setModelNo(data.getModelNo());
		mdata.setWlInfoId(data.getWlInfoId());
		mdata.setStockQuantity(data.getStockQuantity());
		mdata.setBarcodeCount(data.getBarcodeCount());
		mdata.setNullBarcodeCount(data.getNullBarcodeCount());
		if(data.getCode().equals("") == true && data.getCode() != null){
			mdata.setSmZt("二维码未被使用且有效");
		}else if(data.getCode().length() > 1){
			mdata.setSmZt(data.getCode());
		}else{
			mdata.setSmZt(setEwmCodeMsg(data.getCode()));
		}
		
		qrBarCodeList.add(0,mdata);
		listadapter.setLists(qrBarCodeList);
	}
	
	
	/**
	 * 录入二维码到物料明细
	 */
	private void setEwmMessage(){
		// TODO 录入二维码到物料明细
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}

		if(qrBarCodeList.size() <= 0){
			T.showShort(mContext, "请扫描条码!");
			return;
		}
		
		if(getcontainsuser("二维码未被使用且有效")){
			T.showShort(mContext, "请删除有红色标识扫描状态的条码!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("wrId", user.getWrId());//仓位ID
		params.put("wlList", gson.toJson(qrBarCodeList));

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.LREwmMsg), params,
				new AjaxHttpCallBack<List<QRBarCodeBean>>(this,
						new TypeToken<List<QRBarCodeBean>>() {
						}.getType(), true) {
		
				@Override
				public void onError(int errorNo, String strMsg) {
					super.onError(errorNo, strMsg);
					
					KingTellerProgressUtils.closeProgress();
					Log.e("ATMsearch","onError" + errorNo + ":" + strMsg);
					T.showShort(mContext, "数据访问超时!");
				}
				
				@Override
				public void onStart() {
					Log.e("ATMsearch","onStart");
					KingTellerProgressUtils.showProgress(mContext, "正在录入中...");

				}
				@Override
				public void onDo(List<QRBarCodeBean> data) {
					KingTellerProgressUtils.closeProgress();
					
					String msg = "";
					if(data.get(0).getCode().equals("")){
						msg = "成功保存: " + qrBarCodeList.size() + " 条！";
						qrBarCodeList = new ArrayList<QRBarCodeBean>();
					}else{
						if(data.get(0).getBarCode() != null){
							int cg = qrBarCodeList.size() - data.size();
							int sb = data.size();
							msg = "成功保存: " + cg + " 条！" + " 保存失败: " + sb + " 条！" ;
							
							List<QRBarCodeBean> qrBarCodeErrorList = new ArrayList<QRBarCodeBean>();
							for(int i = 0; i<data.size(); i++){
								for(int n = 0; n<qrBarCodeList.size(); n++){
									if(qrBarCodeList.get(n).getBarCode().equals(data.get(i).getBarCode())){
										qrBarCodeErrorList.add(qrBarCodeList.get(n));
										qrBarCodeErrorList.get(i).setSmZt(data.get(i).getCode());
										break;
									}	
								}
							}	
							qrBarCodeList = qrBarCodeErrorList;
							
						}else{ 
							msg = "保存出错！详细信息： " + data.get(0).getCode() + " 请重新保存！" ;
						}
					}
					
					OpenDialog(msg, 1);
					
					listadapter.setLists(qrBarCodeList);
					
				};
			});

	}
	
	/**
     * 设置二维码返回状态信息
     * 1，二维码为空
     * 2，没有该二维码相关信息记录
     * 3，二维码无效
     * 4，二维码已经被使用
     * @param code_num
     * @return 
     */
	private String setEwmCodeMsg(String code_num){ 
		String mEwmCodeMsg = "";
		if(code_num.equals("1") == true){
			mEwmCodeMsg = "二维码为空";
		}else if(code_num.equals("2") == true){
			mEwmCodeMsg = "没有该二维码相关信息记录";
		}else if(code_num.equals("3") == true){
			mEwmCodeMsg = "二维码无效";
		}else if(code_num.equals("4") == true){
			mEwmCodeMsg = "二维码已经被使用";
		}else if(code_num.length() > 1){
			mEwmCodeMsg = code_num;
		}
		return mEwmCodeMsg;
	}
	
	/**
	 * 判断二维码是否在当前列表
	 * @param codenum
	 * @return
	 */
	private boolean getcontains(String codenum){ 
		for(int i = 0; i<qrBarCodeList.size(); i++){
			if(qrBarCodeList.get(i).getBarCode().equals(codenum))
			return true;
		}
		return false;
	}
	
	/**
	 * 判断二维码是否为未被使用
	 * @param codenum
	 * @return
	 */
	private boolean getcontainsuser(String smzt){ 
		for(int i = 0; i<qrBarCodeList.size(); i++){
			if(!qrBarCodeList.get(i).getSmZt().equals(smzt))
			return true;
		}
		return false;
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
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
