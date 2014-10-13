package com.fivetrue.timeattack.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.BaseFragment;

public class RecentlyRouteFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.fragment_recently, null);
		return view;
	}
}
