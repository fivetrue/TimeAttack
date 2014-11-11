package com.fivetrue.timeattack.view.pager;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
	public interface OnSwipeDirectionListener{
		public void onSwipeLeft(MotionEvent event);
		public void onSwipeRight(MotionEvent event);
		public void onSwipeComplete(MotionEvent event);
	}

	private OnSwipeDirectionListener mOnSwipeDirectionListener = null;
	
	private boolean isTouch = false;
	private Point mLastTouchPoint = new Point();
	
	public CustomViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(mOnSwipeDirectionListener == null){
			return super.onTouchEvent(event);
		}
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN :
			isTouch = true;
			mLastTouchPoint.x = (int) event.getX();
			mLastTouchPoint.y = (int) event.getY();
			break;
			
		case MotionEvent.ACTION_MOVE :
			
			if(isTouch){
				if(mLastTouchPoint.x < event.getX()){
					mOnSwipeDirectionListener.onSwipeRight(event);
				}else{
					mOnSwipeDirectionListener.onSwipeLeft(event);
				}
				mLastTouchPoint.x = (int) event.getX();
				mLastTouchPoint.y = (int) event.getY();
			}
			break;
			
		case MotionEvent.ACTION_UP :
			mOnSwipeDirectionListener.onSwipeComplete(event);
			isTouch = false;
			break;
		}
		
		return super.onTouchEvent(event);
	}
	
	public void setOnSwipeDirectionListener(OnSwipeDirectionListener listener){
		mOnSwipeDirectionListener = listener;
	}
}
