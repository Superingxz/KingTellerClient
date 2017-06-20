package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.offlineupload.OfflineDotMachineBean;
import com.kingteller.client.bean.offlineupload.OfflineDotMachineReplaceBean;
import com.kingteller.client.bean.qrcode.QRDeliveryBean;
import com.kingteller.client.bean.qrcode.QRDotMachineBean;
import com.kingteller.client.bean.qrcode.QRGuaranteeBean;
import com.kingteller.client.bean.qrcode.QRReceiptBean;
import com.kingteller.client.bean.qrcode.QRServiceCheckBean;
import com.kingteller.client.utils.KingTellerJudgeUtils;

public class QRDeliveryReceiptAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private Context mContext;
	private List<QRDeliveryBean> lists_delivery = new ArrayList<QRDeliveryBean>();
	private List<QRReceiptBean> lists_receipt = new ArrayList<QRReceiptBean>();
	private List<QRGuaranteeBean> lists_guarantee = new ArrayList<QRGuaranteeBean>();
	private List<QRServiceCheckBean> lists_service = new ArrayList<QRServiceCheckBean>();
	private List<OfflineDotMachineBean> lists_dotmachine_offline = new ArrayList<OfflineDotMachineBean>();
	private List<OfflineDotMachineReplaceBean> lists_dotmachine_replace_offline = new ArrayList<OfflineDotMachineReplaceBean>();
	private int Type;//1发货  2收货 3报修  4服务站物料出入库登记  5离线设备录入

	public QRDeliveryReceiptAdapter(Context context, List<QRDeliveryBean> listsdelivery, 
			List<QRReceiptBean> listsreceipt, List<QRGuaranteeBean> listsguarantee,
			List<QRServiceCheckBean> listsservice, List<OfflineDotMachineBean> listsdotmachine_offline,
			List<OfflineDotMachineReplaceBean> listsdotmachinereplace_offline,int type) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.lists_delivery = listsdelivery;
		this.lists_receipt = listsreceipt;
		this.lists_guarantee = listsguarantee;
		this.lists_service = listsservice;
		this.lists_dotmachine_offline = listsdotmachine_offline;
		this.lists_dotmachine_replace_offline = listsdotmachinereplace_offline;
		this.Type = type;
	}
	
	public void setListsdotmachinereplace_offline(List<OfflineDotMachineReplaceBean> listsdotmachinereplace_offline) {
		this.lists_dotmachine_replace_offline = listsdotmachinereplace_offline;
		notifyDataSetChanged();
	}
	public void setListsdotmachine_offline(List<OfflineDotMachineBean> listsdotmachine_offline) {
		this.lists_dotmachine_offline = listsdotmachine_offline;
		notifyDataSetChanged();
	}
	public void setListsservice(List<QRServiceCheckBean> listsservice) {
		this.lists_service = listsservice;
		notifyDataSetChanged();
	}
	public void setListsguarantee(List<QRGuaranteeBean> listsguarantee) {
		this.lists_guarantee = listsguarantee;
		notifyDataSetChanged();
	}
	public void setListsdelivery(List<QRDeliveryBean> listsdelivery) {
		this.lists_delivery = listsdelivery;
		notifyDataSetChanged();
	}
	public void setListsreceipt(List<QRReceiptBean> listsreceipt) {
		this.lists_receipt = listsreceipt;
		notifyDataSetChanged();
	}
	
	public void addListsdotmachinereplace_offline(List<OfflineDotMachineReplaceBean> listsdotmachinereplace_offline) {
		this.lists_dotmachine_replace_offline.addAll(listsdotmachinereplace_offline);
		notifyDataSetChanged();
	}
	public void addListsdotmachine_offline(List<OfflineDotMachineBean> listsdotmachine_offline) {
		this.lists_dotmachine_offline.addAll(listsdotmachine_offline);
		notifyDataSetChanged();
	}
	public void addListsservice(List<QRServiceCheckBean> listsservice) {
		this.lists_service.addAll(listsservice);
		notifyDataSetChanged();
	}
	public void addListsguarantee(List<QRGuaranteeBean> listsguarantee) {
		this.lists_guarantee.addAll(listsguarantee);
		notifyDataSetChanged();
	}
	public void addListsdelivery(List<QRDeliveryBean> listsdelivery) {
		this.lists_delivery.addAll(listsdelivery);
		notifyDataSetChanged();
	}
	public void addListsreceipt(List<QRReceiptBean> listsreceipt) {
		this.lists_receipt.addAll(listsreceipt);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int size = 0;
		if(Type == 1){
			if (lists_delivery == null){
				return size;
			}else{
				size = lists_delivery.size();
			}
		}else if(Type == 2){
			if (lists_receipt == null){
				return size;
			}else{
				size = lists_receipt.size();
			}
		}else if(Type == 3){
			if (lists_guarantee == null){
				return size;
			}else{
				size = lists_guarantee.size();
			}
		}else if(Type == 4){
			if (lists_service == null){
				return size;
			}else{
				size = lists_service.size();
			}
		}else if(Type == 5){
			if (lists_dotmachine_offline == null){
				return size;
			}else{
				size = lists_dotmachine_offline.size();
			}
		}else if(Type == 6){
			if (lists_dotmachine_replace_offline == null){
				return size;
			}else{
				size = lists_dotmachine_replace_offline.size();
			}
		}
		return size;
	}

	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		if(Type == 1){
			return lists_delivery.get(location);
		}else if(Type == 2){
			return lists_receipt.get(location);
		}else if(Type == 3){
			return lists_guarantee.get(location);
		}else if(Type == 4){
			return lists_service.get(location);
		}else if(Type == 5){
			return lists_dotmachine_offline.get(location);
		}else if(Type == 6){
			return lists_dotmachine_replace_offline.get(location);
		}else{
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(Type == 1){
			QRDeliveryBean data_delivery = (QRDeliveryBean) lists_delivery.get(position);
			ViewHolerDelivery viewHolerDelivery = null;
			if (convertView == null) {
				viewHolerDelivery = new ViewHolerDelivery();
				convertView = inflater.inflate(R.layout.item_deliveryreceipt_qrcode, null);
				viewHolerDelivery.mDeliveryoddnumbers = (TextView) convertView.findViewById(R.id.fh_deliveryoddnumbers);
				viewHolerDelivery.mDeliverywarehouse = (TextView) convertView.findViewById(R.id.fh_deliverywarehouse);
				viewHolerDelivery.mReceivingwarehouse = (TextView) convertView.findViewById(R.id.fh_receivingwarehouse);
				viewHolerDelivery.mProcessstate = (TextView) convertView.findViewById(R.id.fh_processstate);
				viewHolerDelivery.mSinglename = (TextView) convertView.findViewById(R.id.fh_singlename);
				viewHolerDelivery.mDocumenttype = (TextView) convertView.findViewById(R.id.fh_documenttype);
				viewHolerDelivery.mDocumentwlzs = (TextView) convertView.findViewById(R.id.fh_lb_wlzs);
				viewHolerDelivery.mDocumentytms = (TextView) convertView.findViewById(R.id.fh_lb_ytms);
				viewHolerDelivery.mDocumentmtms = (TextView) convertView.findViewById(R.id.fh_lb_mtms);
				viewHolerDelivery.mSingletime = (TextView) convertView.findViewById(R.id.fh_singletime);
				convertView.setTag(viewHolerDelivery);
			} else {
				viewHolerDelivery = (ViewHolerDelivery) convertView.getTag();
			}
			
			convertView.findViewById(R.id.goodrs_delivery).setVisibility(View.VISIBLE);
			convertView.findViewById(R.id.goodrs_receipt).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_guaranteen).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_servicestationinout).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_dotmachine_offline).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_dotmachine_replace_offline).setVisibility(View.GONE);
			
			viewHolerDelivery.mDeliveryoddnumbers.setText(data_delivery.getFbillNo());
			viewHolerDelivery.mDeliverywarehouse.setText(data_delivery.getFdcstockName());
			viewHolerDelivery.mReceivingwarehouse.setText(data_delivery.getFdeptName());
			viewHolerDelivery.mProcessstate.setText(data_delivery.getFlowStatusName());
			viewHolerDelivery.mSinglename.setText(data_delivery.getFbillerName());
			viewHolerDelivery.mDocumenttype.setText(data_delivery.getFbillCaptionName());
			viewHolerDelivery.mDocumentwlzs.setText("物料总数：" + data_delivery.getWlTotal());
			viewHolerDelivery.mDocumentytms.setText("有条码数：" + data_delivery.getBarcodeCount());
			viewHolerDelivery.mDocumentmtms.setText("没条码数：" + data_delivery.getNullBarcodeCount());
			viewHolerDelivery.mSingletime.setText(data_delivery.getCreateDateStr());
			
		}else if(Type == 2){
			QRReceiptBean data_receipt = (QRReceiptBean) lists_receipt.get(position);
			ViewHolerReceipt viewHolerReceipt = null;
			if (convertView == null) {
				viewHolerReceipt = new ViewHolerReceipt();
				convertView = inflater.inflate(R.layout.item_deliveryreceipt_qrcode, null);
				viewHolerReceipt.mReceiptoddnumbers = (TextView) convertView.findViewById(R.id.sh_receiptoddnumbers);
				viewHolerReceipt.mDeliverywarehouse = (TextView) convertView.findViewById(R.id.sh_deliverywarehouse);
				viewHolerReceipt.mReceivingwarehouse = (TextView) convertView.findViewById(R.id.sh_receivingwarehouse);
				viewHolerReceipt.mDelivergoodsname = (TextView) convertView.findViewById(R.id.sh_relivergoodsname);
				viewHolerReceipt.mDelivergoodswlzs= (TextView) convertView.findViewById(R.id.sh_wlzs);
				viewHolerReceipt.mDelivergoodswytmzs = (TextView) convertView.findViewById(R.id.sh_wyztms);
				viewHolerReceipt.mDelivergoodstmzs = (TextView) convertView.findViewById(R.id.sh_tmzs);
				viewHolerReceipt.mSingletime = (TextView) convertView.findViewById(R.id.sh_singletime);
				convertView.setTag(viewHolerReceipt);
			} else {
				viewHolerReceipt = (ViewHolerReceipt) convertView.getTag();
			}
			
			convertView.findViewById(R.id.goodrs_delivery).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_receipt).setVisibility(View.VISIBLE);
			convertView.findViewById(R.id.goodrs_guaranteen).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_servicestationinout).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_dotmachine_offline).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_dotmachine_replace_offline).setVisibility(View.GONE);
			
			viewHolerReceipt.mReceiptoddnumbers.setText(data_receipt.getBillNo());
			viewHolerReceipt.mDeliverywarehouse.setText(data_receipt.getFdcstockName()); 
			viewHolerReceipt.mReceivingwarehouse.setText(data_receipt.getFdeptName());
			viewHolerReceipt.mDelivergoodsname.setText(data_receipt.getFsmanagerName());
			viewHolerReceipt.mDelivergoodswlzs.setText("物料总数：" + data_receipt.getWlTotal());
			viewHolerReceipt.mDelivergoodstmzs.setText("有条码数：" + data_receipt.getBarcodeCount());
			viewHolerReceipt.mDelivergoodswytmzs.setText("未扫描数：" + data_receipt.getUnscanBarcodeCount());
			
			viewHolerReceipt.mSingletime.setText(data_receipt.getFdateStr());

		}else if(Type == 3){
			QRGuaranteeBean data_guaranteen = (QRGuaranteeBean) lists_guarantee.get(position);
			ViewHolerGuaranteen viewHolerGuaranteen = null;
			if (convertView == null) {
				viewHolerGuaranteen = new ViewHolerGuaranteen();
				convertView = inflater.inflate(R.layout.item_deliveryreceipt_qrcode, null);
				viewHolerGuaranteen.mGuaranteenddnumbers = (TextView) convertView.findViewById(R.id.bx_guaranteennumber);
				viewHolerGuaranteen.mGuaranteenwarehouse = (TextView) convertView.findViewById(R.id.bx_guaranteenwarehouse);
				viewHolerGuaranteen.mGuaranteengoodsname = (TextView) convertView.findViewById(R.id.bx_guaranteengoodsname);
				viewHolerGuaranteen.mGuaranteendzt = (TextView) convertView.findViewById(R.id.bx_guaranteendzt);
				viewHolerGuaranteen.mSingletime = (TextView) convertView.findViewById(R.id.bx_singletime);
				convertView.setTag(viewHolerGuaranteen);
			} else {
				viewHolerGuaranteen = (ViewHolerGuaranteen) convertView.getTag();
			}
			
			convertView.findViewById(R.id.goodrs_delivery).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_receipt).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_guaranteen).setVisibility(View.VISIBLE);
			convertView.findViewById(R.id.goodrs_servicestationinout).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_dotmachine_offline).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_dotmachine_replace_offline).setVisibility(View.GONE);
			
			viewHolerGuaranteen.mGuaranteenddnumbers.setText(data_guaranteen.getRepairReceiptNo());
			viewHolerGuaranteen.mGuaranteenwarehouse.setText(data_guaranteen.getFillinWrName());
			viewHolerGuaranteen.mGuaranteengoodsname.setText(data_guaranteen.getFillinPeopleName());
			viewHolerGuaranteen.mSingletime.setText(data_guaranteen.getFillinDateStr());
			
			if("已扫描".equals(data_guaranteen.getScanned())){
				viewHolerGuaranteen.mGuaranteendzt.setText("已扫描");
				viewHolerGuaranteen.mGuaranteendzt.setTextColor(Color.parseColor("#008B00"));  
			}else{
				viewHolerGuaranteen.mGuaranteendzt.setText("未扫描");
				viewHolerGuaranteen.mGuaranteendzt.setTextColor(Color.parseColor("#FF0000"));  
			}
			
		}else if(Type == 4){
			QRServiceCheckBean data_service = (QRServiceCheckBean) lists_service.get(position);
			ViewHolerService viewHolerService = null;
			if (convertView == null) {
				viewHolerService = new ViewHolerService();
				convertView = inflater.inflate(R.layout.item_deliveryreceipt_qrcode, null);
				viewHolerService.mServiceddnumbers = (TextView) convertView.findViewById(R.id.dj_servicenumber);
				viewHolerService.mServicewarehouse = (TextView) convertView.findViewById(R.id.dj_servicehouse);
				viewHolerService.mServicegoodsname = (TextView) convertView.findViewById(R.id.dj_servicename);
				viewHolerService.mServiceLcZt = (TextView) convertView.findViewById(R.id.dj_servicelczt);
				viewHolerService.mServiceCkxz = (TextView) convertView.findViewById(R.id.dj_serviceckxz);
				viewHolerService.mServiceRkxz = (TextView) convertView.findViewById(R.id.dj_servicerkxz);
				viewHolerService.mSingletime = (TextView) convertView.findViewById(R.id.dj_singletime);
				viewHolerService.mServiceCkZs = (TextView) convertView.findViewById(R.id.dj_serviceckzs);
				viewHolerService.mServiceCkTmZs = (TextView) convertView.findViewById(R.id.dj_servicecktmzs);
				viewHolerService.mServiceCkWtms = (TextView) convertView.findViewById(R.id.dj_serviceckwtms);
				
				viewHolerService.mServiceRkZs = (TextView) convertView.findViewById(R.id.dj_servicerkzs);
				viewHolerService.mServiceRkTmZs = (TextView) convertView.findViewById(R.id.dj_servicerktmzs);
				viewHolerService.mServiceRkWtms = (TextView) convertView.findViewById(R.id.dj_servicerkwtms);
				
				convertView.setTag(viewHolerService);
			} else {
				viewHolerService = (ViewHolerService) convertView.getTag();
			}
			
			convertView.findViewById(R.id.goodrs_delivery).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_receipt).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_guaranteen).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_servicestationinout).setVisibility(View.VISIBLE);
			convertView.findViewById(R.id.goodrs_dotmachine_offline).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_dotmachine_replace_offline).setVisibility(View.GONE);
			
			viewHolerService.mServiceddnumbers.setText(data_service.getRegFbillNo());
			viewHolerService.mServicewarehouse.setText(data_service.getWrName());
			viewHolerService.mServicegoodsname.setText(data_service.getFbillerName());
			viewHolerService.mServiceLcZt.setText(data_service.getAuditFlowStatusName());
			
			
			if(KingTellerJudgeUtils.isEmpty(data_service.getRegisterPropertyName())){
				viewHolerService.mServiceCkxz.setText("");
				viewHolerService.mServiceRkxz.setText("");
			}else{
				String [] Crkxz = data_service.getRegisterPropertyName().split(","); //Crkxz[0] 出库性质 Crkxz[1]入库性质
				viewHolerService.mServiceCkxz.setText(Crkxz[0]);
				viewHolerService.mServiceRkxz.setText(Crkxz[1]);
			}
			
			viewHolerService.mServiceCkZs.setText(data_service.getTotalOutQuantity());
			viewHolerService.mServiceCkTmZs.setText(data_service.getTotalOutBarcodeQuantity());
			viewHolerService.mServiceCkWtms.setText(data_service.getTotalOutNullBarcodeQuantity());
			
			viewHolerService.mServiceRkZs.setText(data_service.getTotalInQuantity());
			viewHolerService.mServiceRkTmZs.setText(data_service.getTotalInBarcodeQuantity());
			viewHolerService.mServiceRkWtms.setText(data_service.getTotalInNullBarcodeQuantity());
			viewHolerService.mSingletime.setText(data_service.getRegDate());

		}else if(Type == 5){
			final OfflineDotMachineBean data_dotmachine_offline = (OfflineDotMachineBean) lists_dotmachine_offline.get(position);
			
			ViewHolerDotmachineOffline ViewHolerDotmachineOffline = null;
			
			if (convertView == null) {
				ViewHolerDotmachineOffline = new ViewHolerDotmachineOffline();
				convertView = inflater.inflate(R.layout.item_deliveryreceipt_qrcode, null);
				ViewHolerDotmachineOffline.mDotmachineOfflineID = (TextView) convertView.findViewById(R.id.offline_jqlr_id);
				ViewHolerDotmachineOffline.mDotmachineOfflineZJEWM = (TextView) convertView.findViewById(R.id.offline_jqlr_zjewm);
				ViewHolerDotmachineOffline.mDotmachineOfflineJQBH = (TextView) convertView.findViewById(R.id.offline_jqlr_jqbh);
				ViewHolerDotmachineOffline.mDotmachineOfflineRWXX = (TextView) convertView.findViewById(R.id.offline_jqlr_rwxx);
				ViewHolerDotmachineOffline.mDotmachineOfflineHCSJ = (TextView) convertView.findViewById(R.id.offline_jqlr_hcsj);
	
				convertView.setTag(ViewHolerDotmachineOffline);
			} else {
				ViewHolerDotmachineOffline = (ViewHolerDotmachineOffline) convertView.getTag();
			}
			
			convertView.findViewById(R.id.goodrs_delivery).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_receipt).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_guaranteen).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_servicestationinout).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_dotmachine_offline).setVisibility(View.VISIBLE);
			convertView.findViewById(R.id.goodrs_dotmachine_replace_offline).setVisibility(View.GONE);
			
			
			ViewHolerDotmachineOffline.mDotmachineOfflineID.setText("(请到离线任务中选择机器)");
			
			List<QRDotMachineBean> list = data_dotmachine_offline.getEwmList();
			for(int i = 0; i<list.size(); i++ ){
				if(list.get(i).getWlBarCode().length() < 8){
					ViewHolerDotmachineOffline.mDotmachineOfflineZJEWM.setText(list.get(i).getWlBarCode());
				}
			}
			
			ViewHolerDotmachineOffline.mDotmachineOfflineJQBH.setText(data_dotmachine_offline.getEwmList().get(0).getJqBm());
			
			ViewHolerDotmachineOffline.mDotmachineOfflineRWXX.setText("已扫描("+ 
											data_dotmachine_offline.getEwmList().size()+ ")个部件");
			
			ViewHolerDotmachineOffline.mDotmachineOfflineHCSJ.setText(data_dotmachine_offline.getCacheTime());

		}else if(Type == 6){
			final OfflineDotMachineReplaceBean data_dotmachine_replace_offline = (OfflineDotMachineReplaceBean) lists_dotmachine_replace_offline.get(position);
			
			ViewHolerDotmachineReplaceOffline ViewHolerDotmachineReplaceOffline = null;
			
			if (convertView == null) {
				ViewHolerDotmachineReplaceOffline = new ViewHolerDotmachineReplaceOffline();
				convertView = inflater.inflate(R.layout.item_deliveryreceipt_qrcode, null);
				ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineID = (TextView) convertView.findViewById(R.id.offline_bjghsm_id);
				ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineGDDH = (TextView) convertView.findViewById(R.id.offline_bjghsm_gddh);
				ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineJQBH = (TextView) convertView.findViewById(R.id.offline_bjghsm_jqbh);
				ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineRWXX= (TextView) convertView.findViewById(R.id.offline_bjghsm_rwxx);
				ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineHCSJ= (TextView) convertView.findViewById(R.id.offline_bjghsm_hcsj);
	
				convertView.setTag(ViewHolerDotmachineReplaceOffline);
			} else {
				ViewHolerDotmachineReplaceOffline = (ViewHolerDotmachineReplaceOffline) convertView.getTag();
			}
			
			convertView.findViewById(R.id.goodrs_delivery).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_receipt).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_guaranteen).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_servicestationinout).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_dotmachine_offline).setVisibility(View.GONE);
			convertView.findViewById(R.id.goodrs_dotmachine_replace_offline).setVisibility(View.VISIBLE);
			
			
			
			String [] otherdata = data_dotmachine_replace_offline.getCacheOtherData().split(",");//工单id,工单单号,机器id,机器编号

			if("0".equals(otherdata[1])){
				ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineID.setText("(请到离线任务中选择工单)");
				ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineGDDH.setText("");
			}else{
				ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineID.setText("");
				ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineGDDH.setText(otherdata[1]);
			}
			
			convertView.findViewById(R.id.RelativeLayout29).setVisibility(View.GONE);
			
			ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineRWXX.setText("已扫描("+ 
					data_dotmachine_replace_offline.getEwmList().size()+ ")个部件");
			
			ViewHolerDotmachineReplaceOffline.mDotmachineReplaceOfflineHCSJ.setText(data_dotmachine_replace_offline.getCacheTime());
		}
		
		return convertView;
	}

	private static class ViewHolerDelivery {
		public TextView mDeliveryoddnumbers;
		public TextView mDeliverywarehouse;
		public TextView mReceivingwarehouse;
		public TextView mProcessstate;
		public TextView mSinglename;
		public TextView mDocumenttype;
		public TextView mDocumentwlzs;
		public TextView mDocumentytms;
		public TextView mDocumentmtms;
		public TextView mSingletime;
	}
	
	private static class ViewHolerReceipt {
		public TextView mReceiptoddnumbers;
		public TextView mDeliverywarehouse;
		public TextView mReceivingwarehouse;
		public TextView mDelivergoodsname;
		public TextView mDelivergoodswlzs;
		public TextView mDelivergoodstmzs;
		public TextView mDelivergoodswytmzs;
		public TextView mSingletime;
	}
	
	private static class ViewHolerGuaranteen {
		public TextView mGuaranteenddnumbers;
		public TextView mGuaranteenwarehouse;
		public TextView mGuaranteengoodsname;
		public TextView mGuaranteendzt;
		public TextView mSingletime;
	}
	
	private static class ViewHolerService {
		public TextView mServiceddnumbers;
		public TextView mServicewarehouse;
		public TextView mServicegoodsname;
		public TextView mServiceLcZt;
		public TextView mServiceCkxz;
		public TextView mServiceRkxz;
		public TextView mServiceCkZs;
		public TextView mServiceCkTmZs;
		public TextView mServiceCkWtms;
		public TextView mServiceRkZs;
		public TextView mServiceRkTmZs;
		public TextView mServiceRkWtms;
		public TextView mSingletime;
	}
	
	private static class ViewHolerDotmachineOffline {
		public TextView mDotmachineOfflineID;
		public TextView mDotmachineOfflineZJEWM;
		public TextView mDotmachineOfflineJQBH;
		public TextView mDotmachineOfflineRWXX;
		public TextView mDotmachineOfflineHCSJ;
	}
	
	private static class ViewHolerDotmachineReplaceOffline {
		public TextView mDotmachineReplaceOfflineID;
		public TextView mDotmachineReplaceOfflineGDDH;
		public TextView mDotmachineReplaceOfflineJQBH;
		public TextView mDotmachineReplaceOfflineRWXX;
		public TextView mDotmachineReplaceOfflineHCSJ;
		
	}
	
}
