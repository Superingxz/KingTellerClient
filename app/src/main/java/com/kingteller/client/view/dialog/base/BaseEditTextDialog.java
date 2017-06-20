package com.kingteller.client.view.dialog.base;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.client.view.dialog.listener.OnBtnSelectDateClickL;

public abstract class BaseEditTextDialog<T extends BaseEditTextDialog<T>> extends BaseDialog {
	/** container */
    protected LinearLayout ll_container;
    //title
    /** title */
    protected TextView tv_title;
    /** title content(标题) */
    protected String title;
    /** title textcolor(标题颜色) */
    protected int titleTextColor;
    /** title textsize(标题字体大小,单位sp) */
    protected float titleTextSize_SP;
    /** enable title show(是否显示标题) */
    protected boolean isTitleShow = true;
    
    /** EditText Layout */
    protected LinearLayout ll_EditTextLayout;
    /** (标题) */
    protected TextView edit_title;
    /** (内容) */
    protected EditText edit_content;
    
    //btns
    /** num of btns, [1,2] */
    protected int btnNum = 2;
    /** btn container */
    protected LinearLayout ll_btns;
    /** btns */
    protected TextView tv_btn_left;
    protected TextView tv_btn_right;

    /** btn text(按钮内容) */
    protected String btnLeftText = "取消";
    protected String btnRightText = "确定";

    /** btn textcolor(按钮字体颜色) */
    protected int leftBtnTextColor;
    protected int rightBtnTextColor;

    /** btn textsize(按钮字体大小) */
    protected float leftBtnTextSize_SP = 16f;
    protected float rightBtnTextSize_SP = 16f;

    /** btn press color(按钮点击颜色) */
    protected int btnPressColor = Color.parseColor("#E3E3E3");// #85D3EF,#ffcccccc,#E3E3E3
    /** left btn click listener(左按钮接口) */
    protected OnBtnSelectDateClickL onBtnLeftClickL;
    /** right btn click listener(右按钮接口) */
    protected OnBtnSelectDateClickL onBtnRightClickL;


    /** corner radius,dp(圆角程度,单位dp) */
    protected float cornerRadius_DP = 5;
    /** background color(背景颜色) */
    protected int bgColor = Color.parseColor("#ffffff");
    
    /**
     * method execute order:
     * show:constrouctor---show---oncreate---onStart---onAttachToWindow
     * dismiss:dismiss---onDetachedFromWindow---onStop
     *
     * @param context
     */
    public BaseEditTextDialog(Context context) {
        super(context);
        widthScale(0.85f);

        ll_container = new LinearLayout(context);
        ll_container.setOrientation(LinearLayout.VERTICAL);

        /** title */
        tv_title = new TextView(context);

        /** EditText_Layout */
        ll_EditTextLayout = new LinearLayout(context);
        ll_EditTextLayout.setOrientation(LinearLayout.HORIZONTAL);
        
        /** EditText_title */
        edit_title = new TextView(context);
        /** EditText_Layout */
        edit_content = new EditText(context);

        /**btns*/
        ll_btns = new LinearLayout(context);
        ll_btns.setOrientation(LinearLayout.HORIZONTAL);

        tv_btn_left = new TextView(context);
        tv_btn_left.setGravity(Gravity.CENTER);


        tv_btn_right = new TextView(context);
        tv_btn_right.setGravity(Gravity.CENTER);
    }

    @Override
    public void setUiBeforShow() {
        /** title */
        tv_title.setVisibility(isTitleShow ? View.VISIBLE : View.GONE);

        tv_title.setText(TextUtils.isEmpty(title) ? "温馨提示" : title);
        tv_title.setTextColor(titleTextColor);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize_SP);

        edit_title.setText("温馨提示");
        edit_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize_SP);
        edit_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize_SP);
        
        
        /**btns*/
        tv_btn_left.setText(btnLeftText);
        tv_btn_right.setText(btnRightText);

        tv_btn_left.setTextColor(leftBtnTextColor);
        tv_btn_right.setTextColor(rightBtnTextColor);

        tv_btn_left.setTextSize(TypedValue.COMPLEX_UNIT_SP, leftBtnTextSize_SP);
        tv_btn_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, rightBtnTextSize_SP);

        if (btnNum == 1) {
            tv_btn_left.setVisibility(View.GONE);
        }

        tv_btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv_btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            }
        });

    }
    
    /** set title text(设置标题内容) @return MaterialDialog */
    public T title(String title) {
        this.title = title;
        return (T) this;
    }

    /** set title textcolor(设置标题字体颜色) */
    public T titleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        return (T) this;
    }

    /** set title textsize(设置标题字体大小) */
    public T titleTextSize(float titleTextSize_SP) {
        this.titleTextSize_SP = titleTextSize_SP;
        return (T) this;
    }

    /** enable title show(设置标题是否显示) */
    public T isTitleShow(boolean isTitleShow) {
        this.isTitleShow = isTitleShow;
        return (T) this;
    }
    
    /**
     * set btn text(设置按钮文字内容)
     * btnTexts size 1, right
     * btnTexts size 2, left right
     */
    public T btnNum(int btnNum) {
        if (btnNum < 1 || btnNum > 2) {
            throw new IllegalStateException("btnNum is [1,2]!");
        }
        this.btnNum = btnNum;

        return (T) this;
    }

    /**
     * set btn text(设置按钮文字内容)
     * btnTexts size 1, right
     * btnTexts size 2, left right
     */
    public T btnText(String... btnTexts) {
        if (btnTexts.length < 1 || btnTexts.length > 2) {
            throw new IllegalStateException(" range of param btnTexts length is [1,2]!");
        }

        if (btnTexts.length == 1) {
            this.btnRightText = btnTexts[0];
        } else if (btnTexts.length == 2) {
            this.btnLeftText = btnTexts[0];
            this.btnRightText = btnTexts[1];
        }

        return (T) this;
    }

    /**
     * set btn textcolor(设置按钮字体颜色)
     * btnTextColors size 1, right
     * btnTextColors size 2, left right
     */
    public T btnTextColor(int... btnTextColors) {
        if (btnTextColors.length < 1 || btnTextColors.length > 2) {
            throw new IllegalStateException(" range of param textColors length is [1,2]!");
        }

        if (btnTextColors.length == 1) {
            this.rightBtnTextColor = btnTextColors[0];
        } else if (btnTextColors.length == 2) {
            this.leftBtnTextColor = btnTextColors[0];
            this.rightBtnTextColor = btnTextColors[1];
        }

        return (T) this;
    }

    /**
     * set btn textsize(设置字体大小,单位sp)
     * btnTextSizes size 1, right
     * btnTextSizes size 2, left right
     */
    public T btnTextSize(float... btnTextSizes) {
        if (btnTextSizes.length < 1 || btnTextSizes.length > 2) {
            throw new IllegalStateException(" range of param btnTextSizes length is [1,2]!");
        }

        if (btnTextSizes.length == 1) {
            this.rightBtnTextSize_SP = btnTextSizes[0];
        } else if (btnTextSizes.length == 2) {
            this.leftBtnTextSize_SP = btnTextSizes[0];
            this.rightBtnTextSize_SP = btnTextSizes[1];
        } 

        return (T) this;
    }

    /** set btn press color(设置按钮点击颜色) */
    public T btnPressColor(int btnPressColor) {
        this.btnPressColor = btnPressColor;
        return (T) this;
    }

    /** set corner radius (设置圆角程度) */
    public T cornerRadius(float cornerRadius_DP) {
        this.cornerRadius_DP = cornerRadius_DP;
        return (T) this;
    }

    /** set backgroud color(设置背景色) */
    public T bgColor(int bgColor) {
        this.bgColor = bgColor;
        return (T) this;
    }

    /**
     * set btn click listener(设置按钮监听事件)
     * onBtnClickLs size 1, right
     * onBtnClickLs size 2, left right
     */
    public void setOnBtnClickL(OnBtnSelectDateClickL... onBtnClickLs) {
        if (onBtnClickLs.length < 1 || onBtnClickLs.length > 2) {
            throw new IllegalStateException(" range of param onBtnClickLs length is [1,2]!");
        }

        if (onBtnClickLs.length == 1) {
            this.onBtnRightClickL = onBtnClickLs[0];
        } else if (onBtnClickLs.length == 2) {
            this.onBtnLeftClickL = onBtnClickLs[0];
            this.onBtnRightClickL = onBtnClickLs[1];
        }
    }
}
