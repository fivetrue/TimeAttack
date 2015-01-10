package com.fivetrue.timeattack.view.adapter;

import java.util.ArrayList;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.api.google.place.model.PlaceVO;
import com.fivetrue.network.VolleyInstance;
import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.map.NearBySearchListFragment.PlaceItemDetailClickListener;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

public class NearBySearchResultAdapter extends CommonListAdapter<PlaceVO>{
	
	public PlaceItemDetailClickListener mPlaceItemDetailClickListener = null;

	public NearBySearchResultAdapter(Context context,
			ArrayList<PlaceVO> arrayList, int[] colorList, PlaceItemDetailClickListener ll) {
		super(context, arrayList, colorList);
		// TODO Auto-generated constructor stub
		mPlaceItemDetailClickListener = ll;
	}

	private String getPlaceSubtitle(PlaceVO entry){
		String keyword  = null;

		if(entry == null){
			return keyword;
		}

		return keyword;
	}



	@Override
	public View getView(
			int position,
			View convertView,
			ViewGroup parent,
			final com.fivetrue.timeattack.view.adapter.CommonListAdapter.ViewHolder holder) {
		// TODO Auto-generated method stub
		final PlaceVO data = getItem(position);

		if(data == null){
			return convertView;
		}
		
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
		
		holder.mainImage.setVisibility(View.GONE);
		holder.thumbBackImage.setVisibility(View.GONE);
		holder.aboveBodyShadow.setVisibility(View.GONE);

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
					holder.thumbImage.setImageBitmap(response.getBitmap());
				}else{
					holder.thumbImage.setImageResource(R.drawable.map);
				}
			}
		});
		
		holder.arrowImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mPlaceItemDetailClickListener != null){
					mPlaceItemDetailClickListener.onClickDetailItem(data);
				}
			}
		});
		holder.headerTitle.setText(data.getName());
		holder.Title.setText(getPlaceSubtitle(data));
		holder.subTitle.setText(data.getVicinity());
		holder.contentText.setText(getPlaceSubtitle(data));
		return convertView;
	}

	public PlaceItemDetailClickListener getPlaceItemDetailClickListener() {
		return mPlaceItemDetailClickListener;
	}

	public void setPlaceItemDetailClickListener(
			PlaceItemDetailClickListener ll) {
		this.mPlaceItemDetailClickListener = ll;
	}
	
	
}
