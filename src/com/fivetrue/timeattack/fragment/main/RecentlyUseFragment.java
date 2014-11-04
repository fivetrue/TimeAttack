package com.fivetrue.timeattack.fragment.main;


import java.util.ArrayList;


import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.database.NetworkResultDBManager;
import com.fivetrue.timeattack.database.model.NetworkResult;
import com.fivetrue.timeattack.fragment.BaseListFragment;
import com.fivetrue.timeattack.view.adapter.RecentlyNetworkResultAdapter;

public class RecentlyUseFragment extends BaseListFragment<NetworkResult> {

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
		
		NetworkResultDBManager manager = new NetworkResultDBManager(getActivity());
		ArrayList<NetworkResult> arr = manager.getNetworkResults();
		if(arr != null && arr.size() > 0){
			listView.setVisibility(View.VISIBLE);
			emptyLayout.setVisibility(View.GONE);
			if(adapter != null){
				adapter.setArrayList(arr);
				adapter.notifyDataSetChanged();
			}else{
				adapter = new RecentlyNetworkResultAdapter(getActivity(), R.layout.item_recently_use, arr);
			}
			listView.setAdapter(adapter);
		}else{
			listView.setVisibility(View.GONE);
			emptyLayout.setVisibility(View.VISIBLE);
			emptyLayout.setBackgroundColor(getResources().getColor(R.color.list_background));
			tvEmpty.setText(R.string.recently_infomation_empty);
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

