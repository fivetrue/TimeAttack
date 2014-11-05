package com.fivetrue.timeattack.activity.manager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.api.common.BaseEntry;
import com.api.google.directions.entry.DirectionsEntry;
import com.api.google.directions.model.RouteVO;
import com.api.google.geocoding.entry.GeocodingEntry;
import com.api.google.place.entry.PlacesEntry;
import com.api.google.place.model.PlaceVO;
import com.fivetrue.timeattack.activity.MapActivity;
import com.fivetrue.utils.Logger;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivityManager {
	public static String MAP_DATA  = "MAP_DATA";
	public static String MAP_DATA_TYPE  = "MAP_DATA_TYPE";
	
	public static String DATA_GEOCODING = "DATA_GEOCODING";
	public static String DATA_PLACE = "DATA_PLACE";
	public static String DATA_DIRECTION = "DATA_DIRECTION";
	
	private final int INVALID_DATA = -1;
	
	private Context mContext = null;
	private Class<?> cls = MapActivity.class;
	
	static public MapActivityManager newInstance(Context context){
		return new MapActivityManager(context);
	}
	
	private MapActivityManager(Context context){
		mContext = context;
	}
	public void goToMapActivity(BaseEntry entry){
		
		if(entry instanceof GeocodingEntry){
			goToMapForGeocodingData((GeocodingEntry) entry);
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
	
	public void drawPointToMap(final GoogleMap map, final GeocodingEntry entry, final float zoom){
		if(entry != null && map != null){
			
//			new Handler().post(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					while(!map.isBuildingsEnabled()){
//						
//					}
//					LatLng lat = new LatLng(Double.parseDouble(entry.getLatitude()), Double.parseDouble(entry.getLongitude()));
//					drawPointToMap(map, entry.getAddress(), lat);
//					zoomToPoint(map, latlng, zoom)
//				}
//			});
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
	
	private void goToMapForGeocodingData(GeocodingEntry entry){
		Intent i = new Intent(mContext, cls);
		Bundle b = new Bundle();
		b.putString(MAP_DATA_TYPE, DATA_GEOCODING);
		b.putParcelable(MAP_DATA, entry);
		i.putExtras(b);
		mContext.startActivity(i);
	}
	
	private void log(String msg){
		Logger.d(getClass().getName(), msg);
	}
	
	private void error(String msg){
		Logger.e(getClass().getName(), msg);
	}
	
	private void warring(String msg){
		Logger.w(getClass().getName(), msg);
	}
}
