package com.kingteller.client.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.pxkh.XzTkParamBean;

@SuppressLint("ResourceAsColor")
public class SingleChoiceQuestionView extends GroupViewBase {

	private TextView contentName;
	private RadioGroup radioGroup;

	public SingleChoiceQuestionView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_singlechoice, this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					200);
			lp.setMargins(0, 10, 0, 0);
			setLayoutParams(lp);	
			
			contentName = (TextView) findViewById(R.id.contentName);
			radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
			
	}
	
	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}


	public void setData(XzTkParamBean data, int numb) {

		contentName.setText(numb + "." + data.getQuest().getContent());
		
		RadioButton radioButton;
		for (int i = 0; i < data.getAnswer().size(); i++) {
			
			LayoutInflater.from(getContext()).inflate(R.layout.item_dxxz_st,this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(0, 10, 0, 0);
			radioButton = new RadioButton(getContext());
			radioButton.setLayoutParams(lp);
			radioButton.setText(data.getAnswer().get(i).getContent());
			radioGroup.addView(radioButton);
			
			/*radioButton = new RadioButton(getContext());
			radioButton.setButtonDrawable(R.drawable.common_radiobutton_bg);
			radioButton.setBackgroundColor(R.color.bg);
			radioButton.setPadding(R.dimen.common_padding,
					R.dimen.common_padding, R.dimen.common_padding,
					R.dimen.common_padding);
			radioButton.setText(data.getAnswer().get(i).getContent());
			radioGroup.addView(radioButton,
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);*/
		}
	}
	
	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

}

