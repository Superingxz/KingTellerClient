package com.kingteller.client.view.dialog;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.activity.map.ATMQueryActivity;
import com.kingteller.client.adapter.SelectListAdapter;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.map.ATMSearchCondition;
import com.kingteller.client.bean.map.ServiceStationSearchCondition;
import com.kingteller.client.bean.qrcode.QRSearchCondition;
import com.kingteller.client.utils.KingTellerJudgeUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ATMSearchConditionDialog extends Dialog implements android.view.View.OnClickListener{
	
	
	private Button ok, cancel;
	private EditText mATM_search_jqbh, mATM_search_atmhm, mATM_search_ssdq,
	mATM_search_ssqy, mATM_search_ssyh, mATM_search_ssfwz, mATM_search_jqgsr,
	mFwz_search_ssdq, mFwz_search_ssqy, mFwz_search_fwz, mEwm_search_ckdbh;
	private TextView  mATM_search_jqlb, mEwm_search_ckdbhname, mEwm_search_wlztbtn;
	private ATMSearchCondition mATMCondition;
	private ServiceStationSearchCondition mFWZCondition;
	private QRSearchCondition mEWMFbillNoCondition;
	private Context mContext;
	private int jqlb_num = 0;
	private int Ewm_Bxnum = 0;
	private ATMMyDialogListener mListener;
	private int mSearchNum;
	
	/**
	 * ATM搜索
	 * @param context
	 * @param theme
	 * @param SearchNum  0
	 * @param AtmCondition
	 * @param listener
	 */
	public ATMSearchConditionDialog(Context context, int theme, int SearchNum, 
			ATMSearchCondition AtmCondition, ATMMyDialogListener listener) {
		super(context, theme);
		mContext = context;
		mATMCondition = AtmCondition;	
		mListener = listener;
		mSearchNum = SearchNum;
	}
	
	/**
	 * 服务站搜索
	 * @param context
	 * @param theme
	 * @param SearchNum  1
	 * @param FwzCondition
	 * @param listener
	 */
	public ATMSearchConditionDialog(Context context, int theme, int SearchNum, 
			ServiceStationSearchCondition FwzCondition, ATMMyDialogListener listener) {
		super(context, theme);
		mContext = context;
		mFWZCondition = FwzCondition;	
		mListener = listener;
		mSearchNum = SearchNum;
	}
	
	/**
	 * 二维码搜索
	 * @param context
	 * @param theme
	 * @param SearchNum  2
	 * @param EwmCondition
	 * @param listener
	 */
	public ATMSearchConditionDialog(Context context, int theme, int SearchNum, 
			QRSearchCondition EWMFbillNoCondition, ATMMyDialogListener listener) {
		super(context, theme);
		mContext = context;
		mEWMFbillNoCondition = EWMFbillNoCondition;	
		mListener = listener;
		mSearchNum = SearchNum;
	}
	
	public ATMSearchConditionDialog(Context context) {
		super(context);
	}

	  
	    
    public interface ATMMyDialogListener{   
        public void refreshActivity(String text);   
    } 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.atmsearchcondition_dialog);
		setCancelable(false);
		if(mSearchNum == 0){
			findViewById(R.id.atm_scrollView).setVisibility(View.VISIBLE);//显示Atm搜索
			findViewById(R.id.fwz_scrollView).setVisibility(View.GONE);//隐藏Fwz搜索
			findViewById(R.id.ewm_scrollView).setVisibility(View.GONE);//隐藏Ewm搜索
			initUI_Atm();
			initData_Atm();
		}else if(mSearchNum == 1){
			findViewById(R.id.atm_scrollView).setVisibility(View.GONE);//隐藏Atm搜索
			findViewById(R.id.fwz_scrollView).setVisibility(View.VISIBLE);//显示Fwz搜索
			findViewById(R.id.ewm_scrollView).setVisibility(View.GONE);//隐藏Ewm搜索
			initUI_Fwz();
			initData_Fwz();
		}else if(mSearchNum == 2 || mSearchNum == 3 || mSearchNum == 5){
			findViewById(R.id.atm_scrollView).setVisibility(View.GONE);//隐藏Atm搜索
			findViewById(R.id.fwz_scrollView).setVisibility(View.GONE);//隐藏Fwz搜索
			findViewById(R.id.ewm_scrollView).setVisibility(View.VISIBLE);//显示Ewm搜索
			findViewById(R.id.ewm_search_select_btn).setVisibility(View.GONE);//隐藏选择条件
			initUI_Ewm();
			initData_Ewm();
		}else if(mSearchNum == 4){
			findViewById(R.id.atm_scrollView).setVisibility(View.GONE);//隐藏Atm搜索
			findViewById(R.id.fwz_scrollView).setVisibility(View.GONE);//隐藏Fwz搜索
			findViewById(R.id.ewm_scrollView).setVisibility(View.VISIBLE);//显示Ewm搜索
			findViewById(R.id.ewm_search_select_btn).setVisibility(View.VISIBLE);//显示选择条件
			initUI_Ewm();
			initData_Ewm();
		}
		
	}
	
	private void initUI_Ewm() {
		ok = (Button) this.findViewById(R.id.atm_search_ok);
		cancel = (Button) this.findViewById(R.id.atm_search_cancel);
		
		mEwm_search_ckdbh = (EditText) this.findViewById(R.id.ewm_search_wcdbh);
		mEwm_search_ckdbhname = (TextView) this.findViewById(R.id.ewm_search_wcdbhname);
		mEwm_search_wlztbtn =  (TextView) this.findViewById(R.id.ewm_search_wlztbtn);
		
		if(mSearchNum == 2){
			mEwm_search_ckdbh.setHint("请输入出库单编号");
			mEwm_search_ckdbhname.setText("出库单编号：");
		}else if(mSearchNum == 3){
			mEwm_search_ckdbh.setHint("请输入收货单编号");
			mEwm_search_ckdbhname.setText("收货单编号：");
		}else if(mSearchNum == 4){
			mEwm_search_ckdbh.setHint("请输入报修单编号");
			mEwm_search_ckdbhname.setText("报修单编号：");
		}else if(mSearchNum == 5){
			mEwm_search_ckdbh.setHint("请输入登记单号");
			mEwm_search_ckdbhname.setText("物料出入库登记单号：");
		}
		
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this); 
		mEwm_search_wlztbtn.setOnClickListener(this);
	}
	
	private void initData_Ewm() {
		if(mSearchNum == 4){
			if(mEWMFbillNoCondition.getFbillNo()!=null){
				String [] bxString = mEWMFbillNoCondition.getFbillNo().split(",");
				mEwm_search_ckdbh.setText(bxString[0]); 
				mEwm_search_wlztbtn.setText(bxString[1]); 
			}else{
				mEwm_search_ckdbh.setText(""); 
				mEwm_search_wlztbtn.setText("全部"); 
			}
			
		}else{
			mEwm_search_ckdbh.setText(mEWMFbillNoCondition.getFbillNo()); 
		}
	}

	private void initUI_Fwz() {
		ok = (Button) this.findViewById(R.id.atm_search_ok);
		cancel = (Button) this.findViewById(R.id.atm_search_cancel);
		
		mFwz_search_ssdq = (EditText) this.findViewById(R.id.fwz_search_ssdq);
		mFwz_search_ssqy = (EditText) this.findViewById(R.id.fwz_search_ssqy);
		mFwz_search_fwz = (EditText) this.findViewById(R.id.fwz_search_fwzname);
		
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this); 
	}
	
	private void initData_Fwz() {
		mFwz_search_ssdq.setText(mFWZCondition.getSsdq()); 
		mFwz_search_ssqy.setText(mFWZCondition.getSsqy());
		mFwz_search_fwz.setText(mFWZCondition.getFwz());	
	}
	
	private void initUI_Atm() {
		// TODO Auto-generated method stub
		ok = (Button) this.findViewById(R.id.atm_search_ok);
		cancel = (Button) this.findViewById(R.id.atm_search_cancel);
		
		mATM_search_jqbh = (EditText) this.findViewById(R.id.atm_search_jqbh);
		mATM_search_atmhm = (EditText) this.findViewById(R.id.atm_search_atmhm);
		mATM_search_jqlb = (TextView) this.findViewById(R.id.atm_search_jqlb);
		mATM_search_ssdq = (EditText) this.findViewById(R.id.atm_search_ssdq);
		mATM_search_ssqy = (EditText) this.findViewById(R.id.atm_search_ssqy);
		mATM_search_ssyh = (EditText) this.findViewById(R.id.atm_search_ssyh);
		mATM_search_ssfwz = (EditText) this.findViewById(R.id.atm_search_ssfwz);
		mATM_search_jqgsr = (EditText) this.findViewById(R.id.atm_search_jqgsr);

		

		ok.setOnClickListener(this);
		cancel.setOnClickListener(this); 
		mATM_search_jqlb.setOnClickListener(this);
	}

	private void initData_Atm() {
		JudgeJqlb(0, mATMCondition.getJqlb());
		mATM_search_jqbh.setText(mATMCondition.getJqbh()); 
		mATM_search_atmhm.setText(mATMCondition.getAtmhlike());
		
		JudgeJqlb(1, mATMCondition.getJqlb());
		
		mATM_search_ssdq.setText(mATMCondition.getExtfield20());
		mATM_search_ssqy.setText(mATMCondition.getSsqy());
		mATM_search_ssyh.setText(mATMCondition.getSsyhLike());
		mATM_search_ssfwz.setText(mATMCondition.getSsbsc());
		mATM_search_jqgsr.setText(mATMCondition.getJqgsrusername());
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.atm_search_jqlb:
			new AlertDialog.Builder(mContext)
			.setTitle("机器类别：")//A ATM Q 清分机
			.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
               public void onClick(DialogInterface dialog, int which) { 
            	   
            	   JudgeJqlb(1, mATMCondition.getJqlb());
            	  
            	   dialog.dismiss();
               } 
			})
			.setSingleChoiceItems(new String[] { "ATM", "清分机", "CRS", "VTM", "查询机", "其他"},
					jqlb_num, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int pos) {
								switch (pos) { 
				            	   	case 0:
				            	   		mATMCondition.setJqlb("A");
										jqlb_num = 0;
										break;
									case 1:
										mATMCondition.setJqlb("Q");
										jqlb_num = 1;
										break;
									case 2:
				            	   		mATMCondition.setJqlb("C");
										jqlb_num = 2;
										break;
									case 3:
				            	   		mATMCondition.setJqlb("V");
										jqlb_num = 3;
										break;
									case 4:
				            	   		mATMCondition.setJqlb("S");
										jqlb_num = 4;
										break;
									case 5:
				            	   		mATMCondition.setJqlb("O");
										jqlb_num = 5;
										break;
									default:
										break;
								}
							}
						})
			.setCancelable(false).show();
			break;
			
		case R.id.atm_search_ok:
			if(mSearchNum == 0){
				mATMCondition.setJqbh(mATM_search_jqbh.getText().toString());
				mATMCondition.setAtmhlike(mATM_search_atmhm.getText().toString());
				
				mATMCondition.setExtfield20(mATM_search_ssdq.getText().toString());
				mATMCondition.setSsqy(mATM_search_ssqy.getText().toString());
				
				mATMCondition.setSsyhLike(mATM_search_ssyh.getText().toString());
				mATMCondition.setSsbsc(mATM_search_ssfwz.getText().toString());
				mATMCondition.setJqgsrusername(mATM_search_jqgsr.getText().toString());
				
				mATMCondition.setPage("1");
				dismiss();
				
				mListener.refreshActivity(mATM_search_atmhm.getText().toString());
			}else if(mSearchNum == 1){
				mFWZCondition.setSsdq(mFwz_search_ssdq.getText().toString());
				mFWZCondition.setSsqy(mFwz_search_ssqy.getText().toString());
				mFWZCondition.setFwz(mFwz_search_fwz.getText().toString());
				mFWZCondition.setPage("1");
				dismiss();
				mListener.refreshActivity("1");
			}else if(mSearchNum == 2 || mSearchNum == 3 || mSearchNum == 4 || mSearchNum == 5){
				String num = mSearchNum + "";
				if(mSearchNum == 4){
					mEWMFbillNoCondition.setFbillNo(mEwm_search_ckdbh.getText().toString() + "," +
							mEwm_search_wlztbtn.getText().toString());
					mListener.refreshActivity(num);
				}else{
					mEWMFbillNoCondition.setFbillNo(mEwm_search_ckdbh.getText().toString());
					mListener.refreshActivity(num);
				}
				
				dismiss();
				
			}
			break;
		case R.id.ewm_search_wlztbtn:
			String str = mEwm_search_wlztbtn.getText().toString();
			if("全部".equals(str)){
				Ewm_Bxnum = 0;
			}else if("已扫描".equals(str)){
				Ewm_Bxnum = 1;
			}else if("未扫描".equals(str)){
				Ewm_Bxnum = 2;
			}
			new AlertDialog.Builder(mContext)
			.setTitle("请选择保修单状态")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
               public void onClick(DialogInterface dialog, int which) {   	  
            	   dialog.dismiss();
               } 
			})
			.setSingleChoiceItems(new String[] { "全部", "已扫描", "未扫描"},
					Ewm_Bxnum, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int pos) {
								switch (pos) { 
									case 0:
										mEwm_search_wlztbtn.setText("全部");
										break;
				            	   	case 1:
				            	   	    mEwm_search_wlztbtn.setText("已扫描");
										break;
									case 2:
										mEwm_search_wlztbtn.setText("未扫描");
										break;
									default:
										break;
								}
							}
						})
			.setCancelable(false).show();
			break;
		case R.id.atm_search_cancel:
			dismiss();
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * 判断当前选中的项和更新UI
	 */
	private void JudgeJqlb(int num, String jqlb) {
		switch (num) {
		case 0:
			if(KingTellerJudgeUtils.isEmpty(jqlb)){
				jqlb_num = 0;
				mATMCondition.setJqlb("A");
			}else if(jqlb.equals("A") == true){
				jqlb_num = 0;
			}else if(jqlb.equals("Q") == true){
				jqlb_num = 1;
			}else if(jqlb.equals("C") == true){
				jqlb_num = 2;
			}else if(jqlb.equals("V") == true){
				jqlb_num = 3;
			}else if(jqlb.equals("S") == true){
				jqlb_num = 4;
			}else if(jqlb.equals("O") == true){
				jqlb_num = 5;
			}
			break;
		case 1:
			//"ATM", "清分机", "CRS", "VTM", "查询机", "其他"
			if(jqlb.equals("A") == true){
			    mATM_search_jqlb.setText("ATM");
		    }else if(jqlb.equals("Q") == true){
			    mATM_search_jqlb.setText("清分机");
		    }else if(jqlb.equals("C") == true){
		    	mATM_search_jqlb.setText("CRS");
			}else if(jqlb.equals("V") == true){
				mATM_search_jqlb.setText("VTM");
			}else if(jqlb.equals("S") == true){
				mATM_search_jqlb.setText("查询机");
			}else if(jqlb.equals("O") == true){
				mATM_search_jqlb.setText("其他");
			}
			break;
		default:
			break;
		}
	}
}
