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

public class TydmkytjAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<LogisticConsignMobileBean> tydlist = new ArrayList<LogisticConsignMobileBean>();
	private Context mContext;
	private LogisticConsignMobileBean tyd;
	private HwxxAdapter adapter;
	private String rwdzt;
	private HwxxDdAdapter ddAdapter;
	private HwxxJqAdapter jqAdapter;
	private Gson gson = new Gson();
	private ListView listView ;
	private Dialog dialog;
	private HwDDBean hddb = null;

	public TydmkytjAdapter(Context context,
			List<LogisticConsignMobileBean> tydlist, String rwdzt) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.tydlist = tydlist;
		this.rwdzt = rwdzt;
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
	public View getView(final int postion, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub

		tyd = tydlist.get(postion);

		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_add_tydmkytj, null);
			viewHoler.tydh = (TextView) v.findViewById(R.id.tydh);
			viewHoler.tydId = (TextView) v.findViewById(R.id.tydId);
			viewHoler.tcdz = (TextView) v.findViewById(R.id.tcdz);
			viewHoler.tclxr1 = (TextView) v.findViewById(R.id.tclxr1);
			viewHoler.tclxr2 = (TextView) v.findViewById(R.id.tclxr2);
			viewHoler.tclxr3 = (TextView) v.findViewById(R.id.tclxr3);
			viewHoler.tclxdh1 = (TextView) v.findViewById(R.id.tclxdh1);
			viewHoler.tclxdh2 = (TextView) v.findViewById(R.id.tclxdh2);
			viewHoler.tclxdh3 = (TextView) v.findViewById(R.id.tclxdh3);
			viewHoler.jqbm = (TextView) v.findViewById(R.id.jqbm);
			viewHoler.trdz = (TextView) v.findViewById(R.id.trdz);
			viewHoler.trlxr1 = (TextView) v.findViewById(R.id.trlxr1);
			viewHoler.trlxr2 = (TextView) v.findViewById(R.id.trlxr2);
			viewHoler.trlxr3 = (TextView) v.findViewById(R.id.trlxr3);
			viewHoler.trlxdh1 = (TextView) v.findViewById(R.id.trlxdh1);
			viewHoler.trlxdh2 = (TextView) v.findViewById(R.id.trlxdh2);
			viewHoler.trlxdh3 = (TextView) v.findViewById(R.id.trlxdh3);
			viewHoler.acceptMalf = (Button) v.findViewById(R.id.acceptMalf);
			viewHoler.takePhoto = (Button) v.findViewById(R.id.takePhoto);
			viewHoler.listView = (ListViewForScrollView) v
					.findViewById(R.id.listView);

			v.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) v.getTag();
		}

		viewHoler.tydId.setText(tyd.getConsign().getId());
		viewHoler.tydh.setText(tyd.getConsign().getTydh());
		viewHoler.tcdz.setText(tyd.getConsign().getDcdz());
		String[] dclxrStr = tyd.getConsign().getDcdzlxr().split(",");
		String[] dclxdhStr = tyd.getConsign().getDcdzlxdh().split(",");
		String[] drlxrStr = tyd.getConsign().getDrdzlxr().split(",");
		String[] drlxdhStr = tyd.getConsign().getDrdzlxdh().split(",");
		viewHoler.tclxr1.setText(dclxrStr[0]);
		if(dclxrStr.length == 2){
			viewHoler.tclxr2.setVisibility(View.VISIBLE);
			viewHoler.tclxr2.setText(dclxrStr[1]);
		}else if(dclxrStr.length == 3){
			viewHoler.tclxr2.setVisibility(View.VISIBLE);
			viewHoler.tclxr3.setVisibility(View.VISIBLE);
			viewHoler.tclxr2.setText(dclxrStr[1]);
			viewHoler.tclxr3.setText(dclxrStr[2]);
		}
		viewHoler.tclxdh1.setText(dclxdhStr[0]);
		if(dclxdhStr.length == 2){
			viewHoler.tclxdh2.setVisibility(View.VISIBLE);
			viewHoler.tclxdh2.setText(dclxrStr[1]);
		}else if(dclxdhStr.length == 3){
			viewHoler.tclxdh2.setVisibility(View.VISIBLE);
			((LinearLayout)viewHoler.tclxdh3.getParent()).setVisibility(View.VISIBLE);
			viewHoler.tclxdh2.setText(dclxdhStr[1]);
			viewHoler.tclxdh3.setText(dclxdhStr[2]);
		}
		viewHoler.jqbm.setText(tyd.getConsign().getJqbm());
		viewHoler.trdz.setText(tyd.getConsign().getDrdz());
		viewHoler.trlxr1.setText(drlxrStr[0]);
		if(drlxrStr.length == 2){
			viewHoler.trlxr2.setVisibility(View.VISIBLE);
			viewHoler.trlxr2.setText(drlxrStr[1]);
		}else if(drlxrStr.length == 3){
			viewHoler.trlxr2.setVisibility(View.VISIBLE);
			viewHoler.trlxr3.setVisibility(View.VISIBLE);
			viewHoler.trlxr2.setText(drlxrStr[1]);
			viewHoler.trlxr3.setText(drlxrStr[2]);
		}
		
		viewHoler.trlxdh1.setText(drlxdhStr[0]);
		if(drlxdhStr.length == 2){
			viewHoler.trlxdh2.setVisibility(View.VISIBLE);
			viewHoler.trlxdh2.setText(drlxdhStr[1]);
		}else if(drlxrStr.length == 3){
			viewHoler.trlxdh2.setVisibility(View.VISIBLE);
			((LinearLayout)viewHoler.trlxdh3.getParent()).setVisibility(View.VISIBLE);
			viewHoler.trlxdh2.setText(drlxdhStr[1]);
			viewHoler.trlxdh3.setText(drlxdhStr[2]);
		}
		adapter = new HwxxAdapter(mContext, tyd.getHwList());
		viewHoler.listView.setAdapter(adapter);

		viewHoler.tclxdh1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(mContext, ((Button)arg0).getText().toString());
			}
		});
		
		viewHoler.tclxdh2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(mContext, ((Button)arg0).getText().toString());
			}
		});

		viewHoler.tclxdh3.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(mContext, ((Button)arg0).getText().toString());
			}
		});
		
		viewHoler.trlxdh1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(mContext, ((Button) arg0).getText().toString());
			}
		});

		viewHoler.trlxdh2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(mContext, ((Button) arg0).getText().toString());
			}
		});

		viewHoler.trlxdh3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(mContext, ((Button) arg0).getText().toString());
			}
		});
		
		final String tydzt = tyd.getConsign().getTydzt();
		final String tydId = tyd.getConsign().getId();
		if (rwdzt.equals("qc")) {
			viewHoler.acceptMalf.setVisibility(View.VISIBLE);
			if (tydzt.equals("40")) {
				viewHoler.acceptMalf.setText("司机到达");
			} else if ("50".equals(tydzt)) {
				viewHoler.acceptMalf.setText("开始维护");
				viewHoler.takePhoto.setVisibility(View.VISIBLE);
			} else if ("60".equals(tydzt)) {
				viewHoler.acceptMalf.setText("维护完成");
				viewHoler.takePhoto.setVisibility(View.VISIBLE);
			} else if ("70".equals(tydzt)) {
				viewHoler.acceptMalf.setText("返回确认");
				viewHoler.takePhoto.setVisibility(View.GONE);
			} else {
				viewHoler.acceptMalf.setVisibility(View.GONE);
			}
		}

		final LogisticConsignMobileBean lcmb = tyd;
		viewHoler.acceptMalf.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
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
							Log.e("11111111111",count+"");
							for(int i =0 ; i < count; i ++){
								if(hddb != null){
									hddb = null;
								}
								hddb = new HwDDBean();
								hddb.setHwId(((KingTellerEditText)listView.getChildAt(i).findViewById(R.id.hwmc)).getFieldValue());
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
				}else{
					new KTAlertDialog.Builder(mContext)
					.setTitle("提示")
					.setMessage(onChangeStatus(tydzt))
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface,
										int pos) {
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

	public void updateTydStatus(String tydId, String tydzt,List<HwDDBean> hwList) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		AddressBean address = KingTellerApplication.getApplication().getCurAddress();
		Log.e("address",address.getAddress()+" "+address.getLat()+"  "+address.getLng());
		params.put("tydId", tydId);
		params.put("status", tydzt);
		params.put("address", address.getAddress());
		params.put("lat", String.valueOf(address.getLat()));
		params.put("lng", String.valueOf(address.getLng()));
		if(hwList != null){
			params.put("hwList", gson.toJson(hwList));
		}else{
			params.put("hwList","");
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
						if (data.getResult().equals("success")) {
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
	
	private static class ViewHoler {
		public TextView tydId;
		public TextView tydh;
		public TextView tcdz;
		public TextView tclxr1;
		public TextView tclxr2;
		public TextView tclxr3;
		public TextView tclxdh1;
		public TextView tclxdh2;
		public TextView tclxdh3;
		public TextView jqbm;
		public TextView trdz;
		public TextView trlxr1;
		public TextView trlxr2;
		public TextView trlxr3;
		public TextView trlxdh1;
		public TextView trlxdh2;
		public TextView trlxdh3;
		public Button acceptMalf;
		public Button takePhoto;
		public ListViewForScrollView listView;
	}
}
