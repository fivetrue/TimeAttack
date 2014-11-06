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

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchLocationResultAdapter extends TimeAttackBaseAdapter <AddressResultVO>{
	
	private ViewHolder mViewHolder = null;

	public SearchLocationResultAdapter(Context context, int layoutResourceId,
			ArrayList<AddressResultVO> arrayList) {
		super(context, layoutResourceId, arrayList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		mViewHolder = new ViewHolder();

		if (convertView == null) {
			convertView = mLayoutInflater.inflate(mResourceId, null);

			mViewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_recently_item_image);
			mViewHolder.ivBackImage = (ImageView) convertView.findViewById(R.id.iv_recently_item_back_image);
			mViewHolder.ivArrow =  (ImageView) convertView.findViewById(R.id.iv_recently_item_arrow);
			mViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_recently_item_title);
			mViewHolder.tvSubtitle = (TextView) convertView.findViewById(R.id.tv_recently_item_sub_title);
			mViewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tv_recently_item_description);
			convertView.setTag(mViewHolder);
		}else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		final AddressResultVO data = getItem(position);

		if(data == null){
			return convertView;
		}

		mViewHolder.ivBackImage.setVisibility(View.VISIBLE);

		mViewHolder.ivImage.setImageResource(R.drawable.map);
		mViewHolder.ivBackImage.setVisibility(View.GONE);
		mViewHolder.tvTitle.setText(mContext.getString(R.string.location_infomation));
		if(data.getTypes() != null){
			if(!TextUtils.isEmpty(data.getTypes().get(0))){
				mViewHolder.tvSubtitle.setText(data.getTypes().get(0));
			}
		}
		mViewHolder.tvDescription.setText(getGeocodingDescription(data));
		mViewHolder.ivArrow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MapActivityManager.newInstance(mContext).goToActivity(data);
			}
		});

		return convertView;
	}

	private class ViewHolder{
		public ImageView ivImage;
		public ImageView ivBackImage;
		public TextView tvTitle;
		public TextView tvSubtitle;
		public ImageView ivArrow;
		public TextView tvDescription;
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

}
