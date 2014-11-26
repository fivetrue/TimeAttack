package com.fivetrue.timeattack.activity.manager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.api.common.BaseEntry;
import com.fivetrue.timeattack.activity.NearbyActivity;

public class NearbyActivityManager extends BaseActivityManager{
	public static String NEARBY_DATA  = "NEARBY_DATA";
	
	static public NearbyActivityManager newInstance(Context context){
		return new NearbyActivityManager(context);
	}
	
	private NearbyActivityManager(Context context){
		super(context, NearbyActivity.class);
	}
	
	@Override
	public void goToActivity(BaseEntry entry) {
		// TODO Auto-generated method stub
		Intent i = new Intent(mContext, cls);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle b = new Bundle();
		b.putParcelable(NEARBY_DATA, entry);
		i.putExtras(b);
		startActivity(mContext, i);
	}
}
