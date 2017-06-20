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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.CommonSelectGZBJDataAdapter;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.CommonSelectGZBJ;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.utils.SortByPinyinGZ;
import com.kingteller.client.view.ConditionView;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;

public class CommonSelectGZMSActivity extends Activity implements
		OnClickListener, IXListViewListener, OnItemClickListener {

	private List<CommonSelectGZBJ> listGzbjData = new ArrayList<CommonSelectGZBJ>();
	private CommonSelectGZBJDataAdapter gzbjAdapter;
	private String[] typedatas;
	private int type;
	private String eatra_data;
	private String troubleModule;
	public final static String TITLE = "title";
	public final static String TYPE = "type";
	public final static String DATA = "data";
	public final static String EXTRADATA = "extra_data";
	public final static String TROUBLEMODULE = "troubleModule";
	private final static int KEY_TITLE = 0;
	private final static int KEY_URL = 1;
	private ConditionView troubleMark;
	private String pplb;
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_select_gzms);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		initUI();

	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		
		
		
		type = getIntent().getIntExtra(TYPE, 0);
		typedatas = getResources().getStringArray(type);
		eatra_data = getIntent().getStringExtra(EXTRADATA);
		troubleModule = getIntent().getStringExtra(TROUBLEMODULE);
		pplb = getIntent().getStringExtra("pplb");
		troubleMark = (ConditionView) findViewById(R.id.troubleMark);
		
		title_text.setText(typedatas[KEY_TITLE]);

		gzbjAdapter = new CommonSelectGZBJDataAdapter(this, listGzbjData);
		getListviewObj().getListview().setAdapter(gzbjAdapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		getGzms();
		//getListviewObj().setStatus(LoadingEnum.NODATA, "请输入条件进行搜索");
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.layout_main_righttwo_btn:
			getGzms();
			break;
		default:
			break;
		}
	}

	//故障描述-维护信息
	private void getGzms() {
		
		String myPath = SQLiteHelper.DB_PATH + SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,
				null);
		Cursor c = database.rawQuery("select _id,trouble_remark,path_name from tb_kfwh_trouble_remark where parent_id=? and two_level_id=?;",
						new String[] { eatra_data, troubleModule });

		BasePageBean<CommonSelectGZBJ> pagedata = new BasePageBean<CommonSelectGZBJ>();
		List<CommonSelectGZBJ> lists = new ArrayList<CommonSelectGZBJ>();

		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) {
			CommonSelectGZBJ data = new CommonSelectGZBJ();
			data.setText(c.getString(c.getColumnIndex("trouble_remark")));
			data.setPathname(c.getString(c.getColumnIndex("path_name")));
			data.setValue(c.getString(c.getColumnIndex("_id")));
			
//			byte[] bname = c.getBlob(c.getColumnIndex("trouble_remark"));
//			byte[] bname1 = c.getBlob(c.getColumnIndex("path_name"));
//
//			try {
//				data.setText(new String(bname, "UTF-8"));
//				data.setValue(c.getString(c.getColumnIndex("_id")));
//				data.setPathname(new String(bname1, "UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			lists.add(data);
			c.moveToNext();
		}

		if (lists.size() == 0) {
			c = database.rawQuery("select _id,trouble_remark,path_name from tb_kfwh_trouble_remark where parent_id=?;",
							new String[] { eatra_data });

			pagedata = new BasePageBean<CommonSelectGZBJ>();
			lists = new ArrayList<CommonSelectGZBJ>();

			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) {
				CommonSelectGZBJ data = new CommonSelectGZBJ();
				
				data.setText(c.getString(c.getColumnIndex("trouble_remark")));
				data.setPathname(c.getString(c.getColumnIndex("path_name")));
				data.setValue(c.getString(c.getColumnIndex("_id")));
				
//				byte[] bname = c.getBlob(c.getColumnIndex("trouble_remark"));
//				byte[] bname1 = c.getBlob(c.getColumnIndex("path_name"));
//
//				try {
//					data.setText(new String(bname, "UTF-8"));
//					data.setValue(c.getString(c.getColumnIndex("_id")));
//					data.setPathname(new String(bname1, "UTF-8"));
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				lists.add(data);
				c.moveToNext();
			}
		}

		if (lists.size() == 0) {
			CommonSelectGZBJ data = new CommonSelectGZBJ();  //其它  其他
			data.setText("其他");
			data.setValue("201409291609a");
			data.setPathname("其他");

			lists.add(data);
		}

		List<CommonSelectGZBJ> sealists = new ArrayList<CommonSelectGZBJ>();
		if (!KingTellerJudgeUtils.isEmpty(troubleMark.getFieldValue())) {
			if (lists.size() > 0) {
				for (CommonSelectGZBJ comm : lists) {
					if (comm.getText().indexOf(troubleMark.getFieldValue()) > -1) {
						sealists.add(comm);
					}
				}
			}
		}
		if (null != sealists && sealists.size() > 0) {
			SortByPinyinGZ.sort(sealists);
			pagedata.setList(sealists);
		} else {
			SortByPinyinGZ.sort(lists);
			pagedata.setList(lists);
		}
		setGZBJData(pagedata);
		c.close();
		database.close();
	}

	private void setGZBJData(BasePageBean<CommonSelectGZBJ> pagedata) {
		// TODO Auto-generated method stub
		
/*		if(CommonUtils.isEmpty(pplb) && pplb.equals("O")){
			CommonSelectGZBJ csg = new CommonSelectGZBJ();
			csg.setText("其它");
			csg.setValue("201409291609a");
			List<CommonSelectGZBJ> datalist = new ArrayList<CommonSelectGZBJ>();
			datalist.add(csg);
			gzbjAdapter.setLists(datalist);
		}else{*/
		getListviewObj().getListview().stopRefresh();
		List<CommonSelectGZBJ> data = pagedata.getList();
		if (data.size() > 0) {
			gzbjAdapter.setLists(data);
			getListviewObj().setStatus(LoadingEnum.LISTSHOW);
		} else {
			getListviewObj().setStatus(LoadingEnum.NODATA, "没有数据");
		}
		//}
		
	}

	@Override
	public void onRefresh() {
		getGzms();
		//getListviewObj().getListview().setLoadMoreEnabled(true);
	}

	@Override
	public void onLoadMore() {

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra(DATA,(CommonSelectGZBJ) adapterView.getItemAtPosition(pos));
		setResult(RESULT_OK, intent);
		finish();
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