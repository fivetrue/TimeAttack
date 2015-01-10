package com.fivetrue.timeattack.view.adapter;

import java.util.ArrayList;


import com.api.google.directions.model.RouteVO;
import com.api.google.directions.model.steps.StepTransitVO;
import com.fivetrue.timeattack.fragment.map.DirectionsListFragment.DirectionsItemClickListener;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

public class DirectionsDetailInfoAdapter extends CommonListAdapter<StepTransitVO>{
	

	public DirectionsDetailInfoAdapter(Context context,
			ArrayList<StepTransitVO> arrayList, int[] colorList) {
		super(context, arrayList, colorList);
		// TODO Auto-generated constructor stub
	}
	
	private String getDirectionTitle(RouteVO entry){
		String title = "";
		if(entry != null){
			title = "거리 " + entry.getDistanceText();
		}
		return title;
	}

	private String getDirectionSubtitle(RouteVO entry){
		String subtitle = "";
		if(entry != null){
			subtitle = "시간" + entry.getDurationText();
		}
		return subtitle;
	}
	
	private String getDirectionContetent(RouteVO entry){
		String content = "";
		if(entry != null){
			content = entry.getSummary();
		}
		return content;
	}

	@Override
	public View getView(
			int position,
			View convertView,
			ViewGroup parent,
			final com.fivetrue.timeattack.view.adapter.CommonListAdapter.ViewHolder holder) {
		// TODO Auto-generated method stub
		final StepTransitVO data = getItem(position);

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
		holder.arrowImage.setVisibility(View.GONE);

//		holder.headerTitle.setText(data.getArrivalAddress());
//		holder.Title.setText(getDirectionTitle(data));
//		holder.subTitle.setText(getDirectionSubtitle(data));
//		holder.contentText.setText(getDirectionContetent(data));
		return convertView;
	}

}
