package com.kingteller.client.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.R;

public class KingTellerTextView extends LinearLayout {
	private TextView editText;
	private Context mContext;
	private TextView title;

	private String fieldValue;
	private String fieldTitle;

	public KingTellerTextView(Context context) {
		super(context);
		mContext = context;
		initView(context);
	}

	public KingTellerTextView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		mContext = context;
		TypedArray typedArray = context.obtainStyledAttributes(attributeSet,
				R.styleable.kingteller_edittext);

		fieldValue = typedArray
				.getString(R.styleable.kingteller_edittext_fieldValue);
		fieldTitle = typedArray
				.getString(R.styleable.kingteller_edittext_fieldTitle);

		typedArray.recycle();
		initView(context);
	}

	private void initView(Context context) {
		LayoutInflater.from(mContext).inflate(R.layout.kingteller_textview,
				this);
		editText = (TextView) findViewById(R.id.edittext);
		title = (TextView) findViewById(R.id.title);

		editText.setText(fieldValue);
		title.setText(fieldTitle);
	}

	public String getText() {
		return editText.getText().toString();
	}

	public String getTitle() {
		return title.getText().toString();
	}

	public void setText(String paramString) {
		editText.setText(paramString);
	}

	public void setTitle(String paramString) {
		title.setText(paramString);
	}

}
