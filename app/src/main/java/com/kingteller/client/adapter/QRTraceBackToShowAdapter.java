package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.qrcode.QRBarCodeBean;
import com.kingteller.client.bean.qrcode.QRTraceBackToBean;
import com.kingteller.client.bean.qrcode.QRTraceBackToSjBean;
import com.kingteller.client.bean.qrcode.QRTraceBackToZlBean;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;

public class QRTraceBackToShowAdapter extends BaseAdapter {
	private QRTraceBackToBean data = new QRTraceBackToBean();
	private List<QRTraceBackToSjBean> data_RecordList = new ArrayList<>();
	private List<QRTraceBackToZlBean> data_MaintainList = new ArrayList<>();
	private Context mContext;
	private int isOnOff = 0;
	public QRTraceBackToShowAdapter(Context context, QRTraceBackToBean data){
		this.mContext = context;
		this.data = data;
		this.data_RecordList = data.getRecordList();
		this.data_MaintainList = data.getMaintainList();
	}
	
	public void setLists(QRTraceBackToBean data) {
		this.data = data;
		this.data_RecordList = data.getRecordList();
		this.data_MaintainList = data.getMaintainList();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if(data_RecordList == null){
			return 0;
		}
		return data_RecordList.size() + 1;
		
	}

	@Override
	public QRTraceBackToSjBean getItem(int position) { 
		if(position > 0){
			return data_RecordList.get(position);
		}else{
			return null;
		}
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
			ViewHoler viewHoler = null;
			if (convertView == null) {
				viewHoler = new ViewHoler();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tracebackto_show, null);
	
				//必显示
				viewHoler.jcxx_traceback_necessary = (LinearLayout) convertView.findViewById(R.id.traceback_basic_information_necessary);
				viewHoler.jcxx_traceback_ewm = (TextView) convertView.findViewById(R.id.traceback_text_basic_ewm);		//二维码
				viewHoler.jcxx_traceback_wlmc = (TextView) convertView.findViewById(R.id.traceback_text_basic_wlmc);	//物料名称
				viewHoler.jcxx_traceback_wlbm = (TextView) convertView.findViewById(R.id.traceback_text_basic_wlbm);	//物料编码
				viewHoler.jcxx_traceback_ggxh = (TextView) convertView.findViewById(R.id.traceback_text_basic_ggxh);	//规格型号
				viewHoler.jcxx_traceback_szdz = (TextView) convertView.findViewById(R.id.traceback_text_basic_szdz);	//所在地址
				viewHoler.jcxx_traceback_ssjg = (TextView) convertView.findViewById(R.id.traceback_text_basic_ssjg);	//所属机构
				viewHoler.jcxx_traceback_ssqy = (TextView) convertView.findViewById(R.id.traceback_text_basic_ssqy);	//所属区域
				viewHoler.jcxx_traceback_ssdq = (TextView) convertView.findViewById(R.id.traceback_text_basic_ssdq);	//所属大区
				
				//需要展示
				viewHoler.jcxx_traceback_unnecessary = (LinearLayout) convertView.findViewById(R.id.traceback_basic_information_unnecessary);
				
				viewHoler.jcxx_traceback_jqbm = (TextView) convertView.findViewById(R.id.traceback_text_basic_jqbm);	//机器编码
				viewHoler.jcxx_traceback_syzt = (TextView) convertView.findViewById(R.id.traceback_text_basic_syzt);	//使用状态
				viewHoler.jcxx_traceback_wbs = (TextView) convertView.findViewById(R.id.traceback_text_basic_wbs);		//维保商
				viewHoler.jcxx_traceback_scdh = (TextView) convertView.findViewById(R.id.traceback_text_basic_scdh);	//生产单号
				viewHoler.jcxx_traceback_rkrq = (TextView) convertView.findViewById(R.id.traceback_text_basic_rkrq);	//生产日期
				viewHoler.jcxx_traceback_bbh = (TextView) convertView.findViewById(R.id.traceback_text_basic_bbh);		//版本号
				viewHoler.jcxx_traceback_gysdm = (TextView) convertView.findViewById(R.id.traceback_text_basic_gysdm);	//供应商代码
				viewHoler.jcxx_traceback_gysmc = (TextView) convertView.findViewById(R.id.traceback_text_basic_gysmc);	//供应商名称
				viewHoler.jcxx_traceback_cjbmh = (TextView) convertView.findViewById(R.id.traceback_text_basic_cjbmh);	//厂家编码号
				viewHoler.jcxx_traceback_cjlsm = (TextView) convertView.findViewById(R.id.traceback_text_basic_cjlsm);	//厂家流水码
				viewHoler.jcxx_traceback_wbqx = (TextView) convertView.findViewById(R.id.traceback_text_basic_wbqx);	//维保期限
				viewHoler.jcxx_traceback_fhrq = (TextView) convertView.findViewById(R.id.traceback_text_basic_fhrq);	//发货日期
				viewHoler.jcxx_traceback_synx = (TextView) convertView.findViewById(R.id.traceback_text_basic_synx);	//使用年限
				viewHoler.jcxx_traceback_gzcs = (TextView) convertView.findViewById(R.id.traceback_text_basic_gzcs);	//故障次数
				
				
				
				//显示更多btn
				viewHoler.jcxx_traceback_onclick = (LinearLayout) convertView.findViewById(R.id.traceback_basic_information_onclick);
				viewHoler.jcxx_traceback_onclick_text = (TextView) convertView.findViewById(R.id.traceback_basic_information_onclick_text);	//显示更多
				
				//质量信息
				viewHoler.jcxx_traceback_zlxx = (LinearLayout) convertView.findViewById(R.id.traceback_basic_information_zlxx);
				
				viewHoler.czsj_traceback_img_day = (ImageView) convertView.findViewById(R.id.traceback_img_day);
				
//				viewHoler.czsj_traceback_year_view = (RelativeLayout) convertView.findViewById(R.id.traceback_yearview);
//				viewHoler.czsj_traceback_year = (TextView) convertView.findViewById(R.id.traceback_year); 					//创建时间-年
//				viewHoler.czsj_traceback_time = (TextView) convertView.findViewById(R.id.traceback_time);					//创建时间-月日
				
				viewHoler.czsj_traceback_sjlx = (TextView) convertView.findViewById(R.id.traceback_operation_text_sjlx);	//事件类型
				viewHoler.czsj_traceback_ywms = (TextView) convertView.findViewById(R.id.traceback_operation_text_ywms);	//业务描述
				viewHoler.czsj_traceback_bzxx = (TextView) convertView.findViewById(R.id.traceback_operation_text_bzxx);	//备注
				
				
	
				convertView.setTag(viewHoler);
			} else {
				viewHoler = (ViewHoler) convertView.getTag();
			}
		
			
			/**==========================================================================**/
			if(position == 0){//基础信息
				convertView.findViewById(R.id.traceback_basic_information).setVisibility(View.VISIBLE);
				convertView.findViewById(R.id.traceback_basic_zlxx).setVisibility(View.VISIBLE);
				convertView.findViewById(R.id.traceback_operation_event).setVisibility(View.GONE);
				
				viewHoler.jcxx_traceback_ewm.setText(data.getBarcode());
				viewHoler.jcxx_traceback_wlmc.setText(data.getWlName());
				viewHoler.jcxx_traceback_wlbm.setText(data.getNewCode());
				viewHoler.jcxx_traceback_ggxh.setText(data.getModelNo());
				viewHoler.jcxx_traceback_szdz.setText(data.getLocationName());
				viewHoler.jcxx_traceback_ssjg.setText(data.getOrgName());
				viewHoler.jcxx_traceback_ssqy.setText(data.getAreaName());
				viewHoler.jcxx_traceback_ssdq.setText(data.getBigAreaName());
				
				
				viewHoler.jcxx_traceback_jqbm.setText(data.getMachineCode());
				viewHoler.jcxx_traceback_wbs.setText(data.getMaintenanceName());
				viewHoler.jcxx_traceback_scdh.setText(data.getProduceNum());
				viewHoler.jcxx_traceback_rkrq.setText(data.getStockDateStr());
				viewHoler.jcxx_traceback_bbh.setText(data.getVersion());
				viewHoler.jcxx_traceback_gysdm.setText(data.getSupplierCode());
				viewHoler.jcxx_traceback_gysmc.setText(data.getSupplierName());
				viewHoler.jcxx_traceback_cjbmh.setText(data.getSupplierBarcode());
				viewHoler.jcxx_traceback_cjlsm.setText(data.getSerialNumber());
				viewHoler.jcxx_traceback_wbqx.setText(data.getMaintenancePeriod());
				viewHoler.jcxx_traceback_fhrq.setText(data.getDeliveryDateStr());
				viewHoler.jcxx_traceback_synx.setText(data.getServiceLifePeriod());
				viewHoler.jcxx_traceback_gzcs.setText(data.getFaultTime());
				
				if("可用".equals(data.getStatusName())){ 
					viewHoler.jcxx_traceback_syzt.setText("正在使用");
				}else{
					viewHoler.jcxx_traceback_syzt.setText("已被禁用");
				}
 
				viewHoler.jcxx_traceback_zlxx.removeAllViews();
				for(int i = 0; i <data_MaintainList.size() + 1; i++){
					LinearLayout zl_item = new LinearLayout(mContext);
					zl_item.setOrientation(LinearLayout.HORIZONTAL);
					zl_item.setBackgroundResource(R.drawable.btn_layout_off_bg);
					zl_item.setPadding(5, 5, 5, 5);
					
					TextView tv_item_1 = new TextView(mContext);
	        		tv_item_1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
	 	                    LinearLayout.LayoutParams.WRAP_CONTENT, 3));
	        		tv_item_1.setGravity(Gravity.CENTER);
	        		tv_item_1.setTextSize(16);
	        		
	        		TextView tv_item_2 = new TextView(mContext);
	        		tv_item_2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
	 	                    LinearLayout.LayoutParams.WRAP_CONTENT, 2));
	        		tv_item_2.setGravity(Gravity.CENTER);
	        		tv_item_2.setTextSize(16);
	        		
	        		TextView tv_item_3 = new TextView(mContext);
	        		tv_item_3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
	 	                    LinearLayout.LayoutParams.WRAP_CONTENT, 3));
	        		tv_item_3.setGravity(Gravity.CENTER);
	        		tv_item_3.setTextSize(16);
	        		
	            	if(i == 0){
	            		tv_item_1.setText("序号");
	            		tv_item_2.setText("维修/自修时间");
	            		tv_item_3.setText("是否在保");
	            	}else{
	            		tv_item_1.setText(data_MaintainList.get(i - 1).getSerialnumber());
	            		tv_item_2.setText(data_MaintainList.get(i - 1).getMaintainDateStr());
	            		tv_item_3.setText(data_MaintainList.get(i - 1).getIsOverdue());
	            	}
	            	zl_item.addView(tv_item_1);
	            	zl_item.addView(tv_item_2);
	            	zl_item.addView(tv_item_3);
	            	viewHoler.jcxx_traceback_zlxx.addView(zl_item); 
	            }
	            
			
			}else{//操作事件

				convertView.findViewById(R.id.traceback_basic_information).setVisibility(View.GONE);
				convertView.findViewById(R.id.traceback_basic_zlxx).setVisibility(View.GONE);
				convertView.findViewById(R.id.traceback_operation_event).setVisibility(View.VISIBLE);
				
				if(position  == 1){
					viewHoler.czsj_traceback_img_day.setBackgroundResource(R.drawable.ic_time_axis_day_pressed);
				}else{
					viewHoler.czsj_traceback_img_day.setBackgroundResource(R.drawable.ic_time_axis_day_nomal);
				}
				
//				int selection = getSelectionByPosition(position-1);//获取当前年份
//				int index = getPositionBySelection(selection);//获取这年份的首项
//				if((position - 1) == index){
//					viewHoler.czsj_traceback_year_view.setVisibility(View.VISIBLE);
//					viewHoler.czsj_traceback_year.setText(selection + "");
//				}else{
//					viewHoler.czsj_traceback_year_view.setVisibility(View.GONE);
//				}
//				 
//				if(position == 1){
//					viewHoler.czsj_traceback_img_day.setBackgroundResource(R.drawable.ic_time_axis_day_pressed);
//				}else{
//					viewHoler.czsj_traceback_img_day.setBackgroundResource(R.drawable.ic_time_axis_day_nomal);
//				}
//				
//				String aprilday = TimeFormatUtils.getYearMonthDay(data_RecordList.get(position-1).getCreateDateStr(), 1);
//				String apriltime = TimeFormatUtils.getYearMonthDay(data_RecordList.get(position-1).getCreateDateStr(), 2);
//
//				viewHoler.czsj_traceback_time.setText(Html.fromHtml(aprilday + "<br/>" + apriltime));
				
				viewHoler.czsj_traceback_sjlx.setText("[" + data_RecordList.get(position-1).getBillTypeName() + "]");
				viewHoler.czsj_traceback_ywms.setText(data_RecordList.get(position-1).getDescription());
				
				if(!KingTellerJudgeUtils.isEmpty(data_RecordList.get(position-1).getRemark())){
					viewHoler.czsj_traceback_bzxx.setVisibility(View.GONE);
					viewHoler.czsj_traceback_bzxx.setText(Html.fromHtml("<font color=red>备注：</font>"  + data_RecordList.get(position-1).getRemark()));
				}else{
					viewHoler.czsj_traceback_bzxx.setVisibility(View.GONE);
				}
				
			}
			
			if(isOnOff == 0){
				viewHoler.jcxx_traceback_unnecessary.setVisibility(View.GONE);
				viewHoler.jcxx_traceback_onclick_text.setText("显示更多");
			}else{
				viewHoler.jcxx_traceback_unnecessary.setVisibility(View.VISIBLE);
				viewHoler.jcxx_traceback_onclick_text.setText("收起");
			}
			
			if(data.getIsWholeMachineName().equals("否")){
				convertView.findViewById(R.id.traceback_layout_basic_jqbm).setVisibility(View.GONE);
			}else{
				convertView.findViewById(R.id.traceback_layout_basic_jqbm).setVisibility(View.VISIBLE);
			}
			
			viewHoler.jcxx_traceback_onclick.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(isOnOff == 0){
						isOnOff = 1;
					}else{
						isOnOff = 0;
					}
					notifyDataSetChanged();
				}
			});
			
			
			
			return convertView;	
	}
	
	//获取当前年份
	public int getSelectionByPosition(int position){
		String time = data_RecordList.get(position).getCreateDateStr();
		int year = Integer.parseInt(KingTellerTimeUtils.getYearMonthDay(time, 0, 0));
		return year;
	}
	
	/**
	 * 通过年份获取显示该首年份的信息
	 * @author Xubin
	 *
	 */
	public int getPositionBySelection(int selection){
		for (int i = 0; i < data_RecordList.size(); i++) {
			String sortStr = data_RecordList.get(i).getCreateDateStr();
			int firstChar= Integer.parseInt(KingTellerTimeUtils.getYearMonthDay(sortStr, 0, 0));
			if (firstChar == selection) {
				return i;
			}
		}
		return -1;
	}
	
	
	private class ViewHoler {
		//基础信息
		public LinearLayout jcxx_traceback_necessary;
		public TextView jcxx_traceback_ewm;
		public TextView jcxx_traceback_wlmc;
		public TextView jcxx_traceback_wlbm;
		public TextView jcxx_traceback_ggxh;
		public TextView jcxx_traceback_szdz;
		public TextView jcxx_traceback_ssjg;
		public TextView jcxx_traceback_ssqy;
		public TextView jcxx_traceback_ssdq;
		
		public LinearLayout jcxx_traceback_unnecessary;
		public TextView jcxx_traceback_jqbm;
		public TextView jcxx_traceback_wbs;
		public TextView jcxx_traceback_scdh;
		public TextView jcxx_traceback_rkrq;
		public TextView jcxx_traceback_bbh;
		public TextView jcxx_traceback_gysmc;
		public TextView jcxx_traceback_gysdm;
		public TextView jcxx_traceback_cjbmh;
		public TextView jcxx_traceback_cjlsm;
		public TextView jcxx_traceback_wbqx;
		public TextView jcxx_traceback_fhrq;
		public TextView jcxx_traceback_synx;
		public TextView jcxx_traceback_syzt;
		public TextView jcxx_traceback_gzcs;
		
		
		public LinearLayout jcxx_traceback_onclick;
		public TextView jcxx_traceback_onclick_text;
		
		public LinearLayout jcxx_traceback_zlxx;
		
		//操作事件
		public RelativeLayout czsj_traceback_year_view;
		public ImageView czsj_traceback_img_day;
		public TextView czsj_traceback_year;
		public TextView czsj_traceback_time;
		
	
		public TextView czsj_traceback_sjlx;
		public TextView czsj_traceback_ywms;
		public TextView czsj_traceback_bzxx;
	
		

	}

}
