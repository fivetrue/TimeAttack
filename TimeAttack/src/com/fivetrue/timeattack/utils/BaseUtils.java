package com.fivetrue.timeattack.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

abstract public class BaseUtils {
	
	protected Context mContext = null;
	
	public String encodeString(String str, int flag){
		String code = "";
		if(str != null){
			code = Base64.encodeToString(str.getBytes(), flag);
		}
		return code;
	}
	
	public String decodeString(String encode, int flag){
		String str = "";
		if(encode != null){
			byte[] bytes = Base64.decode(encode, flag);
			str = new String(bytes);
		}
		return str;
	}
	
	protected BaseUtils(Context context){
		mContext = context;
	}
	
	protected String getLastNumber(String number, int count){
		if(!TextUtils.isEmpty(number)){
			return number.substring(number.length() - count, number.length());
		}
		return null;
	}
	
	abstract public String getClassName();
}
