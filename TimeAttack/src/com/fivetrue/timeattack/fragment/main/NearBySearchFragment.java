package com.fivetrue.timeattack.fragment.main;



import android.view.LayoutInflater;
import android.view.View;

import com.fivetrue.timeattack.fragment.BaseMapFragment;
import com.google.android.gms.maps.GoogleMap;

public class NearBySearchFragment extends BaseMapFragment {

	@Override
	protected View onCreateAddingViews(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void configurationMap(GoogleMap map) {
		// TODO Auto-generated method stub
		map.setMyLocationEnabled(true);
		
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}

	
}

