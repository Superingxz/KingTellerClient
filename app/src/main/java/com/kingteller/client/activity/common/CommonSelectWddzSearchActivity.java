package com.kingteller.client.activity.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.workorder.IncreaseWddzActivity;
import com.kingteller.client.adapter.CommonSelectDataAdapter;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.workorder.WddzBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ConditionView;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * 网点信息查询
 * @author 
 *
 */
public class CommonSelectWddzSearchActivity extends Activity implements
		OnClickListener, IXListViewListener, OnItemClickListener {
	
	private ConditionView conditionView;
	private Button btn_add_item;
	private List<CommonSelectData> listData = new ArrayList<CommonSelectData>();
	private CommonSelectDataAdapter adapter;
	private int currentPage = 1;
	private String[] typedatas;
	private int type;
	private final static int KEY_TITLE=0;
	private final static int KEY_FIELD_NAME=1;
	private final static int KEY_URL=2;
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	private Context mContext;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_common_select_wddz_search);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		mContext = CommonSelectWddzSearchActivity.this;
		initUI();
	}


	private void initUI() {
		// TODO Auto-generated method stub
		type=getIntent().getIntExtra(CommonSelectDataActivity.TYPE, 0);
		typedatas=getResources().getStringArray(type);
		
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		
		title_text.setText(typedatas[KEY_TITLE]);

		conditionView = (ConditionView) findViewById(R.id.condition);

		btn_add_item = (Button) findViewById(R.id.btn_add_item);
		btn_add_item.setOnClickListener(this);
		
		adapter = new CommonSelectDataAdapter(this, listData,"wddz");
		getListviewObj().getListview().setAdapter(adapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		getListviewObj().setStatus(LoadingEnum.NODATA, "请输入查询条件");
	}
	
	private void getWddzs(){
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params =new AjaxParams();
		
		params.put(typedatas[KEY_FIELD_NAME],conditionView.getFieldValue());
		
		fh.post(KingTellerConfigUtils.CreateUrl(this,typedatas[KEY_URL]), params,
				new AjaxHttpCallBack<BasePageBean<WddzBean>>(this,
						new TypeToken<BasePageBean<WddzBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}

					@Override
					public void onDo(BasePageBean<WddzBean> basePageBean) {
						BasePageBean<CommonSelectData> pagedata=(BasePageBean) basePageBean;
						List<CommonSelectData> lists=new ArrayList<CommonSelectData>();
						for (int i = 0; i < basePageBean.getList().size(); i++) {
							CommonSelectData data=new CommonSelectData();
							data.setText(basePageBean.getList().get(i).getWdAddress());
							data.setValue(basePageBean.getList().get(i).getId());
							data.setObj(basePageBean.getList().get(i));
							lists.add(data);
						}
						pagedata.setList(lists);
						setData(pagedata);
					};

				});
	}
	
	private void setData(BasePageBean<CommonSelectData> pagedata){
		List<CommonSelectData> data = pagedata.getList();
		if (data.size() > 0) {
			if (pagedata.getCurrentPage() == 1)
				adapter.setLists(data);
			else
				adapter.addLists(data);

			getListviewObj().setStatus(LoadingEnum.LISTSHOW);
			
			if (pagedata.getCurrentPage() == pagedata.getTotalPage())
				getListviewObj().getListview().setLoadMoreEnabled(false);

		} else {
			getListviewObj().setStatus(LoadingEnum.NODATA,"没有数据");
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.layout_main_left_btn:
			this.finish();
			break;
		case R.id.btn_add_item:
			startActivityForResult(new Intent(this, IncreaseWddzActivity.class), KingTellerStaticConfig.SELECT_WDDZ);
			break;
		case R.id.layout_main_righttwo_btn:
			if(KingTellerJudgeUtils.isEmpty(conditionView.getFieldValue())){
				T.showShort(mContext, "查询条件不能为空!");
				return;
			}
			if(conditionView.getFieldValue().length() < 3){
				T.showShort(mContext, "查询条件必须在三个字符以上!");
				return;
			}
			getWddzs();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == KingTellerStaticConfig.SELECT_WDDZ
				&& resultCode == RESULT_OK) {
			CommonSelectData csa = (CommonSelectData) data
					.getSerializableExtra(CommonSelectDataActivity.DATA);
			Intent intent=new Intent();
			intent.putExtra(CommonSelectDataActivity.DATA, csa);
			setResult(RESULT_OK,intent);
			finish();
		}

	}
	

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.putExtra(CommonSelectDataActivity.DATA, (CommonSelectData) adapterView.getItemAtPosition(pos));
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void onRefresh() {
		currentPage = 1;
		getWddzs();
		getListviewObj().getListview().setLoadMoreEnabled(true);
	}

	@Override
	public void onLoadMore() {
		currentPage++;
		getWddzs();
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
