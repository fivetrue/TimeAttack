package com.fivetrue.timeattack.view.adapter;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
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
import com.fivetrue.network.VolleyInstance;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.activity.MapActivity;
import com.fivetrue.timeattack.activity.manager.MapActivityManager;
import com.fivetrue.timeattack.activity.manager.SearchActivityManager;
import com.fivetrue.timeattack.database.model.NetworkResult;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecentlyNetworkResultAdapter extends TimeAttackBaseAdapter <NetworkResult>{
	
	private ViewHolder mViewHolder = null;

	public RecentlyNetworkResultAdapter(Context context, int layoutResourceId,
			ArrayList<NetworkResult> arrayList) {
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
		
		NetworkResult data = getItem(position);
		
		if(data == null){
			return convertView;
		}
		
		mViewHolder.ivBackImage.setVisibility(View.VISIBLE);
		
		switch(data.getType()){
		case Direction : 
		{
			DirectionsEntry entry = convertDirectionEntry(data.getResult());
			mViewHolder.tvTitle.setText(entry.getRouteArray().get(0).getArrivalAddress());
			mViewHolder.tvSubtitle.setText(entry.getRouteArray().get(0).getSummary());
			break;
		}

		case Place :
		{
			if(data.getResult() != null){
				PlacesEntry entry = convertPlaceEntry(data.getResult());
				if(entry.getPlaceList().size() > 0){
					mViewHolder.tvTitle.setText(entry.getPlaceList().get(0).getName());
					mViewHolder.tvSubtitle.setText(entry.getPlaceList().get(0).getTypeList().get(0));

					Bitmap bm = VolleyInstance.getLruCache().get(entry.getPlaceList().get(0).getIcon());

					if(bm != null){
						mViewHolder.ivImage.setImageBitmap(bm);
					}else{
						VolleyInstance.getImageLoader().get(entry.getPlaceList().get(0).getIcon(), new ImageListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								// TODO Auto-generated method stub
								error.printStackTrace();
							}

							@Override
							public void onResponse(ImageContainer response, boolean isImmediate) {
								// TODO Auto-generated method stub
								if(response != null){
									Bitmap bm = response.getBitmap();
									if(bm != null){
										mViewHolder.ivImage.setImageBitmap(bm);
									}
								}

							}
						});
					}
				}
			}
			break;
		}

		case GeoCoding :
		{
			final GeocodingEntry entry = convertGeocodeEntry(data.getResult());
			mViewHolder.ivImage.setImageResource(R.drawable.map);
			mViewHolder.ivBackImage.setVisibility(View.GONE);
			mViewHolder.tvTitle.setText(entry.getAddressList().size() > 1 ? R.string.location_search : R.string.location_infomation);
			mViewHolder.tvSubtitle.setText(getGeocodingSubtitle(entry));
			mViewHolder.tvDescription.setText(getGeocodingDescription(entry));
			mViewHolder.ivArrow.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(entry.getAddressList().size() > 1){
						SearchActivityManager.newInstance(mContext).goToActivity(entry);
					}else{
						MapActivityManager.newInstance(mContext).goToActivity(entry.getAddressList().get(0));
					}
				}
			});
			break;
		}

		case SubwayInfo :
		{
			SubwayInfoEntry entry = convertSubwayInfoEntry(data.getResult());
			mViewHolder.tvTitle.setText(entry.getStationList().get(0).getStationName());
			mViewHolder.tvSubtitle.setText(entry.getStationList().get(0).getLineNumber());
			break;
		}

		case SubwayArrival :
		{
			SubwayArrivalInfoEntry entry = convertSubwayArrivalInfoEntry(data.getResult());
			mViewHolder.tvTitle.setText(entry.getArrivalList().get(0).getSubwayName());
			mViewHolder.tvSubtitle.setText(entry.getArrivalList().get(0).getDestinationName());
			break;
		}
		}
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
	
	private String getGeocodingSubtitle(GeocodingEntry entry){
		String keyword  = null;
		
		if(entry == null){
			return keyword;
		}
		
		if(entry.getAddressList().size() > 1){
			if(entry.getAddressList().size() > 0){
				if(entry.getAddressList().get(0).getAddressComponents().size() > 0){
					keyword = entry.getAddressList().get(0).getAddressComponents().get(0).getLongName();
				}
			}
		}else{
			if(entry.getAddressList().size() > 0){
				if(entry.getAddressList().get(0).getAddressComponents().size() > 0){
					keyword = entry.getAddressList().get(0).getAddressComponents().get(0).getTypes().get(0);
				}
			}
		}
	
		return keyword;
	}
		
	
	private String getGeocodingDescription(GeocodingEntry entry){
		StringBuilder desc = new StringBuilder();
		
		//주소가 복수개 일 경우.
		if(entry.getAddressList().size() > 1){
			for(AddressResultVO vo : entry.getAddressList()){
				if(vo != null){
					desc.append(vo.getAddress())
					.append("\n");
				}
			}
		}
		//주소가 단수일 경우.
		else{
			String postal_code = null; 
			AddressResultVO vo = entry.getAddressList().get(0);
			for(AddressComponentVO component : vo.getAddressComponents()){
				if(component.getTypes() != null && component.getTypes().size() >= 0){
					if(component.getTypes().get(0).equals(GeocodingConstants.Types.postal_code.toString())){
						postal_code = component.getLongName();
						break;
					}
				}
			}
			
			desc.append(mContext.getString(R.string.location_address))
			.append("\n")
			.append(vo.getAddress())
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
			.append(vo.getLatitude())
			.append(",")
			.append(vo.getLongitude())
			.append(")\n");
		}
		
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
