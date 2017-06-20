package com.kingteller.client.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * 通用滑动页适配器
 * @author 王定波
 *
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
	private ArrayList<Fragment> fragmentsList;

	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
		super(fm);
		this.fragmentsList = fragments;
	}

	@Override
	public int getCount() {
		return fragmentsList.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentsList.get(arg0);
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

}
