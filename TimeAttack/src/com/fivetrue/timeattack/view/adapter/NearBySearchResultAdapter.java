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
import com.api.google.place.model.PlaceVO;
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

public class NearBySearchResultAdapter extends TimeAttackBaseAdapter <PlaceVO>{
	
	private ViewHolder mViewHolder = null;

	public NearBySearchResultAdapter(Context context, int layoutResourceId,
			ArrayList<PlaceVO> arrayList) {
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
		
		PlaceVO data = getItem(position);
		
		if(data == null){
			return convertView;
		}
		
		VolleyInstance.getImageLoader().get(data.getIcon(), new ImageListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				error.printStackTrace();
			}
			
			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				// TODO Auto-generated method stub
				if(response.getBitmap() != null){
					mViewHolder.ivImage.setImageBitmap(response.getBitmap());
				}else{
					mViewHolder.ivImage.setImageResource(R.drawable.map);
				}
			}
		});
			mViewHolder.tvTitle.setText(data.getName());
			mViewHolder.tvSubtitle.setText(getPlaceSubtitle(data));
			mViewHolder.tvDescription.setText(getPlaceSubtitle(data));
//			mViewHolder.ivArrow.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if(entry.getAddressList().size() > 1){
//						SearchActivityManager.newInstance(mContext).goToActivity(entry);
//					}else{
//						MapActivityManager.newInstance(mContext).goToActivity(entry.getAddressList().get(0));
//					}
//				}
//			});
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
	
	private String getPlaceSubtitle(PlaceVO entry){
		String keyword  = null;
		
		if(entry == null){
			return keyword;
		}
		
		return keyword;
	}
		
	
	private String getPlaceDescription(PlaceVO entry){
		StringBuilder desc = new StringBuilder();
		
		
		return desc.toString();
	}
}
