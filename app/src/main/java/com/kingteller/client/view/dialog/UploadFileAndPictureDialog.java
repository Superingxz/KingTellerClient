package com.kingteller.client.view.dialog;

import com.kingteller.R;
import com.kingteller.client.view.dialog.utils.CornerUtils;

import android.app.Dialog;
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

public class UploadFileAndPictureDialog extends Dialog implements OnClickListener{
	
	private Context mContext;
	private TextView title;
	private Button  file, camera, pic, cancle;
	private FileAndPictureOnClickListener mFileAndPictureListener;
	
	private int mType; // 0 有文件    1没文件
	 
	private int bgColor = Color.parseColor("#ffffff");
	private int btnPressColor = Color.parseColor("#E3E3E3");
	    
	public UploadFileAndPictureDialog(Context context, int theme, int type) {
        super(context, theme);
        this.mContext = context;
        this.mType = type;
    }

    public UploadFileAndPictureDialog(Context context) {
        super(context);
        this.mContext = context;
    }
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);//让window进行全屏显示
        setContentView(R.layout.layout_dialog_uploadfileandpicture);
//      setCanceledOnTouchOutside(false);// 设置点击屏幕 Dialog不消失
//      setCancelable(false);// 设置点击返回键 Dialog不消失
        
        initUI();
    }
	
    private void initUI(){
    	/**将对话框的大小按屏幕大小的百分比设置**/
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        lp.width = (int) (d.widthPixels * 0.85); // 宽度设置为屏幕的0.85
        getWindow().setAttributes(lp);
        getWindow().setWindowAnimations(R.style.common_dialog_bottom_animstyle);// 设置显示动画

        title = (TextView) this.findViewById(R.id.upload_file_pic_title);
        if(mType == 1){
        	title.setText("请选择图片");
        	this.findViewById(R.id.View02).setVisibility(View.GONE);
        	this.findViewById(R.id.upload_file_pic_filebtn).setVisibility(View.GONE);
        }else{
        	title.setText("请选择文件或图片");
        }
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);

        file = (Button) this.findViewById(R.id.upload_file_pic_filebtn);
        camera = (Button) this.findViewById(R.id.upload_file_pic_camerabtn);
        pic = (Button) this.findViewById(R.id.upload_file_pic_picbtn);
        cancle = (Button) this.findViewById(R.id.upload_file_pic_canclebtn);
        
        float radius = dp2px(5);
        title.setBackgroundDrawable(CornerUtils.cornerDrawable(bgColor, new float[]{radius, radius, radius, radius, 0, 0, 0, 0}));
        file.setBackgroundDrawable(CornerUtils.btnSelector(0, bgColor, btnPressColor, -2));
        camera.setBackgroundDrawable(CornerUtils.btnSelector(0, bgColor, btnPressColor, -2));
        pic.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, -1));
        
        cancle.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, -2));
        
        file.setOnClickListener(this);
        camera.setOnClickListener(this);
        pic.setOnClickListener(this);
        cancle.setOnClickListener(this);
        
        
    }
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.upload_file_pic_filebtn:
				if(mFileAndPictureListener != null){
					mFileAndPictureListener.onClickNum(0);
				}
				break;
			case R.id.upload_file_pic_camerabtn:
				if(mFileAndPictureListener != null){
					mFileAndPictureListener.onClickNum(1);
				}
				break;
			case R.id.upload_file_pic_picbtn:
				if(mFileAndPictureListener != null){
					mFileAndPictureListener.onClickNum(2);
				}
				break;
			case R.id.upload_file_pic_canclebtn:
				if(mFileAndPictureListener != null){
					mFileAndPictureListener.onClickNum(3);
				}
				break;
			default:
				break;
		}
	}
	
	public void setFileAndPictureOnClickListener(FileAndPictureOnClickListener Listener) {
		this.mFileAndPictureListener = Listener;
	}
	
	/**
	 * 0: 取消  1：拍照  2：图片  3：文件
	 * @author Administrator
	 */
	public interface FileAndPictureOnClickListener{
		public void onClickNum(int num);
	}
	
	 /** dp to px */
    private int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
