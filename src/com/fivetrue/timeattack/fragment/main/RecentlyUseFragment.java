package com.fivetrue.timeattack.fragment.main;


import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.api.common.BaseResponseListener;
import com.api.google.place.PlaceAPIHelper;
import com.api.google.place.PlacesConstans;
import com.api.google.place.PlaceAPIHelper.API_TYPE;
import com.api.google.place.converter.PlacesConverter;
import com.api.google.place.entry.PlacesEntry;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.BaseActivity;
import com.fivetrue.timeattack.database.NetworkResultDBHelper;
import com.fivetrue.timeattack.database.NetworkResultDBManager;
import com.fivetrue.timeattack.database.model.NetworkResult;
import com.fivetrue.timeattack.database.model.NetworkResult.Type;
import com.fivetrue.timeattack.fragment.BaseListFragment;
import com.fivetrue.timeattack.model.RecentlyUseItem;
import com.fivetrue.timeattack.view.adapter.RecentlyUseAdapter;

public class RecentlyUseFragment extends BaseListFragment<RecentlyUseItem> {

	@Override
	protected View initHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected View initFooter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onLoadListData() {
		// TODO Auto-generated method stub
		
//			new PlaceAPIHelper(getActivity(), API_TYPE.NEAR_BY_SEARCH, ((BaseActivity)getActivity())).
//			requestNearBySearchSubway("37.497942,127.027621", "10000", new BaseResponseListener<PlacesEntry>() {
//				@Override
//
//				public void onResponse(PlacesEntry response) {
//					// TODO Auto-generated method stub
//					if(response != null && getActivity() != null){
//						if(response.getStatus().equals(PlacesConstans.Status.OK.toString())){
//							ArrayList<RecentlyUseItem> list = new ArrayList<RecentlyUseItem>();
//							RecentlyUseItem item = new RecentlyUseItem();
//							item.setPlace(response);
////							for(int i = 0 ; i < 100 ; i ++){
////								list.add(item);	
////							}
//							adapter = new RecentlyUseAdapter(getActivity(), R.layout.item_recently_use, list);
//							listView.setAdapter(adapter);
//						}else{
//							Toast.makeText(getActivity(), response.getStatus(), Toast.LENGTH_SHORT).show();
//						}
//					}
//
//				}
//			});
		NetworkResultDBManager manager = new NetworkResultDBManager(getActivity());
		ArrayList<NetworkResult> arr = manager.getNetworkResults();
		if(arr != null && arr.size() > 0){
			ArrayList<RecentlyUseItem> list = new ArrayList<RecentlyUseItem>();
			for(NetworkResult result : arr){
				RecentlyUseItem item = new RecentlyUseItem();
				if(result.getType().equals(Type.Place)){
					try {
						Log.e("", result.getResult());
						JSONObject json = new JSONObject(result.getResult());
						item.setPlace(new PlacesConverter().onReceive(json));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				list.add(item);
			}
			
			adapter = new RecentlyUseAdapter(getActivity(), R.layout.item_recently_use, list);
			listView.setAdapter(adapter);
		}
		
	}

	@Override
	protected void configListView(ListView listview) {
		// TODO Auto-generated method stub
		listView.setDividerHeight(0);
		
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

