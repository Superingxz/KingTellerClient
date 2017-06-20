package com.kingteller.client.view.dialog;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.kingteller.R;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.calendar.CalendarDay;
import com.kingteller.client.view.dialog.entity.SearchGeneraItem;
import com.kingteller.client.view.dialog.listener.OnBtnSearchClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSelectDateClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSelectTimeClickL;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;
import com.kingteller.client.view.dialog.use.KingTellerDateTimeDialogUtils;
import com.kingteller.client.view.dialog.utils.CornerUtils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


/**
 * 搜索通用 Dialog
 * @param type 0:考勤模块		考勤审批  
 * @param type 1:地图模块		机器定位查询
 * @param type 2:地图模块		服务站定位查询
 * @param type 3:地图模块		员工定位查询
 * @param type 4:二维码模块		出入库登记查询
 * @param type 5:二维码模块		报修物料查询
 * @param type 6:二维码模块		发货物料查询
 * @param type 7:二维码模块		收货物料查询
 * Created by Administrator on 2015/7/19.
 */
public class SearchGeneralDialog extends Dialog implements OnClickListener{
	    private Context mContext;
	    private TextView title;
	    private Button  btn_search, btn_clear, btn_cancel;
	    private	int mType;
	    private String mList;
	    
	    //oa 	sp====================0
	    private TextView aduit_djlb, aduit_ksrq, aduit_jsrq;
	    private EditText aduit_ssbm;
	    
	    //map   atm===================1
	    private TextView mapatm_jqlb;
	    private EditText mapatm_jqbh, mapatm_atmhm, mapatm_ssdq, mapatm_ssqy, mapatm_ssyh, mapatm_ssfwz, mapatm_jqgsr;

	    //map   fwz===================2
	    private EditText mapfwz_ssdq, mapfwz_ssqy, mapfwz_fwzm;
	    
	    //map   yg====================3
	    private EditText mapyg_yhxm, mapyg_yhzh, mapyg_ssbm;
	    private TextView mapyg_cxrq, mapyg_kssj, mapyg_jssj;
	    
	    //qr    crkdj=================4
	    private EditText qrcrkdj_djdh;
	    
	    //qr    bxwl==================5
	    private EditText qrbxwl_wldh;
	    private TextView qrbxwl_wlzt;
	    
	    //qr    fhwl==================6
	    private EditText qrfhwl_fhdh;
	    
	    //qr    shwl==================7
	    private EditText qrshwl_shdh;
	    
    	private String[] StringItem;
    	private List<String> StringItemList;
    	
	    private int bgColor = Color.parseColor("#ffffff");
	    private int btnPressColor = Color.parseColor("#E3E3E3");
	    
	    /** left btn click listener(左按钮接口) */
	    private OnBtnSearchClickL onBtnLeftClickL;
	    /** right btn click listener(右按钮接口) */
	    private OnBtnSearchClickL onBtnRightClickL;

	    /**
	     * set btn click listener(设置按钮监听事件)
	     */
	    public void setOnBtnSearchClickL(OnBtnSearchClickL... OnBtnSearchClickLs) {
            this.onBtnLeftClickL = OnBtnSearchClickLs[0];
            this.onBtnRightClickL = OnBtnSearchClickLs[1];
	    }
	    
	    public SearchGeneralDialog(Context context, int theme, int type, String list) {
	        super(context, theme);
	        this.mContext = context;
	        this.mType = type;
	        this.mList = list;
	    }

	    public SearchGeneralDialog(Context context) {
	        super(context);
	        this.mContext = context;
	    }
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);//让window进行全屏显示
	        setContentView(R.layout.layout_dialog_searchgeneral);
	        setCanceledOnTouchOutside(false);// 设置点击屏幕 Dialog不消失
//	        setCancelable(false);// 设置点击返回键 Dialog不消失
	        initUI();
	        initData(mType);
	    }

	    private void initUI(){
	        /**将对话框的大小按屏幕大小的百分比设置**/
	        WindowManager.LayoutParams lp = getWindow().getAttributes();
	        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
	        lp.width = (int) (d.widthPixels * 0.85); // 宽度设置为屏幕的0.85
	        getWindow().setAttributes(lp);

	        title = (TextView) this.findViewById(R.id.search_title);
	        title.setText("请输入查询条件");
	        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
	        
	        btn_search = (Button) this.findViewById(R.id.btn_search_search);
	        btn_clear = (Button) this.findViewById(R.id.btn_search_clear);
	        btn_cancel = (Button) this.findViewById(R.id.btn_search_cancle);
				
	        float radius = dp2px(5);
	        btn_search.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 0));
	        btn_clear.setBackgroundDrawable(CornerUtils.btnSelector(0, bgColor, btnPressColor, -2));
	        btn_cancel.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 1));
   
	        btn_search.setOnClickListener(this);
	        btn_clear.setOnClickListener(this);
	        btn_cancel.setOnClickListener(this);
	       
	    }

	    private void initData(int num){
	    	switch (num) {
				case 0:
					btn_search.setText("搜索");
				    btn_clear.setText("清空");
				    btn_cancel.setText("取消");
				        
					this.findViewById(R.id.search_kq_aduit_layout).setVisibility(View.VISIBLE);
					aduit_djlb = (TextView) this.findViewById(R.id.search_kq_aduit_djlb);
					aduit_ksrq = (TextView) this.findViewById(R.id.search_kq_aduit_ksrq);
					aduit_jsrq = (TextView) this.findViewById(R.id.search_kq_aduit_jsrq);
					aduit_ssbm = (EditText) this.findViewById(R.id.search_kq_aduit_ssbm);
					aduit_djlb.setOnClickListener(this);
					aduit_ksrq.setOnClickListener(this);
					aduit_jsrq.setOnClickListener(this);
					
					SearchGeneraItem searchStr_kq = KingTellerJsonUtils.getPerson(mList, SearchGeneraItem.class);
					
					aduit_djlb.setText(KingTellerJudgeUtils.isEmpty(searchStr_kq.getBillType()) ? "请选择单据类型" : searchStr_kq.getBillType()); 
					aduit_ksrq.setText(KingTellerJudgeUtils.isEmpty(searchStr_kq.getStartTime()) ? "请选择开始日期" : searchStr_kq.getStartTime());
					aduit_jsrq.setText(KingTellerJudgeUtils.isEmpty(searchStr_kq.getEndTime()) ? "请选择结束日期" : searchStr_kq.getEndTime());
					aduit_ssbm.setText(KingTellerJudgeUtils.isEmpty(searchStr_kq.getTheDepartment()) ? "" : searchStr_kq.getTheDepartment());
					
					StringItem = new String[] {"加班", "请假", "销假", "出差", "补登", "旷工"};
					StringItemList = Arrays.asList(StringItem);
					break;
				case 1:
					btn_search.setText("搜索");
				    btn_clear.setText("默认");
				    btn_cancel.setText("取消");
				    
					this.findViewById(R.id.search_map_atm_layout).setVisibility(View.VISIBLE);
					mapatm_jqbh = (EditText) this.findViewById(R.id.search_map_atm_jqbh);
					mapatm_atmhm = (EditText) this.findViewById(R.id.search_map_atm_atmhm);
					mapatm_jqlb = (TextView) this.findViewById(R.id.search_map_atm_jqlb);
					mapatm_ssdq = (EditText) this.findViewById(R.id.search_map_atm_ssdq);
					mapatm_ssqy = (EditText) this.findViewById(R.id.search_map_atm_ssqy);
					mapatm_ssyh = (EditText) this.findViewById(R.id.search_map_atm_ssyh);
					mapatm_ssfwz = (EditText) this.findViewById(R.id.search_map_atm_ssfwz);
					mapatm_jqgsr = (EditText) this.findViewById(R.id.search_map_atm_jqgsr);
					
					mapatm_jqlb.setOnClickListener(this);
					
					SearchGeneraItem searchStr_atm = KingTellerJsonUtils.getPerson(mList, SearchGeneraItem.class);
					
					mapatm_jqbh.setText(KingTellerJudgeUtils.isEmpty(searchStr_atm.getAtmJqbh()) ? "" : searchStr_atm.getAtmJqbh()); 
					mapatm_atmhm.setText(KingTellerJudgeUtils.isEmpty(searchStr_atm.getAtmNum()) ? "" : searchStr_atm.getAtmNum());
					mapatm_jqlb.setText(KingTellerJudgeUtils.isEmpty(searchStr_atm.getAtmJqlb()) ? "请选择机器类别" : searchStr_atm.getAtmJqlb());
					mapatm_ssdq.setText(KingTellerJudgeUtils.isEmpty(searchStr_atm.getAtmSsdq()) ? "" : searchStr_atm.getAtmSsdq());
					mapatm_ssqy.setText(KingTellerJudgeUtils.isEmpty(searchStr_atm.getAtmSsqy()) ? "" : searchStr_atm.getAtmSsqy());
					mapatm_ssyh.setText(KingTellerJudgeUtils.isEmpty(searchStr_atm.getAtmSsyh()) ? "" : searchStr_atm.getAtmSsyh());
					mapatm_ssfwz.setText(KingTellerJudgeUtils.isEmpty(searchStr_atm.getAtmSsfwz()) ? "" : searchStr_atm.getAtmSsfwz());
					mapatm_jqgsr.setText(KingTellerJudgeUtils.isEmpty(searchStr_atm.getAtmJqgsr()) ? "" : searchStr_atm.getAtmJqgsr());
					
					StringItem = new String[] {"ATM", "清分机", "CRS", "VTM", "查询机", "其他"};
					StringItemList = Arrays.asList(StringItem);
					break;
				case 2:
					btn_search.setText("搜索");
				    btn_clear.setText("清空");
				    btn_cancel.setText("取消");
				    
					this.findViewById(R.id.search_map_fwz_layout).setVisibility(View.VISIBLE);
					mapfwz_ssdq = (EditText) this.findViewById(R.id.search_map_fwz_ssdq);
					mapfwz_ssqy = (EditText) this.findViewById(R.id.search_map_fwz_ssqy);
					mapfwz_fwzm = (EditText) this.findViewById(R.id.search_map_fwz_fwzm);
					
					SearchGeneraItem searchStr_fwz = KingTellerJsonUtils.getPerson(mList, SearchGeneraItem.class);

					mapfwz_ssdq.setText(KingTellerJudgeUtils.isEmpty(searchStr_fwz.getFwzSsdq()) ? "" : searchStr_fwz.getFwzSsdq());
					mapfwz_ssqy.setText(KingTellerJudgeUtils.isEmpty(searchStr_fwz.getFwzSsqy()) ? "" : searchStr_fwz.getFwzSsqy());
					mapfwz_fwzm.setText(KingTellerJudgeUtils.isEmpty(searchStr_fwz.getFwzName()) ? "" : searchStr_fwz.getFwzName());

					break;
				case 3:
					btn_search.setText("搜索");
				    btn_clear.setText("默认");
				    btn_cancel.setText("取消");
				    
					this.findViewById(R.id.search_map_yg_layout).setVisibility(View.VISIBLE);
				    mapyg_yhxm = (EditText) this.findViewById(R.id.search_map_yg_yhxm);
				    mapyg_yhzh = (EditText) this.findViewById(R.id.search_map_yg_yhzh);
				    mapyg_ssbm = (EditText) this.findViewById(R.id.search_map_yg_ssbm);
				    mapyg_cxrq = (TextView) this.findViewById(R.id.search_map_yg_cxrq);
				    mapyg_kssj = (TextView) this.findViewById(R.id.search_map_yg_kssj);
				    mapyg_jssj = (TextView) this.findViewById(R.id.search_map_yg_jssj);
				    
				    mapyg_cxrq.setOnClickListener(this);
				    mapyg_kssj.setOnClickListener(this);
				    mapyg_jssj.setOnClickListener(this);
				    
					SearchGeneraItem searchStr_yg = KingTellerJsonUtils.getPerson(mList, SearchGeneraItem.class);

					mapyg_yhxm.setText(KingTellerJudgeUtils.isEmpty(searchStr_yg.getYgYhxm()) ? "" : searchStr_yg.getYgYhxm());
					mapyg_yhzh.setText(KingTellerJudgeUtils.isEmpty(searchStr_yg.getYgYhzh()) ? "" : searchStr_yg.getYgYhzh());
					mapyg_ssbm.setText(KingTellerJudgeUtils.isEmpty(searchStr_yg.getYgSsbm()) ? "" : searchStr_yg.getYgSsbm());
					mapyg_cxrq.setText(KingTellerJudgeUtils.isEmpty(searchStr_yg.getYgCxrq()) ? "" : searchStr_yg.getYgCxrq());
					mapyg_kssj.setText(KingTellerJudgeUtils.isEmpty(searchStr_yg.getYgKssj()) ? "" : searchStr_yg.getYgKssj());
					mapyg_jssj.setText(KingTellerJudgeUtils.isEmpty(searchStr_yg.getYgJssj()) ? "" : searchStr_yg.getYgJssj());
					break;
				case 4:
					btn_search.setText("搜索");
				    btn_clear.setText("清空");
				    btn_cancel.setText("取消");
				    
					this.findViewById(R.id.search_qr_crkdj_layout).setVisibility(View.VISIBLE);
					qrcrkdj_djdh = (EditText) this.findViewById(R.id.search_qr_crkdj_djdh);

					SearchGeneraItem searchStr_crkdj = KingTellerJsonUtils.getPerson(mList, SearchGeneraItem.class);

					qrcrkdj_djdh.setText(KingTellerJudgeUtils.isEmpty(searchStr_crkdj.getQrCrkdjdh()) ? "" : searchStr_crkdj.getQrCrkdjdh());

					break;
				case 5:
					btn_search.setText("搜索");
				    btn_clear.setText("默认");
				    btn_cancel.setText("取消");
				    
					this.findViewById(R.id.search_qr_bxwl_layout).setVisibility(View.VISIBLE);
					qrbxwl_wldh = (EditText) this.findViewById(R.id.search_qr_bxwl_bxdh);
					qrbxwl_wlzt = (TextView) this.findViewById(R.id.search_qr_bxwl_wlzt);
					
					qrbxwl_wlzt.setOnClickListener(this);
					
					SearchGeneraItem searchStr_wlbx = KingTellerJsonUtils.getPerson(mList, SearchGeneraItem.class);
	
					qrbxwl_wldh.setText(KingTellerJudgeUtils.isEmpty(searchStr_wlbx.getQrBxwldh()) ? "" : searchStr_wlbx.getQrBxwldh());
					qrbxwl_wlzt.setText(KingTellerJudgeUtils.isEmpty(searchStr_wlbx.getQrBxwlzt()) ? "" : searchStr_wlbx.getQrBxwlzt());
					
					StringItem = new String[] {"全部", "已扫描", "未扫描"};
					StringItemList = Arrays.asList(StringItem);
					break;
				case 6:
					btn_search.setText("搜索");
				    btn_clear.setText("清空");
				    btn_cancel.setText("取消");
				    
					this.findViewById(R.id.search_qr_fhwl_layout).setVisibility(View.VISIBLE);
					qrfhwl_fhdh = (EditText) this.findViewById(R.id.search_qr_fhwl_ckdh);

					SearchGeneraItem searchStr_fhwl = KingTellerJsonUtils.getPerson(mList, SearchGeneraItem.class);

					qrfhwl_fhdh.setText(KingTellerJudgeUtils.isEmpty(searchStr_fhwl.getQrFhwldh()) ? "" : searchStr_fhwl.getQrFhwldh());

					break;
				case 7:
					btn_search.setText("搜索");
				    btn_clear.setText("清空");
				    btn_cancel.setText("取消");
				    
					this.findViewById(R.id.search_qr_shwl_layout).setVisibility(View.VISIBLE);
					qrshwl_shdh = (EditText) this.findViewById(R.id.search_qr_shwl_shdh);

					SearchGeneraItem searchStr_shwl = KingTellerJsonUtils.getPerson(mList, SearchGeneraItem.class);

					qrshwl_shdh.setText(KingTellerJudgeUtils.isEmpty(searchStr_shwl.getQrShwldh()) ? "" : searchStr_shwl.getQrShwldh());

					break;
				default:
					break;
			}
	    }
	    
	    /** dp to px */
	    private int dp2px(float dp) {
	        final float scale = mContext.getResources().getDisplayMetrics().density;
	        return (int) (dp * scale + 0.5f);
	    }

		@Override
		public void onClick(View v) {
			 switch (v.getId()) {
		        case R.id.search_kq_aduit_djlb:
		        	setList(StringItem, "请选择单据类型", 0);
			  		break;
		        case R.id.search_map_atm_jqlb:
		        	setList(StringItem, "请选择机器类别", 1);
		        	break;
		        case R.id.search_qr_bxwl_wlzt:
		        	setList(StringItem, "请选择报修物料状态", 2);
		        	break;
		        case R.id.search_kq_aduit_ksrq:
		        	setDay(aduit_ksrq.getText().toString(), 0);
	                break;
		        case R.id.search_kq_aduit_jsrq:
		        	setDay(aduit_jsrq.getText().toString(), 1);
	                break;
		        case R.id.search_map_yg_cxrq:
		        	setDay(mapyg_cxrq.getText().toString(), 2);
	                break;
		        case R.id.search_map_yg_kssj:
		        	setTime(mapyg_kssj.getText().toString(), 0);
	                break;
		        case R.id.search_map_yg_jssj:
		        	setTime(mapyg_jssj.getText().toString(), 1);
	                break;
	            case R.id.btn_search_search://同意
	            	SearchGeneraItem str = new SearchGeneraItem();
	            	if(mType == 0){
	            		String kq_djlb = aduit_djlb.getText().toString();
	            		String kq_ksrq = aduit_ksrq.getText().toString();
	            		String kq_jsrq = aduit_jsrq.getText().toString();
	            		String kq_ssbm = aduit_ssbm.getText().toString();
	            		
	            		
            			str.setBillType("请选择单据类型".equals(kq_djlb) ? "" : kq_djlb); 
            			str.setStartTime("请选择开始日期".equals(kq_ksrq) ? "" : kq_ksrq);
            			str.setEndTime("请选择结束日期".equals(kq_jsrq) ? "" : kq_jsrq);
            			str.setTheDepartment("".equals(kq_ssbm) ? "" : kq_ssbm);
	            		
	            	}else if(mType == 1){
	            		String atm_jqbh = mapatm_jqbh.getText().toString();
	            		String atm_atmhm = mapatm_atmhm.getText().toString();
	            		String atm_jqlb = mapatm_jqlb.getText().toString();
	            		String atm_ssdq = mapatm_ssdq.getText().toString();
	            		String atm_ssqy = mapatm_ssqy.getText().toString();
	            		String atm_ssyh = mapatm_ssyh.getText().toString();
	            		String atm_ssfwz = mapatm_ssfwz.getText().toString();
	            		String atm_jqgsr = mapatm_jqgsr.getText().toString();
	            		
	            		str.setAtmJqbh("".equals(atm_jqbh) ? "" : atm_jqbh);
            			str.setAtmNum("".equals(atm_atmhm) ? "" : atm_atmhm);
            			str.setAtmJqlb("请选择机器类别".equals(atm_jqlb) ? "" : atm_jqlb);
            			str.setAtmSsdq("".equals(atm_ssdq) ? "" : atm_ssdq);
            			str.setAtmSsqy("".equals(atm_ssqy) ? "" : atm_ssqy);
            			str.setAtmSsyh("".equals(atm_ssyh) ? "" : atm_ssyh);
            			str.setAtmSsfwz("".equals(atm_ssfwz) ? "" : atm_ssfwz);
            			str.setAtmJqgsr("".equals(atm_jqgsr) ? "" : atm_jqgsr);
            			
	            	}else if(mType == 2){
	            		String fwz_ssdq = mapfwz_ssdq.getText().toString();
	            		String fwz_ssqy = mapfwz_ssqy.getText().toString();
	            		String fwz_name = mapfwz_fwzm.getText().toString();

            			str.setFwzSsdq("".equals(fwz_ssdq) ? "" : fwz_ssdq);
            			str.setFwzSsqy("".equals(fwz_ssqy) ? "" : fwz_ssqy);
            			str.setFwzName("".equals(fwz_name) ? "" : fwz_name);
	            	}else if(mType == 3){
	            		String yg_yhxm = mapyg_yhxm.getText().toString();
	            		String yg_yhzh = mapyg_yhzh.getText().toString();
	            		String yg_ssbm = mapyg_ssbm.getText().toString();
	            		String yg_cxrq = mapyg_cxrq.getText().toString();
	            		String yg_kssj = mapyg_kssj.getText().toString();
	            		String yg_jssj = mapyg_jssj.getText().toString();
	            		
	            		str.setYgYhxm("".equals(yg_yhxm) ? "" : yg_yhxm);
            			str.setYgYhzh("".equals(yg_yhzh) ? "" : yg_yhzh);
            			str.setYgSsbm("".equals(yg_ssbm) ? "" : yg_ssbm);
            			str.setYgCxrq("请选择日期".equals(yg_cxrq) ? "" : yg_cxrq);
            			str.setYgKssj("开始时间".equals(yg_kssj) ? "" : yg_kssj);
            			str.setYgJssj("结束时间".equals(yg_jssj) ? "" : yg_jssj);
	            	}else if(mType == 4){
	            		String qr_djdh = qrcrkdj_djdh.getText().toString();
	            		
	            		str.setQrCrkdjdh("".equals(qr_djdh) ? "" : qr_djdh);
	            	}else if(mType == 5){
	            		String qr_wldh = qrbxwl_wldh.getText().toString();
	            		String qr_wlzt = qrbxwl_wlzt.getText().toString();
	            		
	            		str.setQrBxwldh("".equals(qr_wldh) ? "" : qr_wldh);
            			str.setQrBxwlzt("".equals(qr_wlzt) ? "" : qr_wlzt);
	            	}else if(mType == 6){
	            		String qr_fhdh = qrfhwl_fhdh.getText().toString();
	            		
	            		str.setQrFhwldh("".equals(qr_fhdh) ? "" : qr_fhdh);
	            	}else if(mType == 7){
	            		String qr_shdh = qrshwl_shdh.getText().toString();
	            		
	            		str.setQrShwldh("".equals(qr_shdh) ? "" : qr_shdh);
	            	}
	            	
	            	if (onBtnLeftClickL != null) {
	            		onBtnLeftClickL.onBtnClick(new Gson().toJson(str));
	                }
	                break;
	            case R.id.btn_search_cancle://取消
	            	if (onBtnRightClickL != null) {
	            		onBtnRightClickL.onBtnClick("");
	                }
	                break;
	            case R.id.btn_search_clear://清空
	            	if(mType == 0){
		            	aduit_djlb.setText("请选择单据类型");
		            	aduit_ksrq.setText("请选择开始日期");
		            	aduit_jsrq.setText("请选择结束日期");
		            	aduit_ssbm.setText("");
	            	}else if(mType == 1){
	            		mapatm_jqbh.setText("");
	            		mapatm_atmhm.setText("");
	            		mapatm_jqlb.setText("ATM");
	            		mapatm_ssdq.setText("");
	            		mapatm_ssqy.setText("");
	            		mapatm_ssyh.setText("");
	            		mapatm_ssfwz.setText("");
	            		mapatm_jqgsr.setText("");
	            	}else if(mType == 2){
	            		mapfwz_ssdq.setText("");
	            		mapfwz_ssqy.setText("");
	            		mapfwz_fwzm.setText("");
	            	}else if(mType == 3){
	            		mapyg_yhxm.setText("");
	            		mapyg_yhzh.setText("");
	            		mapyg_ssbm.setText("");
	            		mapyg_cxrq.setText(KingTellerTimeUtils.getNowDayAndTime(0));
	            		mapyg_kssj.setText("00:00");
	            		mapyg_jssj.setText("23:59");
	            	}else if(mType == 4){
	            		qrcrkdj_djdh.setText("");
	            	}else if(mType == 5){
	            		qrbxwl_wldh.setText("");
	            		qrbxwl_wlzt.setText("全部");
	            	}else if(mType == 6){
	            		qrfhwl_fhdh.setText("");
	            	}else if(mType == 7){
	            		qrshwl_shdh.setText("");
	            	}
	                break;
	            default:
	                break;
	        }
			
		}
		
		//设置日期
		private void setDay(String day, final int num) {
			Calendar calendar = null;
			
			if("请选择结束日期".equals(day) || "请选择开始日期".equals(day) || "请选择日期".equals(day)){
				calendar = Calendar.getInstance();
			 }else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(day, 2));
			 }
 
			 KingTellerDateTimeDialogUtils.showDatePickerDialog(mContext, calendar,
						new OnBtnSelectDateClickL() {
	                    	@Override
		                    public void OnBtnSelectDateClick(DatePicker view, int year, int month, int day) {
		                    	//取消
		                    }
	                    },new OnBtnSelectDateClickL() {
		                    @Override
		                    public void OnBtnSelectDateClick(DatePicker view, int year, int month, int day) {
		                    	if(num == 0){
		                    		aduit_ksrq.setText(KingTellerTimeUtils.getConversionFormatStringByDate(
			                    			CalendarDay.from(year, month, day).getDate(), 2));
		                    	}else if(num == 1){
		                    		aduit_jsrq.setText(KingTellerTimeUtils.getConversionFormatStringByDate(
			                    			CalendarDay.from(year, month, day).getDate(), 2));
		                    	}else if(num == 2){
		                    		mapyg_cxrq.setText(KingTellerTimeUtils.getConversionFormatStringByDate(
			                    			CalendarDay.from(year, month, day).getDate(), 2));
		                    	}
		                    }
	                });
		}
		
		//设置时间
		private void setTime(String time, final int num) {
			Calendar calendar_time = null;

			if("开始时间".equals(time) || "结束时间".equals(time)){
				calendar_time = Calendar.getInstance(TimeZone.getDefault());  
			}else{
				calendar_time = Calendar.getInstance(TimeZone.getDefault());  
				calendar_time.setTime(KingTellerTimeUtils.getConversionFormatDateByString(time, 3));
			}
				
			KingTellerDateTimeDialogUtils.showTimePickerDialog(mContext, calendar_time,  null,
				new OnBtnSelectTimeClickL() {
                	@Override
                    public void OnBtnSelectTimeClick(TimePicker view, int hour, int minute) {
                    	//取消
                    }
                },new OnBtnSelectTimeClickL() {
                    @Override
                    public void OnBtnSelectTimeClick(TimePicker view, int hour, int minute) {
                        if(num == 0){
                        	mapyg_kssj.setText(KingTellerTimeUtils.getConversionFormatTime(hour + ":" + minute, 1));
                    	}else if(num == 1){
                    		mapyg_jssj.setText(KingTellerTimeUtils.getConversionFormatTime(hour + ":" + minute, 1));
                    	}
                    }
            });
		}
		
		//设置时间
		private void setList(String[] str, String title, final int num) {
			final NormalListDialog dialog_list = new NormalListDialog(mContext, StringItem);
			dialog_list.title(title)//
  		                .layoutAnimation(null)
  		                .titleBgColor(Color.parseColor("#409ED7"))//
  		                .itemPressColor(Color.parseColor("#85D3EF"))//
  		                .itemTextColor(Color.parseColor("#303030"))//
  		                .show();
			dialog_list.setOnOperItemClickL(new OnOperItemClickL() {
  		            @Override
  		            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
  		            	dialog_list.dismiss();
  		            	if(num == 0){
  		            		aduit_djlb.setText(StringItemList.get(position).toString());
                    	}else if(num == 1){
                    		mapatm_jqlb.setText(StringItemList.get(position).toString());
                    	}else if(num == 2){
                    		qrbxwl_wlzt.setText(StringItemList.get(position).toString());
                    	}
  		            	
  		            }
  		       });
		}
}


