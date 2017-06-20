package com.kingteller.client.activity.onlinelearning;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.utils.KingTellerConfigUtils;

public class OnlineWorkCatalogActivity extends Activity implements OnClickListener {
	
	private WebView mWebView;
	private TextView title_title;
	private Button title_left;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		// TODO Auto-generated method stub
		super.onCreate(paramBundle);
		this.setContentView(R.layout.layout_online_work_catalog);
		KingTellerApplication.addActivity(this);
		

		initUI();
		initData();
	}
	
	private void initUI() {
		
		title_title = (TextView)findViewById(R.id.layout_main_text);
		title_left = (Button)findViewById(R.id.layout_main_left_btn);
		title_left.setBackgroundResource(R.drawable.btn_back_arrow);
		
		title_title.setText("文档内容");
		mWebView  = (WebView)  findViewById(R.id.online_work_webView);
		title_left.setOnClickListener(this);

		//KingTellerProgress.showProgress(this, "加载数据中,请稍后");
		//KingTellerProgress.closeProgress();

	}
	
	private void initData() {
		//http://192.168.1.130:8080/ktcs/custmgr/km/wordlibaray/getLibayById_wordlibaray.action?version=2.0.7.9&versionCode=27&libaryId=ff80808140cd691a0140cdc9ae6b1ba7
		String url = KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WdydURL);
		String id = "&libaryId=402881024d26f3f4014d26f6b82c000c";//ff80808140cd691a0140cdc9ae6b1ba7 //402881024d26f3f4014d26f6b82c000c
		String mUrl= url + id;
		//mUrl = "http://www.baidu.com";
		//WebView加载web资源
		mWebView.loadUrl(mUrl);
		//启用支持javascript
		mWebView.getSettings().setJavaScriptEnabled(true);
		//支持播放flash
		mWebView.getSettings().setPluginState(PluginState.ON);
		
		mWebView.getSettings().setAllowFileAccess(true);
		mWebView.getSettings().setDefaultTextEncodingName("GBK");
		
		//判断页面加载过程
		mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                	Log.e("网页加载", newProgress + "");
                } else {
                    Log.e("网页加载", newProgress + "");
                }

            }
        });
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
	             view.loadUrl(url);
	            return true;
	        }
       });
		
		

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
