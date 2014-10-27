package com.fivetrue.timeattack.fragment.main;



import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.api.common.BaseResponseListener;
import com.api.google.place.PlaceAPIHelper;
import com.api.google.place.PlaceAPIHelper.API_TYPE;
import com.api.google.place.entry.PlacesEntry;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.BaseActivity;
import com.fivetrue.timeattack.fragment.BaseMapFragment;
import com.google.android.gms.maps.GoogleMap;

public class NearBySearchFragment extends BaseMapFragment {

	private class ViewHolder{
		public Button btnMapVisible = null;
	}
	private ViewGroup mContentView = null;
	
	private ViewHolder mViewHolder = new ViewHolder();
	
	
	@Override
	protected View onCreateAddingViews(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		mContentView = (ViewGroup) inflater.inflate(R.layout.fragment_nearbysearch, null);
		
		mViewHolder.btnMapVisible = (Button) mContentView.findViewById(R.id.btn_visible);
		
		mViewHolder.btnMapVisible.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isVisibleMap()){
					((Button)v).setText(R.string.show_map);
					setVisibleMap(false);
				}else{
					((Button)v).setText(R.string.hide_map);
					setVisibleMap(true);
				}
				
			}
		});
		
		return mContentView;
	}

	@Override
	protected void configurationMap(GoogleMap map) {
		// TODO Auto-generated method stub
		map.setMyLocationEnabled(true);
		
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		BaseActivity activity = (BaseActivity) getActivity();
//		if(activity.)
		new PlaceAPIHelper(activity, API_TYPE.NEAR_BY_SEARCH, activity)
		.requestNearBySearchSubway(0, 0, 0, new BaseResponseListener<PlacesEntry>() {
			
			@Override
			public void onResponse(PlacesEntry response) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
}
