package com.kingteller.client.view;

import com.kingteller.R;
import com.kingteller.client.utils.KingTellerImageUtils;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;


/**
 * 自定义Tabview
 * @author 王定波
 *
 */
public class TabView extends LinearLayout {
	private OnItemTabListener mListener;
	private LinearLayout radio_group;
	private int dividerColor;
	private int dividerHeight;
	private boolean isTop;
	private View line_bottom;
	private View line_top;
	private BaseAdapter adapter;

	public TabView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		TypedArray typedArray = context.obtainStyledAttributes(attributeSet,
				R.styleable.base_tab);

		dividerColor = typedArray.getColor(R.styleable.base_tab_dividerColor,
				getResources().getColor(R.color.toolbar_divide));
		dividerHeight = typedArray
				.getInt(R.styleable.base_tab_dividerHeight, 1);

		isTop = typedArray.getBoolean(R.styleable.base_tab_isTop, false);

		initView();
	}

	private void initView() {
		inflate(getContext(), R.layout.custom_tab_btn, this);
		radio_group = (LinearLayout) findViewById(R.id.radio_group);
		line_bottom = (View) findViewById(R.id.line_bottom);
		line_top = (View) findViewById(R.id.line_top);
		if (isTop) {
			line_bottom.setVisibility(View.VISIBLE);
			line_top.setVisibility(View.GONE);
			
		} else {
			line_top.setVisibility(View.VISIBLE);
			line_bottom.setVisibility(View.GONE);
		}
		
		line_bottom.setBackgroundColor(dividerColor);
		line_top.setBackgroundColor(dividerColor);
		setOrientation(LinearLayout.VERTICAL);
	}

	public void setAdapter(BaseAdapter adapter) {
		radio_group.removeAllViews();
		this.adapter = adapter;

		AdapterDataSetObserver mDataSetObserver = new AdapterDataSetObserver();
		adapter.registerDataSetObserver(mDataSetObserver);

		updateUI();
	}

	public void setOnItemListener(OnItemTabListener paramOnChengedListener) {
		mListener = paramOnChengedListener;
	}

	public static abstract interface OnItemTabListener {
		public abstract void OnItemClick(int pos);
	}

	private void updateUI() {
		radio_group.removeAllViews();

		for (int i = 0; i < adapter.getCount(); i++) {
			View view = adapter.getView(i, null, TabView.this);

			/*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.FILL_PARENT, 1);*/
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT, 1);
			lp.gravity = Gravity.CENTER;
			view.setLayoutParams(lp);
			//view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT,1));
			view.setId(i);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mListener!=null)
					mListener.OnItemClick(v.getId());
				}
			});

			radio_group.addView(view);
			if (adapter.getCount() - 1 > i && dividerHeight > 0) {
				View divider = new View(getContext());
				LinearLayout.LayoutParams lpd = new LinearLayout.LayoutParams(
						KingTellerImageUtils.dp2px(getContext(), dividerHeight),
						LinearLayout.LayoutParams.FILL_PARENT);
				divider.setLayoutParams(lpd);
				divider.setBackgroundColor(dividerColor);
				radio_group.addView(divider);
			}
		}
	}

	private class AdapterDataSetObserver extends DataSetObserver {
		@Override
		public void onChanged() {
			// TODO Auto-generated method stub
			super.onChanged();
			updateUI();

		}
	}

}