package com.kingteller.client.utils;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.kingteller.client.bean.common.CommonSelectGZBJ;

@SuppressWarnings("rawtypes")
public class SortByPinyinGZ implements Comparator {

	Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
	@Override
	public int compare(Object obj1, Object obj2) {
		// TODO Auto-generated method stub
		if (obj1.getClass().getName()
				.equals("com.kingteller.client.bean.common.CommonSelectGZBJ")) {
			String s1 = ((CommonSelectGZBJ) obj1).getText();
			String s2 = ((CommonSelectGZBJ) obj2).getText();
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
	public static List<CommonSelectGZBJ> sort(List<CommonSelectGZBJ> list) {
		SortByPinyinGZ comp = new SortByPinyinGZ();
		Collections.sort(list, comp);
		return list;
	}


	
}
