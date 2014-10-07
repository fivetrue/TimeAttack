package com.fivetrue.timeattack.activity;

import com.fivetrue.location.activity.LocationActivity;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.DrawerFragment;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

abstract public class BaseActivity extends LocationActivity {
	
	private DrawerLayout mDrawerLayout = null;

	private ViewGroup mContentView = null;
	private ViewGroup mLayoutDrawer = null;
	private DrawerFragment mFragmentDrawer = null;
	
	private LayoutInflater mInflater = null;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
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
		mFragmentDrawer = (DrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
	
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

	
	
	abstract View onCreateView(LayoutInflater inflater);
	
	abstract String getActionBarTitleName();
	
	abstract String getActionBarSubTitle();
	
	abstract boolean isHomeAsUp();
	
}
