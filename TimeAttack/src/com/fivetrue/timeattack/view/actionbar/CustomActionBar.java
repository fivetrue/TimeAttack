package com.fivetrue.timeattack.view.actionbar;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.BaseActivity;
import com.fivetrue.timeattack.view.HomeButtonView;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomActionBar {
	
	private int[] PRIMARY_COLOR = {0xF4, 0x43, 0x36};//0xFFF44336
	private int[] PRIMARY_DARK_COLOR = {0xB7, 0x1C, 0x1C};//0xFFB71C1C; new 930000
	private int COLOR_MOVE_VALUE = 21;
	private int COLOR_VALUE = 0x12;
	
	private LayoutInflater mInfalter = null;
	private Context mContext = null;
	
	//Views
	private ViewGroup mContentView = null;
	private ViewGroup mActionBarLayout = null;
	
	private ViewGroup mActionBarHomeButtonGroup = null;
	private ViewGroup mActionBarHomeViewGroup = null;
	private HomeButtonView mHomeButton = null;

	private TextView mTvHomeTitle = null;
	private TextView mTvHomeSubtitle = null;
	private View mShadow = null;
	
	private DrawerLayout mDrawerLayout = null;
	private View mLayoutDrawer = null;
	
	
	//Models
	private boolean isHomeAsUp = false;
	

	public CustomActionBar(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mInfalter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContentView = (ViewGroup) mInfalter.inflate(R.layout.layout_action_bar, null);
		initView();
	}
	
	public CustomActionBar(Context context, DrawerLayout drawerLayout, View layoutDrawer) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mInfalter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContentView = (ViewGroup) mInfalter.inflate(R.layout.layout_action_bar, null);
		
		mDrawerLayout = drawerLayout;
		mLayoutDrawer = layoutDrawer;
		initView();
	}
	
	private void initView(){
		mActionBarLayout = (ViewGroup) mContentView.findViewById(R.id.layout_actionbar);
		mActionBarHomeViewGroup = (ViewGroup) mContentView.findViewById(R.id.layout_actionbar_home);
		mActionBarHomeButtonGroup = (ViewGroup) mContentView.findViewById(R.id.layout_actionbar_home_group);
		mHomeButton = (HomeButtonView) mContentView.findViewById(R.id.home_button) ;
		mTvHomeTitle = (TextView) mContentView.findViewById(R.id.tv_actionbar_home_title);
		mTvHomeSubtitle = (TextView) mContentView.findViewById(R.id.tv_actionbar_home_subtitle);
		mShadow = mContentView.findViewById(R.id.actionbar_shadow);
		
		mActionBarHomeButtonGroup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isHomeAsUp){
					((BaseActivity)mContext).onBackPressed();
				}else{
					if(mDrawerLayout != null && mLayoutDrawer != null){
						if(mDrawerLayout.isDrawerOpen(mLayoutDrawer)){
							mDrawerLayout.closeDrawer(mLayoutDrawer);
						}else{
							mDrawerLayout.openDrawer(mLayoutDrawer);
						}
					}
				}
			}
		});
	}
	
	private DrawerListener mDrawerListener = new DrawerListener() {
		
		@Override
		public void onDrawerStateChanged(int arg0) {
			// TODO Auto-generated method stub
		
		}
		
		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			// TODO Auto-generated method stub
			if(mHomeButton != null){
				mHomeButton.setAnimaitonValue(arg1);
			}
			if(mActionBarLayout != null){
				float val = arg1 * COLOR_MOVE_VALUE;
				changeColor(mActionBarLayout, PRIMARY_COLOR, PRIMARY_DARK_COLOR, val * COLOR_VALUE);
			}
		}
		
		@Override
		public void onDrawerOpened(View arg0) {
			// TODO Auto-generated method stub
			setTitle(R.string.app_name);
			setSubTitle(null);
			if(mHomeButton != null){
				mHomeButton.setRevert(true);
			}
		}
		
		@Override
		public void onDrawerClosed(View arg0) {
			// TODO Auto-generated method stub
			
			setTitle(((BaseActivity)mContext).getActionBarTitleName());
			setSubTitle(null);
			if(mHomeButton != null){
				mHomeButton.setRevert(false);
			}
		}
	};
	
	private void changeColor(View view, final int[] arrColorFrom, final int[] arrColorTo, float value){
		if(view == null || arrColorFrom == null || arrColorTo == null
				|| arrColorFrom.length <= 0 || arrColorTo.length <= 0 )
			return;
			
		int color[] = new int[arrColorFrom.length];
		for(int i = 0 ; i < arrColorFrom.length ; i ++){
			
			float val = arrColorFrom[i] - value;
			
			if(arrColorTo[i] <= val){
				color[i] = (int)val;
			}
			
		}
		String backround = new String();
		backround += Integer.toHexString(0xFF);
		for(int i = 0 ; i < color.length ; i++){
			int to = arrColorTo[i] > 15 ? arrColorTo[i] : 0x10;
			backround += Integer.toHexString(color[i] >= to ? color[i] : to);
		}
		long setColor = Long.valueOf(backround, 16);
		
		view.setBackgroundColor((int)setColor);
	}
	
	public ViewGroup getContentView(){
		return mContentView;
	}
	
	public HomeButtonView getHomeButtonView(){
		return mHomeButton;
	}
	
	public Context getContext(){
		return mContext;
	}

	public boolean isHomeAsUp() {
		return isHomeAsUp;
	}
	
	public void setTitle(String title){
		if(mTvHomeTitle != null){
			if(TextUtils.isEmpty(title)){
				mTvHomeTitle.setVisibility(View.GONE);
			}else{
				mTvHomeTitle.setText(title);
			}
		}
	}
	
	public void setTitle(int titleRes){
		if(mTvHomeTitle != null){
			String title = mContext.getResources().getString(titleRes);
			if(TextUtils.isEmpty(title)){
				mTvHomeTitle.setVisibility(View.GONE);
			}else{
				mTvHomeTitle.setText(title);
			}
		}
	}
	
	public void setSubTitle(String title){
		if(mTvHomeTitle != null){
			if(TextUtils.isEmpty(title)){
				mTvHomeSubtitle.setVisibility(View.GONE);
			}else{
				mTvHomeSubtitle.setText(title);
			}
		}
	}
	
	public void setSubTitle(int titleRes){
		if(mTvHomeTitle != null){
			String title = mContext.getResources().getString(titleRes);
			if(TextUtils.isEmpty(title)){
				mTvHomeSubtitle.setVisibility(View.GONE);
			}else{
				mTvHomeSubtitle.setText(title);
			}
		}
	}

	public void setHomeAsUp(boolean isHomeAsUp) {
		this.isHomeAsUp = isHomeAsUp;
		
		int shadowVisible = isHomeAsUp ? View.GONE : View.VISIBLE;
		if(mShadow != null){
			mShadow.setVisibility(shadowVisible);
		}
		
		if(mHomeButton != null){
			mHomeButton.setHomeAsUp(isHomeAsUp);
		}
	}

	public DrawerLayout getDrawerLayout() {
		return mDrawerLayout;
	}

	public void setDrawerLayout(DrawerLayout drawerLayout) {
		this.mDrawerLayout = drawerLayout;
	}

	public DrawerListener getDrawerListener() {
		return mDrawerListener;
	}

	public void setDrawerListener(DrawerListener drawerListener) {
		this.mDrawerListener = drawerListener;
	}

	public View getLayoutDrawer() {
		return mLayoutDrawer;
	}

	public void setLayoutDrawer(View layoutDrawer) {
		this.mLayoutDrawer = layoutDrawer;
	}
}
