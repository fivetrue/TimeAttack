package com.fivetrue.timeattack.fragment.main;


import java.util.ArrayList;


import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class NearBySearchFragment extends BaseListFragment<PlaceVO> {

	private Location mLocation = null;
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
			mLocation = (Location) b.getParcelable(MapActivityManager.MAP_DATA);
		}
	}
	
	private void loadData(){
		if(mLocation != null){
			new PlaceAPIHelper(getActivity(), API_TYPE.NEAR_BY_SEARCH, ((BaseActivity)getActivity()))
			.requestNearBySearchSubway(mLocation.getLatitude(), mLocation.getLongitude(), 1000, new BaseResponseListener<PlacesEntry>() {
				
				@Override
				public void onResponse(PlacesEntry response) {
					// TODO Auto-generated method stub
					if(response != null){
						if(response.getStatus().equals(PlacesConstans.Status.OK.toString())){
							setListData(response.getPlaceList());
						}else{
							makeToast(response.getStatusMessgae());
							setEmptyLayout(true);
						}
					}else{
						showNetworkFailToast();
					}
				}
			});
		}
	}
	
	private void setListData(ArrayList<PlaceVO> arr){
		if(getActivity() == null)
			return;
		
		if(adapter != null){
			adapter.setArrayList(arr);
			adapter.notifyDataSetChanged();
		}else{
			adapter = new NearBySearchResultAdapter(getActivity(), R.layout.item_recently_use, arr);
			listView.setAdapter(adapter);
		}
		setEmptyLayout(!(arr.size() > 0));
		
	}

	@Override
	protected void configListView(ListView listview) {
		// TODO Auto-generated method stub
		
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
}

