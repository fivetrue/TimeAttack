package com.fivetrue.timeattack.preference;

import android.content.Context;
import android.text.TextUtils;

import com.fivetrue.preference.SharedPrefrenceManager;
import com.fivetrue.timeattack.utils.UserUtils;
import com.fivetrue.utils.Logger;

public class UserPreferenceManager extends SharedPrefrenceManager {

	static private final String PREFERENCE_NAME = "UserPreferenceManager";
	private final String USER_ID = "USER_ID";
	private final String USER_PASS = "USER_PASS";
	private final String USER_IMAGE_URL = "USER_IMAGE_URL";
	private final String USER_THUMBNAIL_URL = "USER_THUMBNAIL_URL";
	private final String USER_NAME = "USER_NAME";
	private final String USER_INFO = "USER_INFO";
	
	
	static public UserPreferenceManager newInstance(Context context){
		return new UserPreferenceManager(context, PREFERENCE_NAME);
	}
	private UserPreferenceManager(Context context, String name) {
		super(context, name);
		// TODO Auto-generated constructor stub
	}
	
	public void setUserId(String id){
		savePref(USER_ID, id);
	}
	
	public String getUserId(){
		return loadStringPref(USER_ID, "");
	}
	
	public void setUserPass(String pass){
		String code = UserUtils.getInstance(getContext()).decodePassword(pass);
		if(TextUtils.isEmpty(code)){
			Logger.e(PREFERENCE_NAME, "user Password is null or empty");
		}else{
			savePref(USER_PASS, pass);
		}
	}
	
	public String getUserPass(){
		return loadStringPref(USER_PASS, "");
	}
	
	public void setUserImageURL(String imageUrl){
		savePref(USER_IMAGE_URL, imageUrl);
	}
	
	public String getUserImageURL(){
		return loadStringPref(USER_IMAGE_URL, "");
	}
	
	public void setUserThumbnailImageURL(String imageUrl){
		savePref(USER_THUMBNAIL_URL, imageUrl);
	}
	
	public String getUserThumbnailImageURL(){
		return loadStringPref(USER_THUMBNAIL_URL, "");
	}
	
	public void setUserName(String name){
		savePref(USER_NAME, name);
	}
	
	public String getUserName(){
		return loadStringPref(USER_NAME, "");
	}
	
	public void setUserInfo(String info){
		savePref(USER_INFO, info);
	}
	
	public String getUserInfo(){
		return loadStringPref(USER_INFO, "");
	}
}
