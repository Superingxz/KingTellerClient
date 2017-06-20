package com.kingteller.client.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.utils.EditInputFilter;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.utils.CornerUtils;
import com.kingteller.client.view.toast.T;

/**
 * 维护报告 修改响应时间 	Dialog
 * Created by Administrator on 2015/7/19.
 */
public class ReportEditDialog extends Dialog implements OnClickListener {
	    private Context mContext;
	    private TextView title;
	    private EditText xysj, whsj;
	    private Button confim, cancel;
	    
	    private InputFilter[] filters = { new EditInputFilter() };
	    
	    private int bgColor = Color.parseColor("#ffffff");
	    private int btnPressColor = Color.parseColor("#E3E3E3");
	    
	    private EditOnClickL EditOnClickL;
	    
		public void setEditOnClickL(EditOnClickL EditOnClickL) {
			this.EditOnClickL = EditOnClickL;
		}
		
		public interface EditOnClickL{
			public void EditOnClick(String str1, String str2);
		}
	    
	    public ReportEditDialog(Context context, int theme) {
	        super(context, theme);
	        this.mContext = context;
	    }

	    public ReportEditDialog(Context context) {
	        super(context);
	        this.mContext = context;
	    }

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);//让window进行全屏显示
	        setContentView(R.layout.layout_dialog_reportedit);
	        setCanceledOnTouchOutside(false);// 设置点击屏幕 Dialog不消失
	        //setCancelable(false);// 设置点击返回键 Dialog不消失
	        initUI();
	    }

	    private void initUI(){
	        /**将对话框的大小按屏幕大小的百分比设置**/
	        WindowManager.LayoutParams lp = getWindow().getAttributes();
	        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
	        lp.width = (int) (d.widthPixels * 0.85); // 宽度设置为屏幕的0.85
	        getWindow().setAttributes(lp);

	        title = (TextView) this.findViewById(R.id.edit_xysjedit_title);
	        title.setText("请修改以下内容");
	        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
	        
	        confim = (Button) this.findViewById(R.id.btn_xysjedit_confirm);
	        cancel = (Button) this.findViewById(R.id.btn_xysjedit_cancel);
	        
	        float radius = dp2px(5);
	        cancel.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 0));
	        confim.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 1));
	 
	        xysj = (EditText) this.findViewById(R.id.edit_xysjedit_xysj);
	        xysj.setFilters(filters);
	        
	        whsj = (EditText) this.findViewById(R.id.edit_xysjedit_whsj);
	        whsj.setFilters(filters);
	        
	        confim.setOnClickListener(this);
	        cancel.setOnClickListener(this);

	    }

	    @Override
	    public void onClick(View v) {
	        switch (v.getId()) {
	            case R.id.btn_xysjedit_confirm:
	                String xysj_str = xysj.getText().toString().trim();
	                String whsj_str = whsj.getText().toString().trim();
	                
	    			if(KingTellerJudgeUtils.isEmpty(xysj_str)){
	    				T.showShort(mContext, "标准响应时间不能为空!");
	    				return;
	    			}
	    			
//	    			if(KingTellerJudgeUtils.isEmpty(whsj_str)){
//	    				T.showShort(mContext, "维护时间不能为空!");
//	    				return;
//	    			}
	    			
	    			if (EditOnClickL != null) {
	    				 EditOnClickL.EditOnClick(String.valueOf(Float.parseFloat(xysj_str)),
	    						 String.valueOf(Float.parseFloat("0")));  
	                }
	    			
	                break;
	            case R.id.btn_xysjedit_cancel:
	            	if (EditOnClickL != null) {
	    				 EditOnClickL.EditOnClick("", "");  
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
	    
	    public void setEditStr(String str1, String str2) {
	    	xysj.setText(String.valueOf(Float.parseFloat(str1)));
	    	whsj.setText(String.valueOf(Float.parseFloat(str2)));
		}
	    
	}