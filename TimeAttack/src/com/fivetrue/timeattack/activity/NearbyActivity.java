package com.fivetrue.timeattack.activity;


import com.api.common.BaseResponseListener;
import com.api.google.place.PlaceDetailAPIHelper;
import com.api.google.place.PlacesConstans;
import com.api.google.place.entry.PlacesDetailEntry;
import com.api.google.place.model.PlaceVO;
import com.api.seoul.SeoulAPIConstants;
import com.api.seoul.subway.SubwayArrivalInfoAPIHelper;
import com.api.seoul.subway.SubwayFindInfoAPIHelper;
import com.api.seoul.subway.entry.SubwayInfoEntry;
import com.api.seoul.subway.model.StationVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.NearbyActivityManager;
import com.fivetrue.timeattack.utils.ImageUtils;
import com.fivetrue.utils.Logger;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NearbyActivity extends BaseActivity {

	private class ViewHolder{
		public TextView tvHeader = null;
		public ImageView ivMap = null;
		
		public ViewGroup layoutLactionInfo = null;
		public TextView tvLocationDetail = null;
		
		public ProgressBar pbLocationDetail = null;
	}

	private ViewGroup mContentView = null;
	private ViewHolder mViewHolder = new ViewHolder();


	private PlaceVO mEntry = null;
	private NearbyActivityManager mManager = null;


	@Override
	public View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub

		mContentView = (ViewGroup) inflater.inflate(R.layout.activity_nearby, null);
		initViews();
		initModels();
		initIntentData();
		initData();

		return mContentView;
	}

	private void initViews(){
		mViewHolder.tvHeader = (TextView) mContentView.findViewById(R.id.tv_nearby_header);
		mViewHolder.ivMap = (ImageView) mContentView.findViewById(R.id.iv_nearby_map_image);
		mViewHolder.tvLocationDetail = (TextView) mContentView.findViewById(R.id.tv_nearby_loaction_detail);
		mViewHolder.layoutLactionInfo = (ViewGroup) mContentView.findViewById(R.id.layout_naerby_location_info);
		
		mViewHolder.pbLocationDetail = (ProgressBar) mContentView.findViewById(R.id.pb_nearby_location_detail);

		mViewHolder.tvHeader.setTextColor(getResources().getColor(R.color.nearby_primary_light_color));
		mViewHolder.tvHeader.setBackground(getResources().getDrawable(R.color.nearby_primary_color));
		
		mViewHolder.layoutLactionInfo.setBackground(getResources().getDrawable(R.color.nearby_primary_color));

		getCustomActionBar().setBackGroundColorRes(R.color.nearby_primary_color, R.color.nearby_primary_dark_color);
		getCustomActionBar().setHomeIconLineColor(R.color.nearby_primary_light_color);
		getCustomActionBar().setIconSelector(R.drawable.selector_nearby_primary_color);

		if(getDrawerFragment() != null){
			getDrawerFragment().setBackGroundColorRes(R.color.nearby_primary_color, R.color.nearby_primary_dark_color);
			getDrawerFragment().setLineColor(R.color.nearby_primary_light_color);
			getDrawerFragment().setIconSelector(R.drawable.selector_nearby_primary_color);
		}

		mContentView.setBackground(getResources().getDrawable(R.color.nearby_primary_light_color));

	}
	
	private void initModels(){
		mManager = NearbyActivityManager.newInstance(this);
	}

	private void initIntentData(){
		Bundle b = getIntent().getExtras();
		if(b != null){
			mEntry = b.getParcelable(NearbyActivityManager.NEARBY_DATA);
		}
	}

	private void initData(){
		if(mEntry != null){
			Bitmap map = ImageUtils.getInstance(this).getImageBitmap(mEntry.getReference());
			if(map != null){
				mViewHolder.ivMap.setImageBitmap(map);
				map = null;
			}
			setLocationInfoFromPlaceInfo(mViewHolder.tvLocationDetail, mEntry);
			loadDetailData();
		}
	}
	
	public void setLocationInfoFromPlaceInfo(TextView tv, PlaceVO vo){
		
		if(tv == null || vo == null)
			return;
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(getString(R.string.location_latlng_message), vo.getLatitude(), vo.getLongitude()));
		tv.setText(sb.toString());
	}
	
	public void setLocationInfoFromPlaceDetailInfo(TextView tv, PlacesDetailEntry entry){
		
		if(tv == null || entry == null)
			return;
		StringBuilder sb = new StringBuilder();
		if(!TextUtils.isEmpty(tv.getText().toString())){
			sb.append(tv.getText().toString());
		}
		sb.append(String.format(getString(R.string.location_address_message), entry.getFormattedAddress()));
		tv.setText(sb.toString());
	}

	private void loadDetailData(){

		new PlaceDetailAPIHelper(NearbyActivity.this, this).requestPlaceDetail(mEntry.getReference(), new BaseResponseListener<PlacesDetailEntry>() {

			@Override
			public void onResponse(PlacesDetailEntry response) {
				// TODO Auto-generated method stub
				if(response != null){
					if(response.getStatus().equals(PlacesConstans.Status.OK.toString())){
						mViewHolder.pbLocationDetail.setVisibility(View.GONE);
						setData(response);
					}
				}
			}
		});
	}

	private void setData(PlacesDetailEntry entry){
		setLocationInfoFromPlaceDetailInfo(mViewHolder.tvLocationDetail, entry);
		String subwayName = mManager.getSubwayNameFromPlacesSubwayData(mEntry);
		loadingSubwayStaionInfo(subwayName);
	}
	
	private void loadingSubwayStaionInfo(String subwayName){
		if(subwayName != null){
			mManager.findingSubwayInfo(subwayName, new BaseResponseListener<SubwayInfoEntry>() {
				
				@Override
				public void onResponse(SubwayInfoEntry response) {
					// TODO Auto-generated method stub
					if(response != null){
						if(response.getStatus().equals(SeoulAPIConstants.ResultInfo.OK)){
							setSubwayInfoData(response);
						}else if(response.getStatus().equals(SeoulAPIConstants.ResultInfo.NO_DATA)){
							Logger.e("station info", "No data");
						}
					}
				}
			});
		}else{
			
		}
	}
	
	private void setSubwayInfoData(SubwayInfoEntry entry){
		if(entry != null){
			for(StationVO vo : entry.getStationList()){
				Logger.e("station info", vo.toString());
			}
		}
	}
	

	@Override
	public String getActionBarTitleName() {
		// TODO Auto-generated method stub
		if(mEntry != null){
			if(!TextUtils.isEmpty(mEntry.getName())){
				return mEntry.getName();
			}
		}
		return null;
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
	public void requsetNetworkResultSuccess() {
		// TODO Auto-generated method stub

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


	@Override
	public void onClickAcitionMenuLocationSearch(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isActionBarBlending() {
		// TODO Auto-generated method stub
		return false;
	}

}
