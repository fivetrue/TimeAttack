package com.fivetrue.timeattack.activity;

import java.util.ArrayList;

import com.api.common.BaseEntry;
import com.api.google.geocoding.model.AddressResultVO;
import com.api.google.place.model.PlaceVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.MapActivityManager;
import com.fivetrue.timeattack.fragment.map.NearBySearchFragment;
import com.fivetrue.timeattack.preference.MapPreferenceManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MapActivity extends BaseActivity {
	
	class ViewHolder{
		public View ivPlace = null;
		public View ivDirection = null;
		public ViewGroup layoutMyControl = null;
	}

	//View
	private ViewGroup mContentView = null;
	
	//Map
	private SupportMapFragment mMapFragment = null;
	private GoogleMap mMap = null;
	
	//Fragment
	private NearBySearchFragment mNearBySearchFragment = null;
	
	//Model
	private BaseEntry mEntry = null;
	private int mType = INVALID_VALUE;
	private MapActivityManager mMapManager = null;
	private ViewHolder mMyControlView = null;
	private MyLocationSearchAsycTask mMyLocationAyncTask = null;
	
	private Location mMyLocation = null;

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
	
	private void initViews(){
		mMyControlView = new ViewHolder();
		
		mMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_map);
		
		mMyControlView.layoutMyControl = (ViewGroup) mContentView.findViewById(R.id.layout_map_my_control);
		mMyControlView.ivDirection = mContentView.findViewById(R.id.iv_map_direction);
		mMyControlView.ivPlace = mContentView.findViewById(R.id.iv_map_place);
		
		mMyControlView.layoutMyControl.setVisibility(View.GONE);
		mMyControlView.ivPlace.setOnClickListener(onClickFindSubwayNearBy);
		mMyControlView.ivDirection.setOnClickListener(onClickFindDirection);
	}
	
	private void initModels(){
		
		if(mMapFragment != null){
			MapPreferenceManager pref = MapPreferenceManager.newInstance(this);
			mMap = mMapFragment.getMap();
			mMap.setMapType(pref.getMapType());
			mMap.getUiSettings().setRotateGesturesEnabled(pref.isRotateEnable());
			mMap.getUiSettings().setZoomControlsEnabled(pref.isZoomButtonEnable());
			mMap.setBuildingsEnabled(pref.isBuildingEnable());
			mMap.setIndoorEnabled(pref.isIndoorEnable());
			mMapManager = MapActivityManager.newInstance(this);
		}else{
			log("map fragment is null");
		}
	}
	
	private void initDatas(){
		
		if(mEntry != null && mMap != null){
			
			switch(mType){
			case MapActivityManager.DATA_GEOCODING : 
				mMapManager.drawPointToMap(mMap, (AddressResultVO)mEntry, mZoomValue);
				return;
				
			case MapActivityManager.DATA_DIRECTION :
				return;
			
			case MapActivityManager.DATA_PLACE :
				
				return;
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
//			mMyLocation = location;
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
				onStartLocationService();
				mMyLocationAyncTask.execute();
			}else{
				onPauseLocationService();
				mMyControlView.layoutMyControl.setVisibility(View.GONE);
				mMyLocationAyncTask.cancel(true);
				mMyLocationAyncTask = null;
				mMap.setMyLocationEnabled(false);
			}
		}else{
			makeToast(R.string.error_location_out_of_service);
		}
	}
	
	
	/**
	 * @author Fivetrue
	 * My Location Search Async
	 */
	private class MyLocationSearchAsycTask extends AsyncTask<Void, Void, Location>{
		
		private GoogleMap mMap = null;
		
		public MyLocationSearchAsycTask(GoogleMap map) {
			// TODO Auto-generated constructor stub
			mMap = map;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected Location doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Location location = null;
			while(location == null){
				location = getCurrentLocation();
				if(isCancelled()){
					break;
				}
			}
			return location;
		}
		@Override
		protected void onPostExecute(Location result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null && mMap != null){
				mMyLocation = result;
				LatLng latlng = new LatLng(result.getLatitude(), result.getLongitude());
				mMapManager.zoomToPoint(mMap, latlng, mMyLocationZoomValue);
				mMyControlView.layoutMyControl.setVisibility(View.VISIBLE);
				mMap.setMyLocationEnabled(true);
			}
		}
	}
	
	private OnClickListener onClickFindSubwayNearBy = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Bundle argument = new Bundle();
			argument.putParcelable(MapActivityManager.MAP_DATA, mMyLocation);
			mNearBySearchFragment = (NearBySearchFragment) createFragment(NearBySearchFragment.class, 
					"nearby", FragmentTransaction.TRANSIT_ENTER_MASK, argument
					, R.anim.slide_in_top, R.anim.slide_in_top);

			new AsyncTask<NearBySearchFragment, Void, ArrayList<PlaceVO>>(){

				@Override
				protected ArrayList<PlaceVO> doInBackground(
						NearBySearchFragment... params) {
					// TODO Auto-generated method stub
					NearBySearchFragment f = params[0];
					ArrayList<PlaceVO> arr = null;
					
					while(true){
						if(f != null){
							arr = f.getPlaceList();
							if(arr != null){
								break;
							}
						}else{
							break;
						}
					}
					return arr;
				}
				
				protected void onPostExecute(java.util.ArrayList<PlaceVO> result) {
					MapActivityManager.newInstance(MapActivity.this).drawPointToMap(mMap, result);
				};
			}.execute(mNearBySearchFragment);
		}
	};
	
	private OnClickListener onClickFindDirection = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		}
	};
	
	public void onBackPressed(){
		if(mNearBySearchFragment != null){
			removeFragment(mNearBySearchFragment, R.anim.slide_out_bottom, R.anim.slide_out_bottom);
			mNearBySearchFragment = null;
		}else{
			super.onBackPressed();
		}
	};
}
