package com.fivetrue.timeattack.view.adapter;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.api.google.directions.converter.DirectionsConverter;
import com.api.google.directions.entry.DirectionsEntry;
import com.api.google.geocoding.GeocodingConstants;
import com.api.google.geocoding.converter.GeocodingConverter;
import com.api.google.geocoding.entry.GeocodingEntry;
import com.api.google.geocoding.model.AddressComponentVO;
import com.api.google.geocoding.model.AddressResultVO;
import com.api.google.place.converter.PlacesConverter;
import com.api.google.place.entry.PlacesEntry;
import com.api.seoul.subway.converter.SubwayArrivalInfoConverter;
import com.api.seoul.subway.converter.SubwayInfoConverter;
import com.api.seoul.subway.entry.SubwayArrivalInfoEntry;
import com.api.seoul.subway.entry.SubwayInfoEntry;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.manager.MapActivityManager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

public class SearchLocationResultAdapter extends CommonListAdapter <AddressResultVO>{
	

	public SearchLocationResultAdapter(Context context,
			ArrayList<AddressResultVO> arrayList, int[] colorList) {
		super(context, arrayList, colorList);
		// TODO Auto-generated constructor stub
	}


	private String getGeocodingDescription(AddressResultVO entry){
		String postal_code = null;
		for(AddressComponentVO componnt : entry.getAddressComponents()){
			if(componnt.getTypes() != null && componnt.getTypes().size() >= 0){
				if(componnt.getTypes().get(0).equals(GeocodingConstants.Types.postal_code.toString())){
					postal_code = componnt.getLongName();
					break;
				}
			}
		}
		
		StringBuilder desc = new StringBuilder();
		desc.append(mContext.getString(R.string.location_address))
		.append("\n")
		.append(entry.getAddress())
		.append("\n");
		if(!TextUtils.isEmpty(postal_code)){
			desc.append(mContext.getString(R.string.location_postal_code))
			.append("(")
			.append(postal_code)
			.append(")\n");
		}
		desc.append("\n")
		.append(mContext.getString(R.string.location_latitude))
		.append(",")
		.append(mContext.getString(R.string.location_longitude))
		.append(" (")
		.append(entry.getLatitude())
		.append(",")
		.append(entry.getLongitude())
		.append(")\n");
		return desc.toString();
	}

	public DirectionsEntry convertDirectionEntry(String result){
		DirectionsEntry entry = null;
		try {
			JSONObject json = new JSONObject(result);
			entry = new DirectionsConverter().onReceive(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entry;
	}

	public PlacesEntry convertPlaceEntry(String result){
		PlacesEntry entry = null;
		try {
			JSONObject json = new JSONObject(result);
			entry = new PlacesConverter().onReceive(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entry;
	}

	public GeocodingEntry convertGeocodeEntry(String result){
		GeocodingEntry entry = null;
		try {
			JSONObject json = new JSONObject(result);
			entry = new GeocodingConverter().onReceive(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entry;
	}

	public SubwayInfoEntry convertSubwayInfoEntry(String result){
		SubwayInfoEntry entry = null;
		try {
			JSONObject json = new JSONObject(result);
			entry = new SubwayInfoConverter().onReceive(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entry;
	}

	public SubwayArrivalInfoEntry convertSubwayArrivalInfoEntry(String result){
		SubwayArrivalInfoEntry entry = null;
		try {
			JSONObject json = new JSONObject(result);
			entry = new SubwayArrivalInfoConverter().onReceive(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entry;
	}

	@Override
	public View getView(
			int position,
			View convertView,
			ViewGroup parent,
			com.fivetrue.timeattack.view.adapter.CommonListAdapter.ViewHolder holder) {
		// TODO Auto-generated method stub
		final AddressResultVO data = getItem(position);

		if(data == null){
			return convertView;
		}
		
		if(!data.isFinishAnimation()){
			convertView.setX(mContext.getResources().getDisplayMetrics().widthPixels);
			ObjectAnimator moveX = ObjectAnimator.ofFloat(convertView, "translationX", convertView.getX(), 0);
			moveX.setInterpolator(new AccelerateDecelerateInterpolator());
			moveX.setDuration(ANI_DURATION);
			AnimatorSet aniPlayer = new AnimatorSet();
			aniPlayer.play(moveX);
			aniPlayer.start();
			data.setFinishAnimation(true);
		}

		holder.thumbImage.setImageResource(R.drawable.map);
		holder.thumbBackImage.setVisibility(View.GONE);
		holder.mainImage.setVisibility(View.GONE);
		holder.headerTitle.setText(mContext.getString(R.string.location_infomation));
		holder.Title.setText(data.getAddress());
		if(data.getTypes() != null){
			if(!TextUtils.isEmpty(data.getTypes().get(0))){
				holder.subTitle.setText(data.getTypes().get(0));
			}
		}
		holder.contentText.setText(getGeocodingDescription(data));
		holder.arrowImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MapActivityManager.newInstance(mContext).goToActivity(data);
			}
		});

		return convertView;
	}

}
