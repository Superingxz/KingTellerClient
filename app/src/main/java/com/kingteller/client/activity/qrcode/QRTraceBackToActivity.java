package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.CommonSelectQrCodeAdapter;
import com.kingteller.client.adapter.QRCodeInputAdapter;
import com.kingteller.client.bean.qrcode.QRBarCodeBean;
import com.kingteller.client.bean.qrcode.QRReceiptBean;
import com.kingteller.client.bean.qrcode.QRTraceBackToBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.modules.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QRTraceBackToActivity extends Activity implements OnClickListener, OnItemClickListener{
	
	private EditText edit_barcode;
	private ListView mCodeRraceSearchListView;
	private CommonSelectQrCodeAdapter listadapter;
	
	private TextView title_text,text_mage;
	private Button title_left_btn, btn_search, btn_open;
	
	private Context mContext;
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		
		setContentView(R.layout.layout_qrcode_trace_backto);
		KingTellerApplication.addActivity(this);
		
		mContext = QRTraceBackToActivity.this;

		initUI();
		initData();
	}

	public void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		btn_search = (Button) findViewById(R.id.qr_trace_btn_search);
		btn_open = (Button) findViewById(R.id.qr_trace_btn_open);
		edit_barcode = (EditText) findViewById(R.id.qr_trace_edit);
		text_mage = (TextView) findViewById(R.id.qr_trace_text);

		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		btn_open.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		
		mCodeRraceSearchListView = (ListView) findViewById(R.id.qr_trace_search_list);
		mCodeRraceSearchListView.setOnItemClickListener(this);
		listadapter = new CommonSelectQrCodeAdapter(mContext, new ArrayList<QRTraceBackToBean>());
		mCodeRraceSearchListView.setAdapter(listadapter);
		
		
		KingTellerConfigUtils.hideInputMethod(this);

	}
	
	public void initData() {
		title_text.setText("二维码追溯");
		text_mage.setText(Html.fromHtml("如二维码无法扫描<br/>请在下面输入该二维码进行<font color=blue>搜索</font>！"));
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 扫描返回函数
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.QRCODE_ZS_BACK:
			if (resultCode == RESULT_OK) {
				String mEwm_Number = (String) data.getSerializableExtra("ewm_num");
				
				edit_barcode.setText(mEwm_Number);
				
				getEwmTraceBackToMessage(mEwm_Number);

			}
			break;
		default:
			break;
		}
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				finish();
				break;
			case R.id.qr_trace_btn_open:
				Intent intent = new Intent(mContext, CaptureActivity.class);
				startActivityForResult(intent, KingTellerStaticConfig.QRCODE_ZS_BACK);
				break;
			case R.id.qr_trace_btn_search:
				String search_zsewm = edit_barcode.getText().toString();

				if(search_zsewm.length() < 5){
					T.showShort(mContext, "请输入不少于5个字符!");
				}else if(search_zsewm.matches("[0]+")){
					T.showShort(mContext, "不能全为0!");
				}else{
					getSearchEwmTraceBackToMessage(search_zsewm);
				}

				//startActivity(new Intent(mContext, QRTraceBackToShowActivity.class));
				break;
	
			default:
				break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		QRTraceBackToBean data = (QRTraceBackToBean) parent.getAdapter().getItem(position);
		edit_barcode.setText(data.getBarcode());

		getEwmTraceBackToMessage(data.getBarcode());
	}
	
	
	
	/**
	 * 搜索二维码追溯信息
	 */
	public void getSearchEwmTraceBackToMessage(String text){
		// TODO 搜索二维码追溯信息
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("barcode", text);//二维码号
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SsZsEwm), params,
			new AjaxHttpCallBack<List<QRTraceBackToBean>>(this,
					new TypeToken<List<QRTraceBackToBean>>() {
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
					KingTellerProgressUtils.showProgress(mContext, "正在查询二维码信息中...");
				}

				@Override
				public void onDo(List<QRTraceBackToBean> data) { 
					KingTellerProgressUtils.closeProgress();
					
					if("".equals(data.get(0).getCode())){
						listadapter.setLists(data);
					}else{
						listadapter.setLists(new ArrayList<QRTraceBackToBean>());
						T.showShort(mContext, data.get(0).getCode());
					}		
				};
			});
	}
	
	
	
	
	/**
	 * 获取该二维码追溯信息
	 */
	public void getEwmTraceBackToMessage(String ewm){
		// TODO 获取二维码信息
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("barcode", ewm);//二维码号
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CkZsEwm), params,
			new AjaxHttpCallBack<QRTraceBackToBean>(this,
					new TypeToken<QRTraceBackToBean>() {
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
					KingTellerProgressUtils.showProgress(mContext, "正在查询追溯信息中...");
				}

				@Override
				public void onDo(QRTraceBackToBean data) { 
					KingTellerProgressUtils.closeProgress();
					
					if("".equals(data.getCode())){
						Intent intent = new Intent(QRTraceBackToActivity.this, QRTraceBackToShowActivity.class);
						intent.putExtra("TraceBackToBean", (QRTraceBackToBean) data); 
						startActivity(intent); 
						
					}else{
						edit_barcode.setText("");
						listadapter.setLists(new ArrayList<QRTraceBackToBean>());
						T.showShort(mContext, data.getCode());
					}
				};
			});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
