package com.fivetrue.timeattack.fragment.main;



import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.api.common.BaseResponseListener;
import com.api.google.geocoding.GeocodingAPIHelper;
import com.api.google.geocoding.GeocodingConstants;
import com.api.google.geocoding.entry.GeocodingEntry;
import com.api.google.place.PlaceAPIHelper;
import com.api.google.place.PlaceAPIHelper.API_TYPE;
import com.api.google.place.entry.PlacesEntry;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.BaseActivity;
import com.fivetrue.timeattack.fragment.BaseMapFragment;
import com.google.android.gms.maps.GoogleMap;

public class NearBySearchFragment extends BaseMapFragment {

	private class SearchLocationViewHolder{
		public ViewGroup layout = null;
		public ViewGroup layoutTop = null;
		public EditText etSearch = null;
		public Button btnSearch = null;
		public ListView lvSearch = null;
	}

	private class NearBySubwayViewHolder{
		public ViewGroup layout = null;
	}
	private ViewGroup mContentView = null;

	private SearchLocationViewHolder mSearchHolder = new SearchLocationViewHolder();


	@Override
	protected View onCreateAddingViews(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		mContentView = (ViewGroup) inflater.inflate(R.layout.fragment_nearbysearch, null);
		initSearchLayout(mContentView);
		initNearBySubwayLayout(mContentView);

		return mContentView;
	}

	private void initSearchLayout(ViewGroup view){
		mSearchHolder.layout = (ViewGroup) view.findViewById(R.id.layout_location_search);
		mSearchHolder.layoutTop = (ViewGroup) view.findViewById(R.id.layout_location_search_top);
		mSearchHolder.etSearch = (EditText) view.findViewById(R.id.et_location_search_top);
		mSearchHolder.btnSearch = (Button) view.findViewById(R.id.btn_location_search_top);
		mSearchHolder.lvSearch = (ListView) view.findViewById(R.id.lv_location_search);
		
		mSearchHolder.btnSearch.setOnClickListener(onClickSearchViews);
	}

	public void initNearBySubwayLayout(ViewGroup view){

	}

	@Override
	protected void configurationMap(GoogleMap map) {
		// TODO Auto-generated method stub
		map.setMyLocationEnabled(true);

	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
//		BaseActivity activity = (BaseActivity) getActivity();
//		//		if(activity.)
//		new PlaceAPIHelper(activity, API_TYPE.NEAR_BY_SEARCH, activity)
//		.requestNearBySearchSubway(0, 0, 0, new BaseResponseListener<PlacesEntry>() {
//
//			@Override
//			public void onResponse(PlacesEntry response) {
//				// TODO Auto-generated method stub
//
//			}
//		});
	}
	
	private OnClickListener onClickSearchViews = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mSearchHolder.btnSearch == null )
				return ;
			
			switch(v.getId()){
			case R.id.btn_location_search_top : 
				onLoadSearchLocation();
				break;
			}
			
		}
	};
	
	private void onLoadSearchLocation(){
		if(mSearchHolder.btnSearch == null 
				|| mSearchHolder.etSearch == null 
				|| TextUtils.isEmpty(mSearchHolder.etSearch.getText().toString())){
			return ;
		}
		final String input = mSearchHolder.etSearch.getText().toString();
		new GeocodingAPIHelper(getActivity(), (BaseActivity)getActivity()).requestGeocoding(input, new BaseResponseListener<GeocodingEntry>() {
			
			@Override
			public void onResponse(GeocodingEntry response) {
				// TODO Auto-generated method stub
				if(response != null){
					if(response.getStatus().equals(GeocodingConstants.Status.OK)){
						setSearchLocationResult(response);
					}
				}
			}
		});
	}
	
	private void setSearchLocationResult(GeocodingEntry entry){
		System.out.println("ojkwon : == > " + entry.toString());
	}


}

