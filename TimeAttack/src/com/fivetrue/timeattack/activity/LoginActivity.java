package com.fivetrue.timeattack.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startApplication();
		
	}
	
	private boolean checkLoginStatus(){
		
		return false;
	}
	
	public void startApplication(){
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}
}
