package com.kingteller.client.view.groupview.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.R;

public class CommonGroupListView extends LinearLayout implements
		View.OnClickListener {

	private AddViewCallBack addViewCallBack;
	private LinearLayout layout_list;
    private Button btn_add;
    private TextView text_add;


    /**
     * 添加视图回调接口
     */
    public interface AddViewCallBack {
        public void addView(CommonGroupListView view);
    }

    public void setAddViewCallBack(AddViewCallBack addViewCallBack) {
        this.addViewCallBack = addViewCallBack;
    }
	public CommonGroupListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public CommonGroupListView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.group_common_list_view, this);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 0);
		setLayoutParams(lp);

		layout_list = (LinearLayout) findViewById(R.id.layout_list);
        btn_add = (Button) findViewById(R.id.btn_add);
        text_add = (TextView) findViewById(R.id.add_workType);

        btn_add.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			if (addViewCallBack != null) {
				addViewCallBack.addView(CommonGroupListView.this);
			}
			break;
		default:
			break;
		}
	}

    /**
     * 设置Text说明
   */
    public void setText(String msg) {
        text_add.setText(msg);
    }

	/**
	 * 添加一个视图
	 */
	public void addItem(View view) {
		layout_list.addView(view);
	}

    /**
     * 删除一个视图
     */
    public void delItem(View view) {
        layout_list.removeView(view);
    }

    /**
     * 获取多少个视图
     */
    public int getItemNum() {
        return layout_list.getChildCount();
    }

    /**
     * 获取当前视图
     */
	public LinearLayout getItemView(int index){
		return (LinearLayout)layout_list.getChildAt(index);
	}

    /**
     * 获取视图list
     */
    public LinearLayout getLayoutList() {
        return layout_list;
    }


	
}
