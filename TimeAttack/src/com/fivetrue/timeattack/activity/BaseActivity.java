package com.fivetrue.timeattack.activity;

import com.api.common.IRequestResult;
import com.fivetrue.location.activity.LocationActivity;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.database.NetworkResultDBManager;
import com.fivetrue.timeattack.fragment.DrawerFragment;
import com.fivetrue.timeattack.fragment.DrawerFragment.OnDrawerMenuClickListener;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

abstract public class BaseActivity extends LocationActivity implements IRequestResult{
	
	private DrawerLayout mDrawerLayout = null;
	private ActionBarDrawerToggle mDrawerToggle = null;

	private ViewGroup mContentView = null;
	private ViewGroup mLayoutDrawer = null;
	static protected DrawerFragment mFragmentDrawer = null;
	private NetworkResultDBManager mNetworkResultDBM = null;
	
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
	
	public void initActionBarSetting(){
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(new ColorDrawable(0x00000000));
		getActionBar().setTitle(getActionBarTitleName());
		getActionBar().setSubtitle(getActionBarSubTitle());
	}
	
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
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, isHomeAsUp() ? R.drawable.ic_action_back : R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){

			@Override
			public void onDrawerStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onDrawerSlide(View arg0, float arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onDrawerOpened(View arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onDrawerClosed(View arg0) {
				// TODO Auto-generated method stub
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	
	private void initModels(){
		mNetworkResultDBM = new NetworkResultDBManager(getApplicationContext());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(getActionBarMenuResource(), menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		
		if(mDrawerToggle.onOptionsItemSelected(item)){
	        return onSelectedActionBarItem(item);
	    }
		return super.onOptionsItemSelected(item);
	}
	
	protected boolean onSelectedActionBarItem(MenuItem item){
		int id = item.getItemId();
		
		switch(id){
		case android.R.id.home :
			if(isHomeAsUp()){
				NavUtils.navigateUpFromSameTask(this);
			}else{
//				if(mDrawerLayout.isDrawerOpen(mLayoutDrawer)){
//					mDrawerLayout.closeDrawer(mLayoutDrawer);
//				}else{
//					mDrawerLayout.openDrawer(mLayoutDrawer);
//				}
			}
			return true;
			
		case R.id.action_settings :
			
			return true;
		}
		
		return false;
	}
	
	protected void onPostCreate(Bundle savedInstanceState){
	    super.onPostCreate(savedInstanceState);
	    mDrawerToggle.syncState();
	}
	 
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    mDrawerToggle.onConfigurationChanged(newConfig);
	}
	 
	
	
	public Fragment createFragment(Class<?> fragmentClass, String tag){
		Fragment f = null;
		try {
			f = (Fragment) fragmentClass.newInstance();
			FragmentTransaction trans =  getSupportFragmentManager().beginTransaction();
			trans.add(R.id.layout_main_frame, f, tag);
			trans.commit();
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
	
	public Fragment createFragment(int containLayoutId, Class<?> fragmentClass, String tag){
		
		Fragment f = null;
		try {
			f = (Fragment) fragmentClass.newInstance();
			FragmentTransaction trans =  getSupportFragmentManager().beginTransaction();
			trans.add(containLayoutId, f, tag);
			trans.commit();
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
	
	public void onSuccessRequest(String url, org.json.JSONObject request) {
		mNetworkResultDBM.insertNetworkResult(url, request);
	};
	
	@Override
	public void onFailRequest(String url) {
		// TODO Auto-generated method stub
	}
	
}
