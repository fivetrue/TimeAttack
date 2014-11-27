package com.fivetrue.timeattack.activity;


import com.api.common.BaseResponseListener;
import com.api.google.place.PlaceDetailAPIHelper;
import com.api.google.place.PlacesConstans;
import com.api.google.place.entry.PlacesDetailEntry;
import com.api.google.place.model.PlaceVO;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.NearbyActivityManager;
import com.fivetrue.timeattack.utils.ImageUtils;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NearbyActivity extends BaseActivity {

	private class ViewHolder{
		public TextView tvHeader = null;
		public ImageView ivMap = null;
		public TextView tvLocationInfo = null;

	}

	private ViewGroup mContentView = null;
	private ViewHolder mViewHolder = new ViewHolder();


	private PlaceVO mEntry = null;


	@Override
	public View onCreateView(LayoutInflater inflater) {
		// TODO Auto-generated method stub

		mContentView = (ViewGroup) inflater.inflate(R.layout.activity_nearby, null);
		initViews();
		initIntentData();
		initData();

		return mContentView;
	}

	private void initViews(){
		mViewHolder.tvHeader = (TextView) mContentView.findViewById(R.id.tv_nearby_header);
		mViewHolder.ivMap = (ImageView) mContentView.findViewById(R.id.iv_nearby_map_image);

		mViewHolder.tvHeader.setBackground(getResources().getDrawable(R.color.nearby_primary_color));

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
			loadDetailData();
		}
	}

	private void loadDetailData(){

		new PlaceDetailAPIHelper(NearbyActivity.this, this).requestPlaceDetail(mEntry.getReference(), new BaseResponseListener<PlacesDetailEntry>() {

			@Override
			public void onResponse(PlacesDetailEntry response) {
				// TODO Auto-generated method stub
				if(response != null){
					if(response.getStatus().equals(PlacesConstans.Status.OK.toString())){
						setData(response);
					}
				}
			}
		});
	}

	private void setData(PlacesDetailEntry entry){

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
