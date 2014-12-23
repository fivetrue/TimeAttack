package com.fivetrue.timeattack.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBaseData extends BaseModel {
	
	protected String userId = null;
	protected String userName = null;
	
	public UserBaseData(){};

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(userId);
		dest.writeString(userName);
	}
	
	public UserBaseData(Parcel source){
		userId = source.readString();
		userName = source.readString();
	}
	
	public static final Parcelable.Creator<UserBaseData> CREATOR = new Parcelable.Creator<UserBaseData>() {
		@Override
		public UserBaseData createFromParcel(Parcel source) {
			return new UserBaseData(source);
		}

		@Override
		public UserBaseData[] newArray(int size) {
			return new UserBaseData[size];
		}
	};

}
