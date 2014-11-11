package com.fivetrue.timeattack.activity;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.PagerFragment;
import com.fivetrue.timeattack.fragment.PagerFragment.OnSelectedFragmentNameListener;
import com.fivetrue.timeattack.fragment.main.RecentlyUseFragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

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
		mPagerFragment.addFragment(new RecentlyUseFragment(), getString(R.string.recently_infomation));
		mPagerFragment.addFragment(new RecentlyUseFragment(), "주변 찾기 !!!!");
		mPagerFragment.addFragment(new RecentlyUseFragment(), "주변 찾기 222");
		mPagerFragment.addFragment(new RecentlyUseFragment(), "주변 찾기33333 ");
		return null;
	}

	@Override
	public String getActionBarTitleName() {
		// TODO Auto-generated method stub
		return getString(R.string.activity_home);
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

	@Override
	public int getActionBarMenuResource() {
		// TODO Auto-generated method stub
		return R.menu.actionbar_main_menu;
	}

	@Override
	public void requsetNetworkResultSuccess() {
		// TODO Auto-generated method stub

		if(mPagerFragment != null){
			for(Fragment f : mPagerFragment.getFragmentList()){
				if(f instanceof RecentlyUseFragment){
					((RecentlyUseFragment)f).onLoadListData();
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
}
