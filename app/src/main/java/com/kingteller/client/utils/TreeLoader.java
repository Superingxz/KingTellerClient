package com.kingteller.client.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.kingteller.client.activity.common.TreeChooserActivity;
import com.kingteller.client.bean.common.TreeBean;

/**
 * 树形处理
 * @author 王定波
 * 
 */
public class TreeLoader extends AsyncTaskLoader<List<TreeBean>> {

	private List<TreeBean> mData;
	private String mPid;
	private String mType;
	public TreeLoader(Context context, String pid, String type) {
		super(context);
		this.mPid = pid;
		this.mType = type;
	}

	//PID()和ID
	@Override
	public List<TreeBean> loadInBackground() {
		List<TreeBean> lists = new ArrayList<TreeBean>();
		int length = TreeChooserActivity.datalist.size();
		for (int i = 0; i < TreeChooserActivity.datalist.size(); i++) {
			if("备件".equals(mType)){
				TreeChooserActivity.datalist.get(i).setLast(true);
				lists.add(TreeChooserActivity.datalist.get(i));
			}else{
				if (TreeChooserActivity.datalist.get(i).getPid().equals(mPid)) {
					for (int j = 0; j < length; j++) {
						if (TreeChooserActivity.datalist.get(i).getId().equals(TreeChooserActivity.datalist.get(j).getPid())) {
							TreeChooserActivity.datalist.get(i).setLast(false);
							break;
						} else if (j == length - 1) {
							TreeChooserActivity.datalist.get(i).setLast(true);
						}
					}
	
					lists.add(TreeChooserActivity.datalist.get(i));
				}else if(TreeChooserActivity.datalist.get(i).getId().equals(mPid)){
					for (int j = 0; j < length; j++) {
						TreeChooserActivity.datalist.get(i).setLast(true);
					}
					lists.add(TreeChooserActivity.datalist.get(i));
				}
			}
		}
		return lists;
	}

	@Override
	public void deliverResult(List<TreeBean> data) {
		if (isReset()) {
			return;
		}
		mData = data;
		if (isStarted())
			super.deliverResult(data);

	}

	@Override
	protected void onStartLoading() {
		if (mData != null)
			deliverResult(mData);

		if (takeContentChanged() || mData == null)
			forceLoad();
	}

	@Override
	protected void onStopLoading() {
		cancelLoad();
	}

	@Override
	protected void onReset() {
		onStopLoading();

		if (mData != null) {
			mData = null;
		}
	}

	@Override
	public void onCanceled(List<TreeBean> data) {
		super.onCanceled(data);
	}

}