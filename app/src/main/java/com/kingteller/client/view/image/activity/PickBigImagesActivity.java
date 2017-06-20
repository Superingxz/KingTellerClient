package com.kingteller.client.view.image.activity;

import java.util.ArrayList;

import com.kingteller.R;
import com.kingteller.client.view.image.allview.ZoomImageView;
import com.kingteller.client.view.image.model.SingleImageModel;
import com.kingteller.client.view.image.util.AlbumBitmapCacheHelper;
import com.kingteller.client.view.toast.T;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author: zzp
 * @since: 2015-06-15
 * Description: 仿微信大图选择界面
 */
public class PickBigImagesActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener{
    private ViewPager viewPager;
    private TextView tv_choose_pic;
    private ImageView iv_choose_state;
    private Button btn_choose_finish;

    private MyViewPagerAdapter adapter;

    private ArrayList<SingleImageModel> allimages;
    ArrayList<String> picklist;
    /** 当前选中的图片 */
    private int currentPic;

    private int last_pics;
    private int total_pics;

    private boolean isFinish = false;

    /** 选择的照片文件夹 */
    public final static String EXTRA_DATA = "extra_data";
    /** 所有被选中的图片 */
    public final static String EXTRA_ALL_PICK_DATA = "extra_pick_data";
    /** 当前被选中的照片 */
    public final static String EXTRA_CURRENT_PIC = "extra_current_pic";
    /** 剩余的可选择照片 */
    public final static String EXTRA_LAST_PIC = "extra_last_pic";
    /** 总的照片 */
    public final static String EXTRA_TOTAL_PIC = "extra_total_pic";

    private TextView title_text;
	private Button title_left_btn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pick_big_images);
        initFindView();
        initData();
    }

    protected void initFindView() {
    	title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 finish();
			}
		});
    	
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        tv_choose_pic = (TextView) findViewById(R.id.tv_choose_pic);
        iv_choose_state = (ImageView) findViewById(R.id.iv_choose_state);
        tv_choose_pic.setOnClickListener(this);
        iv_choose_state.setOnClickListener(this);

        btn_choose_finish = (Button) findViewById(R.id.layout_main_rightone_btn);
        btn_choose_finish.setTextColor(getResources().getColor(R.color.common_all_color_greys));
        btn_choose_finish.setText("完成");
        btn_choose_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFinish = true;
                finish();
            }
        });
       
    }

    protected void initData() {
        allimages = (ArrayList<SingleImageModel>) getIntent().getSerializableExtra(EXTRA_DATA);
        picklist = (ArrayList<String>) getIntent().getSerializableExtra(EXTRA_ALL_PICK_DATA);
        if (picklist == null){
        	picklist = new ArrayList<String>();
        }
           
        currentPic = getIntent().getIntExtra(EXTRA_CURRENT_PIC, 0);

        last_pics = getIntent().getIntExtra(EXTRA_LAST_PIC, 0);
        total_pics = getIntent().getIntExtra(EXTRA_TOTAL_PIC, 9);
        
        if(last_pics < total_pics) {
            btn_choose_finish.setTextColor(getResources().getColor(R.color.white));
            btn_choose_finish.setText(String.format("完成(%1$d/%2$d)", total_pics - last_pics, total_pics));
        }
        
        title_text.setText((currentPic + 1) + "/" + getImagesCount());
        
        //如果该图片被选中
        if (getChooseStateFromList(currentPic)){
            iv_choose_state.setBackgroundResource(R.drawable.ic_checkbox_three_pressed);
        }else{
            iv_choose_state.setBackgroundResource(R.drawable.ic_checkbox_three_nomal);
        }

        adapter = new MyViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(currentPic);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //如果该图片被选中
        if (getChooseStateFromList(position)){
            iv_choose_state.setBackgroundResource(R.drawable.ic_checkbox_three_pressed);
        }else{
            iv_choose_state.setBackgroundResource(R.drawable.ic_checkbox_three_nomal);
        }
        currentPic = position;
        title_text.setText((currentPic + 1) + "/" + getImagesCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View view) {
        toggleChooseState(currentPic);
        //如果被选中
        if(getChooseStateFromList(currentPic)){
            if (last_pics <= 0){
                toggleChooseState(currentPic);
                T.showShort(this, String.format("你最多只能选择%1$d张照片", total_pics));
                return ;
            }
            picklist.add(getPathFromList(currentPic));
            last_pics --;
            iv_choose_state.setBackgroundResource(R.drawable.ic_checkbox_three_pressed);
            if(last_pics == total_pics-1){
                btn_choose_finish.setTextColor(getResources().getColor(R.color.white));
            }
            btn_choose_finish.setText(String.format("完成(%1$d/%2$d)", total_pics-last_pics, total_pics));
        }else{
            picklist.remove(getPathFromList(currentPic));
            last_pics ++;
            iv_choose_state.setBackgroundResource(R.drawable.ic_checkbox_three_nomal);
            if(last_pics == total_pics){
                btn_choose_finish.setTextColor(getResources().getColor(R.color.common_all_color_greys));
                btn_choose_finish.setText("完成");
            }else{
                btn_choose_finish.setText(String.format("完成(%1$d/%2$d)", total_pics-last_pics, total_pics));
            }
        }
    }

    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return getImagesCount();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(PickBigImagesActivity.this).inflate(R.layout.layout_widget_zoom_image, null);
            final ZoomImageView zoomImageView = (ZoomImageView) view.findViewById(R.id.zoom_image_view);

            AlbumBitmapCacheHelper.getInstance().addPathToShowlist(getPathFromList(position));
            zoomImageView.setTag(getPathFromList(position));

            Bitmap bitmap = AlbumBitmapCacheHelper.getInstance().getBitmap(getPathFromList(position), 0, 0, new AlbumBitmapCacheHelper.ILoadImageCallback() {
                @Override
                public void onLoadImageCallBack(Bitmap bitmap, String path, Object... objects) {
                    ZoomImageView view = ((ZoomImageView)viewPager.findViewWithTag(path));
                    if (view != null && bitmap != null)
                        ((ZoomImageView)viewPager.findViewWithTag(path)).setSourceImageBitmap(bitmap, PickBigImagesActivity.this);
                }
            }, position);

            if (bitmap != null){
                zoomImageView.setSourceImageBitmap(bitmap, PickBigImagesActivity.this);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
            AlbumBitmapCacheHelper.getInstance().removePathFromShowlist(getPathFromList(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 通过位置获取该位置图片的path
     */
    private String getPathFromList(int position){
        return allimages.get(position).path;
    }

    /**
     * 通过位置获取该位置图片的选中状态
     */
    private boolean getChooseStateFromList(int position){
        return allimages.get(position).isPicked;
    }

    /**
     * 反转图片的选中状态
     */
    private void toggleChooseState(int position){
        allimages.get(position).isPicked = !allimages.get(position).isPicked;
    }

    /**
     * 获得所有的图片数量
     */
    private int getImagesCount(){
        return allimages.size();
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("pick_data", picklist);
        data.putExtra("isFinish", isFinish);
        setResult(RESULT_OK, data);
        super.finish();
    }
}