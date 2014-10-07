package com.fivetrue.timeattack.activity;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends BaseActivity{

	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	}
	@Override
	View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getActionBarTitleName() {
		// TODO Auto-generated method stub
		return "TimeAttack";
	}

	@Override
	String getActionBarSubTitle() {
		// TODO Auto-generated method stub
		return "MainActivty";
	}

	@Override
	boolean isHomeAsUp() {
		// TODO Auto-generated method stub
		return false;
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
