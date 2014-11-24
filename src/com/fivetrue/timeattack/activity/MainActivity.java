package com.fivetrue.timeattack.activity;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.PagerFragment;
import com.fivetrue.timeattack.fragment.PagerFragment.OnSelectedFragmentNameListener;
import com.fivetrue.timeattack.fragment.main.RecentlyListFragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends BaseActivity{
	
	private PagerFragment mPagerFragment = null;

	@Override
	public View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		mPagerFragment = (PagerFragment) createFragment(PagerFragment.class, "pager", null);
		mPagerFragment.setOnSelectedFragmentName(new OnSelectedFragmentNameListener() {
			
			@Override
			public void onReceiveFragmentName(String name) {
				// TODO Auto-generated method stub
				if(!TextUtils.isEmpty(name)){
					getCustomActionBar().setTitle(name);
				}
			}
		});
		
		mPagerFragment.addFragment(new RecentlyListFragment(), getString(R.string.recently_infomation));
		return null;
	}

	@Override
	public String getActionBarTitleName() {
		// TODO Auto-generated method stub
		return getString(R.string.recently_infomation);
	}

	@Override
	public String getActionBarSubTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHomeAsUp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void outOfService(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void avaliableService(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void temporarilyServiceUnavailable(String provider, int status,
			Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void changeLocation(Location location) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public int getActionBarMenuResource() {
//		// TODO Auto-generated method stub
//		return R.menu.actionbar_main_menu;
//	}

	@Override
	public void requsetNetworkResultSuccess() {
		// TODO Auto-generated method stub

		if(mPagerFragment != null){
			for(Fragment f : mPagerFragment.getFragmentList()){
				if(f instanceof RecentlyListFragment){
					((RecentlyListFragment)f).onLoadListData();
				}
			}
		}
	}

	@Override
	public void onClickAcitionMenuLocationSearch(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActionBarBlending() {
		// TODO Auto-generated method stub
		return false ;
	}

	@Override
	public ViewGroup getActionBarMenuView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		ViewGroup menu = (ViewGroup) inflater.inflate(R.layout.actionbar_main_menu, null);
		menu.findViewById(R.id.action_item_searching).setOnClickListener(onClickActionBarItem);
		menu.findViewById(R.id.action_item_map).setOnClickListener(onClickActionBarItem);
		menu.findViewById(R.id.action_item_setting).setOnClickListener(onClickActionBarItem);
		return menu;
	}
}
