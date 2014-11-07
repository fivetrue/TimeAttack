package com.fivetrue.timeattack.dialog;


import com.fivetrue.timeattack.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CustomDialog extends Dialog {
	
	private ViewGroup mLayoutButton = null;
	private ViewGroup mLayoutContent = null;
	private TextView mTvTitle = null;
	private TextView mTvContent = null;
	private TextView mTvOk = null;
	private TextView mTvCancel = null;
	
	private LayoutInflater mInflater = null;

	public CustomDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.dialog_custom);
        
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView(mInflater);
	}
	
	private void initView(LayoutInflater inflater){
		mLayoutContent = (ViewGroup) findViewById(R.id.layout_dialog_content);
		mLayoutButton = (ViewGroup) findViewById(R.id.layout_dialog_button);
		
		mTvTitle = (TextView) findViewById(R.id.tv_dialog_title);
		mTvContent = (TextView) findViewById(R.id.tv_dialog_content);
		mTvOk = (TextView) findViewById(R.id.tv_dialog_ok);
		mTvCancel = (TextView) findViewById(R.id.tv_dialog_cancel);
		
		View addView = addChildView(mInflater);
		if(addView != null){
			mLayoutContent.addView(addView, 0);
			mTvContent.setVisibility(View.GONE);
		}
		
		mTvCancel.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isShowing()){
					dismiss();
				}
			}
		});
	}
	
	protected View addChildView(LayoutInflater inflater){
		return null;
	}
	
	public void setOnClickOkButtonListener(android.view.View.OnClickListener onClick){
		mTvOk.setOnClickListener(onClick);
	}
	
	public void setOnClickCancelButtonListener(android.view.View.OnClickListener onClick){
		mTvCancel.setOnClickListener(onClick);
	}
	
	public void setTitle(int resId){
		mTvTitle.setText(resId);
	}
	
	public void setTitle(String title){
		mTvTitle.setText(title);
	}
	
	public void setContentMessage(int resId){
		mTvContent.setText(resId);
	}
	
	public void setContentMessage(String title){
		mTvContent.setText(title);
	}
	
	public void setVisibleButtons(boolean visible){
		int v = visible ? View.VISIBLE : View.GONE;
		mLayoutButton.setVisibility(v);
	}
	
	public void setVisibleOkButton(boolean visible){
		int v = visible ? View.VISIBLE : View.GONE;
		mTvOk.setVisibility(v);
	}
	
	public void setVisibleCancelButton(boolean visible){
		int v = visible ? View.VISIBLE : View.GONE;
		mTvCancel.setVisibility(v);
	}
}
