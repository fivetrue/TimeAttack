package com.fivetrue.timeattack.ga;

import com.google.android.gms.analytics.Logger.LogLevel;
import com.google.android.gms.analytics.*;

import android.content.Context;


public class GoogleAnalyticsManager {

	private Context context;
	private Tracker mTracker;
	private GoogleAnalytics mGaInstance;
	
	private final String mUserID = "UA-58423167-1";

	public GoogleAnalyticsManager(Context context) {
		if (context == null) {
			return;
		}
		this.context = context;
		mGaInstance = GoogleAnalytics.getInstance(this.context);
		mGaInstance.getLogger().setLogLevel(LogLevel.VERBOSE);
		mTracker = mGaInstance.newTracker(mUserID);
	}

	public void sendView(String viewName) {
		mTracker.setScreenName(viewName);
		mTracker.send(new HitBuilders.AppViewBuilder().build());
	}
	
	public void sendEven(String viewName , String paramName, String param){
		mTracker.setScreenName(viewName);
		mTracker.send(new HitBuilders.EventBuilder(paramName, param).build());
	}
	
	public Tracker getTracker() {
		return mTracker;
	}
	
	public GoogleAnalytics getGAInstance(){
		return mGaInstance;
	}
}
