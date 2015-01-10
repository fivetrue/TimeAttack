package com.fivetrue.timeattack.activity;


import java.util.List;

import com.api.common.BaseEntry;
import com.api.common.BaseResponseListener;
import com.api.google.directions.DirectionsAPIHelper;
import com.api.google.directions.DirectionsConstants;
import com.api.google.directions.entry.DirectionsEntry;
import com.api.google.directions.model.RouteVO;
import com.api.google.geocoding.model.AddressResultVO;
import com.api.google.place.PlaceAPIHelper;
import com.api.google.place.PlacesConstans;
import com.api.google.place.PlaceAPIHelper.API_TYPE;
import com.api.google.place.entry.PlacesEntry;
import com.api.google.place.model.PlaceVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.MapActivityManager;
import com.fivetrue.timeattack.activity.manager.MapActivityManager.OnSaveMapImageListener;
import com.fivetrue.timeattack.activity.manager.NearbyActivityManager;
import com.fivetrue.timeattack.fragment.map.DirectionsListFragment;
import com.fivetrue.timeattack.fragment.map.DirectionsListFragment.DirectionsItemClickListener;
import com.fivetrue.timeattack.fragment.map.NearBySearchListFragment;
import com.fivetrue.timeattack.fragment.map.NearBySearchListFragment.PlaceItemDetailClickListener;
import com.fivetrue.timeattack.ga.GoogleAnalyticsConstants.ACTION;
import com.fivetrue.timeattack.preference.MapPreferenceManager;
import com.fivetrue.timeattack.utils.ImageUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
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
	private NearBySearchListFragment mNearBySearchFragment = null;
	private DirectionsListFragment mDirectionFragment = null;

	//Model
	private BaseEntry mEntry = null;
	private int mType = INVALID_VALUE;
	private MapActivityManager mMapManager = null;
	private ViewHolder mMyControlView = null;
	private MyLocationSearchAsycTask mMyLocationAyncTask = null;
	
	private MapPreferenceManager mMapPref = null;

	private Location mMyLocation = null;

	//Value
	private float mZoomValue = 14;
	private float mMyLocationZoomValue = 18;
	private float mPlcaeZoomValue = 19;
	
	private LatLng mDestinationLatLng = null;
	private int mDrawColorIndex = 0;


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
		
		if(getDrawerFragment() != null){
			getDrawerFragment().setBackGroundColorRes(R.color.map_primary_color, R.color.map_primary_dark_color);
			getDrawerFragment().setLineColor(R.color.map_primary_light_color);
			getDrawerFragment().setIconSelector(R.drawable.selector_map_primary_color);
		}
		
		mContentView.setBackground(getResources().getDrawable(R.color.map_primary_light_color));
	}

	private void initModels(){
		mMapPref = MapPreferenceManager.newInstance(this);
		
		if(mMapFragment != null){
			mMap = mMapFragment.getMap();
			mMap.setMapType(mMapPref.getMapType());
			mMap.getUiSettings().setRotateGesturesEnabled(mMapPref.isRotateEnable());
			mMap.getUiSettings().setZoomControlsEnabled(mMapPref.isZoomButtonEnable());
			mMap.setBuildingsEnabled(mMapPref.isBuildingEnable());
			mMap.setIndoorEnabled(mMapPref.isIndoorEnable());
			mMapManager = MapActivityManager.newInstance(this);
		}else{
			log("map fragment is null");
		}
	}

	private void initDatas(){

		if(mEntry != null && mMap != null){
			
			switch(mType){
			case MapActivityManager.DATA_GEOCODING : 
				setGeocodingMapData((AddressResultVO)mEntry);
				return;

			case MapActivityManager.DATA_DIRECTION :
				return;

			case MapActivityManager.DATA_PLACE :

				return;
			}
		}
	}

	private void setGeocodingMapData(final AddressResultVO addressVo){
		mMyControlView.layoutMyControl.setVisibility(View.VISIBLE);
		final String key = addressVo.getLatitude() + addressVo.getLongitude();
		mDestinationLatLng = new LatLng(Double.parseDouble(addressVo.getLatitude()), Double.parseDouble(addressVo.getLongitude()));
		showProgressDialog();
		mMapManager.saveLocateMapImage(mMap, addressVo.getLatitude(), addressVo.getLongitude(), mMyLocationZoomValue, key, new OnSaveMapImageListener() {
			
			@Override
			public void onComplete(Bitmap bm) {
				// TODO Auto-generated method stub
				dismissProgressDialog();
				mMapManager.drawPointToMap(mMap, addressVo, 0);
				bm = null;
			}
		});
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
	public ViewGroup getActionBarMenuView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		
		ViewGroup menu = (ViewGroup) inflater.inflate(R.layout.actionbar_map_menu, null);
		
		menu.findViewById(R.id.action_item_place).setOnClickListener(onClickActionBarItem);
		menu.findViewById(R.id.action_item_directions).setOnClickListener(onClickActionBarItem);
		menu.findViewById(R.id.action_item_setting).setOnClickListener(onClickActionBarItem);
		menu.findViewById(R.id.action_item_location_searching).setOnClickListener(onClickActionBarItem);
		
		switch(mType){
		case MapActivityManager.DATA_GEOCODING : 
			menu.findViewById(R.id.action_item_place).setVisibility(View.GONE);
			menu.findViewById(R.id.action_item_directions).setVisibility(View.GONE);

		case MapActivityManager.DATA_DIRECTION :
			break;

		case MapActivityManager.DATA_PLACE :

			break;
			
		default :
			menu.findViewById(R.id.action_item_place).setVisibility(View.GONE);
			menu.findViewById(R.id.action_item_directions).setVisibility(View.GONE);
			break;
		}
		return menu;
	}

	@Override
	public boolean isHomeAsUp() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void requsetNetworkResultSuccess(String url, String request) {
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
			
			mGaManager.sendEven(getClass().getSimpleName(), ACTION.TAG, ACTION.DO_CURRENT_LOCATION_SEARCH);
			
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
			final Bundle argument = new Bundle();

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
			
			mGaManager.sendEven(getClass().getSimpleName(), ACTION.TAG, ACTION.DO_FIND_NEAR_BY_SEARCH);
			new PlaceAPIHelper(MapActivity.this, API_TYPE.NEAR_BY_SEARCH, MapActivity.this)
			.requestNearBySearchSubway(latitude, longitude, mMapPref.getMapPlaceRadius(), new BaseResponseListener<PlacesEntry>() {
				
				@Override
				public void onResponse(PlacesEntry response) {
					// TODO Auto-generated method stub
					if(response != null){
						if(response.getStatus().equals(PlacesConstans.Status.OK.toString())){
							argument.putParcelableArrayList(MapActivityManager.MAP_DATA, response.getPlaceList());
							mNearBySearchFragment = (NearBySearchListFragment) createFragment(NearBySearchListFragment.class, 
									"nearby", INVALID_VALUE, argument
									, R.anim.slide_in_top, R.anim.slide_in_top);
							mNearBySearchFragment.setPlaceItemDetailClickListener(mPlaceItemDetailClickListener);
							mMapManager.drawPointToMap(mMap, response.getPlaceList());
						}else if(response.getStatus().equals(PlacesConstans.Status.ZERO_RESULTS.toString())){
							makeToast(String.format(getString(R.string.error_zero_result_nearby_subway), mMapPref.getMapPlaceRadius()));
						}else{
							makeToast(response.getStatusMessgae());
						}
					}else{
						showNetworkFailToast();
					}
				}
			});
		}
	};
	
	private OnClickListener onClickFindDirection = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mMap != null && mMyLocation != null && mDestinationLatLng != null){
				mGaManager.sendEven(getClass().getSimpleName(), ACTION.TAG, ACTION.DO_FIND_DIRECTION);
				new DirectionsAPIHelper(MapActivity.this, MapActivity.this)
				.requestTransitModeDirections(mMyLocation.getLatitude(), mMyLocation.getLongitude(),
						mDestinationLatLng.latitude, mDestinationLatLng.longitude,
						System.currentTimeMillis() / 1000, new BaseResponseListener<DirectionsEntry>() {
							
							@Override
							public void onResponse(DirectionsEntry response) {
								// TODO Auto-generated method stub
								if(response != null){
									if(response.getStatus().equals(DirectionsConstants.Status.OK.toString())){
										Bundle argument = new Bundle();
										argument.putParcelable(MapActivityManager.MAP_DATA, response);
										mDirectionFragment = (DirectionsListFragment) createFragment(DirectionsListFragment.class, 
												"place", INVALID_VALUE, argument
												, R.anim.slide_in_top, R.anim.slide_in_top);
										mDirectionFragment.setDirecitonsItemClickListener(mDirectionItemClickListener);
									}
								}
								
							}
						});
			}else{
				makeToast("현재 위치를 알 수 없습니다.");
			}
		}
	};
	
	private void drawPolyline(RouteVO route){
		if(mMap != null && route != null){
			int arrColor[];
			arrColor = new int[5];
			arrColor[0] = getResources().getColor(android.R.color.holo_red_dark);
			arrColor[1] = getResources().getColor(android.R.color.holo_blue_dark);
			arrColor[2] = getResources().getColor(android.R.color.holo_green_dark);
			arrColor[3] = getResources().getColor(android.R.color.holo_orange_dark);
			arrColor[4] = getResources().getColor(android.R.color.holo_purple);
			List<LatLng> points = route.decodePoly(route.getOverviewPolylinePoints());
			PolylineOptions line = new PolylineOptions();
			if(mDrawColorIndex >= arrColor.length)
				mDrawColorIndex = 0;
			
			line.addAll(points).color(arrColor[mDrawColorIndex++]);
			mMap.addPolyline(line);
		}
	}
	
	private PlaceItemDetailClickListener mPlaceItemDetailClickListener = new PlaceItemDetailClickListener() {
		
		@Override
		public void onClickDetailItem(final PlaceVO item) {
			// TODO Auto-generated method stub
			if(item != null){
			
				if(ImageUtils.getInstance(MapActivity.this).getImageBitmap(item.getReference()) != null){
					NearbyActivityManager.newInstance(MapActivity.this).goToActivity(item);
				}else{
					showProgressDialog();
					mMapManager.saveLocateMapImage(mMap, item.getLatitude(), item.getLongitude(), mPlcaeZoomValue, item.getReference(), new OnSaveMapImageListener() {
						
						@Override
						public void onComplete(Bitmap bm) {
							// TODO Auto-generated method stub
							ImageUtils.getInstance(MapActivity.this).saveBitmap(bm, item.getReference());
							dismissProgressDialog();
							NearbyActivityManager.newInstance(MapActivity.this).goToActivity(item);
						}
					});
				}
				
			}
		}
	};
	
	private DirectionsItemClickListener mDirectionItemClickListener = new DirectionsItemClickListener() {
		
		@Override
		public void onClickDetailItem(RouteVO item) {
			// TODO Auto-generated method stub
			if(mMap != null){
				mMap.clear();
				mMapManager.drawPointToMap(mMap, item);
				drawPolyline(item);
			}
			
		}
	};
	
	public void onBackPressed(){
		if(mNearBySearchFragment != null || mDirectionFragment != null){
			if(mNearBySearchFragment != null){
				removeFragment(mNearBySearchFragment, R.anim.slide_out_bottom, R.anim.slide_out_bottom);
				mNearBySearchFragment = null;
			}
			
			if(mDirectionFragment != null){
				removeFragment(mDirectionFragment, R.anim.slide_out_bottom, R.anim.slide_out_bottom);
				mDirectionFragment = null;
			}
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
