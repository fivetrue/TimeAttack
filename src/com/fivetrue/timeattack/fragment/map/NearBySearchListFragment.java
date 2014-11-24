package com.fivetrue.timeattack.fragment.map;


import java.util.ArrayList;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.api.common.BaseResponseListener;
import com.api.google.place.PlaceAPIHelper;
import com.api.google.place.PlaceAPIHelper.API_TYPE;
import com.api.google.place.PlacesConstans;
import com.api.google.place.entry.PlacesEntry;
import com.api.google.place.model.PlaceVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.BaseActivity;
import com.fivetrue.timeattack.activity.manager.MapActivityManager;
import com.fivetrue.timeattack.fragment.BaseListFragment;
import com.fivetrue.timeattack.view.adapter.NearBySearchResultAdapter;
import com.google.android.gms.maps.model.LatLng;

public class NearBySearchListFragment extends BaseListFragment<PlaceVO> {

	private ArrayList<PlaceVO> mArrPlace = new ArrayList<PlaceVO>();
	
	@Override
	public View initHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View initFooter() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public void onLoadListData() {
		// TODO Auto-generated method stub
		initData();
		loadData();
	}
	
	private void initData(){
		Bundle b = getArguments();
		if(b != null){
			mArrPlace = b.getParcelableArrayList(MapActivityManager.MAP_DATA);
		}
	}
	
	private void loadData(){
		if(mArrPlace != null){
			setListData(mArrPlace);
		}
	}
	
	private void setListData(ArrayList<PlaceVO> arr){
		if(getActivity() == null)
			return;
		
		if(adapter != null){
			adapter.setArrayList(arr);
			adapter.notifyDataSetChanged();
		}else{
			setColorList(new int[]{R.color.map_primary_color,
					R.color.map_primary_light_color, R.color.map_primary_deep_color});
			adapter = new NearBySearchResultAdapter(getActivity(), arr, getColorList());
			adapter.setIconSelector(R.drawable.selector_map_primary_color);
			listView.setAdapter(adapter);
		}
		setEmptyLayout(!(arr.size() > 0));
		
	}

	@Override
	protected void configView(ListView listview, LayoutInflater inflater) {
		// TODO Auto-generated method stub
		getContentView().addView(initTopView(inflater), 0);
		
	}

	@Override
	protected void onListItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onListScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onListScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<PlaceVO> getPlaceList(){
		if(adapter != null){
			return adapter.getArrayList();
		}
		return null;
	}
	
	private View initTopView(LayoutInflater inflater){
		ViewGroup topContent = (ViewGroup) inflater.inflate(R.layout.include_dismiss_top_layout, null);
		topContent.setBackground(getResources().getDrawable(R.color.map_primary_color));
		TextView title = (TextView) topContent.findViewById(R.id.tv_dismiss_title);
		title.setTextColor(getResources().getColor(R.color.map_primary_light_color));
		title.setText(R.string.find_subway_nearby);
		View close = topContent.findViewById(R.id.iv_dismiss_close);
		close.setBackground(getResources().getDrawable(R.drawable.selector_map_primary_color));
		close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(getActivity() != null){
					getActivity().onBackPressed();
				}
			}
		});
	
		return topContent;
	}
}

