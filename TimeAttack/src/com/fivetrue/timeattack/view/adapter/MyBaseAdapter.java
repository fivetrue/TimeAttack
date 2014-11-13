package com.fivetrue.timeattack.view.adapter;

import java.util.ArrayList;

import com.fivetrue.timeattack.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

abstract public class MyBaseAdapter <T> extends BaseAdapter {
	
	protected final int INVALID_VALUE = -1;
	protected ArrayList<T> mArrayList = null;
	protected int mResourceId = INVALID_VALUE;
	protected Context mContext = null;
	protected LayoutInflater mLayoutInflater = null;
	protected int mSelector = R.drawable.selector_common_alpha_another_gray;
	
	public MyBaseAdapter(Context context, int layoutResourceId, ArrayList<T> arrayList){
		mContext = context;
		mResourceId = layoutResourceId;
		mArrayList = arrayList;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public ArrayList<T> getArrayList(){
		return mArrayList;
	}
	
	public void setArrayList(ArrayList<T> arrayList){
		mArrayList = arrayList;
		notifyDataSetChanged();
	}
	
	public void setIconSelector(int resourceId){
		mSelector = resourceId;
	}
}
