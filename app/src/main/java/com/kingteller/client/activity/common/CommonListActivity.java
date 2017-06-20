package com.kingteller.client.activity.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.WaitDoAdapter;
import com.kingteller.client.bean.account.Notice;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.account.WaitDoBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.framework.KingTellerDb;
import com.kingteller.framework.http.AjaxParams;

/**
 * 通用数据列表
 * @author 王定波
 *
 */
public class CommonListActivity extends Activity implements
		IXListViewListener, OnItemClickListener {

	private WaitDoBean waitdodata;
	private WaitDoAdapter adpater;
	private int nowpage = 1;
	private String nowaction = XListView.ACTION_UP;
	private User user;
	private KingTellerDb finalDb;
	public static final String TITLE = "title";
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_alllist_view);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		initUI();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		waitdodata = (WaitDoBean) getIntent().getSerializableExtra("data");
		user = User.getInfo(this);
		finalDb = KingTellerDb.create(this);
		getNotices();

	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);


		String title = getIntent().getStringExtra(TITLE);

		if (KingTellerJudgeUtils.isEmpty(title))
			title = "公告";
		
		title_text.setText(title);
		title_left_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		adpater = new WaitDoAdapter(this, new ArrayList<WaitDoBean>());
		getListviewObj().getListview().setAdapter(adpater);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);

	}
	
	/**
	 * 设置是否已经读了
	 * 
	 * @param data
	 */
	private void setRead(WaitDoBean data) {
		Notice nd = new Notice();
		nd.setId(data.getOnlyId());
		Notice datatmp = finalDb.findById(nd.getId(), Notice.class);
		if (datatmp == null) {
			finalDb.save(nd);
			adpater.notifyDataSetChanged();
		}
	}

	private void getNotices() {
		// TODO Auto-generated method stub

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		if (!KingTellerJudgeUtils.isEmpty(waitdodata.getBeanName())) {
			String[] beanName = waitdodata.getBeanName().split("\\.");
			params.put("beanName", beanName[0]);
			params.put("methodName", beanName[1]);
		}
		params.put("flowCode", waitdodata.getFlowCode());
		params.put("curUserCode", waitdodata.getCurUserCode());

		params.put("condition", "");
		params.put("page", String.valueOf(nowpage));
		params.put("rows", String.valueOf(10));// 返回的
		params.put("readed", "0");
		params.put("flag", waitdodata.getFlag());

		// Notice
		if (waitdodata.getFlag().equals("notice")) {
			params.put("orgId", user.getOrgId());
			params.put("roleCode", user.getRoleCode());
			params.put("userId", user.getUserId());
		}

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetCommonDataListUrl),
				params, new AjaxHttpCallBack<List<WaitDoBean>>(this,
						new TypeToken<List<WaitDoBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();

					}

					@Override
					public void onDo(List<WaitDoBean> data) {
						if (data.size() > 0) {
							if (nowaction.equals(XListView.ACTION_UP))
								adpater.setLists(data);
							else
								adpater.addLists(data);

							if (nowpage == 1)
								getListviewObj()
										.setStatus(LoadingEnum.LISTSHOW);

							if (data.size() < 10)
								getListviewObj().getListview()
										.setLoadMoreEnabled(false);

						} else if (nowpage == 1)
							getListviewObj().setStatus(LoadingEnum.NODATA);
						else {
							getListviewObj().getListview().setLoadMoreEnabled(
									false);
						}

					};

				});

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		nowaction = XListView.ACTION_UP;
		nowpage = 1;
		getNotices();
		getListviewObj().getListview().setLoadMoreEnabled(true);

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		nowaction = XListView.ACTION_DOWN;
		nowpage++;
		getNotices();

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int postion,
			long arg3) {
		// TODO Auto-generated method stub
		WaitDoBean data = (WaitDoBean) adapterView.getAdapter()
				.getItem(postion);
		if (data != null) {
			setRead(data);
			Intent intent = new Intent(this, CommonWebViewActivity.class);
			intent.putExtra(CommonWebViewActivity.URL, String.format(
					KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CommonDataWebUrl),
					data.getBusId(), user.getUserId()));

			intent.putExtra(CommonWebViewActivity.TITLE, data.getTitle());
			startActivity(intent);

		}

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
