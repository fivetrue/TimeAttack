package com.fivetrue.timeattack.activity.manager;

import com.api.common.BaseEntry;
import com.fivetrue.utils.Logger;

import android.content.Context;


abstract public class BaseActivityManager {
	
	protected Context mContext = null;
	protected Class<?> cls = null;
	protected final int INVALID_DATA = -1;
	
	protected BaseActivityManager(Context context, Class<?> clss){
		mContext = context;
		cls = clss;
	}
	
	abstract public void goToActivity(BaseEntry entry);
	
	protected void log(String msg){
		Logger.d(getClass().getName(), msg);
	}
	
	protected void error(String msg){
		Logger.e(getClass().getName(), msg);
	}
	
	protected void warring(String msg){
		Logger.w(getClass().getName(), msg);
	}
}
