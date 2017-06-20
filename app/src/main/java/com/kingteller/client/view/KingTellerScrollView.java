package com.kingteller.client.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 自定义滚动视图
 * @author 王定波
 *
 */
public class KingTellerScrollView extends ScrollView {

	private ScrollViewListener scrollViewListener = null;

	public KingTellerScrollView(Context context) {
		super(context);
	}

	public KingTellerScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public KingTellerScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}

	public interface ScrollViewListener {
		public void onScrollChanged(KingTellerScrollView scrollView, int x,
				int y, int oldx, int oldy);
	}
	
	
	/**
	 * 获取ExpandableListView高度
	 * @param listView
	 */
	public void setExpandableListViewHeight(ExpandableListView listView) {
	    ListAdapter listAdapter = listView.getAdapter(); 
	    if (listAdapter == null) {
	        return;
	    }
	
	    int totalHeight = 0;
	    for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
	        View listItem = listAdapter.getView(i, null, listView);
	        listItem.measure(0, 0);  //计算子项View 的宽高
	        totalHeight += listItem.getMeasuredHeight() + 25;  //统计所有子项的总高度
	    }
	
	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	    //listView.getDividerHeight()获取子项间分隔符占用的高度
	    //params.height最后得到整个ListView完整显示需要的高度
	    listView.setLayoutParams(params);
	}
	
	/**
	 * 获取ListView高度
	 * @param listView
	 */
	public void setListViewHeight(ListView listView) {
	    ListAdapter listAdapter = listView.getAdapter(); 
	    if (listAdapter == null) {
	        return;
	    }
	
	    int totalHeight = 0;
	    for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
	        View listItem = listAdapter.getView(i, null, listView);
	        listItem.measure(0, 0);  //计算子项View 的宽高
	        totalHeight += listItem.getMeasuredHeight() + dp2px(8);  //统计所有子项的总高度
	    }
	
	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (dp2px(2) * (listAdapter.getCount()));
	    //listView.getDividerHeight()获取子项间分隔符占用的高度
	    //params.height最后得到整个ListView完整显示需要的高度
	    listView.setLayoutParams(params);
	}

    /** dp to px */
    private int dp2px(float dp) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}