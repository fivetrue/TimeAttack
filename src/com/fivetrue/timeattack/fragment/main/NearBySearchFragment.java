package com.fivetrue.timeattack.fragment.main;



import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.BaseActivity;
import com.fivetrue.timeattack.activity.manager.MapActivityManager;
import com.fivetrue.timeattack.fragment.BaseFragment;

public class NearBySearchFragment extends BaseFragment {

	private class SearchLocationViewHolder{
		public ViewGroup layout = null;
		public ViewGroup layoutTop = null;
		public EditText etSearch = null;
		public Button btnSearch = null;
		public ListView lvSearch = null;
	}

	private ViewGroup mContentView = null;

	private SearchLocationViewHolder mSearchHolder = new SearchLocationViewHolder();


	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
				|| TextUtils.isEmpty(mSearchHolder.etSearch.getText().toString().trim())){
			return ;
		}
		final String input = mSearchHolder.etSearch.getText().toString().trim();
		new GeocodingAPIHelper(getActivity(), (BaseActivity)getActivity()).requestGeocoding(input, new BaseResponseListener<GeocodingEntry>() {
			
			@Override
			public void onResponse(GeocodingEntry response) {
				// TODO Auto-generated method stub
				if(response != null){
					if(response.getStatus().equals(GeocodingConstants.Status.OK.toString())){
						setSearchLocationResult(response);
					}
				}
			}
		});
	}
	
	private void setSearchLocationResult(GeocodingEntry entry){
		MapActivityManager.newInstance(getActivity()).goToMapActivity(entry);
		System.out.println("ojkwon : == > " + entry.toString());
	}


}

