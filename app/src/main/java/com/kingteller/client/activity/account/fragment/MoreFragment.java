package com.kingteller.client.activity.account.fragment;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import cn.jpush.android.api.JPushInterface;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.more.AboutUsActivity;
import com.kingteller.client.activity.more.FeedBackActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.workorder.LoadNewDataBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.service.KingTellerService;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.utils.KingTellerUpdateUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxCallBack;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.modules.zxing.activity.CaptureActivity;

/**
 * 更多设置的Fragment
 * @author 王定波
 *
 */
public class MoreFragment extends Fragment implements OnClickListener {

	private View mContentView;
	private Context mContext;
	private TextView text_username, text_name, text_department, text_service_ip, 
					 text_port, text_push_status, app_version;
	private ToggleButton switch_lock_push, switch_zd_push, switch_sy_push;
	private ImageView icon_app_new, icon_database_new;
	
	private String[] maxTime;
	private SQLiteHelper helper;
	
	private User user;
	
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            String isSuccess = msg.getData().getString("isSuccess");
            if(msg.what == 1){
        		icon_database_new.setVisibility(View.GONE);
        		T.showShort(mContext, "数据更新成功!");
            }else if(msg.what == 2){
            	T.showShort(mContext, "数据更新失败!" + isSuccess);

            }
            KingTellerProgressUtils.closeProgress();
        }
    };
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.layout_more, null);
		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = this.getActivity();
		
		helper = new SQLiteHelper(mContext);
		user = User.getInfo(mContext);
		
		initUI();
		initData();
	}

	private void initUI() {

		mContentView.findViewById(R.id.aboutus_field).setOnClickListener(this);
		mContentView.findViewById(R.id.feedback_field).setOnClickListener(this);
		
		mContentView.findViewById(R.id.check_databases).setOnClickListener(this);

		mContentView.findViewById(R.id.update_field).setOnClickListener(this);
		mContentView.findViewById(R.id.button_loginout).setOnClickListener(this);
		switch_zd_push = (ToggleButton) mContentView.findViewById(R.id.switch_zd_push);
		switch_sy_push = (ToggleButton) mContentView.findViewById(R.id.switch_sy_push);
		switch_lock_push = (ToggleButton) mContentView.findViewById(R.id.switch_lock_push);

		text_username = (TextView) mContentView.findViewById(R.id.text_username);
		text_name = (TextView) mContentView.findViewById(R.id.text_name);
		text_department = (TextView) mContentView.findViewById(R.id.text_department);
		text_service_ip = (TextView) mContentView.findViewById(R.id.text_service_ip);
		text_port = (TextView) mContentView.findViewById(R.id.text_port);
		text_push_status = (TextView) mContentView.findViewById(R.id.text_push_status);
		
		app_version = (TextView) mContentView.findViewById(R.id.app_version);

		icon_app_new = (ImageView) mContentView.findViewById(R.id.icon_app_new);
		
		icon_database_new = (ImageView) mContentView.findViewById(R.id.icon_database_new);

		mContentView.findViewById(R.id.erweima_field).setOnClickListener(this);
	}
	
	private void initData() {
		text_username.setText(user.getUserName());
		text_name.setText(user.getName());
		text_department.setText(user.getOrgName());
		
		text_service_ip.setText(KingTellerConfigUtils.getIpDomain(mContext));
		text_port.setText(KingTellerConfigUtils.getPort(mContext));
		app_version.setText(KingTellerUpdateUtils.getCurrentVersionName(mContext));
		
		icon_app_new.setVisibility(KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_APP_NEW, false) ? View.VISIBLE : View.GONE);	
		text_push_status.setText(user.isPushReg() ? "成功" : "失败");
		
		switch_sy_push.setChecked(KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_SY, true));
		switch_zd_push.setChecked(KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_ZD, true));
		switch_lock_push.setChecked(KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_LOCK, false));
		KingTellerConfigUtils.setConfigMeta(KingTellerConfigUtils.KEY_PUSH_LOCK, switch_lock_push.isChecked());
		
		switch_sy_push.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				KingTellerConfigUtils.setConfigMeta(KingTellerConfigUtils.KEY_PUSH_SY, switch_sy_push.isChecked());
			}
		});

		switch_zd_push.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				KingTellerConfigUtils.setConfigMeta(KingTellerConfigUtils.KEY_PUSH_ZD, switch_zd_push.isChecked());
			}
		});

		switch_lock_push.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				KingTellerConfigUtils.setConfigMeta(KingTellerConfigUtils.KEY_PUSH_LOCK, switch_lock_push.isChecked());
			}
		});
	}


	@Override
	public void onResume() {
		super.onResume();
		/**
		user = User.getInfo(mContext);
		icon_app_new.setVisibility(KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_APP_NEW, false) ? View.VISIBLE : View.GONE);	
		text_push_status.setText(user.isPushReg() ? "成功" : "失败");
		
		switch_sy_push.setChecked(KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_SY, true));
		switch_zd_push.setChecked(KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_ZD, true));
		switch_lock_push.setChecked(KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_LOCK, true));
		*/
	}

	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.aboutus_field:
			startActivity(new Intent(mContext, AboutUsActivity.class));
			break;
		case R.id.update_field:
			KingTellerUpdateUtils.CheckUpdate(mContext, true);
			break;
		case R.id.feedback_field:
			startActivity(new Intent(mContext, FeedBackActivity.class));
			break;
		case R.id.erweima_field:
			Intent intent = new Intent(mContext, CaptureActivity.class);
			intent.putExtra("cameType", "more");
			startActivity(intent);
			break;
		case R.id.button_loginout:
			final NormalDialog dialog = new NormalDialog(mContext);
	        dialog.content("您确定要注销并退出吗？")
	                .style(NormalDialog.STYLE_TWO)
	                .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#D4D4D4"))
					.show();
	        dialog.setCanceledOnTouchOutside(false);
	        dialog.setCancelable(false);
	        dialog.setOnBtnClickL(
	                new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                    	 dialog.dismiss();
	                    }
	                },
	                new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                        dialog.dismiss();
	                        
	                        KingTellerApplication.getApplication().exit(true);
							JPushInterface.stopPush(mContext);
							mContext.stopService(new Intent(mContext, KingTellerService.class));
							//mContext.stopService(new Intent(mContext, KingTellerPushService.class));
							KingTellerApplication.finishActivity();
	                    }
	                });
			break;
		case R.id.check_databases:
			sjkgx(true);
			break;
		default:
			break;
		}

	}
	
	/**
	 * 获取本地数据库数据
	 */
	public void sjkgx(final boolean optType){
		
		maxTime = helper.getMaxTimeArray();
		
		Log.e("DBupdata","Time" + maxTime[0] + " " + maxTime[1] + " " + 
				maxTime[2] + " " + maxTime[3] + " " + maxTime[4] + " " + 
				maxTime[5] + " " + maxTime[6] + " " + maxTime[7]);

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("handleWdateStr", maxTime[0]);
		params.put("troubledateStr", maxTime[1]);
		params.put("workdateStr", maxTime[2]);
		params.put("wldateStr", maxTime[3]);
		params.put("treedateStr", maxTime[4]);
		params.put("tempdateStr", maxTime[5]);
		params.put("atmpartsStr", maxTime[6]);
		params.put("atmpartswlStr", maxTime[7]);

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.SjkgxUrl), params,
				new AjaxHttpCallBack<LoadNewDataBean>(mContext,
						new TypeToken<LoadNewDataBean>() {
						}.getType(), true) {
			
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在数据更新中...");
					}

					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
						Log.e("DBupdata","onError" + errorNo + ":" + strMsg);
					}

					@Override
					public void onDo(LoadNewDataBean basePageBean)  {
						int databaseSize = 0;
						databaseSize = basePageBean.getHandleSubByList().size() 
								+ basePageBean.getTroubleRemarkAllInfo().size()
								+ basePageBean.getWlInfoAll().size() 
								+ basePageBean.getTreeInfoAll().size() 
								+ basePageBean.getWorkTypeHandleSubInfo().size()
								+ basePageBean.getWlCompatibleTempList().size()
								+ basePageBean.getAtmPartsConfigList().size()
								+ basePageBean.getAtmPartsConfigWlList().size();
						
						if(databaseSize > 0){
							UpDataDBdatasesThread updataDb = new UpDataDBdatasesThread(basePageBean);
							updataDb.start();
						}else{
							icon_database_new.setVisibility(View.GONE);
							T.showShort(mContext, "暂无数据更新!");
						}
					};
				});
	}
	

	/**
	 * 注销session
	 */
	private void loginout() {
		KTHttpClient fh = new KTHttpClient(true);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.LoginoutUrl),
				new AjaxCallBack<String>() { });
	}
	
	/**
	 * 更新本地数据库线程UpDataDBdatasesThread
	 */
	class UpDataDBdatasesThread extends Thread {
        private LoadNewDataBean mData; //定义需要传值进来的参数

        public UpDataDBdatasesThread(LoadNewDataBean data){
                this.mData = data;
        }
        @Override 
        public void run() {
        	Message message = new Message();
        	Bundle bundleData = new Bundle();  
        	
        	try {
				helper.insertHandleSub(mData.getHandleSubByList(), maxTime[0]);
				helper.insertTroubleRemark(mData.getTroubleRemarkAllInfo(), maxTime[1]);
				helper.insertWorkTypeHandleSub(mData.getWorkTypeHandleSubInfo(), maxTime[2]);
				helper.insertSmWlInfo(mData.getWlInfoAll(), maxTime[3]);
				helper.insertSmWlTreeInfo(mData.getTreeInfoAll(), maxTime[4]);
				helper.insertSmWlCompatibleTempInfo(mData.getWlCompatibleTempList(), maxTime[5]);
				helper.insertAtmPartsConfigListInfo(mData.getAtmPartsConfigList(), maxTime[6]);
				helper.insertAtmPartsConfigWlInfo(mData.getAtmPartsConfigWlList(), maxTime[7]);
				
				message.what = 1;
				bundleData.putString("isSuccess", "success");
			} catch (SQLException e) {
				message.what = 2;
				bundleData.putString("isSuccess", "fail:" + e );
			} catch (UnsupportedEncodingException e) {
				message.what = 2;
				bundleData.putString("isSuccess", "fail:" + e );
			}
        	
            message.setData(bundleData);  
            handler.sendMessage(message);
    	}
    }

}
