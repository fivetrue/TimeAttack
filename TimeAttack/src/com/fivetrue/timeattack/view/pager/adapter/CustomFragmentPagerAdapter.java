package com.fivetrue.timeattack.view.pager.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class CustomFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
	
	private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
	
	public CustomFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		mFragmentList = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragmentList.size();
	}

	@Override
	public android.support.v4.app.Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return mFragmentList.get(arg0);
	}

}
