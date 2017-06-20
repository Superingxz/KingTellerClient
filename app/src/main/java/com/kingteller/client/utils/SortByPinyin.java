package com.kingteller.client.utils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.kingteller.client.bean.common.CommonSelectData;

@SuppressWarnings("rawtypes")
public class SortByPinyin implements Comparator {

	Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
	@Override
	public int compare(Object obj1, Object obj2) {
		// TODO Auto-generated method stub
		if (obj1.getClass().getName()
				.equals("com.kingteller.client.bean.common.CommonSelectData")) {
			String s1 = ((CommonSelectData) obj1).getText();
			String s2 = ((CommonSelectData) obj2).getText();
			if(s1.contains("其他") && (!s2.contains("其他"))){  //其它 其他 
				return -1;
			}else if(!s1.contains("其他") && (s2.contains("其他"))){
				return 1;
			}else{
				return -Collator.getInstance(Locale.CHINA).compare(s1, s2);
			}
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public static List<CommonSelectData> sort(List<CommonSelectData> list) {
		SortByPinyin comp = new SortByPinyin();
		Collections.sort(list, comp);
		return list;
	}

	
	public static List<CommonSelectData> dxSort(List<CommonSelectData> list) {
		List<CommonSelectData> hanziList = new ArrayList<CommonSelectData>();
		List<CommonSelectData> qitaList = new ArrayList<CommonSelectData>();
		List<CommonSelectData> zimuList = new ArrayList<CommonSelectData>();
		List<CommonSelectData> shuziList = new ArrayList<CommonSelectData>();
		List<CommonSelectData> listsort = new ArrayList<CommonSelectData>();
		
		for(Iterator<CommonSelectData> i=list.iterator();i.hasNext();) {
			CommonSelectData str = (CommonSelectData)i.next();
			if(str.getText().contains("其他")){  //其它 其他 
				qitaList.add(str);
			}else if(str.getText().matches("^[0-9].*")) {
				shuziList.add(str);
			}else if(str.getText().matches("^[A-Za-z].*")){
				zimuList.add(str);
			}else {
				hanziList.add(str);
			}
		}
		getSortList(qitaList);
		getSortList(hanziList);
		getSortList(zimuList);
		getSortList(shuziList);
		
		for(CommonSelectData str : qitaList){
			listsort.add(str);
		}
		for(CommonSelectData str : hanziList){
			listsort.add(str);
		}
		for(CommonSelectData str : zimuList){
			listsort.add(str);
		}
		for(CommonSelectData str : shuziList){
			listsort.add(str);
		}
		return listsort;
	}
	
	public static List<CommonSelectData> getSortList(List<CommonSelectData> list){
		SortByPinyin sbp = new SortByPinyin();
		for(int i = 0;i < list.size();i ++){
			for(int j = i +1;j < list.size(); j ++){
				CommonSelectData s1 = ((CommonSelectData) list.get(i));
				CommonSelectData s2 = ((CommonSelectData) list.get(j));
				sbp.compare(s1, s2);
			}
		}
		return list;
	}
	
}
