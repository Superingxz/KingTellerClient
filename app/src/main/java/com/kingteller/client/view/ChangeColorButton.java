package com.kingteller.client.view;

import com.kingteller.R;
import com.kingteller.client.utils.KingTellerJudgeUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChangeColorButton extends LinearLayout implements OnClickListener {
	private Drawable drawable;
	private Drawable hintDrawable;
	private String hintString;
	private int hintTextColor;
	private View icon;
	private Context mContext;
	private OnClickListener mListener;
	private int textColor;
	private TextView txt;

	public ChangeColorButton(Context paramContext) {
		super(paramContext);
		mContext = paramContext;
		initView(paramContext);
	}

	public ChangeColorButton(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		mContext = paramContext;
		TypedArray localTypedArray = paramContext.obtainStyledAttributes(
				paramAttributeSet, R.styleable.change_color_button);
		hintString = localTypedArray
				.getString(R.styleable.change_color_button_hintString);

		if (KingTellerJudgeUtils.isEmpty(hintString))
			hintString = "请输入...";
		
		textColor = localTypedArray.getInt(
				R.styleable.change_color_button_textColor, R.color.black);
		hintTextColor = localTypedArray.getInt(
				R.styleable.change_color_button_hintTextColor,
				R.color.text_666666);
		drawable = localTypedArray
				.getDrawable(R.styleable.change_color_button_drawable);
		hintDrawable = localTypedArray
				.getDrawable(R.styleable.change_color_button_hintDrawable);
		localTypedArray.recycle();
		initView(paramContext);
	}

	private void initView(Context paramContext) {
		LayoutInflater.from(mContext).inflate(R.layout.change_color_button,
				this);
		txt = ((TextView) findViewById(R.id.txt));
		icon = findViewById(R.id.icon);
		txt.setHint(hintString);
		txt.setTextColor(getResources().getColor(hintTextColor));
		icon.setBackgroundDrawable(hintDrawable);
	}

	public void onClick(View paramView) {
		if (mListener != null)
			mListener.onClick(paramView);
	}

	public void setOnClickListener(View.OnClickListener paramOnClickListener) {
		mListener = paramOnClickListener;
		super.setOnClickListener(this);
	}

	public void setText(CharSequence paramCharSequence) {
		if (KingTellerJudgeUtils.isEmpty(paramCharSequence.toString())) {
			txt.setTextColor(getResources().getColor(hintTextColor));
			icon.setBackgroundDrawable(hintDrawable);
			txt.setText(hintString);
		} else {
			txt.setTextColor(getResources().getColor(textColor));
			icon.setBackgroundDrawable(drawable);
			txt.setText(paramCharSequence);
		}
	}
}