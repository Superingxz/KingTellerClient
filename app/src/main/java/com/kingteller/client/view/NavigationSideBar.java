package com.kingteller.client.view;


import com.kingteller.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class NavigationSideBar extends View {

	private String[] chars = { "置顶", "2015", "2014", "2013"};
	private int chooseIndex; // 被选中的字母下标
	private Paint paint = new Paint();// 画笔
	private TextView mTextView;
	
	private OnLetterSelectedListener letterSelectedListener;

	public OnLetterSelectedListener getLetterSelectedListener() {
		return letterSelectedListener;
	}

	public void setLetterSelectedListener(OnLetterSelectedListener letterSelectedListener) {
		this.letterSelectedListener = letterSelectedListener;
	}

	public TextView getmTextView() {
		return mTextView;
	}

	public void setmTextView(TextView mTextView) {
		this.mTextView = mTextView;
	}
	
	public void setSideBarStr(String[] chars) {
		invalidate();
		this.chars = chars;
	}

	public NavigationSideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
    	int speHSize = MeasureSpec.getSize(heightMeasureSpec);  //180
    	int speWSize = MeasureSpec.getSize(widthMeasureSpec);  //75
	
//		Log.e("MyView", "---width---" + speWSize);  
//		Log.e("MyView", "---height---" + speHSize);  
		speWSize = 80;
		speHSize = chars.length * 60;
        setMeasuredDimension(speWSize, speHSize);  
    }  
	
	/**
	 * 绘制视图
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取控件的宽高
		int width = getWidth();
		int height = getHeight();
		// 每个字母的高度
		int singleHeight = height / chars.length;
		
		for (int i = 0; i < chars.length; i++) {
			paint.setColor(Color.rgb(102, 102, 102));
			paint.setTypeface(Typeface.DEFAULT_BOLD);// 设置粗体
			paint.setAntiAlias(true); // 抗锯齿
			paint.setTextSize(20);

			//返回包围整个字符串的最小的一个Rect区域
			Rect rect = new Rect();
			paint.getTextBounds(chars[i], 0, 1, rect);
			int strwidth = rect.width();
			int strheight = rect.height();
			
			// 绘制文本
			float xPos = (width - paint.measureText(chars[i])) / 2;
			
			float yPos = singleHeight*(i+1) - strheight;	// 每个字母的Y坐标
			
			canvas.drawText(chars[i], xPos, yPos, paint);
			
			paint.reset();	// 重置画笔
		}
		
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action = event.getAction(); // 手指动作
		float y = event.getY();	// 手指Y坐标
		int oldChoode = chooseIndex;
		int c = (int) (y/this.getHeight()*chars.length);
		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundColor(Color.parseColor("#00FFFFFF"));	//设置背景透明
			chooseIndex = -1;
			invalidate(); // 重绘
			if (null != mTextView) {
				mTextView.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			setBackgroundColor(Color.parseColor("#B446AAF8")); // 设置背景
			// 根据y坐标获取点到的字母
			if (oldChoode != c) {
				if (c >= 0 && c < chars.length) {
					if (letterSelectedListener != null) {
						letterSelectedListener.onLetterSelected(chars[c]);
					}
					if (null != mTextView) {
						mTextView.setVisibility(View.VISIBLE);
						Log.e("text","" + c);
						mTextView.setText(chars[c]);// 设置被选中的字母
					}
				}
				chooseIndex=c;
				invalidate();
			}
			break;
		}
		return true;
	}
	
	public interface OnLetterSelectedListener{
		public void onLetterSelected(String s);
	}
}