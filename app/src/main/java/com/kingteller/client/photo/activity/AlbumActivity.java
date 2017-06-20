package com.kingteller.client.photo.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kingteller.R;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.photo.adapter.AlbumGridViewAdapter;
import com.kingteller.client.photo.util.AlbumHelper;
import com.kingteller.client.photo.util.Bimp;
import com.kingteller.client.photo.util.ImageBucket;
import com.kingteller.client.photo.util.ImageItem;
import com.kingteller.client.photo.util.PublicWay;
import com.kingteller.client.view.toast.T;

public class AlbumActivity extends BaseActivity implements OnClickListener {

	private GridView gridView;
	private TextView tv;
	private AlbumGridViewAdapter gridImageAdapter;
	private Button btn_submit;
	private Intent intent;
	private Context mContext;
	private ArrayList<ImageItem> dataList;
	private AlbumHelper helper;
	public static List<ImageBucket> contentList;
	public static Bitmap bitmap;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_album);
		PublicWay.activityList.add(this);
		mContext = this;
		IntentFilter filter = new IntentFilter("data.broadcast.action");
		registerReceiver(broadcastReceiver, filter);
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.plugin_camera_no_pictures);
		init();
		initListener();
		isShowOkBt();
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// mContext.unregisterReceiver(this);
			// TODO Auto-generated method stub
			gridImageAdapter.notifyDataSetChanged();
		}
	};

	private class AlbumSendListener implements OnClickListener {
		public void onClick(View v) {
			overridePendingTransition(R.anim.activity_translate_in,
					R.anim.activity_translate_out);
			intent.setClass(mContext, PhotoMainActivity.class);
			intent.putExtra("tydId", getIntent().getStringExtra("tydId"));
			intent.putExtra("tydzt", getIntent().getStringExtra("tydzt"));
			startActivity(intent);
			finish();
		}

	}

	private void init() {
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		
		if(Bimp.tempSelectBitmap.size() >= 9){
			Bimp.tempSelectBitmap.clear();
		}

		contentList = helper.getImagesBucketList(false);
		dataList = new ArrayList<ImageItem>();
		for (int i = 0; i < contentList.size(); i++) {
			dataList.addAll(contentList.get(i).imageList);
		}
		title_left.setOnClickListener(this);
		title_title.setText("选择图片");
		btn_submit = (Button) findViewById(R.id.btn_submit);

		intent = getIntent();
		Bundle bundle = intent.getExtras();
		gridView = (GridView) findViewById(R.id.myGrid);
		gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
				Bimp.tempSelectBitmap);
		gridView.setAdapter(gridImageAdapter);
		tv = (TextView) findViewById(R.id.myText);
		gridView.setEmptyView(tv);

		btn_submit.setText("完成" + "("
				+ Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
	}

	private void initListener() {

		gridImageAdapter
				.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(final ToggleButton toggleButton,
							int position, boolean isChecked, Button chooseBt) {
						if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
							toggleButton.setChecked(false);
							chooseBt.setVisibility(View.GONE);
							if (!removeOneData(dataList.get(position))) {
								T.showShort(mContext, "超出可选图片张数!");
							}
							return;
						}
						if (isChecked) {
							chooseBt.setVisibility(View.VISIBLE);
							Bimp.tempSelectBitmap.add(dataList.get(position));
							btn_submit.setText("完成" + "("
									+ Bimp.tempSelectBitmap.size() + "/"
									+ PublicWay.num + ")");
						} else {
							Bimp.tempSelectBitmap.remove(dataList.get(position));
							chooseBt.setVisibility(View.GONE);
							btn_submit.setText("完成" + "("
									+ Bimp.tempSelectBitmap.size() + "/"
									+ PublicWay.num + ")");
						}
						isShowOkBt();
					}
				});

		btn_submit.setOnClickListener(new AlbumSendListener());

	}

	private boolean removeOneData(ImageItem imageItem) {
		if (Bimp.tempSelectBitmap.contains(imageItem)) {
			Bimp.tempSelectBitmap.remove(imageItem);
			btn_submit.setText("完成" + "("
					+ Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			return true;
		}
		return false;
	}

	public void isShowOkBt() {
		if (Bimp.tempSelectBitmap.size() > 0) {
			btn_submit.setText("完成" + "("
					+ Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			btn_submit.setPressed(true);
			btn_submit.setClickable(true);
			btn_submit.setTextColor(Color.WHITE);
		} else {
			btn_submit.setText("完成" + "("
					+ Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			btn_submit.setPressed(false);
			btn_submit.setClickable(false);
			btn_submit.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	@Override
	protected void onRestart() {
		isShowOkBt();
		super.onRestart();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.button_left:
			intent.setClass(mContext, PhotoMainActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}
}
