package com.fivetrue.timeattack.fragment;


import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.view.adapter.TimeAttackBaseAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

abstract public class BaseListFragment <T> extends BaseFragment implements OnItemClickListener, OnScrollListener{
	
	private ViewGroup emptyLayout = null;
	private TextView tvEmpty = null;
	protected ListView listView = null;
	protected TimeAttackBaseAdapter<T> adapter = null;
	
	private ViewGroup contentView = null;
	private View shadow = null;
	private View listHeader = null;
	private View listFooter = null;
	
	public BaseListFragment(){};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initView(inflater);
		onLoadListData();
		return contentView;
	}
	
	public void initView(LayoutInflater inflater){
		contentView = (ViewGroup) inflater.inflate(R.layout.fragment_list, null);
		listView = (ListView) contentView.findViewById(R.id.lv_base_list);
		emptyLayout = (ViewGroup) contentView.findViewById(R.id.layout_empty);
		tvEmpty = (TextView) contentView.findViewById(R.id.tv_empty);
		shadow = contentView.findViewById(R.id.shadow);
		listView.setOnItemClickListener(this);
		
		listFooter = initFooter();
		listHeader = initHeader();
		
		if(listHeader != null){
			listView.addHeaderView(listHeader, null, false);
		}
		
		if(listFooter != null){
			listView.addFooterView(listFooter, null, false);
		}
		
		configListView(listView);
	}
	
	protected void setEmptyLayout(boolean isShowing){
		if(emptyLayout == null || listView == null){
			return;
		}
		
		if(isShowing){
			emptyLayout.setVisibility(View.VISIBLE);
			shadow.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}else{
			emptyLayout.setVisibility(View.GONE);
			shadow.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
		}
	}
	
	abstract public View initHeader();
	
	abstract public View initFooter();
	
	abstract public void onLoadListData();
	
	abstract protected void configListView(ListView listview);
	
	abstract protected void onListItemClick(AdapterView<?> parent, View view, int position,	long id);
	
	abstract protected void onListScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount);
	
	abstract protected void onListScrollStateChanged(AbsListView view, int scrollState);
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		onListItemClick(parent, view, position, id);
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		onListScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		onListScrollStateChanged(view, scrollState);
	}

	public ListView getListView() {
		return listView;
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	public TimeAttackBaseAdapter<T> getAdapter() {
		return adapter;
	}

	public void setAdapter(TimeAttackBaseAdapter<T> adapter) {
		this.adapter = adapter;
	}

	public ViewGroup getContentView() {
		return contentView;
	}

	public void setContentView(ViewGroup contentView) {
		this.contentView = contentView;
	}

	public View getListHeader() {
		return listHeader;
	}

	public void setListHeader(View listHeader) {
		this.listHeader = listHeader;
	}

	public View getListFooter() {
		return listFooter;
	}

	public void setListFooter(View listFooter) {
		this.listFooter = listFooter;
	}

	public ViewGroup getEmptyLayout() {
		return emptyLayout;
	}

	public TextView getTvEmpty() {
		return tvEmpty;
	}
	
}
