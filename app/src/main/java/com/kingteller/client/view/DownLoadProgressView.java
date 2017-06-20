package com.kingteller.client.view;

import com.kingteller.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下载上传进度视图
 * @author 王定波
 *
 */
public class DownLoadProgressView extends LinearLayout {

	private TextView tv;
	private ProgressBar pro;
	private TextView msg;

	public DownLoadProgressView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		initView();
	}

	public DownLoadProgressView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		inflate(getContext(), R.layout.download_dialog, this);
		pro = (ProgressBar) findViewById(R.id.nowprogressbar);
		tv = (TextView) findViewById(R.id.downtv);
		msg= (TextView) findViewById(R.id.msg);

	}

	public void setProgress(int progress) {
		pro.setProgress(progress);
		tv.setText(progress + "%");
	}
	
	public void setMsg(String message)
	{
		msg.setText(message);
	}

}