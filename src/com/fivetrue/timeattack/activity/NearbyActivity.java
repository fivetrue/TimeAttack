package com.fivetrue.timeattack.activity;


import com.api.google.place.model.PlaceVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.NearbyActivityManager;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

public class NearbyActivity extends BaseActivity {

//	private class ViewHolder{
//		public ViewGroup layout_top = null;
//		public ViewGroup layout_bottom = null;
//		public EditText et_input = null;
//		public View shadow_top = null;
//	}

	private ViewGroup mContentView = null;
//	private ViewHolder mViewHolder = new ViewHolder();
	
	
	private InputMethodManager mImeManager = null;
	private PlaceVO mEntry = null;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub

		mContentView = (ViewGroup) inflater.inflate(R.layout.activity_nearby, null);
		initViews();
		initIntentData();
		initData();

		return mContentView;
	}

	private void initViews(){
//		mViewHolder.layout_top = (ViewGroup) mContentView.findViewById(R.id.layout_search_top);
//		mViewHolder.layout_bottom = (ViewGroup) mContentView.findViewById(R.id.layout_search_bottom);
//		mViewHolder.et_input = (EditText) mContentView.findViewById(R.id.et_search_top);
//		mViewHolder.shadow_top = mContentView.findViewById(R.id.shadow_search_top);
//
//		mViewHolder.et_input.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
//		mViewHolder.et_input.setOnEditorActionListener(onEditorActionListener);
		
		getCustomActionBar().setBackGroundColorRes(R.color.nearby_primary_color, R.color.nearby_primary_dark_color);
		getCustomActionBar().setHomeIconLineColor(R.color.nearby_primary_light_color);
		getCustomActionBar().setIconSelector(R.drawable.selector_nearby_primary_color);
		
		if(getDrawerFragment() != null){
			getDrawerFragment().setBackGroundColorRes(R.color.nearby_primary_color, R.color.nearby_primary_dark_color);
			getDrawerFragment().setLineColor(R.color.nearby_primary_light_color);
			getDrawerFragment().setIconSelector(R.drawable.selector_nearby_primary_color);
		}
		
		mContentView.setBackground(getResources().getDrawable(R.color.nearby_primary_light_color));
		
	}

	private void initIntentData(){
		Bundle b = getIntent().getExtras();
		if(b != null){
			mEntry = b.getParcelable(NearbyActivityManager.NEARBY_DATA);
			if(mEntry != null){
			}
		}
	}
	
	private void initData(){
		if(mEntry != null){
		}
	}

	@Override
	public String getActionBarTitleName() {
		// TODO Auto-generated method stub
		if(mEntry != null){
			if(!TextUtils.isEmpty(mEntry.getName())){
				return mEntry.getName();
			}
		}
		return null;
	}

	@Override
	public String getActionBarSubTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewGroup getActionBarMenuView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHomeAsUp() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void requsetNetworkResultSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void outOfService(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void avaliableService(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void temporarilyServiceUnavailable(String provider, int status,
			Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void changeLocation(Location location) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void onClickAcitionMenuLocationSearch(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActionBarBlending() {
		// TODO Auto-generated method stub
		return false;
	}

}
