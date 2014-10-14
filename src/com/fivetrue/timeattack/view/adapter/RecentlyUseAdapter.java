package com.fivetrue.timeattack.view.adapter;

import java.util.ArrayList;

import com.api.google.directions.entry.DirectionsEntry;
import com.api.google.directions.model.RouteVO;
import com.api.google.place.entry.PlacesEntry;
import com.api.google.place.model.PlaceVO;
import com.api.seoul.subway.entry.SubwayInfoEntry;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.model.RecentlyUseItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecentlyUseAdapter extends TimeAttackBaseAdapter <RecentlyUseItem>{

	public RecentlyUseAdapter(Context context, int layoutResourceId,
			ArrayList<RecentlyUseItem> arrayList) {
		super(context, layoutResourceId, arrayList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder = new ViewHolder();

		if (convertView == null) {
			convertView = mLayoutInflater.inflate(mResourceId, null);

			holder.ivImage = (ImageView) convertView.findViewById(R.id.iv_recently_item_image);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_recently_item_title);
			holder.tvDescription = (TextView) convertView.findViewById(R.id.tv_recently_item_description);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}

		RecentlyUseItem data = getItem(position);
		
		String title = null;
		String description = null;

		if(data.getDirection() != null){
			DirectionsEntry entry = data.getDirection();
			RouteVO route = entry.getRouteArray().get(0);
			title = "깇 찾기";
			description = route.getDepartureAddress() + " -> " + route.getArrivalAddress();
			
		}else if(data.getPlace() != null){
			PlacesEntry places = data.getPlace();
			PlaceVO place = places.getPlaceList().get(0);
			title = "주변 역 찾기";
			description = place.getName();

		}else if(data.getSubwayInfo() != null){
			SubwayInfoEntry subway = data.getSubwayInfo();
			title = "지하철 역 정보";
			description = subway.getStationList().get(0).getStationName();
		}
		
		holder.tvTitle.setText(title);
		holder.tvDescription.setText(description);

		return convertView;
	}

	private class ViewHolder{
		public ImageView ivImage;
		public TextView tvTitle;
		public TextView tvDescription;
	}


}
