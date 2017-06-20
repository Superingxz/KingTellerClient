package com.kingteller.client.view;


import com.kingteller.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;

public class PopDialog extends Dialog {

	private Context context;
	private View view;

	public PopDialog(Context context, int resource) {
		super(context, R.style.Select_ImageAndFile_dialog);
		this.context = context;
		initView(resource);

	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	private void initView(int resource) {
		// TODO Auto-generated method stub
		view = getLayoutInflater().inflate(resource, null);
		setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// 设置显示位置
		onWindowAttributesChanged(wl);
		// 设置点击外围解散
		setCanceledOnTouchOutside(true);
	}

}
