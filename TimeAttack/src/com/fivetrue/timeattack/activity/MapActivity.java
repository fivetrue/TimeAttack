package com.fivetrue.timeattack.activity;

import com.api.common.BaseEntry;
import com.api.google.geocoding.model.AddressResultVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.MapActivityManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.AsyncTask;
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
	
	private MyLocationSearchAsycTask mMyLocationAyncTask= null;

	
	//Value
	private float mZoomValue = 14;
	private float mMyLocationZoomValue = 18;



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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		onPauseLocationService();
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
			return R.menu.actionbar_map_geocoding_menu;
			
		case MapActivityManager.DATA_DIRECTION :
			break;
		
		case MapActivityManager.DATA_PLACE :
			
			break;
		}
		return R.menu.actionbar_map_default_menu;
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
		makeToast(R.string.error_location_out_of_service);
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
		if(location != null){
			mMap.clear();
			LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
			mMapManager.drawPointToMap(mMap, "현재 위치", latlng);
		}
	}
		
	// 지도정보에서 현재위치 액션버튼 눌렀을 경우.
	@Override
	void onClickAcitionMenuLocationSearch(final View view) {
	// TODO Auto-generated method stub
		if(isGpsEnable()){
			view.setSelected(!view.isSelected());
			
			if(mMyLocationAyncTask == null){
				mMyLocationAyncTask = new MyLocationSearchAsycTask(mMap);
			}
			if(view.isSelected()){
				mMyLocationAyncTask.execute();
			}else{
				mMyLocationAyncTask.cancel(true);
				mMyLocationAyncTask = null;
			}
		}else{
			makeToast(R.string.error_location_out_of_service);
		}
	}
	
	OnMarkerClickListener onMyLocationMarkerClickListener = new OnMarkerClickListener() {
		
		@Override
		public boolean onMarkerClick(Marker marker) {
			// TODO Auto-generated method stub
			if(marker != null){
				
			}
			return false;
		}
	};
	
	private class MyLocationSearchAsycTask extends AsyncTask<Void, Void, Location>{
		
		private boolean runLocationSearching = false;
		private boolean isRunningNowLocation = false;
		private GoogleMap mMap = null;
		
		public MyLocationSearchAsycTask(GoogleMap map) {
			// TODO Auto-generated constructor stub
			mMap = map;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			isRunningNowLocation = true;
			runLocationSearching = true;
			onStartLocationService();
		}
		@Override
		protected Location doInBackground(Void... params) {
			// TODO Auto-generated method stub
			while(runLocationSearching){
				if(getCurrentLocation() != null){
					runLocationSearching = false;
				}
				
				if(isCancelled()){
					isRunningNowLocation = false;
					break;
				}
			}
			return getCurrentLocation();
		}
		@Override
		protected void onPostExecute(Location result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null && mMap != null){
				LatLng latlng = new LatLng(result.getLatitude(), result.getLongitude());
				MarkerOptions maker = mMapManager.drawPointToMap(mMap, "현재 위치", latlng);
				mMapManager.zoomToPoint(mMap, latlng, mMyLocationZoomValue);
				mMap.setOnMarkerClickListener(onMyLocationMarkerClickListener);
			}
			if(!isRunningNowLocation){
				onPauseLocationService();
			}
		}
		
		@Override
		protected void onCancelled(Location result) {
			// TODO Auto-generated method stub
			super.onCancelled(result);
			onPauseLocationService();
		}
	}
}
