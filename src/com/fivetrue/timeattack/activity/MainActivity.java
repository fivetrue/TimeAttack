package com.fivetrue.timeattack.activity;

import java.util.ArrayList;


import com.api.google.geocoding.entry.GeocodingEntry;
import com.api.google.place.entry.PlacesDetailEntry;
import com.api.google.place.entry.PlacesEntry;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.database.NetworkResultDBManager;
import com.fivetrue.timeattack.fragment.BaseFragment;
import com.fivetrue.timeattack.fragment.PagerFragment;
import com.fivetrue.timeattack.fragment.PagerFragment.OnSelectedFragmentNameListener;
import com.fivetrue.timeattack.fragment.main.BaseRecentlyListFragment;
import com.fivetrue.timeattack.fragment.main.RecentlyAddressSearchListFragment;
import com.fivetrue.timeattack.fragment.main.RecentlyPlacesListFragment;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends BaseActivity{
	
	private PagerFragment mPagerFragment = null;
	private NetworkResultDBManager mDbManager = null;
	
	private RecentlyAddressSearchListFragment mRecentlyGeocodingFragment = null;
	private RecentlyPlacesListFragment mRecentlyPlaceFragment = null;
	

	@Override
	public View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		initData();
		initView();
		initFragment();
		return null;
	}
	
	private void initData(){
		mDbManager = new NetworkResultDBManager(this);
	}
	
	private void initView(){
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
	}
	
	private void updateFragmentData(){
		if(mRecentlyGeocodingFragment != null){
			mRecentlyGeocodingFragment.onResetData(getRecentlyGeocodingFromDB().getAddressList());
		}
		
		if(mRecentlyPlaceFragment != null){
			mRecentlyPlaceFragment.onResetData(getRecentlyPlacesDetailFromDB().getPlaceList());
		}
	}
	
	private void initFragment(){
		mRecentlyGeocodingFragment = new RecentlyAddressSearchListFragment();
		GeocodingEntry geocodingEntry = getRecentlyGeocodingFromDB();
		if(geocodingEntry != null){
			Bundle argument = new Bundle();
			argument.putParcelable(BaseRecentlyListFragment.RECENTRY_DATA, geocodingEntry);
			mRecentlyGeocodingFragment.setArguments(argument);
		}
		addFragmentToPager(mRecentlyGeocodingFragment, getString(R.string.recently_search_address), mPagerFragment, null);
		
		
		mRecentlyPlaceFragment = new RecentlyPlacesListFragment();
		PlacesEntry placesEntry = getRecentlyPlacesDetailFromDB();
		if(placesEntry != null){
			Bundle argument = new Bundle();
			argument.putParcelable(BaseRecentlyListFragment.RECENTRY_DATA, placesEntry);
			mRecentlyPlaceFragment.setArguments(argument);
		}
		addFragmentToPager(mRecentlyPlaceFragment, getString(R.string.recently_search_place), mPagerFragment, null);
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

	@Override
	public void requsetNetworkResultSuccess(String url, String request) {
		// TODO Auto-generated method stub
		updateFragmentData();
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
	
	@Override
	protected boolean isSettingNetworkResultBroadcast() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void addFragmentToPager(BaseFragment f, String name, PagerFragment pager, Bundle arg){
		if(f != null && !TextUtils.isEmpty(name) && pager != null){
			if(arg != null){
				f.setArguments(arg);
			}
			pager.addFragment(f, name);
		}
		
	}
	
	private GeocodingEntry getRecentlyGeocodingFromDB(){
		GeocodingEntry geocoding = null;
		
		if(mDbManager != null){
			geocoding = new GeocodingEntry();
			ArrayList<GeocodingEntry> geocodings = mDbManager.getGeocodingNetworkResults();
			
			if(geocodings != null){
				for(GeocodingEntry entry : geocodings){
					if(entry != null && entry.getAddressList() != null && entry.getAddressList().size() > 0){
						geocoding.getAddressList().addAll(entry.getAddressList());
					}
				}
			} 
		}
		return geocoding;
	}
	
	private PlacesEntry getRecentlyPlacesDetailFromDB(){
		PlacesEntry place = null;
		
		if(mDbManager != null){
			place = new PlacesEntry();
			ArrayList<PlacesDetailEntry> places = mDbManager.getPlacesNetworkResults();
			
			if(places != null){
				for(PlacesEntry entry : places){
					if(entry != null && entry.getPlaceList() != null && entry.getPlaceList().size() > 0){
						place.getPlaceList().addAll(entry.getPlaceList());
					}
				}
			} 
		}
		return place;
	}
	
	
}
