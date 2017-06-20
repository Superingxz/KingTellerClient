package com.kingteller.client.view;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.activity.common.PicViewActivity;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.BitmapUtils;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 通用图片上传
 * @author 王定波
 *
 */
public class GroupPicGridView extends LinearLayout {
	private List<View> lists = new ArrayList<View>();
	private List<String> images = new ArrayList<String>();

	private boolean isOpt = true;

	public List<String> getImages() {
		return images;
	}

	public void setOpt(boolean isOpt) {
		this.isOpt = isOpt;
	}

	public void setImages(List<String> image) {
		//if (images.size() != image.size()) {
			lists.clear();
			images.clear();
			int length = image.size();
			for (int i = 0; i < length; i++) {
				addAndsetItem(image.get(i));
			}
			addPicItem();
		//}
	}

	private int rows = 3;
	private OnItemClickLister lister;
	private RemoveItemClickLister removelister;
	
	public GroupPicGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public GroupPicGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		if (KingTellerStaticConfig.SCREEN.Width > 480)
			rows = 4;
		else
			rows = 3;
		addPicItem();
	}

	public void addAndsetItem(String path) {

		View item = LayoutInflater.from(getContext()).inflate(R.layout.item_add_pic, null);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				(KingTellerStaticConfig.SCREEN.Width - 60) / rows,
				(KingTellerStaticConfig.SCREEN.Width - 60) / rows);
		item.setLayoutParams(lp);

		lists.add(item);

		ImageView item_add_pic = (ImageView) item.findViewById(R.id.item_add_pic);
		item_add_pic.setImageBitmap(BitmapUtils.decodeStream(path,
				KingTellerStaticConfig.SCREEN.Width,
				KingTellerStaticConfig.SCREEN.Height));
		images.add(path);

		refreshView();
	}

	public void addPicItem() {

		View item = LayoutInflater.from(getContext()).inflate(
				R.layout.item_add_pic, null);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				(KingTellerStaticConfig.SCREEN.Width - 60) / rows,
				(KingTellerStaticConfig.SCREEN.Width - 60) / rows);
		item.setLayoutParams(lp);

		lists.add(item);

		refreshView();
	}

	private void refreshView() {
		// TODO Auto-generated method stub
		int cl = getChildCount();
		for (int i = 0; i < cl; i++) {
			((LinearLayout) getChildAt(i)).removeAllViews();
		}
		removeAllViews();

		LinearLayout layout = null;
		for (int i = 0; i < lists.size(); i++) {
			if (i % rows == 0) {
				layout = new LinearLayout(getContext());
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.setGravity(Gravity.LEFT);

				lp.setMargins(0, 10, 0, 10);
				layout.setLayoutParams(lp);

				addView(layout);
			}
			layout.addView(lists.get(i));

			Button item_delete = (Button) lists.get(i).findViewById(R.id.item_delete);
			item_delete.setTag(Integer.valueOf(i));

			item_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					removePicItem(((Integer) view.getTag()).intValue());
				}
			});

			if (images.size() >= i + 1 && isOpt) {
				item_delete.setVisibility(View.VISIBLE);
			} else
				item_delete.setVisibility(View.GONE);


			lists.get(i).setTag(Integer.valueOf(i));
			lists.get(i).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					int pos = ((Integer) view.getTag()).intValue();
					if (lister != null) {
						if (images.size() >= pos + 1) {
							Intent intent = new Intent(getContext(), PicViewActivity.class);
							intent.putExtra(PicViewActivity.PICPATH, images.get(pos));
							getContext().startActivity(intent);
						} else {
							lister.OnItemClick(view, pos);
						}
					}
				}
			});

		}
	}

	public void removePicItem(int pos) {
		if (removelister != null) {
			removelister.RemoveItemClick(pos);
		}
		
		lists.remove(pos);
		images.remove(pos);
		refreshView();
	}

	public void setItemImageView(int pos, String path) {
		View item = lists.get(pos);
		ImageView item_add_pic = (ImageView) item
				.findViewById(R.id.item_add_pic);
		item_add_pic.setImageBitmap(BitmapUtils.decodeStream(path,
				KingTellerStaticConfig.SCREEN.Width,
				KingTellerStaticConfig.SCREEN.Height));
		images.add(pos, path);
		refreshView();
	}

	public void setOnItemClickLister(OnItemClickLister lister) {
		this.lister = lister;
	}

	public interface OnItemClickLister {
		public void OnItemClick(View view, int pos);
	}

	public void setRemoveItemClickLister(RemoveItemClickLister removelister) {
		this.removelister = removelister;
	}

	public interface RemoveItemClickLister {
		public void RemoveItemClick(int pos);
	}
}
