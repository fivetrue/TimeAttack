package com.fivetrue.timeattack.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fivetrue.timeattack.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class SubwayMapFragment extends Fragment {
	private GoogleMap mGoogleMap = null;
	
	public SubwayMapFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_map, null);
		mGoogleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.fragment_map)).getMap();
		return view;
	}
}
