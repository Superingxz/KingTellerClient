package com.kingteller.client.view.dialog;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kingteller.R;
import com.kingteller.client.view.dialog.utils.CornerUtils;
import com.kingteller.client.view.toast.T;

/**
 * 通用  全局提示  Dialog
 * Created by Administrator on 2015/7/19.
 */
public class GlobalCueDialog extends Dialog implements OnClickListener{
    private Context mContext;
    private TextView title, count;
    private Button btn_copy, btn_cancel;
    
    private String mTitle;
    private String mError;
    
    private int bgColor = Color.parseColor("#ffffff");
    private int btnPressColor = Color.parseColor("#E3E3E3");

    private OnBtnClickL OnBtnClickL;
    
	public void setOnBtnClickL(OnBtnClickL OnBtnClickL) {
		this.OnBtnClickL = OnBtnClickL;
	}
	
	public interface OnBtnClickL{
		public void OnBtnClick();
	}
	
    public GlobalCueDialog(Context context, int theme, String title, String error) {
        super(context, theme);
        this.mContext = context;
        this.mTitle = title;
        this.mError = error;
    }

    public GlobalCueDialog(Context context) {
        super(context);
        this.mContext = context;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);//让window进行全屏显示
        setContentView(R.layout.layout_dialog_globalcue);
        setCanceledOnTouchOutside(false);// 设置点击屏幕 Dialog不消失
        setCancelable(false);// 设置点击返回键 Dialog不消失
        initUI();
    }

	private void initUI() {
		/**将对话框的大小按屏幕大小的百分比设置**/
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.85); // 宽度设置为屏幕的0.85
        lp.height = (int) (d.heightPixels * 0.70); // 高度设置为屏幕的0.80
        getWindow().setAttributes(lp);
        //getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//成为系统提示
        
        title = (TextView) this.findViewById(R.id.qjts_title);
        title.setText(mTitle);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
        
        count = (TextView) this.findViewById(R.id.qjts_content);
        count.setText(mError);
        count.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);

        btn_cancel = (Button) this.findViewById(R.id.btn_qjts_cancle);
        btn_copy = (Button) this.findViewById(R.id.btn_qjts_copy);
        
        btn_copy.setText("复制错误");
        btn_cancel.setText("关闭程序");
			
        float radius = dp2px(5);
        btn_copy.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 0));
        btn_cancel.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 1));
        
        btn_copy.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_qjts_copy:
			ClipboardManager myClipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData myClip = ClipData.newPlainText("text", mError);
		    myClipboard.setPrimaryClip(myClip);
		    
		    //T.showLongSetG(mContext, "成功复制到粘贴板");
            break;
        case R.id.btn_qjts_cancle:
        	if(OnBtnClickL != null){
        		OnBtnClickL.OnBtnClick();
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
