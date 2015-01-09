package com.fivetrue.timeattack.activity;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.preference.MapPreferenceManager;
import com.google.android.gms.maps.GoogleMap;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class SettingActivity extends BaseActivity {
	
	private ViewGroup mContentView = null;
	
	static public class ViewHolder {
		public ScrollView scrollView = null;
		public ViewGroup layoutMapSetting = null;
		public ViewGroup layoutMapSettingDetail = null;
		public TextView tvMapSettingType = null;
		public TextView tvMapSettingInteract = null;
		public RadioGroup rgMapSetting = null;
		public CheckBox cbMapSettingRotation = null;
		public CheckBox cbMapSettingZoomButton = null;
		
		
		public ViewGroup layoutAccountSetting = null;
	}
	
	private ViewHolder mViewHolder = new ViewHolder();
	
	private MapPreferenceManager mMapPref = null;

	@Override
	public View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		
		initModels();
		mContentView = initViews(inflater);
		initViewData();
		
		return mContentView;
	}
	
	private ViewGroup initViews(LayoutInflater inflater){
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_setting, null);
		
		getCustomActionBar().setBackGroundColorRes(R.color.setting_primary_color, R.color.setting_primary_dark_color);
		getCustomActionBar().setHomeIconLineColor(R.color.setting_primary_light_color);
		getCustomActionBar().setIconSelector(R.drawable.selector_setting_primary_color);
		
		if(getDrawerFragment() != null){
			getDrawerFragment().setBackGroundColorRes(R.color.setting_primary_color, R.color.setting_primary_dark_color);
			getDrawerFragment().setLineColor(R.color.setting_primary_light_color);
			getDrawerFragment().setIconSelector(R.drawable.selector_setting_primary_color);
		}
		
		mViewHolder.scrollView = (ScrollView) view.findViewById(R.id.scroll_setting);
		mViewHolder.layoutMapSetting = (ViewGroup) view.findViewById(R.id.layout_map_setting);
		mViewHolder.layoutMapSettingDetail  = (ViewGroup) view.findViewById(R.id.layout_map_setting_detail);
		mViewHolder.tvMapSettingType = (TextView)  view.findViewById(R.id.tv_setting_map_type);
		mViewHolder.tvMapSettingInteract = (TextView)  view.findViewById(R.id.tv_setting_map_interact);
		
		mViewHolder.cbMapSettingRotation = (CheckBox) view.findViewById(R.id.cb_setting_map_rotation);
		mViewHolder.cbMapSettingZoomButton = (CheckBox) view.findViewById(R.id.cb_setting_map_zoom_button);
		mViewHolder.rgMapSetting = (RadioGroup) view.findViewById(R.id.rg_setting_map);
		
		mViewHolder.layoutMapSetting.setBackground(getResources().getDrawable(R.color.setting_primary_color));
		mViewHolder.tvMapSettingType.setBackground(getResources().getDrawable(R.color.setting_primary_soft_color));
		mViewHolder.tvMapSettingInteract.setBackground(getResources().getDrawable(R.color.setting_primary_soft_color));
		
		mViewHolder.layoutAccountSetting = (ViewGroup) view.findViewById(R.id.layout_account_setting);
		mViewHolder.layoutAccountSetting.setBackground(getResources().getDrawable(R.color.setting_primary_color));
		
		
		view.setBackground(getResources().getDrawable(R.color.setting_primary_light_color));
		return view;
	}
	
	private void initViewData(){
		switch(mMapPref.getMapType()){
		case GoogleMap.MAP_TYPE_NORMAL :
			mViewHolder.rgMapSetting.check(R.id.rb_setting_map_normal);
			break;
			
		case GoogleMap.MAP_TYPE_SATELLITE :
			mViewHolder.rgMapSetting.check(R.id.rb_setting_map_satellite);
			break;
			
		case GoogleMap.MAP_TYPE_TERRAIN :
			mViewHolder.rgMapSetting.check(R.id.rb_setting_map_terrain);
			break;
			
		case GoogleMap.MAP_TYPE_HYBRID :
			mViewHolder.rgMapSetting.check(R.id.rb_setting_map_hybrid);
			break;
		}
		
		mViewHolder.rgMapSetting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(group != null){
					switch(checkedId){
					case R.id.rb_setting_map_normal : 
						mMapPref.setMapType(GoogleMap.MAP_TYPE_NORMAL);
						break;
					case R.id.rb_setting_map_satellite :
						mMapPref.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
						break;
						
					case R.id.rb_setting_map_terrain :
						mMapPref.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
						break;
						
					case R.id.rb_setting_map_hybrid :
						mMapPref.setMapType(GoogleMap.MAP_TYPE_HYBRID);
						break;
					}
				}
			}
		});
		
		mViewHolder.cbMapSettingRotation.setChecked(mMapPref.isRotateEnable());
		mViewHolder.cbMapSettingRotation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(buttonView != null){
					buttonView.setChecked(isChecked);
					mMapPref.setRotateEnable(isChecked);
				}
			}
		});
		
		mViewHolder.cbMapSettingZoomButton.setChecked(mMapPref.isZoomButtonEnable());
		mViewHolder.cbMapSettingZoomButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(buttonView != null){
					buttonView.setChecked(isChecked);
					mMapPref.setZoomButtonEnable(isChecked);
				}
			}
		});
		
	}
	
	private void initModels(){
		mMapPref = MapPreferenceManager.newInstance(this);
	}
	

	@Override
	public String getActionBarTitleName() {
		// TODO Auto-generated method stub
		return getString(R.string.actionbar_setting);
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
	public void requsetNetworkResultSuccess(String url, String request) {
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

}
