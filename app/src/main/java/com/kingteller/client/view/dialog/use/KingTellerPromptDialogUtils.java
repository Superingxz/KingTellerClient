package com.kingteller.client.view.dialog.use;

import android.graphics.Color;

import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;

/**
 * 通用 提示  Dialog 工具类
 * @author Administrator
 */
public class KingTellerPromptDialogUtils {

	/**
	 * 通用	一个按钮	提示	Dialog 工具类
	 * @param context
	 * @param title
	 * @param btnText 确定
	 */
	public static void showOnePromptDialog(NormalDialog OnePrompt_Dialog, String title, OnBtnClickL onBtnClick) {
		OnePrompt_Dialog.content(title)
			.btnNum(1)
	        .style(NormalDialog.STYLE_TWO)
	        .btnText("确认")
	        .btnTextColor(Color.parseColor("#383838"))
			.show();
		OnePrompt_Dialog.setOnBtnClickL(onBtnClick);
	}
	
	/**
	 * 通用	两个按钮	提示	Dialog 工具类
	 * @param context
	 * @param title
	 * @param btnText	取消	确定
	 */
	public static void showTwoPromptDialog(NormalDialog TwoPrompt_Dialog, String title, OnBtnClickL... OnBtnClickLs) {
		TwoPrompt_Dialog.content(title)
                .style(NormalDialog.STYLE_TWO)
                .btnText("取消", "确定")
                .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#383838"))
				.show();
		TwoPrompt_Dialog.setCanceledOnTouchOutside(false);
		TwoPrompt_Dialog.setOnBtnClickL(OnBtnClickLs);
	}
	
	/**
	 * 通用	两个按钮	提示	Dialog 工具类
	 * @param context
	 * @param title
	 * @param btnText	btntext1	btntext2
	 */
	public static void showSaveTwoPromptDialog(NormalDialog SaveTwoPrompt_Dialog, String title,
			String btntext1, String btntext2, OnBtnClickL... OnBtnClickLs) {
		SaveTwoPrompt_Dialog.content(title)
                .style(NormalDialog.STYLE_TWO)
                .btnText(btntext1, btntext2)
                .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#383838"))
				.show();
		SaveTwoPrompt_Dialog.setCanceledOnTouchOutside(false);
		SaveTwoPrompt_Dialog.setOnBtnClickL(OnBtnClickLs);
	}
}
