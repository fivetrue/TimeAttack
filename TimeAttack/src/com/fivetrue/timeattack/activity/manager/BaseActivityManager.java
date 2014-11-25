package com.fivetrue.timeattack.activity.manager;

import com.api.common.BaseEntry;
import com.fivetrue.timeattack.R;
import com.fivetrue.utils.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


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
	
	static public void startActivity(Context context, Class<?> cls){
		startActivity(context, cls, false, null, null);
	}
	
	static public void startActivity(Context context, Intent intent){
		startActivity(context, null, false, null, intent);
	}

	static public void startActivity(Context context, Class<?> cls , boolean bringToTop){
		startActivity(context, cls, bringToTop, null, null);
	}

	static public void startActivity (Context context, Class<?> cls , boolean bringToTop, Bundle b, Intent intent){
		
		if(intent == null){
			intent = new Intent(context, cls);
		}
		
		if(bringToTop){
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		}
		if(b != null){
			intent.putExtras(b);
		}
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_slide_in_right);
	}
}
