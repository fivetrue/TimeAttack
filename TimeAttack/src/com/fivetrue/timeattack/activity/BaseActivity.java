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
import com.fivetrue.dialog.ProgressDialog;
import com.fivetrue.location.activity.LocationActivity;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.BaseActivityManager;
import com.fivetrue.timeattack.database.NetworkResultDBManager;
import com.fivetrue.timeattack.fragment.BaseFragment;
import com.fivetrue.timeattack.fragment.DrawerFragment;
import com.fivetrue.timeattack.view.actionbar.CustomActionBar;
import com.fivetrue.utils.Logger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
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
	protected DrawerFragment mFragmentDrawer = null;
	private NetworkResultDBManager mNetworkResultDBM = null;

	private CustomActionBar mCustomActionBar = null;

	private LayoutInflater mInflater = null;

	private ProgressDialog mProgressDialog = null;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_base);
		mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		initViews(mInflater);
		initActionBar(mInflater);
		initActionBarSetting(mInflater);
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
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_alpha_out);
	}
	

	/**
	 * 기본적인 View를 초기화함.
	 */
	private void initViews(LayoutInflater inflater){

		mCustomActionBar = new CustomActionBar(this);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mContentView = (ViewGroup) findViewById(R.id.layout_main_frame);
		mLayoutDrawer = (ViewGroup) findViewById(R.id.layout_drawer);

		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setCanceledOnTouchOutside(false);
		mProgressDialog.setCancelable(false);

		mFragmentDrawer = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
		mFragmentDrawer.setDrawerLayout(mDrawerLayout, mLayoutDrawer);

		View contentView = onCreateView(inflater);
		if(contentView != null){
			mContentView.addView(contentView);
		}
		mDrawerLayout.setDrawerListener(onDrawerListener);
	}

	/**
	 * Actionbar Setting Area Start
	 */

	public void initActionBar(LayoutInflater inflater){

		if(isActionBarBlending()){
			ViewGroup achor =  (ViewGroup) findViewById(R.id.layout_main);
			achor.addView(mCustomActionBar.getContentView());
		}else{
			ViewGroup achor =  (ViewGroup) findViewById(R.id.layout_content);
			achor.addView(mCustomActionBar.getContentView(), 0);

			ViewGroup shadowAchor =  (ViewGroup) findViewById(R.id.layout_main);
			View shadow = new View(this);
			shadow.setBackground(getResources().getDrawable(R.drawable.shadow_pattern));
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, (int)getResources().getDimension(R.dimen.shadow_height));
			shadowAchor.addView(shadow, params);
		}
	}


	public void initActionBarSetting(LayoutInflater inflater){

		if(mCustomActionBar == null)
			return ;
		mCustomActionBar.setHomeAsUp(isHomeAsUp());
		mCustomActionBar.setActionBarBlending(isActionBarBlending());
		mCustomActionBar.setTitle(getActionBarTitleName());
		mCustomActionBar.setSubTitle(getActionBarSubTitle());
		mCustomActionBar.setHomeMenuViews(getActionBarMenuView(inflater));
		mCustomActionBar.setDrawerLayout(mDrawerLayout);
		mCustomActionBar.setLayoutDrawer(mLayoutDrawer);
	}


	//	private void initActionBarButtons(Menu menu){
	//		if(menu != null){
	//			for(int i = 0 ; i < menu.size() ; i ++){
	//				View view = menu.getItem(i).getActionView();
	//				if(view != null){
	//					view.setOnClickListener(onClickActionBarItem);
	//				}
	//			}
	//		}
	//	}

	DrawerListener onDrawerListener = new DrawerListener() {

		@Override
		public void onDrawerStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			// TODO Auto-generated method stub
			if(mCustomActionBar != null){
				mCustomActionBar.getDrawerListener().onDrawerSlide(arg1);
			}

			if(mFragmentDrawer != null){
				mFragmentDrawer.slideDrawer(arg1);
			}
		}

		@Override
		public void onDrawerOpened(View arg0) {
			// TODO Auto-generated method stub
			if(mCustomActionBar != null){
				mCustomActionBar.getDrawerListener().onDrawerOpened(arg0, getString(R.string.app_name), null);
			}
		}


		@Override
		public void onDrawerClosed(View arg0) {
			// TODO Auto-generated method stub
			if(mCustomActionBar != null){
				mCustomActionBar.getDrawerListener().onDrawerClosed(arg0, getActionBarTitleName(), getActionBarSubTitle());
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
		//		if(getActionBarMenuResource() != INVALID_VALUE){
		//			MenuInflater inflater = getMenuInflater();
		//			inflater.inflate(getActionBarMenuResource(), menu);
		//			initActionBarButtons(menu);
		//		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

	public CustomActionBar getCustomActionBar(){
		return mCustomActionBar;
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

	public BaseFragment createFragment(Class<?> fragmentClass, String tag, Bundle argument){
		return createFragment(INVALID_VALUE, fragmentClass, tag, INVALID_VALUE, argument , INVALID_VALUE, INVALID_VALUE);
	}

	public BaseFragment createFragment(int containLayoutId, Class<?> fragmentClass, String tag, Bundle argument){
		return createFragment(containLayoutId, fragmentClass, tag, INVALID_VALUE, argument , INVALID_VALUE, INVALID_VALUE);
	}

	public BaseFragment createFragment(Class<?> fragmentClass, String tag, int transit, Bundle argument){
		return createFragment(INVALID_VALUE, fragmentClass, tag, transit, argument , INVALID_VALUE, INVALID_VALUE);
	}

	public BaseFragment createFragment(Class<?> fragmentClass, String tag, int transit, Bundle argument, int startAni, int endAni){
		return createFragment(INVALID_VALUE, fragmentClass, tag, transit, argument , startAni, endAni);
	}

	public BaseFragment createFragment(int containLayoutId, Class<?> fragmentClass
			, String tag , int transit, Bundle argument, int aniStart, int aniEnd){

		if(containLayoutId == INVALID_VALUE){
			containLayoutId  = R.id.layout_main_frame;
		}

		if(transit == INVALID_VALUE){
			transit = FragmentTransaction.TRANSIT_NONE;
		}
		BaseFragment f = null;
		try {
			f = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
			FragmentTransaction trans =  getSupportFragmentManager().beginTransaction();
			if(f != null){
				trans.show(f);
			}else{
				f = (BaseFragment) fragmentClass.newInstance();
				f.setArguments(argument);
				f.setCustomActionBar(getCustomActionBar());
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


	abstract public View onCreateView(LayoutInflater inflater);

	abstract public String getActionBarTitleName();

	abstract public String getActionBarSubTitle();

	abstract public ViewGroup getActionBarMenuView(LayoutInflater inflater);

	abstract public boolean isHomeAsUp();

	abstract public void requsetNetworkResultSuccess();

	abstract public void onClickAcitionMenuLocationSearch(View view);

	abstract public boolean isActionBarBlending();
	
	protected OnClickListener onClickActionBarItem = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){

			case R.id.action_item_setting :
			{
				//설정.
				BaseActivityManager.startActivity(BaseActivity.this, SettingActivity.class);
				return ;
			}


			case R.id.action_item_place :
			{
				//위치 검색..
				Toast.makeText(getApplicationContext(), "action_place", Toast.LENGTH_SHORT).show();
				return ;
			}

			case R.id.action_item_searching :
			{
				//위치 검색.
				BaseActivityManager.startActivity(BaseActivity.this, SearchLocationActivity.class);
				break;
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
				BaseActivityManager.startActivity(BaseActivity.this, MapActivity.class);
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
		mNetworkResultDBM.deleteNetworkResult(url);
	}

	protected void log(String msg){
		Logger.e(getPackageName(), getClass().getName() + " = " + msg);
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

	public void showProgressDialog(){
		if(mProgressDialog != null){
			if(!mProgressDialog.isShowing()){
				mProgressDialog.show();
			}
		}
	}

	public void dismissProgressDialog(){
		if(mProgressDialog != null){
			if(mProgressDialog.isShowing()){
				mProgressDialog.dismiss();
			}
		}
	}

	public DrawerFragment getDrawerFragment(){
		return mFragmentDrawer;
	}

}
