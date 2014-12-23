package com.fivetrue.timeattack.activity;

import com.fivetrue.timeattack.R;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class SettingActivity extends BaseActivity {
	
	private ViewGroup mContentView = null;
	
	static public class ViewHolder {
		public ScrollView scrollView = null;
		public ViewGroup layoutAccountSetting = null;
		public ViewGroup layoutMapSetting = null;
	}
	
	private ViewHolder mViewHolder = new ViewHolder();

	@Override
	public View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		
		mContentView = initViews(inflater);
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
		mViewHolder.layoutAccountSetting = (ViewGroup) view.findViewById(R.id.layout_account_setting);
		
		mViewHolder.layoutMapSetting.setBackground(getResources().getDrawable(R.color.setting_primary_color));
		mViewHolder.layoutAccountSetting.setBackground(getResources().getDrawable(R.color.setting_primary_color));
		
		view.setBackground(getResources().getDrawable(R.color.setting_primary_light_color));
		return view;
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
	public void requsetNetworkResultSuccess() {
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
