package com.fivetrue.timeattack.activity;

import com.fivetrue.location.activity.LocationActivity;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.DrawerFragment;
import com.fivetrue.timeattack.fragment.DrawerFragment.OnDrawerMenuClickListener;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

abstract public class BaseActivity extends LocationActivity {
	
	private DrawerLayout mDrawerLayout = null;

	private ViewGroup mContentView = null;
	private ViewGroup mLayoutDrawer = null;
	static protected DrawerFragment mFragmentDrawer = null;
	
	private LayoutInflater mInflater = null;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_base);
		initViews();
		initActionBarSetting();
	}
	
	public void initActionBarSetting(){
		
		getActionBar().setDisplayHomeAsUpEnabled(isHomeAsUp());
		if(isHomeAsUp()){
			
		}else{
			getActionBar().setDisplayShowHomeEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
		}
		
		getActionBar().setTitle(getActionBarTitleName());
		getActionBar().setSubtitle(getActionBarSubTitle());
	}
	
	private void initViews(){
		mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mContentView = (ViewGroup) findViewById(R.id.layout_main_frame);
		mLayoutDrawer = (ViewGroup) findViewById(R.id.layout_drawer);
		if(mFragmentDrawer == null){
			mFragmentDrawer = (DrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
			mFragmentDrawer.setOnClickDrawerMenuClickListener(mDrawerMenuClickListener);
		}
		View contentView = onCreateView(mInflater);
		if(contentView != null){
			mContentView.addView(contentView);
		}
		
		mDrawerLayout.setDrawerListener(mDrawerListener);
	}
	
	private DrawerListener mDrawerListener = new DrawerListener() {
		
		@Override
		public void onDrawerStateChanged(int arg0) {
			// TODO Auto-generated method stub
            switch (arg0) {
            case DrawerLayout.STATE_IDLE:
                break;
            case DrawerLayout.STATE_DRAGGING:
                break;
            case DrawerLayout.STATE_SETTLING:
                break;
            default:
            }
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else if(id == android.R.id.home){
			
			if(isHomeAsUp()){
				NavUtils.navigateUpFromSameTask(this);
			}else{
				if(mDrawerLayout.isDrawerOpen(mLayoutDrawer)){
					mDrawerLayout.closeDrawer(mLayoutDrawer);
				}else{
					mDrawerLayout.openDrawer(mLayoutDrawer);
				}
			}
			return true;
		};
		return super.onOptionsItemSelected(item);
	}
	
	
	public void createFragment(Fragment fragment, String tag){
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.add(R.id.layout_main_frame, fragment, tag);
		trans.commit();
	}
	
	public void createFragment(int containLayoutId, Fragment fragment, String tag){
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.add(containLayoutId, fragment, tag);
		trans.commit();
	};
	
	
	abstract View onCreateView(LayoutInflater inflater);
	
	abstract String getActionBarTitleName();
	
	abstract String getActionBarSubTitle();
	
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
	
}
