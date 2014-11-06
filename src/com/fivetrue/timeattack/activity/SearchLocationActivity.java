package com.fivetrue.timeattack.activity;

import com.fivetrue.timeattack.R;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchLocationActivity extends BaseActivity {
	
	private ViewGroup mContentView = null;

	@Override
	View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		
		mContentView = (ViewGroup) inflater.inflate(R.layout.activity_search, null);
		
		return mContentView;
	}

	@Override
	String getActionBarTitleName() {
		// TODO Auto-generated method stub
		return getString(R.string.actionbar_search);
	}

	@Override
	String getActionBarSubTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	int getActionBarMenuResource() {
		// TODO Auto-generated method stub
		return INVALID_VALUE;
	}

	@Override
	boolean isHomeAsUp() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	void requsetNetworkResultSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void outOfService(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

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

	}

}
