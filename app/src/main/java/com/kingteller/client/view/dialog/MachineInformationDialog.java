package com.kingteller.client.view.dialog;


import com.kingteller.R;
import com.kingteller.client.activity.workorder.MobileNavJqbhActivity;
import com.kingteller.client.bean.workorder.JqxxDataBean;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.utils.CornerUtils;
import com.kingteller.client.view.toast.T;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 获取机器信息Dialog
 * Created by Administrator on 2015/7/19.
 */
public class MachineInformationDialog extends Dialog implements OnClickListener{
    private Context mContext;
    private TextView title, wdsbjc, wdlxr;
    private Button  wdlxrdh1, wdlxrdh2, btn_cancel;
    private	ImageButton jqxxMap;
    
    private String mData;
    private JqxxDataBean data;
    
    private int bgColor = Color.parseColor("#ffffff");
    private int btnPressColor = Color.parseColor("#E3E3E3");

    public MachineInformationDialog(Context context, int theme, String data) {
        super(context, theme);
        this.mContext = context;
        this.mData = data;
    }

    public MachineInformationDialog(Context context) {
        super(context);
        this.mContext = context;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);//让window进行全屏显示
        setContentView(R.layout.layout_dialog_machineinformation);
        setCanceledOnTouchOutside(false);// 设置点击屏幕 Dialog不消失
//        setCancelable(false);// 设置点击返回键 Dialog不消失
        initUI();
        initData();
    }

	private void initUI() {
		  /**将对话框的大小按屏幕大小的百分比设置**/
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.85); // 宽度设置为屏幕的0.85
        getWindow().setAttributes(lp);

        title = (TextView) this.findViewById(R.id.jqxx_title);
        title.setText("机器信息");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
        
        wdsbjc = (TextView) this.findViewById(R.id.jqxx_wdsbjc);
        wdlxr = (TextView) this.findViewById(R.id.jqxx_wdlxr);
        wdlxrdh1 = (Button) this.findViewById(R.id.jqxx_wdlxdh1);
        wdlxrdh2 = (Button) this.findViewById(R.id.jqxx_wdlxdh2);
        jqxxMap = (ImageButton) this.findViewById(R.id.jqxx_map);
        
        btn_cancel = (Button) this.findViewById(R.id.btn_jqxx_cancle);
        
        btn_cancel.setText("关闭");
			
        float radius = dp2px(5);
        btn_cancel.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, -1));
        
        btn_cancel.setOnClickListener(this);
        wdlxrdh1.setOnClickListener(this);
        wdlxrdh2.setOnClickListener(this);
        jqxxMap.setOnClickListener(this);
	}


    private void initData(){
    	data = KingTellerJsonUtils.getPerson(mData, JqxxDataBean.class);
    	wdsbjc.setText(data.getWdsbjc());
		wdlxr.setText(data.getWdlxr());

		if (data.getWdlxdh().trim().contains(",")) {
			String[] str = data.getWdlxdh().split(",");
			wdlxrdh1.setText(str[0]);
			wdlxrdh2.setText(str[1]);
			wdlxrdh2.setVisibility(View.VISIBLE);
		} else {
			wdlxrdh1.setText(data.getWdlxdh());
			wdlxrdh2.setVisibility(View.GONE);
		}

    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        case R.id.jqxx_wdlxdh1:
        	KingTellerConfigUtils.dial(getContext(), wdlxrdh1.getText().toString().trim());
            break;
        case R.id.jqxx_wdlxdh2:
        	KingTellerConfigUtils.dial(getContext(), wdlxrdh2.getText().toString().trim());
            break;
        case R.id.jqxx_map:
        	if(KingTellerJudgeUtils.isEmpty(data.getExtfield28())){
				T.showShort(getContext(), "经度或纬度为空!");
				return; 
			}
			
			Intent intent = new Intent();
			String[] str = data.getExtfield28().split(",");
			intent.putExtra("lat", str[0]);
			intent.putExtra("lng", str[1]);
			intent.putExtra("title", "jqxx");
			intent.setClass(getContext(), MobileNavJqbhActivity.class);
			getContext().startActivity(intent);
            break;
        case R.id.btn_jqxx_cancle:
        	dismiss();
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
