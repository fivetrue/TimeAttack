package com.fivetrue.timeattack.database;

import java.util.ArrayList;

import com.api.seoul.subway.entry.SubwayInfoEntry;
import com.api.seoul.subway.model.StationVO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SubwayStationListDBManager {
	
	private Context mContext = null;
	private SQLiteDatabase mDb = null;
	private TimeAttackDBHelper mHelper = null;
	
	public SubwayStationListDBManager(Context context){
		mContext = context;
		mHelper = new TimeAttackDBHelper(context, null);
	}
	
	public void insertSubway(StationVO vo){
		if(vo != null){
			mDb = mHelper.getWritableDatabase();
			if(mDb != null){
				ContentValues values = new ContentValues();
				values.put(DatabaseConstants.SubwayStationTableInfo.FIELD_NAME, vo.getStationName());
				values.put(DatabaseConstants.SubwayStationTableInfo.FIELD_CODE, vo.getStationCode());
				values.put(DatabaseConstants.SubwayStationTableInfo.FIELD_FORIEGN_CODE, vo.getForiegnCode());
				values.put(DatabaseConstants.SubwayStationTableInfo.FIELD_LINE_NUMBER, vo.getLineNumber());
				mDb.insert(DatabaseConstants.SubwayStationTableInfo.TABLE, null, values);
				mDb.close();
			}
		}
	}
	
	public void insertSubways(SubwayInfoEntry entry){
		if(entry != null){
			mDb = mHelper.getWritableDatabase();
			if(mDb != null){
				for(StationVO vo : entry.getStationList()){
					ContentValues values = new ContentValues();
					values.put(DatabaseConstants.SubwayStationTableInfo.FIELD_NAME, vo.getStationName());
					values.put(DatabaseConstants.SubwayStationTableInfo.FIELD_CODE, vo.getStationCode());
					values.put(DatabaseConstants.SubwayStationTableInfo.FIELD_FORIEGN_CODE, vo.getForiegnCode());
					values.put(DatabaseConstants.SubwayStationTableInfo.FIELD_LINE_NUMBER, vo.getLineNumber());
					mDb.insert(DatabaseConstants.SubwayStationTableInfo.TABLE, null, values);
				}
				mDb.close();
			}
		}
	}
	
	public ArrayList<StationVO> getStations(String selection){
		
		ArrayList<StationVO> stationList = new ArrayList<StationVO>();
		mDb = mHelper.getReadableDatabase();
		if(mDb != null){
			Cursor c = mDb.query(DatabaseConstants.SubwayStationTableInfo.TABLE, null, selection, null, null, null, null);
			if(c.moveToFirst()){
				do{
					StationVO data = new StationVO();
					int index = c.getInt(c.getColumnIndex(DatabaseConstants.SubwayStationTableInfo.FIELD_ID));
					String name = c.getString(c.getColumnIndex(DatabaseConstants.SubwayStationTableInfo.FIELD_NAME));
					String code = c.getString(c.getColumnIndex(DatabaseConstants.SubwayStationTableInfo.FIELD_CODE));
					String foriegnCode = c.getString(c.getColumnIndex(DatabaseConstants.SubwayStationTableInfo.FIELD_CODE));
					String lineNumber = c.getString(c.getColumnIndex(DatabaseConstants.SubwayStationTableInfo.FIELD_LINE_NUMBER));
					data.setStationName(name);
					data.setStationCode(code);
					data.setForiegnCode(foriegnCode);
					data.setLineNumber(lineNumber);
					stationList.add(data);
				}while(c.moveToNext());
			}
			
			if(c != null){
				c.close();
			}
			mDb.close();
		}
		return stationList;
	}
	
	public ArrayList<StationVO> getStationsByName(String subwayName){
		String selction = DatabaseConstants.SubwayStationTableInfo.FIELD_NAME + "='" + subwayName + "'";
		return getStations(selction);
	}

}
