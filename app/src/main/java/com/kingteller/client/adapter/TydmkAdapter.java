package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.logisticmonitor.LogisticTaskActivity;
import com.kingteller.client.activity.logisticmonitor.UploadPhotosActivity;
import com.kingteller.client.bean.logisticmonitor.HwDDBean;
import com.kingteller.client.bean.logisticmonitor.LogisticConsignMobileBean;
import com.kingteller.client.bean.logisticmonitor.TydDataBean;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.ListViewForScrollView;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class TydmkAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<LogisticConsignMobileBean> tydlist = new ArrayList<LogisticConsignMobileBean>(); 
	private Context mContext;
	private LogisticConsignMobileBean tyd;
	private HwxxAdapter adapter;
	private HwxxDdAdapter ddAdapter;
	private HwxxJqAdapter jqAdapter;
	private String rwdzt;
	private String sferzy;
	private ListView listView ;
	private Dialog dialog;
	private Gson gson = new Gson();
	private HwDDBean hddb = null;

	public TydmkAdapter(Context context,List<LogisticConsignMobileBean> tydlist,String rwdzt,String sferzy){
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.tydlist = tydlist;
		this.rwdzt = rwdzt;
		this.sferzy = sferzy;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (tydlist == null) {
			return 0;
		}
		return tydlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return tydlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int postion, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		tyd = tydlist.get(postion);
		
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_add_tydmk, null);
			viewHoler.tydh = (TextView) v.findViewById(R.id.tydh);
			viewHoler.tydId = (TextView) v.findViewById(R.id.tydId);
			viewHoler.psdz = (TextView) v.findViewById(R.id.psdz);
			viewHoler.lxr1 = (TextView) v.findViewById(R.id.lxr1);
			viewHoler.lxr2 = (TextView) v.findViewById(R.id.lxr2);
			viewHoler.lxr3 = (TextView) v.findViewById(R.id.lxr3);
			viewHoler.lxdh1 = (Button) v.findViewById(R.id.lxdh1);
			viewHoler.lxdh2 = (Button) v.findViewById(R.id.lxdh2);
			viewHoler.lxdh3 = (Button) v.findViewById(R.id.lxdh3);
			viewHoler.acceptMalf = (Button) v.findViewById(R.id.acceptMalf);
			viewHoler.takePhoto = (Button) v.findViewById(R.id.takePhoto);
			viewHoler.listView = (ListViewForScrollView) v.findViewById(R.id.listView);

			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		
		viewHoler.tydh.setText(tyd.getConsign().getTydh());
		viewHoler.tydId.setText(tyd.getConsign().getId());
		viewHoler.psdz.setText(tyd.getConsign().getPsxxdz());
		String[] strLxr = tyd.getConsign().getPsdzlxr().split(",");
		String[] strLxdh = tyd.getConsign().getPsdzlxdh().split(",");
		viewHoler.lxr1.setText(strLxr[0]);
		if(strLxr.length == 2){
			viewHoler.lxr2.setVisibility(View.VISIBLE);
			viewHoler.lxr2.setText(strLxr[1]);
		}else if(strLxr.length == 3){
			viewHoler.lxr2.setVisibility(View.VISIBLE);
			viewHoler.lxr3.setVisibility(View.VISIBLE);
			viewHoler.lxr2.setText(strLxr[1]);
			viewHoler.lxr3.setText(strLxr[2]);
		}
		viewHoler.lxdh1.setText(strLxdh[0]);
		if(strLxdh.length == 2){
			viewHoler.lxdh2.setVisibility(View.VISIBLE);
			viewHoler.lxdh2.setText(strLxdh[1]);
		}else if(strLxr.length == 3){
			viewHoler.lxdh2.setVisibility(View.VISIBLE);
			((LinearLayout)viewHoler.lxdh3.getParent()).setVisibility(View.VISIBLE);
			viewHoler.lxdh2.setText(strLxdh[1]);
			viewHoler.lxdh3.setText(strLxdh[2]);
		}
		
		//402881474a9a15e9014a9a373b6100f4
		//402881474a9a15e9014a9a373b4100da
		
		adapter = new HwxxAdapter(mContext, tyd.getHwList());
		viewHoler.listView.setAdapter(adapter);
		
		viewHoler.lxdh1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(mContext, ((Button)arg0).getText().toString());
			}
		});
		
		viewHoler.lxdh2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(mContext, ((Button)arg0).getText().toString());
			}
		});
		
		viewHoler.lxdh3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(mContext, ((Button)arg0).getText().toString());
			}
		});
		
		final String tydzt = tyd.getConsign().getTydzt();
		final String tydId = tyd.getConsign().getId();
		if(rwdzt.equals("qc")){
			viewHoler.acceptMalf.setVisibility(View.VISIBLE);
			if(tydzt.equals("40")){
				viewHoler.acceptMalf.setText("司机到达");
				if("否".equals(sferzy)){
					viewHoler.takePhoto.setVisibility(View.GONE);
				}else {
					viewHoler.takePhoto.setVisibility(View.VISIBLE);
				}
			}else if("50".equals(tydzt)){
				viewHoler.acceptMalf.setText("开始维护");
				viewHoler.takePhoto.setVisibility(View.VISIBLE);
			}else if("60".equals(tydzt)){
				viewHoler.acceptMalf.setText("维护完成");
				viewHoler.takePhoto.setVisibility(View.VISIBLE);
			}else if("70".equals(tydzt)){
				viewHoler.acceptMalf.setText("返回确认");
				viewHoler.takePhoto.setVisibility(View.GONE);
			}else {
				viewHoler.acceptMalf.setVisibility(View.GONE);
			}
		}
		final LogisticConsignMobileBean lcmb = tyd;
		viewHoler.acceptMalf.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(final View arg0) {
				// TODO Auto-generated method stub
				if("40".equals(tydzt)){
					View layout = inflater.inflate(R.layout.item_layout_wcwh, null);
					dialog = new AlertDialog.Builder(mContext).create();
					dialog.setCanceledOnTouchOutside(false);
					dialog.show();
					dialog.getWindow().setContentView(layout);
					dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
					listView = (ListView) layout.findViewById(R.id.listView);
					ddAdapter = new HwxxDdAdapter(mContext, lcmb.getHwList());
					listView.setAdapter(ddAdapter);
					
					Button submit = (Button) layout.findViewById(R.id.submit);
					Button cancel = (Button) layout.findViewById(R.id.cancel);
					
					submit.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							List<HwDDBean> hwList = new ArrayList<HwDDBean>();
							int count = listView.getChildCount();
							for(int i =0 ; i < count; i ++){
								if(hddb != null){
									hddb = null;
								}
								hddb = new HwDDBean();
								KingTellerEditText hwmc = (KingTellerEditText)listView.getChildAt(i).findViewById(R.id.hwmc);
								hddb.setHwId(hwmc.getFieldValue());
								KingTellerEditText sqxxz = (KingTellerEditText)listView.getChildAt(i).findViewById(R.id.sqxxz);
								if(KingTellerJudgeUtils.isEmpty(sqxxz.getFieldValue())){
									T.showShort(mContext, "市区/县乡镇没有填写完整!");
									return;
								}
								hddb.setSqxxz(sqxxz.getFieldValue());
								hwList.add(hddb);
							}
							updateTydStatus(tydId, tydzt,hwList);
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
				}else if("60".equals(tydzt)){
					View layout = inflater.inflate(R.layout.item_layout_wcwh, null);
					dialog = new AlertDialog.Builder(mContext).create();
					dialog.setCanceledOnTouchOutside(false);
					dialog.show();
					dialog.getWindow().setContentView(layout);
					dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
					listView = (ListView) layout.findViewById(R.id.listView);
					
					jqAdapter = new HwxxJqAdapter(mContext, lcmb.getHwList());
					listView.setAdapter(jqAdapter);
					
					Button submit = (Button) layout.findViewById(R.id.submit);
					Button cancel = (Button) layout.findViewById(R.id.cancel);
					
					submit.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							List<HwDDBean> hwList = new ArrayList<HwDDBean>();
							int count = listView.getChildCount();
							for(int i =0 ; i < count; i ++){
								if(hddb != null){
									hddb = null;
								}
								hddb = new HwDDBean();
								hddb.setHwId(((TextView)listView.getChildAt(i).findViewById(R.id.hwId)).getText().toString().trim());
								KingTellerEditText jqbm = (KingTellerEditText)listView.getChildAt(i).findViewById(R.id.jqbm);
								if(KingTellerJudgeUtils.isEmpty(jqbm.getFieldValue())){
									T.showShort(mContext, "机器编码没有填写完整!");
									return;
								}
								hddb.setJqbm(jqbm.getFieldValue());
								
								hwList.add(hddb);
							}
							updateTydStatus(tydId, tydzt,hwList);
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
				}else{
					new KTAlertDialog.Builder(mContext)
					.setTitle("提示")
					.setMessage(onChangeStatus(tydzt))
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface,int pos) {
							//KingTellerProgress.showProgress(context, "加载中...");
							updateTydStatus(tydId, tydzt,null);
							dialogInterface.dismiss();
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
				
				
			}
		});
		
		final String tyId = tydId;
		final String tyzt = tydzt;
		if(!"0".equals(tyd.getConsign().getExpand2Like().trim())){
			viewHoler.takePhoto.setText("已上传"+tyd.getConsign().getExpand2Like()+"张照片");
		}else{
			viewHoler.takePhoto.setText("选择上传");
			viewHoler.takePhoto.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					new KTAlertDialog.Builder(mContext)
					.setTitle("提示")
					.setMessage(onChangePicStatus(tydzt))
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface,int pos) {
									Intent intent = new Intent();
									intent.setClass(mContext, UploadPhotosActivity.class);
									//Bimp.tempSelectBitmap.clear();
									if("40".equals(tydzt) || "60".equals(tydzt)){
										intent.putExtra(UploadPhotosActivity.UPLOAD_NUM, "1");
									}else if("50".equals(tydzt)){
										intent.putExtra(UploadPhotosActivity.UPLOAD_NUM, "9");
									}
									intent.putExtra(LogisticTaskActivity.TYDID, tydId);
									intent.putExtra("tydzt", tydzt);
									((Activity) mContext).startActivityForResult(intent, KingTellerStaticConfig.UPLOAD_PIC);
									dialogInterface.dismiss();
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
			});
		}
		
		return v;
	}

	
	public void updateTydStatus(String tydId,String tydzt,List<HwDDBean> hwList){
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		AddressBean address = KingTellerApplication.getApplication().getCurAddress();
		Log.e("address",address.getAddress()+" "+address.getLat()+"  "+address.getLng());
		params.put("tydId",tydId);
		params.put("status",tydzt);
		params.put("address", address.getAddress());
		params.put("lat", String.valueOf(address.getLat()));
		params.put("lng", String.valueOf(address.getLng()));
		
		if(hwList != null){
			params.put("hwList", gson.toJson(hwList));
		}
		
		Log.e("23868564385648568465",params.toString());
		
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WljktydztUrl), params,
				new AjaxHttpCallBack<ReturnBackStatus>(mContext,
						new TypeToken<ReturnBackStatus>() {
						}.getType(), true) {

						@Override
						public void onStart() {
							KingTellerProgressUtils.showProgress(mContext, "加载中...");
						}
						
						@Override
						@SuppressLint("ShowToast")
						public void onError(int errorNo, String strMsg) {
							super.onError(errorNo, strMsg);
							KingTellerProgressUtils.closeProgress();
						}
			
						@Override
						public void onFinish() {
							super.onFinish();
							KingTellerProgressUtils.closeProgress();
						}
				
					@Override
					public void onDo(ReturnBackStatus data) {
						//context
						if(data.getResult().equals("success")){
							((LogisticTaskActivity) mContext).initData();
						}
						T.showShort(mContext, data.getMessage());
					};
				});
	}
	
	private String onChangeStatus(String tydzt){
		if ("40".equals(tydzt)) {
			return "是否到达目的地?";
		} else if ("50".equals(tydzt)) {
			return "是否开始卸货?";
		} else if("60".equals(tydzt)){
			return "客户是否已完成签收";
		}else if("70".equals(tydzt)){
			return "是否返回确认?";
		}
		return "";
	}
	
	private String onChangePicStatus(String tydzt){
		if ("40".equals(tydzt)) {
			return "拍摄转运签收单";
		} else if ("50".equals(tydzt)) {
			return "请分别拍摄机器拆包前及拆包后的四个面及配件箱正面";
		} else if("60".equals(tydzt)){
			return "请拍摄客户完成签名盖章的签收单";
		}else if("70".equals(tydzt)){
			return "是否返回确认?";
		}
		return "";
	}
	
	private static class ViewHoler{
		public TextView tydh;
		public TextView psdz;
		public TextView lxr1;
		public TextView lxr2;
		public TextView lxr3;
		public TextView tydId;
		public Button lxdh1;
		public Button lxdh2;
		public Button lxdh3;
		public Button acceptMalf;
		public Button takePhoto;
		public ListViewForScrollView listView;
	} 

}
