package com.fivetrue.timeattack.activity;

import com.api.common.BaseEntry;
import com.api.google.geocoding.model.AddressResultVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.MapActivityManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.location.Location;
import android.os.Bundle;
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
	private int mType = INVALID_VALUE;
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
//			mMap.setMyLocationEnabled(true);
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
			mType = b.getInt(MapActivityManager.MAP_DATA_TYPE, INVALID_VALUE);
			mEntry = b.getParcelable(MapActivityManager.MAP_DATA);
			if(mType == INVALID_VALUE || mEntry == null){
				log("entry or type is null");
				return ;
			}
		}
	}
	
	private void initDatas(){
		
		if(mType != INVALID_VALUE && mEntry != null && mMap != null){
			
			switch(mType){
			case MapActivityManager.DATA_GEOCODING : 
				mMapManager.drawPointToMap(mMap, (AddressResultVO)mEntry, mZoomValue);
				break;
				
			case MapActivityManager.DATA_DIRECTION :
				break;
			
			case MapActivityManager.DATA_PLACE :
				
				break;
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
		
		switch(mType){
		case MapActivityManager.DATA_GEOCODING : 
			return getString(R.string.location_infomation);
			
		case MapActivityManager.DATA_DIRECTION :
			break;
		
		case MapActivityManager.DATA_PLACE :
			
			break;
		}
		
		return null;
	}

	@Override
	int getActionBarMenuResource() {
		// TODO Auto-generated method stub
		
		switch(mType){
		case MapActivityManager.DATA_GEOCODING : 
			return R.menu.actionbar_map_menu;
			
		case MapActivityManager.DATA_DIRECTION :
			break;
		
		case MapActivityManager.DATA_PLACE :
			
			break;
		}
		return INVALID_VALUE;
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
