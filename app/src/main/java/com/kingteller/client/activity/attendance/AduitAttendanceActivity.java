package com.kingteller.client.activity.attendance;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.attendance.frament.AduitAttendanceFragment;
import com.kingteller.client.adapter.PagerAdapter;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.view.dialog.SearchGeneralDialog;
import com.kingteller.client.view.dialog.entity.SearchGeneraItem;
import com.kingteller.client.view.dialog.listener.OnBtnSearchClickL;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AduitAttendanceActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener{

	private AduitAttendanceFragment waitFragment, conductFragment, completeFragment;

	private PagerAdapter pagerAdapter;
	private ViewPager mViewPager;
	private ArrayList<Fragment> fragments;

	private String[] mTwoLevelTitles = new String[]{"待审批","审批中","已完成"};
	
	private List<TextView> mTwoLevelTitlesTVLists;
	
	private TextView title_text, title_twolevel_textone, title_twolevel_texttwo, title_twolevel_textthree;
	
	private Button title_left_btn, title_righttwo_btn, mBatch;
	
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ImageView imageView;// 动画图片
	
	private Context mContext;
	
	private SearchGeneraItem waitFragment_search = new SearchGeneraItem();
	private SearchGeneraItem conductFragment_search = new SearchGeneraItem();
	private SearchGeneraItem completeFragment_search = new SearchGeneraItem();
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_aduit);
		KingTellerApplication.addActivity(this);
		
		mContext = AduitAttendanceActivity.this;
		
		initUI();

	}
	
	private void initUI() {	
		InitImageView();
		initTitle();
		InitTextView();
		initViewPager();
	}
	
	/**
	 * 初始化title
	 */
	private void initTitle() {
		
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("考勤审批");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		
		mBatch = (Button) findViewById(R.id.oa_aduit_attendance_batch);
		mBatch.setOnClickListener(this);
		
	}
	
	/**
	 * 初始化头标
	 * 
	 */
	private void InitTextView() {
		mTwoLevelTitlesTVLists  = new ArrayList<TextView>();
		
		title_twolevel_textone = (TextView) findViewById(R.id.title_twolevel_textone);
		title_twolevel_texttwo = (TextView) findViewById(R.id.title_twolevel_texttwo);
		title_twolevel_textthree = (TextView) findViewById(R.id.title_twolevel_textthree);
		
		mTwoLevelTitlesTVLists.add(title_twolevel_textone);
		mTwoLevelTitlesTVLists.add(title_twolevel_texttwo);
		mTwoLevelTitlesTVLists.add(title_twolevel_textthree);
		
		title_twolevel_textone.setText(mTwoLevelTitles[0]);
		title_twolevel_texttwo.setText(mTwoLevelTitles[1]);
		title_twolevel_textthree.setText(mTwoLevelTitles[2]);
		
		TwoLevelTitleOnClickListener mTwoLevelTitleOnClickListener = new TwoLevelTitleOnClickListener();// 底部按钮监听事件
		 
		title_twolevel_textone.setOnClickListener(mTwoLevelTitleOnClickListener);
		title_twolevel_texttwo.setOnClickListener(mTwoLevelTitleOnClickListener);
		title_twolevel_textthree.setOnClickListener(mTwoLevelTitleOnClickListener);
		
		chosenTwoLevelTVColor(0);
	}
	
	/**
	 * 初始化Viewpager页
	 */
	private void initViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.viewPaper1);
		
		fragments = new ArrayList<Fragment>();
		waitFragment = new AduitAttendanceFragment().getInstance("tab1");
		conductFragment = new AduitAttendanceFragment().getInstance("tab2");
		completeFragment = new AduitAttendanceFragment().getInstance("tab3");

		fragments.add(waitFragment);
		fragments.add(conductFragment);
		fragments.add(completeFragment);
		
		pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
		
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(this);
	}
	
	

	
	@Override  
	public void onRestart() {  
        super.onRestart();  
        if(KingTellerStaticConfig.OA_ADUIT_ATTENDANCE_ISUDATE && mViewPager.getCurrentItem() == 0){
        	waitFragment.onRefresh();
        	conductFragment.onRefresh();
        	completeFragment.onRefresh();
        	KingTellerStaticConfig.OA_ADUIT_ATTENDANCE_ISUDATE = false;
        }
        
    }  
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.layout_main_righttwo_btn:
			SearchGeneraItem str = new SearchGeneraItem();
			if(mViewPager.getCurrentItem() == 0){
				str = waitFragment_search;
        	}else if(mViewPager.getCurrentItem() == 1){
        		str = conductFragment_search;
        	}else{
        		str = completeFragment_search;
        	}

			final SearchGeneralDialog searchgeneral_dialog = new SearchGeneralDialog(mContext, R.style.Login_dialog, 0, new Gson().toJson(str));
			searchgeneral_dialog.setOnBtnSearchClickL(
				new OnBtnSearchClickL() {
	                @Override
	                public void onBtnClick(String ss) {
	                	searchgeneral_dialog.dismiss();
	                	if(mViewPager.getCurrentItem() == 0){
	                		waitFragment_search = KingTellerJsonUtils.getPerson(ss, SearchGeneraItem.class);
	                		waitFragment.setSearchList(waitFragment_search);
	                		waitFragment.onRefresh();
	                	}else if(mViewPager.getCurrentItem() == 1){
	                		conductFragment_search = KingTellerJsonUtils.getPerson(ss, SearchGeneraItem.class);
	                		conductFragment.setSearchList(conductFragment_search);
	                		conductFragment.onRefresh();
	                	}else{
	                		completeFragment_search = KingTellerJsonUtils.getPerson(ss, SearchGeneraItem.class);
	                		completeFragment.setSearchList(completeFragment_search);
	                		conductFragment.onRefresh();
	                	}
	                }
	            },
	            new OnBtnSearchClickL() {
	                @Override
	                public void onBtnClick(String qx) {
	                	searchgeneral_dialog.dismiss();
	                }
	            });
			
			searchgeneral_dialog.show();
			break;
		case R.id.oa_aduit_attendance_batch:
			Intent intent_AduitList = new Intent(mContext, AduitBatchAttendanceActivity.class);
			intent_AduitList.putExtra("mAduitAttendanListData", new Gson().toJson(waitFragment.getAduitAttendanListData()));
			mContext.startActivity(intent_AduitList);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		
		Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
		
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		imageView.startAnimation(animation);
		
		chosenTwoLevelTVColor(arg0);
		
		if(arg0 == 0){
			findViewById(R.id.oa_aduit_attendance_btn).setVisibility(View.VISIBLE);
		}else if(arg0 == 1){
			findViewById(R.id.oa_aduit_attendance_btn).setVisibility(View.GONE);
		}else{
			findViewById(R.id.oa_aduit_attendance_btn).setVisibility(View.GONE);
		}
		
		currIndex = arg0;

	}

	/**
	 * 头标点击监听
	 */
	private class TwoLevelTitleOnClickListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_twolevel_textone:
				mViewPager.setCurrentItem(0, true);
				break;
			case R.id.title_twolevel_texttwo:
				mViewPager.setCurrentItem(1, true);
				break;
			case R.id.title_twolevel_textthree:
				mViewPager.setCurrentItem(2, true);
				break;
			}
		}
	}
	
	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */
	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.title_twolevel_imageone);
		
		bmpW = BitmapFactory.decodeResource(getResources(),R.drawable.ic_twoleveltitle_bg).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2
													// = 偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}

	 /**
     * 设置选中图标与字体
     */
    private void chosenTwoLevelTVColor(int arg) {
    	resetTwoLevelTVColor(3);
    	mTwoLevelTitlesTVLists.get(arg).setTextColor(getResources().getColor(R.color.twotitle_text_color_pressde));
    }
    
    /**
     * 重设所有底部按钮图片和按钮字体颜色
     */
    private void resetTwoLevelTVColor(int arg) {
        for(int i = 0; i<arg; i++){
        	mTwoLevelTitlesTVLists.get(i).setTextColor(getResources().getColor(R.color.twotitle_text_color_nomal));
        }
    }
	
    @Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}


}
