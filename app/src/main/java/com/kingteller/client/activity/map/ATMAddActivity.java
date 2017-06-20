package com.kingteller.client.activity.map;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.google.gson.Gson;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BaseBean;
import com.kingteller.client.bean.db.AtmGroup;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.bean.map.AtmGroupBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerFileUtils;
import com.kingteller.client.utils.KingTellerImageUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.groupview.itemview.ATMGroupView;
import com.kingteller.client.view.groupview.itemview.ATMGroupView.OnATMGroupLister;
import com.kingteller.client.view.groupview.listview.CommonGroupListView;
import com.kingteller.client.view.groupview.listview.CommonGroupListView.AddViewCallBack;
import com.kingteller.client.view.image.activity.ChangePicBigImagesActivity;
import com.kingteller.client.view.image.activity.PickOrTakeImageActivity;
import com.kingteller.client.view.image.allview.SelectPicGridView;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxFilesParams;

public class ATMAddActivity extends Activity implements OnClickListener{

	private Context mContext;
	private TextView title_text, atm_location;
    private LinearLayout layout_location;
	private Button title_left_btn, title_righttwo_btn;

    private CheckBox atm_location_cb;

    private CommonGroupListView atm_group_list;

    private SelectPicGridView mSelectPicGridView;

    private List<AtmGroupBean> mAtmGroupList;

    private LocationManagerProxy aMapLocAtmManager;
    private AddressBean address;

    private List<String> picNameLists;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				AjaxFilesParams params = (AjaxFilesParams) msg.obj;
				submitATM(params);
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_atm_add);
		KingTellerApplication.addActivity(this);
		
		mContext = ATMAddActivity.this;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);

        title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
        title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);

        title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
        title_righttwo_btn.setBackgroundResource(R.drawable.btn_check);

        atm_group_list = (CommonGroupListView) findViewById(R.id.atm_group_list);
        atm_group_list.setText("点击按钮添加机器");
        atm_group_list.setAddViewCallBack(new AddViewCallBack() {
            @Override
            public void addView(CommonGroupListView view) {
                ATMGroupView atm_view_0 = new ATMGroupView(mContext, true);
                atm_view_0.setOnATMGroupLister(mAliasCallback);
                view.addItem(atm_view_0);
            }
        });

        layout_location = (LinearLayout) findViewById(R.id.layout_location);
        atm_location = (TextView) findViewById(R.id.atm_location);

        atm_location_cb = (CheckBox) findViewById(R.id.atm_location_cb);

        layout_location.setOnClickListener(this);
        title_left_btn.setOnClickListener(this);
        title_righttwo_btn.setOnClickListener(this);

        aMapLocAtmManager = LocationManagerProxy.getInstance(this);
        aMapLocAtmManager.setGpsEnable(true);
	}

	private void initData() {
		title_text.setText("机器定位");

        //添加ATM视图
        ATMGroupView atm_view_1 = new ATMGroupView(mContext, false);
        atm_view_1.setOnATMGroupLister(mAliasCallback);
        atm_group_list.addItem(atm_view_1);

        //获取定位信息
        address = new AddressBean();
        getATMLocation();
	}
	
	/**
     * 获取当前定位
     */
    private void getATMLocation() {
        atm_location.setText("正在定位中...");

        KingTellerProgressUtils.showProgress(this, "正在定位中...");

        aMapLocAtmManager.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 0, staffAtmListener);
    }
	
    //视图控制器
    private OnATMGroupLister mAliasCallback = new OnATMGroupLister() {
        @Override
        public void OnItemClick(ATMGroupView aview, int type, int num) {
            mSelectPicGridView = aview.getLayoutAddPic();
            int list_size = mSelectPicGridView.getImagesList().size();

            if(type == 0){//添加图片
                if(list_size < 9){
                    Intent intent = new Intent();
                    intent.setClass(mContext, PickOrTakeImageActivity.class);
                    intent.putExtra(PickOrTakeImageActivity.EXTRA_NUMS, 9 - list_size);
                    startActivityForResult(intent, KingTellerStaticConfig.PIC_SELECT_NUM);
                }else{
                    T.showShort(mContext, "最多只能选择9张图片");
                }
            } else {
                Intent intent = new Intent();
                intent.setClass(mContext, ChangePicBigImagesActivity.class);
                intent.putExtra(ChangePicBigImagesActivity.EXTRA_IS_DELETE, type);
                intent.putExtra(ChangePicBigImagesActivity.EXTRA_NOW_PIC, num);
                intent.putExtra(ChangePicBigImagesActivity.EXTRA_NOW_DATA, new Gson().toJson(mSelectPicGridView.getImagesList()));
                startActivityForResult(intent, KingTellerStaticConfig.PIC_DELETE_NUM);
            }
        }

        @Override
        public void OnDelClick(ATMGroupView aview) {
            atm_group_list.delItem(aview);
        }

    };
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case KingTellerStaticConfig.PIC_SELECT_NUM:
                if (resultCode == RESULT_OK) {
                    String picdata = (String) data.getSerializableExtra("pic_dataList");
                    List<String> addlist = KingTellerJsonUtils.getStringPersons(picdata);
                    List<String> alllist = mSelectPicGridView.getImagesList();
                    alllist.addAll(addlist);
                    mSelectPicGridView.setImagesView(alllist);
                }
                break;
            case KingTellerStaticConfig.PIC_DELETE_NUM:
                if (resultCode == RESULT_OK) {
                    String picdata = (String) data.getSerializableExtra("pic_dataList_de");
                    List<String> remainlist = KingTellerJsonUtils.getStringPersons(picdata);
                    mSelectPicGridView.setImagesView(remainlist);
                }
                break;
            default:
                break;
        }
    }

    private AMapLocationListener staffAtmListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapAtmLocation) {

            KingTellerProgressUtils.closeProgress();

            if (aMapAtmLocation != null && aMapAtmLocation.getAMapException().getErrorCode() == 0) {

                String desc = "";
                Bundle locBundle = aMapAtmLocation.getExtras();
                if (locBundle != null) {
                    desc = locBundle.getString("desc");
                }

                address.setLat(aMapAtmLocation.getLatitude());
                address.setLng(aMapAtmLocation.getLongitude());
                address.setAddress(desc);
                address.setCity(aMapAtmLocation.getCity());

                atm_location.setText(address.getAddress());
                T.showShort(mContext, "获取定位信息成功!");

            } else {
                atm_location.setText("定位失败, 请重新定位!");

                String erro = aMapAtmLocation.getAMapException().getErrorMessage();
                T.showShort(mContext, "获取定位信息失败!" + erro);
            }

        }

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String arg0) {

        }

        @Override
        public void onProviderDisabled(String arg0) {

        }

        @Override
        public void onLocationChanged(Location arg0) {

        }
    };

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
            case R.id.layout_main_left_btn:
                finish();
                break;
            case R.id.layout_main_righttwo_btn:
                getATMGroupData();
                break;
            default:
                break;
		}
	}
	

	 public void getATMGroupData() {
	        mAtmGroupList = new ArrayList<>();
	        int length = atm_group_list.getItemNum();
	        String picdesc = "";
	        for (int i = 0; i < length; i++) {
	            ATMGroupView view = (ATMGroupView) atm_group_list.getItemView(i);
	            AtmGroupBean data = view.getData();
	            //-1未输入,
	            //-2需要重新验证 ,
	            //0没有图片 且 无经纬度信息,
	            //1没有图片 且 有经纬度信息,
	            //2有待审核图片 且 无经纬度信息,
	            //3有待审核图片 且 有经纬度信息,
	            //4有合格图片 且 无经纬度信息,
	            //5有合格图片 且 有经纬度信息,

	            switch (data.getStatus()) {
	                case -1:
	                    T.showShort(mContext, "机器编码未输入!");
	                    return;
	                case -2:
	                    T.showShort(mContext, data.getJqbh() + "机器未检索, 请先检索!");
	                    return;
	                case 2:
	                    break;
	                case 3:
	                    break;
	                case 4:
	                case 5:
	                    picdesc += data.getJqbh() + ",";
	                    break;
	                default:
	                    break;

	            }

	            if (KingTellerJudgeUtils.isEmpty(picdesc)){
	                picdesc = "是否确定上传?";
	            }else{
	                picdesc += "已有图片 \n 是否确定上传?";
	            }

	            if (data.getPiclist().size() == 0 ){
	                if(data.getStatus() != 4 && data.getStatus() != 5) {
	                    T.showShort(mContext, "机器必须上传图片, 请选择图片!");
	                    return;
	                }
	            }

	            mAtmGroupList.add(data);
	        }

	        String location = atm_location.getText().toString();
	        if("".equals(location) || location.contains("定位失败") || location.contains("正在定位")){
	            T.showShort(mContext, "请重新定位!");
	            return;
	        }


	        final NormalDialog dialog = new NormalDialog(mContext);
	        KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "当前地址为:" + location + "\n " + picdesc,
	                new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                        dialog.dismiss();
	                    }
	                }, new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                        dialog.dismiss();
	                        setATMGroupData();

	                    }
	                });
	 }

	 public void setATMGroupData() {

	        KingTellerProgressUtils.showProgress(mContext, "正在上传...");

	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                //picNameLists = new ArrayList<>();

	                AjaxFilesParams params = new AjaxFilesParams();
	                params.put("longitude", String.valueOf(address.getLng()));
	                params.put("latitude", String.valueOf(address.getLat()));
	                params.put("localInfo", String.valueOf(address.getAddress()));
	                params.put("upimage", atm_location_cb.isChecked() ? "1" : "0");
	                params.put("userid", User.getInfo(mContext).getUserId());

	                if (atm_location_cb.isChecked()) {//上传图片
	                    for(int i = 0; i < mAtmGroupList.size(); i++){
	                        String jqbh_1 = mAtmGroupList.get(i).getJqbh();
	                        List<String> piclist_1 = mAtmGroupList.get(i).getPiclist();
	                        StringBuffer strs_1 = new StringBuffer("机器编号: " + jqbh_1
	                                + "     " + "上传人: " + User.getInfo(mContext).getName());
	                        strs_1.append("\n经度:" + address.getLng() + "     " + "纬度:"+ address.getLat());
	                        strs_1.append("\n地址:" + address.getAddress());
	                        
	                        picNameLists = new ArrayList<>();
	                        for (int j = 0; j < piclist_1.size(); j++) {
	                            File file = KingTellerImageUtils.createBitmap(piclist_1.get(j), strs_1.toString());
	                            if(file != null){
	                                params.put("files", file);
	                                picNameLists.add(file.getName());
	                            }
	                        }

	                        
	                        StringBuffer l = new StringBuffer();
	                        for(int n = 0; n < picNameLists.size(); n++){
	                            if (n == picNameLists.size() - 1) {
	                                l.append(picNameLists.get(n));
	                            } else {
	                                l.append(picNameLists.get(n) + ",");
	                            }
	                        }

	                        StringBuffer s = new StringBuffer();
	                        s.append("{");
	                        s.append("\"atm\":\"" + jqbh_1 + "\",");
	                        s.append("\"image\":\"" + l + "\",");
	                        s.append("\"yiji\":\"" + 0 + "\"");
	                        s.append("}");

	                        params.put("viewjson" + i, s.toString());
	                        Log.e("ATMGroupData ：", s.toString());
	                    }
	                    Message msg = new Message();
		                msg.what = 1;
		                msg.obj = params;
		                handler.sendMessage(msg);
	                }else{

	                    //获取缓存所有数据
	                    List<?> mALLDataList = KingTellerDbUtils.getCacheDataAll(mContext, AtmGroup.class);
	                    List<Integer> mALLIdLists = new ArrayList<>();
	                    if(mALLDataList.size() > 0){
	                        for (Object data :  mALLDataList) {
	                            AtmGroup mAtmGroup = (AtmGroup) data;
	                            mALLIdLists.add(Integer.parseInt(mAtmGroup.getAtmGroupId()));
	                        }
	                    }

	                    //获取缓存 id
	                    int mAtmGroupId;
	                    if(mALLIdLists.size() == 0){
	                        mAtmGroupId = 0;
	                    }else{
	                        Collections.sort(mALLIdLists);
	                        mAtmGroupId = mALLIdLists.get(mALLIdLists.size() - 1);//当前数据 最大 id
	                    }

	                    for(int j = 0; j < mAtmGroupList.size(); j++){
	                        String jqbh_2 = mAtmGroupList.get(j).getJqbh();
	                        List<String> piclist_2 = mAtmGroupList.get(j).getPiclist();
	                        StringBuffer strs_2 = new StringBuffer("机器编号: " + jqbh_2
	                                + "     " + "上传人: " + User.getInfo(mContext).getName());
	                        strs_2.append("\n经度:" + address.getLng() + "     " + "纬度:"+ address.getLat());
	                        strs_2.append("\n地址:" + address.getAddress());

	                        mAtmGroupId += 1;
	                        
	                        //判断缓存中 含有当前机器
	                        for(int n = 0; n < mALLDataList.size(); n++){
	                            AtmGroup data = (AtmGroup) mALLDataList.get(n);
	                            if(data.getAtmGroupJqBh().equals(jqbh_2)){
	                                mAtmGroupId = Integer.parseInt(data.getAtmGroupId());
	                            }
	                        }

	                        if(mAtmGroupList.get(j).getStatus() == 4 || mAtmGroupList.get(j).getStatus() == 5){
	                            if(piclist_2.size() > 0){
	                                //保存数据库
	                                KingTellerDbUtils.saveAtmGroupToDataBase(
	                                        mContext,
	                                        String.valueOf(mAtmGroupId),
	                                        "atmgroupuppic",
	                                        jqbh_2,
	                                        new Gson().toJson(piclist_2),
	                                        strs_2.toString(),
	                                        KingTellerTimeUtils.getNowDayAndTime(1));
	                            }else{
	                                //不做保存
	                            }
	                        }else{
	                            //保存数据库
	                            KingTellerDbUtils.saveAtmGroupToDataBase(
	                                    mContext,
	                                    String.valueOf(mAtmGroupId),
	                                    "atmgroupuppic",
	                                    jqbh_2,
	                                    new Gson().toJson(piclist_2),
	                                    strs_2.toString(),
	                                    KingTellerTimeUtils.getNowDayAndTime(1));
	                        }

	                        /*
	                        StringBuffer l = new StringBuffer();
	                        for(int n = 0; n < picNameLists.size(); n++){
	                            if (n == picNameLists.size() - 1) {
	                                l.append(picNameLists.get(n));
	                            } else {
	                                l.append(picNameLists.get(n) + ",");
	                            }
	                        }

	                        StringBuffer s = new StringBuffer();
	                        s.append("{");
	                        s.append("\"atm\":\"" + jqbh_2 + "\",");
	                        s.append("\"image\":\"" + l + "\",");
	                        s.append("\"yiji\":\"" + 0 + "\"");
	                        s.append("}");

	                        params.put("viewjson" + j, s.toString());
	                        */
	                    }
	                }
	                KingTellerProgressUtils.closeProgress();
	                finish();
/*
	                Message msg = new Message();
	                msg.what = 1;
	                msg.obj = params;
	                handler.sendMessage(msg);
	                */
	            }
	        }).start();
	    }

	
	 	/**
	     * 上传
	     */
	    private void submitATM(AjaxFilesParams params) {
	        KTHttpClient fh = new KTHttpClient(true);
	        fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.UploadPicUrl), params,
	                new AjaxHttpCallBack<BaseBean>(this, true) {

	                    @Override
	                    public void onError(int errorNo, String strMsg) {
	                        super.onError(errorNo, strMsg);
	                        KingTellerProgressUtils.closeProgress();
	                        T.showShort(mContext, "数据访问超时!");
	                    }

	                    @Override
	                    public void onSuccess(String response) {
	                        Gson gson = new Gson();
	                        BaseBean data = new BaseBean();
	                        data.setMsg(response);
	                        data.setStatus("1");
	                        response = gson.toJson(data);
	                        super.onSuccess(response);
	                    }

	                    @Override
	                    public void onDo(BaseBean data) {
	                        KingTellerProgressUtils.closeProgress();

	                        if (data.getMsg().trim().equals("上传成功.")) {

	                            T.showShort(mContext, "上传成功!");
	                            
	                            finish();
	                        } else{
	                            T.showShort(mContext, data.getMsg());
	                        }
	                    }
	                });
	    }

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
