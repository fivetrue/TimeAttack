package com.fivetrue.timeattack.activity;


import com.api.common.BaseResponseListener;
import com.api.google.geocoding.GeocodingAPIHelper;
import com.api.google.geocoding.GeocodingConstants;
import com.api.google.geocoding.entry.GeocodingEntry;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.SearchActivityManager;
import com.fivetrue.timeattack.fragment.search.AddressSearchListFragment;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchLocationActivity extends BaseActivity {

	private class ViewHolder{
		public ViewGroup layout_top = null;
		public ViewGroup layout_bottom = null;
		public EditText et_input = null;
		public View shadow_top = null;
	}

	private ViewGroup mContentView = null;
	private ViewHolder mViewHolder = new ViewHolder();
	
	
	private InputMethodManager mImeManager = null;
	private GeocodingEntry mEntry = null;
	
	private AddressSearchListFragment mSearchFragment = null;
	

	@Override
	public View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub

		mContentView = (ViewGroup) inflater.inflate(R.layout.activity_search, null);
		initViews();
		initModels();
		initIntentData();

		return mContentView;
	}

	private void initViews(){
		mViewHolder.layout_top = (ViewGroup) mContentView.findViewById(R.id.layout_search_top);
		mViewHolder.layout_bottom = (ViewGroup) mContentView.findViewById(R.id.layout_search_bottom);
		mViewHolder.et_input = (EditText) mContentView.findViewById(R.id.et_search_top);
		mViewHolder.shadow_top = mContentView.findViewById(R.id.shadow_search_top);

		mViewHolder.et_input.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		mViewHolder.et_input.setOnEditorActionListener(onEditorActionListener);
		
		getCustomActionBar().setBackGroundColorRes(R.color.search_primary_color, R.color.search_primary_dark_color);
		getCustomActionBar().setHomeIconLineColor(R.color.search_primary_light_color);
		getCustomActionBar().setIconSelector(R.drawable.selector_search_primary_color);
		
		if(getDrawerFragment() != null){
			getDrawerFragment().setBackGroundColorRes(R.color.search_primary_color, R.color.search_primary_dark_color);
			getDrawerFragment().setLineColor(R.color.search_primary_light_color);
			getDrawerFragment().setIconSelector(R.drawable.selector_search_primary_color);
		}
		
		mContentView.setBackground(getResources().getDrawable(R.color.search_primary_light_color));
		
	}

	private void initModels(){
		mImeManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	}
	
	private void initIntentData(){
		Bundle b = getIntent().getExtras();
		if(b != null){
			mEntry = b.getParcelable(SearchActivityManager.SEARCH_DATA);
			if(mEntry != null){
				setListData(mEntry);
				if(mViewHolder.layout_top != null){
					mViewHolder.layout_top.setVisibility(View.GONE);
					mViewHolder.shadow_top.setVisibility(View.GONE);
				}
			}
		}
	}

	@Override
	public String getActionBarTitleName() {
		// TODO Auto-generated method stub
		if(mEntry != null){
			return getString(R.string.location_infomation);
		}
		return getString(R.string.location_search);
	}

	@Override
	public String getActionBarSubTitle() {
		// TODO Auto-generated method stub
		if(mEntry != null){
			if(mEntry.getAddressList().size() > 0){
				return mEntry.getAddressList().get(0).getAddressComponents().get(0).getLongName();
			}
		}
		return null;
	}

//	@Override
//	public int getActionBarMenuResource() {
//		// TODO Auto-generated method stub
//		return INVALID_VALUE;
//	}
	
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

	private OnEditorActionListener onEditorActionListener = new OnEditorActionListener() {

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			// TODO Auto-generated method stub

			switch(actionId){
			case EditorInfo.IME_ACTION_SEARCH :
				if(mImeManager != null && mViewHolder.et_input != null){
					mImeManager.hideSoftInputFromWindow(mViewHolder.et_input.getWindowToken(), 0);
				}
				onLoadSearchLocation();
				return true;
			}
			return false;
		}
	};

	private void onLoadSearchLocation(){
		if(mViewHolder.et_input == null 
				|| TextUtils.isEmpty(mViewHolder.et_input.getText().toString().trim())){
			return ;
		}
		
		final String input = mViewHolder.et_input.getText().toString().trim();
		new GeocodingAPIHelper(this, this).requestGeocoding(input, new BaseResponseListener<GeocodingEntry>() {

			@Override
			public void onResponse(GeocodingEntry response) {
				// TODO Auto-generated method stub
				if(response != null){
					if(response.getStatus().equals(GeocodingConstants.Status.OK.toString())){
						setListData(response);
					}else{
//						setEmptyLayout(true);
						makeToast(response.getStatusMessgae());
					}
				}else{
					showNetworkFailToast();
				}
			}
		});
	}
	
	private void setListData(GeocodingEntry entry){
		if(mSearchFragment == null){
			Bundle argument = new Bundle();
			argument.putParcelable(AddressSearchListFragment.ADDRESS_DATA_KEY, entry);
			mSearchFragment = (AddressSearchListFragment) createFragment(mViewHolder.layout_bottom.getId(), AddressSearchListFragment.class,
					"search_result", INVALID_VALUE, argument,  R.anim.slide_in_top, R.anim.slide_in_top);
		}else{
			mSearchFragment.onLoadListData(entry.getAddressList());
		}
		
	}
	
	@Override
	public void onClickAcitionMenuLocationSearch(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActionBarBlending() {
		// TODO Auto-generated method stub
		return getIntent().getExtras() != null;
	}

}
