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
	public View onCreateView(LayoutInflater inflater) {
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
		
		mMyControlView.ivPlace.setOnClickListener(onClickFindSubwayNearBy);
		mMyControlView.ivDirection.setOnClickListener(onClickFindDirection);
		
		switch(mType){
		case MapActivityManager.DATA_GEOCODING :
			mMyControlView.layoutMyControl.setVisibility(View.VISIBLE);
			break;

		case MapActivityManager.DATA_DIRECTION :
			break;

		case MapActivityManager.DATA_PLACE :

			break;
			
		default :
			mMyControlView.layoutMyControl.setVisibility(View.GONE);
			break;
		}
		
		getCustomActionBar().setBackGroundColorRes(R.color.map_primary_color, R.color.map_primary_dark_color);
		getCustomActionBar().setHomeIconLineColor(R.color.map_primary_light_color);
		getCustomActionBar().setIconSelector(R.drawable.selector_map_primary_color);
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
	public String getActionBarTitleName() {
		// TODO Auto-generated method stub
		return getString(R.string.activity_map);
	}

	@Override
	public String getActionBarSubTitle() {
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
	public int getActionBarMenuResource() {
		// TODO Auto-generated method stub
		
		switch(mType){
//		case MapActivityManager.DATA_GEOCODING : 
//			return R.menu.actionbar_map_geocoding_menu;
			
		case MapActivityManager.DATA_DIRECTION :
			break;
		
		case MapActivityManager.DATA_PLACE :
			
			break;
		}
		return R.menu.actionbar_map_default_menu;
	}

	@Override
	public boolean isHomeAsUp() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void requsetNetworkResultSuccess() {
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
	public void onClickAcitionMenuLocationSearch(final View view) {
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
				mMyLocationAyncTask.cancel(true);
				mMyLocationAyncTask = null;
				mMap.setMyLocationEnabled(false);
				
				switch(mType){
				case MapActivityManager.DATA_GEOCODING :
					break;

				case MapActivityManager.DATA_DIRECTION :
					break;

				case MapActivityManager.DATA_PLACE :

					break;
					
				default :
					mMyControlView.layoutMyControl.setVisibility(View.GONE);
					break;
				}
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
			
			double latitude = INVALID_VALUE;
			double longitude = INVALID_VALUE;
			
			switch(mType){
			case MapActivityManager.DATA_GEOCODING :
				if(mEntry != null){
					latitude = Double.parseDouble(((AddressResultVO)mEntry).getLatitude());
					longitude = Double.parseDouble(((AddressResultVO)mEntry).getLongitude());
				}
				break;

			case MapActivityManager.DATA_DIRECTION :
				if(mEntry != null){
				}
				break;

			case MapActivityManager.DATA_PLACE :
				if(mEntry != null){
				}
				break;
				
			default :
				if(mMyLocation != null){
					latitude = mMyLocation.getLatitude();
					longitude = mMyLocation.getLongitude();
				}
				break;
			}
			
			if(latitude == INVALID_VALUE || longitude == INVALID_VALUE)
				return;
			
			LatLng location = new LatLng(latitude, longitude);
			
			argument.putParcelable(MapActivityManager.MAP_DATA, location);
			
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
					mMapManager.drawPointToMap(mMap, result);
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
	}

	@Override
	public boolean isActionBarBlending() {
		// TODO Auto-generated method stub
		return false;
	};
}
