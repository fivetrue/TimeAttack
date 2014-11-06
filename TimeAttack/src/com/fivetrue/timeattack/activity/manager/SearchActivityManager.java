package com.fivetrue.timeattack.activity.manager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.api.common.BaseEntry;
import com.fivetrue.timeattack.activity.SearchLocationActivity;

public class SearchActivityManager extends BaseActivityManager{
	public static String SEARCH_DATA  = "SEARCH_DATA";
	
	static public SearchActivityManager newInstance(Context context){
		return new SearchActivityManager(context);
	}
	
	private SearchActivityManager(Context context){
		super(context, SearchLocationActivity.class);
	}
	
	@Override
	public void goToActivity(BaseEntry entry) {
		// TODO Auto-generated method stub
		Intent i = new Intent(mContext, cls);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle b = new Bundle();
		b.putParcelable(SEARCH_DATA, entry);
		i.putExtras(b);
		mContext.startActivity(i);
	}
}
