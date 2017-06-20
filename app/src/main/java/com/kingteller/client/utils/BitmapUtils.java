package com.kingteller.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.framework.utils.Logger;
import com.kingteller.framework.utils.MD5Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

/**
 * 圖片處理
 * 
 * @author Administrator
 * 
 */
public class BitmapUtils {

	private static final String TAG = "BtimapUtils";

	/**
	 * 压缩图片
	 * 
	 * @param path
	 * @param requreWidth
	 * @param requreHeight
	 * @return
	 */
	public static Bitmap decodeStream(String path, int requreWidth,
			int requreHeight) {

		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		String pre = requreWidth + "_" + requreHeight;
		File file = getThumbnail(path, pre);

		try {
			if (file != null) {
				return BitmapFactory.decodeFile(file.getPath());
			}
			BitmapFactory.decodeStream(new FileInputStream(new File(path)),
					null, o);
			int width_tmp = o.outWidth;
			int hight_tmp = o.outHeight;

			if (requreWidth != 0 && requreHeight == 0)
				requreHeight = requreWidth * hight_tmp / width_tmp;
			else if (requreWidth == 0 && requreHeight != 0)
				requreWidth = requreHeight * width_tmp / hight_tmp;

			int scale = 1;
			while (true) {
				if (width_tmp / 2 < requreWidth || hight_tmp / 2 < requreHeight)
					break;
				width_tmp /= 2;
				hight_tmp /= 2;
				scale *= 2;
			}

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			o2.inPreferredConfig = Bitmap.Config.RGB_565;

			Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
					new File(path)), null, o2);

			saveThumbnail(path, pre, bitmap);
			return bitmap;

		} catch (Exception e) {
		}
		return null;

	}

	/**
	 * 获取缩略图
	 * 
	 * @param bmpOrg
	 * @param requestWidth
	 * @param requestHeight
	 * @return
	 */
	public static Bitmap getCompressBmp(Bitmap bmpOrg, int requestWidth,
			int requestHeight) {
		if (bmpOrg != null) {
			int width = bmpOrg.getWidth();
			int height = bmpOrg.getHeight();

			if (requestWidth != 0 && requestHeight == 0)
				requestHeight = requestWidth * height / width;
			else if (requestWidth == 0 && requestHeight != 0)
				requestWidth = requestHeight * width / height;

			if (width != requestWidth) {

				float sw = ((float) requestWidth) / width;
				float sh = ((float) requestHeight) / height;
				Matrix matrix = new Matrix();
				matrix.postScale(sw, sh);
				Bitmap resizeBitmap = Bitmap.createBitmap(bmpOrg, 0, 0, width,
						height, matrix, true);
				if (!bmpOrg.isRecycled()) {
					bmpOrg.recycle();
					bmpOrg = null;
				}
				return resizeBitmap;
			}
			return bmpOrg;
		}
		return null;
	}

	/**
	 * Bitmap保存到本地
	 * 
	 * @param bmpOrg
	 * @param fileDir
	 * @param fileName
	 * @param imageQuelity
	 * @throws Exception
	 */
	public static void compress(Context context, Bitmap bmpOrg,
			int imageQuelity, String file) throws Exception {
		bmpOrg.compress(Bitmap.CompressFormat.JPEG, imageQuelity,
				new FileOutputStream(file));
	}

	/**
	 * 获得图片Bitmap对象无压缩（本地图片）
	 * 
	 * @param imagePath
	 * @return
	 */
	public static Bitmap getLocalBitmap(Context ctx, String imagePath) {
		try {
			return BitmapFactory.decodeStream(ctx.getAssets().open(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得图片Bitmap对象（资源ID）
	 * 
	 * @param imagePath
	 * @return
	 */
	public static Bitmap getBitmap(Context content, int resoureId) {
		return BitmapFactory.decodeResource(content.getResources(), resoureId);
	}

	/**
	 * 改变图片变成灰色（资源ID）
	 * 
	 * @param content
	 * @param resoureId
	 * @return
	 */

	public static Bitmap toGray(Context content, int resoureId) {
		Bitmap mBitmap = BitmapFactory.decodeResource(content.getResources(),
				resoureId);
		return toGray(mBitmap);
	}

	/**
	 * 改变图片变成灰色（本地）
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap toGray(String imagePath) {
		Bitmap mBitmap = BitmapFactory.decodeFile(imagePath);
		return toGray(mBitmap);
	}

	/**
	 * 改变图片变成灰色（网络地址）
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap toGrayForHttp(String url) {
		Bitmap mBitmap = getHttpBitmap(url);
		return toGray(mBitmap);
	}

	/**
	 * 改变图片变成灰色（Bitmap）
	 * 
	 * @param mBitmap
	 * @return
	 */
	public static Bitmap toGray(Bitmap mBitmap) {
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();
		Bitmap grayImg = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(grayImg);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
				colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(mBitmap, 0, 0, paint);
		return grayImg;
	}

	/**
	 * 从网络取图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			Log.d("getHttpBitmap", url);
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setConnectTimeout(0);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 设置透明度
	 * 
	 * @param mBitmap
	 * @param number
	 * @return
	 */
	public static Bitmap setAlpha(Bitmap mBitmap, int number) {
		int[] argb = new int[mBitmap.getWidth() * mBitmap.getHeight()];
		mBitmap.getPixels(argb, 0, mBitmap.getWidth(), 0, 0,
				mBitmap.getWidth(), mBitmap.getHeight());
		// 获得图片的ARGB值
		number = number * 255 / 100;
		for (int i = 0; i < argb.length; i++) {
			argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
			// 修改最高2位的值
		}
		mBitmap = Bitmap.createBitmap(argb, mBitmap.getWidth(),
				mBitmap.getHeight(), Config.ARGB_8888);
		return mBitmap;

	}

	/**
	 * 图片圆角
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = null;
		if (bitmap != null) {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
					Config.ARGB_8888);

			Canvas canvas = new Canvas(output);

			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = pixels;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		}

		return output;
	}

	/**
	 * 设置透明度
	 * 
	 * @param content
	 * @param resoureId
	 * @param number
	 * @return
	 */
	public static Bitmap setAlpha(Context content, int resoureId, int number) {
		Bitmap mBitmap = BitmapFactory.decodeResource(content.getResources(),
				resoureId);
		return setAlpha(mBitmap, number);

	}

	private static File saveThumbnail(String path, String pre, Bitmap bmp) {
		String filename = KingTellerStaticConfig.CACHE_PATH.SD_DATA + "/"
				+ MD5Utils.toMD5(path + "thumbnail" + pre) + ".jpg";
		try {
			FileOutputStream out = new FileOutputStream(filename);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
			return new File(filename);
		} catch (Exception e) {

		}

		return null;
	}

	private static File getThumbnail(String path, String pre) {
		String filename = KingTellerStaticConfig.CACHE_PATH.SD_DATA + "/"
				+ MD5Utils.toMD5(path + "thumbnail" + pre) + ".jpg";

		File file = new File(filename);
		if (file.exists()) {
			return file;
		}

		return null;
	}

	public static File getThumbnail(String path, int requiredWidth,
			int requiredHeight) {

		File file = getThumbnail(path, requiredWidth + "_" + requiredHeight);
		if (file != null) {
			return file;
		} else
			decodeStream(path, requiredWidth, requiredHeight);

		return getThumbnail(path, requiredWidth + "_" + requiredHeight);

	}

	public static File getFile(Bitmap bmp) {
		String filename = KingTellerStaticConfig.CACHE_PATH.SD_DATA + "/"
				+ MD5Utils.toMD5(String.valueOf(System.currentTimeMillis()))
				+ ".jpg";
		try {
			FileOutputStream out = new FileOutputStream(filename);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
			return new File(filename);
		} catch (Exception e) {

		}

		return null;
	}

	/**
	 * 图片合成
	 * 
	 * @param bitmap
	 * @param strs must
	 * @return
	 */
	public static File createBitmap(String path, int width, int height,
			String strs) {
		if (path == null) {
			return null;
		}
		if(strs == null){
			return null;
		}
		String pre = width + "_" + height+"_"+strs;
		File file = getThumbnail(path, pre);
		if (file != null) {
			return file;
		}
		
		Bitmap src = decodeStream(path, width, height);

		int neww = KingTellerStaticConfig.DefaultImgMaxWidth;// 图片宽度
		int newh = src.getHeight() * neww / src.getWidth(); // 图片高度
		int padding = 20;// 左右的宽度

		TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔

		textPaint.setTextSize(20.0f);// 字体大小
		textPaint.setTypeface(Typeface.DEFAULT);// 采用默认的宽度
		textPaint.setColor(Color.RED);// 采用的颜色
		StaticLayout layout = new StaticLayout(strs, textPaint, neww - 2
				* padding, Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
		int syh = layout.getHeight() + 2 * padding;// 水印高度

		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(neww, newh + syh, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		Rect dst = new Rect(0, syh, neww, newh + syh);
		cv.drawBitmap(src, null, dst, null);// 在 0，0坐标开始画入src

		// 开始写字
		cv.translate(padding, padding);
		layout.draw(cv);

		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		saveThumbnail(path, pre, newb);
		
		return saveThumbnail(path, pre, newb);
	}
	
}
