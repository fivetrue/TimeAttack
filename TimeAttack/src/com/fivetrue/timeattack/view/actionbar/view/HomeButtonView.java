package com.fivetrue.timeattack.view.actionbar.view;

import com.fivetrue.timeattack.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class HomeButtonView extends View {

	private final int INVALID_VALUE = -1;
	private final int MOVING_VALUE = 150;
	private final int CIRCLE_HALF_DEGREE = 180;

	private int mStrokWidth = 2;
	private float mDensity = INVALID_VALUE;

	private boolean isHomeAsUp = false;
	private boolean isRevert = false;
	private float mValue = INVALID_VALUE;
	
	private int mMovingValue = INVALID_VALUE;
	private Paint mPaint = null;
	
	private int mLineColorRes = R.color.main_primary_light_color;

	public HomeButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		initModel();
		initPaint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(Color.TRANSPARENT);
		if(mValue == INVALID_VALUE){
			mValue = 0;
		}
		if(isHomeAsUp){
			onDrawBack(canvas);
		}else{
			onDrawHome(canvas);
		}
	}
	
	private void initModel(){
		mDensity = getContext().getResources().getDisplayMetrics().density;
		mMovingValue =  MOVING_VALUE * (int) mDensity;
	}

	private void initPaint(){
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(getContext().getResources().getColor(mLineColorRes));
		mPaint.setStrokeWidth(mStrokWidth * mDensity);
	}

	private void onDrawHome(Canvas canvas){
		
		float moveValue = ((mValue * mMovingValue) / getWidth()) * mDensity;
		canvas.drawLine(moveValue, mDensity, getWidth(), moveValue + mDensity, mPaint);
		canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
		canvas.drawLine(moveValue, getHeight() - mDensity, getWidth(), (getHeight() - mDensity) - moveValue, mPaint);
		
		float rotateValue = mValue * CIRCLE_HALF_DEGREE;
		rotateValue = rotateValue * (isRevert ? -1 : 1);
		setRotation(rotateValue);
	}

	private void onDrawBack(Canvas canvas){
		
		float moveValue = ((mValue * mMovingValue) / getWidth()) * mDensity;
	
		canvas.drawLine(getWidth() - mDensity, mDensity,
				(getWidth() / 2) - moveValue , (getHeight() / 2) + moveValue + 1, mPaint);
		canvas.drawLine(getWidth() - mDensity , getHeight() - mDensity,
				getWidth() / 2 - moveValue, (getHeight() / 2) - moveValue - 1, mPaint);
		
		float rotateValue = mValue * CIRCLE_HALF_DEGREE;
		rotateValue = rotateValue * (isRevert ? -1 : 1);
		setRotation(rotateValue);

	}

	public void setHomeAsUp(boolean isHomeAsUp){
		this.isHomeAsUp = isHomeAsUp;
		invalidate();
	}

	public void setAnimaitonValue(float value){
		mValue = value;
		invalidate();
	}

	public void setRevert(boolean isRevert) {
		this.isRevert = isRevert;
	}
	
	public void setLineColorRes(int res){
		mLineColorRes = res;
		initPaint();
	}
	
	
}
