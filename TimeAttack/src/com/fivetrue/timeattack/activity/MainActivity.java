package com.fivetrue.timeattack.activity;

import com.fivetrue.timeattack.fragment.main.NearBySearchFragment;
import com.fivetrue.timeattack.fragment.main.RecentlyUseFragment;
import com.fivetrue.timeattack.fragment.tab.TabFragment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends BaseActivity{
	
	private TabFragment mTapFragment = null;

	@Override
	View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
//		View view = inflater.inflate(R.layout.activity_main, null);
//		if(view != null){
//			
//			view.findViewById(R.id.btn_add_map).setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
					// TODO Auto-generated method stub
//					FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
//					if(!fragmentSubway.isHidden()){
//						trans.hide(fragmentSubway);
//						trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//					}else{
//						if(fragmentSubway != null){
//						}
//						trans.add(R.id.layout_map, fragmentSubway);
//						trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//					}
//					trans.commit();
//				}
//			});
//		}else{
//			Toast.makeText(getApplicationContext(), "View null", Toast.LENGTH_SHORT).show();
//		}
	
		mTapFragment = (TabFragment) createFragment(TabFragment.class, "");
		mTapFragment.addFragment(new RecentlyUseFragment(), "최근");
		mTapFragment.addFragment(new NearBySearchFragment(), "주변 찾기");
		return null;
	}

	@Override
	String getActionBarTitleName() {
		// TODO Auto-generated method stub
		return "TimeAttack";
	}

	@Override
	String getActionBarSubTitle() {
		// TODO Auto-generated method stub
		return "MainActivty";
	}

	@Override
	boolean isHomeAsUp() {
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

	
}
