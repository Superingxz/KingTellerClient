package com.kingteller.client.view.dialog;

import com.kingteller.R;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.toast.T;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 端口设置Dialog
 * Created by Administrator on 2015/7/19.
 */
public class LoginSetUpDialog extends Dialog implements OnClickListener{
	    private Context mContext;
	    private EditText address, port;
	    private Button confim, cancel;
	    public LoginSetUpDialog(Context context, int theme) {
	        super(context, theme);
	        this.mContext = context;
	    }

	    public LoginSetUpDialog(Context context) {
	        super(context);
	        this.mContext = context;
	    }

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);//让window进行全屏显示
	        setContentView(R.layout.layout_dialog_loginsetup);
	        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
	        initUI();
	        initData();
	    }

	    private void initUI(){
	        /**将对话框的大小按屏幕大小的百分比设置**/
	        WindowManager.LayoutParams lp = getWindow().getAttributes();
	        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
	        lp.width = (int) (d.widthPixels * 0.85); // 宽度设置为屏幕的0.85
	        getWindow().setAttributes(lp);

	        confim = (Button) this.findViewById(R.id.btn_login_setup_confirm);
	        cancel = (Button) this.findViewById(R.id.btn_login_setup_cancel);

	        address = (EditText) this.findViewById(R.id.edit_login_setup_address);
	        port = (EditText) this.findViewById(R.id.edit_login_setup_port);
   
	        confim.setOnClickListener(this);
	        cancel.setOnClickListener(this);

	    }

	    private void initData(){
	    	address.setText(KingTellerConfigUtils.getIpDomain(getContext()));
	        address.setSelection(KingTellerConfigUtils.getIpDomain(getContext()).length());
	        
			port.setText(KingTellerConfigUtils.getPort(getContext()));
			port.setSelection(KingTellerConfigUtils.getPort(getContext()).length());
	    }

	    @Override
	    public void onClick(View v) {
	        switch (v.getId()) {
	            case R.id.btn_login_setup_confirm:
	                String addressstr = address.getText().toString().trim();
	                String portstr = port.getText().toString().trim();
	              
	    			if(KingTellerJudgeUtils.isEmpty(addressstr)){
	    				T.showShort(mContext, "服务器不能为空!");
	    				return;
	    			}
	    			
	    			if(KingTellerJudgeUtils.isEmpty(portstr)){
	    				T.showShort(mContext, "端口不能为空!");
	    				return;
	    			}
	    			
	    			KingTellerConfigUtils.setIpDomain(getContext(), addressstr);
	    			KingTellerConfigUtils.setPort(getContext(), portstr);
	    			
	                Log.e("Login setup", addressstr + " , " + portstr);
	                
	                dismiss();
	                break;
	            case R.id.btn_login_setup_cancel:
	                dismiss();
	                break;
	            default:
	                break;
	        }
	    }
	}
