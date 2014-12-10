package com.fivetrue.timeattack.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class UserUtils extends BaseUtils{
	
	static private UserUtils instance = null;
	private final int RADIX = 16; 
	private final int LAST_NUMBER_COUNT = 4;
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
		String imei = null;
		if(mTelephonyManager != null){
			imei = mTelephonyManager.getDeviceId();
		}
		return imei;
	}
	
	public String getPhoneNumber(){
		String number = null;
		if(mTelephonyManager != null){
			number = mTelephonyManager.getLine1Number();
		}
		return number;
	}
	
	public String encodePassword(String pass){
		String number = getIemi();
		String lastNumber = null;
		String code = null;
		
		if(number != null){
			lastNumber = getLastNumber(number, LAST_NUMBER_COUNT);
		}
		
		if(lastNumber != null && !TextUtils.isEmpty(pass)){
			code = encodeString(pass, Integer.parseInt(lastNumber, RADIX));
		}
		return code;
	}
	
	public String decodePassword(String code){
		String number = getIemi();
		String lastNumber = null;
		String pass = null;
		
		if(number != null){
			lastNumber = getLastNumber(number, LAST_NUMBER_COUNT);
		}
		
		if(lastNumber != null && !TextUtils.isEmpty(code)){
			pass = decodeString(code, Integer.parseInt(lastNumber, RADIX));
		}
		return pass;
		
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
