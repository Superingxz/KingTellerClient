package com.kingteller.client.activity.account.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.attendance.AduitAttendanceActivity;
import com.kingteller.client.activity.attendance.WorkAttendanceActivity;
import com.kingteller.client.activity.common.CommonListActivity;
import com.kingteller.client.activity.common.CommonWebViewActivity;
import com.kingteller.client.activity.notice.NoticeListActivity;
import com.kingteller.client.activity.workorder.AduitReportActivity;
import com.kingteller.client.activity.workorder.WorkOrderActivity;
import com.kingteller.client.adapter.WaitDoAdapter;
import com.kingteller.client.bean.account.Notice;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.account.WaitDoBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerFileUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.framework.KingTellerDb;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.client.view.LoadingObjView;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
/**
 * 待办事项的Fragment
 * @author 王定波
 *
 */
public class WaitDoFragment extends Fragment implements OnRefreshListener,
		OnItemClickListener {

	private View mContentView;
	private Context mContext;
	private WaitDoAdapter waitDoAdpater;
	private ListView mListView;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private List<WaitDoBean> waitlist;
	private KingTellerDb finalDb;
	private LoadingObjView mLoadingObjView;
	private RelativeLayout mWaitdoNetError;
    private Handler handler;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.layout_waitdo, null);
		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = this.getActivity();
		
		initUI();
		initData();
		getWaitDosData();
	}

	private void initUI() {
		
		mLoadingObjView = new LoadingObjView(getView());
		mLoadingObjView.setOnClickLister(LoadingEnum.NODATA, res);
		mLoadingObjView.setOnClickLister(LoadingEnum.NETERROR, res);
		
		mListView = (ListView) mContentView.findViewById(R.id.common_swipe_listview);  
		mWaitdoNetError = (RelativeLayout) mContentView.findViewById(R.id.waitdo_net_error);  
		
		mSwipeRefreshLayout = (SwipeRefreshLayout) mContentView.findViewById(R.id.common_swipe_ly);  
		mSwipeRefreshLayout.setOnRefreshListener(this);
		
		//设置卷的背景颜色
		mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#2897f4"));
	    //设置卷内的颜色
	    mSwipeRefreshLayout.setColorSchemeResources(android.R.color.white);
	    
	    //mSwipeRefreshLayout.setBackgroundColor(context.getResources().getColor(R.color.common_mark_edittext_textcolor_red));
	    //mSwipeRefreshLayout.setBackgroundResource(android.R.color.holo_red_light);
	    
	    waitlist = new ArrayList<WaitDoBean>();
		waitDoAdpater = new WaitDoAdapter(mContext, waitlist);
		mListView.setAdapter(waitDoAdpater);
		mListView.setOnItemClickListener(this);
		
		
		mSwipeRefreshLayout.setVisibility(View.GONE);
		mLoadingObjView.setStatus(LoadingEnum.LOADING);
	}
	
	
	private OnClickListener res = new OnClickListener() {
		@Override
		public void onClick(View view) {
			
			//mSwipeRefreshLayout.setVisibility(View.GONE);
			//mLoadingObjView.setStatus(LoadingEnum.LOADING);
			
			//onRefresh();
			onClickRefresh();
		}
	};
	
	
	private void initData() {
		finalDb = KingTellerDb.create(mContext);
	}
	
	public void onClickRefresh() {
		mSwipeRefreshLayout.setVisibility(View.GONE);
		mLoadingObjView.setStatus(LoadingEnum.LOADING);
		getWaitDos();
	}

	@Override
	public void onRefresh() {
		getWaitDos();
	}
	
	public void getWaitDosData() {
		mSwipeRefreshLayout.setRefreshing(true);
		getWaitDos();
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		
		isNetAvaliable(KingTellerJudgeUtils.isNetAvaliable(mContext));//判断网络是否正常
	}
	
	 /**
      * 判断网络是否正常
      * @param study
      */
    public void isNetAvaliable(final boolean study) {
    	if(mWaitdoNetError == null){
    		return;
    	}
    	if(study == true && mWaitdoNetError.getVisibility() == View.GONE){
    		return;
    	}
    	if(study == false && mWaitdoNetError.getVisibility() == View.VISIBLE){
    		return;
    	}
    	
    	handler = new Handler(); 
    	new Thread(new Runnable() {  
            @Override  
            public void run() {  
                handler.post(new Runnable() {  
                    @Override  
                    public void run() {
	                	 if (study){
	                		 mWaitdoNetError.setVisibility(View.GONE);
	                		 getWaitDosData();
	                     }else{
	                    	 mWaitdoNetError.setVisibility(View.VISIBLE);
	                     }
                    }  
                });  
            }  
        }).start();  
    	 
    }
    
	private void getWaitDos() {
				
		User user = User.getInfo(mContext);
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("userAccount", user.getUserName());
		params.put("orgId", user.getOrgId());
		params.put("roleCode", user.getRoleCode());
		params.put("userId", user.getUserId());
		
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WaitDoUrl), params,
				new AjaxHttpCallBack<List<WaitDoBean>>(mContext,
						new TypeToken<List<WaitDoBean>>() {
						}.getType(), true) {

					@Override
					public void onSuccess(String response) {
						super.onSuccess(response);
						try {
							KingTellerFileUtils.writeFile("waitdo", response);
						} catch (Exception e) {
							
						}
					}

					@Override
					public void onError(int errorNo, String strMsg) {
							super.onError(errorNo, strMsg);
							mSwipeRefreshLayout.setRefreshing(false); 
							
							//mSwipeRefreshLayout.setVisibility(View.GONE);
							if(!mLoadingObjView.getStatus().equals(LoadingEnum.LISTSHOW)) {
								mLoadingObjView.setStatus(LoadingEnum.NETERROR);
							}
					}

					@Override
					public void onDo(List<WaitDoBean> data) {
						waitlist = data;
						waitDoAdpater.setLists(data);
						mSwipeRefreshLayout.setRefreshing(false);  
						if (data.size() > 0) {
							
							mSwipeRefreshLayout.setVisibility(View.VISIBLE);
							mLoadingObjView.setStatus(LoadingEnum.LISTSHOW);
							
							KingTellerConfigUtils.MainUpdateStatus(mContext,
									KingTellerStaticConfig.MAIN_WAITDOT,
									false, true);
						} else {
							
							mSwipeRefreshLayout.setVisibility(View.GONE);
							mLoadingObjView.setStatus(LoadingEnum.NODATA);
							
							KingTellerConfigUtils.MainUpdateStatus(mContext,
									KingTellerStaticConfig.MAIN_WAITDOT,
									false, false);
						}
					};

				});

	}

	
	/**
	 * 设置是否已经读了
	 * @param data
	 */
	private void setRead(WaitDoBean data) {
		Notice nd = new Notice();
		nd.setId(data.getOnlyId());
		Notice datatmp = finalDb.findById(nd.getId(), Notice.class);
		if (datatmp == null) {
			finalDb.save(nd);
			KingTellerStaticConfig.WAITDO_BOTTOM_CORNER = 0;
			waitDoAdpater.notifyDataSetChanged();
			
			KingTellerConfigUtils.MainUpdateStatus(mContext,
					KingTellerStaticConfig.MAIN_WAITDOT,
					false, true);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> view, View arg1, int postion, long arg3) {
		WaitDoBean data = (WaitDoBean) view.getAdapter().getItem(postion);
		if (data != null) {
			// 点击添加到数据库
			setRead(data);
			//data.setFlowCode("");
			if (!KingTellerJudgeUtils.isEmpty(data.getFlowCode())) {
				// 进入销售下单
				if (data.getFlowCode().equals("saleBillFlowNoContract") || data.getFlowCode().equals("saleBillFlowContracted")) {

					if (!KingTellerJudgeUtils.isEmpty(data.getToDoTaskUrl())) {
						Intent intent = new Intent(mContext,CommonWebViewActivity.class);
						String url = data.getToDoTaskUrl().substring(0,data.getToDoTaskUrl().indexOf("?"));
						intent.putExtra("id", data.getBusId());
						intent.putExtra("flowCode", data.getFlowCode());

						intent.putExtra(CommonWebViewActivity.URL,KingTellerConfigUtils.CreateLocalUrl(mContext, url));
						intent.putExtra(CommonWebViewActivity.TITLE,data.getTitle());
						
						if (url.indexOf("my_sales_view.html") != -1) {
							intent.putExtra(CommonWebViewActivity.TITLE,"我的销售单");
						} else {
							intent.putExtra(CommonWebViewActivity.TITLE,"销售单审核");
						}
						startActivity(intent);
					} else if (!KingTellerJudgeUtils.isEmpty(data.getBeanName())) {

						Intent intent = new Intent(mContext,CommonListActivity.class);
						intent.putExtra(CommonListActivity.TITLE,data.getFlowTitle());
						intent.putExtra("data", data);
						startActivity(intent);
					} else {
						T.showShort(mContext, "该功能正在建设中，敬请关注!");
					}

				}
				// 进入 我的工单  界面
				else if (data.getFlowCode().equals("logisticsFlow") || data.getFlowCode().equals("otherMatter") || 
							data.getFlowCode().equals("assignOrder")) {
					startActivity(new Intent(mContext, WorkOrderActivity.class));
				}
				//报告审核系统
				else if (data.getFlowCode().indexOf("ReportFlow") != -1) {
					if(data.getActCode().equals("act0")){
						// 进入 我的工单  界面 
						startActivity(new Intent(mContext, WorkOrderActivity.class));
					}else if(data.getActCode().equals("act1")){
						// 进入 报告审核  界面
						startActivity(new Intent(mContext, AduitReportActivity.class));
					}
				}
				//考勤系统
				else if (data.getFlowCode().equals("kqmgrOvertimeFlow") || data.getFlowCode().equals("kqmgrLeaveFlow") || 
						data.getFlowCode().equals("kqmgrSickleaveFlow") || data.getFlowCode().equals("kqmgrAbsenteeismFlow") || 
						data.getFlowCode().equals("kqmgrFILLFlow") || data.getFlowCode().equals("kqmgrTravelFlow")) {
					if(data.getActCode().contains("_start")){
						// 进入 我的考勤  界面 
						startActivity(new Intent(mContext, WorkAttendanceActivity.class));
					}else{
						// 进入 考勤审批  界面 
						startActivity(new Intent(mContext, AduitAttendanceActivity.class));
					}
				}
			} else {
				// flowcode为空的情况
				if (!KingTellerJudgeUtils.isEmpty(data.getBusId())) {
					//进入工单列表
					startActivity(new Intent(mContext, WorkOrderActivity.class));

				} else if (!KingTellerJudgeUtils.isEmpty(data.getToDoTaskUrl())) {
					Intent intent = new Intent(mContext, 
							CommonWebViewActivity.class);
					intent.putExtra(
							CommonWebViewActivity.URL,
							KingTellerConfigUtils.CreateUrl(mContext, data.getToDoTaskUrl()));
					intent.putExtra(CommonWebViewActivity.TITLE,
							data.getFlowTitle());
					startActivity(intent);
				} else if (!KingTellerJudgeUtils.isEmpty(data.getBeanName())) {
					if(data.getFlag().equals("notice")){ 
						// 公告信息
						Intent intent = new Intent(mContext, NoticeListActivity.class);
						intent.putExtra("data", data);
						intent.putExtra("readed", "0");
						startActivity(intent);
						
					}else{
						// 合起来的工单
						Intent intent = new Intent(mContext, CommonListActivity.class);
						intent.putExtra("data", data);
						intent.putExtra(CommonListActivity.TITLE, data.getFlowTitle());
						startActivity(intent);
					}
					
				} else{
					T.showShort(mContext, "该功能正在建设中，敬请关注!");
				}

			}
		}
	}
	
	
}
