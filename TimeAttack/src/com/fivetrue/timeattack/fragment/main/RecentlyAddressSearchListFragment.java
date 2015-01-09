package com.fivetrue.timeattack.fragment.main;


import java.util.ArrayList;



import com.api.common.BaseEntry;
import com.api.google.geocoding.entry.GeocodingEntry;
import com.api.google.geocoding.model.AddressResultVO;
import com.fivetrue.timeattack.view.adapter.SearchLocationResultAdapter;

public class RecentlyAddressSearchListFragment extends BaseRecentlyListFragment<AddressResultVO> {

	@Override
	public void onLoadListData(ArrayList<AddressResultVO> arr) {
		// TODO Auto-generated method stub
		if(arr != null && arr.size() > 0){
			if(adapter != null){
				adapter.setArrayList(arr);
				adapter.notifyDataSetChanged();
			}else{
				adapter = new SearchLocationResultAdapter(getActivity(), arr, getColorList());
			}
			listView.setAdapter(adapter);
		}
	}
	
	@Override
	protected ArrayList<AddressResultVO> getListFromEntry(BaseEntry entry) {
		// TODO Auto-generated method stub
		ArrayList<AddressResultVO> addressList = new ArrayList<AddressResultVO>();
		if(entry != null && entry instanceof GeocodingEntry){
			addressList = ((GeocodingEntry)entry).getAddressList();
		}
		return addressList;
	}
	
}

