package com.kingteller.client.activity.account.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.kingteller.R;
import com.kingteller.client.activity.attendance.AduitAttendanceActivity;
import com.kingteller.client.activity.attendance.WorkAttendanceActivity;
import com.kingteller.client.activity.base.BaseWebActivity;
import com.kingteller.client.activity.common.CommonWebViewActivity;
import com.kingteller.client.activity.knowledge.KnowledgeActivity;
import com.kingteller.client.activity.logisticmonitor.WLJKMainActivity;
import com.kingteller.client.activity.map.MapMainActivity;
import com.kingteller.client.activity.notice.NoticeListActivity;
import com.kingteller.client.activity.offlineupload.OfflineUploadActivity;
import com.kingteller.client.activity.onlinelearning.OnlineMainActivity;
import com.kingteller.client.activity.pxkhsj.DoPxkhsjListActivity;
import com.kingteller.client.activity.qrcode.QRMainActivity;
import com.kingteller.client.activity.workorder.AduitReportActivity;
import com.kingteller.client.activity.workorder.SendOrderListActivity;
import com.kingteller.client.activity.workorder.WorkOrderActivity;
import com.kingteller.client.adapter.FunctionAdapter;
import com.kingteller.client.bean.account.FunctionBean;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.account.WaitDoBean;
import com.kingteller.client.bean.db.QRDotMachine;
import com.kingteller.client.bean.db.QRDotMachineReplace;
import com.kingteller.client.bean.db.QROfflineDotMachine;
import com.kingteller.client.bean.db.QROfflineDotMachineReplace;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * 功能Fragment
 * @author 王定波
 *
 */
public class FunctionFragment extends Fragment implements OnItemClickListener {

	private View mContentView;
	private Context mContext;
	private GridView gridview;
	private FunctionAdapter adapter;

	private Class<?>[] mOfflineClass = new Class<?>[] {
			QRDotMachine.class, 				//设备录入 			缓存表
			QRDotMachineReplace.class, 			//设备部件更换		缓存表
			QROfflineDotMachine.class,			//设备录入-离线		缓存表
			QROfflineDotMachineReplace.class}; 	//设备部件更换-离线	缓存表
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.layout_function, null);
		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = this.getActivity();
		
		initUI();
		initData();
	}

	private void initData() {
		
	}

	private void initUI() {
		gridview = (GridView) mContentView.findViewById(R.id.function_gridview);
		adapter = new FunctionAdapter(mContext, User.getInfo(mContext).getRight(), FunctionAdapter.MAINMENU);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);
	}

	
	
	@Override
	public void onResume() {
		Log.e("FunctionFragment", "onResume");
		super.onResume();
		//清除 底部角标
		KingTellerStaticConfig.FUNCTION_BOTTOM_CORNER = 0;
		
		//获取缓存数据数量
		KingTellerStaticConfig.FUNCTION_OFFLINE_BOTTOM_CORNER = KingTellerDbUtils.getAllCacheNum(mContext, mOfflineClass);
		
		//重新计算角标
		KingTellerStaticConfig.FUNCTION_BOTTOM_CORNER += KingTellerStaticConfig.FUNCTION_OFFLINE_BOTTOM_CORNER;
		
		adapter.notifyDataSetChanged();
		
	}
	
	/**
	 * 重新获取 设置
	 */
	public void setFunctionResetSetting(){
		
	}
	
	
	
	/**
	 * 获取用户权限
	 */
	private void getRights() {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("userAccount", User.getInfo(mContext).getUserName());

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.GetRightUrl), params,
				new AjaxHttpCallBack<FunctionBean>(mContext, true) {

					@Override
					public void onDo(FunctionBean data) {
						User user = User.getInfo(mContext);
						user.setRight(data.getContent());
						User.SaveInfo(mContext, user);
						adapter.setData(data.getContent());
						adapter.notifyDataSetChanged();
					}

				});

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int postion, long arg3) {
		User user = User.getInfo(mContext);
		String action = (String) adapterView.getAdapter().getItem(postion); //获取权限简称
		String weburl = "";
		
		/**自制**/
		if(action.equals("MOBILE_RWD")){							//我的工单
			startActivity(new Intent(mContext, WorkOrderActivity.class));
		}else if(action.equals("MOBILE_ASSIGN")){					//我的派单
			startActivity(new Intent(mContext, SendOrderListActivity.class));
		}else if(action.equals("MOBILE_SPBG")){						//报告审核
			startActivity(new Intent(mContext, AduitReportActivity.class));
		}else if(action.equals("MOBILE_ZSK")){						//知识库
			startActivity(new Intent(mContext, KnowledgeActivity.class));
		}else if (action.equals("MOBILE_MAP")) {					//地图功能
			startActivity(new Intent(mContext, MapMainActivity.class));
		}else if(action.equals("WLJK_MOBILE")) {					//物流监控
			startActivity(new Intent(mContext, WLJKMainActivity.class));
		}else if(action.equals("MOBILE_PXKHSJ")){					//专项考核
			startActivity(new Intent(mContext, DoPxkhsjListActivity.class));
		}else if(action.equals("MOBILE_ONLINE_LEARNING")){			//在线学习
			startActivity(new Intent(mContext, OnlineMainActivity.class));
		}else if(action.equals("MOBILE_EWM")){						//二维码扫描
			startActivity(new Intent(mContext, QRMainActivity.class));
		}else if(action.equals("MOBILE_KQ_FOR_MY")){				//我的考勤
			startActivity(new Intent(mContext, WorkAttendanceActivity.class));
		}else if(action.equals("MOBILE_KQ_AUDIT")){					//考勤审核
			startActivity(new Intent(mContext, AduitAttendanceActivity.class));
		}else if (action.equals("MOBILE_CACHE_DATA")) {				//缓存数据
			startActivity(new Intent(mContext, OfflineUploadActivity.class));
		}else if (action.equals("notice")) {						//公告信息
			WaitDoBean data = new WaitDoBean();
			data.setFlag("notice");
			data.setBeanName("noticeService.queryMobileList");
			data.setFlowCode("");;
			data.setCurUserCode("");
			Intent intent = new Intent(mContext, NoticeListActivity.class);
			intent.putExtra("data", data);
			intent.putExtra("readed", "2");
			startActivity(intent);
		}
		/**网络**/
		else if(action.equals("MOBILE_JNKH")){						//技能考核
			weburl = KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebJnkhUrl);
		} else if (action.equals("saleshen")) {						//销售单审核
			weburl = KingTellerConfigUtils.CreateLocalUrl(mContext, KingTellerUrlConfig.WebShUrl);
		} else if (action.equals("mysale")) {						//我的销售单
			weburl = KingTellerConfigUtils.CreateLocalUrl(mContext, KingTellerUrlConfig.WebMysaleUrl);
		} else if (action.equals("MOBILE_PROJECTSTATISTIC")) {		//项目管理统计
			weburl = KingTellerConfigUtils.CreateLocalUrl(mContext, KingTellerUrlConfig.ProjectUrl);
		} else if (action.equals("MOBILE_PROJECTORDERAUDIT")) {		//项目工单审批
			weburl = KingTellerConfigUtils.CreateLocalUrl(mContext, KingTellerUrlConfig.ProjectAuditUrl);
		}else if (action.equals("MOBILE_PROJECTDEALORDER")) {		//项目工单销单
			weburl = KingTellerConfigUtils.CreateLocalUrl(mContext, KingTellerUrlConfig.ProjectDEALORDERUrl);
		}else {
			T.showShort(mContext, "该功能正在建设中，敬请关注!");
		}
		
		if (!KingTellerJudgeUtils.isEmpty(weburl)) {
			Intent intent = new Intent(mContext, BaseWebActivity.class);
			intent.putExtra(CommonWebViewActivity.TITLE, 
					getString(getResources().getIdentifier(action, "string", mContext.getPackageName())));
			intent.putExtra(CommonWebViewActivity.URL, 
					String.format(weburl, user.getUserName(), user.getPassword()));
			startActivity(intent);
		}

	}
}
