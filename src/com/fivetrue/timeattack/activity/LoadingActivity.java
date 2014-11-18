package com.fivetrue.timeattack.activity;

import com.fivetrue.timeattack.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initModels();
		startApplication();
		
	}
	
	public void initModels(){
		new MyApplication(getApplication());
	}
	
	public void startApplication(){
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}
}
