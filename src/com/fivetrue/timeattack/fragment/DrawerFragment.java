package com.fivetrue.timeattack.fragment;

import com.fivetrue.timeattack.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;


public class DrawerFragment extends Fragment {
	
	public interface OnDrawerMenuClickListener{
		public void onMenuClick(View view);
	}
	
	private OnDrawerMenuClickListener mDrawerMenuClickListener = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_drawer, null);
		
		view.findViewById(R.id.layout_drawer_home).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "on Click", Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	}
	
	public void setOnClickDrawerMenuClickListener(OnDrawerMenuClickListener listener){
		mDrawerMenuClickListener = listener;
	}
}
