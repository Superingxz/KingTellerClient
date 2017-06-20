package com.kingteller.client.activity.base;

import com.kingteller.R;
import com.kingteller.client.view.ListViewObj;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Fragment的基类
 * 
 * @author 王定波
 * 
 */
public class BaseFragment extends Fragment {
	private ListViewObj listviewObj;
	protected boolean isVisible;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (getView().findViewById(R.id.loading_view) != null)
			setListviewObj(new ListViewObj(getView()));
	}

	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}

	protected void onVisible() {
		lazyLoad();
	}

	protected void lazyLoad() {
	}
	protected void onInvisible() {
	}

	@Override
	public void onResume() {
		super.onResume();
//		try {
//			
//			// 检查网络是否可用
//			if (getView().findViewById(R.id.net_error) != null) {
//				if (!CommonUtils.isNetAvaliable(getActivity())){
//					getView().findViewById(R.id.net_error).setVisibility(View.VISIBLE);
//					
//					/*写入网络不可用---调试代码 */
//					//CrashHandler.saveStaffLogInZtFile("staffLogInRecord.txt","当前网络不可用！");
//		
//				}else{
//					getView().findViewById(R.id.net_error).setVisibility(View.GONE);
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

	}

}
