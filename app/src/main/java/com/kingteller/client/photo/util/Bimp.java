package com.kingteller.client.photo.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.ClipData.Item;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kingteller.framework.KingTellerDb;

public class Bimp {
	public static int max = 0;
	
	public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();   //选择的图片的临时列表

	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	
	public static void setTempSelectBitmaps(Context context,ImageItem imageItem){
		KingTellerDb db = KingTellerDb.create(context);
		ImageItem item = db.findById(imageItem.getImageId(), ImageItem.class);
		if(item == null){
			item = new ImageItem();
			item.setBitmap(imageItem.getBitmap());
			item.setImageId(imageItem.getImageId());
			item.setImagePath(imageItem.getImagePath());
			item.setThumbnailPath(imageItem.getThumbnailPath());
			item.setSelected(imageItem.isSelected);
			db.save(item);
		}else{
			item = new ImageItem();
			item.setBitmap(imageItem.getBitmap());
			item.setImageId(imageItem.getImageId());
			item.setImagePath(imageItem.getImagePath());
			item.setThumbnailPath(imageItem.getThumbnailPath());
			item.setSelected(imageItem.isSelected);
			db.update(item);
		}
	}
	
	public static ArrayList<ImageItem> getTempSelectBitmaps(Context context){
		KingTellerDb db = KingTellerDb.create(context);
		List<ImageItem> iIList = db.findAll(ImageItem.class);
		return iIList == null ? null:(ArrayList<ImageItem>)iIList;
	}
	
	public static int getSizeSelected(Context context){
		KingTellerDb db = KingTellerDb.create(context);
		List<ImageItem> iIList = db.findAll(ImageItem.class);
		return iIList.size();
	}
	
	public static void removeAll(Context context){
		KingTellerDb db = KingTellerDb.create(context);
		db.deleteAll(ImageItem.class);
	}
	
	public static void remove(Context context,ImageItem imageItem){
		KingTellerDb db = KingTellerDb.create(context);
		db.delete(imageItem);
	}
	
	
}
