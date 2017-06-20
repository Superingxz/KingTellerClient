package com.kingteller.client.photo.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.photo.util.Bimp;
import com.kingteller.client.photo.util.PublicWay;
import com.kingteller.client.photo.zoom.PhotoView;
import com.kingteller.client.photo.zoom.ViewPagerFixed;


/**
 * 这个是用于进行图片浏览时的界面
 *
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:47:53
 */
public class GalleryActivity extends BaseActivity implements OnClickListener{
	private Intent intent;
	// 发送按钮
	private Button btn_submit;
	//顶部显示预览图片位置的textview
	private TextView positionTextView;
	//获取前一个activity传过来的position
	private int position;
	//当前的位置
	private int location = 0;
	
	private ArrayList<View> listViews = null;
	private ViewPagerFixed pager;
	private MyPageAdapter adapter;

	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public List<String> del = new ArrayList<String>();
	
	private Context mContext;

	RelativeLayout photo_relativeLayout;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_gallery);// 切屏到主界面
		PublicWay.activityList.add(this);
		mContext = this;
		btn_submit = (Button) findViewById(R.id.btn_submit);
		
		title_left.setOnClickListener(this);
		title_title.setText("查看图片");
		title_right.setText("删除");
		
		btn_submit.setOnClickListener(this);
		title_right.setOnClickListener(new DelListener());
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		position = Integer.parseInt(intent.getStringExtra("position"));
		// 为发送按钮设置文字
		pager = (ViewPagerFixed) findViewById(R.id.gallery01);
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
			initListViews( Bimp.tempSelectBitmap.get(i).getBitmap() );
		}
		
		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setPageMargin((int)getResources().getDimensionPixelOffset(R.dimen.ui_10_dip));
		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
	}
	
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			location = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}
	};
	
	private void initListViews(Bitmap bm) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		listViews.add(img);
	}
	
	// 删除按钮添加的监听器
	private class DelListener implements OnClickListener {

		public void onClick(View v) {
			if (listViews.size() == 1) {
				Bimp.tempSelectBitmap.clear();
				Bimp.max = 0;
				//btn_submit.setText(R.string.finish+"(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
				Intent intent = new Intent("data.broadcast.action");  
                sendBroadcast(intent);  
				finish();
			} else {
				Bimp.tempSelectBitmap.remove(location);
				Bimp.max--;
				pager.removeAllViews();
				listViews.remove(location);
				adapter.setListViews(listViews);
				adapter.notifyDataSetChanged();
			}
		}
	}
	/**
	 * 监听返回按钮
	 */
	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(position==1){
				intent.setClass(GalleryActivity.this, AlbumActivity.class);
				startActivity(intent);
				finish();
			}else if(position==2){
				intent.setClass(GalleryActivity.this, ShowAllPhoto.class);
				startActivity(intent);
				finish();
			}
		}
		return true;
	}*/
	
	
	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;
		public MyPageAdapter(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {
			try {
				((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.button_left:
			finish();
			break;
		case R.id.btn_submit:
			/*intent.setClass(mContext,PhotoMainActivity.class);
			startActivity(intent);*/
			finish();
			break;
		default:
			break;
		}
	}
}
