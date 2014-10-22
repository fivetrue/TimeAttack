package com.fivetrue.timeattack.database;

import java.util.ArrayList;

import org.json.JSONObject;

import com.api.common.Constants;
import com.api.seoul.SeoulAPIConstants;
import com.fivetrue.timeattack.database.model.NetworkResult;
import com.fivetrue.timeattack.database.model.NetworkResult.Type;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NetworkResultDBManager {
	
	private Context mContext = null;
	private SQLiteDatabase mDb = null;
	private NetworkResultDBHelper mHelper = null;
	
	public NetworkResultDBManager(Context context){
		mContext = context;
		mHelper = new NetworkResultDBHelper(context, null);
	}
	
	public void insertNetworkResult(String url, JSONObject json){
		mDb = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(mHelper.FIELD_URL, url);
		values.put(mHelper.FIELD_RESULT, json.toString());
		values.put(mHelper.FIELD_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
		mDb.insert(mHelper.TABLE, null, values);
	}
	
	public ArrayList<NetworkResult> getNetworkResults(){
		
		ArrayList<NetworkResult> networkList = new ArrayList<NetworkResult>();
		
		mDb = mHelper.getReadableDatabase();
		Cursor c = mDb.query(mHelper.TABLE, null, null, null, null, null, null);
		if(c.moveToFirst()){
			do{
				NetworkResult data = new NetworkResult();
				int index = c.getInt(c.getColumnIndex(mHelper.FIELD_ID));
				String url = c.getString(c.getColumnIndex(mHelper.FIELD_URL));
				String result = c.getString(c.getColumnIndex(mHelper.FIELD_RESULT));
				String timestamp = c.getString(c.getColumnIndex(mHelper.FIELD_TIMESTAMP));
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
				}else if(url.contains(SeoulAPIConstants.Subway.ARRIVAL_INFO_SERVICE)){
					data.setType(Type.SubwayArrival);
				}else if(url.contains(SeoulAPIConstants.Subway.FIND_INFO_SERVICE)){
					data.setType(Type.SubwayInfo);
				}
				networkList.add(data);
			}while(c.moveToNext());
		}
		
		if(c != null){
			c.close();
		}
		return networkList;
	}
	
	public NetworkResult getNetworkResult(String field, String where){
		
		return null;
	}

}
