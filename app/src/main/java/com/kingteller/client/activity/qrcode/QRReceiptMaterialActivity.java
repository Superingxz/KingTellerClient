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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.QRDeliveryReceiptAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.qrcode.QRReceiptBean;
import com.kingteller.client.bean.qrcode.QRSearchCondition;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.ATMSearchConditionDialog;
import com.kingteller.client.view.dialog.SearchGeneralDialog;
import com.kingteller.client.view.dialog.entity.SearchGeneraItem;
import com.kingteller.client.view.dialog.listener.OnBtnSearchClickL;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * 收货物料扫描
 * @author Administrator
 */
public class QRReceiptMaterialActivity extends Activity implements
	OnClickListener, OnItemClickListener, IXListViewListener {
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	
	private QRDeliveryReceiptAdapter adpater;
	private ListViewObj listviewObj;
	private List<QRReceiptBean> mReceiptBeanList = new ArrayList<QRReceiptBean>();
	private int mPage = 1;
	private User user;
	private Boolean isFirst = true;
	private int onItemNum = 0;
	
	private SearchGeneraItem qrShwl_search = new SearchGeneraItem();
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_alllist_view);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		mContext = QRReceiptMaterialActivity.this;
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		
		adpater = new QRDeliveryReceiptAdapter(mContext, null, new ArrayList<QRReceiptBean>(), null, null, null, null, 2);
		
		getListviewObj().getListview().setAdapter(adpater);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		
		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);
	}
	
	private void initData() {
		title_text.setText("收货物料扫描");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		
		user = User.getInfo(mContext);
		onRefresh();
		isFirst = false;
	}

	//下拉刷新执行
	@Override
	public void onRefresh() {
		mPage = 1;
		getQRReceiptData();
	}
	
	@Override
	public void onLoadMore() {
		mPage++;
		getQRReceiptData();
		
	}
	
	@SuppressWarnings("unused")
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		QRReceiptBean receiptbean = (QRReceiptBean) parent.getAdapter().getItem(position);
		onItemNum = position - 1;
		Intent intent = new Intent(this, QRCargoScanActivity.class);
		intent.putExtra("ReceiptBean", (QRReceiptBean) receiptbean); 
		intent.putExtra("Type", "2");
		startActivityForResult(intent, KingTellerStaticConfig.QRCODE_SHSM_BACK); 
		
	}
	
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case KingTellerStaticConfig.QRCODE_SHSM_BACK:
			if (resultCode == RESULT_OK) {
				String mSH_wyztms = (String) data.getSerializableExtra("mSH_wyztms");
				mReceiptBeanList.get(onItemNum).setUnscanBarcodeCount(mSH_wyztms);
				adpater.setListsreceipt(mReceiptBeanList);
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
		case R.id.layout_main_righttwo_btn:
			SearchGeneraItem str = new SearchGeneraItem();
			str = qrShwl_search;

			final SearchGeneralDialog searchgeneral_dialog = new SearchGeneralDialog(mContext, R.style.Login_dialog, 7, new Gson().toJson(str));
			searchgeneral_dialog.setOnBtnSearchClickL(
				new OnBtnSearchClickL() {
	                @Override
	                public void onBtnClick(String ss) {
	                	searchgeneral_dialog.dismiss();
	                	qrShwl_search = KingTellerJsonUtils.getPerson(ss, SearchGeneraItem.class);
	                	mPage = 1;
	                	isFirst = true;
	                	getQRReceiptData();
	                }
	            },
	            new OnBtnSearchClickL() {
	                @Override
	                public void onBtnClick(String qx) {
	                	searchgeneral_dialog.dismiss();
	                }
	            });
			
			searchgeneral_dialog.show();
			
			break;
		default:
			break;
		}
	}
	
	private OnClickListener res = new OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			getListviewObj().setStatus(LoadingEnum.LOADING);
			onRefresh();
		}
	};
	
	public void getQRReceiptData() {

		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orgId", user.getOrgId());//服务站id
		String NewPage = Integer.toString(mPage); 
		params.put("page", NewPage);//页数
		
		if(!KingTellerJudgeUtils.isEmpty(qrShwl_search.getQrShwldh())){
			params.put("billNo", qrShwl_search.getQrShwldh());//收货单编号 
		}
	
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SHdLists),params, 
				new AjaxHttpCallBack<List<QRReceiptBean>>(this,
						new TypeToken<List<QRReceiptBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}
					
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						if (mPage == 1 && isFirst == true)
							getListviewObj().setStatus(LoadingEnum.LOADING);
					}
					@Override
					public void onError(int errorNo, String strMsg) {
						// TODO Auto-generated method stub
						super.onError(errorNo, strMsg);
						
						Log.e("ATMsearch","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(List<QRReceiptBean> data) {
						if (data.size() > 0 && data.get(0).getCode().equals("")) {
							if(mPage == 1){
								mReceiptBeanList = data;
								adpater.setListsreceipt(mReceiptBeanList);
								getListviewObj().setStatus(LoadingEnum.LISTSHOW);
								getListviewObj().getListview().setLoadMoreEnabled(true);
							}else{
								adpater.addListsreceipt(data); 
							}
						}else {
							if(mPage == 1){
								getListviewObj().setStatus(LoadingEnum.NODATA,getString(R.string.no_data_try));
								isFirst = true;
							}else{
								getListviewObj().getListview().setLoadMoreEnabled(false);
							}
							T.showShort(mContext, data.get(0).getCode());
						}
					
					};
				});
	}
	

	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
