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
import com.fivetrue.timeattack.activity.manager.NearbyActivityManager;
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

public class RecentlyGeocodingResultAdapter extends CommonListAdapter <NetworkResult>{
	

	public RecentlyGeocodingResultAdapter(Context context, ArrayList<NetworkResult> arrayList, int[] colorList) {
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
			
			desc.append(String.format(mContext.getString(R.string.location_address_message), vo.getAddress()));
			if(!TextUtils.isEmpty(postal_code)){
				desc.append(String.format(mContext.getString(R.string.location_postal_code_message), postal_code));
			}
			desc.append("\n");
			desc.append(String.format(mContext.getString(R.string.location_latlng_message), vo.getLatitude(), vo.getLongitude()));
		}
		
		return desc.toString();
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

	@Override
	public View getView(
			int position,
			View convertView,
			ViewGroup parent,
			final com.fivetrue.timeattack.view.adapter.CommonListAdapter.ViewHolder holder) {
		// TODO Auto-generated method stub
		NetworkResult data = getItem(position);
		
		if(data != null){
			if(!data.isFinishAnimation()){
				convertView.setX(-mContext.getResources().getDisplayMetrics().widthPixels);
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
			
					final GeocodingEntry geocoding = convertGeocodeEntry(data.getResult());
					holder.headerTitle.setText(R.string.location_search);
					holder.thumbImage.setImageResource(R.drawable.map);
					holder.thumbBackImage.setVisibility(View.GONE);
					
					holder.thumbImage.setImageResource(R.drawable.map);
					holder.thumbBackImage.setVisibility(View.GONE);
					
					if(geocoding.getAddressList().size() == 1){
						AddressResultVO address = geocoding.getAddressList().get(0);
						Bitmap image = ImageUtils.getInstance(mContext).getImageBitmap(address.getLatitude() + address.getLongitude());
						if(image != null){
							holder.aboveBodyShadow.setVisibility(View.VISIBLE);
							holder.mainImage.setVisibility(View.VISIBLE);
							holder.mainImage.setImageBitmap(image);
						}else{
							holder.mainImage.setVisibility(View.GONE);
						}
					}
					
					holder.Title.setText(geocoding.getAddressList().size() > 1 ? R.string.location_search : R.string.location_infomation);
					holder.subTitle.setText(getGeocodingSubtitle(geocoding));
					holder.contentText.setText(getGeocodingDescription(geocoding));
					holder.arrowImage.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if(geocoding.getAddressList().size() > 1){
								SearchActivityManager.newInstance(mContext).goToActivity(geocoding);
							}else{
								MapActivityManager.newInstance(mContext).goToActivity(geocoding.getAddressList().get(0));
							}
						}
					});
					return convertView;
				}
		return convertView;
	}

}
