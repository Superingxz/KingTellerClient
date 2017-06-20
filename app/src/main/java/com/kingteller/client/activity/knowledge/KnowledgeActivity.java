package com.kingteller.client.activity.knowledge;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.KnowledgeAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.knowledge.KnowledgeBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.ConditionView;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.framework.condition.Condition;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.framework.utils.Logger;
/**
 * 错误代码搜索界面处理类
 * @author tom
 *
 */
public class KnowledgeActivity extends Activity implements OnClickListener,IXListViewListener,
OnItemClickListener{
	
	private ConditionView errorCode;//错误代码
	private ConditionView component;//故障部件
	private List<KnowledgeBean> knowledgeData = new ArrayList<KnowledgeBean>();
	private KnowledgeAdapter adapter;
	private int currentPage = 1;
	private ListViewObj listviewObj;
	
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	
	private boolean isFirst = true;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_error_code_search);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		
		initUI();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("知识库");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		
		
		errorCode = (ConditionView) findViewById(R.id.errorCode);
		component = (ConditionView) findViewById(R.id.component);
		
		adapter = new KnowledgeAdapter(this,knowledgeData);
		getListviewObj().getListview().setAdapter(adapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		getListviewObj().setStatus(LoadingEnum.NODATA,"请输入条件进行搜索");
		
	}
	
	private void getKnowledgeData() {
		KTHttpClient fh = new KTHttpClient(true);
		LinearLayout conditionLayout = (LinearLayout)findViewById(R.id.knowledge_condition);
		Condition cond = ConditionUtils.getCondition(this, conditionLayout);
		AjaxParams params = ConditionUtils.getAjaxParams(cond, currentPage);
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.KnowledgeUrl), params,
				new AjaxHttpCallBack<BasePageBean<KnowledgeBean>>(this,
						new TypeToken<BasePageBean<KnowledgeBean>>() {
						}.getType(), true) {
			
					@Override
					public void onStart() {
						if(currentPage == 1 && isFirst == true){
							getListviewObj().setStatus(LoadingEnum.LOADING);
						}
					}
			
					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}

					@Override
					public void onDo(BasePageBean<KnowledgeBean> basePageBean) {
						List<KnowledgeBean> data = basePageBean.getList();
						if (data.size() > 0) {
							if (basePageBean.getCurrentPage() == 1){
								adapter.setLists(data);
								isFirst = false;
								getListviewObj().setStatus(LoadingEnum.LISTSHOW);
							}else{
								adapter.addLists(data);
							}
							
							//如果当前页等于总页数，则不需要先是load more按钮
							if (basePageBean.getCurrentPage() == basePageBean.getTotalPage()){
								getListviewObj().getListview().setLoadMoreEnabled(false);
							}
						} else{
							getListviewObj().setStatus(LoadingEnum.NODATA,"没有数据");
						}
						
					};

				});
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()){
			case R.id.layout_main_left_btn:
				this.finish();
				break;
			case R.id.layout_main_righttwo_btn:
				isFirst = true;
				onRefresh();
				break;
		}
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onRefresh() {
		currentPage = 1;
		getKnowledgeData();
		getListviewObj().getListview().setLoadMoreEnabled(true);
	}


	@Override
	public void onLoadMore() {
		currentPage++;
		getKnowledgeData();
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
