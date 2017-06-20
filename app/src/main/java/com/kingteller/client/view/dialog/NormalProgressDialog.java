package com.kingteller.client.view.dialog;

import com.kingteller.R;
import com.kingteller.client.view.LoadingProgressView;
import com.kingteller.client.view.dialog.utils.CornerUtils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NormalProgressDialog extends Dialog{
	private Context mContext;
	private String msg;
	private LoadingProgressView mLoadingProgressView;
	private TextView mLoadingTextView;
	
	public NormalProgressDialog(Context context, int theme) {
	        super(context, theme);
	        this.mContext = context;
	    }
	
    public NormalProgressDialog(Context context) {
        super(context);
        this.mContext = context;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);//让window进行全屏显示
        setContentView(R.layout.layout_normalprogress_dialog);
        setCanceledOnTouchOutside(false);// 设置点击屏幕 Dialog不消失
        //setCancelable(false);// 设置点击返回键 Dialog不消失
        initUI();
    }
    
    private void initUI(){
        
        mLoadingProgressView = (LoadingProgressView) this.findViewById(R.id.loadingprogress_view);
    	mLoadingProgressView.setIndeterminate(true);
		mLoadingProgressView.startAnimation();
		
		mLoadingTextView = (TextView) this.findViewById(R.id.loadingprogress_textview);
		mLoadingTextView.setText(TextUtils.isEmpty(msg) ? "加载中..." : msg);
    }

    
    /** set title underline color(设置标题下划线颜色) */
    public NormalProgressDialog setMsgText(String msg) {
        this.msg = msg;
        return this;
    }

}
