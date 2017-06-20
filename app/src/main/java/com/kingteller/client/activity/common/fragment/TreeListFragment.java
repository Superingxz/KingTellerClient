package com.kingteller.client.activity.common.fragment;

import java.util.List;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Html;
import android.view.View;
import android.widget.ListView;

import com.kingteller.R;
import com.kingteller.client.activity.common.TreeChooserActivity;
import com.kingteller.client.adapter.TreeListAdapter;
import com.kingteller.client.bean.common.TreeBean;
import com.kingteller.client.utils.TreeLoader;

/**
 * 通用树形选择器
 * 
 * @author 王定波
 * 
 */
public class TreeListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<List<TreeBean>> {

	private static final int LOADER_ID = 1;

	private TreeListAdapter mAdapter;
	private String mPid;
	private String mType;
	/**
	 * Create a new instance with the given file path.
	 * 
	 * @param path
	 *            The absolute path of the file (directory) to display.
	 * @return A new Fragment with the given file path.
	 */
	public static TreeListFragment newInstance(String pid) {
		TreeListFragment fragment = new TreeListFragment();
		Bundle args = new Bundle();
		args.putString(TreeChooserActivity.PID, pid);
		fragment.setArguments(args);

		return fragment;
	}
	
	//维护报告
	public static TreeListFragment newInstance(String pid, String type) {
		TreeListFragment fragment = new TreeListFragment();
		Bundle args = new Bundle();
		args.putString(TreeChooserActivity.PID, pid);
		args.putString(TreeChooserActivity.TYPE, type);
		fragment.setArguments(args);

		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAdapter = new TreeListAdapter(getActivity());
		mPid = getArguments() != null ? getArguments().getString(
				TreeChooserActivity.PID) : "0";
		mType = getArguments() != null ? getArguments().getString(
				TreeChooserActivity.TYPE) : "";
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setEmptyText(Html.fromHtml("<font color='#000000'>没有数据</font>"));
		setListAdapter(mAdapter);
		setListShown(false);
		getListView().setDivider(new ColorDrawable(getResources().getColor(R.color.divider_gray)));
		getListView().setDividerHeight(1);
		getLoaderManager().initLoader(LOADER_ID, null, this);

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		TreeListAdapter adapter = (TreeListAdapter) l.getAdapter();
		if (adapter != null) {
			TreeBean data = (TreeBean) adapter.getItem(position);
			mPid = data.getId();
			((TreeChooserActivity) getActivity()).onFileSelected(data);
		}
	}

	@Override
	public Loader<List<TreeBean>> onCreateLoader(int id, Bundle args) {

		return new TreeLoader(getActivity(), mPid, mType);
	}

	@Override
	public void onLoadFinished(Loader<List<TreeBean>> loader,
			List<TreeBean> data) {
		((TreeChooserActivity) getActivity()).getListviewObj().setVisibility(View.GONE);
		mAdapter.setListItems(data);
		if (isResumed())
			setListShown(true);
		else
			setListShownNoAnimation(true);
	}

	@Override
	public void onLoaderReset(Loader<List<TreeBean>> loader) {
		mAdapter.clear();
	}
}