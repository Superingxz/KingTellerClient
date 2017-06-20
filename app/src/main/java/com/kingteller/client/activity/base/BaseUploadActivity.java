package com.kingteller.client.activity.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.kingteller.R;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.PopDialog;
import com.kingteller.client.view.toast.T;
import com.kingteller.modules.filechooser.FileChooserActivity;


/**
 * 文件上传基础Activity
 * @author 王定波
 *
 */
public class BaseUploadActivity extends Activity implements OnClickListener {
	protected PopDialog popdialog;
	/***
	 * 从Intent获取图片路径的KEY
	 */
	public static final String KEY_PHOTO_PATH = "photo_path";
	private Uri photoUri;
	//是否可以上传文件
	private boolean selectAllFile = false;


	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		
		popdialog = new PopDialog(this, R.layout.popmenu_pic);

		popdialog.getView().findViewById(R.id.button_my_info_cancle)
				.setOnClickListener(this);
		popdialog.getView().findViewById(R.id.button_my_info_gallery)
				.setOnClickListener(this);
		popdialog.getView().findViewById(R.id.button_my_info_photo)
				.setOnClickListener(this);
		popdialog.getView().findViewById(R.id.button_file)
				.setOnClickListener(this);
		setSelectAllFile(selectAllFile);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_my_info_cancle:
			popdialog.dismiss();
			break;
		case R.id.button_my_info_gallery:
			popdialog.dismiss();
			pickPhoto();
			break;
		case R.id.button_my_info_photo:
			popdialog.dismiss();
			takePhoto();
			break;
		case R.id.button_file:
			popdialog.dismiss();
			pickFile();
			break;
		default:
			break;
		}
	}

	/**
	 * 拍照获取图片
	 */
	private void takePhoto() {
		// 执行拍照前，应该先判断SD卡是否存在
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
			/***
			 * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
			 * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
			 */
			ContentValues values = new ContentValues();
			//values.put(KEY_PHOTO_PATH, );
			photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
			/** ----------------- */
			startActivityForResult(intent, KingTellerStaticConfig.SELECT_PIC_BY_TACK_PHOTO);
		} else {
			T.showShort(this, "内存卡不存在!");
		}
	}

	/***
	 * 从相册中取图片
	 */
	private void pickPhoto() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, KingTellerStaticConfig.SELECT_PIC_BY_PICK_PHOTO);
	}

	/***
	 * 文件选择器
	 */
	private void pickFile() {
		Intent intent = new Intent(this, FileChooserActivity.class);
		startActivityForResult(intent, KingTellerStaticConfig.SELECT_FILE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case KingTellerStaticConfig.SELECT_PIC_BY_TACK_PHOTO:
		case KingTellerStaticConfig.SELECT_PIC_BY_PICK_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				doPhoto(requestCode, data);
			} else {
				T.showShort(this, "未选择!");
			}
			break;
		case KingTellerStaticConfig.SELECT_FILE:
			if (resultCode == Activity.RESULT_OK) {
				doFile(data);
			} else {
				T.showShort(this, "未选择!");
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 选择文件获取路径
	 * 
	 * @param data
	 */
	private void doFile(Intent data) {
		if (data == null) {
			T.showShort(this, "选择文件出错!");
			return;
		}
		Uri fileUri = data.getData();
		if (fileUri == null) {
			T.showShort(this, "选择文件出错!");
			return;
		}

		String path = fileUri.getPath();

		if (path != null) {
			OnCallBackPhoto(path);

		} else {
			T.showShort(this, "选择图片文件不正确!");
		}

	}

	/**
	 * 选择图片后，获取图片的路径
	 * @param requestCode
	 * @param data
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT) 
	private void doPhoto(int requestCode, Intent data) {
		String picPath = null;
		if (requestCode == KingTellerStaticConfig.SELECT_PIC_BY_PICK_PHOTO) // 从相册取图片，有些手机有异常情况，请注意
		{
			if (data == null) {
				T.showShort(this, "选择图片文件出错!");
				return;
			}
			photoUri = data.getData();
			if (photoUri == null) {
				T.showShort(this, "选择图片文件出错!");
				return;
			}
		}

		Log.e("1111111111111",photoUri.toString());
		
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;  
		
		//content://com.android.providers.media.documents/document/image%3A1046083 4.4.4 没问题    5.1.0没问题
		//content://media/external/images/media/13165 4.1.1 以下没问题
		
		if(isKitKat){//content://com.android.providers.media.documents/document/image:3951
		
			picPath = getPath_4_4(this, photoUri);
		}else{//content://media/external/images/media/13165
			picPath = getPath_4_2(this, photoUri);
		}

		if (picPath != null && KingTellerJudgeUtils.isPicure(picPath)) {
			OnCallBackPhoto(picPath);

		} else {
			picPath = null;
			T.showShort(this, "选择图片文件不正确!");
		}
	}

	
	
	public void OnCallBackPhoto(String picPath) {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (popdialog != null && popdialog.isShowing())
			popdialog.dismiss();
	}

	public boolean isSelectAllFile() {
		return selectAllFile;
	}

	/**
	 * 设置是否显示所有文件上传类型
	 * @param selectAllFile
	 */
	public void setSelectAllFile(boolean selectAllFile) {
		this.selectAllFile = selectAllFile;
		popdialog.getView().findViewById(R.id.button_file)
				.setVisibility(selectAllFile ? View.VISIBLE : View.GONE);
	}
	
	
	/**  
	 * <br>功能简述:4.4以下获取图片的方法 
	 * <br>功能详细描述: 
	 * <br>注意: 
	 * @param context 
	 * @param uri 
	 * @return 
	 */ 
	public static String getPath_4_2(final Context context, final Uri uri) {
		
		 String[] pojo = { MediaStore.Images.Media.DATA };
		 Cursor cursor = context.getContentResolver().query(uri, pojo, null, null, null);
		 int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		 cursor.moveToFirst();  
		 
		 return cursor.getString(column_index);
	}
	
	/**  
	 * <br>功能简述:4.4及以上获取图片的方法 
	 * <br>功能详细描述: 
	 * <br>注意: 
	 * @param context 
	 * @param uri 
	 * @return 
	 */  
	@TargetApi(Build.VERSION_CODES.KITKAT)  
	public static String getPath_4_4(final Context context, final Uri uri) {  
	  
	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;  
	  
	    // DocumentProvider  
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {  
	        // ExternalStorageProvider  
	        if (isExternalStorageDocument(uri)) {  
	            final String docId = DocumentsContract.getDocumentId(uri);  
	            final String[] split = docId.split(":");  
	            final String type = split[0];  
	  
	            if ("primary".equalsIgnoreCase(type)) {  
	                return Environment.getExternalStorageDirectory() + "/" + split[1];  
	            }  
	        }  
	        // DownloadsProvider  
	        else if (isDownloadsDocument(uri)) {  
	  
	            final String id = DocumentsContract.getDocumentId(uri);  
	            final Uri contentUri = ContentUris.withAppendedId(  
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));  
	  
	            return getDataColumn(context, contentUri, null, null);  
	        }  
	        // MediaProvider  
	        else if (isMediaDocument(uri)) {  
	            final String docId = DocumentsContract.getDocumentId(uri);  
	            final String[] split = docId.split(":");  
	            final String type = split[0];  
	  
	            Uri contentUri = null;  
	            if ("image".equals(type)) {  
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;  
	            } else if ("video".equals(type)) {  
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;  
	            } else if ("audio".equals(type)) {  
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  
	            }  
	  
	            final String selection = "_id=?";  
	            final String[] selectionArgs = new String[] { split[1] };  
	  
	            return getDataColumn(context, contentUri, selection, selectionArgs);  
	        }  
	    }  
	    // MediaStore (and general)  
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {  
	  
	        // Return the remote address  
	        if (isGooglePhotosUri(uri))  
	            return uri.getLastPathSegment();  
	  
	        return getDataColumn(context, uri, null, null);  
	    }  
	    // File  
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {  
	        return uri.getPath();  
	    }  
	  
	    return null;  
	}  
	
	public static String getDataColumn(Context context, Uri uri, String selection,  
	        String[] selectionArgs) {  
	  
	    Cursor cursor = null;  
	    final String column = "_data";  
	    final String[] projection = { column };  
	  
	    try {  
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,  
	                null);  
	        if (cursor != null && cursor.moveToFirst()) {  
	            final int index = cursor.getColumnIndexOrThrow(column);  
	            return cursor.getString(index);  
	        }  
	    } finally {  
	        if (cursor != null)  
	            cursor.close();  
	    }  
	    return null;  
	}  
	  
	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is ExternalStorageProvider. 
	 */  
	public static boolean isExternalStorageDocument(Uri uri) {  
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());  
	}  
	  
	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is DownloadsProvider. 
	 */  
	public static boolean isDownloadsDocument(Uri uri) {  
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());  
	}  
	  
	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is MediaProvider. 
	 */  
	public static boolean isMediaDocument(Uri uri) {  
	    return "com.android.providers.media.documents".equals(uri.getAuthority());  
	}  
	  
	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is Google Photos. 
	 */  
	public static boolean isGooglePhotosUri(Uri uri) {  
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());  
	}
}