package com.kingteller.client.activity.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.CommonSelectDataAdapter;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.framework.utils.Logger;

public class CommonSelectAreaActivity extends Activity implements IXListViewListener, OnClickListener,OnItemClickListener {

	public final static String TYPE = "type";
	private String type;
	private String area_pid;
	private List<CommonSelectData> listData = new ArrayList<CommonSelectData>();
	private CommonSelectDataAdapter adapter;
	public final static String DATA = "data";
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_alllist_view);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		initUI();
		getData();
	}
	
	private void initUI(){
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);

		type = getIntent().getStringExtra(TYPE);
		area_pid = getIntent().getStringExtra("area_pid");
		
		if("provence".equals(type)){
			title_text.setText("选择省份");
		}else if("city".equals(type)){
			title_text.setText("选择地市");
		}else if("district".equals(type)){
			title_text.setText("选择县区");
		}
		
		adapter = new CommonSelectDataAdapter(this, listData);
		getListviewObj().getListview().setAdapter(adapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
	}
	
	private void getData(){
		if("provence".equals(type)){
			getProvences();
		}else if("city".equals(type)){
			getCitys(area_pid);
		}else if("district".equals(type)){
			getDistricts(area_pid);
		}else{
			Logger.e("TAG", "数据类型错误");
		}
	}
	
	//获取省份
	private void getProvences(){
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		Cursor c = database.rawQuery("select area_id,area_cname from tb_sm_area where area_level=?;",new String[]{"1"});
		
		BasePageBean<CommonSelectData> pagedata = new BasePageBean<CommonSelectData>();
		List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
		
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) {  
			CommonSelectData data = new CommonSelectData();
			
			data.setText(c.getString(c.getColumnIndex("area_cname")));
			data.setValue(c.getString(c.getColumnIndex("area_id")));
			
//			byte[] bname = c.getBlob(c.getColumnIndex("area_cname"));
//			try {
//				data.setText(new String(bname,"GBK"));
//				data.setValue(c.getString(c.getColumnIndex("area_id")));
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
			
			lists.add(data);
			c.moveToNext();   
		}   
		
		pagedata.setList(lists);
		setData(pagedata);
		c.close();
		database.close();
	}
	
	private void getCitys(String area_pid){
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		Cursor c = database.rawQuery("select area_id,area_cname from tb_sm_area where area_pid=?;",new String[]{area_pid});
		
		BasePageBean<CommonSelectData> pagedata = new BasePageBean<CommonSelectData>();
		List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
		
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) {  
			CommonSelectData data = new CommonSelectData();
			//byte[] bname = c.getBlob(c.getColumnIndex("area_cname"));

			data.setText(c.getString(c.getColumnIndex("area_cname")));
			data.setValue(c.getString(c.getColumnIndex("area_id")));
			
//			try {
//				data.setText(new String(bname,"GBK"));
//				data.setValue(c.getString(c.getColumnIndex("area_id")));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			lists.add(data);
			c.moveToNext();   
		}   
		
		pagedata.setList(lists);
		setData(pagedata);
		c.close();
		database.close();
	}
	
	private void getDistricts(String area_pid){
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		Cursor c = database.rawQuery("select area_id,area_cname from tb_sm_area where area_pid=?;",new String[]{area_pid});
		
		BasePageBean<CommonSelectData> pagedata = new BasePageBean<CommonSelectData>();
		List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
		
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) {  
			CommonSelectData data = new CommonSelectData();
			//byte[] bname = c.getBlob(c.getColumnIndex("area_cname"));
			
			data.setText(c.getString(c.getColumnIndex("area_cname")));
			data.setValue(c.getString(c.getColumnIndex("area_id")));
			
//			try {
//				data.setText(new String(bname,"GBK"));
//				data.setValue(c.getString(c.getColumnIndex("area_id")));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			lists.add(data);
			c.moveToNext();   
		}   
		
		pagedata.setList(lists);
		setData(pagedata);
		c.close();
		database.close();
	}
	
	private void setData(BasePageBean<CommonSelectData> pagedata) {
		List<CommonSelectData> data = pagedata.getList();
		if (data.size() > 0) {
			adapter.setLists(data);
			getListviewObj().setStatus(LoadingEnum.LISTSHOW);
		} else {
			getListviewObj().setStatus(LoadingEnum.NODATA, "没有数据");
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra(DATA,(CommonSelectData) adapterView.getItemAtPosition(pos));
		setResult(RESULT_OK, intent);
		finish();
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		getData();
		getListviewObj().getListview().setLoadMoreEnabled(true);
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;

		default:
			break;
		}
	}
	

	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
