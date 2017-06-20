package com.kingteller.client.activity.logisticmonitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.kingteller.R;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.photo.activity.TestAlbumActivity;
import com.kingteller.client.utils.BitmapUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.GroupPicGridView;
import com.kingteller.client.view.GroupPicGridView.OnItemClickLister;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.PopDialog;
import com.kingteller.framework.http.AjaxFilesParams;

public class UploadPhotosActivity extends BaseActivity implements View.OnClickListener{

	private GroupPicGridView item_add_pic;
	private int selectPos;
	private GroupPicGridView selectGview;
	private String tydId;
	private String tydzt;
	private List<String> piclist = new ArrayList<String>();
	private ArrayList<String> listArray = new ArrayList<String>();
	private final static int SUCCESS = 1;
	public final static String UPLOAD_NUM = "upload_num";
	private int upload_num = 0;
	protected PopDialog popdialog;
	private Context mContext;
	/***
	 * 从Intent获取图片路径的KEY
	 */
	public static final String KEY_PHOTO_PATH = "photo_path";
	private Uri photoUri;
	//是否可以上传文件
	private boolean selectAllFile = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_upload_photos);
		mContext = UploadPhotosActivity.this;
		initUI();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SUCCESS:
				AjaxFilesParams params = (AjaxFilesParams) msg.obj;
				submitPic(params);
				break;

			default:
				break;
			}
		};
	};
	
	private void initUI() {
		title_title.setText("选择图片");
		title_left.setOnClickListener(this);
		
		popdialog = new PopDialog(this, R.layout.popmenu_pic);

		popdialog.getView().findViewById(R.id.button_my_info_cancle)
				.setOnClickListener(this);
		popdialog.getView().findViewById(R.id.button_my_info_gallery)
				.setOnClickListener(this);
		popdialog.getView().findViewById(R.id.button_my_info_photo)
				.setOnClickListener(this);
		popdialog.getView().findViewById(R.id.button_file)
				.setOnClickListener(this);
		popdialog.getView().findViewById(R.id.button_file).setVisibility(selectAllFile ? View.VISIBLE : View.GONE);
		
		tydId = getIntent().getStringExtra("tydId");
		tydzt = getIntent().getStringExtra("tydzt");
		findViewById(R.id.btn_submit).setOnClickListener(this);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
		
		//popdialog.getView().findViewById(R.id.button_my_info_photo).setVisibility(View.GONE);
		
		item_add_pic = (GroupPicGridView) findViewById(R.id.item_add_pic);
		item_add_pic.setOnItemClickLister(new OnItemClickLister() {

			@Override
			public void OnItemClick(View view, int pos) {
				// TODO Auto-generated method stub
				selectPos = pos;
				selectGview = item_add_pic;
				popdialog.show();
			}
		});
		
		if(!KingTellerJudgeUtils.isEmpty(getIntent().getStringExtra(UPLOAD_NUM))){
			upload_num = Integer.parseInt(getIntent().getStringExtra(UPLOAD_NUM));
		}

	}

	protected void submitPic(AjaxFilesParams params) {
		// TODO Auto-generated method stub
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WljkUploadPicUrl), params,
				new AjaxHttpCallBack<ReturnBackStatus>(this, true) {

					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(UploadPhotosActivity.this, "正在上传...");
					}
					
					@Override
					public void onDo(ReturnBackStatus data) {
						// TODO Auto-generated method stub
						if(data.getResult().trim().equals("success")){
							T.showShort(mContext, "上传成功!");
							Intent intent = new Intent();
							intent.putExtra(LogisticTaskActivity.TYDID, tydId);
							intent.putExtra(LogisticTaskActivity.UPLOAD_NUM, String.valueOf(piclist.size()));
							setResult(RESULT_OK, intent);
							finish();
						} else{
							T.showShort(mContext, "上传失败!");
						}
					}
				});
	}

	public void OnCallBackPhoto(String picPath) {
		// TODO Auto-generated method stub
		selectGview.setItemImageView(selectPos, picPath);
		selectGview.addPicItem();

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case KingTellerStaticConfig.SELECT_PIC_UPLOAD:
			if (resultCode == Activity.RESULT_OK) {
				listArray = data.getStringArrayListExtra("data");
				if(listArray != null){	
					for(int i =0; i < listArray.size(); i ++){
						selectGview.setItemImageView(selectPos+i, listArray.get(i));
						selectGview.addPicItem();
					}
				}else{
					T.showShort(mContext, "未选择!");
				}
			} else {
				T.showShort(mContext, "未选择!");
			}
			break;
		case KingTellerStaticConfig.SELECT_FILE:
			if (resultCode == Activity.RESULT_OK) {
			} else {
				T.showShort(mContext, "未选择!");
			}
			break;
		case KingTellerStaticConfig.SELECT_PIC_TAKE:
				if (resultCode == Activity.RESULT_OK) {
					doPhoto(requestCode,data);
				} else {
					T.showShort(mContext, "未选择!");
				}
			break;
		default:
			break;
		}
	}
	
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
			photoUri = this.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
			/** ----------------- */
			startActivityForResult(intent, KingTellerStaticConfig.SELECT_PIC_TAKE);
		} else {
			T.showShort(mContext, "内存卡不存在!");
		}
	}
	
	private void doPhoto(int requestCode, Intent data) {
		String picPath = null;

		if (photoUri.toString().startsWith("content://")) {
			String[] pojo = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
			if (cursor != null) {
				int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
				cursor.moveToFirst();
				picPath = cursor.getString(columnIndex);

			}
		} else {
			picPath = photoUri.getPath();
		}

		if (picPath != null && KingTellerJudgeUtils.isPicure(picPath)) {
			OnCallBackPhoto(picPath);

		} else {
			picPath = null;
			T.showShort(mContext, "选择图片文件不正确!");

		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_left:
			finish();
			break;
		case R.id.btn_submit:
			new KTAlertDialog.Builder(this)
			.setTitle("提示")
			.setMessage("上传后不能更改,确定要上传吗?")
			.setPositiveButton("确定",
					new KTAlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface,int pos) {
							piclist = item_add_pic.getImages();
							dealPic();
							dialogInterface.dismiss();
						}
					})
			.setNegativeButton("取消",
					new KTAlertDialog.OnClickListener() {
						public void onClick(
								DialogInterface dialogInterface,
								int paramAnonymousInt) {
							dialogInterface.dismiss();
						}
					}).create().show();
			break;
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.button_my_info_gallery:
			Intent intent = new Intent(UploadPhotosActivity.this,
					TestAlbumActivity.class);
			startActivityForResult(intent, KingTellerStaticConfig.SELECT_PIC_UPLOAD);
			popdialog.dismiss();
			break;
		case R.id.button_my_info_cancle:
			popdialog.dismiss();
			break;
		case R.id.button_my_info_photo:
			/*intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, CommonCodeConfig.SELECT_PIC_TAKE);*/
			takePhoto();
			popdialog.dismiss();
			break;
		default:
			break;
		}
	}
	
	private void dealPic(){

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				AjaxFilesParams params = new AjaxFilesParams();

				params.put("tydId", tydId);
				params.put("tydzt", tydzt);
				params.put("userId", User.getInfo(UploadPhotosActivity.this).getUserId());
				
				List<String> filelist = new ArrayList<String>();
				for(int i = 0; i < piclist.size(); i ++){
					File file = BitmapUtils.createBitmap(piclist.get(i), 
							KingTellerStaticConfig.DefaultImgMaxWidth,
							KingTellerStaticConfig.DefaultImgMaxHeight," ");
					params.put("files", file);
					params.put("filesFileName", file.getName());
				}
				
				Message msg = new Message();
				msg.what = SUCCESS;
				msg.obj = params;
				handler.sendMessage(msg);

			}
		}).start();
	}

}
