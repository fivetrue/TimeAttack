package com.fivetrue.timeattack.fragment;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.MainActivity;
import com.fivetrue.timeattack.activity.MapActivity;
import com.fivetrue.timeattack.activity.SearchLocationActivity;
import com.fivetrue.timeattack.utils.ImageUtils;
import com.fivetrue.utils.ColorUtil;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class DrawerFragment extends BaseFragment {

	private long[] PRIMARY_COLOR = new long[3];
	private long[] PRIMARY_DARK_COLOR = new long[3];
	private int COLOR_VALUE = 0xFF;

	private ViewHolder mViewHolder = new ViewHolder();

	private int mPrimaryColorRes = R.color.main_primary_color;
	private int mPrimaryDarkColorRes = R.color.main_primary_dark_color;
	private int mIconSelector = R.drawable.selector_main_primary_color;
	private int mLineColor = R.color.main_primary_light_color;
	private int mColorSubstringIndex = 3;
	private DrawerLayout mDrawerLayout = null;
	private ViewGroup mLayoutDrawer = null;

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

		mViewHolder.layoutDefaultItems = (ViewGroup) mViewHolder.layout.findViewById(R.id.layout_drawer_default_items);

		mViewHolder.tvHome = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_home);
		mViewHolder.tvMap = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_map);
		mViewHolder.tvSearch = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_search);
		mViewHolder.tvSetting = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_setting);

//		mViewHolder.layoutDefaultItems.findViewById(R.id.line).setBackground(getResources().getDrawable(mLineColor));

		mViewHolder.tvHome.setOnClickListener(mOnClickListener);
		mViewHolder.tvMap.setOnClickListener(mOnClickListener);
		mViewHolder.tvSearch.setOnClickListener(mOnClickListener);
		mViewHolder.tvSetting.setOnClickListener(mOnClickListener);
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

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(getActivity() == null)
				return ;

			switch(v.getId()){

			case R.id.tv_drawer_home :
				if(!(getActivity() instanceof MainActivity)){
					Intent i = new Intent(getActivity(), MainActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
				break;

			case R.id.tv_drawer_map :
				if(!(getActivity() instanceof MapActivity)){
					Intent i = new Intent(getActivity(), MapActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
				break;

			case R.id.tv_drawer_search :
				if(!(getActivity() instanceof SearchLocationActivity)){
					Intent i = new Intent(getActivity(), SearchLocationActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
				break;
			case R.id.tv_drawer_setting :
				break;
			}
			
			if(mDrawerLayout != null){
				if(mDrawerLayout != null && mLayoutDrawer != null){
					mDrawerLayout.closeDrawer(mLayoutDrawer);
				}
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
	
	public void setDrawerLayout(DrawerLayout drawer, ViewGroup layout){
		mDrawerLayout = drawer;
		mLayoutDrawer = layout;
	}

	private class ViewHolder{
		public View layout;

		public ViewGroup layoutUserInfo;
		public TextView tvUserName;
		public TextView tvUserInfo;
		public ImageView ivUserProfile;

		public ViewGroup layoutDefaultItems;
		public TextView tvHome;
		public TextView tvMap;
		public TextView tvSearch;
		public TextView tvSetting;
	}
}
