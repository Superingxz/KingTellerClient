package com.kingteller.client.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.jpush.android.api.JPushInterface;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.welcome.WelComeActivity;
import com.kingteller.client.bean.account.LoginBean;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.CrashHandler;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerUpdateUtils;
import com.kingteller.client.view.dialog.LoginSetUpDialog;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.edittext.LoginEditTextView;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.framework.utils.EncroptionUtils;

/**
 * 登陆Activity
 * @author 王定波
 *
 */
public class LoginActivity extends Activity implements OnClickListener {

	private LoginEditTextView username;
	private LoginEditTextView password;
	private Button btn_login_in, btn_login_setup;
	private String etUserNamestr;
	private String etPasswordstr;
	public static final String ISFROM = "is_from";
	private Context mContext;
	public boolean isfrom = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (KingTellerApplication.getApplication().IsLogin()) {
			startActivity(new Intent(LoginActivity.this, WelComeActivity.class));
			
			/*记录登陆信息 --- 调试代码*/
			CrashHandler.saveStaffLogInTextFile("用户已经登陆，再次登陆成功！", User.getInfo(LoginActivity.this),
					KingTellerConfigUtils.getIpDomain(LoginActivity.this),
					KingTellerConfigUtils.getPort(LoginActivity.this), null, 0);

			finish();
			return;
		}
		
		setContentView(R.layout.layout_login);
		KingTellerApplication.addActivity(this);
		
		mContext = LoginActivity.this;
		
		initUI();
		initData();
	}

	private void initUI() {
		username = (LoginEditTextView) findViewById(R.id.login_username);
		password = (LoginEditTextView) findViewById(R.id.login_password);
		
		btn_login_in = (Button) findViewById(R.id.btn_login_in);
		btn_login_setup = (Button) findViewById(R.id.btn_login_setup);
		
		btn_login_in.setOnClickListener(this);
		btn_login_setup.setOnClickListener(this);
	}
	
	private void initData() {
		User user = User.getInfo(this);
		username.setText(user.getUserName());
		password.setText(user.getPassword());
	}

	

	@Override
	public void onPause() {
		super.onPause();
		 JPushInterface.onPause(this);

	}
	
	@Override
	public void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_login_in:
			login();
			break;
		case R.id.btn_login_setup:
			new LoginSetUpDialog(mContext, R.style.Login_dialog).show();// 设置服务器
			break;
		default:
			break;
		}
	}

	/**
	 * 登录执行
	 */
	private void login() {
		etUserNamestr = username.getText().toString().trim();
		if (KingTellerJudgeUtils.isEmpty(etUserNamestr)) {
			T.showShort(mContext, "用户不能为空!");
			return;
		}
		etPasswordstr = password.getText().toString().trim();
		if (KingTellerJudgeUtils.isEmpty(etPasswordstr)) {
			T.showShort(mContext, "密码不能为空!");
			return;
		}
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "网络异常，请稍后重试!");
			return;
		}

		AjaxParams params = new AjaxParams();
		params.put("userAccount", etUserNamestr);
		params.put("loginPassword", EncroptionUtils.EncryptSHA(etPasswordstr));
		params.put("iemi", KingTellerApplication.getDeviceToken());
		params.put("versionName", KingTellerUpdateUtils.getCurrentVersionName(mContext));
		params.put("appType", "android");
		
		KTHttpClient fh = new KTHttpClient(false);
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.LoginUrl), params,
				new AjaxHttpCallBack<LoginBean>(this, false) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在登录中...");
					}

					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(LoginBean data) {
						if (data.getLoginError() == 1) {
							// 保存用户信息
							data.setUsername(etUserNamestr);
							data.setPassword(etPasswordstr);
							data.setVersionName(KingTellerUpdateUtils.getCurrentVersionName(LoginActivity.this));
							User.SaveInfo(LoginActivity.this, data);
							
							// 保存回话cookie
							KingTellerApplication.getApplication()
									.setAccessToken(KingTellerConfigUtils.getAuthCookie(onGetHeader("Set-Cookie")));

							startActivity(new Intent(LoginActivity.this, WelComeActivity.class));
							
							/*记录登陆信息 --- 调试代码*/
							CrashHandler.saveStaffLogInTextFile("首次登陆成功!", User.getInfo(LoginActivity.this),
									KingTellerConfigUtils.getIpDomain(LoginActivity.this),
									KingTellerConfigUtils.getPort(LoginActivity.this), null, 0);

							finish();

						} else {
							T.showShort(mContext, data.getLoginState());
						}
					};

				});

	}
	
}
