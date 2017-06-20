package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.kingteller.client.adapter.QRDotDeliveradapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.db.QRDotDelivery;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.modules.zxing.activity.CaptureActivity;

/**
 * 网点发货物料扫描
 * @author Administrator
 */
public class QRDotDeliveryCargoScanActivity extends Activity implements OnClickListener{
	
	private TextView mEwm_fwz;
	private Button mEwm_Opencame,mEwm_Ok,mEwm_Cancel;
	private ListView mDotDeliveryListView;
	private QRDotDeliveradapter listadapter;
	private List<QRCargoScanBean> qrBarCodeList = new ArrayList<QRCargoScanBean>();
	private List<QRCargoScanBean> qrShowBarCodeList = new ArrayList<QRCargoScanBean>();
	private String mEwm_Number;
	private Gson gson;
	private User user;
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_qrcode_input);
		KingTellerApplication.addActivity(this);
		
		mContext = QRDotDeliveryCargoScanActivity.this;
		gson = new Gson();
		
		KingTellerStaticConfig.QR_DOTDELIVERY_LISTDATA = false;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);

		
		findViewById(R.id.LinearLayout25).setVisibility(View.GONE);

		mEwm_Opencame = (Button) findViewById(R.id.ewm_opencame);
		mEwm_Ok = (Button) findViewById(R.id.ewm_preservation);
		mEwm_Cancel = (Button) findViewById(R.id.ewm_cancel);
		mEwm_fwz = (TextView) findViewById(R.id.ewm_fwz_text);
		
		mDotDeliveryListView = (ListView) findViewById(R.id.qrcode_input_listview);
		
		listadapter = new QRDotDeliveradapter(mContext, new ArrayList<QRCargoScanBean>(), new ArrayList<QRCargoScanBean>());
		mDotDeliveryListView.setAdapter(listadapter);
		
		mEwm_Opencame.setOnClickListener(this);
		mEwm_Ok.setOnClickListener(this);
		mEwm_Cancel.setOnClickListener(this);
	}
	
	private void initData() {
		title_text.setText("网点发货扫描");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		user = User.getInfo(mContext);
		mEwm_fwz.setText(user.getOrgName());
		
		//从数据库读取数据
		QRDotDelivery data = KingTellerDbUtils.getDotDeliveryToDataBase(mContext, user.getOrgId());
		if (data != null){
			qrBarCodeList = KingTellerJsonUtils.getPersons(data.getDotDeliveryData(), QRCargoScanBean.class);
			getShowlist(qrBarCodeList);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				isOpenDialog();
				break;
			case R.id.ewm_opencame:
				Intent intent = new Intent(QRDotDeliveryCargoScanActivity.this, CaptureActivity.class);
				startActivityForResult(intent, KingTellerStaticConfig.QRCODE_NUM);
				break;
			case R.id.ewm_preservation:
				setQRDotDeliveryBaoCunData();
				break;
			case R.id.ewm_cancel:
				isOpenDialog();
				break;
			default:
				break;
		}
		
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
					getEwmXinxi(mEwm_Number);
				}
			}
			break;
		default:
			break;
		}
	}
	
	//捕捉返回键
	public void onBackPressed() {
		isOpenDialog();
	}
	
	private void getEwmXinxi(String ewm){
		String mWlBm = getJqAndWlBm(ewm);
		
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		
		//1.查询config_id parent_id 层级
		Cursor c = database.rawQuery("select wl_name from "
				+ "tb_sm_wl_info "
				+ "where new_code=?;",new String[]{mWlBm});
		
		c.moveToFirst();
		if(c.getCount() > 0){
			String wlname = c.getString(c.getColumnIndex("wl_name"));
			QRCargoScanBean data = new QRCargoScanBean();
			data.setNewCode(mWlBm);
			data.setWlName(wlname);
			data.setWlBarCode(ewm);
			setDotDeliveryCargoScanOllData(data);
			
		}else{
			OpenDialog("该物料不存在本地数据库，请更新数据库！", 1);
		}
		c.close();
		database.close();
	}
	
	/**
	 * 解析二维码信息
	 */
	public String getJqAndWlBm(String barcode){ 
	
		char stringArr []  = barcode.toCharArray(); //注意返回值是char数组
		if(stringArr.length > 26){ 

			//物料编码 = 条码字符数量 -26；
			int num = stringArr.length - 26;
			char str [] = new char [num];  
			
			for(int i = 0; i<num; i++){
				str[i] = stringArr[i];
			}		
			String wlbm = new String(str);
			return wlbm;
		}else{
			//机器编码 = 条码字符数量 <26；
			String jqbm = new String(stringArr);
			return jqbm;
		}
	}
	
	/**
	 * 保存网发货物料
	 */
	private void setQRDotDeliveryBaoCunData(){

		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}

		if(qrBarCodeList.size() <= 0){
			T.showShort(mContext, "请扫描物料条码!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orgId", user.getOrgId());
		params.put("recDeliversWsFormsList", gson.toJson(qrBarCodeList));
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WdFHBC),params, 
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
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						Log.e("保存网点发货物料","onError" + errorNo + ":" + strMsg);
						
						//保存数据库
						setBaoCunDotDeliveryData(0);

						T.showShort(mContext, "网络异常, 数据已缓存到本地!");
					}
					
					@Override
					public void onDo(QRCargoScanBean data) {
						KingTellerProgressUtils.closeProgress();
						
						if(data.getCode().equals("")){
							OpenDialog("保存成功!", 1);
							//重设数据
							qrBarCodeList = new ArrayList<QRCargoScanBean>();
							qrShowBarCodeList = new ArrayList<QRCargoScanBean>();
							listadapter.setLists(qrShowBarCodeList, qrBarCodeList);
							KingTellerDbUtils.deleteDotDeliveryToDataBase(mContext, user.getOrgId());
						}else{
							OpenDialog("保存失败：" + data.getCode(), 1);	
						}
					};
				});
	}
	
	
	private void setDotDeliveryCargoScanOllData(QRCargoScanBean data) {
		KingTellerStaticConfig.QR_DOTDELIVERY_LISTDATA = true;
		
		qrBarCodeList.add(data);
		getShowlist(qrBarCodeList);
		opencame();
	}

	/**
	 * 统计同种物料的个数
	 * @param barcodelist
	 */
	private void getShowlist(List<QRCargoScanBean> barcodelist){
		qrShowBarCodeList = new ArrayList<QRCargoScanBean>();
		
		ArrayList<String> list = new ArrayList<String>();  
		for(int i = 0; i<barcodelist.size(); i++){
			list.add(barcodelist.get(i).getNewCode() + "," + barcodelist.get(i).getWlName());//相同标志
		}
        HashMap<String, Integer> hash = new HashMap<String, Integer>();  
        for (int i = 0; i < list.size(); i++) {  
            try {  
                if (!hash.isEmpty() && hash.containsKey(list.get(i))) {  
                    hash.put(list.get(i).toString(), hash.get(list.get(i)) + 1);  
                } else {  
                    hash.put(list.get(i).toString(), 1);  
                }  
            } catch (Exception e) {  
  
            }  
        }  
        Set<String> set = hash.keySet();  
        for (String key : set) {  //重组结果
        	String [] stringArr = key.split(",");
        	QRCargoScanBean showData = new QRCargoScanBean();
			showData.setNewCode(stringArr[0]);
			showData.setWlName(stringArr[1]); 
			showData.setQuantity(hash.get(key).toString());
			showData.setBarcodeCount(hash.get(key).toString());
			qrShowBarCodeList.add(showData);
        }  
    	listadapter.setLists(qrShowBarCodeList, barcodelist);
	}
	
	/**
	 * 重启扫描界面
	 */
	private void opencame(){
		//二维码录入 成功添加后自动启动扫描界面
		new Thread(new Runnable() {  
			@Override  
			public void run() {
				try {
					Thread.sleep(500);
					Intent intent = new Intent(mContext, CaptureActivity.class);
					startActivityForResult(intent, KingTellerStaticConfig.QRCODE_NUM);	 
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}).start(); 
	}

	/**
	 * 判断二维码是否在当前列表
	 * @param codenum
	 * @return
	 */
	private boolean getcontains(String codenum){ 
		for(int i = 0; i<qrBarCodeList.size(); i++){
			if(qrBarCodeList.get(i).getWlBarCode().equals(codenum))
			return true;
		}
		return false;
	}
	
	/**
	 * 判断保存列表是否空而选择提示
	 */
	public void isOpenDialog(){
		if(qrBarCodeList.size() > 0 && 	KingTellerStaticConfig.QR_DOTDELIVERY_LISTDATA){
			OpenDialog("当前数据已更改且未保存, 是否离线保存？", 3);
		}else{
			OpenDialog("您确定要退出扫描吗？", 2);
		}
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
							setBaoCunDotDeliveryData(0);
							finish();
						}
                    });
		}
	}
	
	/**
	 * 保存数据到数据库
	 * @param isSusser
	 */
	public void setBaoCunDotDeliveryData(int isSuccesser){
		KingTellerDbUtils.saveDotDeliveryToDataBase(
				mContext, 
				user.getOrgId(), 
				"dotdelivery",
				gson.toJson(qrBarCodeList),
				user.getOrgId() + "," + user.getOrgName(), //所属服务站id,所属服务站名称
				KingTellerTimeUtils.getNowTime(),
				isSuccesser);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
	
}
