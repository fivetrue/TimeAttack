package com.fivetrue.timeattack.fragment;

import com.fivetrue.timeattack.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract public class BaseMapFragment extends BaseFragment{
	
	private ViewGroup contentView = null;
	private GoogleMap map = null;
	private SupportMapFragment mapFragment = null;
	private LocationManager locationManager = null;
	
	public BaseMapFragment(){};
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initView(inflater);
		initModel();
		loadData();
		return contentView;
	}
	
	private void initView(LayoutInflater inflater){
		contentView = (ViewGroup) inflater.inflate(R.layout.fragment_map, null);
		mapFragment = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.fragment_map);
		map = mapFragment.getMap();
		View childView = onCreateAddingViews(inflater);
		if(childView != null){
			contentView.addView(childView);
		}
		setVisibleMap(false);
	}
	
	private void initModel(){
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
	}
	
	/**
	 * 추가로 View를 생성 함
	 * @param inflater
	 * @return
	 */
	abstract protected View onCreateAddingViews(LayoutInflater inflater);
	
	/**
	 * 초기 맵 정보 설정 
	 * @param map
	 */
	abstract protected void configurationMap(GoogleMap map);
	
	/**
	 * 초기 데이터 로드
	 */
	abstract protected void loadData();

	public ViewGroup getContentView() {
		return contentView;
	}

	public GoogleMap getMap() {
		return map;
	}
	
	public void setVisibleMap(boolean visible){
		if(mapFragment != null){
			if(visible){
				getFragmentManager()
				.beginTransaction()
//				.setCustomAnimations(android.R.animator.fade_out, android.R.animator.fade_in)
				.show(mapFragment)
				.commit();
			}else{
				getFragmentManager()
				.beginTransaction()
//				.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
				.hide(mapFragment)
				.commit();
			}
		}
	}
	
	public boolean isVisibleMap(){
		if(mapFragment != null){
			return mapFragment.isVisible();
		}else{
			return false;
		}
	}

	public SupportMapFragment getMapFragment() {
		return mapFragment;
	}

	public LocationManager getLocationManager() {
		return locationManager;
	}
	
	public boolean isGpsOn(){
		if(locationManager != null){
			return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}else{
			return false;
		}
	}
	
	public void goToGpsSetting(){
		if(getActivity() != null){
			startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
	}
	
	public void turnOnGpsSetting(){
		
	}
	
}
