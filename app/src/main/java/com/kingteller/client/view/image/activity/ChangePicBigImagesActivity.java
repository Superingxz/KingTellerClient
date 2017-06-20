package com.kingteller.client.view.image.activity;


import java.util.List;

import com.google.gson.Gson;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.image.allview.ZoomImageView;
import com.kingteller.client.view.image.util.AlbumBitmapCacheHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ChangePicBigImagesActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener{
	
    /** 当前选中的图片 */
    private int now_pics;
    /** 总共的图片 */
    private int total_pics;
    /** 是否删除图片标志  */
    private int isdelete_pics;
    
    /** 图片路径list */
    private List<String> picklist;
    
    /** 已选择的照片列表 */
    public final static String EXTRA_NOW_DATA = "extra_now_data";
    /** 当前点击图片 */
    public final static String EXTRA_NOW_PIC = "extra_now_pic";
    /** 是否允许删除图片 */
    public final static String EXTRA_IS_DELETE = "extra_is_delete";
    
    /** 是否做了修改 */
    private boolean isChangeList = false;
    
    private ViewPager viewPager;
	private MyChangeViewPagerAdapter adapter;
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn;
	private Button btn_choose_delete;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_pic_big_images);
        mContext = ChangePicBigImagesActivity.this;
        
        
        initFindView();
        initData();
    }
    
    protected void initFindView() {
    	isChangeList = false;
    	
    	String picdata = (String) getIntent().getSerializableExtra(EXTRA_NOW_DATA);
    	picklist = KingTellerJsonUtils.getStringPersons(picdata);
    	
    	now_pics = getIntent().getIntExtra(EXTRA_NOW_PIC, 0);
    	isdelete_pics = getIntent().getIntExtra(EXTRA_IS_DELETE, 0);
    	total_pics = picklist.size();
    	
    	title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
	    btn_choose_delete = (Button) findViewById(R.id.layout_main_righttwo_btn);
	       
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
    	if(isdelete_pics != 2){
    		btn_choose_delete.setBackgroundResource(R.drawable.btn_delete_image);
            btn_choose_delete.setOnClickListener(this);
    	}

        viewPager = (ViewPager) findViewById(R.id.vp_change_content);
    }

    protected void initData() {
    	title_text.setText((now_pics + 1) + "/" + total_pics);      

        adapter = new MyChangeViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        
        viewPager.setOffscreenPageLimit(total_pics);
        viewPager.setCurrentItem(now_pics);
    }
    
    @Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {

		now_pics = arg0;
        title_text.setText((now_pics + 1) + "/" + total_pics);
	}
	
    private class MyChangeViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return picklist.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(ChangePicBigImagesActivity.this).inflate(R.layout.layout_widget_zoom_image, null);
            final ZoomImageView zoomImageView = (ZoomImageView) view.findViewById(R.id.zoom_image_view);
            
            Bitmap bitmap = setImagesSize(picklist.get(position));

            if (bitmap != null){
                zoomImageView.setSourceImageBitmap(bitmap, ChangePicBigImagesActivity.this);
            }
            
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            View view = (View) object;
//            container.removeView(view);
//            AlbumBitmapCacheHelper.getInstance().removePathFromShowlist(getPathFromList(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
    
    public Bitmap setImagesSize(String path) {
		Bitmap bitmap = null;
        
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = AlbumBitmapCacheHelper.computeScale(options, ((WindowManager) (KingTellerApplication.getApplication().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getWidth(),
        		((WindowManager) (KingTellerApplication.getApplication().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getWidth());
        options.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeFile(path, options);
        }catch (OutOfMemoryError error){
            bitmap = null;
        }

        return bitmap;
	}
    
    @Override
    public void onBackPressed() {
    	returnListDataAndClose();
    }
  
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			returnListDataAndClose();
			break;
		case R.id.layout_main_righttwo_btn:
			final NormalDialog dialog = new NormalDialog(mContext);
			KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "你确定要删除吗？",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    },new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                    	dialog.dismiss();
	                    	
	                    	isChangeList = true;
	                    	
	                    	picklist.remove(now_pics);
	                    	
	                    	now_pics = now_pics - 1;
	                    	total_pics = total_pics - 1; 
	                    	
	                    	if(total_pics > 0){
	                    		viewPager.setAdapter(adapter);
	                    		viewPager.setOffscreenPageLimit(total_pics);
	                    		
		                    	if(now_pics < 0){
		                    		now_pics = 0;
		                    	}
		                    	title_text.setText((now_pics + 1) + "/" + total_pics);
			                    viewPager.setCurrentItem(now_pics);
	                    			                    		
	                    	}else{
	                    		returnListDataAndClose();
	                    	}
	                    }
                });
			break;
		default:
			break;
		}
	}
	
	/**
     * 返回修改后的图片list
     */
    private void returnListDataAndClose(){
    	if(isChangeList){
    	    Intent intent_pic = new Intent();
    	    intent_pic.putExtra("pic_dataList_de", new Gson().toJson(picklist));
    		setResult(RESULT_OK, intent_pic);
    	}
		finish();
    }
}
