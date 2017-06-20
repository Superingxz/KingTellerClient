package com.kingteller.client.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;
import android.util.Log;

import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.image.model.SingleImageModel;
import com.kingteller.client.view.log.L;
import com.kingteller.framework.utils.EncroptionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片处理工具类
 * Created by Administrator on 16-2-16.
 */
public class KingTellerImageUtils {

    /**
     * 把bitmap转换成String
     */
    public static String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    
    /**
     * 计算图片的缩放值
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            //inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }


    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     */
    public static Bitmap getSmallBitmap(String filePath) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        if(options.inSampleSize == 1){ //小图
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

            Matrix matrix = new Matrix();
            if(bmp.getWidth() == bmp.getHeight()){
                float w = 480 / bmp.getWidth();
                matrix.preScale(w, w);
            }else{
                float w = 480.0f / bmp.getWidth();
                float l = 800.0f / bmp.getHeight();
                matrix.preScale(w, l);
            }
            
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }else{
            return BitmapFactory.decodeFile(filePath, options);
        }
    }


    /**
     * 图片合成
     */
    public static File createBitmap(String path, String strs) {

        Bitmap src = getSmallBitmap(path);

        int w = src.getWidth();// 图片宽度
        int h = src.getHeight(); // 图片高度
        int padding = 20;// 左右的宽度

        //写字
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
        textPaint.setTextSize(20.0f);// 字体大小
        textPaint.setTypeface(Typeface.DEFAULT);// 采用默认的宽度
        textPaint.setColor(Color.RED);// 采用的颜色

        StaticLayout layout = new StaticLayout(strs, textPaint, w - 2 * padding, Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        int syh = layout.getHeight() + 2 * padding;// 水印高度

        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap( w, h + syh, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        Rect dst = new Rect(0, syh, w, h + syh);
        cv.drawBitmap(src, null, dst, null);// 在 0，0坐标开始画入src

        // 开始写字
        cv.translate(padding, padding);
        layout.draw(cv);

        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储

        return saveImageFile(path, newb);
    }

    private static File saveImageFile(String path, Bitmap bmp) {
        String filename = KingTellerStaticConfig.CACHE_PATH.SD_IMAGECACHE + "/"
                + EncroptionUtils.encryptMD5ToString(path + "hc") + ".jpg";

        File file = new File(filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 40, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }



    /** 查看合成图片是否存存在 path  */
    private static File chackImageFile(String path) {
        String filename = KingTellerStaticConfig.CACHE_PATH.SD_IMAGECACHE + "/"
                + EncroptionUtils.encryptMD5ToString(path + "hc") + ".jpg";

        File file = new File(filename);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    /** 删除合成图片 图片list */
    public static void deleteCacheFile(Context mContext) {
//        Uri uri = Uri.parse(KingTellerStaticConfig.CACHE_PATH.SD_IMAGECACHE);
//        Cursor c = mContext.getContentResolver().query(uri, null,
//                MediaStore.Images.Media.MIME_TYPE + "=\"image/jpeg\"", null, MediaStore.Images.Media.DATE_MODIFIED + " desc");
//
        //selection: 指定查询条件
        String selection = MediaStore.Images.Media.DATA + " like %?";
        //设定查询目录
        String path= KingTellerStaticConfig.CACHE_PATH.SD_IMAGECACHE;
        //定义selectionArgs：
        String[] selectionArgs = {path + "%"};
        Cursor c = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                selection, selectionArgs, null);

        if (c != null){
            while (c.moveToNext()){

                SingleImageModel singleImageModel = new SingleImageModel();
                singleImageModel.path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                try {
                    singleImageModel.date = Long.parseLong(c.getString(c.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)));
                }catch (NumberFormatException e){
                    singleImageModel.date  = System.currentTimeMillis();
                }
                try {
                    singleImageModel.id = Long.parseLong(c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)));
                }catch (NumberFormatException e){
                    singleImageModel.id = 0;
                }
                L.e("缓存文件夹的图片有",  singleImageModel.path);
            }
        }

//        if(list != null && list.size() > 0){
//            for(int i = 0; i < list.size(); i++){
//                String filename = KingTellerStaticConfig.CACHE_PATH.SD_IMAGECACHE + "/"
//                        + list.get(i);
//
//                File picfile = new File(filename);
//                deleteFile(picfile);
//            }
//        }
    }

    /** 删除文件 */
    public static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
                L.e("fileutils",  file.getName() + ": 删除成功！");
            }
        } else {

        }
    }
    
    /** dp to px */
    public static int dp2px(Context mContext, float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
