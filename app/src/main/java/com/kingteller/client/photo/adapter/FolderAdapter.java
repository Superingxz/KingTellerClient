package com.kingteller.client.photo.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingteller.client.photo.activity.AlbumActivity;
import com.kingteller.client.photo.activity.ShowAllPhoto;
import com.kingteller.client.photo.util.BitmapCache;
import com.kingteller.client.photo.util.BitmapCache.ImageCallback;
import com.kingteller.client.photo.util.ImageItem;
import com.kingteller.client.photo.util.Res;

/**
 * 杩欎釜鏄樉绀烘墍鏈夊寘鍚浘鐗囩殑鏂囦欢澶圭殑閫傞厤鍣� *
 * @author king
 * @QQ:595163260
 * @version 2014骞�0鏈�8鏃� 涓嬪崍11:49:44
 */
public class FolderAdapter extends BaseAdapter {

	private Context mContext;
	private Intent mIntent;
	private DisplayMetrics dm;
	BitmapCache cache;
	final String TAG = getClass().getSimpleName();
	public FolderAdapter(Context c) {
		cache = new BitmapCache();
		init(c);
	}

	// 鍒濆鍖�	
	public void init(Context c) {
		mContext = c;
		mIntent = ((Activity) mContext).getIntent();
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
	}

	

	@Override
	public int getCount() {
		return AlbumActivity.contentList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	ImageCallback callback = new ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,
				Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "callback, bmp not match");
				}
			} else {
				Log.e(TAG, "callback, bmp null");
			}
		}
	};

	private class ViewHolder {
		//
		public ImageView backImage;
		// 灏侀潰
		public ImageView imageView;
		public ImageView choose_back;
		// 鏂囦欢澶瑰悕绉�		
		public TextView folderName;
		// 鏂囦欢澶归噷闈㈢殑鍥剧墖鏁伴噺
		public TextView fileNum;
	}
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					Res.getLayoutID("plugin_camera_select_folder"), null);
			holder = new ViewHolder();
			holder.backImage = (ImageView) convertView
					.findViewById(Res.getWidgetID("file_back"));
			holder.imageView = (ImageView) convertView
					.findViewById(Res.getWidgetID("file_image"));
			holder.choose_back = (ImageView) convertView
					.findViewById(Res.getWidgetID("choose_back"));
			holder.folderName = (TextView) convertView.findViewById(Res.getWidgetID("name"));
			holder.fileNum = (TextView) convertView.findViewById(Res.getWidgetID("filenum"));
			holder.imageView.setAdjustViewBounds(true);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,dipToPx(65)); 
//			lp.setMargins(50, 0, 50,0); 
//			holder.imageView.setLayoutParams(lp);
			holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		String path;
		if (AlbumActivity.contentList.get(position).imageList != null) {
			
			//path = photoAbsolutePathList.get(position);
			//灏侀潰鍥剧墖璺緞
			path = AlbumActivity.contentList.get(position).imageList.get(0).imagePath;
			// 缁檉olderName璁剧疆鍊间负鏂囦欢澶瑰悕绉�			//holder.folderName.setText(fileNameList.get(position));
			holder.folderName.setText(AlbumActivity.contentList.get(position).bucketName);
			
			// 缁檉ileNum璁剧疆鏂囦欢澶瑰唴鍥剧墖鏁伴噺
			//holder.fileNum.setText("" + fileNum.get(position));
			holder.fileNum.setText("" + AlbumActivity.contentList.get(position).count);
			
		} else
			path = "android_hybrid_camera_default";
		if (path.contains("android_hybrid_camera_default"))
			holder.imageView.setImageResource(Res.getDrawableID("plugin_camera_no_pictures"));
		else {
//			holder.imageView.setImageBitmap( AlbumActivity.contentList.get(position).imageList.get(0).getBitmap());
			final ImageItem item = AlbumActivity.contentList.get(position).imageList.get(0);
			holder.imageView.setTag(item.imagePath);
			cache.displayBmp(holder.imageView, item.thumbnailPath, item.imagePath,
					callback);
		}
		// 涓哄皝闈㈡坊鍔犵洃鍚�		
		holder.imageView.setOnClickListener(new ImageViewClickListener(
				position, mIntent,holder.choose_back));
		
		return convertView;
	}

	// 涓烘瘡涓�釜鏂囦欢澶规瀯寤虹殑鐩戝惉鍣�	
	private class ImageViewClickListener implements OnClickListener {
		private int position;
		private Intent intent;
		private ImageView choose_back;
		public ImageViewClickListener(int position, Intent intent,ImageView choose_back) {
			this.position = position;
			this.intent = intent;
			this.choose_back = choose_back;
		}
		
		public void onClick(View v) {
			ShowAllPhoto.dataList = (ArrayList<ImageItem>) AlbumActivity.contentList.get(position).imageList;
			Intent intent = new Intent();
			String folderName = AlbumActivity.contentList.get(position).bucketName;
			intent.putExtra("folderName", folderName);
			intent.setClass(mContext, ShowAllPhoto.class);
			mContext.startActivity(intent);
			choose_back.setVisibility(v.VISIBLE);
		}
	}

	public int dipToPx(int dip) {
		return (int) (dip * dm.density + 0.5f);
	}

}
