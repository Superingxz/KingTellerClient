package com.kingteller.client.activity.more;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.bean.common.BaseBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 意见反馈
 * @author 王定波
 */
public class FeedBackActivity extends Activity implements OnClickListener {

	private EditText feedback_edittext;
	private TextView title_text;
	private Button title_left_btn, btn_baocun;
	private Context mContext;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_feedback);
		KingTellerApplication.addActivity(this);
		
		mContext = FeedBackActivity.this;
		initUI();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		btn_baocun = (Button) findViewById(R.id.btn_baocun);
		btn_baocun.setOnClickListener(this);
		
		title_text.setText("意见反馈");

		feedback_edittext = (EditText) findViewById(R.id.feedback_edittext);
	}

	private void submitFeedback() {

		if (KingTellerJudgeUtils.isEmpty(feedback_edittext.getText().toString())) {
			T.showShort(mContext, "反馈内容不能为空!");
			return;
		}
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("content", feedback_edittext.getText().toString());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SubmitFeedBackUrl),
				params, new AjaxHttpCallBack<BaseBean>(this, true) {
			
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在提交数据...");
					}
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(BaseBean data) {
						if (data.getStatus().equals("1")) {
							T.showShort(mContext, "数据提交成功!");
							finish();
						} else {
							T.showShort(mContext, "数据提交失败:" + data.getMsg());
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.btn_baocun:
			submitFeedback();
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
