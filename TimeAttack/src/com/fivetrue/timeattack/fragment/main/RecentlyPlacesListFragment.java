package com.fivetrue.timeattack.fragment.main;


import java.util.ArrayList;

import com.api.common.BaseEntry;
import com.api.google.place.entry.PlacesEntry;
import com.api.google.place.model.PlaceVO;
import com.fivetrue.timeattack.activity.manager.NearbyActivityManager;
import com.fivetrue.timeattack.fragment.map.NearBySearchListFragment.PlaceItemDetailClickListener;
import com.fivetrue.timeattack.view.adapter.NearBySearchResultAdapter;

public class RecentlyPlacesListFragment extends BaseRecentlyListFragment<PlaceVO> {

	@Override
	public void onLoadListData(ArrayList<PlaceVO> arr) {
		// TODO Auto-generated method stub
		if(getActivity() == null || arr == null)
			return;
		if(adapter != null){
			adapter.setArrayList(arr);
			adapter.notifyDataSetChanged();
		}else{
			adapter = new NearBySearchResultAdapter(getActivity(), arr, getColorList(), new PlaceItemDetailClickListener() {
				
				@Override
				public void onClickDetailItem(PlaceVO item) {
					// TODO Auto-generated method stub
					if(item != null){
						NearbyActivityManager.newInstance(getActivity()).goToActivity(item);
					}
				}
			});
			listView.setAdapter(adapter);
		}
	}

	@Override
	protected ArrayList<PlaceVO> getListFromEntry(BaseEntry entry) {
		// TODO Auto-generated method stub
		ArrayList<PlaceVO> places = null;
		if(entry != null && entry instanceof PlacesEntry){
			places = ((PlacesEntry)entry).getPlaceList();
		}
		return places;
	}
}

