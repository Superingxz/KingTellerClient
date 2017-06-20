package com.kingteller.client.activity.map;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.AtmUpLoadAdapter;
import com.kingteller.client.adapter.AtmUpLoadAdapter.OnAtmUpLoadPicLister;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BaseBean;
import com.kingteller.client.bean.db.AtmGroup;
import com.kingteller.client.bean.map.AtmGroupBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerImageUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.LoadingObjView;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.image.activity.ChangePicBigImagesActivity;
import com.kingteller.client.view.image.activity.PickOrTakeImageActivity;
import com.kingteller.client.view.image.allview.SelectPicGridView;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxFilesParams;

/**
 * 机器定位
 */

public class ATMUploadActivity extends Activity implements OnClickListener {
	
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
    private Button btn_1, btn_2, btn_3;
    private ListView mListView;
    private AtmUpLoadAdapter mAtmUpLoadAdapter;
    private LoadingObjView mLoadingObjView;
	private Context mContext;
    private SelectPicGridView mSelectPicGridView;
    private String hcId;
    private List<?> mALLDataList;
    private List<AtmGroupBean> Lists;
    private List<AtmGroupBean> selectLists;
    private List<String> picNameLists;
	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				AjaxFilesParams params = (AjaxFilesParams) msg.obj;
				submitATM(params);
				break;
			case 2:
				T.showShort(mContext, "机器必须上传图片, 请选择图片!");
            	break;

			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_atm_upload);
		KingTellerApplication.addActivity(this);
		
		mContext = ATMUploadActivity.this;
		initUI();
		initData();

	}


	private void initUI() {
		mLoadingObjView = new LoadingObjView(this);
        mLoadingObjView.setStatus(LoadingEnum.LISTSHOW);

        title_text = (TextView) findViewById(R.id.layout_main_text);

        title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
        title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);

        title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
        title_righttwo_btn.setBackgroundResource(R.drawable.btn_add);

        mListView = (ListView) findViewById(R.id.common_listview);

        mAtmUpLoadAdapter = new AtmUpLoadAdapter(mContext, new ArrayList<AtmGroupBean>());
        mListView.setAdapter(mAtmUpLoadAdapter);


        btn_1 = (Button) findViewById(R.id.layout_atm_upload_btn_1);
        btn_2 = (Button) findViewById(R.id.layout_atm_upload_btn_2);
        btn_3 = (Button) findViewById(R.id.layout_atm_upload_btn_3);

        title_left_btn.setOnClickListener(this);
        title_righttwo_btn.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
	}
	
	private void initData() {
		title_text.setText("图片上传");
		
		mAtmUpLoadAdapter.setOnAtmUpLoadPicLister(mAliasCallback);
		
		getAtmGroupData();
	}
	
	//视图控制器
    private OnAtmUpLoadPicLister mAliasCallback = new OnAtmUpLoadPicLister() {
        @Override
        public void OnItemClick(SelectPicGridView aview, String id, int type, int num) {
            mSelectPicGridView = aview;
            hcId = id;
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

                    for(int i = 0; i < Lists.size(); i++){
                        if(Lists.get(i).getId().equals(hcId)){
                            Lists.get(i).setPiclist(alllist);

                            //保存数据库
                            KingTellerDbUtils.saveAtmGroupToDataBase(
                                    mContext,
                                    hcId,
                                    "atmgroupuppic",
                                    Lists.get(i).getJqbh(),
                                    new Gson().toJson(Lists.get(i).getPiclist()),
                                    Lists.get(i).getStr(),
                                    KingTellerTimeUtils.getNowDayAndTime(1));
                        }
                    }

                    mAtmUpLoadAdapter.setLists(Lists);
                    mAtmUpLoadAdapter.notifyDataSetChanged();

                }
                break;
            case KingTellerStaticConfig.PIC_DELETE_NUM:
                if (resultCode == RESULT_OK) {
                    String picdata = (String) data.getSerializableExtra("pic_dataList_de");
                    List<String> remainlist = KingTellerJsonUtils.getStringPersons(picdata);

                    for(int i = 0; i < Lists.size(); i++){
                        if(Lists.get(i).getId().equals(hcId)){
                            Lists.get(i).setPiclist(remainlist);

                            //保存数据库
                            KingTellerDbUtils.saveAtmGroupToDataBase(
                                    mContext,
                                    hcId,
                                    "atmgroupuppic",
                                    Lists.get(i).getJqbh(),
                                    new Gson().toJson(Lists.get(i).getPiclist()),
                                    Lists.get(i).getStr(),
                                    KingTellerTimeUtils.getNowDayAndTime(1));
                        }
                    }

                    mAtmUpLoadAdapter.setLists(Lists);
                    mAtmUpLoadAdapter.notifyDataSetChanged();
                }

                break;
            default:
                break;
        }
    }
    
    public void getAtmGroupData() {
        mALLDataList = KingTellerDbUtils.getCacheDataAll(this, AtmGroup.class);
        Lists = new ArrayList<>();

        if(mALLDataList.size() > 0){
            for (Object data :  mALLDataList) {
                AtmGroup mAtmGroupData = (AtmGroup) data;
                Lists.add(new AtmGroupBean(
                        mAtmGroupData.getAtmGroupId(),
                        mAtmGroupData.getAtmGroupType(),
                        mAtmGroupData.getAtmGroupJqBh(),
                        KingTellerJsonUtils.getStringPersons(mAtmGroupData.getAtmGroupPicList()),
                        mAtmGroupData.getAtmGroupStrData()));

            }
        }
        mAtmUpLoadAdapter.setLists(Lists);
    }
    
    public void setATMGroupData() {
        //获取选中list
        selectLists = new ArrayList<>();
        for (int i = 0; i < mAtmUpLoadAdapter.getCount(); i++) {
            if (mAtmUpLoadAdapter.getCheckMap().get(i) != null && mAtmUpLoadAdapter.getCheckMap().get(i)) {
                selectLists.add((AtmGroupBean) mAtmUpLoadAdapter.getItem(i));
            }
        }

        KingTellerProgressUtils.showProgress(ATMUploadActivity.this, "正在上传...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                

                AjaxFilesParams params = new AjaxFilesParams();
                params.put("userid", User.getInfo(mContext).getUserId());

                for (int j = 0; j < selectLists.size(); j++) {
                    List<String> filelist = selectLists.get(j).getPiclist();
                    /**
                     * 2016.06.29 判断有没有图片，没有提示信息，并无法完成上传
                     */
                    if(filelist.size() <= 0) {
                    	KingTellerProgressUtils.closeProgress();
                    	Message msg = new Message();
                        msg.what = 2;
                        handler.sendMessage(msg);
                    	return;
                    }
                    String str = selectLists.get(j).getStr();
                    
                    picNameLists = new ArrayList<>();
                    for(int i = 0; i < filelist.size(); i++){
                        File file = KingTellerImageUtils.createBitmap(filelist.get(i), str);
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
                    s.append("\"atm\":\"" + selectLists.get(j).getJqbh() + "\",");
                    s.append("\"image\":\"" + l + "\",");
                    s.append("\"yiji\":\"" + 0 + "\"");
                    s.append("}");

                    params.put("name" + j, s.toString());

                }

                Message msg = new Message();
                msg.what = 1;
                msg.obj = params;
                handler.sendMessage(msg);
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

                            for (int i = 0; i < selectLists.size(); i++) {
                                KingTellerDbUtils.deleteAtmGroupToDataBase(mContext, selectLists.get(i).getId());
                            }
                            getAtmGroupData();

                        } else{
                            T.showShort(mContext, data.getMsg());
                        }
                    }
                });
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();

        getAtmGroupData();
    }
    
    @Override
    public void onClick(View v) {
        int count = mAtmUpLoadAdapter.getCount();

        if(v.getId() != R.id.layout_main_left_btn && v.getId() != R.id.layout_main_righttwo_btn
                && count == 0){
            T.showShort(mContext, "暂无数据!");
            return;
        }

        // 获取当前的数据数量
        HashMap<Integer, Boolean> map = mAtmUpLoadAdapter.getCheckMap();

        if(v.getId() != R.id.layout_main_left_btn && v.getId() != R.id.layout_main_righttwo_btn && v.getId() != R.id.layout_atm_upload_btn_2){
            //被选中数量
            int inSelect = 0;
            for (int i = 0; i < count; i++) {
                if (map.get(i) != null && map.get(i)) {
                    inSelect ++;
                }
            }
            if(inSelect == 0){
                T.showShort(mContext, "请选择数据!");
                return;
            }
        }

        switch (v.getId()) {
            case R.id.layout_main_left_btn:
                finish();
                break;
            case R.id.layout_main_righttwo_btn:
                startActivity(new Intent(this, ATMAddActivity.class));
                break;
            case R.id.layout_atm_upload_btn_1:
                final NormalDialog dialog = new NormalDialog(mContext);
                KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "您确定要删除所选择项吗？",
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                dialog.dismiss();
                            }
                        }, new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                dialog.dismiss();
                                deletCeacheData();
                            }
                        });
                break;
            case R.id.layout_atm_upload_btn_2:
                if ("全选".equals(btn_2.getText().toString())) {
                    // 所有项目全部选中
                    mAtmUpLoadAdapter.configCheckMap(true);
                    mAtmUpLoadAdapter.notifyDataSetChanged();

                    btn_2.setText("全不选");
                } else {

                    // 所有项目全部不选中
                    mAtmUpLoadAdapter.configCheckMap(false);
                    mAtmUpLoadAdapter.notifyDataSetChanged();

                    btn_2.setText("全选");
                }
                break;
            case R.id.layout_atm_upload_btn_3:
                setATMGroupData();
                break;

            default:
                break;
        }

    }
    
    public void deletCeacheData(){
        // 获取当前的数据数量
        int count = mAtmUpLoadAdapter.getCount();
        HashMap<Integer, Boolean> map = mAtmUpLoadAdapter.getCheckMap();
        // 进行遍历
        for (int i = 0; i < count; i++) {
            // 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
            int position = i - (count - mAtmUpLoadAdapter.getCount());
            if (map.get(i) != null && map.get(i)) {
                AtmGroupBean bean = (AtmGroupBean) mAtmUpLoadAdapter.getItem(position);
                //做本地数据删除
                KingTellerDbUtils.deleteAtmGroupToDataBase(mContext, bean.getId());

                mAtmUpLoadAdapter.getCheckMap().remove(i);
                mAtmUpLoadAdapter.remove(position);
            }
        }

        mAtmUpLoadAdapter.notifyDataSetChanged();
    }
    
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
