package com.fivetrue.timeattack.activity.manager;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.api.common.BaseEntry;
import com.api.google.directions.entry.DirectionsEntry;
import com.api.google.directions.model.RouteVO;
import com.api.google.geocoding.model.AddressResultVO;
import com.api.google.place.model.PlaceVO;
import com.fivetrue.timeattack.activity.MapActivity;
import com.fivetrue.timeattack.utils.ImageUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivityManager extends BaseActivityManager{
	
	public interface OnSaveMapImageListener{
		public void onComplete(Bitmap bm);
	}
	public interface OnMyLocationDialogSelectedListener{
		public void onSelected(View view);
	}
	
	public static String MAP_DATA  = "MAP_DATA";
	public static String MAP_DATA_TYPE  = "MAP_DATA_TYPE";
	public static String DATA_ROUTE  = "DATA_ROUTE";
	
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
	
	public MarkerOptions drawPointToMap(GoogleMap map, String title, Bitmap icon, LatLng latlng){
		if(map != null && latlng != null){
			MarkerOptions departrue = new MarkerOptions();
		
			departrue.position(latlng);
			if(icon != null){
				departrue.icon(BitmapDescriptorFactory.fromBitmap(icon));
				icon = null;
			}
			if(!TextUtils.isEmpty(title)){
				departrue.title(title);
			}
			map.addMarker(departrue);
			return departrue;
		}else{
			error("map, latlng are invalid");
		}
		return null;
	}
	
	public MarkerOptions drawPointToMap(final GoogleMap map, final AddressResultVO entry, final float zoom){
		if(entry != null && map != null){
			
			LatLng lat = new LatLng(Double.parseDouble(entry.getLatitude()), Double.parseDouble(entry.getLongitude()));
			
			if(zoom > 0){
				zoomToPoint(map, lat, zoom);
			}
			
			return drawPointToMap(map, entry.getAddress(), null, lat);
		}else{
			error("Entry is null");
		}
		return null;
	}
	
	public ArrayList<MarkerOptions> drawPointToMap(GoogleMap map, ArrayList<PlaceVO> arr){
		if(arr != null){
			ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
			for(PlaceVO vo : arr){
				LatLng lat = new LatLng(Double.parseDouble(vo.getLatitude()), Double.parseDouble(vo.getLongitude()));
				markers.add(drawPointToMap(map, vo.getName(), null, lat));
			}
			return markers;
		}else{
			error("Entry is null");
		}
		return null;
	}

	public ArrayList<MarkerOptions> drawPointToMap(GoogleMap map, DirectionsEntry entry){
		if(entry != null){
			for(RouteVO vo : entry.getRouteArray()){
				ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
				LatLng arrival = new LatLng(Double.parseDouble(vo.getArrivalLatidute()), Double.parseDouble(vo.getArrivalLongitude()));
				markers.add(drawPointToMap(map, vo.getArrivalAddress(), null, arrival));
				LatLng departure = new LatLng(Double.parseDouble(vo.getDepartureLatidute()), Double.parseDouble(vo.getDepartureLongitude()));
				markers.add(drawPointToMap(map, vo.getDepartureAddress(), null, departure));
				return markers;
			}
		}else{
			error("Entry is null");
		}
		return null;
	}
	
	public ArrayList<MarkerOptions> drawPointToMap(GoogleMap map, RouteVO route){
		if(route != null){
			ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
			LatLng arrival = new LatLng(Double.parseDouble(route.getArrivalLatidute()), Double.parseDouble(route.getArrivalLongitude()));
			markers.add(drawPointToMap(map, route.getArrivalAddress(), null, arrival));
			LatLng departure = new LatLng(Double.parseDouble(route.getDepartureLatidute()), Double.parseDouble(route.getDepartureLongitude()));
			markers.add(drawPointToMap(map, route.getDepartureAddress(), null, departure));
			return markers;
		}else{
			error("Entry is null");
		}
		return null;
	}

	public void zoomToPoint(GoogleMap map,  LatLng latlng, float zoom){
		if(map != null && latlng != null && zoom > 0){
			map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
			map.animateCamera(CameraUpdateFactory.zoomTo(zoom), 1000, null);
		}else{
			error("map, zoom, latlng are invalid");
		}
	}
	
	public void saveLocateMapImage(GoogleMap map, String lat, String lng, float zoom, String cacheKey, OnSaveMapImageListener callback){
		saveLocateMapImage(map, Double.parseDouble(lat), Double.parseDouble(lng), zoom, cacheKey, callback);
	}
	
	public void saveLocateMapImage(final GoogleMap map, double lat, double lng, float zoom, final String cacheKey, final OnSaveMapImageListener callback){
		LatLng latlng = new LatLng(lat, lng);
		zoomToPoint(map, latlng, zoom);
		Bitmap bitmap = ImageUtils.getInstance(mContext).getImageBitmap(cacheKey);
		if(bitmap != null){
			callback.onComplete(bitmap);
		}else{
			map.setOnMapLoadedCallback(new OnMapLoadedCallback() {

				@Override
				public void onMapLoaded() {
					// TODO Auto-generated method stub
					map.snapshot(new SnapshotReadyCallback() {

						@Override
						public void onSnapshotReady(Bitmap paramBitmap) {
							// TODO Auto-generated method stub
							if(paramBitmap != null){
								ImageUtils.getInstance(mContext).saveBitmap(paramBitmap, cacheKey);
								callback.onComplete(paramBitmap);
							}
						}
					});
				}
			});
		}
	}
	
	private void goToMapForGeocodingData(AddressResultVO entry){
		Intent i = new Intent(mContext, cls);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle b = new Bundle();
		b.putInt(MAP_DATA_TYPE, DATA_GEOCODING);
		b.putParcelable(MAP_DATA, entry);
		i.putExtras(b);
		startActivity(mContext, i);
	}
	
	public void showMyLocationDialog(OnMyLocationDialogSelectedListener listener){
		
	}
}
