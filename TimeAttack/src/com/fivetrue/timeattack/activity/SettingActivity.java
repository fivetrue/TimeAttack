package com.fivetrue.timeattack.activity;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.login.LoginManager;
import com.fivetrue.timeattack.preference.MapPreferenceManager;
import com.google.android.gms.maps.GoogleMap;
import com.kakao.APIErrorResult;
import com.kakao.AuthType;
import com.kakao.MeResponseCallback;
import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.UnlinkResponseCallback;
import com.kakao.UserProfile;
import com.kakao.exception.KakaoException;
import com.kakao.widget.LoginButton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SettingActivity extends BaseActivity {

	private ViewGroup mContentView = null;

	static public class ViewHolder {
		public ScrollView scrollView = null;
		public ViewGroup layoutMapSetting = null;
		public ViewGroup layoutMapSettingDetail = null;
		public TextView tvMapSettingType = null;
		public TextView tvMapSettingInteract = null;
		public TextView tvMapSettingRadius = null;
		public TextView tvMapSettingRadiusValue = null;
		public RadioGroup rgMapSetting = null;
		public CheckBox cbMapSettingRotation = null;
		public CheckBox cbMapSettingZoomButton = null;
		public SeekBar sbMapSettingRadius = null;

		public ViewGroup layoutAccountSetting = null;
		public ViewGroup layoutAccountSettingDetail = null;
		public LoginButton btnLoginKakao = null;
		public Button btnLogoutKakao = null;
	}

	private ViewHolder mViewHolder = new ViewHolder();
	private MapPreferenceManager mMapPref = null;

	private KakaoSessionCallback mKakaoSessionCallback = new KakaoSessionCallback();

	@Override
	public View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub

		initModels();
		mContentView = initViews(inflater);
		initViewData();

		return mContentView;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if(Session.initializeSession(this, mKakaoSessionCallback, AuthType.KAKAO_TALK)){
			// 1. 세션을 갱신 중이면, 프로그레스바를 보이거나 버튼을 숨기는 등의 액션을 취한다
			if(mViewHolder != null && mViewHolder.btnLoginKakao != null){
				mViewHolder.btnLoginKakao.setVisibility(View.GONE);
			}
		} else if (Session.getCurrentSession().isOpened()){
			// 2. 세션이 오픈된된 상태이면, 다음 activity로 이동한다.
			KakaoSessionOpend();
		}
	}

	private ViewGroup initViews(LayoutInflater inflater){
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_setting, null);

		getCustomActionBar().setBackGroundColorRes(R.color.setting_primary_color, R.color.setting_primary_dark_color);
		getCustomActionBar().setHomeIconLineColor(R.color.setting_primary_light_color);
		getCustomActionBar().setIconSelector(R.drawable.selector_setting_primary_color);

		if(getDrawerFragment() != null){
			getDrawerFragment().setBackGroundColorRes(R.color.setting_primary_color, R.color.setting_primary_dark_color);
			getDrawerFragment().setLineColor(R.color.setting_primary_light_color);
			getDrawerFragment().setIconSelector(R.drawable.selector_setting_primary_color);
		}

		//Map Settings
		mViewHolder.scrollView = (ScrollView) view.findViewById(R.id.scroll_setting);
		mViewHolder.layoutMapSetting = (ViewGroup) view.findViewById(R.id.layout_map_setting);
		mViewHolder.layoutMapSettingDetail  = (ViewGroup) view.findViewById(R.id.layout_map_setting_detail);
		mViewHolder.tvMapSettingType = (TextView)  view.findViewById(R.id.tv_setting_map_type);
		mViewHolder.tvMapSettingInteract = (TextView)  view.findViewById(R.id.tv_setting_map_interact);
		mViewHolder.tvMapSettingRadius = (TextView)  view.findViewById(R.id.tv_setting_map_radius);
		mViewHolder.tvMapSettingRadiusValue = (TextView)  view.findViewById(R.id.tv_setting_map_radius_value);
		

		mViewHolder.cbMapSettingRotation = (CheckBox) view.findViewById(R.id.cb_setting_map_rotation);
		mViewHolder.cbMapSettingZoomButton = (CheckBox) view.findViewById(R.id.cb_setting_map_zoom_button);
		mViewHolder.rgMapSetting = (RadioGroup) view.findViewById(R.id.rg_setting_map);
		mViewHolder.sbMapSettingRadius = (SeekBar) view.findViewById(R.id.sb_setting_map_radius);

		mViewHolder.layoutMapSetting.setBackground(getResources().getDrawable(R.color.setting_primary_color));
		mViewHolder.tvMapSettingType.setBackground(getResources().getDrawable(R.color.setting_primary_soft_color));
		mViewHolder.tvMapSettingInteract.setBackground(getResources().getDrawable(R.color.setting_primary_soft_color));
		mViewHolder.tvMapSettingRadius.setBackground(getResources().getDrawable(R.color.setting_primary_soft_color));
		
		//Account Settings
		mViewHolder.layoutAccountSetting = (ViewGroup) view.findViewById(R.id.layout_account_setting);
		mViewHolder.layoutAccountSettingDetail  = (ViewGroup) view.findViewById(R.id.layout_account_setting_detail);
		mViewHolder.btnLoginKakao = (LoginButton) view.findViewById(R.id.btn_kakao_login);
		mViewHolder.btnLogoutKakao = (Button) view.findViewById(R.id.btn_kakao_logout);

		mViewHolder.layoutAccountSetting.setBackground(getResources().getDrawable(R.color.setting_primary_color));

		view.setBackground(getResources().getDrawable(R.color.setting_primary_light_color));
		return view;
	}

	private void initViewData(){
		switch(mMapPref.getMapType()){
		case GoogleMap.MAP_TYPE_NORMAL :
			mViewHolder.rgMapSetting.check(R.id.rb_setting_map_normal);
			break;

		case GoogleMap.MAP_TYPE_SATELLITE :
			mViewHolder.rgMapSetting.check(R.id.rb_setting_map_satellite);
			break;

		case GoogleMap.MAP_TYPE_TERRAIN :
			mViewHolder.rgMapSetting.check(R.id.rb_setting_map_terrain);
			break;

		case GoogleMap.MAP_TYPE_HYBRID :
			mViewHolder.rgMapSetting.check(R.id.rb_setting_map_hybrid);
			break;
		}

		mViewHolder.rgMapSetting.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(group != null){
					switch(checkedId){
					case R.id.rb_setting_map_normal : 
						mMapPref.setMapType(GoogleMap.MAP_TYPE_NORMAL);
						break;
					case R.id.rb_setting_map_satellite :
						mMapPref.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
						break;

					case R.id.rb_setting_map_terrain :
						mMapPref.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
						break;

					case R.id.rb_setting_map_hybrid :
						mMapPref.setMapType(GoogleMap.MAP_TYPE_HYBRID);
						break;
					}
				}
			}
		});
		
		
		mViewHolder.sbMapSettingRadius.setProgress(mMapPref.getMapPlaceRadius());
		mViewHolder.tvMapSettingRadiusValue.setText(mMapPref.getMapPlaceRadius() + "M");
		mViewHolder.sbMapSettingRadius.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if(seekBar != null){
					mMapPref.setMapPlaceRadius(seekBar.getProgress());
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if(seekBar != null && fromUser){
					mViewHolder.tvMapSettingRadiusValue.setText(progress + "M");
				}
				
			}
		});

		mViewHolder.cbMapSettingRotation.setChecked(mMapPref.isRotateEnable());
		mViewHolder.cbMapSettingRotation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(buttonView != null){
					buttonView.setChecked(isChecked);
					mMapPref.setRotateEnable(isChecked);
				}
			}
		});

		mViewHolder.cbMapSettingZoomButton.setChecked(mMapPref.isZoomButtonEnable());
		mViewHolder.cbMapSettingZoomButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(buttonView != null){
					buttonView.setChecked(isChecked);
					mMapPref.setZoomButtonEnable(isChecked);
				}
			}
		});

		mViewHolder.btnLogoutKakao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
				new AlertDialog.Builder(SettingActivity.this)
				.setMessage(appendMessage)
				.setPositiveButton(getString(R.string.com_kakao_ok_button),
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						LoginManager.requestUnlinkKakao(SettingActivity.this, new UnlinkResponseCallback() {
							@Override
							public void onSuccess(final long userId) {
								showKakaoLoginButton(true);
							}

							@Override
							public void onSessionClosedFailure(final APIErrorResult errorResult) {
							
							}

							@Override
							public void onFailure(final APIErrorResult errorResult) {
							}
						});
						dialog.dismiss();
					}
				})
				.setNegativeButton(getString(R.string.com_kakao_cancel_button),
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
			}
		});

	}

	private void initModels(){
		mMapPref = MapPreferenceManager.newInstance(this);
	}


	@Override
	public String getActionBarTitleName() {
		// TODO Auto-generated method stub
		return getString(R.string.actionbar_setting);
	}

	@Override
	public String getActionBarSubTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewGroup getActionBarMenuView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHomeAsUp() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void requsetNetworkResultSuccess(String url, String request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClickAcitionMenuLocationSearch(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isActionBarBlending() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void outOfService(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void avaliableService(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void temporarilyServiceUnavailable(String provider, int status,
			Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void changeLocation(Location location) {
		// TODO Auto-generated method stub

	}

	private void showKakaoLoginButton(boolean isShow){
		if(mViewHolder != null && mViewHolder.btnLoginKakao != null && mViewHolder.btnLogoutKakao != null){
			mViewHolder.btnLoginKakao.setVisibility(isShow ? View.VISIBLE : View.GONE);
			mViewHolder.btnLogoutKakao.setVisibility(isShow ? View.GONE : View.VISIBLE);
		}
	}

	private void KakaoSessionOpend(){
		showKakaoLoginButton(false);
		LoginManager.requestKakaoUserInfo(SettingActivity.this, new MeResponseCallback() {
			
			@Override
			public void onSuccess(UserProfile userProfile) {
				// TODO Auto-generated method stub
				System.out.println("ojkwon : userProfile = " + userProfile.toString());
				System.out.println("ojkwon : userProfile = " + userProfile.getId());
			}
			
			@Override
			public void onSessionClosedFailure(APIErrorResult errorResult) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onNotSignedUp() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(APIErrorResult errorResult) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	private class KakaoSessionCallback implements SessionCallback {
		@Override
		public void onSessionOpened() {
			// 프로그레스바를 보이고 있었다면 중지하고 세션 오픈후 보일 페이지로 이동
			KakaoSessionOpend();
		}

		@Override
		public void onSessionClosed(final KakaoException exception) {
			// 프로그레스바를 보이고 있었다면 중지하고 세션 오픈을 못했으니 다시 로그인 버튼 노출.
			if(mViewHolder != null && mViewHolder.btnLoginKakao != null){
				mViewHolder.btnLoginKakao.setVisibility(View.VISIBLE);
			}
		}

	}
}
