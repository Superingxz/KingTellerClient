package com.kingteller.client.activity.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.CommonSelectDataAdapter;
import com.kingteller.client.adapter.CommonSelectGZBJDataAdapter;
import com.kingteller.client.adapter.CommonSelectJQZDDataAdapter;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.common.CommonSelectGZBJ;
import com.kingteller.client.bean.logisticmonitor.LogisticFeeTypeBean;
import com.kingteller.client.bean.workorder.AssignWorkerNameBean;
import com.kingteller.client.bean.workorder.BjWlBean;
import com.kingteller.client.bean.workorder.FeeModeBean;
import com.kingteller.client.bean.workorder.HandleSubBean;
import com.kingteller.client.bean.workorder.JqzdDataBean;
import com.kingteller.client.bean.workorder.RTroubleRemarkBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.utils.SortByPinyin;
import com.kingteller.client.utils.SortByPinyinGZ;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.KingTellerDb;
import com.kingteller.framework.http.AjaxParams;

/**
 * 不是树形全部
 * 
 * @author 王定波
 * 
 */
public class CommonSelectDataActivity extends Activity implements
		OnClickListener, IXListViewListener, OnItemClickListener {

	private List<CommonSelectData> listData = new ArrayList<CommonSelectData>();
	private CommonSelectDataAdapter adapter;
	private List<CommonSelectGZBJ> listGzbjData = new ArrayList<CommonSelectGZBJ>();
	private CommonSelectGZBJDataAdapter gzbjAdapter;
	private List<JqzdDataBean> listJqzdData = new ArrayList<JqzdDataBean>();
	private CommonSelectJQZDDataAdapter jqzdAdapter;
	private String[] typedatas;
	private int type;
	private String eatra_data;
	private String troubleModule;
	public final static String TITLE = "title";
	private String pplb;
	public final static String TYPE = "type";
	public final static String DATA = "data";
	public final static String EXTRADATA = "extra_data";
	public final static String TROUBLEMODULE="troubleModule";
	private final static int KEY_TITLE = 0;
	private final static int KEY_URL = 1;
	private Gson gson = new Gson();
	private HashMap<String,String> hashMap = new HashMap<String,String>();
	private KingTellerDb clgcdb = KingTellerDbUtils.create(this);
	private KingTellerDb gzmkdb = KingTellerDbUtils.create(this);
	private KingTellerDb gzmsdb = KingTellerDbUtils.create(this);
	public KingTellerDb gzbjdb = KingTellerDbUtils.create(this);
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn;
	private Context mContext;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_alllist_view);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		mContext = CommonSelectDataActivity.this;
		initUI();
		getData();
		
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		type = getIntent().getIntExtra(TYPE, 0);
		typedatas = getResources().getStringArray(type);
		eatra_data = getIntent().getStringExtra(EXTRADATA);
		troubleModule = getIntent().getStringExtra(TROUBLEMODULE);
		pplb = getIntent().getStringExtra("pplb");

		title_text.setText(typedatas[KEY_TITLE]);

		if(typedatas[KEY_TITLE].equals("机器字段")) {
			jqzdAdapter = new CommonSelectJQZDDataAdapter(this, listJqzdData);
			getListviewObj().getListview().setAdapter(jqzdAdapter);
			getListviewObj().getListview().setRefreshEnabled(true);
			getListviewObj().getListview().setLoadMoreEnabled(false);
			getListviewObj().getListview().setOnRefreshListener(this);
			getListviewObj().getListview().setOnItemClickListener(this);
		}else if(!typedatas[KEY_TITLE].equals("故障部件") && !typedatas[KEY_TITLE].equals("故障描述")){
			adapter = new CommonSelectDataAdapter(this, listData);
			getListviewObj().getListview().setAdapter(adapter);
			getListviewObj().getListview().setRefreshEnabled(true);
			getListviewObj().getListview().setLoadMoreEnabled(false);
			getListviewObj().getListview().setOnRefreshListener(this);
			getListviewObj().getListview().setOnItemClickListener(this);
		}else{
			gzbjAdapter = new CommonSelectGZBJDataAdapter(this, listGzbjData);
			getListviewObj().getListview().setAdapter(gzbjAdapter);
			getListviewObj().getListview().setRefreshEnabled(true);
			getListviewObj().getListview().setLoadMoreEnabled(false);
			getListviewObj().getListview().setOnRefreshListener(this);
			getListviewObj().getListview().setOnItemClickListener(this);
		}

	}

	private void getData() {

		switch (type) {
		case R.array.bjmk:// 故障模块
			getBjmk();
			break;

		case R.array.gzms:// 故障描述
			getGzms();
			break;
		case R.array.clgc:// 处理过程
			String maintainDataCLGC = KingTellerDbUtils.getMaintainInfoClgcDataBase(clgcdb, eatra_data+"clgc");
			if(!KingTellerJudgeUtils.isEmpty(maintainDataCLGC) ){
				List<HandleSubBean> bjList = gson.fromJson(maintainDataCLGC, new TypeToken<List<HandleSubBean>>() {}.getType());
				BasePageBean<CommonSelectData> pagedata = new BasePageBean<CommonSelectData>();
				List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
				for (int i = 0; i < bjList.size(); i++) {
					CommonSelectData data = new CommonSelectData();
					data.setText(bjList.get(i).getHandleSub());
					data.setValue(bjList.get(i).getId());

					lists.add(data);
				}
				
				SortByPinyin.sort(lists);
				pagedata.setList(lists);
				setData(pagedata);
			}else{
				getAllCLGC();
				getCLGC();
			}
			break;
		case R.array.jqzd:// 获取机器字段
			String jqId = getIntent().getExtras().getString("jqId");
			String wlId = getIntent().getExtras().getString("wlId");
			getJQZD(jqId, wlId);
			break;
		case R.array.jtgj:// 获取交通工具
			getJTGJ();
			break;
		case R.array.bjwl:// 获取故障部件
			getGzbj();
			break;
		case R.array.clgcworktype:
			getClgcWorktype();
			break;
		case R.array.cxfwzry:
			getFwzrys();
			break;
		case R.array.fylx:
			getFeeLx();
			break;
		default:
			break;
		}

	}

	/**
	 * 获取 机器字段
	 */
	private void getJQZD(String jqId, String wlId) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		Log.e("jqid+wlid", "jqId:"+jqId+",  wlId:"+wlId);
		params.put("jqid", jqId);
		params.put("wlid", wlId);
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, typedatas[KEY_URL]), params, new AjaxHttpCallBack<BasePageBean<JqzdDataBean>>(this,
				new TypeToken<BasePageBean<JqzdDataBean>>() {
				}.getType(), true) {


			@Override
			public void onFinish() {
				getListviewObj().getListview().stopRefresh();
				getListviewObj().getListview().stopLoadMore();
			}
			
			@Override
			public void onError(int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				Log.e("error", errorNo + ", "+strMsg);
				getListviewObj().setStatus(LoadingEnum.NODATA, "没有数据");
			}
			
			@Override
			public void onDo(BasePageBean<JqzdDataBean> basePageBean) {
				//后台返回数据与Bean类型不一致时跳到onError方法中去
				Log.e("onDo", basePageBean.toString());
				listJqzdData = basePageBean.getList();
				if(listJqzdData != null && listJqzdData.size() > 0) {
					setJQZDData(basePageBean);
				}else {
					getListviewObj().setStatus(LoadingEnum.NODATA, "没有数据");
				}
				
				/*
				BasePageBean<CommonSelectGZBJ> pagedata = (BasePageBean) basePageBean;
				List<CommonSelectGZBJ> lists = new ArrayList<CommonSelectGZBJ>();
				for (int i = 0; i < basePageBean.getList().size(); i++) {
					CommonSelectGZBJ data = new CommonSelectGZBJ();
					data.setText(basePageBean.getList().get(i).getAttrName());
					data.setPathname(basePageBean.getList().get(i).getWlPathName());
					data.setValue(basePageBean.getList().get(i).getAttrCodeId());
					lists.add(data);
				}
				SortByPinyinGZ.sort(lists);
				pagedata.setList(lists);
				setGZBJData(pagedata);
				*/
			};
		});
	}

	

	private void getFeeLx() {
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();

		fh.post(KingTellerConfigUtils.CreateUrl(this, typedatas[KEY_URL]), params,
				new AjaxHttpCallBack<BasePageBean<LogisticFeeTypeBean>>(this,
						new TypeToken<BasePageBean<LogisticFeeTypeBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}
					
					@Override
					public void onDo(BasePageBean<LogisticFeeTypeBean> basePageBean) {
						BasePageBean<CommonSelectData> pagedata = (BasePageBean) basePageBean;
						List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
						for (int i = 0; i < basePageBean.getList().size(); i++) {
							CommonSelectData data = new CommonSelectData();
							data.setText(basePageBean.getList().get(i).getEname());
							data.setValue(basePageBean.getList().get(i).getId());

							lists.add(data);
						}
						SortByPinyin.sort(lists);
						pagedata.setList(lists);
						setData(pagedata);
					};

				});
	}
	
	private void getClgcWorktype(){
		
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		//Cursor c = database.rawQuery("select _id,pathname from tb_sm_wl_tree_info where parent_id=?;",new String[]{eatra_data});
		Cursor c = database.rawQuery("select _id,handle_sub from tb_kfwh_worktype_handlesub where parent_id=?;",new String[]{eatra_data});
		
		Log.e("111111111111111",eatra_data);
		BasePageBean<CommonSelectData> pagedata = new BasePageBean<CommonSelectData>();
		List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
		
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) {  
			CommonSelectData data = new CommonSelectData();
			data.setText(c.getString(c.getColumnIndex("handle_sub")));
			data.setValue(c.getString(c.getColumnIndex("_id")));
			
//			byte[] bname = c.getBlob(c.getColumnIndex("handle_sub"));
//
//			try {
//				if(eatra_data.equals("OTHER")){
//					data.setText(new String(bname,"GBK"));
//				}else{
//					data.setText(new String(bname,"UTF-8"));
//				}
//				data.setValue(c.getString(c.getColumnIndex("_id")));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			lists.add(data);
			c.moveToNext();   
		}   
		
		//SortByPinyin.sort(lists);
		pagedata.setList(lists);
		setData(pagedata);
		c.close();
		database.close();
	}

	/**
	 * 获取交通工具
	 */
	private void getJTGJ() {
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();

		params.put("id", eatra_data);

		fh.post(KingTellerConfigUtils.CreateUrl(this, typedatas[KEY_URL]), params,
				new AjaxHttpCallBack<BasePageBean<FeeModeBean>>(this,
						new TypeToken<BasePageBean<FeeModeBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}

					@Override
					public void onDo(BasePageBean<FeeModeBean> basePageBean) {
						BasePageBean<CommonSelectData> pagedata = (BasePageBean) basePageBean;
						List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
						for (int i = 0; i < basePageBean.getList().size(); i++) {
							CommonSelectData data = new CommonSelectData();
							data.setText(basePageBean.getList().get(i)
									.getModeName());
							data.setValue(basePageBean.getList().get(i).getId());

							lists.add(data);
						}
						SortByPinyin.sort(lists);
						pagedata.setList(lists);
						setData(pagedata);
					};

				});
	}

	private void getAllCLGC(){

		String maintainDataGZMK = KingTellerDbUtils.getMaintainInfoClgcDataBase(gzmkdb, "gzmk");
		if (!KingTellerJudgeUtils.isEmpty(maintainDataGZMK)) {
			List<BjWlBean> bjList = gson.fromJson(maintainDataGZMK,new TypeToken<List<BjWlBean>>() {}.getType());
			for (int i = 0; i < bjList.size(); i++) {
				String maintainDataGZMS = KingTellerDbUtils.getMaintainInfoClgcDataBase(gzmsdb, bjList.get(i).getId()+"gzms");
				if(!KingTellerJudgeUtils.isEmpty(maintainDataGZMS)){
					List<RTroubleRemarkBean> trouList = gson.fromJson(maintainDataGZMS, new TypeToken<List<RTroubleRemarkBean>>(){}.getType());
					
					for(int j = 0; j < trouList.size();j ++){
						KTHttpClient fh = new KTHttpClient(true);
						AjaxParams params = new AjaxParams();
						params.put("pId", trouList.get(j).getId());
						
						final String pid = trouList.get(j).getId();
						fh.post(KingTellerConfigUtils.CreateUrl(this, typedatas[KEY_URL]), params,
								new AjaxHttpCallBack<BasePageBean<HandleSubBean>>(this,
										new TypeToken<BasePageBean<HandleSubBean>>() {
										}.getType(), true) {

									@Override
									public void onFinish() {
										getListviewObj().getListview().stopRefresh();
										getListviewObj().getListview().stopLoadMore();
									}

									@Override
									public void onDo(BasePageBean<HandleSubBean> basePageBean) {
										KingTellerDbUtils.saveMaintainInfoClgcDataBase(mContext,
												pid+"clgc", gson.toJson(basePageBean.getList()).toString(), 1);
									};

								});
					}
				}
			}

		}
	
	}
	
	//查询服务站人员
		private void getFwzrys(){
			
			KTHttpClient fh = new KTHttpClient(true);
			AjaxParams params = new AjaxParams();

			fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CxfwzryUrl),
					params, new AjaxHttpCallBack<BasePageBean<AssignWorkerNameBean>>(this,
							new TypeToken<BasePageBean<AssignWorkerNameBean>>() {
							}.getType(), true) {
						
						@Override
						public void onFinish() {
							getListviewObj().getListview().stopRefresh();
						}
						
						@Override
						public void onDo(BasePageBean<AssignWorkerNameBean> basePageBean) {
							BasePageBean<CommonSelectData> pagedata=(BasePageBean) basePageBean;
							List<CommonSelectData> lists=new ArrayList<CommonSelectData>();
							for (int i = 0; i < basePageBean.getList().size(); i++) {
								CommonSelectData data=new CommonSelectData();
								data.setText(basePageBean.getList().get(i).getUserName());
								data.setValue(basePageBean.getList().get(i).getUserName());
								lists.add(data);
							}
							pagedata.setList(lists);
							setData(pagedata);
						};
					});
		}
	
	/**
	 * 获取处理过程
	 */
	private void getCLGC() {

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("pId", eatra_data);

		fh.post(KingTellerConfigUtils.CreateUrl(this, typedatas[KEY_URL]), params,
				new AjaxHttpCallBack<BasePageBean<HandleSubBean>>(this,
						new TypeToken<BasePageBean<HandleSubBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}

					@Override
					public void onDo(BasePageBean<HandleSubBean> basePageBean) {
						KingTellerDbUtils.saveMaintainInfoClgcDataBase(mContext,
								eatra_data+"clgc", gson.toJson(basePageBean.getList()).toString(), 1);
						BasePageBean<CommonSelectData> pagedata = (BasePageBean) basePageBean;
						List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
						for (int i = 0; i < basePageBean.getList().size(); i++) {
							CommonSelectData data = new CommonSelectData();
							data.setText(basePageBean.getList().get(i).getHandleSub());
							data.setValue(basePageBean.getList().get(i).getId());

							lists.add(data);
						}
						pagedata.setList(lists);
						setData(pagedata);
					};

				});
	}
	
	private void getGzms(){
		
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		Cursor c = database.rawQuery("select _id,trouble_remark,path_name from tb_kfwh_trouble_remark where parent_id=? and two_level_id=?;",new String[]{eatra_data,troubleModule});
		
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
//				data.setText(new String(bname,"UTF-8"));
//				data.setValue(c.getString(c.getColumnIndex("_id")));
//				data.setPathname(new String(bname1,"UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			lists.add(data);
			c.moveToNext();   
		}   
		
		if(lists.size() == 0){
			c = database.rawQuery("select _id,trouble_remark,path_name from tb_kfwh_trouble_remark where parent_id=?;",new String[]{eatra_data});
			
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
//					data.setText(new String(bname,"UTF-8"));
//					data.setValue(c.getString(c.getColumnIndex("_id")));
//					data.setPathname(new String(bname1,"UTF-8"));
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				lists.add(data);
				c.moveToNext();   
			}   
		}

		if (lists.size() == 0) {
			CommonSelectGZBJ data = new CommonSelectGZBJ();//其它  其他
			data.setText("其他");
			data.setValue("201409291609a");
			data.setPathname("其他");

			lists.add(data);
		}
		
	/*	List<CommonSelectGZBJ> sealists = new ArrayList<CommonSelectGZBJ>();
		if(!CommonUtils.isEmpty(troubleRemark.getFieldValue())){
			if(lists.size()>0){
				for(CommonSelectGZBJ comm:lists){
					if(comm.getText().indexOf(troubleRemark.getFieldValue())>-1){
						sealists.add(comm);
					}
				}
			}
		}*/
		SortByPinyinGZ.sort(lists);
		/*if(null!=sealists && sealists.size()>0){
			pagedata.setList(sealists);
		}else{*/
			pagedata.setList(lists);
		//}
		
		setGZBJData(pagedata);
		c.close();
		database.close();
	}
	
	//故障部件-维护信息
	private void getGzbj(){
		
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		//Cursor c = database.rawQuery("select _id,pathname from tb_sm_wl_tree_info where parent_id=?;",new String[]{eatra_data});
		Cursor c = database.rawQuery("select tr._id,t.wl_name,tr.pathname,t.type_id from tb_sm_wl_tree_info tr,tb_sm_wl_info t " +
				"where t._id=tr.wl_info_id and  tr.pathid like '%->"+eatra_data+"->%' and tr.node_level != 1 ;",null);
		//Cursor c = database.rawQuery("select _id,wl_name,new_code,pathname from view_sm_wl_info where pathid like '%->"+eatra_data+"->%' and node_level != 1;",null);
		BasePageBean<CommonSelectGZBJ> pagedata = new BasePageBean<CommonSelectGZBJ>();
		List<CommonSelectGZBJ> lists = new ArrayList<CommonSelectGZBJ>();
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) {  
			CommonSelectGZBJ data = new CommonSelectGZBJ();
			data.setText(c.getString(c.getColumnIndex("wl_name")));
			data.setPathname(c.getString(c.getColumnIndex("pathname")));
			data.setValue(c.getString(c.getColumnIndex("_id")));
			if(c.getString(c.getColumnIndex("type_id")) != null) {
				data.setType_id(c.getString(c.getColumnIndex("type_id")));
			}
			
//			byte[] bname = c.getBlob(c.getColumnIndex("pathname"));
//			byte[] bname1 = c.getBlob(c.getColumnIndex("wl_name"));
//
//			try {
//				if(pplb.equals("O")){
//					data.setText(new String(bname1,"GBK"));
//					data.setPathname(new String(bname,"GBK"));
//				}else{
//					data.setText(new String(bname1,"UTF-8"));
//					data.setPathname(new String(bname,"UTF-8"));
//				}
//				data.setValue(c.getString(c.getColumnIndex("_id")));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			lists.add(data);
			c.moveToNext();   
		}   
		pagedata.setList(lists);
		setGZBJData(pagedata);
		c.close();
		database.close();
	}
	
	public void getBjmk(){
		/**
		 * select * from tb_sm_wl_tree_info where node_level='1';
		 * */
		
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		
		if(KingTellerJudgeUtils.isEmpty(pplb)){
			pplb = "Y";
		}
		Cursor c= database.rawQuery("select tr._id,tr.pathname,t.wl_name from tb_sm_wl_tree_info tr,tb_sm_wl_info t " +
						"where t._id=tr.wl_info_id and  tr.node_level=\'1\' and t.pplb=\'"+pplb+"\';", null);
		//Cursor c = database.rawQuery("select _id,pathname from tb_sm_wl_tree_info where node_level=\'1\';",null);
		
		BasePageBean<CommonSelectData> pagedata = new BasePageBean<CommonSelectData>();
		List<CommonSelectData> lists = new ArrayList<CommonSelectData>();

		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) {  
			CommonSelectData data = new CommonSelectData();
			
			data.setText(c.getString(c.getColumnIndex("wl_name")));
			data.setValue(c.getString(c.getColumnIndex("_id")));
			
//			byte[] bname = c.getBlob(c.getColumnIndex("pathname"));
//			byte[] bname1 = c.getBlob(c.getColumnIndex("wl_name"));
//
//			try {
//				if(pplb.equals("O")){
//					data.setText(new String(bname1,"GBK"));
//				}else{
//					data.setText(new String(bname1,"UTF-8"));
//				}
//				
//				data.setValue(c.getString(c.getColumnIndex("_id")));
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
	
	private void setJQZDData(BasePageBean<JqzdDataBean> basePageBean) {
		// TODO Auto-generated method stub
		getListviewObj().getListview().stopRefresh();
		List<JqzdDataBean> data = basePageBean.getList();
		if (data.size() > 0) {
			jqzdAdapter.setLists(data);
			getListviewObj().setStatus(LoadingEnum.LISTSHOW);
		} else {
			getListviewObj().setStatus(LoadingEnum.NODATA, "没有数据");
		}
	}
	
	private void setGZBJData(BasePageBean<CommonSelectGZBJ> pagedata) {
		// TODO Auto-generated method stub
		getListviewObj().getListview().stopRefresh();
		List<CommonSelectGZBJ> data = pagedata.getList();
		if (data.size() > 0) {
			gzbjAdapter.setLists(data);
			getListviewObj().setStatus(LoadingEnum.LISTSHOW);
		} else {
			getListviewObj().setStatus(LoadingEnum.NODATA, "没有数据");
		}
	}

	private void setData(BasePageBean<CommonSelectData> pagedata) {
		getListviewObj().getListview().stopRefresh();
		List<CommonSelectData> data = pagedata.getList();
		if (data.size() > 0) {
			adapter.setLists(data);
			getListviewObj().setStatus(LoadingEnum.LISTSHOW);
		} else {
			getListviewObj().setStatus(LoadingEnum.NODATA, "没有数据");
		}
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sort(BasePageBean<BjWlBean> data){ 
		
		if(data != null && data.getList().size() == 1){
			return;
		}
		java.util.Collections.sort(data.getList(),new java.util.Comparator(){

			@Override
			public int compare(Object arg0, Object arg1) {
				// TODO Auto-generated method stub
				BjWlBean bj1 = (BjWlBean) arg0;
				BjWlBean bj2 = (BjWlBean) arg1;
				long long1 = bj1.getNodeLevel();
				long long2 = bj2.getNodeLevel();
				if(long1 == long2){
					String pathId1 = bj1.getPathid();
					String pathId2 = bj2.getPathid();
					int flag = pathId1.compareTo(pathId2);
					return flag;
				}else if( long1 > long2){
					return 1;
				}else{
					return -1;
				}
			}
			
		});	
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
//		case R.id.btn_search:
//			getGzms();
//			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int pos,
			long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		if(type == R.array.bjwl){
			intent.putExtra(DATA,
					(CommonSelectGZBJ) adapterView.getItemAtPosition(pos));
		}else if(type == R.array.gzms){
			intent.putExtra(DATA,
					(CommonSelectGZBJ) adapterView.getItemAtPosition(pos));
		}else if(type == R.array.jqzd) {
			intent.putExtra(DATA,
					(JqzdDataBean) adapterView.getItemAtPosition(pos));
		}else {
			intent.putExtra(DATA,
					(CommonSelectData) adapterView.getItemAtPosition(pos));
		}
		
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void onRefresh() {
		getData();
		//getListviewObj().getListview().setLoadMoreEnabled(true);
	}

	@Override
	public void onLoadMore() {
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