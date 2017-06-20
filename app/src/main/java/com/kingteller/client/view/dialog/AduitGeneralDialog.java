package com.kingteller.client.view.dialog;

import java.util.Arrays;
import java.util.List;

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
import android.widget.EditText;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.BarCodeEditDialog.EditOnClickL;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSearchClickL;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;
import com.kingteller.client.view.dialog.utils.CornerUtils;
import com.kingteller.client.view.toast.T;


/**
 * 批量 审批通用dialog
 * Created by Administrator on 2015/7/19.
 */
public class AduitGeneralDialog extends Dialog implements OnClickListener {
	    private Context mContext;
	    private TextView title;
	    private Button  btn_agree, btn_return, btn_cancel;
	    private	int mType;
	    
	    //oa
	    private TextView oa_audit_csy;
	    private EditText oa_audit_yj;
    	private String[] mSpyj_StringItem;
    	private List<String> mSpyj_StringItemList;
    	private String csy;
    	private String yj;
	    
	    private int bgColor = Color.parseColor("#ffffff");
	    private int btnPressColor = Color.parseColor("#E3E3E3");
	    
	    /** left btn click listener(左按钮接口) */
	    private OnBtnSearchClickL onBtnLeftClickL;
	    /** middle btn click listener(中按钮接口) */
	    private OnBtnSearchClickL onBtnMiddleClickL;
	    /** right btn click listener(右按钮接口) */
	    private OnBtnSearchClickL onBtnRightClickL;

	    /**
	     * set btn click listener(设置按钮监听事件)
	     */
	    public void setOnBtnSearchClickL(OnBtnSearchClickL... OnBtnSearchClickLs) {
            this.onBtnLeftClickL = OnBtnSearchClickLs[0];
            this.onBtnMiddleClickL = OnBtnSearchClickLs[1];
            this.onBtnRightClickL = OnBtnSearchClickLs[2];
	    }
		
	    
	    public AduitGeneralDialog(Context context, int theme, int type) {
	        super(context, theme);
	        this.mContext = context;
	        this.mType = type;
	    }

	    public AduitGeneralDialog(Context context) {
	        super(context);
	        this.mContext = context;
	    }

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);//让window进行全屏显示
	        setContentView(R.layout.layout_dialog_aduitgeneral);
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

	        title = (TextView) this.findViewById(R.id.aduit_title);
	        title.setText("请输入您的审批意见");
	        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
	        
	        btn_agree = (Button) this.findViewById(R.id.btn_aduit_agree);
	        btn_return = (Button) this.findViewById(R.id.btn_aduit_return);
	        btn_cancel = (Button) this.findViewById(R.id.btn_aduit_cancle);
	        
	        btn_agree.setText("同意");
			btn_return.setText("退回");
			btn_cancel.setText("取消");
				
	        float radius = dp2px(5);
	        btn_agree.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 0));
	        btn_return.setBackgroundDrawable(CornerUtils.btnSelector(0, bgColor, btnPressColor, -2));
	        btn_cancel.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 1));
   
	        btn_agree.setOnClickListener(this);
	        btn_return.setOnClickListener(this);
	        btn_cancel.setOnClickListener(this);
	
	    }

	    private void initData(int num){
	    	switch (num) {
				case 0:
					oa_audit_csy = (TextView) this.findViewById(R.id.oa_audit_csy);
			        oa_audit_csy.setOnClickListener(this);
			        oa_audit_yj= (EditText) this.findViewById(R.id.oa_audit_yj);
	
			        mSpyj_StringItem = new String[] {"同意", "核查无误", "批准", "退回"};
					mSpyj_StringItemList = Arrays.asList(mSpyj_StringItem);
					break;
				default:
					break;
			}
	    }
	    
	    @Override
	    public void onClick(View v) {
	    	if(mType == 0 && v.getId() != R.id.oa_audit_csy && v.getId() != R.id.btn_aduit_cancle){
	    		csy = oa_audit_csy.getText().toString();
				yj = oa_audit_yj.getText().toString();
				//限制条件
        		if("请选择常用批示语".equals(csy)){
        			T.showShort(mContext, "请选择常用批示语!");
        			return;
        		}
        		if(KingTellerJudgeUtils.isEmpty(yj)){
        			T.showShort(mContext, "请输入您的审批意见!");
        			return;
        		}
	    	}
	        switch (v.getId()) {
		        case R.id.oa_audit_csy:
		        	    final NormalListDialog dialog_spCsy = new NormalListDialog(mContext, mSpyj_StringItem);
		        	    dialog_spCsy.title("请选择常用批示语")//
			  		                .layoutAnimation(null)
			  		                .titleBgColor(Color.parseColor("#409ED7"))//
			  		                .itemPressColor(Color.parseColor("#85D3EF"))//
			  		                .itemTextColor(Color.parseColor("#303030"))//
			  		                .show();
		        	    dialog_spCsy.setOnOperItemClickL(new OnOperItemClickL() {
			  		            @Override
			  		            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
			  		            	dialog_spCsy.dismiss();
			  		            	oa_audit_csy.setText(mSpyj_StringItemList.get(position).toString());
			  		            	oa_audit_yj.setText(mSpyj_StringItemList.get(position).toString());
			  		            }
			  		       });
			  		break;
			  		
	            case R.id.btn_aduit_agree://同意
	        		if( "退回".equals(csy)){
	        			T.showShort(mContext, "您选择的常用批示语为：" + csy + "! 不能选择同意" );
	        			return;
	        		}
	            	if (onBtnLeftClickL != null) {
	            		onBtnLeftClickL.onBtnClick(yj);
	                }
	                break;
	            case R.id.btn_aduit_return://退回
	            	if("同意".equals(csy) || "核查无误".equals(csy) ||	"批准".equals(csy)){
	        			T.showShort(mContext, "您选择的常用批示语为：" + csy + "! 不能选择退回" );
        				return;
	        		}
	            	
	            	if (onBtnMiddleClickL != null) {
	            		onBtnMiddleClickL.onBtnClick(yj);
	                }
	                break;
	                
	            case R.id.btn_aduit_cancle://取消
	            	if (onBtnRightClickL != null) {
	            		onBtnRightClickL.onBtnClick("");
	                }
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

	}