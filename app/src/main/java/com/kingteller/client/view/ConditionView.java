package com.kingteller.client.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.KingTellerEditText.OnChangeListener;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;
import com.kingteller.client.view.calendar.CalendarDay;
import com.kingteller.client.view.datatype.DataTypes;
import com.kingteller.client.view.dialog.NormalListDialog;
import com.kingteller.client.view.dialog.listener.OnBtnSelectDateAndTimeClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSelectDateClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSelectTimeClickL;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;
import com.kingteller.client.view.dialog.use.KingTellerDateTimeDialogUtils;
import com.kingteller.framework.condition.FieldCond;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 条件通用View
 * 
 * @author 王定波
 * 
 */
public class ConditionView extends LinearLayout implements
		android.view.View.OnClickListener {

	private Context mContext;
	private String fieldName;
	private String fieldValue;
	private String fieldTitle;

	private boolean fieldRequested;
	private int fieldType;
	private int fieldOp;
	private TextView title;
	private EditText edittext;
	private TextView textview;
	private String fieldHint;
	private String fieldText;

	public final static int QUERY_TYPE_EQ = 0;
	public final static int QUERY_TYPE_LIKE = 1;
	public final static int EQ = 2;
	public final static int NOT_EQ = 3;
	public final static int LESS_THAN = 4;
	public final static int LESS_EQ_THAN = 5;
	public final static int LARGE_THAN = 6;
	public final static int LARGE_EQ_THAN = 7;
	public final static int LARGE_LESS = 8;
	public final static int LARGE_LESS_EQ = 9;
	public final static int LIKE = 10;
	public final static int LLIKE = 11;
	public final static int RLIKE = 12;
	public final static int ISNOT = 13;
	public final static int IN = 14;

	private String[] ConditionOP = { FieldCond.QUERY_TYPE_EQ,
			FieldCond.QUERY_TYPE_LIKE, FieldCond.EQ, FieldCond.NOT_EQ,
			FieldCond.LESS_THAN, FieldCond.LESS_EQ_THAN, FieldCond.LARGE_THAN,
			FieldCond.LARGE_EQ_THAN, FieldCond.LARGE_LESS,
			FieldCond.LARGE_LESS_EQ, FieldCond.LIKE, FieldCond.LLIKE,
			FieldCond.RLIKE, FieldCond.ISNOT, FieldCond.IN };

	private int[] ConditionType = {
			com.kingteller.framework.condition.DataTypes.VARCHAR,
			com.kingteller.framework.condition.DataTypes.NUMBER,
			com.kingteller.framework.condition.DataTypes.DATE,
			com.kingteller.framework.condition.DataTypes.DATE,
			com.kingteller.framework.condition.DataTypes.TIME,
			com.kingteller.framework.condition.DataTypes.VARCHAR,
			com.kingteller.framework.condition.DataTypes.VARCHAR };

	private List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
	private int fieldBindData;
	private KTAlertDialog dialog;
	private boolean fieldEnabled;
	private OnChangeListener onChangeListener;
	private OnDialogClickLister onDialogClickLister;

	private Calendar calendar = null;
	
	public ConditionView(Context context) {
		super(context);
		mContext = context;
		initView(context);
	}

	public ConditionView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		mContext = context;
		TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.kingteller_edittext);
		fieldName = typedArray.getString(R.styleable.kingteller_edittext_fieldName);
		fieldValue = typedArray.getString(R.styleable.kingteller_edittext_fieldValue);
		fieldTitle = typedArray.getString(R.styleable.kingteller_edittext_fieldTitle);
		fieldText = typedArray.getString(R.styleable.kingteller_edittext_fieldText);

		fieldRequested = typedArray.getBoolean(R.styleable.kingteller_edittext_fieldRequested, false);
		fieldType = typedArray.getInt(R.styleable.kingteller_edittext_fieldType, DataTypes.String);
		fieldOp = typedArray.getInt(R.styleable.kingteller_edittext_fieldOp,fieldType == DataTypes.String ? LIKE : EQ);
		fieldHint = typedArray.getString(R.styleable.kingteller_edittext_fieldHint);
		fieldBindData = typedArray.getResourceId(R.styleable.kingteller_edittext_fieldBindData, 0);
		fieldEnabled = typedArray.getBoolean(R.styleable.kingteller_edittext_fieldEnabled, true);

		if (!KingTellerJudgeUtils.isEmpty(fieldText) && KingTellerJudgeUtils.isEmpty(fieldValue)){
			fieldValue = fieldText;
		}else if (!KingTellerJudgeUtils.isEmpty(fieldValue) && KingTellerJudgeUtils.isEmpty(fieldText)){
			fieldText = fieldValue;
		}

		if (fieldBindData != 0) {
			setLists(getResources().getStringArray(fieldBindData));
		}

		typedArray.recycle();
		initView(context);
	}

	private void initView(Context context) {
		LayoutInflater.from(mContext).inflate(R.layout.kingteller_edittext, this);
		title = (TextView) findViewById(R.id.title);
		edittext = (EditText) findViewById(R.id.edittext);
		textview = (TextView) findViewById(R.id.textview);
		setFieldEnabled(fieldEnabled);

		if (!KingTellerJudgeUtils.isEmpty(fieldTitle)){
			title.setText(fieldTitle);
		}
		
		if (!KingTellerJudgeUtils.isEmpty(fieldText)){
			setFieldTextAndValue(fieldText, fieldValue);
		}
			
		if (fieldType > DataTypes.Number) {
			edittext.setVisibility(View.GONE);
			textview.setVisibility(View.VISIBLE);
		} else {
			edittext.setVisibility(View.VISIBLE);
			textview.setVisibility(View.GONE);
		}

		if (!KingTellerJudgeUtils.isEmpty(fieldHint)){
			edittext.setHint(fieldHint);
		}

		textview.setOnClickListener(this);
		
		if (fieldRequested){
			title.setTextColor(getResources().getColor(R.color.orange));
		}

	}

	public boolean isFieldEnabled() {
		return fieldEnabled;
	}

	public void setFieldEnabled(boolean fieldEnabled) {
		this.fieldEnabled = fieldEnabled;
		edittext.setEnabled(fieldEnabled);
		if (!fieldEnabled){
			edittext.setTextColor(getResources().getColor(R.color.gray));
		}else{
			edittext.setTextColor(getResources().getColor(R.color.black));
		}
	}

	public void setFocusEnabled(boolean fieldEnabled) {
		this.fieldEnabled = fieldEnabled;
		edittext.setEnabled(fieldEnabled);
		if (fieldEnabled){
			edittext.setTextColor(getResources().getColor(R.color.gray));
		}else{
			edittext.setTextColor(getResources().getColor(R.color.black));
		}
	}
	
	/**
	 * 设置value和text的值
	 * 
	 * @param text
	 * @param value
	 */
	public void setFieldTextAndValue(String text, String value) {
		if (fieldType > DataTypes.Number){
			textview.setText(text);
		}else{
			edittext.setText(text);
		}
		setFieldValue(value);
		setFieldText(text);
	}
	
	public void setFouces(boolean bool){
		edittext.setFocusable(bool);
		if (!KingTellerJudgeUtils.isEmpty(fieldHint)) {
			edittext.setHint("");
			textview.setHint("");
		}
	}

	/**
	 * 快速设置value和text的值
	 * 
	 * @param text
	 */
	public void setFieldTextAndValue(String text) {
		if (fieldType > DataTypes.Number){
			textview.setText(text);
		}else{
			edittext.setText(text);
		}
		setFieldValue(text);
		setFieldText(text);
	}

	/**
	 * 获取对应的操作
	 * 
	 * @return
	 */
	public String getFieldOp() {
		return ConditionOP[fieldOp];
	}

	public void setFieldOp(int fieldOp) {
		this.fieldOp = fieldOp;
	}

	public boolean isFieldRequested() {
		return fieldRequested;
	}

	public void setFieldRequested(boolean fieldRequested) {
		this.fieldRequested = fieldRequested;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getFieldType() {
		return ConditionType[fieldType - 1];
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldTitle() {
		return title.getText().toString();
	}

	public void setFieldTitle(String fieldTitle) {
		title.setText(fieldTitle);
	}

	public boolean checkDataType() {
		switch (fieldType) {
			case DataTypes.Number:
				if (KingTellerJudgeUtils.isNumber(getFieldValue())){
					return true;
				}else{
					return false;
				}
			default:
				return true;

		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.textview:
			switch (fieldType) {
			case DataTypes.DateTime:
				
				if(KingTellerJudgeUtils.isEmpty(getFieldValue())){
					 calendar = Calendar.getInstance();
				}else{
					 calendar = Calendar.getInstance();
					 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(getFieldValue(), 1));
				}
				
				KingTellerDateTimeDialogUtils.showDateAndTimePickerDialog(mContext, calendar, null, 
					new OnBtnSelectDateAndTimeClickL() {
		            	@Override
		                public void OnBtnSelectDateAndTimeClick(int year, int month, int day, int hour, int minute) {
		                	//取消
		            	}
				    },new OnBtnSelectDateAndTimeClickL() {
				        @Override
				        public void OnBtnSelectDateAndTimeClick(int year, int month, int day, int hour, int minute) {
				        	//确定
				        	String datetime = KingTellerTimeUtils.getConversionFormatStringByDate(CalendarDay.from(year, month, day).getDate(), 2) 
				        			+ " " + KingTellerTimeUtils.getConversionFormatTime(hour + ":" + minute, 1);
				        	setFieldTextAndValue(datetime);
				         }
				});
				
//				PickDialog datetimedialog = new PickDialog(getContext(),
//						new DateTimePickView.PickerDateTimeListener() {
//							public void onDateChanged(Date date) {
//								KingTellerDateTime kdate = new KingTellerDateTime()
//										.initNow();
//								kdate.parseDate(date);
//								setFieldTextAndValue(kdate.getDateTimeString());
//							}
//						}, "选择日期");
//				datetimedialog.show();
				break;
			case DataTypes.Date:
				
				if(KingTellerJudgeUtils.isEmpty(getFieldValue())){
					 calendar = Calendar.getInstance();
				}else{
					 calendar = Calendar.getInstance();
					 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(getFieldValue(), 2));
				}
				 
				KingTellerDateTimeDialogUtils.showDatePickerDialog(mContext, calendar,
						new OnBtnSelectDateClickL() {
	                    	@Override
		                    public void OnBtnSelectDateClick(DatePicker view, int year, int month, int day) {
		                    	//取消
		                    }
	                    },new OnBtnSelectDateClickL() {
		                    @Override
		                    public void OnBtnSelectDateClick(DatePicker view, int year, int month, int day) {
		                    	//确定
		                    	String date = KingTellerTimeUtils.getConversionFormatStringByDate(
		                    			CalendarDay.from(year, month, day).getDate(), 2);
		                    	setFieldTextAndValue(date);
		                    }
		        });
				
//				PickDialog datedialog = new PickDialog(getContext(),
//						new DatePickView.PickerDateListener() {
//							public void onDateChanged(Date date) {
//								KingTellerDateTime kdate = new KingTellerDateTime()
//										.initNow();
//								kdate.parseDate(date);
//								setFieldTextAndValue(kdate.getDateString());
//							}
//						}, "选择日期");
//				datedialog.show();
				break;
			case DataTypes.Time:
				
				if(KingTellerJudgeUtils.isEmpty(getFieldValue())){
					 calendar = Calendar.getInstance();
				 }else{
					 calendar = Calendar.getInstance();
					 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(getFieldValue(), 3));
				 }
				
				KingTellerDateTimeDialogUtils.showTimePickerDialog(mContext, calendar,  null,
						new OnBtnSelectTimeClickL() {
	                    	@Override
		                    public void OnBtnSelectTimeClick(TimePicker view, int hour, int minute) {
		                    	//取消
		                    }
	                    },new OnBtnSelectTimeClickL() {
		                    @Override
		                    public void OnBtnSelectTimeClick(TimePicker view, int hour, int minute) {
		                    	//确定
		                    	String time = KingTellerTimeUtils.getConversionFormatTime(hour + ":" + minute, 1);
		                    	setFieldTextAndValue(time);
		                    }
	                });
				
//				PickDialog startdialog = new PickDialog(getContext(),
//						new TimePickView.PickerTimeListener() {
//							public void onTimeChanged(int hour, int min) {
//								setFieldTextAndValue(hour + ":" + min);
//							}
//						}, "选择时间");
//
//				startdialog.show();
				break;
			case DataTypes.Select:
				
				String[] mbdlx_StringItem = new String[lists.size()];
				for(int i = 0; i < lists.size(); i++){
					mbdlx_StringItem [i] = lists.get(i).getText();
				}
				
				final NormalListDialog dialog_bdlx = new NormalListDialog(mContext, mbdlx_StringItem);
				dialog_bdlx.title(getFieldTitle())//
			                .layoutAnimation(null)
			                .titleBgColor(Color.parseColor("#409ED7"))//
			                .itemPressColor(Color.parseColor("#85D3EF"))//
			                .itemTextColor(Color.parseColor("#303030"))//
			                .show();
				dialog_bdlx.setOnOperItemClickL(new OnOperItemClickL() {
			            @Override
			            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
			            	dialog_bdlx.dismiss();
			            	
			            	CommonSelectData data = lists.get(position);
			            	setFieldTextAndValue(data.getText(),data.getValue());
			            	if (onChangeListener != null){
			            		onChangeListener.onChanged(data);
			            	}
			            }
			        });
				
//				SelectListAdapter adapter = new SelectListAdapter(getContext(), lists);
//				dialog = new KTAlertDialog.Builder(getContext())
//						.setTitle(getFieldTitle())
//						.setItems(adapter, 0, new OnItemClickListener() {
//							@Override
//							public void onItemClick(AdapterView<?> adapterView,
//									View arg1, int pos, long arg3) {
//								// TODO Auto-generated method stub
//								CommonSelectData data = (CommonSelectData) adapterView.getItemAtPosition(pos);
//								setFieldTextAndValue(data.getText(), data.getValue());
//								dialog.dismiss();
//								if (onChangeListener != null)
//									onChangeListener.onChanged(data);
//
//							}
//						})
//						.setNegativeButton("取消",
//								new KTAlertDialog.OnClickListener() {
//
//									@Override
//									public void onClick(
//											DialogInterface dialogInterface,
//											int pos) {
//										// TODO Auto-generated method stub
//										dialogInterface.dismiss();
//									}
//								}).create();
//				dialog.show();
				break;
			case DataTypes.Dialog:
				if (onDialogClickLister != null){
					onDialogClickLister.OnDialogClick();
				}
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
	}

	public String getFieldValue() {
		if (fieldType == DataTypes.String){
			return edittext.getText().toString().trim();
		}else{
			return fieldValue;
		}
	}

	private void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldText() {
		if (fieldType == DataTypes.String){
			return edittext.getText().toString().trim();
		}else{
			return fieldText;
		}
	}

	private void setFieldText(String fieldText) {
		this.fieldText = fieldText;
	}

	public void setLists(List<CommonSelectData> lists) {
		this.lists = lists;
	}

	public void setLists(String[] liststr) {
		lists = new ArrayList<CommonSelectData>();
		for (int i = 0; i < liststr.length; i++) {
			CommonSelectData data = new CommonSelectData();
			data.setText(liststr[i]);
			data.setValue(liststr[i]);
			lists.add(data);
		}
	}

	public void setOnChangeListener(OnChangeListener onChangeListener) {
		this.onChangeListener = onChangeListener;
	}

	public void setOnDialogClickLister(OnDialogClickLister onDialogClickLister) {
		this.onDialogClickLister = onDialogClickLister;
	}

}
