package com.kingteller.client.activity.map;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.login.LoginActivity;
import com.kingteller.client.activity.welcome.WelComeActivity;
import com.kingteller.client.bean.attendance.OverTimeSonPeople;
import com.kingteller.client.bean.attendance.WorkAttendanceSearchPeopleBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.calendar.CalendarDay;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSelectDateClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSelectTimeClickL;
import com.kingteller.client.view.dialog.use.KingTellerDateTimeDialogUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleGroupView;
import com.kingteller.client.view.image.activity.ChangePicBigImagesActivity;
import com.kingteller.client.view.image.activity.PickBigImagesActivity;
import com.kingteller.client.view.image.activity.PickOrTakeImageActivity;
import com.kingteller.client.view.image.allview.SelectPicGridView;
import com.kingteller.client.view.image.allview.SelectPicGridView.OnSelectImageItemLister;
import com.kingteller.client.view.toast.T;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class SelectImageAllActivity extends Activity implements OnClickListener{
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn;
	private SelectPicGridView layout_select_pic;
	
	private List<String> list = new ArrayList<String>();
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_select_imageall);
		KingTellerApplication.addActivity(this);
		
		mContext = SelectImageAllActivity.this;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_text.setText("图片选择测试");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);

		layout_select_pic = (SelectPicGridView) findViewById(R.id.layout_add_pic);
		
		KingTellerConfigUtils.hideInputMethod(this);
	}

	private void initData() {
		layout_select_pic.setOnSelectImageItemLister(new OnSelectImageItemLister() {
			@Override
			public void OnItemClick(View view, int type, int num) {
				if(type == 0){//添加图片
					if(list.size() < 9){
						Intent intent = new Intent();
		                intent.setClass(mContext, PickOrTakeImageActivity.class);
		                intent.putExtra(PickOrTakeImageActivity.EXTRA_NUMS, 9 - list.size());
		                startActivityForResult(intent, KingTellerStaticConfig.PIC_SELECT_NUM);
					}else{
						T.showShort(mContext, "最多只能选择9张图片");
					}
				} else {
					Intent intent = new Intent();
	                intent.setClass(mContext, ChangePicBigImagesActivity.class);
	                intent.putExtra(ChangePicBigImagesActivity.EXTRA_IS_DELETE, type);
	                intent.putExtra(ChangePicBigImagesActivity.EXTRA_NOW_PIC, num);
	                intent.putExtra(ChangePicBigImagesActivity.EXTRA_NOW_DATA, new Gson().toJson(list));
	                startActivityForResult(intent, KingTellerStaticConfig.PIC_DELETE_NUM);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case KingTellerStaticConfig.PIC_SELECT_NUM:
				if (resultCode == RESULT_OK) {
					String picdata = (String) data.getSerializableExtra("pic_dataList");
					List<String> addlist = KingTellerJsonUtils.getStringPersons(picdata);
					list.addAll(addlist);
					layout_select_pic.setImagesView(list);
				}
				break;
			case KingTellerStaticConfig.PIC_DELETE_NUM:
				if (resultCode == RESULT_OK) {
					String picdata = (String) data.getSerializableExtra("pic_dataList_de");
					list = KingTellerJsonUtils.getStringPersons(picdata);
					layout_select_pic.setImagesView(list);
				}
				break;
			default:
				break;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				finish();
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
