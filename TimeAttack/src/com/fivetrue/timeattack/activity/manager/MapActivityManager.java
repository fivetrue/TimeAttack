package com.fivetrue.timeattack.activity.manager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.api.common.BaseEntry;
import com.api.google.directions.entry.DirectionsEntry;
import com.api.google.directions.model.RouteVO;
import com.api.google.geocoding.model.AddressResultVO;
import com.api.google.place.entry.PlacesEntry;
import com.api.google.place.model.PlaceVO;
import com.fivetrue.timeattack.activity.MapActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivityManager extends BaseActivityManager{
	public static String MAP_DATA  = "MAP_DATA";
	public static String MAP_DATA_TYPE  = "MAP_DATA_TYPE";
	
	public static final int DATA_GEOCODING = 0x00;
	public static final int DATA_PLACE = 0x01;
	public static final int DATA_DIRECTION = 0x02;
	
	static public MapActivityManager newInstance(Context context){
		return new MapActivityManager(context);
	}
	
	private MapActivityManager(Context context){
		super(context, MapActivity.class);
	}
	
	@Override
	public void goToActivity(BaseEntry entry) {
		// TODO Auto-generated method stub
		if(entry instanceof AddressResultVO){
			goToMapForGeocodingData((AddressResultVO) entry);
		}
	}
	
	public void drawPointToMap(GoogleMap map, String title, LatLng latlng){
		if(map != null && latlng != null){
			MarkerOptions departrue = new MarkerOptions();
		
			departrue.position(latlng);
			
			if(!TextUtils.isEmpty(title)){
				departrue.title(title);
			}
			map.addMarker(departrue);
		}else{
			error("map, latlng are invalid");
		}
	}
	
	public void drawPointToMap(final GoogleMap map, final AddressResultVO entry, final float zoom){
		if(entry != null && map != null){
			
			LatLng lat = new LatLng(Double.parseDouble(entry.getLatitude()), Double.parseDouble(entry.getLongitude()));
			drawPointToMap(map, entry.getAddress(), lat);
			zoomToPoint(map, lat, zoom);
		}else{
			error("Entry is null");
		}
	}
	
	public void drawPointToMap(GoogleMap map, PlacesEntry entry){
		if(entry != null){
			for(PlaceVO vo : entry.getPlaceList()){
				LatLng lat = new LatLng(Double.parseDouble(vo.getLatitude()), Double.parseDouble(vo.getLongitude()));
				drawPointToMap(map, vo.getName(), lat);
			}
		}else{
			error("Entry is null");
		}
	}

	public void drawPointToMap(GoogleMap map, DirectionsEntry entry){
		if(entry != null){
			for(RouteVO vo : entry.getRouteArray()){
				LatLng arrival = new LatLng(Double.parseDouble(vo.getArrivalLatidute()), Double.parseDouble(vo.getArrivalLongitude()));
				drawPointToMap(map, vo.getArrivalAddress(), arrival);
				LatLng departure = new LatLng(Double.parseDouble(vo.getDepartureLatidute()), Double.parseDouble(vo.getDepartureLongitude()));
				drawPointToMap(map, vo.getDepartureAddress(), departure);
			}
		}else{
			error("Entry is null");
		}
	}

	public void zoomToPoint(GoogleMap map,  LatLng latlng, float zoom){
		if(map != null && latlng != null && zoom > 0){
			map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
			map.animateCamera(CameraUpdateFactory.zoomTo(zoom), 1000, null);
		}else{
			error("map, zoom, latlng are invalid");
		}
	}
	
	private void goToMapForGeocodingData(AddressResultVO entry){
		Intent i = new Intent(mContext, cls);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle b = new Bundle();
		b.putInt(MAP_DATA_TYPE, DATA_GEOCODING);
		b.putParcelable(MAP_DATA, entry);
		i.putExtras(b);
		mContext.startActivity(i);
	}
}
