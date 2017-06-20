package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.bean.offlineupload.OfflineSelectDataBean;
import com.kingteller.client.bean.offlineupload.OfflineUploadBean;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.bean.qrcode.QRDotMachineBean;
import com.kingteller.client.bean.workorder.LogisticsReportBean;
import com.kingteller.client.bean.workorder.OtherMatterReportBean;
import com.kingteller.client.bean.workorder.RepairReportBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.dialog.NormalListDialog;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class OfflineUploadAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private List<OfflineUploadBean> lists = new ArrayList<OfflineUploadBean>();
	private Context mContext;
	private HashMap<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();
	
	private ChangeOfflineDataClickL ChangeOfflineDataClickL;
	
	public OfflineUploadAdapter(Context context, List<OfflineUploadBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.mContext = context;
		configCheckMap(false);
	} 
	
	public void setLists(List<OfflineUploadBean> lists) {
		this.lists = lists;
		configCheckMap(false);
		notifyDataSetChanged();
	}
	
	/**
	 * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
	 */
	public void configCheckMap(boolean bool) {
		for (int i = 0; i < lists.size(); i++) {
			isCheckedMap.put(i, bool);
		}
	}
	@Override
	public int getCount() {
		if (lists == null)
			return 0;
		return lists.size();
	}

	@Override
	public Object getItem(int location) {
		return lists.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		OfflineUploadBean data = (OfflineUploadBean) lists.get(position);
		
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_offlineupload_data,null);
			viewHoler.mOfflineText_dh = (TextView) convertView.findViewById(R.id.item_offline_bg_dh);
			viewHoler.mOfflineText_jqbh_tou = (TextView) convertView.findViewById(R.id.item_offline_bg_jqbm_tou);
			viewHoler.mOfflineText_jqbh = (TextView) convertView.findViewById(R.id.item_offline_bg_jqbm);
			viewHoler.mOfflineText_wdjc = (TextView) convertView.findViewById(R.id.item_offline_bg_wdjc);
			viewHoler.mOfflineText_rwxx_tou = (TextView) convertView.findViewById(R.id.item_offline_bg_rwxx_tou);
			viewHoler.mOfflineText_rwxx = (TextView) convertView.findViewById(R.id.item_offline_bg_rwxx);
			viewHoler.mOfflineText_hcsj = (TextView) convertView.findViewById(R.id.item_offline_bg_hcsj);
			viewHoler.mOfflineCheckBox = (CheckBox) convertView.findViewById(R.id.item_offline_checkbox);
			viewHoler.mOfflineSelectBtn = (Button) convertView.findViewById(R.id.item_offline_selectbtn);
			
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		

		viewHoler.mOfflineCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isCheckedMap.put(position, isChecked); //将选择项加载到map里面寄存
			}
		});
		
		if (isCheckedMap.get(position) == null) {
			isCheckedMap.put(position, false);
		}
		viewHoler.mOfflineCheckBox.setChecked(isCheckedMap.get(position));
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(((CheckBox) arg0.findViewById(R.id.item_offline_checkbox)).isChecked()){
					((CheckBox) arg0.findViewById(R.id.item_offline_checkbox)).setChecked(false);
					isCheckedMap.put(position, false);
					
				}else{
					((CheckBox) arg0.findViewById(R.id.item_offline_checkbox)).setChecked(true);
					isCheckedMap.put(position, true);
				}
			}
		});
		
		
		viewHoler.mOfflineText_wdjc.setVisibility(View.VISIBLE);
		
		/**====================================================================================**/
		if(!KingTellerJudgeUtils.isEmpty(data.getCacheType())){
			switch (KingTellerDbUtils.getTypeValue(data.getCacheType())) {
				case 0:
					break;
				case 1://维护工单
					RepairReportBean reportData = getDataFromString(data.getCacheData(), RepairReportBean.class);
					viewHoler.mOfflineText_dh.setText("维护工单：" + "(" + reportData.getOrderNo() + ")");
					viewHoler.mOfflineText_jqbh_tou.setText("机器编号：");
					viewHoler.mOfflineText_jqbh.setText(reportData.getExpand1());
					viewHoler.mOfflineText_wdjc.setText("网点设备简称：" + reportData.getWdsbmc());
					viewHoler.mOfflineText_rwxx_tou.setText("任务信息：");
					viewHoler.mOfflineText_rwxx.setText(reportData.getWorkOrderPrompt());
					viewHoler.mOfflineText_hcsj.setText("缓存时间：" + data.getCacheTime());
					break;
				case 2://其他事物
					OtherMatterReportBean otherReportData = getDataFromString(data.getCacheData(), OtherMatterReportBean.class);
					viewHoler.mOfflineText_dh.setText("其他事物：" + "(" + otherReportData.getOrderNo() + ")");
					viewHoler.mOfflineText_jqbh_tou.setText("机器编号：");
					viewHoler.mOfflineText_jqbh.setText("无");
					viewHoler.mOfflineText_wdjc.setText("网点设备简称：无" );
					viewHoler.mOfflineText_rwxx_tou.setText("任务信息：");
					viewHoler.mOfflineText_rwxx.setText(otherReportData.getTroubleRemark());
					viewHoler.mOfflineText_hcsj.setText("缓存时间：" + data.getCacheTime());
					break;
				case 3://物流报告
					LogisticsReportBean logisticsReportData = getDataFromString(data.getCacheData(), LogisticsReportBean.class);
					viewHoler.mOfflineText_dh.setText("物流报告：" + "(" + logisticsReportData.getOrderNo() + ")");
					viewHoler.mOfflineText_jqbh_tou.setText("机器编号：");
					viewHoler.mOfflineText_jqbh.setText("无");
					viewHoler.mOfflineText_wdjc.setText("网点设备简称：无" );
					viewHoler.mOfflineText_rwxx_tou.setText("任务信息：");
					viewHoler.mOfflineText_rwxx.setText(logisticsReportData.getTroubleRemark());
					viewHoler.mOfflineText_hcsj.setText("缓存时间：" + data.getCacheTime());
					break;
				case 5://设备录入
					List<QRDotMachineBean> dotMachineData = KingTellerJsonUtils.getPersons(data.getCacheData(), QRDotMachineBean.class);
				
					String [] stringArrdotMachine = data.getCacheOtherData().split(",");   //机器id,机器编号
					
					viewHoler.mOfflineText_dh.setText("设备录入");
					viewHoler.mOfflineText_jqbh_tou.setText("机器编号：");
					viewHoler.mOfflineText_jqbh.setText(stringArrdotMachine[1]);
					viewHoler.mOfflineText_wdjc.setVisibility(View.GONE);
					viewHoler.mOfflineText_rwxx_tou.setText("任务信息：");
					viewHoler.mOfflineText_rwxx.setText("已扫描(" + dotMachineData.size() + ")个部件;");
					viewHoler.mOfflineText_hcsj.setText("缓存时间：" + data.getCacheTime());
					break;
				case 6://网点机器部件更换
					List<QRCargoScanBean> dotMachineReplaceData = KingTellerJsonUtils.getPersons(data.getCacheData(), QRCargoScanBean.class);
					
					String [] stringArrdotMachineReplace = data.getCacheOtherData().split(",");   //工单id,工单单号,机器id,机器编号
					
					viewHoler.mOfflineText_dh.setText("部件更换扫描");
					viewHoler.mOfflineText_jqbh_tou.setText("工单单号：");
					viewHoler.mOfflineText_jqbh.setText(stringArrdotMachineReplace[1]);
					viewHoler.mOfflineText_wdjc.setVisibility(View.GONE);
					viewHoler.mOfflineText_rwxx_tou.setText("任务信息：");
					viewHoler.mOfflineText_rwxx.setText("已扫描(" + dotMachineReplaceData.size() + ")个部件;");
					viewHoler.mOfflineText_hcsj.setText("缓存时间：" + data.getCacheTime());
					break;
				case 7://网点发货
					List<QRCargoScanBean> dotDeliverydata = KingTellerJsonUtils.getPersons(data.getCacheData(), QRCargoScanBean.class);
					
					String [] stringArrdotDeliverydata = data.getCacheOtherData().split(",");   	//所属服务站id, 所属服务站名称
					
					viewHoler.mOfflineText_dh.setText("网点发货：" + "(" + stringArrdotDeliverydata[1] + ")");
					viewHoler.mOfflineText_jqbh_tou.setText("机器编号：");
					viewHoler.mOfflineText_jqbh.setText("无" );
					viewHoler.mOfflineText_wdjc.setText("网点设备简称：无");
					viewHoler.mOfflineText_rwxx_tou.setText("任务信息：");
					viewHoler.mOfflineText_rwxx.setText("已扫描(" + dotDeliverydata.size() + ")个部件;");
					viewHoler.mOfflineText_hcsj.setText("缓存时间：" + data.getCacheTime());
					break;
				case 8://离线设备录入
					final List<QRDotMachineBean> dotOfflineMachineData = KingTellerJsonUtils.getPersons(data.getCacheData(), QRDotMachineBean.class);
					
					final String stringArrdotOfflineMachineId = data.getCacheDataId();				//设备录入id
					final String stringArrdotOfflineMachineType = data.getCacheType();				//设备录入类型
					final String stringArrdotOfflineMachineData = data.getCacheData();				//设备录入数据
					
					String [] stringArrdotOfflineMachine = data.getCacheOtherData().split(",");   	//机器id, 机器编号
					
					viewHoler.mOfflineText_dh.setText("设备录入");
					viewHoler.mOfflineText_jqbh_tou.setText("机器编号：");
					
					if("0".equals(stringArrdotOfflineMachine[1])){
						viewHoler.mOfflineText_jqbh.setText("");
					}else{
						viewHoler.mOfflineText_jqbh.setText(stringArrdotOfflineMachine[1]);
					}
					
					viewHoler.mOfflineSelectBtn.setVisibility(View.VISIBLE); 
					viewHoler.mOfflineSelectBtn.setText("请选择机器编号");
					viewHoler.mOfflineSelectBtn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							String machineCode = "";
							for(int i = 0; i<dotOfflineMachineData.size();i++ ){
								if(dotOfflineMachineData.get(i).getWlBarCode().length() < 8){
									machineCode = dotOfflineMachineData.get(i).getWlBarCode();
								}
							}
							
							submitJQBH(machineCode, stringArrdotOfflineMachineId, stringArrdotOfflineMachineType, stringArrdotOfflineMachineData);
						}
					});
	
					viewHoler.mOfflineText_wdjc.setVisibility(View.GONE);
					viewHoler.mOfflineText_rwxx_tou.setText("任务信息：");
					viewHoler.mOfflineText_rwxx.setText("已扫描(" + dotOfflineMachineData.size() + ")个部件;");
					viewHoler.mOfflineText_hcsj.setText("缓存时间：" + data.getCacheTime());

					
					break;
				case 9://离线部件更换
					final List<QRCargoScanBean> dotOfflineMachineReplaceData = KingTellerJsonUtils.getPersons(data.getCacheData(), QRCargoScanBean.class);
					
					final String stringArrdotOfflineMachineReplaceId = data.getCacheDataId();				//离线部件更换id
					final String stringArrdotOfflineMachineReplaceType = data.getCacheType();				//离线部件更换类型
					final String stringArrdotOfflineMachineReplaceData = data.getCacheData();				//离线部件更换数据
					
					String [] stringArrdotOfflineMachineReplace = data.getCacheOtherData().split(",");   	//工单id,工单单号,机器id,机器编号
					
					
					viewHoler.mOfflineText_dh.setText("部件更换扫描");
					viewHoler.mOfflineText_jqbh_tou.setText("工单单号：");
					

					if("0".equals(stringArrdotOfflineMachineReplace[1])){
						viewHoler.mOfflineText_jqbh.setText("");
					}else{
						viewHoler.mOfflineText_jqbh.setText(stringArrdotOfflineMachineReplace[1]);
					}

					viewHoler.mOfflineSelectBtn.setVisibility(View.VISIBLE); 
					viewHoler.mOfflineSelectBtn.setText("请选择工单单号");
					viewHoler.mOfflineSelectBtn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							submitGDDH(stringArrdotOfflineMachineReplaceId, stringArrdotOfflineMachineReplaceType, stringArrdotOfflineMachineReplaceData);
						}
					});
					
					viewHoler.mOfflineText_wdjc.setVisibility(View.GONE);
					viewHoler.mOfflineText_rwxx_tou.setText("任务信息：");
					viewHoler.mOfflineText_rwxx.setText("已扫描(" + dotOfflineMachineReplaceData.size() + ")个部件;");
					viewHoler.mOfflineText_hcsj.setText("缓存时间：" + data.getCacheTime());

					break;
				default:
					break;
			}		
		}else{
			T.showShort(mContext, "数据类型为空!");
		}
		
		return convertView;
	}
	
	// 移除一个项目的时候
	public void remove(int position) {
		this.lists.remove(position);
	}
	
	public HashMap<Integer, Boolean> getCheckMap() {
		return this.isCheckedMap;
	}
	
	
	public void setChangeOfflineDataClickL(ChangeOfflineDataClickL ChangeOfflineDataClickL) {
		this.ChangeOfflineDataClickL = ChangeOfflineDataClickL;
	}
	
	public interface ChangeOfflineDataClickL{
		public void ChangeOfflineDataClick();
	}
	
	public <T> T getDataFromString(String reportData, Class<T> clazz) {
		try {
			Gson gson = new Gson();
			T obj = gson.fromJson(reportData, clazz);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
	
	//离线设备录入    选择  机器编号
	public void submitJQBH(final String machineCode, final String OfflineId, final String OfflineType, final String OfflineData) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("machineCode", machineCode);//整机二维码
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.OfflineCxJqbh),
				params, new AjaxHttpCallBack<List<OfflineSelectDataBean>>(mContext,
						new TypeToken<List<OfflineSelectDataBean>>() {}.getType(), true) {
			
			@Override
			public void onStart() {
				KingTellerProgressUtils.showProgress(mContext, "查询机器编号中...");
			}

			@Override
			public void onFinish() {
				KingTellerProgressUtils.closeProgress();
			}
			
			@Override
			public void onDo(List<OfflineSelectDataBean> data) {
				if("".equals(data.get(0).getCode())){
					
					OpenSelectList(OfflineId, OfflineType, OfflineData, data);
					
				}else{
					T.showShort(mContext, data.get(0).getCode());
				}
			};
		});
	}
	
	
	//离线部件更换    选择	工单单号
	public void submitGDDH(final String OfflineId, final String OfflineType, final String OfflineData) {
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.OfflineCxGddh),
				params, new AjaxHttpCallBack<List<OfflineSelectDataBean>>(mContext,
						new TypeToken<List<OfflineSelectDataBean>>() {}.getType(), true) {
			
			@Override
			public void onStart() {
				KingTellerProgressUtils.showProgress(mContext, "查询工单单号中...");
			}

			@Override
			public void onFinish() {
				KingTellerProgressUtils.closeProgress();
			}
			
			@Override
			public void onDo(List<OfflineSelectDataBean> data) {
				if("".equals(data.get(0).getCode())){
					
					OpenSelectList(OfflineId, OfflineType, OfflineData, data);
					
				}else{
					T.showShort(mContext, data.get(0).getCode());
				}
			};
		});

	}
	 
	public void OpenSelectList(final String OfflineId, final String OfflineType, final String OfflineData,
			final List<OfflineSelectDataBean> data){
		
		String[] StringItem = new String[data.size()];
		String title = "";
		if("dotmachinereplace_offline".equals(OfflineType)){ 
			title = "请选工单单号";
			for(int i = 0; i < data.size(); i++){
				StringItem[i] = data.get(i).getOrderNo();
			}
		}else if ("dotmachine_offline".equals(OfflineType)){
			title = "请选机器编号";
			for(int i = 0; i < data.size(); i++){
				StringItem[i] = data.get(i).getJqbh();
			}
		}
		
	
		
		
		final NormalListDialog dialog_jqbh = new NormalListDialog(mContext, StringItem);
		dialog_jqbh.title(title)//
	                .layoutAnimation(null)
	                .titleBgColor(Color.parseColor("#409ED7"))//
	                .itemPressColor(Color.parseColor("#85D3EF"))//
	                .itemTextColor(Color.parseColor("#303030"))//
	                .show();
		dialog_jqbh.setOnOperItemClickL(new OnOperItemClickL() {
	            @Override
	            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
	            	dialog_jqbh.dismiss();
	            	
	            	String gdid = data.get(position).getOrderId();
	            	String gddh = data.get(position).getOrderNo();
	            	String jqid = data.get(position).getJqid();
	            	String jqbh = data.get(position).getJqbh();
	
	            	
	            	if("dotmachinereplace_offline".equals(OfflineType)){ 
	            		
	            		//保存数据库
	            		KingTellerDbUtils.saveOfflineDotMachineReplaceToDataBase(
	        					mContext, 
	        					OfflineId,
	        					OfflineType,
	        					OfflineData, 
	        					gdid + "," + gddh + "," + jqid + "," + jqbh, //工单id,工单单号,机器id,机器编号
	        					KingTellerTimeUtils.getNowTime(),
	        					0);
	        			
	        		}else if ("dotmachine_offline".equals(OfflineType)){
	        			//保存数据库
	        			KingTellerDbUtils.saveOfflineDotMachineToDataBase(
	        					mContext, 
	        					OfflineId,
	        					OfflineType,
	        					OfflineData, 
	        					jqid + "," + jqbh, //机器id,机器编码
	        					KingTellerTimeUtils.getNowTime(),
	        					0);
	        		}
	            	
        			
        			T.showShort(mContext, "数据更新成功!");
        			ChangeOfflineDataClickL.ChangeOfflineDataClick();
	            }
	        });
	}
	
	public static class ViewHoler {
		public TextView mOfflineText_dh;
		public TextView mOfflineText_jqbh_tou;
		public TextView mOfflineText_jqbh;
		public TextView mOfflineText_wdjc;
		public TextView mOfflineText_rwxx_tou;
		public TextView mOfflineText_rwxx;
		public TextView mOfflineText_hcsj;
		public Button mOfflineSelectBtn;
		public CheckBox mOfflineCheckBox;
	}

}
