package com.fivetrue.timeattack.fragment;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.view.actionbar.CustomActionBar;

import android.support.v4.app.Fragment;
import android.widget.Toast;


abstract public class BaseFragment extends Fragment{
	
	private CustomActionBar mCustomActionBar = null;
	
	public void showNetworkFailToast(){
		if(getActivity() != null){
			Toast.makeText(getActivity(), R.string.error_network_request_fail, Toast.LENGTH_SHORT).show();
		}
	}

	protected void makeToast(String str){
		if(getActivity() != null){
			Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void makeToast(int resId){
		if(getActivity() != null){
			Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
		}

	}
	
	public void setCustomActionBar(CustomActionBar actionbar)
	{
		mCustomActionBar = actionbar;
	}

	public CustomActionBar getCustomActionBar() {
		return mCustomActionBar;
	}
}
