package com.kingteller.client.activity.common;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsInterface;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

/**
 * 通用webview
 * 
 * @author 王定波
 * 
 */

public class CommonWebViewActivity extends Activity implements OnClickListener {
	public static final String FROM_NOW = "from_now";
	//private static final String TAG = CommonWebViewActivity.class.getSimpleName();
	public static final String TITLE = "title";
	public static final String URL = "url";
	public static int state = 0;
	private ConnectivityManager cm;
	private boolean fromNow = false;
	int progress = 0;
	private String title;
	private String url;
	private WebView webView;
	private TextView title_text;
	private Button title_left_btn, title_right_btn;

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			KingTellerProgressUtils.closeProgress();
			webView.destroy();
			try {
				new KTAlertDialog.Builder(CommonWebViewActivity.this)
						.setTitle("连接失败")
						.setMessage("抱歉，您的网络不稳定！")
						.setPositiveButton("确定",
								new KTAlertDialog.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymous2DialogInterface,
											int pos) {
										finish();
									}
								}).create().show();
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.layout_webview);
		KingTellerApplication.addActivity(this);
		
		Bundle bundles = getIntent().getExtras();
		title = "正在读取中";
		if (bundles != null) {
			fromNow = bundles.containsKey(FROM_NOW);
			url = bundles.getString(URL);
			if (!fromNow) {
				title = bundles.getString(TITLE);
			}
		}
		init();
		initData();
	}
	
	private void init() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		
		title_text.setText(title);
		title_left_btn.setOnClickListener(this);
		
		if (fromNow)
			title_text.getLayoutParams().width = 260;
	}

	private void checkNetConect() {
		cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (KingTellerJudgeUtils.isAirplaneMode(CommonWebViewActivity.this))	
			new KTAlertDialog.Builder(this)
					.setTitle("连接失败")
					.setMessage("您的手机正处于飞行模式！")
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface, int pos) {
									finish();
								}
							}).create().show();
		else if (cm.getActiveNetworkInfo() != null
				&& !cm.getActiveNetworkInfo().isAvailable())
			new KTAlertDialog.Builder(this)
					.setTitle("连接失败")
					.setMessage("抱歉，您的网络不稳定！")
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface, int pos) {
									finish();
								}
							}).create().show();
	}


	@SuppressLint("SetJavaScriptEnabled")
	private void initData() {
		checkNetConect();
		webView = (WebView) findViewById(R.id.wv_question);
		WebSettings setting = webView.getSettings();
		setting.setDefaultTextEncodingName("utf-8");
		setting.setJavaScriptEnabled(true);

		KingTellerJsInterface js = new KingTellerJsInterface(this);
		String id = getIntent().getStringExtra("id");
		String flowCode = getIntent().getStringExtra("flowCode");
		if (!KingTellerJudgeUtils.isEmpty(id))
			js.setParam("params", "{'id':'" + id + "','flowCode':'" + flowCode
					+ "'}");

		webView.addJavascriptInterface(js, "kingteller");

		CookieManager.getInstance().setCookie(
				KingTellerConfigUtils.getIpDomain(this) + ":" + KingTellerConfigUtils.getPort(this)
						+ "/",
				KingTellerStaticConfig.COOKIENAME
						+ "="
						+ KingTellerApplication.getApplication()
								.getAccessToken());
		CookieSyncManager.getInstance().sync();

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				KingTellerProgressUtils.closeProgress();

			}

			@Override
			public void onPageStarted(WebView view, final String url,
					Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				KingTellerProgressUtils.showProgress(CommonWebViewActivity.this, "正在加载中...");

			}
		});

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
				if (fromNow)
					title_text.setText(title);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				// Required functionality here
				return super.onJsAlert(view, url, message, result);
			}
		});

		webView.loadUrl(url);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;

		}

	}

	@Override
	public boolean onKeyDown(int keycode, KeyEvent keyEvent) {
		if (state == 0 && keycode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
			KingTellerProgressUtils.closeProgress();
			if (webView.canGoBack() && keycode == KeyEvent.KEYCODE_BACK) {
				webView.goBack();
				return true;
			}
		}
		return super.onKeyDown(keycode, keyEvent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
