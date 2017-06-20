package com.kingteller.client.activity.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.common.CommonWebViewActivity;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsInterface;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;

public class BaseWebActivity extends Activity implements OnClickListener {

    protected WebView mWebView;
    private ProgressBar progressbar;
    private boolean fromNow = false;
	private TextView title_text;
	private Button title_left_btn;
	private String title;
	private String url;
	public static int state = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_baseweb);
        Bundle bundles = getIntent().getExtras();
        
 		title = "正在读取中";
 		if (bundles != null) {
 			fromNow = bundles.containsKey("from_now");
 			url = bundles.getString("url");
 			
 			if (!fromNow) {
 				title = bundles.getString("title");
 			}
 		}
        
		initUI();
        initData();
       
    }

    private void initUI(){
    	title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
			
		title_left_btn.setOnClickListener(this);
		
		progressbar = (ProgressBar) findViewById(R.id.layout_base_progressbar);
		
        mWebView = (WebView) findViewById(R.id.layout_base_webview);
        
        title_text.setText(title);
       
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    private void initData() {
    	WebSettings setting = mWebView.getSettings();
		setting.setDefaultTextEncodingName("utf-8");
		setting.setJavaScriptEnabled(true);//设置支持Javascript
		 
		KingTellerJsInterface js = new KingTellerJsInterface(this);
		String id = getIntent().getStringExtra("id");
		String flowCode = getIntent().getStringExtra("flowCode");
		
		if (!KingTellerJudgeUtils.isEmpty(id)){
			js.setParam("params", "{'id':'" + id + "','flowCode':'" + flowCode + "'}");
		}

		mWebView.addJavascriptInterface(js, "kingteller");

		CookieManager.getInstance().setCookie(
				KingTellerConfigUtils.getIpDomain(this) + ":" + KingTellerConfigUtils.getPort(this)
						+ "/",
				KingTellerStaticConfig.COOKIENAME
						+ "="
						+ KingTellerApplication.getApplication().getAccessToken());
		CookieSyncManager.getInstance().sync();
		
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            	//Log.e("webprog", newProgress + "%");
            	
            	progressbar.setProgress(newProgress);
            	 
                if (newProgress == 100) {
                	progressbar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == progressbar.getVisibility()) {
                    	progressbar.setVisibility(View.VISIBLE);
                    } 
                }
               
                super.onProgressChanged(view, newProgress);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
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
			public void onPageStarted(WebView view, final String url,Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				KingTellerProgressUtils.showProgress(BaseWebActivity.this, "正在加载中...");

			}
		});
   
        mWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
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
    //设置回退  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
    	if (state == 0 && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {  
	        	mWebView.goBack(); //goBack()表示返回WebView的上一页面  
	            return true;  
	        }  
    	}
        return super.onKeyDown(keyCode, event);
    }
    
}