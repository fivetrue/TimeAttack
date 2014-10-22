package com.fivetrue.timeattack.fragment;

import com.fivetrue.timeattack.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract public class BaseMapFragment extends BaseFragment{
	
	private ViewGroup contentView = null;
	private GoogleMap map = null;
	
	public BaseMapFragment(){};
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initView(inflater);
		loadData();
		return contentView;
	}
	
	private void initView(LayoutInflater inflater){
		contentView = (ViewGroup) inflater.inflate(R.layout.fragment_map, null);
		map = ((SupportMapFragment)getFragmentManager().findFragmentById(R.id.fragment_map)).getMap();
		View childView = onCreateAddingViews(inflater);
		if(childView != null){
			contentView.addView(childView);
		}
	}
	
	/**
	 * 추가로 View를 생성 함
	 * @param inflater
	 * @return
	 */
	abstract protected View onCreateAddingViews(LayoutInflater inflater);
	
	/**
	 * 초기 맵 정보 설정 
	 * @param map
	 */
	abstract protected void configurationMap(GoogleMap map);
	
	/**
	 * 초기 데이터 로드
	 */
	abstract protected void loadData();

	public ViewGroup getContentView() {
		return contentView;
	}

	public GoogleMap getMap() {
		return map;
	}
}
