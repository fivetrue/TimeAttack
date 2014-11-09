package com.fivetrue.timeattack.fragment;

import java.util.ArrayList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.BaseFragment;
import com.fivetrue.timeattack.view.pager.CustomViewPager;
import com.fivetrue.timeattack.view.pager.adapter.CustomFragmentPagerAdapter;

public class PagerFragment extends BaseFragment {

	public interface OnSelectedFragmentNameListener{
		public void onReceiveFragmentName(String name);
	}

	static public String PAGE_INDEX = "PAGE_INDEX";
	private int mViewPagerId = 0x009900;

	private final int INVALID_VALUE = -1;

	private ViewGroup mContentView = null;
	private ViewGroup mHorizontalScrollViewGroup = null;
	private ViewGroup mContentViewGroup = null;
	private ViewGroup mIndexViewGroup = null;

	private View mHorizontalScroll = null;

	private CustomViewPager mViewPager = null;
	private CustomFragmentPagerAdapter mPagerAdapter = null;

	private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
	private ArrayList<String> mTitleList = new ArrayList<String>();

	private Typeface roboto_bold = null;
	private Typeface roboto_light = null;

	private int mLayoutWidth = INVALID_VALUE;
	private int mIndex = INVALID_VALUE;
	private int mResHorizontalColor = R.color.gorditas_blue;

	private OnSelectedFragmentNameListener mOnSelectedFragmentName = null;

	public PagerFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContentView = (ViewGroup) inflater.inflate(R.layout.fragment_pager, null);
		initArguments();
		initViewData();
		initAssets();
		initView();
		initModel();
		return mContentView;
	}

	private void initAssets(){
		roboto_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
		roboto_light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
	}

	private void initViewData(){
		mLayoutWidth = getResources().getDisplayMetrics().widthPixels;
	}

	private void initArguments(){
		Bundle b = getArguments();

		if(b != null){
			mIndex = b.getInt(PAGE_INDEX, INVALID_VALUE);
		}
	}

	private void initView(){

		mIndexViewGroup = (ViewGroup) mContentView.findViewById(R.id.layout_index);
		mHorizontalScrollViewGroup = (ViewGroup) mContentView.findViewById(R.id.layout_horizontal_scroll);
		mContentViewGroup = (ViewGroup) mContentView.findViewById(R.id.layout_content);

		mViewPager = new CustomViewPager(getActivity());
		mViewPager.setId(mViewPagerId);

		mContentViewGroup.addView(mViewPager, 0, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));

		if(mFragmentList != null && mFragmentList.size() > 0){
			for(int i = 0 ; i < mFragmentList.size() ; i ++){
				View v = new View(getActivity());
				v.setBackground(getResources().getDrawable(R.drawable.index_dot));
				LinearLayout.LayoutParams params = new LayoutParams((int) getResources().getDimension(R.dimen.index_dot_size)
						, (int) getResources().getDimension(R.dimen.index_dot_size));
				params.leftMargin = (int) getResources().getDimension(R.dimen.index_dot_margin);
				params.rightMargin = (int) getResources().getDimension(R.dimen.index_dot_margin);
				mIndexViewGroup.addView(v, params);
			}

			int index = mIndex == INVALID_VALUE ? 0 : mIndex;

			mIndexViewGroup.getChildAt(index).setSelected(true);

			if(mOnSelectedFragmentName != null && mTitleList != null){
				mOnSelectedFragmentName.onReceiveFragmentName(mTitleList.get(index));
			}

			mHorizontalScroll = new View(getActivity());
			mHorizontalScroll.setBackground(getResources().getDrawable(mResHorizontalColor));
			mHorizontalScrollViewGroup.addView(mHorizontalScroll, mLayoutWidth / mFragmentList.size(), android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		}
	}

	private void initModel(){

		mViewPager.setOffscreenPageLimit(mFragmentList.size());
		mPagerAdapter = new CustomFragmentPagerAdapter(getFragmentManager(), mFragmentList);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
		mContentViewGroup.setOnTouchListener(mPagerTouchListener);
	}

	public void addFragment(Fragment f, String title){
		mTitleList.add(title);
		mFragmentList.add(f);
	}


	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub

			if(mIndexViewGroup != null && mIndexViewGroup.getChildCount() > 0 ){
				for(int i = 0 ; i < mIndexViewGroup.getChildCount() ; i ++){
					mIndexViewGroup.getChildAt(i).setSelected(false);
				}
			}
			mIndexViewGroup.getChildAt(position).setSelected(true);

			if(mOnSelectedFragmentName != null && mTitleList != null){
				mOnSelectedFragmentName.onReceiveFragmentName(mTitleList.get(position));
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub

			switch(state){
			case CustomViewPager.SCROLL_STATE_IDLE :
				break;

			case CustomViewPager.SCROLL_STATE_DRAGGING :
				break;

			case CustomViewPager.SCROLL_STATE_SETTLING :
				break;
			}
		}
	};
	
	private OnTouchListener mPagerTouchListener = new OnTouchListener() {
		private boolean isTouch = false;
		private float touchX = INVALID_VALUE;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			
			if(mFragmentList == null || mFragmentList.size() <= 0){
				return false;
			}
			
			if(mHorizontalScroll != null && mHorizontalScroll.getX() >= 0 && (mHorizontalScroll.getX() <= mLayoutWidth - mHorizontalScroll.getWidth())){
				return false;
			}

				
			
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN :
				isTouch = true;
				touchX = event.getX();
				return true;
				
			case MotionEvent.ACTION_MOVE :
				//Right
				if(isTouch){
					if(touchX > event.getX()){
						mHorizontalScroll.setX(mHorizontalScroll.getX() -  
								1);
					}
					//Left
					else{
						mHorizontalScroll.setX(mHorizontalScroll.getX() +  
								1);
					}
					return true;
				}
				return false;
				
			case MotionEvent.ACTION_UP :
				isTouch = false;
				return true;
			
			}
			
			return false;
		}
	};

	public int getStatusBarHeight() { 
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		} 
		return result;
	}

	public ArrayList<Fragment> getFragmentList() {
		return mFragmentList;
	}

	public OnSelectedFragmentNameListener getOnSelectedFragmentName() {
		return mOnSelectedFragmentName;
	}

	public void setOnSelectedFragmentName(
			OnSelectedFragmentNameListener ll) {
		this.mOnSelectedFragmentName = ll;
	} 

}

