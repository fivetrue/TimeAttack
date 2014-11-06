package com.fivetrue.timeattack.activity;

import com.api.common.BaseEntry;
import com.api.google.geocoding.entry.GeocodingEntry;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.MapActivityManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapActivity extends BaseActivity {

	//View
	private ViewGroup mContentView = null;
	
	//Map
	private SupportMapFragment mMapFragment = null;
	private GoogleMap mMap = null;
	
	//Model
	private BaseEntry mEntry = null;
	private String mType = null;
	private MapActivityManager mMapManager = null;
	
	//Value
	
	private float mZoomValue = 14;



	@Override
	View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		mContentView = (ViewGroup) inflater.inflate(R.layout.activity_map, null);
		
		getIntentData();
		initViews();
		initModels();
		initDatas();
		return mContentView;
	}

	private void initViews(){
		mMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_map);
	}
	private void initModels(){
		if(mMapFragment != null){
			mMap = mMapFragment.getMap();
			mMap.setMyLocationEnabled(true);
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mMap.getUiSettings().setRotateGesturesEnabled(false);
			mMapManager = MapActivityManager.newInstance(this);
		}else{
			log("map fragment is null");
		}
	}

	private void getIntentData(){
		Bundle b = getIntent().getExtras(); 
		if(b != null){
			mType = b.getString(MapActivityManager.MAP_DATA_TYPE);
			mEntry = b.getParcelable(MapActivityManager.MAP_DATA);
			if(TextUtils.isEmpty(mType) || mEntry == null){
				log("entry or type is null");
				return ;
			}
		}
	}
	
	private void initDatas(){
		
		if(!TextUtils.isEmpty(mType) && mEntry != null && mMap != null){
			if(mType.equals(MapActivityManager.DATA_GEOCODING)){
				mMapManager.drawPointToMap(mMap, (GeocodingEntry)mEntry, mZoomValue);
			}else if(mType.equals(MapActivityManager.DATA_DIRECTION)){
			}else if(mType.equals(MapActivityManager.DATA_PLACE)){
			}
		}
	}

	@Override
	String getActionBarTitleName() {
		// TODO Auto-generated method stub
		return getString(R.string.activity_map);
	}

	@Override
	String getActionBarSubTitle() {
		// TODO Auto-generated method stub
		String subTitle = null;
		if(!TextUtils.isEmpty(mType)){
			if(mType.equals(MapActivityManager.DATA_GEOCODING)){
				subTitle = getString(R.string.location_infomation);
			}else if(mType.equals(MapActivityManager.DATA_DIRECTION)){
			}else if(mType.equals(MapActivityManager.DATA_PLACE)){
			}
		}
		return subTitle;
	}

	@Override
	int getActionBarMenuResource() {
		// TODO Auto-generated method stub
		int menuRes = INVALID_VALUE;
		if(TextUtils.isEmpty(mType)){
			menuRes = R.menu.actionbar_map_menu;
		}
		return menuRes;
	}

	@Override
	boolean isHomeAsUp() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	void requsetNetworkResultSuccess() {
		// TODO Auto-generated method stub

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
