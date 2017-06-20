package com.kingteller.client.activity.workorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.common.CommonSelectAreaActivity;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class IncreaseWddzActivity extends Activity implements OnClickListener{

	private KingTellerEditText provence;
	private KingTellerEditText city;
	private KingTellerEditText district;
	private KingTellerEditText town;
	private KingTellerEditText street;
	private KingTellerEditText houseNum;
	private Button btn_save;
	private TextView title_text;
	private Button title_left_btn;
	private Context mContext;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_increase_wddz);
		KingTellerApplication.addActivity(this);
		
		mContext = IncreaseWddzActivity.this;
		initUI();
		initData();
	}
	
	private void initUI(){
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_text.setText("新增网点");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);

		
		provence = (KingTellerEditText) findViewById(R.id.provence);
		city = (KingTellerEditText) findViewById(R.id.city);
		district = (KingTellerEditText) findViewById(R.id.district);
		town = (KingTellerEditText) findViewById(R.id.town);
		street = (KingTellerEditText) findViewById(R.id.street);
		houseNum = (KingTellerEditText) findViewById(R.id.houseNum);
		btn_save = (Button) findViewById(R.id.btn_save);
		
		btn_save.setOnClickListener(this);
	}
	
	private void initData(){
		provence.setOnDialogClickLister(new OnDialogClickLister() {
			
			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IncreaseWddzActivity.this,CommonSelectAreaActivity.class);
				intent.putExtra(CommonSelectAreaActivity.TYPE, "provence");
				startActivityForResult(intent,KingTellerStaticConfig.SELECT_PROVENCE);
			}
		});
		
		city.setOnDialogClickLister(new OnDialogClickLister() {
			
			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				if(KingTellerJudgeUtils.isEmpty(provence.getFieldValue())){
					T.showShort(mContext, "省份必须填写!");
				}else{
					Intent intent = new Intent(IncreaseWddzActivity.this,CommonSelectAreaActivity.class);
					intent.putExtra("area_pid", provence.getFieldValue());
					intent.putExtra(CommonSelectAreaActivity.TYPE, "city");
					startActivityForResult(intent,KingTellerStaticConfig.SELECT_CITY);
				}
				
			}
		});
		
		district.setOnDialogClickLister(new OnDialogClickLister() {
			
			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				if(KingTellerJudgeUtils.isEmpty(city.getFieldValue())){
					T.showShort(mContext, "地市必须填写!");
				}else{
					Intent intent = new Intent(IncreaseWddzActivity.this,CommonSelectAreaActivity.class);
					intent.putExtra("area_pid", city.getFieldValue());
					intent.putExtra(CommonSelectAreaActivity.TYPE, "district");
					startActivityForResult(intent,KingTellerStaticConfig.SELECT_DISTICT);
				}
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.SELECT_PROVENCE:
			if(resultCode == RESULT_OK){
				city.setFieldTextAndValue("");
				district.setFieldTextAndValue("");
				provence.setFieldTextAndValue((CommonSelectData) data
						.getSerializableExtra(CommonSelectDataActivity.DATA));
			}
			break;
		case KingTellerStaticConfig.SELECT_CITY:
			if(resultCode == RESULT_OK){
				district.setFieldTextAndValue("");
				city.setFieldTextAndValue((CommonSelectData) data
						.getSerializableExtra(CommonSelectDataActivity.DATA));
			}
			break;
		case KingTellerStaticConfig.SELECT_DISTICT:
			if(resultCode == RESULT_OK){
				district.setFieldTextAndValue((CommonSelectData) data
						.getSerializableExtra(CommonSelectDataActivity.DATA));
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.btn_save:
			submitData();
			break;
		default:
			break;
		}
	}
	
	private void submitData(){
		if(KingTellerJudgeUtils.isEmpty(provence.getFieldText())){
			T.showShort(mContext, "省份必须填写!");
			return;
		}
		
		if(KingTellerJudgeUtils.isEmpty(city.getFieldText())){
			T.showShort(mContext, "地市必须填写!");
			return;
		}
		
		if(KingTellerJudgeUtils.isEmpty(district.getFieldText())){
			T.showShort(mContext, "县区必须填写!");
			return;
		}
		
		if(KingTellerJudgeUtils.isEmpty(street.getFieldText()) && KingTellerJudgeUtils.isEmpty(town.getFieldValue())
				&& KingTellerJudgeUtils.isEmpty(houseNum.getFieldValue())){
			T.showShort(mContext, "镇、街道、门牌号 至少填写一个!");

			return;
		}
		final String provenceStr = provence.getFieldText();
		final String cityStr = city.getFieldText();
		final String districtStr = district.getFieldText();
		final String townStr = KingTellerJudgeUtils.isEmpty(town.getFieldText())?"":town.getFieldText();
		final String streetStr = street.getFieldText();
		final String houseNumStr = KingTellerJudgeUtils.isEmpty(houseNum.getFieldText())?"":houseNum.getFieldText();
		final String wdAddress = provenceStr+cityStr+districtStr+townStr+streetStr+houseNumStr;
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("province", provenceStr);
		params.put("city", cityStr);
		params.put("county", districtStr);
		params.put("town", townStr);
		params.put("street", streetStr);
		params.put("houseNumber", houseNumStr);
		params.put("wdAddress", wdAddress);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.XzwddzUrl),
				params, new AjaxHttpCallBack<ReturnBackStatus>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "新增中...");
					}
					
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(ReturnBackStatus data) {
						if (data.getResult().equals("success")) {
							T.showShort(mContext, "新增成功!");
							CommonSelectData comm = new CommonSelectData();
							comm.setText(wdAddress);
							comm.setValue(data.getMessage());
							setResultsData(comm);
						} else {
							T.showShort(mContext, "新增失败!");
						}

					}
				});
	}
	
	private void setResultsData(CommonSelectData comm){
		Intent intent = new Intent();
		intent.putExtra(CommonSelectDataActivity.DATA,comm);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}

}
