package com.kingteller.client.photo.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.kingteller.client.activity.more.FeedBackActivity;
import com.kingteller.client.photo.adapter.AlbumGridViewAdapter;
import com.kingteller.client.photo.util.AlbumHelper;
import com.kingteller.client.photo.util.ImageBucket;
import com.kingteller.client.photo.util.ImageItem;
import com.kingteller.client.view.toast.T;

public class TestAlbumActivity extends BaseActivity implements OnClickListener {

	private GridView gridView;
	private TextView tv;
	private AlbumGridViewAdapter gridImageAdapter;
	private Button btn_submit;
	private ArrayList<ImageItem> dataList;
	private AlbumHelper helper;
	public List<ImageBucket> contentList;
	public Bitmap bitmap;
	private List<ImageItem> imageItemSelectedList =null;
	private Context mContext;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_album);
		mContext = TestAlbumActivity.this;
		
		IntentFilter filter = new IntentFilter("data.broadcast.action");
		registerReceiver(broadcastReceiver, filter);
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.plugin_camera_no_pictures);
		initUI();
		initListener();
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			gridImageAdapter.notifyDataSetChanged();
		}
	};

	private void initUI() {
		title_left.setOnClickListener(this);
		title_title.setText("选择图片");
		btn_submit = (Button) findViewById(R.id.btn_submit);
		
		if(imageItemSelectedList == null){
			imageItemSelectedList = new ArrayList<ImageItem>();
		}else if(imageItemSelectedList.size() > 9){
			imageItemSelectedList.clear();
		}
		
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		
		contentList = helper.getImagesBucketList(false);
		dataList = new ArrayList<ImageItem>();
		for (int i = 0; i < contentList.size(); i++) {
			dataList.addAll(contentList.get(i).getImageList());
		}
		
		gridView = (GridView) findViewById(R.id.myGrid);
		gridImageAdapter = new AlbumGridViewAdapter(this, dataList,imageItemSelectedList);
		gridView.setAdapter(gridImageAdapter);
		tv = (TextView) findViewById(R.id.myText);
		gridView.setEmptyView(tv);

	/*	btn_submit.setText("完成" + "("
				+ imageItemSelectedList.size() + "/" + PublicWay.num + ")");*/
	}

	private void initListener() {

		gridImageAdapter
				.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(final ToggleButton toggleButton,
							int position, boolean isChecked, Button chooseBt) {
						if (imageItemSelectedList.size() >= 9) {
							toggleButton.setChecked(false);
							chooseBt.setVisibility(View.GONE);
							if (!removeOneData(dataList.get(position))) {
								T.showShort(mContext, "超出可选图片张数!");
							}
							return;
						}
						if (isChecked) {
							chooseBt.setVisibility(View.VISIBLE);
							imageItemSelectedList.add(dataList.get(position));
						} else {
							imageItemSelectedList.remove(dataList.get(position));
							chooseBt.setVisibility(View.GONE);
						}
					/*	btn_submit.setText("完成" + "("
								+ imageItemSelectedList.size() + "/"
								+ PublicWay.num + ")");*/
					}
				});

		btn_submit.setText("确定");
		btn_submit.setOnClickListener(this);

	}

	private boolean removeOneData(ImageItem imageItem) {
		if (imageItemSelectedList.contains(imageItem)) {
			imageItemSelectedList.remove(imageItem);
			/*btn_submit.setText("完成" + "("
					+ imageItemSelectedList.size() + "/" + PublicWay.num + ")");*/
			return true;
		}
		return false;
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
			finish();
			break;
		case R.id.btn_submit:
			ArrayList<String> strList = new ArrayList<String>();
			Intent intent = new Intent();
			for(int i =0;i < imageItemSelectedList.size(); i ++){
				strList.add(imageItemSelectedList.get(i).getImagePath());
			}
			intent.putStringArrayListExtra("data", strList);
			setResult(RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * 在这边控制页面选择的图片数量即可
	 * */
}

