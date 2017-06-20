package com.kingteller.client.activity.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.BitmapUtils;

/**
 * 图片浏览Activity
 * @author 王定波
 *
 */
public class PicViewActivity extends Activity {
	public final static String PICPATH = "picpath";
	private ImageView image;
	private TextView title_text;
	private Button title_left_btn;
	private String picpath;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_pic_view);
		KingTellerApplication.addActivity(this);
		
		picpath = getIntent().getStringExtra(PICPATH);
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.pic_title);
		title_left_btn = (Button) findViewById(R.id.pic_back);
		
		title_text.setText("图片预览");
		
		title_left_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		image = (ImageView) findViewById(R.id.image);
	}
	
	private void initData() {
		image.setImageBitmap(BitmapUtils.decodeStream(picpath,
				KingTellerStaticConfig.SCREEN.Width,
				KingTellerStaticConfig.SCREEN.Height));

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
