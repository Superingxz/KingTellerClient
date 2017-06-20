package com.kingteller.client.activity.qrcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.QRTraceBackToShowAdapter;
import com.kingteller.client.bean.qrcode.QRTraceBackToBean;
import com.kingteller.client.bean.qrcode.QRTraceBackToSjBean;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.NavigationSideBar;
import com.kingteller.client.view.NavigationSideBar.OnLetterSelectedListener;


public class QRTraceBackToShowActivity extends Activity implements OnClickListener{
	private TextView title_text, mNavigationText;
	private Button title_left_btn;
	private Context mContext;
	private NavigationSideBar mNavigationSideBar;
	private ListView mNavigationListView;
	private QRTraceBackToBean mTraceBackToBean;
	private String chars [];
	private QRTraceBackToShowAdapter adapter;
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_qrcode_trace_backto_show);
		KingTellerApplication.addActivity(this);
		
		mContext = QRTraceBackToShowActivity.this;
		
		initUI();
		initData();
		getmData();
	}
	
	public void initUI(){
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		mNavigationListView = (ListView) findViewById(R.id.navigation_list_view);
		
//		mNavigationText = (TextView) findViewById(R.id.navigation_title_text);
//		mNavigationSideBar = (NavigationSideBar) findViewById(R.id.navigation_side_bar);
//		mNavigationSideBar.setmTextView(mNavigationText);
//		mNavigationSideBar.setLetterSelectedListener(letterSelectedListener);
		
		adapter = new QRTraceBackToShowAdapter(this, new QRTraceBackToBean());
		mNavigationListView.setAdapter(adapter);
		
	}
	
	public void initData(){
		title_text.setText("二维码历史信息");
	}
	
	private void getmData(){
		
		mTraceBackToBean = (QRTraceBackToBean) getIntent().getSerializableExtra("TraceBackToBean"); 
		
		//事件排序
//		sortClass sort = new sortClass();  
//        Collections.sort(mTraceBackToBean.getRecordList(), sort);  
//        
//        for(int i=0;i<data.getSjList().size();i++){  
//            Log.e("日期","生日:"+data.getSjList().get(i).getOperatTime());  
//        }  
//        
//        chars = getyear(mTraceBackToBean.getRecordList());
//		mNavigationSideBar.setSideBarStr(chars);
		
		adapter.setLists(mTraceBackToBean);
    }
	
	//获取所有年份 
	public String[] getyear(List<QRTraceBackToSjBean> sjlist){ 
		Set<Integer> set = new HashSet<Integer>();     
		for(int i = 0; i<sjlist.size(); i++){
			String time = sjlist.get(i).getCreateDateStr();
			int year = Integer.parseInt(KingTellerTimeUtils.getYearMonthDay(time, 0, 0)); 
			set.add(year);     
		}
		Iterator<Integer> i = set.iterator();     
		Integer[] int_array = new Integer[set.size()];     
        int num = 0;     
        while(i.hasNext()){     
            int a = (Integer)i.next();     
            int_array[num] = a;     
            num = num + 1;      
        }  
        
        //年份排序
        Arrays.sort(int_array, Collections.reverseOrder());
        
//        for(int l=0;l<set.size();l++){  
//            Log.e("年","年:"+ int_array[l]);  
//        }  
       

        String [] str_array = new String[set.size() + 1];     
        
        for(int s = 0; s < str_array.length ; s++){      
        	if(s == 0){
        		str_array[s] = "置顶";
        	}else{
        		str_array[s] = String.valueOf(int_array[s -1]);
        	}
         }   
        
        return str_array;
	}
	
	//比较器
	public class sortClass implements Comparator<Object>{ 
	    public int compare(Object arg0,Object arg1){  
	    	QRTraceBackToSjBean sj0 = (QRTraceBackToSjBean)arg0;  
	    	QRTraceBackToSjBean sj1 = (QRTraceBackToSjBean)arg1;  
	        int flag = sj1.getCreateDateStr().compareTo(sj0.getCreateDateStr());  
	        return flag;  
	    }  
	}  
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				finish();
				break;
			default:
				break;
		}
		
	}
	
	private OnLetterSelectedListener letterSelectedListener = new OnLetterSelectedListener() {
		
		@Override
		public void onLetterSelected(String s) {
			int position = 0;
			if(s.equals("置顶")){
				position = 0;
			}else{
				position = adapter.getPositionBySelection(Integer.parseInt(s)) + 1;
			}
			
			mNavigationListView.setSelection(position);
		}
	};
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
