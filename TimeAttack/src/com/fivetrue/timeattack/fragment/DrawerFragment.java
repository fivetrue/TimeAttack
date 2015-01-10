package com.fivetrue.timeattack.fragment;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.fivetrue.network.VolleyInstance;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.BaseActivity;
import com.fivetrue.timeattack.activity.MainActivity;
import com.fivetrue.timeattack.activity.MapActivity;
import com.fivetrue.timeattack.activity.SearchLocationActivity;
import com.fivetrue.timeattack.activity.SettingActivity;
import com.fivetrue.timeattack.activity.manager.BaseActivityManager;
import com.fivetrue.timeattack.constants.ActionConstants;
import com.fivetrue.timeattack.ga.GoogleAnalyticsConstants;
import com.fivetrue.timeattack.ga.GoogleAnalyticsConstants.ACTION;
import com.fivetrue.timeattack.login.LoginManager;
import com.fivetrue.timeattack.preference.UserPreferenceManager;
import com.fivetrue.timeattack.utils.ImageUtils;
import com.fivetrue.utils.ColorUtil;
import com.kakao.UserProfile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
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
	
	UserPreferenceManager mUserPref = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mViewHolder.layout = inflater.inflate(R.layout.fragment_drawer, null);

		initView();
		initData();

		IntentFilter filter = new IntentFilter();
		filter.addAction(ActionConstants.ACTION_KAKAO_LOGIN_SUCCESS);
		filter.addAction(ActionConstants.ACTION_KAKAO_UNLINK_SUCCESS);
		getActivity().registerReceiver(mLoginInfoReceiver, filter);
		return mViewHolder.layout;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mLoginInfoReceiver != null){
			getActivity().unregisterReceiver(mLoginInfoReceiver);
		}
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

		mViewHolder.layoutUserInfo.setOnClickListener(mOnClickListener);
		mViewHolder.tvHome.setOnClickListener(mOnClickListener);
		mViewHolder.tvMap.setOnClickListener(mOnClickListener);
		mViewHolder.tvSearch.setOnClickListener(mOnClickListener);
		mViewHolder.tvSetting.setOnClickListener(mOnClickListener);
	}

	private void initData(){
		mUserPref = UserPreferenceManager.newInstance(getActivity());
		String primary =  getResources().getString(mPrimaryColorRes).substring(mColorSubstringIndex);
		String primaryDark =  getResources().getString(mPrimaryDarkColorRes).substring(mColorSubstringIndex);
		for(int i = 0 ; i < PRIMARY_COLOR.length ; i++){
			String primaryColor = primary.substring(i * 2, (i + 1) * 2);
			String primaryDarkColor = primaryDark.substring(i * 2, (i + 1) * 2);
			PRIMARY_COLOR[i] = Long.valueOf(primaryColor, 16);
			PRIMARY_DARK_COLOR[i] = Long.valueOf(primaryDarkColor, 16);
		}
		
		setProfileText(mUserPref.getUserName(), "");
		setProfileImage(mUserPref.getUserImageURL());
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
					BaseActivityManager.startActivity(getActivity(), i);
					((BaseActivity)getActivity()).mGaManager.sendEven(getClass().getSimpleName(), ACTION.TAG, ACTION.GO_HOME);
				}
				break;

			case R.id.tv_drawer_map :
				if(!(getActivity() instanceof MapActivity)){
					Intent i = new Intent(getActivity(), MapActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					BaseActivityManager.startActivity(getActivity(), i);
					((BaseActivity)getActivity()).mGaManager.sendEven(getClass().getSimpleName(), ACTION.TAG, ACTION.GO_MAP);
				}
				break;

			case R.id.tv_drawer_search :
				if(!(getActivity() instanceof SearchLocationActivity)){
					Intent i = new Intent(getActivity(), SearchLocationActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					BaseActivityManager.startActivity(getActivity(), i);
					((BaseActivity)getActivity()).mGaManager.sendEven(getClass().getSimpleName(), ACTION.TAG, ACTION.GO_SEARCH);
				}
				break;
				
			case R.id.layout_drawer_user_info: 
			case R.id.tv_drawer_setting :
				if(!(getActivity() instanceof SettingActivity)){
					Intent i = new Intent(getActivity(), SettingActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					BaseActivityManager.startActivity(getActivity(), i);
					((BaseActivity)getActivity()).mGaManager.sendEven(getClass().getSimpleName(), ACTION.TAG, ACTION.GO_SEARCH);
				}
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

	private void setUserProfile(final UserProfile profile){
		if(profile != null){
			setProfileText(profile.getNickname(), "");
			setProfileImage(profile.getProfileImagePath());
		}
	}
	
	private void setProfileImage(final String url){
		if(mViewHolder != null && mViewHolder.ivUserProfile != null){
			if(!TextUtils.isEmpty(url)){
				Bitmap bm = ImageUtils.getInstance(getActivity()).getImageBitmap(url);
				if(bm != null){
					mViewHolder.ivUserProfile.setImageBitmap(bm);
				}else{
					VolleyInstance.getImageLoader().get(url, new ImageListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onResponse(ImageContainer response, boolean isImmediate) {
							// TODO Auto-generated method stub
							if(response != null && response.getBitmap() != null){
								ImageUtils.getInstance(getActivity()).saveBitmap(response.getBitmap(), url);
								mViewHolder.ivUserProfile.setImageBitmap(response.getBitmap());
							}
						}
					});
				}
			}else{
				mViewHolder.ivUserProfile.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.map));
			}
		}
	}
	
	private void setProfileText(String name, String info){
		if(mViewHolder != null && mViewHolder.tvUserName != null && mViewHolder.tvUserInfo != null){
			if(!TextUtils.isEmpty(name) && info != null){
				mViewHolder.tvUserName.setText(name);
				mViewHolder.tvUserInfo.setText(info);
			}else{
				mViewHolder.tvUserName.setText(R.string.drawer_login);
				mViewHolder.tvUserInfo.setText("");
			}
		}
	}

	private BroadcastReceiver mLoginInfoReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(context != null && intent != null){
				String action = intent.getAction();
				if(!TextUtils.isEmpty(action)){
					if(action.equals(ActionConstants.ACTION_KAKAO_LOGIN_SUCCESS)){
						setUserProfile((UserProfile)intent.getParcelableExtra(LoginManager.KAKAO_USER_DATA));
					}else if(action.equals(ActionConstants.ACTION_KAKAO_UNLINK_SUCCESS)){
						setProfileImage(null);
						setProfileText(null, null);
					}
				}
			}
		}
	};

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
