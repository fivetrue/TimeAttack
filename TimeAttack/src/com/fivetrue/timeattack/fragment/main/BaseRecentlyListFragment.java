package com.fivetrue.timeattack.fragment.main;


import java.util.ArrayList;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.api.common.BaseEntry;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.BaseListFragment;

abstract public class BaseRecentlyListFragment<T> extends BaseListFragment<T> {

	static public final String RECENTRY_DATA = "RECENTRY_DATA";
	
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
			BaseEntry entry = getArguments().getParcelable(RECENTRY_DATA);
			if(entry != null){
				setColorList(new int[]{R.color.main_primary_color,
						R.color.main_primary_light_color, R.color.main_primary_deep_color});
				ArrayList<T> list = getListFromEntry(entry);
				if(list != null && list.size() > 0){
					onLoadListData(list);
					setEmptyLayout(false);
					if(adapter != null){
						adapter.setIconSelector(R.drawable.selector_main_primary_color);
					}
				}else{
					getTvEmpty().setText(R.string.recently_infomation_empty);
					getTvEmpty().setTextColor(getResources().getColor(R.color.main_primary_deep_color));
					getEmptyLayout().setBackground(getResources().getDrawable(R.color.main_primary_light_color));
					setEmptyLayout(true);
				}
			} 
		}
	}
	
	public abstract void onLoadListData(ArrayList<T> arr);
	
	protected abstract ArrayList<T> getListFromEntry(BaseEntry entry);
	
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
	
	public void onResetData(ArrayList<T> list){
		if(adapter != null){
			adapter.getArrayList().clear();
			adapter = null;
		}
		onLoadListData(list);
	}
}

