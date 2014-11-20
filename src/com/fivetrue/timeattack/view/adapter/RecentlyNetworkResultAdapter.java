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
import com.fivetrue.timeattack.activity.manager.MapActivityManager;
import com.fivetrue.timeattack.activity.manager.SearchActivityManager;
import com.fivetrue.timeattack.database.model.NetworkResult;
import com.fivetrue.timeattack.utils.ImageUtils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.ViewGroup;

public class RecentlyNetworkResultAdapter extends CommonListAdapter <NetworkResult>{
	

	public RecentlyNetworkResultAdapter(Context context, ArrayList<NetworkResult> arrayList, int[] colorList) {
		super(context, arrayList, colorList);
		// TODO Auto-generated constructor stub
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

	@Override
	public View getView(
			int position,
			View convertView,
			ViewGroup parent,
			final com.fivetrue.timeattack.view.adapter.CommonListAdapter.ViewHolder holder) {
		// TODO Auto-generated method stub
		NetworkResult data = getItem(position);
		
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
		
		holder.thumbBackImage.setVisibility(View.GONE);
		holder.mainImage.setVisibility(View.GONE);
		holder.aboveBodyShadow.setVisibility(View.GONE);
		
		switch(data.getType()){
		case Direction : 
		{
			DirectionsEntry entry = convertDirectionEntry(data.getResult());
			holder.headerTitle.setText(R.string.find_direction);
			holder.Title.setText(entry.getRouteArray().get(0).getArrivalAddress());
			holder.subTitle.setText(entry.getRouteArray().get(0).getSummary());
			break;
		}

		case Place :
		{
			holder.headerTitle.setText(R.string.find_subway_nearby);
			if(data.getResult() != null){
				PlacesEntry entry = convertPlaceEntry(data.getResult());
				if(entry.getPlaceList().size() > 0){
					holder.Title.setText(entry.getPlaceList().get(0).getName());
					holder.subTitle.setText(entry.getPlaceList().get(0).getTypeList().get(0));

					Bitmap bm = VolleyInstance.getLruCache().get(entry.getPlaceList().get(0).getIcon());

					if(bm != null){
						holder.thumbImage.setImageBitmap(bm);
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
										holder.thumbImage.setImageBitmap(bm);
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
			holder.headerTitle.setText(R.string.location_search);
			holder.thumbImage.setImageResource(R.drawable.map);
			holder.thumbBackImage.setVisibility(View.GONE);
			
			holder.thumbImage.setImageResource(R.drawable.map);
			holder.thumbBackImage.setVisibility(View.GONE);
			
			
			
			if(entry.getAddressList().size() == 1){
				AddressResultVO address = entry.getAddressList().get(0);
				Bitmap image = ImageUtils.getInstance(mContext).getImageBitmap(address.getLatitude() + address.getLongitude());
				if(image != null){
					holder.aboveBodyShadow.setVisibility(View.VISIBLE);
					holder.mainImage.setVisibility(View.VISIBLE);
					holder.mainImage.setImageBitmap(image);
				}else{
					holder.mainImage.setVisibility(View.GONE);
				}
			}
			
			holder.Title.setText(entry.getAddressList().size() > 1 ? R.string.location_search : R.string.location_infomation);
			holder.subTitle.setText(getGeocodingSubtitle(entry));
			holder.contentText.setText(getGeocodingDescription(entry));
			holder.arrowImage.setOnClickListener(new OnClickListener() {
				
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
//			SubwayInfoEntry entry = convertSubwayInfoEntry(data.getResult());
//			holder.tvTitle.setText(entry.getStationList().get(0).getStationName());
//			holder.tvSubtitle.setText(entry.getStationList().get(0).getLineNumber());
			break;
		}

		case SubwayArrival :
		{
//			SubwayArrivalInfoEntry entry = convertSubwayArrivalInfoEntry(data.getResult());
//			holder.tvTitle.setText(entry.getArrivalList().get(0).getSubwayName());
//			holder.tvSubtitle.setText(entry.getArrivalList().get(0).getDestinationName());
			break;
		}
		}
		return convertView;
	}

}
