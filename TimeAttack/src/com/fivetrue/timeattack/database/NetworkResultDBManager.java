package com.fivetrue.timeattack.database;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.api.common.Constants;
import com.api.google.geocoding.converter.GeocodingConverter;
import com.api.google.geocoding.entry.GeocodingEntry;
import com.api.google.place.converter.PlacesConverter;
import com.api.google.place.converter.PlacesDetailConverter;
import com.api.google.place.entry.PlacesDetailEntry;
import com.api.google.place.entry.PlacesEntry;
import com.fivetrue.timeattack.database.model.NetworkResult;
import com.fivetrue.timeattack.database.model.NetworkResult.Type;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NetworkResultDBManager {
	
	private Context mContext = null;
	private SQLiteDatabase mDb = null;
	private TimeAttackDBHelper mHelper = null;
	
	public NetworkResultDBManager(Context context){
		mContext = context;
		mHelper = new TimeAttackDBHelper(context, null);
	}
	
	public void insertNetworkResult(String url, JSONObject json){
		mDb = mHelper.getWritableDatabase();
		if(mDb != null){
			ContentValues values = new ContentValues();
			values.put(DatabaseConstants.NetworkResultTableInfo.FIELD_URL, url);
			values.put(DatabaseConstants.NetworkResultTableInfo.FIELD_RESULT, json.toString());
			values.put(DatabaseConstants.NetworkResultTableInfo.FIELD_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
			mDb.insert(DatabaseConstants.NetworkResultTableInfo.TABLE, null, values);
			mDb.close();
		}
	}
	
	public void deleteNetworkResult(String url){
		mDb = mHelper.getWritableDatabase();
		if(mDb != null){
			mDb.delete(DatabaseConstants.NetworkResultTableInfo.TABLE, DatabaseConstants.NetworkResultTableInfo.FIELD_URL + "=" + url, null);
			mDb.close();
		}
	}
	
	public ArrayList<NetworkResult> getNetworkResults(){
		
		ArrayList<NetworkResult> networkList = new ArrayList<NetworkResult>();
		
		mDb = mHelper.getReadableDatabase();
		if(mDb != null){
			Cursor c = mDb.query(DatabaseConstants.NetworkResultTableInfo.TABLE, null, null, null, null, null, null);
			if(c.moveToFirst()){
				do{
					NetworkResult data = new NetworkResult();
					int index = c.getInt(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_ID));
					String url = c.getString(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_URL));
					String result = c.getString(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_RESULT));
					String timestamp = c.getString(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_TIMESTAMP));
					data.setIndex(index);
					data.setUrl(url);
					data.setResult(result);
					data.setTimestamp(timestamp);
					
					if(url.contains(Constants.GooglePlaceAPI.PLACE_API_HOST)){
						data.setType(Type.Place);
					}else if(url.contains(Constants.GoogleDirectionsAPI.DIRECTION_API_HOST)){
						data.setType(Type.Direction);
					}else if(url.contains(Constants.GoogleGeocodingAPI.GEOCODING_API_HOST)){
						data.setType(Type.GeoCoding);
					}
//					else if(url.contains(SeoulAPIConstants.Subway.ARRIVAL_INFO_SERVICE)){
//						data.setType(Type.SubwayArrival);
//					}else if(url.contains(SeoulAPIConstants.Subway.FIND_INFO_SERVICE)){
//						data.setType(Type.SubwayInfo);
//					}
					networkList.add(data);
				}while(c.moveToNext());
			}
			
			if(c != null){
				c.close();
			}
			mDb.close();
		}
		return networkList;
	}
	
	public ArrayList<GeocodingEntry> getGeocodingNetworkResults(){
		
		ArrayList<GeocodingEntry> networkList = new ArrayList<GeocodingEntry>();
		
		mDb = mHelper.getReadableDatabase();
		if(mDb != null){
			Cursor c = mDb.query(DatabaseConstants.NetworkResultTableInfo.TABLE, null,
					DatabaseConstants.NetworkResultTableInfo.FIELD_URL +
					" like '%" + Constants.GoogleGeocodingAPI.GEOCODING_API_HOST + "%'"
					, null, null, null, DatabaseConstants.NetworkResultTableInfo.FIELD_TIMESTAMP + " DESC");
			if(c.moveToFirst()){
				do{
					int index = c.getInt(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_ID));
					String url = c.getString(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_URL));
					String result = c.getString(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_RESULT));
					String timestamp = c.getString(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_TIMESTAMP));
					try {
						networkList.add(new GeocodingConverter().onReceive(new JSONObject(result)));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}while(c.moveToNext());
			}
			
			if(c != null){
				c.close();
			}
			mDb.close();
		}
		return networkList;
	}
	
	
	public ArrayList<PlacesDetailEntry> getPlacesNetworkResults(){
		
		ArrayList<PlacesDetailEntry> networkList = new ArrayList<PlacesDetailEntry>();
		
		mDb = mHelper.getReadableDatabase();
		if(mDb != null){
			Cursor c = mDb.query(DatabaseConstants.NetworkResultTableInfo.TABLE, null,
					DatabaseConstants.NetworkResultTableInfo.FIELD_URL +
					" like '%" + Constants.GooglePlaceAPI.PLACE_API_HOST + Constants.GooglePlaceAPI.PLACE_DETAIL + "%'"
					, null, null, null, DatabaseConstants.NetworkResultTableInfo.FIELD_TIMESTAMP + " DESC");
			if(c.moveToFirst()){
				do{
					int index = c.getInt(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_ID));
					String url = c.getString(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_URL));
					String result = c.getString(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_RESULT));
					String timestamp = c.getString(c.getColumnIndex(DatabaseConstants.NetworkResultTableInfo.FIELD_TIMESTAMP));
					try {
						networkList.add(new PlacesDetailConverter().onReceive(new JSONObject(result)));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}while(c.moveToNext());
			}
			if(c != null){
				c.close();
			}
			mDb.close();
		}
		return networkList;
	}
	
	public NetworkResult getNetworkResult(String field, String where){
		
		return null;
	}

}
