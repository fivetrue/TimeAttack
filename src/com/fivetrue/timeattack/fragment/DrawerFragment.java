package com.fivetrue.timeattack.fragment;

import com.fivetrue.timeattack.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;


public class DrawerFragment extends BaseFragment {
	
	public interface OnDrawerMenuClickListener{
		public void onMenuClick(ViewGroup parent, ViewGroup itemLayout, TextView itemText);
	}
	
	private OnDrawerMenuClickListener mDrawerMenuClickListener = null;
	private ViewHolder mViewHolder = new ViewHolder();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initView(inflater);
		return mViewHolder.layout;
	}
	
	private void initView(LayoutInflater inflater){
		mViewHolder.layout = inflater.inflate(R.layout.fragment_drawer, null);
		
		mViewHolder.layoutParent = (ViewGroup) mViewHolder.layout.findViewById(R.id.layout_items_parent);
		mViewHolder.layoutHome = (ViewGroup) mViewHolder.layout.findViewById(R.id.layout_drawer_home);
		mViewHolder.layoutDirection = (ViewGroup) mViewHolder.layout.findViewById(R.id.layout_drawer_direction);
		mViewHolder.layoutNearBy = (ViewGroup) mViewHolder.layout.findViewById(R.id.layout_drawer_near_by);
		
		
		mViewHolder.tvHome = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_home);
		mViewHolder.tvDirection = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_direction);
		mViewHolder.tvNearBy = (TextView) mViewHolder.layout.findViewById(R.id.tv_drawer_near_by);
		
		mViewHolder.layoutHome.setOnClickListener(mOnClickListener);
		mViewHolder.layoutDirection.setOnClickListener(mOnClickListener);
		mViewHolder.layoutNearBy.setOnClickListener(mOnClickListener);
		
	}
	
	
	public void setOnClickDrawerMenuClickListener(OnDrawerMenuClickListener listener){
		mDrawerMenuClickListener = listener;
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mDrawerMenuClickListener == null)
				return ;
			
			switch(v.getId()){
			
			case R.id.layout_drawer_home :
				mDrawerMenuClickListener.onMenuClick((ViewGroup)mViewHolder.layoutParent, mViewHolder.layoutHome, mViewHolder.tvHome);
				break;
				
			case R.id.layout_drawer_near_by :
				mDrawerMenuClickListener.onMenuClick((ViewGroup)mViewHolder.layoutParent, mViewHolder.layoutNearBy, mViewHolder.tvNearBy);
				break;
				
			case R.id.layout_drawer_direction :
				mDrawerMenuClickListener.onMenuClick((ViewGroup)mViewHolder.layoutParent, mViewHolder.layoutDirection, mViewHolder.tvDirection);
				break;
			}
		}
	};
	
	private class ViewHolder{
		public View layout;
		public ViewGroup layoutHome;
		public ViewGroup layoutNearBy;
		public ViewGroup layoutDirection;
		public ViewGroup layoutParent;
		public TextView tvHome;
		public TextView tvNearBy;
		public TextView tvDirection;
	}
}
