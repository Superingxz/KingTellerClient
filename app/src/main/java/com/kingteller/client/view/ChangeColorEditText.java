package com.kingteller.client.view;

import com.kingteller.R;
import com.kingteller.client.utils.KingTellerJudgeUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ChangeColorEditText extends LinearLayout implements TextWatcher {
	private Drawable drawable;
	private EditText editText;
	private Drawable hintDrawable;
	private String hintString;
	private int hintTextColor;
	private View icon;
	private Context mContext;
	private int selectionEnd;
	private int selectionStart;
	private int textColor;
	private boolean textEnabled;
	private OnTextChangeListener onTextChangeListener;

	public ChangeColorEditText(Context context) {
		super(context);
		mContext = context;
		initView(context);
	}

	public ChangeColorEditText(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		mContext = paramContext;
		TypedArray typedArray = paramContext.obtainStyledAttributes(
				paramAttributeSet, R.styleable.change_color_button);;
		hintString = typedArray
				.getString(R.styleable.change_color_button_hintString);

		if (KingTellerJudgeUtils.isEmpty(hintString))
			hintString = "请输入...";
		textColor = typedArray.getInt(
				R.styleable.change_color_button_textColor, R.color.black);
		hintTextColor = typedArray.getInt(
				R.styleable.change_color_button_hintTextColor,
				R.color.text_666666);
		drawable = typedArray
				.getDrawable(R.styleable.change_color_button_drawable);
		hintDrawable = typedArray
				.getDrawable(R.styleable.change_color_button_hintDrawable);
		textEnabled = typedArray.getBoolean(
				R.styleable.change_color_button_textEnabled, true);
		typedArray.recycle();
		initView(paramContext);
	}

	private void initView(Context context) {
		LayoutInflater.from(mContext).inflate(R.layout.change_color_edittext,
				this);
		editText = (EditText) findViewById(R.id.edittext);
		icon = findViewById(R.id.icons);
		editText.setHint(hintString);
		editText.setTextColor(getResources().getColor(hintTextColor));
		if (hintDrawable != null)
			icon.setBackgroundDrawable(hintDrawable);
		else
			icon.setVisibility(View.GONE);
		editText.addTextChangedListener(this);
		if (!textEnabled)
			editText.setTextColor(getResources()
					.getColor(hintTextColor));

		editText.setEnabled(textEnabled);
	}

	@Override
	public void afterTextChanged(Editable paramEditable) {
		if (paramEditable.toString().equals("")) {
			icon.setBackgroundDrawable(hintDrawable);
			editText.setHint(hintString);
		} else {

			icon.setBackgroundDrawable(drawable);
			selectionStart = editText.getSelectionStart();
			selectionEnd = editText.getSelectionEnd();
			editText.setSelection(selectionEnd);

			if (!textEnabled)
				editText.setTextColor(getResources().getColor(hintTextColor));
			else
				editText.setTextColor(getResources().getColor(textColor));
		}

	}

	public String getText() {
		return editText.getText().toString();
	}

	public void setInputType(int type) {
		editText.setInputType(type);
	}

	public void setMaxLength(int paramInt) {
		EditText localEditText = editText;
		InputFilter[] arrayOfInputFilter = new InputFilter[1];
		arrayOfInputFilter[0] = new InputFilter.LengthFilter(paramInt);
		localEditText.setFilters(arrayOfInputFilter);
	}

	public void setText(String paramString) {
		editText.setText(paramString);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if (onTextChangeListener != null) {
			onTextChangeListener.onChange();
		}
	}

	public void setOnTextChangeListener(
			OnTextChangeListener onTextChangeListener) {
		this.onTextChangeListener = onTextChangeListener;
	}

	public interface OnTextChangeListener {
		public void onChange();
	}
}
