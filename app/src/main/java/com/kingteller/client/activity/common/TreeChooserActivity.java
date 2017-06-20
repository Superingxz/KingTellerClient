package com.kingteller.client.activity.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.common.fragment.TreeListFragment;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.TreeBean;
import com.kingteller.client.bean.workorder.BjWlBean;
import com.kingteller.client.bean.workorder.FeeTypeBean;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.KingTellerDb;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.framework.utils.Logger;

/**
 * 通用树形选择器
 * 
 * @author 王定波
 * 
 */
public class TreeChooserActivity extends FragmentActivity implements
		OnBackStackChangedListener {

	public static final String PID = "pid";
	public static final String TYPE = "type";
	public static List<TreeBean> datalist = new ArrayList<TreeBean>();
	public static String ROOTPID = "0";
	private final static int KEY_TITLE = 0;
	private final static int KEY_URL = 1;

	private FragmentManager mFragmentManager;
	private String mPid;
	private String mType;
	private String title;
	private String pplb="";
	private String type_id = "";

	private int type;
	private String[] typedatas;
	private String eatra_data;
	private Gson gson = new Gson();
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tree_chooser);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		mContext = TreeChooserActivity.this;
		
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		
		ROOTPID="0";
		mFragmentManager = getSupportFragmentManager();
		mFragmentManager.addOnBackStackChangedListener(this);
		
		initData();
		
		if (savedInstanceState == null) {
			mPid = ROOTPID;
		} else {
			mPid = savedInstanceState.getString(PID);
		}
		
		title_righttwo_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		title_left_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
	}

	private void initData() {
		type_id = getIntent().getStringExtra("type_id");
		pplb = getIntent().getStringExtra("pplb");
		type = getIntent().getIntExtra(CommonSelectDataActivity.TYPE, 0);
		eatra_data = getIntent().getStringExtra(CommonSelectDataActivity.EXTRADATA);
		title = getIntent().getStringExtra(CommonSelectDataActivity.TITLE);
		typedatas = getResources().getStringArray(type);
		
		if(type == R.array.feetype){
			mType = "费用";
		}else if(type == R.array.bjwl){
			mType = "备件";
		}
		
		datalist = new ArrayList<TreeBean>();
		Intent intent = getIntent();
		Bundle bundles = intent.getExtras();
		if (bundles != null && bundles.containsKey(CommonSelectDataActivity.TYPE)) {
			int type = bundles.getInt(CommonSelectDataActivity.TYPE, 0);
			switch (type) {
			case R.array.feetype:
				getFeeTypes();
				break;
			case R.array.bjwl:
				getBjwl();
				break;
			default:
				break;
			}

		}
		title = KingTellerJudgeUtils.isEmpty(title) ? typedatas[KEY_TITLE] : title;
		setTitle(title);

	}

	private void getFeeTypes() {

		KTHttpClient fh = new KTHttpClient(true);

		fh.post(KingTellerConfigUtils.CreateUrl(this, typedatas[KEY_URL]),
				new AjaxHttpCallBack<BasePageBean<FeeTypeBean>>(this,
						new TypeToken<BasePageBean<FeeTypeBean>>() {
						}.getType(), true) {

					@Override
					public void onDo(final BasePageBean<FeeTypeBean> lists) {
			
						for (int i = 0; i < lists.getList().size(); i++) {
							TreeBean data = new TreeBean();
							data.setId(lists.getList().get(i).getId());
							data.setPid(KingTellerJudgeUtils.isEmpty(lists.getList()
									.get(i).getPid()) ? ROOTPID : lists
									.getList().get(i).getPid());
							data.setTitle(lists.getList().get(i).geteName());
							datalist.add(data);
						}

						addfirstFragment();

					}
				});
	}

	//获取所有的故障部件
	
	private void getBjwl(){
		
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		Cursor c = null;
		mPid = eatra_data;
		List<TreeBean> list = new ArrayList<TreeBean>();
		//TODO 更新 硬件配置 时，删除下一句  type_id = "";
		type_id = "";
		if("原装物料".equals(title)){
			if("".equals(type_id)) {
				c = database.rawQuery("select tr._id, t.wl_name, tr.parent_id "
						+ "from tb_sm_wl_tree_info tr, tb_sm_wl_info t " 
						+ "where t._id = tr.wl_info_id "
						+ "and  tr.pathid like "+"\'%->"+ eatra_data +"%\';",null);
			}else {
				c = database.rawQuery("select tr._id, t.wl_name, tr.parent_id "
					+ "from tb_sm_wl_tree_info tr, tb_sm_wl_info t " 
					+ "where t._id = tr.wl_info_id "
					+ "and tr.pathid like "+"\'%->"+ eatra_data +"%\'"
					+ "and t.type_id = '"+ type_id +"';",null);
			}
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) { 
				TreeBean data = new TreeBean();
				data.setTitle(c.getString(c.getColumnIndex("wl_name")));
				data.setId(c.getString(c.getColumnIndex("_id")));
				data.setPid(c.getString(c.getColumnIndex("parent_id")));
				list.add(data);
				c.moveToNext(); 
			}
			
		}else if("安装物料".equals(title)){
			Log.e("sql-eatra_data---",eatra_data);
			if("".equals(type_id)) {
				c = database.rawQuery("select tr._id,t.wl_name,tr.parent_id "
						+ "from tb_sm_wl_tree_info tr,tb_sm_wl_info t "
						+ "where t._id = tr.wl_info_id "
						+ "and tr.wl_info_id in (select wl_info_id from tb_bj_wl_compatible_temp where wl_type_id in "
						+ "(select wl_type_id from tb_bj_wl_compatible_temp where wl_info_id='"+ eatra_data +"'));",null);
			}else {
				c = database.rawQuery("select tr._id,t.wl_name,tr.parent_id "
					+ "from tb_sm_wl_tree_info tr,tb_sm_wl_info t "
					+ "where t._id = tr.wl_info_id "
					+ "and tr.wl_info_id in (select wl_info_id from tb_bj_wl_compatible_temp where wl_type_id in "
					+ "(select wl_type_id from tb_bj_wl_compatible_temp where wl_info_id='"+ eatra_data +"')"
					+ "and t.type_id = '" + type_id + "');",null);
			
			}
			
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) { 
				TreeBean data = new TreeBean();
				data.setTitle(c.getString(c.getColumnIndex("wl_name")));
				data.setId(c.getString(c.getColumnIndex("_id")));
				data.setPid(c.getString(c.getColumnIndex("parent_id")));
				list.add(data);
				c.moveToNext(); 
				Log.e("第一次查询安装物料：","添加到列表"+data.toString());
			}
			
			List<TreeBean> list2 = new ArrayList<TreeBean>();
			c = database.rawQuery("select tr._id, t.wl_name, tr.parent_id "
							+"from tb_sm_wl_tree_info tr, tb_sm_wl_info t "
							+"where tr.wl_info_id = t._id "
							+"and (t.new_code like \'%.%\' or t.new_code like '%\\%') "
							+"and tr.pathid like \'%->"+ eatra_data +"%\'"                                  
							+"and tr.node_level != 1", null);
			
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) { 
				Log.e("第二次查询安装物料：","查到"+i);
				TreeBean data = new TreeBean();
				data.setTitle(c.getString(c.getColumnIndex("wl_name")));
				data.setId(c.getString(c.getColumnIndex("_id")));
				data.setPid(c.getString(c.getColumnIndex("parent_id")));
				
				//第二次查询结果与第一次查询的结果去重
				if(list.size() > 0) {
					boolean already = false;
					for(TreeBean tb : list) {
						if(tb.getId().equals(data.getId())) {
							already = true;
							break;
						}
					}
					if(!already) {
						Log.e("第二次查询安装物料：","添加到列表"+data.toString());
						list2.add(data);
					}
				}else {
					list2.add(data);
				}
				c.moveToNext(); 
			}
			list.addAll(list2);
		}
/*
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) { 
			TreeBean data = new TreeBean();
			data.setTitle(c.getString(c.getColumnIndex("wl_name")));
			data.setId(c.getString(c.getColumnIndex("_id")));
			data.setPid(c.getString(c.getColumnIndex("parent_id")));
			
//			byte[] bname = c.getBlob(c.getColumnIndex("wl_name"));
//			try {
//				//判断乱码
//				if(!CommonUtils.isEmpty(pplb) &&pplb.equals("O")){
//					data.setTitle(new String(bname,"GBK"));
//				}else{
//					data.setTitle(new String(bname,"UTF-8"));
//				}
//				data.setId(c.getString(c.getColumnIndex("_id")));
//				data.setPid(c.getString(c.getColumnIndex("parent_id")));
//				
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			list.add(data);
			c.moveToNext(); 
		}
 */
		datalist = list;
		addfirstFragment();
		
		c.close();
		database.close();
		
	}
	
	//获取故障部件
	private void getBjWl() {
		
		ROOTPID=eatra_data;
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("pId",eatra_data);
		params.put("flag", "3");
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, typedatas[KEY_URL]), params,
				new AjaxHttpCallBack<BasePageBean<BjWlBean>>(this,
						new TypeToken<BasePageBean<BjWlBean>>() {
						}.getType(), true) {
					@Override
					public void onDo(final BasePageBean<BjWlBean> lists) {
						if(title.equals("安装物料")){
							KingTellerDbUtils.saveMaintainInfoClgcDataBase(mContext, 
									eatra_data+"azwl", gson.toJson(lists.getList()).toString(), 1);
						}else if(title.equals("原装物料")){
							KingTellerDbUtils.saveMaintainInfoClgcDataBase(mContext, 
									eatra_data+"yzwl", gson.toJson(lists.getList()).toString(), 1);
						}
						
						List<TreeBean> list = new ArrayList<TreeBean>();
						for (int i = 0; i < lists.getList().size(); i++) {
							TreeBean data = new TreeBean();
							data.setId(lists.getList().get(i).getId());
							data.setPid(lists.getList().get(i).getParentId());
							data.setTitle(lists.getList().get(i).getWlName());
							list.add(data);
						}

						datalist = list;
						addfirstFragment();
					}
				});

	}

	private void addfirstFragment() {
		Log.e("TreeChooseActivity",mPid + mType);
		addFragment(mPid, mType);
	}

	@Override
	public void setTitle(CharSequence title) {
		title_text.setText(title);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(PID, mPid);
	}

	@Override
	public void onBackStackChanged() {
		String mtitle = title;

		int count = mFragmentManager.getBackStackEntryCount();
		if (count > 0) {
			BackStackEntry fragment = mFragmentManager
					.getBackStackEntryAt(count - 1);
			mtitle = fragment.getName();
		}

		setTitle(mtitle);
	}

	/**
	 * Add the initial Fragment with given path.
	 * 
	 * @param path
	 *            The absolute path of the file (directory) to display.
	 */
	private void addFragment(String mPid, String mType) {
		TreeListFragment explorerFragment = TreeListFragment.newInstance(mPid, mType);
		
		mFragmentManager.beginTransaction().add(R.id.explorer_fragment, explorerFragment).commit();
	}

	/**
	 * "Replace" the existing Fragment with a new one using given path. We're
	 * really adding a Fragment to the back stack.
	 * 
	 * @param path
	 *            The absolute path of the file (directory) to display.
	 */
	private void replaceFragment(TreeBean data) {
		TreeListFragment explorerFragment = TreeListFragment.newInstance(data
				.getId());
		mFragmentManager.beginTransaction()
				.replace(R.id.explorer_fragment, explorerFragment)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.addToBackStack(data.getTitle()).commit();
	}

	/**
	 * Finish this Activity with a result code and URI of the selected file.
	 * 
	 * @param file
	 *            The file selected.
	 */
	private void finishWithResult(TreeBean data) {
		if (data != null) {
			Intent intent = new Intent();
			intent.putExtra(CommonSelectDataActivity.DATA, data);
			setResult(RESULT_OK, intent);
			finish();
		} else {
			setResult(RESULT_CANCELED);
			finish();
		}
	}

	/**
	 * Called when the user selects a File
	 * 
	 * @param file
	 *            The file that was selected
	 */
	public void onFileSelected(TreeBean data) {
		if (data != null) {
			mPid = data.getId();

			if (data.isLast()) {
				finishWithResult(data);

			} else {
				replaceFragment(data);
			}
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
