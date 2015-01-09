package com.fivetrue.timeattack.login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.fivetrue.timeattack.constants.ActionConstants;
import com.fivetrue.timeattack.preference.UserPreferenceManager;
import com.kakao.APIErrorResult;
import com.kakao.MeResponseCallback;
import com.kakao.UnlinkResponseCallback;
import com.kakao.UserManagement;
import com.kakao.UserProfile;

public class LoginManager {
	
	static public final String KAKAO_USER_DATA = "KAKAO_USER_DATA";
	static public final String KAKAO_USER_ID = "KAKAO_USER_ID";

	static public void requestKakaoUserInfo(final Context context, final MeResponseCallback callback) {
	    UserManagement.requestMe(new MeResponseCallback() {
			
			@Override
			public void onSuccess(UserProfile userProfile) {
				// TODO Auto-generated method stub
				callback.onSuccess(userProfile);
				Intent intent = new Intent(ActionConstants.ACTION_KAKAO_LOGIN_SUCCESS);
				intent.putExtra(LoginManager.KAKAO_USER_DATA, userProfile);
				context.sendBroadcast(intent);
				
				if(userProfile != null){
					UserPreferenceManager pref = UserPreferenceManager.newInstance(context);
					pref.setUserId(String.valueOf(userProfile.getId()));
					pref.setUserName(userProfile.getNickname());
					pref.setUserImageURL(userProfile.getProfileImagePath());
					pref.setUserThumbnailImageURL(userProfile.getThumbnailImagePath());
				}
			}
			
			@Override
			public void onSessionClosedFailure(APIErrorResult errorResult) {
				// TODO Auto-generated method stub
				callback.onSessionClosedFailure(errorResult);
				Toast.makeText(context, errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onNotSignedUp() {
				// TODO Auto-generated method stub
				callback.onNotSignedUp();
			}
			
			@Override
			public void onFailure(APIErrorResult errorResult) {
				// TODO Auto-generated method stub
				callback.onFailure(errorResult);
				Toast.makeText(context, errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	static public void requestUnlinkKakao(final Context context, final UnlinkResponseCallback callbacks){
		UserManagement.requestUnlink(new UnlinkResponseCallback() {
			
			@Override
			public void onSuccess(long userId) {
				// TODO Auto-generated method stub
				callbacks.onSuccess(userId);
				Intent intent = new Intent(ActionConstants.ACTION_KAKAO_UNLINK_SUCCESS);
				intent.putExtra(LoginManager.KAKAO_USER_ID, userId);
				context.sendBroadcast(intent);
				
				UserPreferenceManager pref = UserPreferenceManager.newInstance(context);
				pref.setUserId("");
				pref.setUserName("");
				pref.setUserImageURL("");
				pref.setUserThumbnailImageURL("");
			}
			
			@Override
			public void onSessionClosedFailure(APIErrorResult errorResult) {
				// TODO Auto-generated method stub
				callbacks.onSessionClosedFailure(errorResult);
			}
			
			@Override
			public void onFailure(APIErrorResult errorResult) {
				// TODO Auto-generated method stub
				callbacks.onFailure(errorResult);
				
			}
		});
	}
}
