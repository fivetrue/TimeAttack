package com.fivetrue.timeattack.dialog;

import java.util.ArrayList;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.model.DialogListMenuItem;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class ListMenuDialog extends CustomDialog {
	
	ViewGroup mContentView = null;
	ViewGroup mLayoutMenuList = null;
	ArrayList<DialogListMenuItem> mArrMenuList = null;

	public ListMenuDialog(Context context, ArrayList<DialogListMenuItem> menuList) {
		// TODO Auto-generated constructor stub
		super(context, menuList);
		setTitle(R.string.current_loaction);
		setVisibleOkButton(false);
		setCanceledOnTouchOutside(false);
	}
	@Override
	protected View addChildView(LayoutInflater inflater, Object obj) {
		// TODO Auto-generated method stub
		if(obj != null){
			mArrMenuList = (ArrayList<DialogListMenuItem>) obj;
		}
		
		mContentView = (ViewGroup) inflater.inflate(R.layout.dialog_menu_list, null);
		mLayoutMenuList = (ViewGroup) mContentView.findViewById(R.id.layout_menu_list);
		
		if(mArrMenuList != null){
			for(DialogListMenuItem item : mArrMenuList){
				if(item != null){
					TextView tv = new TextView(getContext(), null, R.style.MyDialog_MenuText);
					tv.setTextAppearance(getContext(), R.style.MyDialog_MenuText);
					tv.setText(item.getName());
					tv.setGravity(Gravity.CENTER);
					tv.setPadding((int) getContext().getResources().getDimension(R.dimen.dialog_text_padding),
							0, (int) getContext().getResources().getDimension(R.dimen.dialog_text_padding), 0);
					tv.setOnClickListener(item.getOnClickListener());
					mLayoutMenuList.addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					View line = new View(getContext());
					mLayoutMenuList.addView(line, new LayoutParams(LayoutParams.MATCH_PARENT, (int)getContext().getResources().getDimension(R.dimen.list_underline_height)));
				}
			}
		}
		return mContentView;
	}
}
