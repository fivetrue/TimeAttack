package com.fivetrue.timeattack.view.actionbar;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.view.actionbar.view.HomeButtonView;
import com.fivetrue.utils.ColorUtil;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomActionBar {

	public interface OnDrawerValueListener{

		public void onDrawerSlide(float offset) ;

		public void onDrawerOpened(View view, String openTitle, String subtitle);

		public void onDrawerClosed(View view, String openTitle, String subtitle);
	}

	public interface OnScrollListener{
		public void onScrollUp(float value);
		public void onScrollDown(float value);
		public void onScrollComplete();
	}

	private int[] PRIMARY_COLOR = {0xF4, 0x43, 0x36};//0xFFF44336
	private int[] PRIMARY_DARK_COLOR = {0x93, 0x00, 0x00};//0xFF930000
	private int COLOR_VALUE = 0xFF;
	private long ANIMATION_DURATION = 100L;

	private LayoutInflater mInfalter = null;
	private Context mContext = null;

	//Views
	private ViewGroup mContentView = null;
	private ViewGroup mActionBarLayout = null;

	private ViewGroup mActionBarHomeButtonGroup = null;
	private ViewGroup mActionBarHomeViewGroup = null;
	private HomeButtonView mHomeButton = null;
	
	private View mShadow = null;

	private TextView mTvHomeTitle = null;
	private TextView mTvHomeSubtitle = null;

	private DrawerLayout mDrawerLayout = null;
	private View mLayoutDrawer = null;
	private float mDensity = 0;


	//Models
	private boolean isHomeAsUp = false;
	private boolean isActionBarBlending = false;


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
					if(mDrawerLayout != null && mLayoutDrawer != null){
						if(mDrawerLayout.isDrawerOpen(mLayoutDrawer)){
							mDrawerLayout.closeDrawer(mLayoutDrawer);
						}else{
							((FragmentActivity)mContext).onBackPressed();
						}
					}
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

		mDensity = mContext.getResources().getDisplayMetrics().density;
	}

	private OnScrollListener mOnScrollListener = new OnScrollListener() {
		ObjectAnimator mActionBarAnimator = null;
		float mPreValue = 0;
		@Override
		public void onScrollUp(float value) {
			// TODO Auto-generated method stub
			if(mContentView == null || !isActionBarBlending){
				return;
			}

			if(mActionBarAnimator != null){
				if(mActionBarAnimator.isRunning()){
					mActionBarAnimator.cancel();
				}
			}
			float y = (mContentView.getY()) + (value);
			if(y > mPreValue && y > mContentView.getY()){
				mContentView.setY( y >= 0 ? 0 : y);
				mPreValue = y;
			}
		}

		@Override
		public void onScrollDown(float value) {
			// TODO Auto-generated method stub
			if(mContentView == null || !isActionBarBlending){
				return;
			}
			if(mActionBarAnimator != null){
				if(mActionBarAnimator.isRunning()){
					mActionBarAnimator.cancel();
				}
			}
			
			float y = (mContentView.getY()) - (value);
			if(mPreValue > y && y < mContentView.getY()){
				mContentView.setY(y >= -mContentView.getHeight() ? y : -mContentView.getHeight());
				mPreValue = y;
			}
		}

		@Override
		public void onScrollComplete() {
			// TODO Auto-generated method stub
			if(mContentView == null || !isActionBarBlending){
				return;
			}
			if(mActionBarAnimator != null){
				if(mActionBarAnimator.isRunning()){
					mActionBarAnimator.cancel();
				}
			}
			//Hide
			if(- mContentView.getHeight() / 2 >= mContentView.getY()){
				mActionBarAnimator = ObjectAnimator.ofFloat(mContentView, "translationY", mContentView.getY(), - mContentView.getHeight());
				mActionBarAnimator.setDuration(ANIMATION_DURATION);
				mActionBarAnimator.start();
			}else{
				mActionBarAnimator = ObjectAnimator.ofFloat(mContentView, "translationY", mContentView.getY(), 0);
				mActionBarAnimator.setDuration(ANIMATION_DURATION);
				mActionBarAnimator.start();
			}
		}
	};


	private OnDrawerValueListener mDrawerListener = new OnDrawerValueListener() {

		@Override
		public void onDrawerSlide(float offset) {
			// TODO Auto-generated method stub
			if(mHomeButton != null){
				mHomeButton.setAnimaitonValue(offset);
			}
			if(mActionBarLayout != null){
				mActionBarLayout.setBackgroundColor(ColorUtil.changeColor(PRIMARY_COLOR, PRIMARY_DARK_COLOR, offset * COLOR_VALUE));
			}
		}

		@Override
		public void onDrawerOpened(View view, String openTitle, String subtitle) {
			// TODO Auto-generated method stub
			setTitle(openTitle);
			setSubTitle(openTitle);
			if(mHomeButton != null){
				mHomeButton.setRevert(true);
			}
		}

		@Override
		public void onDrawerClosed(View view, String openTitle, String subtitle) {
			// TODO Auto-generated method stub
			setTitle(openTitle);
			setSubTitle(subtitle);
			if(mHomeButton != null){
				mHomeButton.setRevert(false);
			}
		}
	};


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
		if(mHomeButton != null){
			mHomeButton.setHomeAsUp(isHomeAsUp);
		}
	}

	public void setActionBarBlending(boolean isActionBarBlending) {
		this.isActionBarBlending = isActionBarBlending;
		if(mShadow != null){
			int visible  = isActionBarBlending ? View.VISIBLE : View.GONE;
			mShadow.setVisibility(visible);
		}
	}

	public DrawerLayout getDrawerLayout() {
		return mDrawerLayout;
	}

	public void setDrawerLayout(DrawerLayout drawerLayout) {
		this.mDrawerLayout = drawerLayout;
	}

	public OnDrawerValueListener getDrawerListener() {
		return mDrawerListener;
	}

	public void setDrawerListener(OnDrawerValueListener drawerListener) {
		this.mDrawerListener = drawerListener;
	}


	public OnScrollListener getOnScrollListener() {
		return mOnScrollListener;
	}

	public void setOnScrollListener(OnScrollListener ll) {
		this.mOnScrollListener = ll;
	}

	public View getLayoutDrawer() {
		return mLayoutDrawer;
	}

	public void setLayoutDrawer(View layoutDrawer) {
		this.mLayoutDrawer = layoutDrawer;
	}
}
