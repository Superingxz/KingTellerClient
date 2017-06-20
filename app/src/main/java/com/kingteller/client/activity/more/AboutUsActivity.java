package com.kingteller.client.activity.more;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;

/**
 * 关于我们
 * @author 王定波
 *
 */
public class AboutUsActivity extends Activity {
	private TextView title_text;
	private Button title_left_btn;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_aboutus);
		KingTellerApplication.addActivity(this);
		
		initUI();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		
		title_text.setText("关于我们");
		title_left_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
