package com.kingteller.client.activity.workorder;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.view.View;
import android.view.View.OnClickListener;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseFragmentActivity;
import com.kingteller.modules.filechooser.FileListFragment;

public class FeeChooserActivity extends BaseFragmentActivity implements
		OnBackStackChangedListener {

	public static final String PATH = "path";
	private FragmentManager mFragmentManager;
	private String mPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_file_chooser);
		KingTellerApplication.addActivity(this);
		
		if (savedInstanceState == null) {
			mPath = "";
			addFragment(mPath);
		} else {
			mPath = savedInstanceState.getString(PATH);
		}

		title_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		setTitle(mPath);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		title_title.setText(title);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString(PATH, mPath);
	}
	
	@Override
	public void onBackStackChanged() {
		// TODO Auto-generated method stub
		mPath = "";
		
		int count = mFragmentManager.getBackStackEntryCount();
		if (count > 0) {
			BackStackEntry fragment = mFragmentManager
					.getBackStackEntryAt(count - 1);
			mPath = fragment.getName();
		}
		
		setTitle(mPath);
	}

	/**
	 * Add the initial Fragment with given path.
	 * 
	 * @param path The absolute path of the file (directory) to display.
	 */
	private void addFragment(String path) {
		FileListFragment explorerFragment = FileListFragment.newInstance(mPath);
		mFragmentManager.beginTransaction()
				.add(R.id.explorer_fragment, explorerFragment).commit();
	}

	/**
	 * "Replace" the existing Fragment with a new one using given path.
	 * We're really adding a Fragment to the back stack.
	 * 
	 * @param path The absolute path of the file (directory) to display.
	 */
	private void replaceFragment(String path) {
		FileListFragment explorerFragment = FileListFragment.newInstance(path);
		mFragmentManager.beginTransaction()
				.replace(R.id.explorer_fragment, explorerFragment)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.addToBackStack(path).commit();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
