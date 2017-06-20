package com.kingteller.client.activity.qrcode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.FunctionAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.toast.T;
/**
 * 二维码扫描功能
 * @author Administrator
 *
 */
public class QRMainActivity extends Activity implements OnItemClickListener {
	
	private ListView listview;
	private FunctionAdapter adapter;
	private TextView title_text;
	private Button title_left_btn;
	
	private Context mContext;
	
	 @Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_qrcode_main);
		KingTellerApplication.addActivity(this);

		mContext = QRMainActivity.this;
		initUI();
		initData();
	}
	 
	private void initUI() {
		// TODO Auto-generated method stub
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		listview = (ListView) findViewById(R.id.function_listview);

		String rightstr = User.getInfo(this).getRight();
		//排序显示
		String rightstr_ewm = null;
		String[] allright_ewm = {"MOBILE_EWM_LR","MOBILE_EWM_REG_SM","MOBILE_EWM_BX_SM","MOBILE_EWM_FH_SM",
				"MOBILE_EWM_WD_FH","MOBILE_EWM_SH_SM","MOBILE_EWM_WDJQ_SM","MOBILE_EWM_ZS",
				"MOBILE_EWM_WDJQGH_SM_LX", "MOBILE_EWM_WDJQ_SM_LX"};
		
		for(int i = 0; i<allright_ewm.length; i++){
			if(rightstr.contains(allright_ewm[i])){
				if(KingTellerJudgeUtils.isEmpty(rightstr_ewm)){
					rightstr_ewm = allright_ewm[i];
				}else{
					rightstr_ewm = rightstr_ewm + "," + allright_ewm[i];
				}
				
			}
		}
		
		adapter = new FunctionAdapter(this, rightstr_ewm, FunctionAdapter.EWMMENU);
		listview.setAdapter(adapter);
	
		listview.setOnItemClickListener(this);
	}
	
	private void initData() {
		title_text.setText("二维码扫描");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		String action = (String) parent.getAdapter().getItem(position);
		if (action.equals("MOBILE_EWM_LR")) {
			/**扫描录入**/
			startActivity(new Intent(this, QRCodeInputActivity.class));
		} else if (action.equals("MOBILE_EWM_FH_SM")) {
			/**发货物料扫描**/
			startActivity(new Intent(this, QRDeliveryMaterialActivity.class));
		} else if (action.equals("MOBILE_EWM_SH_SM")) {
			/**收货物料扫描**/
			startActivity(new Intent(this, QRReceiptMaterialActivity.class));
		} else if (action.equals("MOBILE_EWM_BX_SM")) {
			/**报修物料扫描**/
			startActivity(new Intent(this, QRGuaranteeMaterialActivity.class));
		}else if (action.equals("MOBILE_EWM_REG_SM")) {
			/**服务站出入库物料登记**/
			startActivity(new Intent(this, QRServiceStationMaterialInOutCheckActivity.class));
		}else if (action.equals("MOBILE_EWM_WDJQ_SM")) {
			/**网点机器部件扫描**/
			Intent intent_wdjq = new Intent(this, QRDotMachineCargoScanActivict.class);
			intent_wdjq.putExtra("Type", "1");
			startActivity(intent_wdjq);
		}else if (action.equals("MOBILE_EWM_WD_FH")) {
			/**网点发货扫描**/
			startActivity(new Intent(this, QRDotDeliveryCargoScanActivity.class));
		}else if (action.equals("MOBILE_EWM_ZS")) {
			/**扫描备件二维码查看明细信息**/
			startActivity(new Intent(this, QRTraceBackToActivity.class));
		}else if (action.equals("MOBILE_EWM_WDJQGH_SM_LX")) {
			/**网点机器部件更换扫描-离线**/
			startActivity(new Intent(this, QROfflineDotMachineReplaceActivity.class));
		}else if (action.equals("MOBILE_EWM_WDJQ_SM_LX")) {
			/**网点机器扫描-离线**/
			startActivity(new Intent(this, QROfflineDotMachineActivity.class));
		}
		else {
			T.showShort(mContext, "该功能正在建设中，敬请关注!");
		}

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
