package com.kingteller.client.activity.logisticmonitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.adapter.TydmkAdapter;
import com.kingteller.client.adapter.TydmkytjAdapter;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.logisticmonitor.AloneTaskAndConsignBean;
import com.kingteller.client.bean.logisticmonitor.FareDetailParam;
import com.kingteller.client.bean.logisticmonitor.LogisticConsignBasicBean;
import com.kingteller.client.bean.logisticmonitor.LogisticConsignMobileBean;
import com.kingteller.client.bean.logisticmonitor.LogisticTaskBean;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.GroupListView;
import com.kingteller.client.view.GroupListView.AddViewCallBack;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.KingTellerEditText.OnChangeListener;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.client.view.ListViewForScrollView;
import com.kingteller.client.view.LogisticTaskFareGroupView;
import com.kingteller.framework.http.AjaxParams;

public class LogisticTaskActivity extends BaseActivity implements OnClickListener{

	private TextView tylx,thsj,ddsj,thdd,sfzy;
	private TextView zydw,zylxr1,zylxr2,zylxr3,zylxdh1,zylxdh2,zylxdh3,zydwdz;
	private TextView sycl,scry,zysx;
	private TextView bz;
	private ListViewForScrollView listView;
	private Button btn_dial,btn_deal;
	private String rwdId,rwdzt;
	private LogisticConsignBasicBean basicBean = new LogisticConsignBasicBean();
	private LogisticTaskBean taskBean = new LogisticTaskBean();
	private LogisticConsignMobileBean consignBean = new LogisticConsignMobileBean();
	private List<LogisticConsignMobileBean> tydlist = new ArrayList<LogisticConsignMobileBean>(); 
	private TydmkAdapter adapter;
	private TydmkytjAdapter ytjAdapter;
	public static final String UPLOAD_NUM ="uploadNum";
	public static final String TYDID = "tydId";
	private GroupListView costType_group_list ;
	private Gson gson = new Gson();
	private KingTellerEditText involvesFee;
	private String pdzLike;
	private Context mContext;
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			onFresh(rwdzt);
			KingTellerProgressUtils.closeProgress();
		};
	};
	
	
	@Override
	public void onCreate(Bundle paramBundle) {
		// TODO Auto-generated method stub
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_task_nr1);
		mContext = LogisticTaskActivity.this;
		initUI();
		initData();
	}

	private void initUI() {
		title_title.setText("任务内容");
		title_left.setOnClickListener(this);
		
		KingTellerProgressUtils.showProgress(this, "加载中");

		tylx = (TextView) findViewById(R.id.tylx);
		thsj = (TextView) findViewById(R.id.thsj);
		ddsj = (TextView) findViewById(R.id.ddsj);
		sfzy = (TextView) findViewById(R.id.sfzy);
		zydw = (TextView) findViewById(R.id.zydw);
		zylxr1 = (TextView) findViewById(R.id.zylxr1);
		zylxr2 = (TextView) findViewById(R.id.zylxr2);
		zylxr3 = (TextView) findViewById(R.id.zylxr3);
		zylxdh1 = (TextView) findViewById(R.id.zylxdh1);
		zylxdh2 = (TextView) findViewById(R.id.zylxdh2);
		zylxdh3 = (TextView) findViewById(R.id.zylxdh3);
		zydwdz = (TextView) findViewById(R.id.zydwdz);
		sycl = (TextView) findViewById(R.id.sycl);
		scry = (TextView) findViewById(R.id.scry);
		zysx = (TextView) findViewById(R.id.zysx);
		//dqdz = (TextView) findViewById(R.id.dqdz);
		bz = (TextView) findViewById(R.id.bz);
		btn_deal = (Button) findViewById(R.id.btn_deal);
		btn_dial = (Button) findViewById(R.id.btn_dial);
		listView = (ListViewForScrollView) findViewById(R.id.listView);
		
		btn_deal.setOnClickListener(this);
		btn_dial.setOnClickListener(this);
		
		rwdId = getIntent().getStringExtra("rwdId");
		rwdzt = getIntent().getStringExtra("rwdzt");
		onFresh(rwdzt);
		
		zylxdh1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(LogisticTaskActivity.this, ((Button) arg0).getText().toString());
			}
		});

		zylxdh2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(LogisticTaskActivity.this, ((Button) arg0).getText().toString());
			}
		});
		zylxdh3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(LogisticTaskActivity.this, ((Button) arg0).getText().toString());
			}
		});
		
		KingTellerConfigUtils.hideInputMethod(this);
		
		

	}
	
	public void initData(){
		getLogisticTask();
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.button_left:
			finish();
			break;
		case R.id.btn_dial:
			dial(pdzLike);
			break;
		case R.id.btn_deal:
			if("yjs".equals(rwdzt)){
				View layout = getLayoutInflater().inflate(R.layout.item_layout_qc, null);
				final Dialog dialog = new AlertDialog.Builder(this).create();
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
				dialog.getWindow().setContentView(layout);
				dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				
				final KingTellerEditText qclcs = (KingTellerEditText)layout.findViewById(R.id.qclcs);
				final KingTellerEditText sjpsrq = (KingTellerEditText)layout.findViewById(R.id.sjpsrq);
				Button submit = (Button) layout.findViewById(R.id.submit);
				Button cancel = (Button) layout.findViewById(R.id.cancel);
				
				sjpsrq.setFieldTextAndValue(KingTellerTimeUtils.getConversionFormatStringByDate(new Date(), 2));
				
				submit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(KingTellerJudgeUtils.isEmpty(qclcs.getFieldValue())){
							T.showShort(mContext, "返回里程数必须填写!");
							return;
						}
						updateStatus(rwdId, rwdzt,qclcs.getFieldValue().trim(),sjpsrq.getFieldText().trim(),"","");
						dialog.dismiss();
					}
				});
				
				cancel.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
			}else if("qc".equals(rwdzt)){
				View layout = getLayoutInflater().inflate(R.layout.item_layout_fhqc, null);
				final Dialog dialog = new AlertDialog.Builder(this).create();
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
				dialog.getWindow().setContentView(layout);
				dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				
				final KingTellerEditText fhqclcs = (KingTellerEditText)layout.findViewById(R.id.fhqclcs);
				involvesFee = (KingTellerEditText)layout.findViewById(R.id.involvesFee);
				final LinearLayout layout_fee_info = (LinearLayout) layout.findViewById(R.id.layout_fee_info);
				costType_group_list = (GroupListView)layout.findViewById(R.id.costType_group_list);
				Button submit = (Button) layout.findViewById(R.id.submit);
				
				involvesFee.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios01));
				
				involvesFee.setOnChangeListener(new OnChangeListener() {
					
					@Override
					public void onChanged(CommonSelectData data) {
						// TODO Auto-generated method stub
						if(data.getValue().equals("1")){
							layout_fee_info.setVisibility(View.VISIBLE);
						}else{
							layout_fee_info.setVisibility(View.GONE);
						}
					}
				});
			
				costType_group_list.setAddViewCallBack(new AddViewCallBack() {

					@Override
					public void addView(GroupListView view) {
						// TODO Auto-generated method stub
						view.addItem(new LogisticTaskFareGroupView(
								LogisticTaskActivity.this, true));
					}
				});

				costType_group_list.addItem(new LogisticTaskFareGroupView(LogisticTaskActivity.this,false));
				
				submit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(KingTellerJudgeUtils.isEmpty(fhqclcs.getFieldValue())){
							T.showShort(mContext, "返回里程数必须填写!");
							return;
						}
						if(!KingTellerJudgeUtils.isEmpty(involvesFee.getFieldValue())){
							if(involvesFee.getFieldValue().equals("0")){
								new KTAlertDialog.Builder(LogisticTaskActivity.this)
								.setTitle("提示")
								.setMessage("是否需要填写费用？")
								.setPositiveButton("确定",
										new KTAlertDialog.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialogInterface,int pos) {
												dialogInterface.dismiss();
											}
										})
								.setNegativeButton("取消",
										new KTAlertDialog.OnClickListener() {
											public void onClick(
													DialogInterface dialogInterface,
													int paramAnonymousInt) {
												updateStatus(rwdId, rwdzt,"","",fhqclcs.getFieldValue().trim(),"");
												dialog.dismiss();
												dialogInterface.dismiss();
											}
										}).create().show();
							}else if(involvesFee.getFieldValue().equals("1")){
								List<FareDetailParam> fdpList = costType_group_list.getListData();
								for(int i = 0;i< fdpList.size();i ++){
									if(KingTellerJudgeUtils.isEmpty(fdpList.get(i).getFylx())){
										T.showShort(mContext, "费用类型不能为空!");
										return;
									}
									if(KingTellerJudgeUtils.isEmpty(fdpList.get(i).getJe())){
										T.showShort(mContext, "费用类型不能为空!");
										return;
									}
								}
								
								updateStatus(rwdId, rwdzt,"","",fhqclcs.getFieldValue().trim(),gson.toJson(fdpList));
								dialog.dismiss();
							}
						}else{
							T.showShort(mContext, "请选择是否填写费用!");
						}
						
					}
				});
				
			}else{
				new KTAlertDialog.Builder(this)
				.setTitle("提示")
				.setMessage(onChangeStatus(rwdzt)+"？")
				.setPositiveButton("确定",
						new KTAlertDialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface,int pos) {
								if(rwdzt.equals("qc")){
									dialogInterface.dismiss();
									finish();
								}else{	
									updateStatus(rwdId, rwdzt,"","","","");
									dialogInterface.dismiss();
								}
							}
						})
				.setNegativeButton("取消",
						new KTAlertDialog.OnClickListener() {
							public void onClick(
									DialogInterface dialogInterface,
									int paramAnonymousInt) {
								dialogInterface.dismiss();
							}
						}).create().show();
			}
			
			break;
		default:
			break;
		}
	}
	
	//得到界面数据
	private void getLogisticTask() {
		// TODO Auto-generated method stub
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("rwdId", rwdId);
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WljkrwdUrl), params,
				new AjaxHttpCallBack<AloneTaskAndConsignBean>(this,
						new TypeToken<AloneTaskAndConsignBean>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					@SuppressLint("ShowToast")
					public void onError(int errorNo, String strMsg) {
						// TODO Auto-generated method stub
						KingTellerProgressUtils.closeProgress();
					}
			
					@Override
					public void onDo(AloneTaskAndConsignBean data) {
						setInfoData(data);
					};
				});

	}
	
	//操作界面按钮
	private void updateStatus(String rId,final String rzt,String qclcs,String sjpsrq,String fhqclcs,String feeList){
		KingTellerProgressUtils.showProgress(LogisticTaskActivity.this, onChangeStatus(rzt)+"中");
		
		KTHttpClient fh = new KTHttpClient(true);
		AddressBean address;
		AjaxParams params = new AjaxParams();
		params.put("rwdId", rId);
		params.put("status", rzt);
		address = KingTellerApplication.getApplication().getCurAddress();
		params.put("address", address.getAddress());
		params.put("lat", String.valueOf(address.getLat()));
		params.put("lng", String.valueOf(address.getLng()));
		params.put("qclcs", qclcs);
		params.put("sjpsrq", sjpsrq);
		params.put("fhqclcs", fhqclcs);
		params.put("feeList", feeList);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.RwdsjUrl), params,
				new AjaxHttpCallBack<ReturnBackStatus>(this,
						new TypeToken<ReturnBackStatus>() {
						}.getType(), true) {

					@Override
					public void onDo(ReturnBackStatus data) {
						
						if(data.getResult().equals("success")){
							if(rzt.equals("ypd")){
								rwdzt = "yjs";
							}else if(rzt.equals("yjs")){
								rwdzt = "qc";
							}else if(rzt.equals("qc")){
								rwdzt = "qrfh";
							}
							new Thread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									handler.sendEmptyMessage(0);
								}
							}).start();
						}
						T.showShort(mContext, data.getMessage());
					};

				});

	}
	
	private void onFresh(String rwdzt) {
		if ("ypd".equals(rwdzt)) {
			btn_deal.setText("确认接收");
		} else if ("yjs".equals(rwdzt)) {
			btn_deal.setText("启程");
		} else if("qc".equals(rwdzt)){
			btn_deal.setVisibility(View.GONE);
			initData();
		} else if("qrfh".equals(rwdzt)){
			finish();
		}
	}
	
	private boolean getTydzt(List<LogisticConsignMobileBean> list){
		if(list != null){
			
			String tydzt = "80";
			for(int i =0; i < list.size(); i ++){
				if( !list.get(i).getConsign().getTydzt().equals(tydzt)){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	private String onChangeStatus(String rwdzt){
		if ("ypd".equals(rwdzt)) {
			return "是否确认接受本任务";
		} else if ("yjs".equals(rwdzt)) {
			return "是否已完成装货并开始起程";
		} else if("qc".equals(rwdzt)){
			return "确认返回";
		} else if("qrfh".equals(rwdzt)){
			return "确认返回";
		}
		return "";
	}
	
	public void setInfoData(AloneTaskAndConsignBean data){
		basicBean = data.getBasic();
		taskBean = data.getTask();
		tydlist = data.getTydlist();
		consignBean = tydlist.get(0);
		tylx.setText(basicBean.getTylxLike());
		thsj.setText(basicBean.getThrqStr());
		ddsj.setText(basicBean.getPsddrqStr());
		
		pdzLike = data.getTydlist().get(0).getConsign().getPdzLike().trim();
		
		if(basicBean.getTylx().equals("Y") || basicBean.getTylx().equals("T")){
			((LinearLayout)sfzy.getParent()).setVisibility(View.GONE);
			//((LinearLayout)thdd.getParent()).setVisibility(View.GONE);
			
			ytjAdapter = new TydmkytjAdapter(this, tydlist, rwdzt);
			listView.setAdapter(ytjAdapter);
		}else {
			sfzy.setVisibility(View.VISIBLE);
			sfzy.setText(basicBean.getSfeczy());
			if("否".equals(basicBean.getSfeczy())){
				findViewById(R.id.zymk).setVisibility(View.GONE);
			}else if("是".equals(basicBean.getSfeczy())){
				findViewById(R.id.zymk).setVisibility(View.VISIBLE);
				zydw.setText(consignBean.getConsign().getZydwLike());
				String[] lxrStr = consignBean.getConsign().getZylxr().split(",");
				String[] lxdhStr = consignBean.getConsign().getZylxdh().split(",");
				zylxr1.setText(lxrStr[0]);
				if(lxrStr.length == 2){
					zylxr2.setVisibility(View.VISIBLE);
					zylxr2.setText(lxrStr[1]);
				}else if(lxrStr.length == 3){
					zylxr2.setVisibility(View.VISIBLE);
					zylxr3.setVisibility(View.VISIBLE);
					zylxr2.setText(lxrStr[1]);
					zylxr3.setText(lxrStr[2]);
				}
				zylxdh1.setText(lxdhStr[0]);
				if(lxrStr.length == 2){
					zylxdh2.setVisibility(View.VISIBLE);
					zylxdh2.setText(lxdhStr[1]);
				}else if(lxrStr.length == 3){
					zylxr2.setVisibility(View.VISIBLE);
					((LinearLayout)zylxr3.getParent()).setVisibility(View.VISIBLE);
					zylxr2.setText(lxdhStr[1]);
					zylxr3.setText(lxdhStr[2]);
				}
				zydwdz.setText(consignBean.getConsign().getZydwdz());
			}
			
			adapter = new TydmkAdapter(this, tydlist,rwdzt,basicBean.getSfeczy());
			listView.setAdapter(adapter);
		}
		
		sycl.setText(taskBean.getClLike());
		scry.setText(taskBean.getGcy1Like()+"     "+taskBean.getGcy2Like());
		zysx.setText(taskBean.getZysx());
		bz.setText(taskBean.getBz());
		
		if(getTydzt(tydlist)){
			btn_deal.setVisibility(View.VISIBLE);
			btn_deal.setText("返回确认");
		}
		
	}
	
	private void dial(String telephone){
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+telephone));    
        startActivity(intent);  
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.UPLOAD_PIC: 
			if (resultCode == RESULT_OK) {
				TextView tydId;
				int count = listView.getChildCount();
				for(int i =0 ;i < count ;i ++){
					tydId = (TextView)listView.getChildAt(i).findViewById(R.id.tydId);
					if(!KingTellerJudgeUtils.isEmpty(data.getStringExtra(TYDID)) 
							&& !KingTellerJudgeUtils.isEmpty(tydId.getText().toString())){
						if(tydId.getText().toString().equals(data.getStringExtra(TYDID))){
							Button takePhoto = (Button)listView.getChildAt(i).findViewById(R.id.takePhoto);
							takePhoto.setText("已上传"+data.getStringExtra(UPLOAD_NUM)+"张照片");
							takePhoto.setClickable(false);
						}
					}
				}
			}
			break;
			
		case KingTellerStaticConfig.SELECT_FYLX:
			if (resultCode == RESULT_OK) {
				costType_group_list.setItemData((CommonSelectData) data
						.getSerializableExtra(CommonSelectDataActivity.DATA));
			}
			break;

		default:
			break;
		}
	}
}
