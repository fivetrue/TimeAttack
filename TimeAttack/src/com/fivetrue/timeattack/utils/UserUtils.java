package com.fivetrue.timeattack.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class UserUtils extends BaseUtils{
	
	static private UserUtils instance = null;
	private TelephonyManager mTelephonyManager = null; 
	
	static public UserUtils getInstance(Context context){
		if(instance != null){
			return instance;
		}else{
			instance = new UserUtils(context);
			return instance;
		}
	}
	
	public String getIemi(){
		String imei = "";
		if(mTelephonyManager != null){
			imei = mTelephonyManager.getDeviceId();
		}
		return imei;
	}
	
	public String getPhoneNumber(){
		String number = "";
		if(mTelephonyManager != null){
			number = mTelephonyManager.getLine1Number();
		}
		return number;
	}
	
	private UserUtils(Context context){
		super(context);
		mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return UserUtils.class.getSimpleName();
	}
}
