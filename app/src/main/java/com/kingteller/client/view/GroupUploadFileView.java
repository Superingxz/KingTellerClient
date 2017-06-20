package com.kingteller.client.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.activity.common.PicViewActivity;
import com.kingteller.client.bean.account.LoginBean;
import com.kingteller.client.bean.common.PicDesc;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.BitmapUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.GroupPicGridView.OnItemClickLister;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleGroupView;
import com.kingteller.framework.http.AjaxFilesParams;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 通用附件上传
 * 
 * @author 王定波
 * 
 */
public class GroupUploadFileView extends LinearLayout {

	private boolean isOpt = true;
	private final static int UPLOADFILE = 1;
	private static int nowUploadIndex = -1;
	private OnUploadLister uplodlister;
	
	
	private Handler handler = new Handler() {
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPLOADFILE:
				nowUploadIndex++;
				if (nowUploadIndex == list.size()) {
					if (uplodlister != null)
						uplodlister.OnUploadedListener(list);
					dialog.dismiss();
				} else {
					if (list.get(nowUploadIndex).getPath().startsWith("http://")) {
						handler.sendEmptyMessage(UPLOADFILE);
						list.get(nowUploadIndex).setStatus(PicDesc.UPLOAD_SUCCESS);
					} else{
						
					}
						//upLoadFile();
				}
				break;

			default:
				break;
			}
		};
	};
	private List<PicDesc> list = new ArrayList<PicDesc>();

	public void setOpt(boolean isOpt) {
		this.isOpt = isOpt;
	}

	private OnItemClickLister lister;
	private DownLoadProgressView progressView;
	private KTAlertDialog dialog;
	
	private Button item_delete, item_play;
	private ImageView item_add_pic;
	private TextView item_name, item_bfb, item_iscg;
	private ProgressBar item_jdbar;
	private View mItemView;
	
	public GroupUploadFileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public GroupUploadFileView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		addPicItem();

		progressView = new DownLoadProgressView(getContext());
		progressView.setProgress(0);

		dialog = new KTAlertDialog.Builder(getContext()).setView(progressView)
				.setTitle("文件上传").create();
	}

//	public void addAndsetItem(String path) {
//
//		View item = LayoutInflater.from(getContext()).inflate(
//				R.layout.item_add_pic, null);
//
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.MATCH_PARENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT);
//		lp.setMargins(0, 10, 0, 0);
//		item.setLayoutParams(lp);
//
//		ImageView item_add_pic = (ImageView) item
//				.findViewById(R.id.item_add_pic);
//		item_add_pic.setImageBitmap(BitmapUtils.decodeStream(path,
//				KingTellerConfig.DefaultImgMaxWidth,
//				KingTellerConfig.DefaultImgMaxHeight));
//	}

	public void addPicItem() {
 
		final View item = LayoutInflater.from(getContext()).inflate(
				R.layout.item_add_file_desc, null);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		item.setLayoutParams(lp);

		item_delete = (Button) item.findViewById(R.id.item_delete);
		item_play = (Button) item.findViewById(R.id.item_play);
		item_add_pic = (ImageView) item.findViewById(R.id.item_add_pic);
		item_name = (TextView) item.findViewById(R.id.item_name);
		item_bfb = (TextView) item.findViewById(R.id.item_bfb);
		item_iscg = (TextView) item.findViewById(R.id.item_iscg);
		item_jdbar = (ProgressBar) item.findViewById(R.id.item_jdbar);

		
		
		item_add_pic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
//				int length = getChildCount();
//				int pos = 0;
//				for (int i = 0; i < length; i++) {
//					if (getChildAt(i).equals(view)) {
//						pos = i;
//						break;
//					}
//				}
				
				Object path = item_add_pic.getTag();
				if (path != null) {
					if (KingTellerJudgeUtils.isPicure((String) path)) {//查看图片
						Intent intent = new Intent(getContext(),PicViewActivity.class);
						intent.putExtra(PicViewActivity.PICPATH, (String) path);
						getContext().startActivity(intent);
					}else{//查看文件
						
					}
				}
				
//				if (lister != null) {
//					
////					else { //打开dialog
////						lister.OnItemClick(view, pos);
////					}
//				}
			}
		});
		
		item_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				removeView(item);
			}
		});
		
		item_play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//removeView(item);
			}
		});
		
		mItemView = item;
	}
	
	/**
	 * 添加一个视图
	 */
	public void setaddView() {
		addPicItem();
		addView(mItemView);
	}
	
	/**
	 * 设置图标 与 文件名
	 */
	public void setItemImageView(String path) {
		View item = getChildAt(getChildCount() - 1);
		File f;
		if (KingTellerJudgeUtils.isPicure(path)){
			f = new File(path);
			((ImageView) item.findViewById(R.id.item_add_pic)).setImageBitmap(BitmapUtils.decodeStream(path, 
					KingTellerStaticConfig.DefaultImgMaxWidth,
					KingTellerStaticConfig.DefaultImgMaxHeight));
			((TextView) item.findViewById(R.id.item_name)).setText(f.getName());
			
		} else {
			f = new File(path);
			((ImageView) item.findViewById(R.id.item_add_pic)).setImageResource(R.drawable.ic_file);
			((TextView) item.findViewById(R.id.item_name)).setText(f.getName());
		}
		item_add_pic.setTag(path);
	}

	/**
	 * 设置点击上传的监听
	 * @param lister
	 */
	public void setOnItemClickLister(OnItemClickLister lister) {
		this.lister = lister;
	}

//	/**
//	 * 上传文件返回要上传文件的数量 
//	 * @return 要上传文件的数量
//	 */
//	public int upLoadFiles() {
//
//		list = new ArrayList<PicDesc>();
//		int length = getChildCount();
//		for (int i = 0; i < length; i++) {
//			View item = getChildAt(i);
//			ImageView item_add_pic = (ImageView) item.findViewById(R.id.item_add_pic);
//			EditText item_desc = (EditText) item.findViewById(R.id.item_desc);
//
//			if (item_add_pic.getTag() != null) {
//				PicDesc data = new PicDesc();
//				data.setDesc(item_desc.getText().toString());
//				data.setPath((String) item_add_pic.getTag());
//				data.setStatus(PicDesc.UPLOAD_WAITING);
//				list.add(data);
//			}
//		}
//
//		if (list.size() > 0) {
//			nowUploadIndex = -1;
//			handler.sendEmptyMessage(UPLOADFILE);
//			dialog.show();
//		}
//
//		return list.size();
//	}
//
	/**
	 * 单独上传处理
	 */
	private void upLoadFile() {
		AjaxFilesParams params = new AjaxFilesParams();
		KTHttpClient fh = new KTHttpClient(true);
		fh.post(KingTellerConfigUtils.CreateUrl(getContext(), KingTellerUrlConfig.LoginUrl),
				params, new AjaxHttpCallBack<LoginBean>(getContext(), true) {

					@Override
					public void onStart() {
						progressView.setMsg("上传文件(" + (nowUploadIndex + 1) + ""
								+ list.size() + "):");
					}

					@Override
					public void onUplodinging(long count, long current) {
						// TODO Auto-generated method stub
						int progress = (int) (100 * current / count);
						progressView.setProgress(progress);
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						handler.sendEmptyMessage(UPLOADFILE);
					}

					@Override
					public void onDo(LoginBean data) {
						if (uplodlister != null) {
							uplodlister
									.OnItemUploadedListener(null, true, "成功");
						}

						// 设置图片地址
						getChildAt(nowUploadIndex).findViewById(
								R.id.item_add_pic).setTag("");
						list.get(nowUploadIndex).setStatus(PicDesc.UPLOAD_SUCCESS);
						list.get(nowUploadIndex).setPath("");
					};

					@Override
					public void onError(int errorNo, String strMsg) {
						// TODO Auto-generated method stub
						if (uplodlister != null)
							uplodlister.OnItemUploadedListener(null, false,
									strMsg);
						list.get(nowUploadIndex).setStatus(PicDesc.UPLOAD_FAILED);
					}

				});
	}
//
//	/**
//	 * 获取上传的图片组
//	 * @return
//	 */
//	public List<PicDesc> getDataList()
//	{
//		List<PicDesc> list = new ArrayList<PicDesc>();
//		int length = getChildCount();
//		for (int i = 0; i < length; i++) {
//			View item = getChildAt(i);
//			ImageView item_add_pic = (ImageView) item
//					.findViewById(R.id.item_add_pic);
//			EditText item_desc = (EditText) item.findViewById(R.id.item_desc);
//
//			if (item_add_pic.getTag() != null) {
//				PicDesc data = new PicDesc();
//				data.setDesc(item_desc.getText().toString());
//				data.setPath((String) item_add_pic.getTag());
//				data.setStatus(PicDesc.UPLOAD_WAITING);
//				list.add(data);
//			}
//		}
//		return list;
//	}
	
	public interface OnUploadLister {
		/**
		 * 全部上传的回调
		 */
		public void OnUploadedListener(List<PicDesc> list);

		/**
		 * 每一个上传回调
		 * 
		 * @param data
		 * @param isSuccess
		 * @param errMsg
		 */
		public void OnItemUploadedListener(PicDesc data, boolean isSuccess,
				String errMsg);
	}
}
