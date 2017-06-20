package com.kingteller.client.utils;

import java.util.HashMap;

import com.google.gson.Gson;
import com.kingteller.client.view.ConditionView;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.framework.condition.Condition;
import com.kingteller.framework.condition.FieldCond;
import com.kingteller.framework.http.AjaxParams;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 条件处理类
 * 
 * @author 王定波
 * 
 */
public class ConditionUtils {
	/**
	 * 收集条件
	 * 
	 * @param context
	 * @param ly 获取相应的线性布局
	 * @return
	 */
	public static Condition getCondition(Context context, LinearLayout ly) {
		int length = ly.getChildCount();
		Condition condition = new Condition();

		for (int i = 0; i < length; i++) {
			if (ly.getChildAt(i).getClass().getName()
					.equals(ConditionView.class.getName())) {
				ConditionView view = (ConditionView) ly.getChildAt(i);
				if (view.isFieldRequested()) {
					Toast.makeText(context, view.getFieldTitle() + ":必须填写",
							Toast.LENGTH_SHORT).show();
					return null;
				}

				if (!KingTellerJudgeUtils.isEmpty(view.getFieldValue())) {
					if (!view.checkDataType()) {
						Toast.makeText(context,
								view.getFieldTitle() + ":数据类型不正确",
								Toast.LENGTH_SHORT).show();
						return null;
					}
				}

				if (!KingTellerJudgeUtils.isEmpty(view.getFieldValue())) {
					FieldCond fieldCond = new FieldCond(view.getFieldName(),
							view.getFieldType(), view.getFieldOp(),
							view.getFieldValue());
					condition.add(fieldCond);
				}
			}
		}

		condition.setPageSize(10);//设置每页返回的默认数据
		return condition;

	}

	/**
	 * 获取post参数
	 * 
	 * @param condition
	 * @param currpage 设置当前页
	 * @return
	 */
	public static AjaxParams getAjaxParams(Condition condition, int currpage) {
		AjaxParams param = new AjaxParams();
		if (condition != null) {
			condition.setCurrentPage(currpage);
			param.put("condition", condition.getOriCondString());
		}
		return param;
	}

	/**
	 * 获取post参数
	 * @param context
	 * @param ly
	 * @param ischeck 是否检测输入
	 * @return
	 */
	public static AjaxParams getFormParams(Context context, LinearLayout ly,
			boolean ischeck) {
		int length = ly.getChildCount();
		AjaxParams param = new AjaxParams();
		for (int i = 0; i < length; i++) {
			if (ly.getChildAt(i).getClass().getName()
					.equals(KingTellerEditText.class.getName())) {

				KingTellerEditText view = (KingTellerEditText) ly.getChildAt(i);
				if (view.getVisibility() == View.VISIBLE) {
					if (ischeck) {
						if (view.isFieldRequested()) {
							Toast.makeText(context,
									view.getFieldTitle() + ":必须填写",
									Toast.LENGTH_SHORT).show();
							return null;
						}

						if (!KingTellerJudgeUtils.isEmpty(view.getFieldValue())) {
							if (!view.checkDataType()) {
								Toast.makeText(context,
										view.getFieldTitle() + ":数据类型不正确",
										Toast.LENGTH_SHORT).show();
								return null;
							}
						}
					}

					if (!KingTellerJudgeUtils.isEmpty(view.getFieldName()))
						param.put(view.getFieldName(), view.getFieldValue());
				}
			}
		}

		return param;

	}
	
	
	/**
	 * 获取post参数
	 * @param context
	 * @param ly
	 * @param ischeck 是否检测输入
	 * @return
	 */
	public static HashMap<String, Object> getHashMapForm(Context context, LinearLayout ly,
			boolean ischeck) {
		int length = ly.getChildCount();
		HashMap<String, Object> params=new HashMap<String, Object>();
		
		for (int i = 0; i < length; i++) {
			if (ly.getChildAt(i).getClass().getName()
					.equals(KingTellerEditText.class.getName())) {

				KingTellerEditText view = (KingTellerEditText) ly.getChildAt(i);
				if (view.getVisibility() == View.VISIBLE) {
					if (ischeck) {
						if (view.isFieldRequested()) {
							Toast.makeText(context,
									view.getFieldTitle() + ":必须填写",
									Toast.LENGTH_SHORT).show();
							return null;
						}

						if (!KingTellerJudgeUtils.isEmpty(view.getFieldValue())) {
							if (!view.checkDataType()) {
								Toast.makeText(context,
										view.getFieldTitle() + ":数据类型不正确",
										Toast.LENGTH_SHORT).show();
								return null;
							}
						}
					}

					if (!KingTellerJudgeUtils.isEmpty(view.getFieldName()))
						params.put(view.getFieldName(), view.getFieldValue());
				}
			}
		}

		return params;
	}
	
	
	public static HashMap<String, Object> getHashMapFormAllData(Context context, LinearLayout ly,
			boolean ischeck) {
		int length = ly.getChildCount();
		HashMap<String, Object> params=new HashMap<String, Object>();
		
		for (int i = 0; i < length; i++) {
			if (ly.getChildAt(i).getClass().getName()
					.equals(KingTellerEditText.class.getName())) {

				KingTellerEditText view = (KingTellerEditText) ly.getChildAt(i);
				if (view.getVisibility() == View.VISIBLE) {
					if (ischeck) {
						if (view.isFieldRequested()) {
							Toast.makeText(context,
									view.getFieldTitle() + ":必须填写",
									Toast.LENGTH_SHORT).show();
							return null;
						}

						if (!KingTellerJudgeUtils.isEmpty(view.getFieldValue())) {
							if (!view.checkDataType()) {
								Toast.makeText(context,
										view.getFieldTitle() + ":数据类型不正确",
										Toast.LENGTH_SHORT).show();
								return null;
							}
						}
					}
					params.put(view.getFieldName(), view.getFieldValue());
				}
			}
		}

		return params;
	}
	
	/**
	 * 生成json
	 * @param map
	 * @return
	 */
	public static String getJsonFromHashMap(HashMap<String, Object> map)
	{
		Gson gson=new Gson();
		return gson.toJson(map);
	}
	
	/**
	 * 生成json
	 * @param map
	 * @return
	 */
	public static String getJsonFromObj(Object obj)
	{
		Gson gson=new Gson();
		return gson.toJson(obj);
	}

}
