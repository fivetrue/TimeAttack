package com.fivetrue.timeattack.fragment;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.utils.ImageUtils;
import com.fivetrue.utils.ColorUtil;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class DrawerFragment extends BaseFragment {
	
	public interface OnDrawerMenuClickListener{
		public void onMenuClick(ViewGroup parent, ViewGroup itemLayout, TextView itemText);
	}
	
	private long[] PRIMARY_COLOR = new long[3];
	private long[] PRIMARY_DARK_COLOR = new long[3];
	private int COLOR_VALUE = 0xFF;
	
	private OnDrawerMenuClickListener mDrawerMenuClickListener = null;
	private ViewHolder mViewHolder = new ViewHolder();
	
	private int mPrimaryColorRes = R.color.main_primary_color;
	private int mPrimaryDarkColorRes = R.color.main_primary_dark_color;
	private int mIconSelector = R.drawable.selector_main_primary_color;
	private int mLineColor = R.color.main_primary_light_color;
	private int mColorSubstringIndex = 3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mViewHolder.layout = inflater.inflate(R.layout.fragment_drawer, null);
		
		initView();
		initData();
		
		return mViewHolder.layout;
	}
	
	private void initView(){
		
		
		mViewHolder.layoutUserInfo = (ViewGroup) mViewHolder.layout.findViewById(R.id.layout_drawer_user_info);
		mViewHolder.ivUserProfile = (ImageView) mViewHolder.layout.findViewById(R.id.iv_drawer_user);
		mViewHolder.tvUserName = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_user_name);
		mViewHolder.tvUserInfo = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_user_info);
		
		mViewHolder.layoutUserInfo.setBackground(getResources().getDrawable(mPrimaryColorRes));
		mViewHolder.tvUserName.setTextColor(getResources().getColor(mLineColor));
		mViewHolder.tvUserInfo.setTextColor(getResources().getColor(mLineColor));
		mViewHolder.layoutUserInfo.findViewById(R.id.line).setBackground(getResources().getDrawable(mPrimaryDarkColorRes));
		
		mViewHolder.layoutHome = (ViewGroup) mViewHolder.layout.findViewById(R.id.layout_drawer_home);
		mViewHolder.layoutDirection = (ViewGroup) mViewHolder.layout.findViewById(R.id.layout_drawer_direction);
		mViewHolder.layoutNearBy = (ViewGroup) mViewHolder.layout.findViewById(R.id.layout_drawer_near_by);
		
		mViewHolder.layoutHome.setBackground(getResources().getDrawable(mIconSelector));
		mViewHolder.layoutDirection.setBackground(getResources().getDrawable(mIconSelector));
		mViewHolder.layoutNearBy.setBackground(getResources().getDrawable(mIconSelector));
		mViewHolder.layoutHome.findViewById(R.id.line).setBackground(getResources().getDrawable(mLineColor));
		mViewHolder.layoutDirection.findViewById(R.id.line).setBackground(getResources().getDrawable(mLineColor));
		mViewHolder.layoutNearBy.findViewById(R.id.line).setBackground(getResources().getDrawable(mLineColor));
		
		mViewHolder.tvHome = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_home);
		mViewHolder.tvDirection = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_direction);
		mViewHolder.tvNearBy = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_near_by);
		
		mViewHolder.tvHome.setTextColor(getResources().getColor(mPrimaryColorRes));
		mViewHolder.tvDirection.setTextColor(getResources().getColor(mPrimaryColorRes));
		mViewHolder.tvNearBy.setTextColor(getResources().getColor(mPrimaryColorRes));
		
		mViewHolder.layoutHome.setOnClickListener(mOnClickListener);
		mViewHolder.layoutDirection.setOnClickListener(mOnClickListener);
		mViewHolder.layoutNearBy.setOnClickListener(mOnClickListener);
		
	}
	
	private void initData(){
		
		String primary =  getResources().getString(mPrimaryColorRes).substring(mColorSubstringIndex);
		String primaryDark =  getResources().getString(mPrimaryDarkColorRes).substring(mColorSubstringIndex);
		for(int i = 0 ; i < PRIMARY_COLOR.length ; i++){
			String primaryColor = primary.substring(i * 2, (i + 1) * 2);
			String primaryDarkColor = primaryDark.substring(i * 2, (i + 1) * 2);
			PRIMARY_COLOR[i] = Long.valueOf(primaryColor, 16);
			PRIMARY_DARK_COLOR[i] = Long.valueOf(primaryDarkColor, 16);
		}
		
		mViewHolder.ivUserProfile.setImageBitmap(ImageUtils.getInstance(getActivity()).circleBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map)));
	}
	
	
	public void setOnClickDrawerMenuClickListener(OnDrawerMenuClickListener listener){
		mDrawerMenuClickListener = listener;
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mDrawerMenuClickListener == null)
				return ;
			
			switch(v.getId()){
			
			case R.id.layout_drawer_home :
				mDrawerMenuClickListener.onMenuClick((ViewGroup)mViewHolder.layout, mViewHolder.layoutHome, mViewHolder.tvHome);
				break;
				
			case R.id.layout_drawer_near_by :
				mDrawerMenuClickListener.onMenuClick((ViewGroup)mViewHolder.layout, mViewHolder.layoutNearBy, mViewHolder.tvNearBy);
				break;
				
			case R.id.layout_drawer_direction :
				mDrawerMenuClickListener.onMenuClick((ViewGroup)mViewHolder.layout, mViewHolder.layoutDirection, mViewHolder.tvDirection);
				break;
			}
		}
	};
	
	public void setBackGroundColorRes(int PrimaryColorRes, int PrimaryDarkColorRes) {
		this.mPrimaryColorRes = PrimaryColorRes;
		this.mPrimaryDarkColorRes = PrimaryDarkColorRes;
		initView();
		initData();
	}
	
	public void setIconSelector(int selectorRes){
		mIconSelector = selectorRes;
		initView();
	}
	
	public void setLineColor(int res){
		mLineColor = res;
		initView();
	}
	
	public void slideDrawer(float offset){
		if(mViewHolder.layoutUserInfo != null){
			mViewHolder.layoutUserInfo.setBackgroundColor(ColorUtil.changeColor(PRIMARY_COLOR, PRIMARY_DARK_COLOR, offset * COLOR_VALUE));
		}
	}
	
	private class ViewHolder{
		public View layout;
		
		public ViewGroup layoutUserInfo;
		public TextView tvUserName;
		public TextView tvUserInfo;
		public ImageView ivUserProfile;
		
		public ViewGroup layoutHome;
		public ViewGroup layoutNearBy;
		public ViewGroup layoutDirection;
		public TextView tvHome;
		public TextView tvNearBy;
		public TextView tvDirection;
	}
}
