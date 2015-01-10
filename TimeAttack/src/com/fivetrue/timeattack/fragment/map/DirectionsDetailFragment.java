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

import com.api.google.directions.model.RouteVO;
import com.api.google.directions.model.steps.StepTransitVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.MapActivityManager;
import com.fivetrue.timeattack.fragment.BaseListFragment;
import com.fivetrue.timeattack.view.adapter.DirectionsDetailInfoAdapter;

public class DirectionsDetailFragment extends BaseListFragment<StepTransitVO> {
	
	private RouteVO mEntry = null;
	
	
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
			mEntry = b.getParcelable(MapActivityManager.MAP_DATA);
		}
	}
	
	private void loadData(){
		if(mEntry != null){
			setListData(mEntry.getStepInfo());
		}
	}
	
	private void setListData(ArrayList<StepTransitVO> arr){
		if(getActivity() == null)
			return;
		
		if(adapter != null){
			adapter.setArrayList(arr);
			adapter.notifyDataSetChanged();
		}else{
			setColorList(new int[]{R.color.map_primary_color,
					R.color.map_primary_light_color, R.color.map_primary_deep_color});
			adapter = new DirectionsDetailInfoAdapter(getActivity(), arr, getColorList());
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
	
	public ArrayList<StepTransitVO> getDirectionsList(){
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
		title.setText(R.string.find_direction);
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
