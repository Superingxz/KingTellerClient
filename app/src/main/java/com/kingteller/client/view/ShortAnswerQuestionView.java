package com.kingteller.client.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.pxkh.QuestionParamBean;

public class ShortAnswerQuestionView extends GroupViewBase{

	private TextView shortAnswerId;
	private TextView contentName;
	private EditText saEditText;
	
	public ShortAnswerQuestionView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_shortanswer, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		setLayoutParams(lp);
		
		shortAnswerId = (TextView) findViewById(R.id.shortAnswerId);
		contentName = (TextView) findViewById(R.id.contentName);
		saEditText = (EditText) findViewById(R.id.saEditText);
	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setData(QuestionParamBean data,int numb){
		shortAnswerId.setText(data.getId());
		contentName.setText(numb+"."+data.getContent());
	}
	
}
