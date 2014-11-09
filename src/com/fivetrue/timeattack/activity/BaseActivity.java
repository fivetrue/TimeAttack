package com.fivetrue.timeattack.activity;

import com.api.common.Constants;
import com.api.common.IRequestResult;
import com.api.google.directions.DirectionsConstants;
import com.api.google.directions.converter.DirectionsConverter;
import com.api.google.directions.entry.DirectionsEntry;
import com.api.google.geocoding.GeocodingConstants;
import com.api.google.geocoding.converter.GeocodingConverter;
import com.api.google.geocoding.entry.GeocodingEntry;
import com.api.google.place.PlacesConstans;
import com.api.google.place.converter.PlacesConverter;
import com.api.google.place.entry.PlacesEntry;
import com.api.seoul.SeoulAPIConstants;
import com.api.seoul.subway.converter.SubwayArrivalInfoConverter;
import com.api.seoul.subway.converter.SubwayInfoConverter;
import com.api.seoul.subway.entry.SubwayArrivalInfoEntry;
import com.api.seoul.subway.entry.SubwayInfoEntry;
import com.fivetrue.location.activity.LocationActivity;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.database.NetworkResultDBManager;
import com.fivetrue.timeattack.fragment.DrawerFragment;
import com.fivetrue.timeattack.fragment.DrawerFragment.OnDrawerMenuClickListener;
import com.fivetrue.utils.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Fivetrue
 *
 */
	
abstract public class BaseActivity extends LocationActivity implements IRequestResult{
	
	protected boolean isRunningGps = false;
	protected final int INVALID_VALUE = -1;
	private DrawerLayout mDrawerLayout = null;

	private ViewGroup mContentView = null;
	private ViewGroup mLayoutDrawer = null;
	static protected DrawerFragment mFragmentDrawer = null;
	private NetworkResultDBManager mNetworkResultDBM = null;
	
	private ViewGroup mHomeViewGroup = null;
	private float mHomeImageX = 0;
	private int mActionBarBackgroundSelectorRes = 0;
	
	private LayoutInflater mInflater = null;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_base);
		initViews();
		initActionBarSetting();
		initModels();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(isRunningGps){
			onStartLocationService();
		}
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(isLocationServiceRunning()){
			isRunningGps = true;
			onPauseLocationService();
		}else{
			isRunningGps = false;
		}
	}
	/**
	 * 기본적인 View를 초기화함.
	 */
	private void initViews(){
		mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mContentView = (ViewGroup) findViewById(R.id.layout_main_frame);
		mLayoutDrawer = (ViewGroup) findViewById(R.id.layout_drawer);
		
		if(mFragmentDrawer == null){
			mFragmentDrawer = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
			mFragmentDrawer.setOnClickDrawerMenuClickListener(mDrawerMenuClickListener);
		}
		View contentView = onCreateView(mInflater);
		if(contentView != null){
			mContentView.addView(contentView);
		}
	}
	
	/**
	 * Actionbar Setting Area Start
	 */
	public void initActionBarSetting(){
		
		mActionBarBackgroundSelectorRes = R.drawable.selector_common_alpha_raleway_yellow;
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		getActionBar().setTitle(getActionBarTitleName());
		if(!TextUtils.isEmpty(getActionBarSubTitle())){
			getActionBar().setSubtitle(getActionBarSubTitle());
		}
		initActionBarHomeView();
		mDrawerLayout.setDrawerListener(onDrawerListener);
	}
	
	private void initActionBarHomeView(){
		ViewGroup actionBar = (ViewGroup) getActionBarView();
		
		if(actionBar != null){
			ViewGroup homeViewGroup = (ViewGroup) actionBar.getChildAt(0);
			if(homeViewGroup != null){
				homeViewGroup.setBackgroundResource(mActionBarBackgroundSelectorRes);
				mHomeViewGroup = (ViewGroup) homeViewGroup.getChildAt(0);
				if(mHomeViewGroup != null){
					mHomeImageX = mHomeViewGroup.getX();
					View homeImage = mHomeViewGroup.getChildAt(0);
					
					if(homeImage != null && homeImage instanceof ImageView){
						((ImageView)homeImage).setImageResource(isHomeAsUp() ? R.drawable.ic_action_previous_item : R.drawable.ic_drawer);
					}
				
					View homeIcon = mHomeViewGroup.getChildAt(1);
					if(homeIcon != null){
						homeIcon.setVisibility(View.GONE);
					}
				}
			}
		}
	}
	
	private void initActionBarButtons(Menu menu){
		if(menu != null){
			for(int i = 0 ; i < menu.size() ; i ++){
				View view = menu.getItem(i).getActionView();
				if(view != null){
					view.setOnClickListener(onClickActionBarItem);
				}
			}
		}
	}
	
	DrawerListener onDrawerListener = new DrawerListener() {

		private int rotateValue = 180;
		private int moveValue = 10;
		private int reverseInt = 1;

		@Override
		public void onDrawerStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			// TODO Auto-generated method stub
			if(mHomeViewGroup != null){
				mHomeViewGroup.setRotation(mHomeImageX - ((arg1 * rotateValue) * reverseInt));
				mHomeViewGroup.setX(mHomeImageX - (arg1 * moveValue));
			}
		}

		@Override
		public void onDrawerOpened(View arg0) {
			// TODO Auto-generated method stub
			reverseInt = -1;
			getActionBar().setTitle(R.string.app_name);
			getActionBar().setSubtitle(null);
		}


		@Override
		public void onDrawerClosed(View arg0) {
			// TODO Auto-generated method stub
			reverseInt = 1;
			getActionBar().setTitle(getActionBarTitleName());
			if(!TextUtils.isEmpty(getActionBarSubTitle())){
				getActionBar().setSubtitle(getActionBarSubTitle());
			}
		}
	};
	
	/**
	 * Actionbar Setting Area End
	 */
	
	private void initModels(){
		mNetworkResultDBM = new NetworkResultDBManager(getApplicationContext());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(getActionBarMenuResource() != INVALID_VALUE){
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(getActionBarMenuResource(), menu);
			initActionBarButtons(menu);
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(onSelectedActionBarItem(item)){
			return false;
		}else{
			return super.onOptionsItemSelected(item);
		}
	}
	
	protected boolean onSelectedActionBarItem(MenuItem item){
		int id = item.getItemId();
		
		switch(id){
		case android.R.id.home :
			if(isHomeAsUp()){
//				NavUtils.navigateUpFromSameTask(this);
				onBackPressed();
			}else{
				if(mDrawerLayout.isDrawerOpen(mLayoutDrawer)){
					mDrawerLayout.closeDrawer(mLayoutDrawer);
				}else{
					mDrawerLayout.openDrawer(mLayoutDrawer);
				}
			}
			return true;
		}
		
		return false;
	}
	
	public View getActionBarView() {
	    Window window = getWindow();
	    View v = window.getDecorView();
	    int resId = getResources().getIdentifier("action_bar", "id", "android");
	    return v.findViewById(resId);
	}
	
	public void removeFragment(Fragment f, int startAni, int endAni){
		FragmentTransaction trans =  getSupportFragmentManager().beginTransaction();
		if(startAni != INVALID_VALUE && endAni != INVALID_VALUE){
			trans.setCustomAnimations(startAni, endAni);
		}
		trans.remove(f);
		trans.commit();
	}
	
	
	
	public void hideFragment(Fragment f,  int startAni, int endAni){
		FragmentTransaction trans =  getSupportFragmentManager().beginTransaction();
		if(startAni != INVALID_VALUE && endAni != INVALID_VALUE){
			trans.setCustomAnimations(startAni, endAni);
		}
		trans.hide(f);
		trans.commit();
	}
	
	public Fragment createFragment(Class<?> fragmentClass, String tag, Bundle argument){
		return createFragment(INVALID_VALUE, fragmentClass, tag, INVALID_VALUE, argument , INVALID_VALUE, INVALID_VALUE);
	}
	
	public Fragment createFragment(int containLayoutId, Class<?> fragmentClass, String tag, Bundle argument){
		return createFragment(containLayoutId, fragmentClass, tag, INVALID_VALUE, argument , INVALID_VALUE, INVALID_VALUE);
	}
	
	public Fragment createFragment(Class<?> fragmentClass, String tag, int transit, Bundle argument){
		return createFragment(INVALID_VALUE, fragmentClass, tag, transit, argument , INVALID_VALUE, INVALID_VALUE);
	}
	
	public Fragment createFragment(Class<?> fragmentClass, String tag, int transit, Bundle argument, int startAni, int endAni){
		return createFragment(INVALID_VALUE, fragmentClass, tag, transit, argument , startAni, endAni);
	}
	
	public Fragment createFragment(int containLayoutId, Class<?> fragmentClass
			, String tag , int transit, Bundle argument, int aniStart, int aniEnd){
		
		if(containLayoutId == INVALID_VALUE){
			containLayoutId  = R.id.layout_main_frame;
		}
		
		if(transit == INVALID_VALUE){
			transit = FragmentTransaction.TRANSIT_NONE;
		}
		Fragment f = null;
		try {
			f = getSupportFragmentManager().findFragmentByTag(tag);
			FragmentTransaction trans =  getSupportFragmentManager().beginTransaction();
			if(f != null){
				trans.show(f);
			}else{
				f = (Fragment) fragmentClass.newInstance();
				f.setArguments(argument);
				if(aniStart != INVALID_VALUE && aniEnd != INVALID_VALUE){
					trans.setCustomAnimations(aniStart, aniEnd);
				}
				trans.replace(containLayoutId, f, tag);
				trans.setTransition(transit);
				trans.commit();
			}
			return f;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	abstract View onCreateView(LayoutInflater inflater);
	
	abstract String getActionBarTitleName();
	
	abstract String getActionBarSubTitle();
	
	abstract int getActionBarMenuResource();
	
	abstract boolean isHomeAsUp();
	
	abstract void requsetNetworkResultSuccess();
	
	abstract void onClickAcitionMenuLocationSearch(View view);
	
	protected OnDrawerMenuClickListener  mDrawerMenuClickListener = new OnDrawerMenuClickListener() {
		
		@Override
		public void onMenuClick(ViewGroup parent, ViewGroup itemLayout,
				TextView itemText) {
			// TODO Auto-generated method stub
			if(parent != null){
				for(int i = 0 ; i < parent.getChildCount() ; i ++){
					ViewGroup view = (ViewGroup) parent.getChildAt(i);
					if(view != null){
						for(int index = 0 ; index < view.getChildCount() ; index ++){
							View child = view.getChildAt(index);
							if(child != null){
								if(child instanceof TextView){
									child.setSelected(false);
								}
							}
						}
					}
				}
			}
			
			if(itemText == null && itemLayout == null){
				Log.e(getPackageName(), "Drawer layout item is null");
				return;
			}
			
			itemText.setSelected(true);
			
			if(mDrawerLayout != null && mLayoutDrawer != null){
				mDrawerLayout.closeDrawer(mLayoutDrawer);
			}
			
		}
	};
	
	protected OnClickListener onClickActionBarItem = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.action_item_setting :
			{
				//설정.
				Toast.makeText(getApplicationContext(), "action_settings", Toast.LENGTH_SHORT).show();
				return ;
			}
				

			case R.id.action_item_place :
			{
				//주변 검색.
				Toast.makeText(getApplicationContext(), "action_place", Toast.LENGTH_SHORT).show();
				return ;
			}
			
			case R.id.action_item_searching :
			{
				//위치 검색.
				startActivity(SearchLocationActivity.class);
				return;
			}
			case R.id.action_item_location_searching :
			{
				//현재 위치 탐색.
				onClickAcitionMenuLocationSearch(v);
				return;
			}
				
			case R.id.action_item_map : 
			{
				//지도 이동.
				startActivity(MapActivity.class);
				return ;
			}
			
			case R.id.action_item_alarm :
			{
				//알람.
				return;
			}
			case R.id.action_item_add_alarm :
			{
				//알람 추가.
				return;
			}
			case R.id.action_item_directions :
			{
				//경로 검색.
				return;
			}	
			}
		}
	};
	
	public void onSuccessRequest(String url, org.json.JSONObject request) {
		boolean isResultOK = false;
		if(url.contains(Constants.GoogleDirectionsAPI.DIRECTION_API_HOST)){
			DirectionsEntry entry = new DirectionsConverter().onReceive(request);
			if(entry.getStatus().equals(DirectionsConstants.Status.OK.toString())){
				isResultOK = true;
			}
		}else if(url.contains(Constants.GooglePlaceAPI.PLACE_API_HOST)){
			PlacesEntry entry = new PlacesConverter().onReceive(request);
			if(entry.getStatus().equals(PlacesConstans.Status.OK.toString())){
				isResultOK = true;
			}
		}else if(url.contains(Constants.GoogleGeocodingAPI.GEOCODING_API_HOST)){
			GeocodingEntry entry = new GeocodingConverter().onReceive(request);
			if(entry.getStatus().equals(GeocodingConstants.Status.OK.toString())){
				isResultOK = true;
			}
		}else if(url.contains(SeoulAPIConstants.API_HOST)){
			
			if(url.contains(SeoulAPIConstants.Subway.ARRIVAL_INFO_SERVICE)){
				SubwayArrivalInfoEntry entry = new SubwayArrivalInfoConverter().onReceive(request);
				if(entry.getStatus().equals(SeoulAPIConstants.ResultInfo.OK)){
					isResultOK = true;
				}
			}else if(url.contains(SeoulAPIConstants.Subway.FIND_INFO_SERVICE)){
				SubwayInfoEntry entry = new SubwayInfoConverter().onReceive(request);
				if(entry.getStatus().equals(SeoulAPIConstants.ResultInfo.OK)){
					isResultOK = true;
				}
			}
		}
		if(isResultOK){
			mNetworkResultDBM.insertNetworkResult(url, request);
			requsetNetworkResultSuccess();
		}
	};
	
	@Override
	public void onFailRequest(String url) {
		// TODO Auto-generated method stub
	}
	
	protected void log(String msg){
		Logger.e(getPackageName(), getClass().getName() + " = " + msg);
	}
	
	protected void startActivity(Class<?> cls){
		startActivity(cls, false, null);
	}
	
	protected void startActivity(Class<?> cls , boolean bringToTop){
		startActivity(cls, bringToTop, null);
	}
	
	protected void startActivity (Class<?> cls , boolean bringToTop, Bundle b){
		Intent intent = new Intent(getApplicationContext(), cls);
		if(bringToTop){
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		}
		if(b != null){
			intent.putExtras(b);
		}
		startActivity(intent);
	}
	
	protected void makeToast(String str){
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}
	protected void makeToast(int resId){
		Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
	}
	
	public void showNetworkFailToast(){
		Toast.makeText(this, R.string.error_network_request_fail, Toast.LENGTH_SHORT).show();
	}
}
