package com.fivetrue.timeattack.preference;

import android.content.Context;

import com.fivetrue.preference.SharedPrefrenceManager;
import com.google.android.gms.maps.GoogleMap;

public class MapPreferenceManager extends SharedPrefrenceManager {

	static private final String PREFERENCE_NAME = "MapPreferenceManager";
	private final String MAP_TRAFFIC_ENABLE = "MAP_TRAFFIC_ENABLE";
	private final String MAP_INDOOR_ENABLE = "MAP_INDOOR_ENABLE";
	private final String MAP_BUILDING_ENABLE = "MAP_BUILDING_ENABLE";
	private final String MAP_ZOOM_BUTTON = "MAP_ZOOM_BUTTON";
	private final String MAP_ROTATE = "MAP_ROTATE";
	private final String MAP_TYPE = "MAP_TYPE";
	
	
	static public MapPreferenceManager newInstance(Context context){
		return new MapPreferenceManager(context, PREFERENCE_NAME);
	}
	private MapPreferenceManager(Context context, String name) {
		super(context, name);
		// TODO Auto-generated constructor stub
	}
	
	public void setTrafficEnable(boolean enable){
		savePref(MAP_TRAFFIC_ENABLE, enable);
	}
	
	public boolean isTrafficEnable(){
		return loadBoolPref(MAP_TRAFFIC_ENABLE, false);
	}
	
	public void setIndoorEnable(boolean enable){
		savePref(MAP_INDOOR_ENABLE, enable);
	}
	
	public boolean isIndoorEnable(){
		return loadBoolPref(MAP_INDOOR_ENABLE, false);
	}
	
	public void setBuildingEnable(boolean enable){
		savePref(MAP_BUILDING_ENABLE, enable);
	}
	
	public boolean isBuildingEnable(){
		return loadBoolPref(MAP_BUILDING_ENABLE, true);
	}
	
	public void setRotateEnable(boolean enable){
		savePref(MAP_ROTATE, enable);
	}
	
	public boolean isRotateEnable(){
		return loadBoolPref(MAP_ROTATE, false);
	}
	
	public void setZoomButtonEnable(boolean enable){
		savePref(MAP_ZOOM_BUTTON, enable);
	}
	
	public boolean isZoomButtonEnable(){
		return loadBoolPref(MAP_ZOOM_BUTTON, false);
	}
	
	
	public void setMapType(int type){
		savePref(MAP_TYPE, type);
	}
	
	public int getMapType(){
		return loadIntPref(MAP_TYPE, GoogleMap.MAP_TYPE_NORMAL);
	}
	

}
