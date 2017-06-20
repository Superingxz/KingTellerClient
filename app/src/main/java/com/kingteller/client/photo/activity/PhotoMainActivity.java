package com.kingteller.client.photo.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.kingteller.R;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.activity.logisticmonitor.LogisticTaskActivity;
import com.kingteller.client.activity.more.FeedBackActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.photo.util.Bimp;
import com.kingteller.client.photo.util.FileUtils;
import com.kingteller.client.photo.util.ImageItem;
import com.kingteller.client.photo.util.PublicWay;
import com.kingteller.client.photo.util.Res;
import com.kingteller.client.utils.BitmapUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxFilesParams;


/**
 * 首页面activity
 *
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:48:34
 */
public class PhotoMainActivity extends BaseActivity implements OnClickListener{

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap ;
	public static String tydId;
	public static String tydzt;
	private final static int SUCCESS = 1;
	public final static String UPLOAD_NUM = "upload_num";
	private int upload_num = 0;
	
	private Context mContext;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
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
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		tydId = getIntent().getStringExtra("tydId");
		tydzt = getIntent().getStringExtra("tydzt");
		bimap = BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.activity_selectimg, null);
		setContentView(parentView);
		mContext = PhotoMainActivity.this;
		Init();
	}

	public void Init() {
		
		title_title.setText("选择图片");
		title_left.setOnClickListener(this);
		
		//upload_num = Integer.parseInt(getIntent().getStringExtra(UPLOAD_NUM));
		findViewById(R.id.btn_submit).setOnClickListener(this);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
		
		pop = new PopupWindow(PhotoMainActivity.this);
		
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view
				.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view
				.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view
				.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PhotoMainActivity.this,
						AlbumActivity.class);
				intent.putExtra("tydId", tydId);
				intent.putExtra("tydzt", tydzt);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);	
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					ll_popup.startAnimation(AnimationUtils.loadAnimation(PhotoMainActivity.this,R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(PhotoMainActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		
		if(!KingTellerJudgeUtils.isEmpty(getIntent().getStringExtra(UPLOAD_NUM)) && 
				Bimp.tempSelectBitmap != null){
				Bimp.tempSelectBitmap.clear();
		}

	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 9){
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView,ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}
			
			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
			public Button item_delete;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				
				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);
				
				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}


	private void dealPic(final String tydId,final String tydzt){
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				AjaxFilesParams params = new AjaxFilesParams();

				params.put("tydId", getIntent().getStringExtra("tydId"));
				params.put("tydzt", getIntent().getStringExtra("tydzt"));
				params.put("userId", User.getInfo(PhotoMainActivity.this).getUserId());
				
				for(int i = 0 ; i < Bimp.tempSelectBitmap.size(); i ++){
					File file = BitmapUtils.createBitmap(Bimp.tempSelectBitmap.get(i).getImagePath(), 
							KingTellerStaticConfig.DefaultImgMaxWidth,
							KingTellerStaticConfig.DefaultImgMaxHeight,"name"+i);
					params.put("files", file);
					params.put("filesFileName", file.getName());
				}
				
				Message msg = new Message();
				msg.what = SUCCESS;
				msg.obj = params;
				mHandler.sendMessage(msg);

			}
		}).start();
	}
	
	protected void submitPic(AjaxFilesParams params) {
		// TODO Auto-generated method stub
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WljkUploadPicUrl), params,
				new AjaxHttpCallBack<ReturnBackStatus>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(PhotoMainActivity.this,
								"正在上传...");
					}
					
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(ReturnBackStatus data) {
						// TODO Auto-generated method stub
						if(data.getResult().trim().equals("success")){
							T.showShort(mContext, "上传成功!");
							Intent intent = new Intent();
							intent.putExtra(LogisticTaskActivity.TYDID, getIntent().getStringExtra("tydId"));
							intent.putExtra(LogisticTaskActivity.UPLOAD_NUM, String.valueOf(noScrollgridview.getChildCount()));
							setResult(RESULT_OK, intent);
							finish();
						} else{
							T.showShort(mContext, "上传失败!");
						}
					}
				});
	}

	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.button_left:
			finish();
			break;
		case R.id.btn_submit:
			
/*			if(Bimp.tempSelectBitmap.size() != upload_num){
 				T.showShort(mContext, "需要上传"+upload_num+"张照片!");
				return;
			}*/
			
			new KTAlertDialog.Builder(this)
			.setTitle("提示")
			.setMessage("确定要上传吗?")
			.setPositiveButton("确定",
					new KTAlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int pos) {
							dialogInterface.dismiss();
							dealPic(tydId,tydzt);
						}
					})
			.setNegativeButton("取消",
					new KTAlertDialog.OnClickListener() {
						public void onClick(DialogInterface dialogInterface, int paramAnonymousInt) {
							dialogInterface.dismiss();
						}
					}).create().show();
			break;
		case R.id.btn_cancel:
			finish();
			break;
		default:
			break;
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for(int i=0;i<PublicWay.activityList.size();i++){
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
			System.exit(0);
		}
		return true;
	}

}

