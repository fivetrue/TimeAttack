package com.fivetrue.timeattack;

import com.fivetrue.network.VolleyInstance;
import com.fivetrue.timeattack.utils.ImageUtils;

import android.app.Application;

public class MyApplication extends Application {
	
	static public Application application = null;
	static public ImageUtils imageUtils = null;
	
	public MyApplication(Application app) {
		// TODO Auto-generated constructor stub
		VolleyInstance.init(app.getApplicationContext());
		application = app;
		imageUtils = ImageUtils.getInstance(app);
	}
	
}
