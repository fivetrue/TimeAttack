package com.fivetrue.timeattack.activity;

import java.util.ArrayList;

import com.api.common.BaseResponseListener;
import com.api.google.geocoding.GeocodingAPIHelper;
import com.api.google.geocoding.GeocodingConstants;
import com.api.google.geocoding.entry.GeocodingEntry;
import com.api.google.geocoding.model.AddressResultVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.SearchActivityManager;
import com.fivetrue.timeattack.view.adapter.SearchLocationResultAdapter;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchLocationActivity extends BaseActivity {

	private class ViewHolder{
		public ViewGroup layout_top = null;
		public ViewGroup layout_bottom = null;
		public ViewGroup layout_empty = null;
		public EditText et_input = null;
		public TextView tv_empty = null;
		public ListView lv_search = null;
		public View shadow_top = null;
		public View shadow_bottom = null;
	}

	private ViewGroup mContentView = null;
	private ViewHolder mViewHolder = new ViewHolder();
	
	
	private InputMethodManager mImeManager = null;
	private SearchLocationResultAdapter adapter = null;
	private GeocodingEntry mEntry = null;

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
		mViewHolder.layout_empty = (ViewGroup) mContentView.findViewById(R.id.layout_search_empty);
		mViewHolder.tv_empty = (TextView) mContentView.findViewById(R.id.tv_search_map);
		mViewHolder.et_input = (EditText) mContentView.findViewById(R.id.et_search_top);
		mViewHolder.lv_search = (ListView) mContentView.findViewById(R.id.lv_search_list);
		mViewHolder.shadow_top = mContentView.findViewById(R.id.shadow_search_top);
		mViewHolder.shadow_bottom = mContentView.findViewById(R.id.shadow_search_bottom);

		mViewHolder.et_input.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		mViewHolder.et_input.setOnEditorActionListener(onEditorActionListener);
	}

	private void initModels(){
		mImeManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	}
	
	private void initIntentData(){
		Bundle b = getIntent().getExtras();
		if(b != null){
			mEntry = b.getParcelable(SearchActivityManager.SEARCH_DATA);
			if(mEntry != null){
				setListData(mEntry.getAddressList());
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

	@Override
	public int getActionBarMenuResource() {
		// TODO Auto-generated method stub
		return INVALID_VALUE;
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
						setListData(response.getAddressList());
					}else{
						setEmptyLayout(true);
						makeToast(response.getStatusMessgae());
					}
				}else{
					showNetworkFailToast();
				}
			}
		});
	}
	
	private void setListData(ArrayList<AddressResultVO> entry){
		if(adapter == null){
			adapter = new SearchLocationResultAdapter(getApplicationContext(), R.layout.item_recently_use, entry);	
			mViewHolder.lv_search.setAdapter(adapter);
		}else{
			adapter.setArrayList(entry);
			adapter.notifyDataSetChanged();
		}
		
		setEmptyLayout(!(entry.size()>0));
	}
	
	private void setEmptyLayout(boolean enable){
		if(mViewHolder != null && mViewHolder.layout_empty != null
				&& mViewHolder.lv_search != null && mViewHolder.tv_empty != null){
			if(enable){
				mViewHolder.tv_empty.setText(R.string.invalid_location_place_message);
				mViewHolder.layout_empty.setVisibility(View.VISIBLE);
				mViewHolder.shadow_bottom.setVisibility(View.VISIBLE);
				mViewHolder.lv_search.setVisibility(View.GONE);
				
			}else{
				mViewHolder.tv_empty.setText(R.string.input_location_place_message);
				mViewHolder.layout_empty.setVisibility(View.GONE);
				mViewHolder.shadow_bottom.setVisibility(View.GONE);
				mViewHolder.lv_search.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onClickAcitionMenuLocationSearch(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActionBarBlending() {
		// TODO Auto-generated method stub
		return true;
	}

}
