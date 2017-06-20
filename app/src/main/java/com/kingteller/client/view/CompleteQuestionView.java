package com.kingteller.client.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.adapter.TktAdapter;
import com.kingteller.client.bean.pxkh.QuestionParamBean;

public class CompleteQuestionView extends GroupViewBase{

	private TextView contentName;
	private ListView listView;
	private TktAdapter adapter;
	
	public CompleteQuestionView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_complete, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		setLayoutParams(lp);
		
		contentName = (TextView) findViewById(R.id.contentName);
		listView = (ListView) findViewById(R.id.listView);
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
		
		String strOrg = data.getContent().replace("$$$", "$$");
		String[] strArr = strOrg.split("\\$\\$");
		
		StringBuffer strBuffer = new StringBuffer();
		for(int i = 0; i < strArr.length;i++){
			if(strArr[i].equals("")){
				strBuffer.append("____"+"("+(i+1)+")"+"____");
			}else{
				strBuffer.append(strArr[i]);
				if(i != strArr.length-1){
					strBuffer.append("____"+"("+(i+1)+")"+"____");
				}
				
				if( i == strArr.length -1){
					if(strOrg.endsWith("$$")){
						strBuffer.append("____"+"("+(i+1)+")"+"____");
					}
				}
			}
		}
		contentName.setText(numb+"."+strBuffer.toString());
		int num = 0;
		
		String str = data.getContent().replace("$$$", "$$");
		String[] strArr1 = str.split("\\$\\$");
		
		if( strArr1[0].equals("") && str.endsWith("$$")){
			num = strArr1.length ;
		}else if( strArr1[0].equals("") && !str.endsWith("$$")){
			num = strArr1.length - 1;
		}else if(!strArr1[0].equals("") && str.endsWith("$$")){
			num = strArr1.length ;
		}else{
			num = strArr1.length - 1;
		}
		
		adapter = new TktAdapter(getContext(),data, num);
		listView.setAdapter(adapter);
	}
	
}
