package com.kingteller.client.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.common.CommonSelectClgcActivity;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.activity.common.CommonSelectGZMSActivity;
import com.kingteller.client.activity.common.TreeChooserActivity;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.workorder.BJBean;
import com.kingteller.client.bean.workorder.JqzdDataBean;
import com.kingteller.client.bean.workorder.WddzBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.view.KingTellerEditText.OnChangeListener;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * 维护信息
 * 
 * @author 王定波
 * 
 */
public class BjGroupView extends GroupViewBase {

	private KingTellerEditText troubleType;
	private KingTellerEditText installBjWlId;
	private KingTellerEditText downBjWlId;
	private KingTellerEditText isChangeModule;
	private KingTellerEditText troubleRemarkId;
	private KingTellerEditText handleSubId;
	private KingTellerEditText troubleModule;
	private LinearLayout layout_wl;
	private KingTellerEditText otherDescription;
	private KingTellerEditText changeSituation;
	private KingTellerEditText troubleJqZd;
	private KingTellerEditText troubleYjPz;
	private KingTellerEditText otherDescription_troubleRemarkId;
	private TextView typeIdTv;
	
	private String jqId;
	
	private CommonSelectData csd;
	// private AutoCompleteTextView autoComplete;

	public BjGroupView(Context context, String pplb, String jqId) {
		super(context);
		// TODO Auto-generated constructor stub
		this.jqId = jqId;
	}

	public BjGroupView(Context context, boolean isdel) {
		super(context, isdel);
	}
	
	public BjGroupView(Context context, boolean isdel, String jqId) {
		// TODO Auto-generated constructor stub
		super(context, isdel);
		this.jqId = jqId;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_bj, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 25);
		setLayoutParams(lp);

		if (isdel)
			findViewById(R.id.btn_delete).setVisibility(View.VISIBLE);
		else
			findViewById(R.id.btn_delete).setVisibility(View.GONE);

		findViewById(R.id.btn_delete).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LinearLayout) getParent()).removeView(BjGroupView.this);
			}
		});

		findViewById(R.id.btn_downBjWlId).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//changeSituation.setFieldTextAndValueFromValue("1");
						downBjWlId.setFieldTextAndValue("");
					}
				});

		findViewById(R.id.btn_installBjWlId).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						installBjWlId.setFieldTextAndValue("");
					}
				});
		typeIdTv = (TextView) findViewById(R.id.bj_type_id);
		troubleJqZd = (KingTellerEditText)findViewById(R.id.troubleJqZd);
		troubleYjPz = (KingTellerEditText)findViewById(R.id.troubleYjPz);
		changeSituation = (KingTellerEditText)findViewById(R.id.changeSituation);
		
		layout_wl = (LinearLayout) findViewById(R.id.layout_wl);
		troubleType = (KingTellerEditText) findViewById(R.id.troubleType);
		
		downBjWlId = (KingTellerEditText) findViewById(R.id.downBjWlId);
		installBjWlId = (KingTellerEditText) findViewById(R.id.installBjWlId);
		
		troubleModule = (KingTellerEditText) findViewById(R.id.troubleModule);
		isChangeModule = (KingTellerEditText) findViewById(R.id.isChangeModule);
		troubleRemarkId = (KingTellerEditText) findViewById(R.id.troubleRemarkId);
		handleSubId = (KingTellerEditText) findViewById(R.id.handleSubId);
		otherDescription_troubleRemarkId =  (KingTellerEditText) findViewById(R.id.otherDescription_troubleRemarkId);
		otherDescription = (KingTellerEditText) findViewById(R.id.otherDescription);

		//故障类别
		troubleType.setOnDialogClickLister(new OnDialogClickLister() {

			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getContext(), CommonSelectDataActivity.class);
				intent.putExtra(CommonSelectDataActivity.TYPE, R.array.bjmk);
				intent.putExtra("pplb", ((KingTellerEditText)((Activity)getContext()).findViewById(R.id.pplb)).getFieldValue());

				((Activity) getContext()).startActivityForResult(intent, KingTellerStaticConfig.SELECT_BJMK);
				((LinearLayout) getParent()).setTag(troubleType);
			}
		});
		
		

		//故障部件
		troubleModule.setOnDialogClickLister(new OnDialogClickLister() {

			@Override
			public void OnDialogClick() {
				if (KingTellerJudgeUtils.isEqualEmpty(troubleType.getFieldValue())) {
					T.showShort(getContext(), "故障类别必须填写!");
				} else {
					Intent intent = new Intent(getContext(), CommonSelectDataActivity.class);
					intent.putExtra(CommonSelectDataActivity.TITLE, "故障部件");
					intent.putExtra(CommonSelectDataActivity.TYPE, R.array.bjwl);
					
					intent.putExtra("pplb", ((KingTellerEditText)((Activity)getContext()).findViewById(R.id.pplb)).getFieldValue());

					intent.putExtra(CommonSelectDataActivity.EXTRADATA, KingTellerJudgeUtils.isEmpty(troubleType.getFieldValue()) ? "0" : troubleType.getFieldValue());
					((Activity) getContext()).startActivityForResult(intent, KingTellerStaticConfig.REQUEST_GZBJ);
					((LinearLayout) getParent()).setTag(troubleModule);
				}
			}
		});
		

		//故障描述
		troubleRemarkId.setOnDialogClickLister(new OnDialogClickLister() {

			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				if (KingTellerJudgeUtils.isEqualEmpty(troubleType.getFieldValue())) {
					T.showShort(getContext(), "故障类别必须填写!");
				} else {
					// CommonSelectDataActivity
					Intent intent = new Intent(getContext(), CommonSelectGZMSActivity.class);
					intent.putExtra(CommonSelectGZMSActivity.TYPE, R.array.gzms);
					intent.putExtra(CommonSelectGZMSActivity.EXTRADATA, troubleType.getFieldValue());
					intent.putExtra(CommonSelectGZMSActivity.TROUBLEMODULE, KingTellerJudgeUtils.isEmptyGetString(troubleModule.getFieldValue()));
					intent.putExtra("pplb", ((KingTellerEditText)((Activity)getContext()).findViewById(R.id.pplb)).getFieldValue());

					((Activity) getContext()).startActivityForResult(intent, KingTellerStaticConfig.SELECT_GZMS);
					((LinearLayout) getParent()).setTag(troubleRemarkId);
				}
			}
		});
		
		//处理过程
		handleSubId.setOnDialogClickLister(new OnDialogClickLister() {

			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				if (KingTellerJudgeUtils.isEqualEmpty(troubleRemarkId.getFieldValue())) {
					T.showShort(getContext(), "故障描述必须填写!");
				} else {
					Intent intent = new Intent(getContext(),CommonSelectClgcActivity.class);
					intent.putExtra(CommonSelectDataActivity.TYPE, R.array.clgc);
					intent.putExtra(CommonSelectDataActivity.EXTRADATA,KingTellerJudgeUtils.isEqualEmpty(troubleRemarkId.getFieldValue()) ? "" : troubleRemarkId.getFieldValue());
					intent.putExtra("pplb", ((KingTellerEditText)((Activity)getContext()).findViewById(R.id.pplb)).getFieldValue());

					((Activity) getContext()).startActivityForResult(intent,KingTellerStaticConfig.SELECT_CLGC);
					((LinearLayout) getParent()).setTag(handleSubId);
				}
			}
		});

		//机器字段
		troubleYjPz.setOnDialogClickLister(new OnDialogClickLister() {
			
			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				if (KingTellerJudgeUtils.isEqualEmpty(troubleModule.getFieldValue())) {
					T.showShort(getContext(), "故障部件必须填写!");
				} else {
					Intent intent = new Intent(getContext(), CommonSelectDataActivity.class);
					intent.putExtra(CommonSelectDataActivity.TYPE, R.array.jqzd);
					intent.putExtra("wlId", troubleModule.getFieldValue());
					intent.putExtra("jqId", jqId);
					intent.putExtra("pplb", ((KingTellerEditText)((Activity)getContext()).findViewById(R.id.pplb)).getFieldValue());
					intent.putExtra(CommonSelectDataActivity.EXTRADATA, KingTellerJudgeUtils.isEmptyGetString(troubleModule.getFieldValue()));
					((Activity) getContext()).startActivityForResult(intent, KingTellerStaticConfig.REQUEST_JQZD);
					((LinearLayout) getParent()).setTag(troubleYjPz);
				}
			}
		});
		
		//安装的物料
		installBjWlId.setOnDialogClickLister(new OnDialogClickLister() {

			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				if (KingTellerJudgeUtils.isEqualEmpty(troubleModule.getFieldValue())) {
					//如果以后要加上机器字段，要在这里改变控制
					T.showShort(getContext(), "故障部件必须填写!");
				} else {
					Intent intent = new Intent(getContext(), TreeChooserActivity.class);
					intent.putExtra(CommonSelectDataActivity.TITLE, "安装物料");
					intent.putExtra(CommonSelectDataActivity.TYPE, R.array.bjwl);
					intent.putExtra("pplb", ((KingTellerEditText)((Activity)getContext()).findViewById(R.id.pplb)).getFieldValue());
					intent.putExtra(CommonSelectDataActivity.EXTRADATA, KingTellerJudgeUtils.isEmptyGetString(troubleModule.getFieldValue()));
					intent.putExtra("type_id", KingTellerJudgeUtils.isEmptyGetString(typeIdTv.getText().toString()));
					((Activity) getContext()).startActivityForResult(intent, KingTellerStaticConfig.REQUEST_BJWL);
					((LinearLayout) getParent()).setTag(installBjWlId);
				}
			}
		});
				
		//原装的物料
		downBjWlId.setOnDialogClickLister(new OnDialogClickLister() {

			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				if (KingTellerJudgeUtils.isEqualEmpty(troubleModule.getFieldValue())) {
					//如果以后要加上机器字段，要在这里改变控制
					T.showShort(getContext(), "故障部件必须填写!");
				} else {
					Intent intent = new Intent(getContext(), TreeChooserActivity.class);
					intent.putExtra(CommonSelectDataActivity.TITLE, "原装物料");
					intent.putExtra(CommonSelectDataActivity.TYPE, R.array.bjwl);
					intent.putExtra("pplb", ((KingTellerEditText)((Activity)getContext()).findViewById(R.id.pplb)).getFieldValue());
					intent.putExtra(CommonSelectDataActivity.EXTRADATA, KingTellerJudgeUtils.isEmptyGetString(troubleModule.getFieldValue()));
					intent.putExtra("type_id", KingTellerJudgeUtils.isEmptyGetString(typeIdTv.getText().toString()));
					((Activity) getContext()).startActivityForResult(intent, KingTellerStaticConfig.REQUEST_BJWL);
					((LinearLayout) getParent()).setTag(downBjWlId);
				}
			}
		});
		
		isChangeModule.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios01));
		//是否更换备件 默认设置
		isChangeModule.setFieldTextAndValueFromValue("1");
		//是否更换备件
		isChangeModule.setOnChangeListener(new OnChangeListener() {

			@Override
			public void onChanged(CommonSelectData data) {
				// TODO Auto-generated method stub
				if (data.getValue().equals("0")) {
					troubleJqZd.setFieldTextAndValue("");
					troubleYjPz.setFieldTextAndValue("");
					troubleYjPz.setFieldEnabled(false);
					changeSituation.setFieldTextAndValue("");
					installBjWlId.setFieldTextAndValue("");
					installBjWlId.setFieldEnabled(false);
					downBjWlId.setFieldTextAndValue("");
					downBjWlId.setFieldEnabled(false);
					
				} else if (data.getValue().equals("1")) {
					
					troubleYjPz.setFieldEnabled(true);
					downBjWlId.setFieldEnabled(true);
					installBjWlId.setFieldEnabled(true);
					//TODO 更新 硬件配置 时，把下面注释
					
					if(!KingTellerJudgeUtils.isEmpty(troubleModule.getFieldValue())) {
						KTHttpClient fh = new KTHttpClient(true);
						AjaxParams params = new AjaxParams();
			
						params.put("jqid", jqId);
						params.put("wlid", troubleModule.getFieldValue());
						
						fh.post(KingTellerConfigUtils.CreateUrl(getContext(), KingTellerUrlConfig.WebYjpzUrl), params, new AjaxHttpCallBack<BasePageBean<JqzdDataBean>>(getContext(),
								new TypeToken<BasePageBean<JqzdDataBean>>() {
								}.getType(), true) {
				
				
							@Override
							public void onFinish() {
								
							}
							
							@Override
							public void onDo(BasePageBean<JqzdDataBean> basePageBean) {
								//后台返回数据与Bean类型不一致时跳到onError方法中去
								Log.e("onDo", basePageBean.toString());
								List<JqzdDataBean> listJqzdData = basePageBean.getList();
								
								if(listJqzdData == null) {
									troubleYjPz.setFieldTextAndValue("", "");
									troubleYjPz.setFieldEnabled(false);
									changeSituation.setFieldTextAndValueFromValue("2");
								}else {
									if(listJqzdData.size() == 1){
										CommonSelectData csd = new CommonSelectData();
										JqzdDataBean jqzdData = listJqzdData.get(0);
										csd.setText(jqzdData.getAttrName());
										csd.setValue(jqzdData.getAttrCode());
										
										troubleJqZd.setFieldTextAndValue(csd);
										
										csd.setText(jqzdData.getAttrValue());
										csd.setValue(jqzdData.getAttrValueId());
									
										troubleYjPz.setFieldTextAndValue(csd);
										
										if(KingTellerJudgeUtils.isEmpty(jqzdData.getAttrValueId())) {
											changeSituation.setFieldTextAndValueFromValue("2");
										}else {
											changeSituation.setFieldTextAndValueFromValue("0");
										}
										csd.setText(jqzdData.getAttrValue());
										csd.setValue(jqzdData.getAttrValueId());
										downBjWlId.setFieldTextAndValue(csd);
					
										troubleYjPz.setFieldEnabled(false);
									}else {
										troubleYjPz.setFieldEnabled(true);
									}
									
								}
								
							};
						});
					}
					
				}
			}
		});
		
		changeSituation.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.changeSituation));
		/*
		//更换情况
		changeSituation.setOnChangeListener(new OnChangeListener() {
			
			@Override
			public void onChanged(CommonSelectData data) {
				if (KingTellerJudgeUtils.isEqualEmpty(troubleJqZd.getFieldValue())) {
					if(!KingTellerJudgeUtils.isEmpty(changeSituation.getFieldText())) {
						T.showShort(getContext(), "机器字段必须填写!");
					changeSituation.setFieldTextAndValue("");
					}
				} else {
					if(data.getValue().equals("0")) { //原配置正确
						if(csd != null) {
							downBjWlId.setFieldTextAndValue(csd);
							csd = null;
						}
					}else if(data.getValue().equals("1")) { //原配置错误且已修改
						if(csd == null) {
							csd = new CommonSelectData();
							csd.setText(troubleYjPz.getFieldText());
							csd.setValue(troubleYjPz.getFieldValue());
							downBjWlId.setFieldTextAndValue("");
						}
					}else if(data.getValue().equals("2")) { ////无相应配置
					}
				}
			}
		});
		*/
	}
	
	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BJBean getData() {
		// TODO Auto-generated method stub
		BJBean bean = new BJBean();
		bean.setTroubleModule(troubleModule.getFieldValue());
		bean.setTroubleModuleLike(troubleModule.getFieldText());
		bean.setTroubleType(troubleType.getFieldValue());
		bean.setTroubleTypeLike(troubleType.getFieldText());
		bean.setDownBjWlId(downBjWlId.getFieldValue());
		bean.setDownBjWlName(downBjWlId.getFieldText());
		bean.setInstallBjWlId(installBjWlId.getFieldValue());
		bean.setInstallBjWlName(installBjWlId.getFieldText());
		bean.setHandleSubId(handleSubId.getFieldValue());
		bean.setHandleSub(handleSubId.getFieldText());
		if (!KingTellerJudgeUtils.isEmpty(otherDescription.getFieldValue())) {
			bean.setRemark(otherDescription.getFieldValue());
		} else {
			bean.setRemark("");
		}
		if (!KingTellerJudgeUtils.isEmpty(otherDescription_troubleRemarkId.getFieldValue())) {
			bean.setExpand4(otherDescription_troubleRemarkId.getFieldValue());
		}else {
			bean.setExpand4("");
		}
		bean.setTroubleRemarkId(troubleRemarkId.getFieldValue());
		bean.setTroubleRemark(troubleRemarkId.getFieldText());
		bean.setHandleSub(handleSubId.getFieldText());
		
		bean.setIsChangeModule(Long.valueOf(isChangeModule.getFieldValue()));
		
		bean.setMachineAttr(troubleJqZd.getFieldValue());
		bean.setMachineAttrName(troubleJqZd.getFieldText());
		bean.setMachineBjId(troubleYjPz.getFieldValue());
		bean.setMachineBjName(troubleYjPz.getFieldText());
		//TODO 更新 硬件配置 时，把下面注释
		
		if("1".equals(isChangeModule.getFieldValue()) 
				&& !"".equals(KingTellerJudgeUtils.isEmptyGetString(troubleModule.getFieldValue()))) {
			if(!"2".equals(changeSituation.getFieldValue())) {
				if(KingTellerJudgeUtils.isEmptyGetString(troubleYjPz.getFieldValue())
						.equals(KingTellerJudgeUtils.isEmptyGetString(downBjWlId.getFieldValue()))) {
					changeSituation.setFieldTextAndValueFromValue("0");
				}else {
					changeSituation.setFieldTextAndValueFromValue("1");
				}
			}
			
		}else {
			changeSituation.setFieldTextAndValue("");
		}
		
		bean.setChangeSm(changeSituation.getFieldValue());

		return bean;
	}

	public void setData(BJBean data) {

		troubleRemarkId.setFieldTextAndValue(data.getTroubleRemark(),data.getTroubleRemarkId());
		handleSubId.setFieldTextAndValue(data.getHandleSub(),data.getHandleSubId());
		troubleType.setFieldTextAndValue(data.getTroubleTypeLike(),data.getTroubleType());
		troubleJqZd.setFieldTextAndValue(data.getMachineAttrName(), data.getMachineAttr());
		troubleYjPz.setFieldTextAndValue(KingTellerJudgeUtils.isEmptyGetString(data.getMachineBjName()), KingTellerJudgeUtils.isEmptyGetString(data.getMachineBjId()));
		if(!KingTellerJudgeUtils.isEmpty(data.getExpand5())) {
			troubleYjPz.setFieldEnabled(false);
		}
		changeSituation.setFieldTextAndValueFromValue(data.getChangeSm());
		installBjWlId.setFieldTextAndValue(data.getInstallBjWlName(),data.getInstallBjWlId());
		downBjWlId.setFieldTextAndValue(data.getDownBjWlName(),data.getDownBjWlId());
		troubleModule.setFieldTextAndValue(data.getTroubleModuleLike(),data.getTroubleModule());
		otherDescription.setFieldTextAndValue(data.getRemark());
		otherDescription_troubleRemarkId.setFieldTextAndValue(data.getExpand4());
		isChangeModule.setFieldTextAndValueFromValue(String.valueOf(data.getIsChangeModule()));
		
		//查询只为了判断 硬件配置哪里有多少个，只有一个或没有的，troubleYjPz不可选
		if(!KingTellerJudgeUtils.isEmpty(data.getTroubleModule()) && "1".equals(isChangeModule.getFieldValue())) {
			KTHttpClient fh = new KTHttpClient(true);
			AjaxParams params = new AjaxParams();
			params.put("jqid", jqId);
			params.put("wlid", troubleModule.getFieldValue());
			fh.post(KingTellerConfigUtils.CreateUrl(getContext(), "/sysmgr/workreportmobile/queryMachineComponentList_workReportMobileNew.action"), 
					params, 
					new AjaxHttpCallBack<BasePageBean<JqzdDataBean>>(getContext(),
					new TypeToken<BasePageBean<JqzdDataBean>>() {
					}.getType(), true) {
	
	
				@Override
				public void onFinish() {
				}
				
				@Override
				public void onError(int errorNo, String strMsg) {
					// TODO Auto-generated method stub
					Log.e("error", errorNo + ", "+strMsg);
				}
				
				@Override
				public void onDo(BasePageBean<JqzdDataBean> basePageBean) {
					List<JqzdDataBean> listJqzdData = basePageBean.getList();
					if(!(listJqzdData != null && listJqzdData.size() > 1)) {
						troubleYjPz.setFieldEnabled(false);
					}
				};
			});
		}
		
	}
	
}
