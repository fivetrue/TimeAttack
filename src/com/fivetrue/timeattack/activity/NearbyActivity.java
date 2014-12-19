package com.fivetrue.timeattack.activity;


import com.api.common.BaseResponseListener;
import com.api.google.place.PlaceDetailAPIHelper;
import com.api.google.place.PlacesConstans;
import com.api.google.place.entry.PlacesDetailEntry;
import com.api.google.place.model.PlaceVO;
import com.api.seoul.SeoulAPIConstants;
import com.api.seoul.subway.entry.SubwayInfoEntry;
import com.api.seoul.subway.model.StationVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.NearbyActivityManager;
import com.fivetrue.timeattack.utils.ImageUtils;
import com.fivetrue.utils.Logger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NearbyActivity extends BaseActivity {

	private class ViewHolder{
		public TextView tvHeader = null;
		public ImageView ivMap = null;

		public ViewGroup layoutLocationInfo = null;
		public TextView tvLocationDetail = null;
		public ProgressBar pbLocationDetail = null;
	}
	
	private class DetailViewHolder{
		public ViewGroup contentView = null;
		public TextView tvPhoneNumber = null;
		public TextView tvGooglePlusUrl = null;
	}
	
	private class SubwayViewHolder{
		public ViewGroup contentView = null;
		public ViewGroup layoutSubway = null;
		public TextView tvSubwayName = null;
		public TextView tvSubwayLine = null;
		public TextView tvSubwayCode = null;
		public TextView tvSubwayForiegnCode = null;
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
		mViewHolder.layoutLocationInfo = (ViewGroup) mContentView.findViewById(R.id.layout_naerby_location_info);

		mViewHolder.pbLocationDetail = (ProgressBar) mContentView.findViewById(R.id.pb_nearby_location_detail);

		mViewHolder.tvHeader.setTextColor(getResources().getColor(R.color.nearby_primary_light_color));
		mViewHolder.tvHeader.setBackground(getResources().getDrawable(R.color.nearby_primary_color));

		mViewHolder.layoutLocationInfo.setBackground(getResources().getDrawable(R.color.nearby_primary_color));

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
		setDetailInfoFromPlaceDetailInfo((ViewGroup)mContentView.getChildAt(0) ,entry);

		String subwayName = mManager.getSubwayNameFromPlacesSubwayData(mEntry);
		loadingSubwayStaionInfo((ViewGroup)mContentView.getChildAt(0), subwayName);
	}

	private void setLocationInfoFromPlaceDetailInfo(TextView tv, PlacesDetailEntry entry){

		if(tv == null || entry == null)
			return;
		StringBuilder sb = new StringBuilder();
		if(!TextUtils.isEmpty(tv.getText().toString())){
			sb.append(tv.getText().toString());
		}
		sb.append(String.format(getString(R.string.location_address_message), entry.getFormattedAddress()));
		tv.setText(sb.toString());
	}

	private void setDetailInfoFromPlaceDetailInfo(ViewGroup parent, PlacesDetailEntry entry){
		if(entry != null){
			DetailViewHolder viewHolder = new DetailViewHolder();
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			viewHolder.contentView =  (ViewGroup) inflater.inflate(R.layout.include_nearby_detail_info_layout, null);
			viewHolder.tvPhoneNumber = (TextView) viewHolder.contentView.findViewById(R.id.tv_nearby_detail_info_phone_number);
			viewHolder.tvGooglePlusUrl = (TextView) viewHolder.contentView.findViewById(R.id.tv_nearby_detail_info_google_plus_url);
			
			if(TextUtils.isEmpty(entry.getInternationalPhoneNumber())){
				viewHolder.tvPhoneNumber.setVisibility(View.GONE);
			}else{
				viewHolder.tvPhoneNumber.setVisibility(View.GONE);
				viewHolder.tvPhoneNumber.setText(entry.getInternationalPhoneNumber());
				viewHolder.tvPhoneNumber.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_DIAL);
						intent.setData(Uri.parse("tel:" + ((TextView)v).getText().toString().trim()));
						startActivity(intent);
					}
				});
			}
			
			if(TextUtils.isEmpty(entry.getPlaceUrl())){
				viewHolder.tvGooglePlusUrl.setVisibility(View.GONE);
			}else{
				viewHolder.tvGooglePlusUrl.setText(Html.fromHtml("<a href=" + entry.getPlaceUrl() + ">" + entry.getPlaceUrl() + "</a>"));
				viewHolder.tvGooglePlusUrl.setMovementMethod(LinkMovementMethod.getInstance());
			}

			viewHolder.tvGooglePlusUrl.setText(Html.fromHtml("<a href=" + entry.getPlaceUrl() + ">" + entry.getPlaceUrl() + "</a>"));
			viewHolder.tvGooglePlusUrl.setMovementMethod(LinkMovementMethod.getInstance());
			parent.addView(viewHolder.contentView);
		}
	}

	private void loadingSubwayStaionInfo(final ViewGroup parent, String subwayName){
		if(subwayName != null){
			mManager.findingSubwayInfo(this, subwayName, new BaseResponseListener<SubwayInfoEntry>() {

				@Override
				public void onResponse(SubwayInfoEntry response) {
					// TODO Auto-generated method stub
					if(response != null){
						if(response.getStatus().equals(SeoulAPIConstants.ResultInfo.OK)){
							setSubwayInfoData(parent, response);
						}else if(response.getStatus().equals(SeoulAPIConstants.ResultInfo.NO_DATA)){
							Logger.e("station info", "No data");
						}
					}
				}
			});
		}else{

		}
	}

	private void setSubwayInfoData(ViewGroup parent, SubwayInfoEntry entry){
		if(parent != null && entry != null){
			for(StationVO vo : entry.getStationList()){
				if(vo != null){
					SubwayViewHolder viewHolder = new SubwayViewHolder();
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					viewHolder.contentView =  (ViewGroup) inflater.inflate(R.layout.include_nearby_subway_info_layout, null);
					viewHolder.layoutSubway = (ViewGroup) viewHolder.contentView.findViewById(R.id.layout_nearby_subway_info);
					viewHolder.tvSubwayName = (TextView) viewHolder.contentView.findViewById(R.id.tv_nearby_subway_info_title);
					viewHolder.tvSubwayLine = (TextView) viewHolder.contentView.findViewById(R.id.tv_nearby_subway_info);
//					viewHolder.tvSubwayCode = (TextView) viewHolder.contentView.findViewById(R.id.tv_nearby_subway_info2);
					viewHolder.tvSubwayForiegnCode = (TextView) viewHolder.contentView.findViewById(R.id.tv_nearby_subway_info3);
					
					viewHolder.layoutSubway.setBackground(getResources().getDrawable(R.color.nearby_primary_color));
					viewHolder.tvSubwayName.setText(vo.getStationName());
					viewHolder.tvSubwayLine.setText(entry.getLineCodeToName(NearbyActivity.this, vo.getLineNumber()));
//					viewHolder.tvSubwayCode.setText(vo.getStationCode());
					viewHolder.tvSubwayForiegnCode.setText(vo.getForiegnCode());
					
					parent.addView(viewHolder.contentView);
				}
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
