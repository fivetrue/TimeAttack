package com.fivetrue.timeattack.fragment.search;


import java.util.ArrayList;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.api.google.geocoding.entry.GeocodingEntry;
import com.api.google.geocoding.model.AddressResultVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.BaseListFragment;
import com.fivetrue.timeattack.view.adapter.SearchLocationResultAdapter;

public class AddressSearchResultFragment extends BaseListFragment<AddressResultVO> {
	
	static public final String ADDRESS_DATA_KEY = "ADDRESS_DATA_KEY";

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
		if(getArguments() != null){
			GeocodingEntry entry = getArguments().getParcelable(ADDRESS_DATA_KEY);
			if(entry != null){
				onLoadListData(entry.getAddressList());
			}
		}
	}
	
	public void onLoadListData(ArrayList<AddressResultVO> arr){
		if(arr != null && arr.size() > 0){
			if(adapter != null){
				adapter.setArrayList(arr);
				adapter.notifyDataSetChanged();
			}else{
				adapter = new SearchLocationResultAdapter(getActivity(), R.layout.item_recently_use, arr);
			}
			listView.setAdapter(adapter);
			setEmptyLayout(false);
		}else{
			getTvEmpty().setText(R.string.recently_infomation_empty);
			setEmptyLayout(true);
		}
	}

	@Override
	protected void configView(ListView listview, LayoutInflater inflater) {
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
	
}

