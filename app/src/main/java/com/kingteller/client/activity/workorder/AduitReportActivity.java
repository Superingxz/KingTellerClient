package com.kingteller.client.activity.workorder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.workorder.frament.AduitReportFragment;
import com.kingteller.client.adapter.PagerAdapter;
import com.kingteller.client.bean.workorder.AduitReportBean;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;


public class AduitReportActivity extends FragmentActivity implements
		OnClickListener, OnPageChangeListener {

	private Button batchAudit, batchReturn;
	private ViewPager mViewPager;
	private ArrayList<Fragment> fragments;
	private PagerAdapter pagerAdapter;
	private AduitReportFragment untreatedFragment,processedFragment;

	private Button title_left_btn;
	
	private String[] mTwoLevelTitles = new String[]{"待处理","已处理"};
	
	private List<TextView> mTwoLevelTitlesTVLists;
	
	private TextView title_text, title_twolevel_textone, title_twolevel_texttwo;
	
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ImageView imageView;// 动画图片
	
	private Context mContext;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_audit_report);
		KingTellerApplication.addActivity(this);
		
		mContext = AduitReportActivity.this;
		initUI();
	}
	
	
	private void initUI() {
		InitImageView();
		initTitle();
		InitTextView();
		initViewPager();

	}
	
	/**
	 * 初始化头标
	 * 
	 */
	private void InitTextView() {
		mTwoLevelTitlesTVLists  = new ArrayList<TextView>();
		
		title_twolevel_textone = (TextView) findViewById(R.id.title_twolevel_textone);
		title_twolevel_texttwo = (TextView) findViewById(R.id.title_twolevel_texttwo);
		
		mTwoLevelTitlesTVLists.add(title_twolevel_textone);
		mTwoLevelTitlesTVLists.add(title_twolevel_texttwo);
		
		title_twolevel_textone.setText(mTwoLevelTitles[0]);
		title_twolevel_texttwo.setText(mTwoLevelTitles[1]);
		
		TwoLevelTitleOnClickListener mTwoLevelTitleOnClickListener = new TwoLevelTitleOnClickListener();// 底部按钮监听事件
		 
		title_twolevel_textone.setOnClickListener(mTwoLevelTitleOnClickListener);
		title_twolevel_texttwo.setOnClickListener(mTwoLevelTitleOnClickListener);
		
		chosenTwoLevelTVColor(0);
	}

	/**
	 * 初始化title
	 */
	private void initTitle() {
		
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_text.setText("审核报告列表");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		batchAudit = (Button) findViewById(R.id.batchAudit);
		batchReturn = (Button) findViewById(R.id.batchReturn);
		batchAudit.setOnClickListener(this);
		batchReturn.setOnClickListener(this);

	}
	
	/**
	 * 初始化Viewpager页
	 */
	private void initViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.viewPaper);
		
		fragments = new ArrayList<Fragment>();
		untreatedFragment = new AduitReportFragment().getInstance(BaseReportActivity.OPT_UNTREATED);
		processedFragment = new AduitReportFragment().getInstance(BaseReportActivity.OPT_PROCESSED);
		
		fragments.add(untreatedFragment);
		fragments.add(processedFragment);
		
		
		pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
		
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setCurrentItem(0,true);
		mViewPager.setOnPageChangeListener(this);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.batchAudit:
				if(untreatedFragment.getAduitCheckedtList().size() >= 1){
					
					dialog = new NormalDialog(mContext);
		        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "确定将选中的工作报告审核通过吗？",
							new OnBtnClickL() {
								@Override
								public void onBtnClick() {
									dialog.dismiss();	
								}
		                    }, new OnBtnClickL() {
								@Override
								public void onBtnClick() {
									dialog.dismiss();
									batchProcess("0");
								}
		                    });
					
				}else{
					T.showShort(mContext, "您没有选择报告!");
				}	
			break;
		case R.id.batchReturn:
				if(untreatedFragment.getAduitCheckedtList().size() >= 1){
					
					dialog = new NormalDialog(mContext);
		        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "确定将选中的工作报告退回吗？",
							new OnBtnClickL() {
								@Override
								public void onBtnClick() {
									dialog.dismiss();	
								}
		                    }, new OnBtnClickL() {
								@Override
								public void onBtnClick() {
									dialog.dismiss();
									batchProcess("1");
								}
		                    });

				}else{
					T.showShort(mContext, "您没有选择报告!");
				}
			break;
		default:
			break;
		}
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
			}
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
		// TODO Auto-generated method stub
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		
		Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
		
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		imageView.startAnimation(animation);
		
		chosenTwoLevelTVColor(arg0);
		
		if(arg0 == 0){
			batchAudit.setVisibility(View.VISIBLE);
			batchReturn.setVisibility(View.VISIBLE);
		}else if(arg0 == 1){
			batchAudit.setVisibility(View.GONE);
			batchReturn.setVisibility(View.GONE);
		}
		
		currIndex = arg0;
	}
	
	
	/**
	 * 批量处理
	 * 0：批量审核 1：批量退回flag
	 * */
	private void batchProcess(final String flag){
		
		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		List<AduitReportBean> data = untreatedFragment.getAduitCheckedtList();
		if(data != null && data.size() == 0){
			T.showShort(mContext, "没有选择选项!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("report", ConditionUtils.getJsonFromObj(data));
		params.put("flag", flag);
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.PlthUrl),params,
				new AjaxHttpCallBack<ReturnBackStatus>(this,
						new TypeToken<ReturnBackStatus>() {
						}.getType(), true) {
					
					@Override
					public void onStart() {
						String msg = "";
						if("0".equals(flag)){
							msg = "审批中,请稍后...";
						}else{
							msg = "退回中,请稍后...";
						}
						KingTellerProgressUtils.showProgress(mContext, msg);
					}
					
					@Override
					public void onDo(ReturnBackStatus data) {
						KingTellerProgressUtils.closeProgress();
						if("success".equals(data.getResult())){
							KingTellerStaticConfig.AUDIT_REPORT_SUBMISSION_STATE_1 = true;
							KingTellerStaticConfig.AUDIT_REPORT_SUBMISSION_STATE_2 = true;
							untreatedFragment.onResume();
							processedFragment.onResume();
							T.showShort(mContext, "提示：" + data.getMessage());
						}else{
							T.showShort(mContext, "提示：" + data.getMessage());
						}
					};
				});
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
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2
													// = 偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}
	
	
	/**
     * 设置选中图标与字体
     */
    private void chosenTwoLevelTVColor(int arg) {
    	resetTwoLevelTVColor(2);
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
