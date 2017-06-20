package com.kingteller.client.bean.qrcode;

import java.io.Serializable;
import java.util.List;

public class QRPreservationDataBean implements Serializable{

	private static final long serialVersionUID = 8438473055623530248L;

	/*{recDeliversWsId：0123，
		recDeliversWsFormsList:{[
			{recDeliversWsFormsId:aa,newcode:aa,barcode:b1},
			{recDeliversWsFormsId:aa,newcode:aa,barcode:b1},
			{recDeliversWsFormsId:aa,newcode:aa，barcode:b1}
		]}}*/
	private String recDeliversWsId;
	private List<QRPreservationDataEwmListBean> recDeliversWsFormsList;
	
	public String getRecDeliversWsId() {
		return recDeliversWsId;
	}
	public void setRecDeliversWsId(String recDeliversWsId) {
		this.recDeliversWsId = recDeliversWsId;
	}
	public List<QRPreservationDataEwmListBean> getRecDeliversWsFormsList() {
		return recDeliversWsFormsList;
	}
	public void setRecDeliversWsFormsList(
			List<QRPreservationDataEwmListBean> recDeliversWsFormsList) {
		this.recDeliversWsFormsList = recDeliversWsFormsList;
	}
	
}
