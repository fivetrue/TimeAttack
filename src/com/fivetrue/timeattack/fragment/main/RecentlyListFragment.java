package com.fivetrue.timeattack.fragment.main;


import java.util.ArrayList;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.database.NetworkResultDBManager;
import com.fivetrue.timeattack.database.model.NetworkResult;
import com.fivetrue.timeattack.fragment.BaseListFragment;
import com.fivetrue.timeattack.view.adapter.RecentlyNetworkResultAdapter;

public class RecentlyListFragment extends BaseListFragment<NetworkResult> {

	
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
			if(adapter != null){
				adapter.setArrayList(arr);
				adapter.notifyDataSetChanged();
			}else{
				setColorList(new int[]{R.color.main_primary_color,
						R.color.main_primary_light_color, R.color.main_primary_deep_color});
				adapter = new RecentlyNetworkResultAdapter(getActivity(), arr, getColorList());
				adapter.setIconSelector(R.drawable.selector_main_primary_color);
			}
			listView.setAdapter(adapter);
			setEmptyLayout(false);
		}else{
			getTvEmpty().setText(R.string.recently_infomation_empty);
			getTvEmpty().setTextColor(getResources().getColor(R.color.main_primary_deep_color));
			getEmptyLayout().setBackground(getResources().getDrawable(R.color.main_primary_light_color));
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

