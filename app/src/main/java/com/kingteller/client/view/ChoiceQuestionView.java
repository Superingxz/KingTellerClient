package com.kingteller.client.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.adapter.DxXzAdapter;
import com.kingteller.client.adapter.MxXzAdapter;
import com.kingteller.client.bean.pxkh.XzTkParamBean;

@SuppressLint("ResourceAsColor")
public class ChoiceQuestionView extends GroupViewBase{

	private TextView contentId;
	private TextView contentName;
	private ListView listView;
	private DxXzAdapter dxXzAdapter;
	private MxXzAdapter mxXzAdapter;

	public ChoiceQuestionView(Context context) {
		super(context);
		
	}

	@Override
	protected void initView() {

		LayoutInflater.from(getContext()).inflate(R.layout.item_add_choice,this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		setLayoutParams(lp);

		contentId = (TextView) findViewById(R.id.contentId);
		contentName = (TextView) findViewById(R.id.contentName);
		listView = (ListView) findViewById(R.id.listView2);
	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setData(XzTkParamBean data, int numb) {

		if (data.getQuest().getType().equals("1")) {
			contentId.setText(data.getQuest().getId());
			contentName.setText(numb + "." + data.getQuest().getContent());
			dxXzAdapter = new DxXzAdapter(getContext(), data.getAnswer());
			listView.setAdapter(dxXzAdapter);
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			dxXzAdapter.notifyDataSetChanged();

		} else if (data.getQuest().getType().contains("2")) {
			contentId.setText(data.getQuest().getId());
			contentName.setText(numb + "." + data.getQuest().getContent());
			mxXzAdapter = new MxXzAdapter(getContext(), data.getAnswer());
			listView.setAdapter(mxXzAdapter);
		}
		
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}
}
