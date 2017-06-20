package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.QRDotMachineAdapter;
import com.kingteller.client.adapter.QRDotMachineSelectListAdapter;
import com.kingteller.client.adapter.SelectListAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.db.QRDotMachine;
import com.kingteller.client.bean.qrcode.QRBarCodeBean;
import com.kingteller.client.bean.qrcode.QRDotMachineBean;
import com.kingteller.client.bean.qrcode.QRDotMachineWdJqBean;
import com.kingteller.client.bean.qrcode.QRGetGDotMachineListsBean;
import com.kingteller.client.bean.qrcode.QRWRBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.NormalListDialog;
import com.kingteller.client.view.dialog.entity.NormalListMenuItem;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.log.L;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.modules.zxing.activity.CaptureActivity;

/**
 * 网点机器扫描
 */

public class QRDotMachineCargoScanActivict extends Activity implements OnClickListener{
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn;
	
	private TextView mSsfwz, mJqXz;
	private EditText mJqBm;
	private Button mOk,mCancel,mEwm_Opencame;
	private ListView mDotMachineListView;
	
	private QRDotMachineAdapter listadapter; 
	private KTAlertDialog mDotDialogList;
	private String mEwm_Number, mDot_jqid, mDot_wdid, mDot_jqbm, mDot_wdSbjc, mDot_ssfwz, mDot_Type, mDot_TopName;
	private List<QRDotMachineBean> mShowList = new ArrayList<QRDotMachineBean>();
	private List<QRDotMachineBean> mGetDataList = new ArrayList<QRDotMachineBean>();
	private List<QRDotMachineBean> mBaoCunList = new ArrayList<QRDotMachineBean>();
	private List<QRDotMachineBean> mSaoMiaoList = new ArrayList<QRDotMachineBean>();
	private List<CommonSelectData> mSelectDataList; //该网点机器列表
	private QRGetGDotMachineListsBean mGetGDotMachineLists;
	private Gson gson;
	private User user;

	private String[] mStringItem;
	private List<NormalListMenuItem> mStringItemList;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_qrcode_dotmachine_cargoscan);
		KingTellerApplication.addActivity(this);

		mContext = QRDotMachineCargoScanActivict.this;
		gson = new Gson();
		
		KingTellerStaticConfig.QR_DOTMACHINE_LISTDATA = false;
		
		getActivityData();
		initUI();
		initData();
	}
	
	private void getActivityData(){
		mDot_Type = (String) getIntent().getSerializableExtra("Type"); //1 扫描主页面进来    2 我的工单进来
		if("1".equals(mDot_Type)){
			mDot_wdid = "";
			mDot_wdSbjc = "";
			mDot_jqid = "";
			mDot_jqbm = "";
		}else if("2".equals(mDot_Type)){ 
			mDot_jqid = (String) getIntent().getSerializableExtra("JqId"); 
			mDot_jqbm = (String) getIntent().getSerializableExtra("JqBm"); 
			mDot_wdid = (String) getIntent().getSerializableExtra("WdId"); 
			mDot_wdSbjc = (String) getIntent().getSerializableExtra("WdMc"); 
			mDot_ssfwz = (String) getIntent().getSerializableExtra("SsFwz"); 
			
		}
	}
	
	private void initUI() {	
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		
		
		mOk = (Button) findViewById(R.id.ewm_preservation);
		mCancel = (Button) findViewById(R.id.ewm_cancel);
		mEwm_Opencame = (Button) findViewById(R.id.ewm_opencame);
		
		mSsfwz = (TextView) findViewById(R.id.wdjq_ssfwz);
		mJqBm = (EditText) findViewById(R.id.wdjq_jqbm);
		
		mJqXz = (TextView) findViewById(R.id.wdjq_select_jqbm);
		
		if("1".equals(mDot_Type)){
			title_text.setText("机器扫码");
		}else if("2".equals(mDot_Type)){
			title_text.setText("二维码录入");
		}
		
		mDotMachineListView = (ListView) findViewById(R.id.wdjq_listview);
		mDotMachineListView.setAdapter(listadapter);
		
		mEwm_Opencame.setOnClickListener(this);
		mJqXz.setOnClickListener(this);
		mOk.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	private void initData() {	
		user = User.getInfo(mContext);
		mDot_ssfwz = user.getOrgName();
		
		mSsfwz.setText("所属服务站：" + mDot_ssfwz);
		mJqBm.setText(mDot_jqbm);


		if("1".equals(mDot_Type)){ 

		}else if("2".equals(mDot_Type)){ 
			//从数据库读取数据
			QRDotMachine data = KingTellerDbUtils.getDotMachineToDataBase(mContext, mDot_jqid);
			if (data != null){
				mBaoCunList = KingTellerJsonUtils.getPersons(data.getDotMachineData(), QRDotMachineBean.class);
				setJqInformation(mBaoCunList, data.getIsSuccess());
			}else{
				getQRQjXxData(mDot_jqid);
			}
		}
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			isOpenDialog();
			break;
		case R.id.ewm_opencame:
			if(KingTellerJudgeUtils.isEmpty(mDot_jqid)){
				T.showShort(mContext, "机器编码不能为空!");
				return;
			}
			Intent intent = new Intent(QRDotMachineCargoScanActivict.this, CaptureActivity.class);
			startActivityForResult(intent, KingTellerStaticConfig.QRCODE_NUM);
			break;
		case R.id.ewm_preservation:
			getQRBaoCunData();
			break;
		case R.id.ewm_cancel:
			isOpenDialog();
			break;
		case R.id.wdjq_select_jqbm:
			OpenSearchJqidData(mJqBm.getText().toString());
		default:
			break;
		}
		
	}

	//捕捉返回键
	public void onBackPressed() {
		isOpenDialog();
	}
	
	// TODO 扫描返回函数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.QRCODE_NUM:
			if (resultCode == RESULT_OK) {
				mEwm_Number = (String) data.getSerializableExtra("ewm_num");
				if(getcontains(mEwm_Number)){//判断是否已经扫描
					OpenDialog("条码已被扫描！", 1);
				}else{
					getDotMachineEwmMessage(mEwm_Number);
				}
				
			}
			break;
		default:
			break;
		}
	}
	
	public void ChangeListView(List<QRDotMachineBean> mList ){
		Log.e("44444444444444show",gson.toJson(mList));
		
		try {
			listadapter = new QRDotMachineAdapter<QRDotMachineBean>(mDotMachineListView, this, mList, 10);
			listadapter.setAllOpenTree();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		mDotMachineListView.setAdapter(listadapter);
	}

	
	/**
	 * TODO 1.3 搜索机器编号
	 * @param mSelectList
	 */
	public void OpenSearchJqidData(String mWdjqbh){
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		if("".equals(mWdjqbh) || mWdjqbh.length() < 3){
			T.showShort(mContext, "请输入3个字符以上!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqBm", mWdjqbh);
		params.put("orgId", user.getOrgId());
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CsJqName), params,
			new AjaxHttpCallBack<List<QRDotMachineWdJqBean>>(this,
					new TypeToken<List<QRDotMachineWdJqBean>>() {
					}.getType(), true) {
		
				@Override
				public void onError(int errorNo, String strMsg) {
					super.onError(errorNo, strMsg);
					KingTellerProgressUtils.closeProgress();
					T.showShort(mContext, "数据访问超时!");
				}
				
				@Override
				public void onStart() {
					KingTellerProgressUtils.showProgress(mContext, "搜索机器编号中...");
				}

				@Override
				public void onDo(List<QRDotMachineWdJqBean> data) { 
					KingTellerProgressUtils.closeProgress();
					if("".equals(data.get(0).getCode())){
						mStringItem = new String[data.size()];
						mStringItemList = new ArrayList<NormalListMenuItem>();
						
						for(int i = 0; i < data.size(); i++){
							NormalListMenuItem mSelectData = new NormalListMenuItem();
							mSelectData.setResName(data.get(i).getJqBm());
							mSelectData.setResId(data.get(i).getJqId());
							
							mStringItem [i] = data.get(i).getJqBm();
							mStringItemList.add(mSelectData);
						}
						
						final NormalListDialog dialog_ssck = new NormalListDialog(mContext, mStringItem);
						dialog_ssck.title("请选择机器编码")//
					                .layoutAnimation(null)
					                .titleBgColor(Color.parseColor("#409ED7"))//
					                .itemPressColor(Color.parseColor("#85D3EF"))//
					                .itemTextColor(Color.parseColor("#303030"))//
					                .show();
						dialog_ssck.setOnOperItemClickL(new OnOperItemClickL() {
					            @Override
					            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
					            	dialog_ssck.dismiss();
					            	
					            	NormalListMenuItem data = mStringItemList.get(position);
					            	
					            	mDot_jqbm = data.getResName();
									mDot_jqid = data.getResId();
									
									mJqBm.setText(mDot_jqbm);
									
									getQRQjXxData(mDot_jqid);//获取其他机器信息树
					            }
					        });
					}else{
						OpenDialog(data.get(0).getCode(), 1);
					}

				};
			});
	}
	
	public boolean isNumeric(String str){
		   Pattern pattern = Pattern.compile("[0-9]*");
		   Matcher isNum = pattern.matcher(str);
		   if(!isNum.matches()){
		       return false;
		   }
		   return true;
		}
	
	/**
	 * TODO 2.1获取当前网点机器列表与机器信息
	 */
	public void getQRQjXxData(String mJqid) {

		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqid", mJqid);//机器id

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WdJQXinXi),params, 
				new AjaxHttpCallBack<QRGetGDotMachineListsBean>(this,
						new TypeToken<QRGetGDotMachineListsBean>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						// 开始http请求的时候回调
						KingTellerProgressUtils.showProgress(mContext, "正在查询中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						
						Log.e("获取机器信息","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(QRGetGDotMachineListsBean data) {
						KingTellerProgressUtils.closeProgress();
						mGetGDotMachineLists = new QRGetGDotMachineListsBean();
						if("".equals(data.getCode())){
							mGetGDotMachineLists = data;
							setJqInformation(mGetGDotMachineLists.getWlList(), 1);
						}else{
							OpenDialog(data.getCode(), 1);
						}

					};
				});
	}
	
	/**
	 * TODO 2.2获取ATM物料二维码信息
	 */
	private void getDotMachineEwmMessage(String barcode) {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("barcode", barcode);//二维码号
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.AtmWlXinXi), params,
			new AjaxHttpCallBack<QRBarCodeBean>(this,
					new TypeToken<QRBarCodeBean>() {
					}.getType(), true) {
		
				@Override
				public void onError(int errorNo, String strMsg) {
					super.onError(errorNo, strMsg);
					
					KingTellerProgressUtils.closeProgress();
					Log.e("ATMSearchWl","onError" + errorNo + ":" + strMsg);
					T.showShort(mContext, "数据访问超时!");
				}
				
				@Override
				public void onStart() {
					KingTellerProgressUtils.showProgress(mContext, "查询二维码信息中...");
				}

				@Override
				public void onDo(QRBarCodeBean data) { 
					KingTellerProgressUtils.closeProgress();
					
					if("".equals(data.getCode())){
						Log.e("设备录入查询物料信息", data.getWlName() + ": " + data.getNewCode() + ": " + data.getWlDotType());
						getEwmXinxi(mEwm_Number, data.getWlName(), data.getNewCode(), data.getWlDotType());
					}else{
						OpenDialog(data.getCode(), 1);
					}
				};
			});
	}
	
	/**
	 * TODO 3.1保存机器扫码信息
	 */
	public void getQRBaoCunData() {

		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		if(mBaoCunList.size() <= 0 ){
			T.showShort(mContext, "请扫描物料信息!");
			return;
		}
		
		if(getBaoCuncontains("1")){
			if("".equals(mDot_TopName) || mDot_TopName == null){
				T.showShort(mContext, "还有其部件物料还未扫描, 请继续扫描!");
			}else{
				T.showShort(mContext, "还有：" + mDot_TopName + " 所属的物料未被扫描!");
			}
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("wdjqxxList", gson.toJson(mBaoCunList));
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WdJQBC),params, 
				new AjaxHttpCallBack<QRGetGDotMachineListsBean>(this,
						new TypeToken<QRGetGDotMachineListsBean>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						// 开始http请求的时候回调
						KingTellerProgressUtils.showProgress(mContext, "正在保存中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg); 
						
						KingTellerProgressUtils.closeProgress();
						Log.e("保存机器信息","onError" + errorNo + ":" + strMsg);
						
						//保存暂存数据
						if(mGetDataList.size()>0){
							setBaoCunDotMachineData(1);
						}else{
							setBaoCunDotMachineData(0);
						}
						
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(QRGetGDotMachineListsBean data) {
						KingTellerProgressUtils.closeProgress();
						
						if("".equals(data.getCode())){
							OpenDialog("保存成功！", 1);
//							setJqInformation(mBaoCunList);
//							mBaoCunList = new ArrayList<QRDotMachineBean>();
//							mSaoMiaoList = new ArrayList<QRDotMachineBean>();
							
							mBaoCunList = new ArrayList<QRDotMachineBean>();
							mShowList = new ArrayList<QRDotMachineBean>();
							mSaoMiaoList = new ArrayList<QRDotMachineBean>();
							
							getQRQjXxData(mDot_jqid);//重新获取数据
							//删除暂存数据
							KingTellerDbUtils.deleteDotMachineToDataBase(mContext, mDot_jqid);
							
						}else{
							OpenDialog("保存失败！" + data.getCode(),1);
						}
					};
				});
	}
	
	/**
	 * 组建结果返回机器信息树
	 */
	public void setJqInformation(List<QRDotMachineBean> mlist, int isSuccess){
		mShowList = new ArrayList<QRDotMachineBean>();
		mGetDataList = new ArrayList<QRDotMachineBean>();

		if(mlist != null){
			if(mlist.size()>0){
				if(isSuccess == 1){
					for(QRDotMachineBean data: mlist){//如果  当前扫的 为ture   是否新增为ture
						mShowList.add(new QRDotMachineBean(data.getGenreId(), data.getGenrePid(), data.getWlName() + "," 
								+ data.getGenreId() + "," 
								+ data.getWlBarCode() + "," + "false" + "," + data.getIsNewadd()));  
						mGetDataList.add(new QRDotMachineBean(mDot_jqid, mDot_jqbm, data.getGenreId(), 
								data.getGenrePid(), data.getNodeLevel(), data.getWlName(), data.getGenreId(),
								"", data.getWlBarCode(), data.getIsNewadd()));
					}
				}else{
					for(QRDotMachineBean data: mlist){
						mShowList.add(new QRDotMachineBean(data.getGenreId(), data.getGenrePid(), data.getWlName() + "," 
								+ data.getGenreId() + "," 
								+ data.getWlBarCode()+ "," + "false" + "," + data.getIsNewadd()));
						
						mSaoMiaoList.add(new QRDotMachineBean(mDot_jqid, mDot_jqbm, data.getNewCode(), data.getPidNewCode()
								, data.getNodeLevel(), data.getWlName(), data.getGenreId(), data.getGenrePid(), data.getWlBarCode(), data.getIsNewadd()));
					}
				}
			}else{
				OpenDialog("该网点机器未被扫描！", 1);
			}
		}

		ChangeListView(mShowList);

	}
	
	
	/**
	 * 组建扫描后获取本地数据
	 * @param wlewm 物料二维码
	 * @param wlname 物料名称
	 * @param wlnewcode 物料编码
	 * @param wldottype ATM部件类型  0：整机    1：部件
	 */
	private void getEwmXinxi(String wlewm, String wlname ,String wlnewcode, String wldottype){
		//String mWlBm = KingTellerUtils.getJqAndWlBm(ewm);

		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
	
		//1.查询config_id parent_id 层级
		Cursor c = null;
		Cursor n = null;
		
		c = database.rawQuery("select b.config_id, b.parent_id, b.node_level, a.wl_name, a.new_code "
				+ "from tb_bjgl_atm_parts_config_wl a "
				+ "left join tb_bjgl_atm_parts_config b "
				+ "on a.config_id = b.config_id where a.new_code=?;",new String[]{wlnewcode});
		
		if(c.getCount() > 0){
			c.moveToFirst();
			if(c.getCount() > 1){
				List<CommonSelectData> mLists = new ArrayList<>();	
				for (int i = 0; i < c.getCount(); i++) {  
					String path_id = c.getString(c.getColumnIndex("parent_id"));
					
					n = database.rawQuery("select wl_name as path_wl_name , new_code as path_new_code "
							+ "from tb_bjgl_atm_parts_config_wl "
							+ "where config_id=?;",new String[]{path_id});
					n.moveToFirst();
					
					CommonSelectData alldata = new CommonSelectData();
					alldata.setText(c.getString(c.getColumnIndex("wl_name")) +
							"(" + c.getString(c.getColumnIndex("new_code"))+")");
					alldata.setValue(n.getString(n.getColumnIndex("path_wl_name")) +
							"(" + n.getString(n.getColumnIndex("path_new_code"))+")");
					alldata.setObj("┗━" + c.getString(c.getColumnIndex("wl_name")) +
							"(" + c.getString(c.getColumnIndex("new_code"))+")");
					
					alldata.setTextcolor(wlewm + "," + wldottype + ","
							 + c.getString(c.getColumnIndex("config_id")) + ","
							 + c.getString(c.getColumnIndex("parent_id")) + ","
							 + c.getString(c.getColumnIndex("node_level")) + ","
							 + c.getString(c.getColumnIndex("wl_name")) + ","
							 + c.getString(c.getColumnIndex("new_code")) );
					mLists.add(alldata);
					c.moveToNext();
				} 
				n.close();
				
				QRDotMachineSelectListAdapter dotListAdapter = new QRDotMachineSelectListAdapter(mContext, mLists);
				
				mDotDialogList = new KTAlertDialog.Builder(mContext)
				.setTitle("请选择")
				.setCancelable(false)
				.setItems(dotListAdapter, 0, new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View arg1, int pos, long arg3) {
						CommonSelectData selectdata = (CommonSelectData) adapterView.getItemAtPosition(pos);
						
						String [] alldotdatas = selectdata.getTextcolor().split(",");
						
						setEwmXinxi(alldotdatas[0], alldotdatas[1], alldotdatas[2], alldotdatas[3],
									alldotdatas[4], alldotdatas[5], alldotdatas[6], "true");
						
						mDotDialogList.dismiss();
					}
				})
				.setNegativeButton("取消",
						new KTAlertDialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface,int pos) {
								dialogInterface.dismiss();
								T.showShort(mContext, "取消录入!");
							}
						}).create();
	
				mDotDialogList.show();
			}else{
				String onedata = wlewm + "," + wldottype  + "," 
						 + c.getString(c.getColumnIndex("config_id")) + ","
						 + c.getString(c.getColumnIndex("parent_id")) + ","
						 + c.getString(c.getColumnIndex("node_level")) + ","
						 + c.getString(c.getColumnIndex("wl_name")) + ","
						 + c.getString(c.getColumnIndex("new_code"));
				
				String [] onedotdatas = onedata.split(",");
				
				setEwmXinxi(onedotdatas[0], onedotdatas[1], onedotdatas[2], onedotdatas[3],
						 	onedotdatas[4], onedotdatas[5], onedotdatas[6], "true");
			}
		}else{
			OpenDialog("该物料不存在本地数据库, 请更新数据库！", 1);
		}
		
		c.close();
		database.close();
	}

	
	/**
	 * 组建扫描后数据处理
	 * @param ewm 物料编码
	 * @param dottype 机器部件种类 
	 * @param id 当前 物料种类id
	 * @param pid 父  物料种类id
	 * @param nodelevel 物料所在级别  1  2  3 
	 * @param wlname 物料名称
	 * @param newcode 物料编码
	 * @param isnewadd 是否新增
	 */
	private void setEwmXinxi(String ewm, String dottype, 
		String id, String pid, String nodelevel, String wlname, String newcode, String isnewadd){
		
		Log.e("pid",pid);
		Log.e("id",id);
		
		KingTellerStaticConfig.QR_DOTMACHINE_LISTDATA = true;
		
		if("0".equals(pid)){ //整机
			QRDotMachineBean data = new QRDotMachineBean(mDot_jqid, mDot_jqbm, id, pid, nodelevel, wlname, newcode, "0", ewm, isnewadd);
			mSaoMiaoList.add(data);
		}else{
			QRDotMachineBean data = new QRDotMachineBean(mDot_jqid, mDot_jqbm, id, pid, nodelevel, wlname, newcode, "1", ewm, isnewadd);
			mSaoMiaoList.add(data);
		}
		
		if(mSaoMiaoList.size()>1){//根据 id与pid 转换带有相对应的 newcod 与 pidnewcode 
			for(QRDotMachineBean data: mSaoMiaoList){
				getEwmXinxiChang(mSaoMiaoList, data);
			}
		}
		
		
		mShowList = new ArrayList<QRDotMachineBean>();
		mBaoCunList = new ArrayList<QRDotMachineBean>();
		
		/**================================处理扫描数据===============================================**/
		if(mGetDataList.size() > 0){//初始化界面    有数据时
		
			mBaoCunList.addAll(mGetDataList);
			for(QRDotMachineBean data: mSaoMiaoList){
				mBaoCunList.add(new QRDotMachineBean(mDot_jqid, mDot_jqbm, data.getNewCode(), 
						data.getPidNewCode(), data.getNodeLevel(), data.getWlName(), data.getGenreId(),
						data.getGenrePid(), data.getWlBarCode(), data.getIsNewadd()));
			}
			
			/**================================查询上级物料===========================================**/
			for(int i = 0; i<mBaoCunList.size(); i++){ //查询当前树有没有父级  
				if(mBaoCunList.get(i).getGenrePid().equals("1")){
					mBaoCunList.get(i).setGenrePid(setBaoCunList(mBaoCunList.get(i).getPidNewCode()));
				}
			}
			
			int configNun = getWlConfigNun(newcode);
			//如果物料编码为   是否能多个 ：1     不做以下判断
			if(configNun != 1){
				
				/**================================扫到同一物料===========================================**/
				List<String> barcodewllist = setEqualBaoCunCountList(mBaoCunList, newcode, 1);
				if(barcodewllist.size() > 1 ){
					for(String Str: barcodewllist){
						String [] Strs = Str.split(","); 
						if(Strs[1].equals(newcode) && !Strs[2].equals(ewm)){
							for(int n = 0; n<mBaoCunList.size(); n++){
								if(mBaoCunList.get(n).getWlBarCode().equals(Strs[2])){
									mBaoCunList.remove(n);
								}
							}
							for(int n = 0; n<mGetDataList.size(); n++){
								if(mGetDataList.get(n).getWlBarCode().equals(Strs[2])){
									mGetDataList.remove(n);
									OpenDialog("扫的是同种物料,已修改该物料当前的二维码！", 1);
								}
							}
							for(int n = 0; n<mSaoMiaoList.size(); n++){
								if(mSaoMiaoList.get(n).getWlBarCode().equals(Strs[2])){
									mSaoMiaoList.remove(n);
									OpenDialog("扫的是同种物料,已修改该物料当前的二维码！", 1);
								}
							}
						}
					}
				}
				
				
				
				/**================================扫到不同物料,同一物料种类=================================**/
				List<String> barcodelist = setBaoCunCountList(mBaoCunList , newcode, 1);
				if(barcodelist.size() > 1){//如果当前扫的物料  在列表中种类存在两个 就进行更正
					for(String Str: barcodelist){
						if(!Str.equals(ewm)){//如果不相同，把对应的二维码的物料删除
							for(int n = 0; n<mBaoCunList.size(); n++){
								if(mBaoCunList.get(n).getWlBarCode().equals(Str)){
									mBaoCunList.remove(n);
								}
							}
							for(int n = 0; n<mGetDataList.size(); n++){
								if(mGetDataList.get(n).getWlBarCode().equals(Str)){
									mGetDataList.remove(n);
									OpenDialog("扫的是不同种物料,但是属于同一类别,已删除前一物料", 1);
								}
							}
							for(int n = 0; n<mSaoMiaoList.size(); n++){
								if(mSaoMiaoList.get(n).getWlBarCode().equals(Str)){
									mSaoMiaoList.remove(n);
									OpenDialog("扫的是不同种物料,但是属于同一类别,已删除前一物料", 1);
								}
							}
						}
					}
				}
			}
			
		}else{//没数据时
			int configNun = getWlConfigNun(newcode);
			//如果物料编码为   是否能多个 ：1     不做以下判断 
			L.e("atmwl", configNun + "");
			if(configNun != 1){
				
				/**================================扫到同一物料===========================================**/
				List<String> barcodewllist = setEqualBaoCunCountList(mSaoMiaoList, newcode, 0);
				if(barcodewllist.size() > 1 ){
					for(String Str: barcodewllist){
						String [] Strs = Str.split(","); 
						if(Strs[1].equals(newcode) && !Strs[2].equals(ewm)){
							for(int n = 0; n<mSaoMiaoList.size(); n++){
								if(mSaoMiaoList.get(n).getWlBarCode().equals(Strs[2])){
									mSaoMiaoList.remove(n);
									OpenDialog("扫的是同种物料,已修改该物料当前的二维码！", 1);
								}
							}
						}
					}
				}
	
				
				/**================================扫到不同物料,同一物料种类=================================**/
				List<String> barcodelist = setBaoCunCountList(mSaoMiaoList , newcode, 0);
				if(barcodelist.size() > 1){
					for(String Str: barcodelist){
						if(!Str.equals(ewm)){//如果不相同，把对应的二维码的物料删除
							for(int n = 0; n<mSaoMiaoList.size(); n++){
								if(mSaoMiaoList.get(n).getWlBarCode().equals(Str)){
									mSaoMiaoList.remove(n);
									OpenDialog("扫的是不同种物料,但是属于同一类别,已删除前一物料", 1);
								}
							}
						}
					}
				}
			}
			
			/**================================获取保存list===========================================**/
			for(QRDotMachineBean data: mSaoMiaoList){
				mBaoCunList.add(new QRDotMachineBean(mDot_jqid, mDot_jqbm, data.getNewCode(), 
						data.getPidNewCode(), data.getNodeLevel(), data.getWlName(), data.getGenreId(),
						data.getGenrePid(), data.getWlBarCode(), data.getIsNewadd()));
			}
		}
		
		/**================================重新重组展示list===========================================**/
		for(QRDotMachineBean data: mBaoCunList){
			if(data.getWlBarCode().equals(ewm)){ //如果  当前扫的     是否新增为 
				mShowList.add(new QRDotMachineBean(data.getGenreId(), data.getGenrePid(), data.getWlName() + "," 
						+ data.getGenreId() + "," 
						+ data.getWlBarCode() + "," + "true" + "," + data.getIsNewadd()));
			}else{
				mShowList.add(new QRDotMachineBean(data.getGenreId(), data.getGenrePid(), data.getWlName() + "," 
						+ data.getGenreId() + "," 
						+ data.getWlBarCode() + "," + "false" + "," + data.getIsNewadd()));
			}
			
		}

		ChangeListView(mShowList);
		
	}
	
	
	/**
	 * 组建扫描后展示数据树
	 * @param num
	 */
	private void getEwmXinxiChang(List<QRDotMachineBean> dataList, QRDotMachineBean adddata){
        for(QRDotMachineBean data: dataList ){
        	//找有没有父级
        	if(adddata.getGenrePid().equals(data.getGenreId())){
        		for(int i = 0; i<dataList.size(); i++){
        			if(dataList.get(i).getGenreId().equals(adddata.getGenreId())){
        				dataList.get(i).setPidNewCode((data.getNewCode()));
        			}
        		} 
        	}
        }     
	}
	
	
	/**
	 * 返回 存在当前所扫的 相同物料编码 list
	 * @param list
	 * @param code
	 * @param isdata
	 * @return
	 */
	private List<String> setEqualBaoCunCountList(List<QRDotMachineBean> list, String newcode, int isdata){
		List<String> wlStr = new ArrayList<String>();
		if(isdata == 0){//原机器没数据
			for(int i = 0; i<list.size(); i++){
				if(list.get(i).getNewCode().equals(newcode)){
					String genPid = list.get(i).getGenrePid();
					String wlCode = list.get(i).getNewCode();
					String wlBar = list.get(i).getWlBarCode();
					wlStr.add(genPid + "," + wlCode + "," + wlBar);
				}
			}
		}else if(isdata == 1){//原机器有数据
			for(int i = 0; i<list.size(); i++){
				if(list.get(i).getGenreId().equals(newcode)){
					String genPid = list.get(i).getPidNewCode();
					String wlCode = list.get(i).getGenreId();
					String wlBar = list.get(i).getWlBarCode();
					wlStr.add(genPid + "," + wlCode + "," + wlBar);
				}
			}
		}
		return wlStr;	
	}
	
	
	/**
	 * 根据物料编号   查询当前级别的 所有物料   返回 存在当前所扫的物料条码list
	 * @param pid
	 * @return 
	 */
	private List<String> setBaoCunCountList(List<QRDotMachineBean> list, String code, int isdata){
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		List<String> wlStr = new ArrayList<String>();
		//查询 上一级  物料
	
		Cursor d = database.rawQuery("select new_code from "
				+ "tb_bjgl_atm_parts_config_wl "
				+ "where config_id = (select config_id from "
				+ "tb_bjgl_atm_parts_config_wl "
				+ "where new_code = ?)",new String[]{code}); 
		
		d.moveToFirst();
		for (int i = 0; i < d.getCount(); i++) {  //遍历该物料所在的父级的所有物料
			String newcode = d.getString(d.getColumnIndex("new_code"));
			
			for(int l = 0; l<list.size(); l++){// 如果物料编码存在于当前树中
				if(isdata == 0){//原机器没数据
					if(list.get(l).getNewCode().equals(newcode)){
						wlStr.add(mSaoMiaoList.get(l).getWlBarCode());
					}
				}else if(isdata == 1){//原机器有数据
					if(list.get(l).getGenreId().equals(newcode)){
						wlStr.add(mBaoCunList.get(l).getWlBarCode());
					}
				}
			}
			d.moveToNext();   
		}
		d.close();
		database.close();
		
		return wlStr;
	}
	
	
	
	
	/**
	 * 查询上一级的 所有物料   返回 存在当前所扫的物料
	 * @param pid
	 * @return
	 */
	private String setBaoCunList(String pid){
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		//查询 上一级  物料
		Cursor d = database.rawQuery("select new_code from "
				+ "tb_bjgl_atm_parts_config_wl "
				+ "where config_id=?;",new String[]{pid}); 
		
		d.moveToFirst();
		for (int i = 0; i < d.getCount(); i++) {  //遍历该物料所在的父级的所有物料
			String f_newcode = d.getString(d.getColumnIndex("new_code"));
			
			for(int l = 0; l<mBaoCunList.size(); l++){// 如果父级的物料编码存在于当前树中
				if(mBaoCunList.get(l).getGenreId().equals(f_newcode)){
					return f_newcode;
				}
			}
			d.moveToNext();   
		}
		d.close();
		database.close();
		
		return "1";
	}
	
	/**
	 * 查询上一级的 标配名
	 * @param pid
	 * @return
	 */
	private String getTopName(String configid){
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		//查询 上一级  物料
		Cursor e = database.rawQuery("select config_name from "
				+ "tb_bjgl_atm_parts_config "
				+ "where config_id=?;",new String[]{configid}); 
		
		e.moveToFirst();
		String topname = e.getString(e.getColumnIndex("config_name"));
		
		e.close();
		database.close();
		
		return topname;
	}
	
	/**
	 * 根据 物料编码 获取物料种类  0 不多个  1多个
	 * @return
	 */
	private int getWlConfigNun(String newcode){ 
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		//查询 上一级  物料
		Cursor e = database.rawQuery("select max(is_many) as is_many from "
				+ "tb_bjgl_atm_parts_config "
				+ "where config_id = (select config_id from "
				+ "tb_bjgl_atm_parts_config_wl "
				+ "where new_code = ?)",new String[]{newcode}); 
		
		e.moveToFirst();
		
		int configName = e.getInt(e.getColumnIndex("is_many"));
		
		e.close();
		database.close();
		
		return configName;
	}
	
	/**
	 * 判断保存list pid 是否为 "1"
	 * @param codenum
	 * @return
	 */
	private boolean getBaoCuncontains(String codenum){ 
		for(int i = 0; i<mBaoCunList.size(); i++){
			if(mBaoCunList.get(i).getGenrePid().equals(codenum)){
			mDot_TopName = getTopName(mBaoCunList.get(i).getPidNewCode());
			return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 判断物料条码是否在当前列表
	 * @param codenum
	 * @return
	 */
	private boolean getcontains(String ewm){ 
		for(int i = 0; i<mShowList.size(); i++){
			String [] stringArr = mShowList.get(i).getGenreName().split(",");
			if(ewm.equals(stringArr[2]))
			return true;
		}
		return false;
	}
	
	/**
	 * 判断保存列表是否空而选择提示
	 */
	public void isOpenDialog(){
		if(mBaoCunList.size() > 0 && KingTellerStaticConfig.QR_DOTMACHINE_LISTDATA){
			OpenDialog("当前数据已更改且未保存, 是否离线保存？", 3);
		}else{
			OpenDialog("您确定要退出扫描吗？", 2);
		}
	}
	
	/**
	 * 打开提示dialog
	 * @param context
	 * @param msg
	 * @param type
	 */
	public void OpenDialog (String msg, int type){
		if(type == 1){
			
			dialog = new NormalDialog(mContext);
        	KingTellerPromptDialogUtils.showOnePromptDialog(dialog, msg,
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    });

		}else if(type == 2){
			
			dialog = new NormalDialog(mContext);
        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, msg,
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
							
						}
                    }, new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
							finish();
						}
                    });
        	
		}else if(type == 3){
			
			dialog = new NormalDialog(mContext);
			KingTellerPromptDialogUtils.showSaveTwoPromptDialog(dialog, msg, "不保存", "保存",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
							finish();
						}
                    }, new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();

							int isSuccess = 0 ;
							if(mGetDataList.size()>0){
								isSuccess = 1 ;
							}
							
							if(getBaoCuncontains("1")){
									if("".equals(mDot_TopName) || mDot_TopName == null){
										T.showShort(mContext, "还有其部件物料还未扫描, 请继续扫描!");
		
									}else{
										T.showShort(mContext, "还有：" + mDot_TopName + " 所属的物料未被扫描！");
									}
							}else{
								//保存暂存数据
								setBaoCunDotMachineData(isSuccess);
								finish();
							}
						}
                    });
		}

	}
	
	/**
	 * 保存数据到数据库
	 * @param isSusser
	 */
	public void setBaoCunDotMachineData(int isSuccesser){
		//保存数据库
		KingTellerDbUtils.saveDotMachineToDataBase(
				mContext, 
				mDot_jqid, //机器id
				"dotmachine",
				gson.toJson(mBaoCunList), 
				mDot_jqid + "," + mDot_jqbm, //机器id,机器编号
				KingTellerTimeUtils.getNowTime(),
				isSuccesser);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
