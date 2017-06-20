package com.kingteller.client.view.image.allview;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.common.PicViewActivity;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.BitmapUtils;
import com.kingteller.client.view.GroupPicGridView.OnItemClickLister;
import com.kingteller.client.view.image.util.AlbumBitmapCacheHelper;
import com.kingteller.client.view.image.util.CommonUtil;
import com.kingteller.client.view.toast.T;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;

public class SelectPicGridView extends LinearLayout{
	/** container */
	private LinearLayout ll_container;
	/** Image View */
	private View pic_view;
	/** Image View */
	private ImageView pic_imageView;
	/** viewList */
	private List<View> viewLists = new ArrayList<View>();
	/** Image Path List */
	private List<String> imagePaths = new ArrayList<String>();
	/** 默认每行展示多少张图片 */
	private int rows = 3;
	
    /** 每张图片需要显示的高度和宽度 */
    private int perWidth;
    
	private OnSelectImageItemLister mSelectImageLister;
	
	public void setOnSelectImageItemLister(OnSelectImageItemLister SelectImageLister) {
		this.mSelectImageLister = SelectImageLister;
	}

	public interface OnSelectImageItemLister {
		public void OnItemClick(View view, int type, int num);
	}
	
	public SelectPicGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initView();
	}
	
	public SelectPicGridView(Context context) {
		super(context);
		
		initView();
	}
	
	private void initView() {
		if (KingTellerStaticConfig.SCREEN.Width > 480){
			rows = 4;
		}else{
			rows = 3;
		}
		
		//计算每张图片应该显示的宽度
        perWidth = (((WindowManager) (KingTellerApplication.getApplication().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getWidth() - dp2px(4))/3;	
		
        ll_container = new LinearLayout(getContext());
		LinearLayout.LayoutParams lp_container = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp_container.setMargins(10, 10, 0, 10);
		ll_container.setLayoutParams(lp_container);
		ll_container.setOrientation(LinearLayout.HORIZONTAL);
		
		pic_view = LayoutInflater.from(getContext()).inflate(R.layout.item_add_selectpic_gird, null);
		pic_imageView = (ImageView) pic_view.findViewById(R.id.add_select_pic);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dp2px(70),dp2px(70));
		pic_view.setLayoutParams(lp);
		
		viewLists.add(pic_view);
		
		viewLists.get(0).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mSelectImageLister != null) {
					mSelectImageLister.OnItemClick(view, 0, 0);
				}
			}
		});
		
		
		ll_container.addView(viewLists.get(0));
		addView(ll_container);
	}
	 
	//添加图片
	public void setImagesView(final List<String> imagePathList) {
		imagePaths = imagePathList;
		
		int cl = getChildCount();
		for (int i = 0; i < cl; i++) {
			((LinearLayout) getChildAt(i)).removeAllViews();
		}
		
		viewLists.clear();
		removeAllViews();
		
		for (int i = 0; i < imagePaths.size() + 1; i++) {
			if (i % rows == 0) {
				ll_container = new LinearLayout(getContext());
				LinearLayout.LayoutParams lp_container = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lp_container.setMargins(10, 10, 0, 10); 
				ll_container.setLayoutParams(lp_container);
				ll_container.setOrientation(LinearLayout.HORIZONTAL);

				addView(ll_container);
			}
			
			pic_view = LayoutInflater.from(getContext()).inflate(R.layout.item_add_selectpic_gird, null);
			pic_imageView = (ImageView) pic_view.findViewById(R.id.add_select_pic);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dp2px(70),dp2px(70));
			lp.setMargins(0, 0, 10, 0);
			pic_view.setLayoutParams(lp);
			
			if(i == imagePaths.size()){
				viewLists.add(pic_view);
			}else{
				Log.e("pic_lis", imagePaths.get(i));
				
				viewLists.add(pic_view);
				
				Bitmap bitmap = setImagesSize(imagePaths.get(i), perWidth, perWidth);
				
				if (bitmap != null){
	                BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
	                pic_imageView.setBackgroundDrawable(bd);
	            }else{
	            	pic_imageView.setBackgroundResource(R.drawable.ic_selectpic_load);
	            }
			}
 
			ll_container.addView(viewLists.get(i));
			
			viewLists.get(i).setTag(Integer.valueOf(i));
			viewLists.get(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					int pos = ((Integer) view.getTag()).intValue();
					if(pos == imagePaths.size()){
						//添加图片
						if (mSelectImageLister != null) {
							mSelectImageLister.OnItemClick(view, 0, 0);
						}
					}else{
						//查看图片 和 删除图片
						if (mSelectImageLister != null) {
							mSelectImageLister.OnItemClick(view, 1, pos);
						}
					}
				}
			});
		}
	}
	
	//展示图片
	public void setImagesCheckView(final List<String> imagePathList) {
		imagePaths = imagePathList;
		
		int cl = getChildCount();
		for (int i = 0; i < cl; i++) {
			((LinearLayout) getChildAt(i)).removeAllViews();
		}
		
		viewLists.clear();
		removeAllViews();
		
		for (int i = 0; i < imagePaths.size(); i++) {
			if (i % rows == 0) {
				ll_container = new LinearLayout(getContext());
				LinearLayout.LayoutParams lp_container = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lp_container.setMargins(10, 10, 0, 10); 
				ll_container.setLayoutParams(lp_container);
				ll_container.setOrientation(LinearLayout.HORIZONTAL);

				addView(ll_container);
			}
			
			pic_view = LayoutInflater.from(getContext()).inflate(R.layout.item_add_selectpic_gird, null);
			pic_imageView = (ImageView) pic_view.findViewById(R.id.add_select_pic);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dp2px(70),dp2px(70));
			lp.setMargins(0, 0, 10, 0);
			pic_view.setLayoutParams(lp);
			
			
			Log.e("pic_lis", imagePaths.get(i));
			
			viewLists.add(pic_view);
			
			Bitmap bitmap = setImagesSize(imagePaths.get(i), perWidth, perWidth);
			
			if (bitmap != null){
                BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
                pic_imageView.setBackgroundDrawable(bd);
            }else{
            	pic_imageView.setBackgroundResource(R.drawable.ic_selectpic_load);
            }
			
			ll_container.addView(viewLists.get(i));
			
			viewLists.get(i).setTag(Integer.valueOf(i));
			viewLists.get(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					int pos = ((Integer) view.getTag()).intValue();
					
					//查看图片
					if (mSelectImageLister != null) {
						mSelectImageLister.OnItemClick(view, 2, pos);
					}
					
				}
			});
		}
	}
	
	
	
	public Bitmap setImagesSize(String path, int width, int height) {
		Bitmap bitmap = null;
        
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = AlbumBitmapCacheHelper.computeScale(options, width, height);
        options.inJustDecodeBounds = false;
      
        try {
            bitmap = BitmapFactory.decodeFile(path, options);
        }catch (OutOfMemoryError error){
            bitmap = null;
        }
        
        Log.e("bitmap: Width , Height , inSampleSize", bitmap.getWidth() + "," + bitmap.getHeight() + "," + options.inSampleSize);
        
        if (bitmap != null) {
            bitmap = AlbumBitmapCacheHelper.centerSquareScaleBitmap(bitmap, ((bitmap.getWidth() > bitmap.getHeight()) ? bitmap.getHeight() : bitmap.getWidth()));
        }
        
        if (options.inSampleSize >= 3 && bitmap != null) {//大图 
        	 ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	 bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        }else{//小图放大
        	Matrix matrix = new Matrix();
            matrix.preScale(2f, 2f);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        
        return bitmap;
	}
	
	public List<String> getImagesList() {
		return imagePaths;
	}
	
	  /** dp to px */
    protected int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
