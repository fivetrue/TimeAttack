package com.fivetrue.timeattack.activity.manager;


import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.api.common.BaseEntry;
import com.api.common.BaseResponseListener;
import com.api.common.IRequestResult;
import com.api.google.place.model.PlaceVO;
import com.api.seoul.SeoulAPIConstants;
import com.api.seoul.subway.SubwayFindInfoAPIHelper;
import com.api.seoul.subway.converter.SubwayInfoConverter;
import com.api.seoul.subway.entry.SubwayInfoEntry;
import com.api.seoul.subway.model.StationVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.NearbyActivity;
import com.fivetrue.timeattack.database.SubwayStationListDBManager;
import com.fivetrue.utils.Logger;

public class NearbyActivityManager extends BaseActivityManager{
	public static String NEARBY_DATA  = "NEARBY_DATA";
	private final int INVALID_VALUE = -1;
	
	private SubwayStationListDBManager mSubwayDBManager = null;
	
	static public NearbyActivityManager newInstance(Context context){
		return new NearbyActivityManager(context);
	}
	
	private NearbyActivityManager(Context context){
		super(context, NearbyActivity.class);
		mSubwayDBManager = new SubwayStationListDBManager(context);
	}
	
	@Override
	public void goToActivity(BaseEntry entry) {
		// TODO Auto-generated method stub
		Intent i = new Intent(mContext, cls);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle b = new Bundle();
		b.putParcelable(NEARBY_DATA, entry);
		i.putExtras(b);
		startActivity(mContext, i);
	}
	
	public void findingSubwayInfo(final String subwayName, BaseResponseListener<SubwayInfoEntry> ll){
		if(!TextUtils.isEmpty(subwayName) && ll != null){
			ArrayList<StationVO> stationList = mSubwayDBManager.getStationsByName(subwayName);
			if(stationList != null && stationList.size() > 0){
				SubwayInfoEntry entry = new SubwayInfoEntry();
				entry.setStatus(SeoulAPIConstants.ResultInfo.OK);
				entry.setStationList(stationList);
				ll.onResponse(entry);
			}else{
				new SubwayFindInfoAPIHelper(mContext, new IRequestResult() {

					@Override
					public void onSuccessRequest(String url, JSONObject request) {
						// TODO Auto-generated method stub
						if(url != null && request != null){
							SubwayInfoEntry entry = new SubwayInfoConverter().onReceive(request);
							if(entry != null){
								if(entry.getStatus().equals(SeoulAPIConstants.ResultInfo.OK)){
									mSubwayDBManager.insertSubways(entry);
								}
							}
						}
					}

					@Override
					public void onFailRequest(String url) {
						// TODO Auto-generated method stub

					}
				}).requestFindInfoSubway(subwayName, ll);
			}
		}
	}
	
	public String getSubwayNameFromPlacesSubwayData(PlaceVO entry){
		String subwayName = null;
		if(entry != null && mContext != null){
			String station = mContext.getString(R.string.station);
			String stationName = entry.getName();
			if(!TextUtils.isEmpty(station) && !TextUtils.isEmpty(stationName)){
				int stationIndex = stationName.lastIndexOf(station);
				if(stationIndex == INVALID_VALUE){
					Logger.e(this.getClass().getSimpleName(), "Not found station");
				}else{
					subwayName = stationName.substring(0, stationIndex);
				}
			}
		}else{
			Logger.e(this.getClass().getSimpleName(), "Context or PlaceVo is null");
		}
		return subwayName;
	}
}
