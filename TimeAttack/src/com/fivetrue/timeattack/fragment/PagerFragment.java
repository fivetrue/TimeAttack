package com.fivetrue.timeattack.fragment;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.BaseFragment;
import com.fivetrue.timeattack.view.pager.CustomViewPager;
import com.fivetrue.timeattack.view.pager.CustomViewPager.OnSwipeDirectionListener;
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

	private ArrayList<BaseFragment> mFragmentList = new ArrayList<BaseFragment>();
	private ArrayList<String> mTitleList = new ArrayList<String>();

	private Typeface roboto_bold = null;
	private Typeface roboto_light = null;

	private int mLayoutWidth = INVALID_VALUE;
	private int mIndex = INVALID_VALUE;
	private int mResHorizontalColor = R.color.main_primary_dark_color;
	
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
			for(BaseFragment f : mFragmentList){
				View v = new View(getActivity());
				v.setBackground(getResources().getDrawable(R.drawable.index_dot));
				LinearLayout.LayoutParams params = new LayoutParams((int) getResources().getDimension(R.dimen.index_dot_size)
						, (int) getResources().getDimension(R.dimen.index_dot_size));
				params.leftMargin = (int) getResources().getDimension(R.dimen.index_dot_margin);
				params.rightMargin = (int) getResources().getDimension(R.dimen.index_dot_margin);
				mIndexViewGroup.addView(v, params);
				f.setCustomActionBar(getCustomActionBar());
			}

			mIndex = mIndex == INVALID_VALUE ? 0 : mIndex;

			mIndexViewGroup.getChildAt(mIndex).setSelected(true);

			if(mOnSelectedFragmentName != null && mTitleList != null){
				mOnSelectedFragmentName.onReceiveFragmentName(mTitleList.get(mIndex));
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
		mViewPager.setOnSwipeDirectionListener(mSwipeDirectionListener);
	}

	public void addFragment(BaseFragment f, String title){
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
			
			mIndex = position;
			
			if(mHorizontalScroll != null){
				float destination = mHorizontalScroll.getWidth() * position;
				ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(mHorizontalScroll, "translationX", mHorizontalScroll.getX(), destination);
				objectAnimator.setDuration(200);
				objectAnimator.start();
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
				float destination = mHorizontalScroll.getWidth() * mViewPager.getCurrentItem();
				ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(mHorizontalScroll, "translationX", mHorizontalScroll.getX(), destination);
				objectAnimator.setDuration(200);
				objectAnimator.start();
				break;
			}
		}
	};
	
	private OnSwipeDirectionListener mSwipeDirectionListener = new OnSwipeDirectionListener() {
		
		@Override
		public void onSwipeRight(MotionEvent event) {
			// TODO Auto-generated method stub
			
			if(mFragmentList == null || mFragmentList.size() <= 0 || mHorizontalScroll == null){
				return;
			}
			
			if(mHorizontalScroll.getX() >= 0){
				mHorizontalScroll.setX(mHorizontalScroll.getX() - getResources().getDisplayMetrics().density);
			}
		}
		
		@Override
		public void onSwipeLeft(MotionEvent event) {
			// TODO Auto-generated method stub
			
			if(mFragmentList == null || mFragmentList.size() <= 0 || mHorizontalScroll == null){
				return;
			}
			
			if(mHorizontalScroll.getX() <= mLayoutWidth - mHorizontalScroll.getWidth()){
				mHorizontalScroll.setX(mHorizontalScroll.getX() + getResources().getDisplayMetrics().density);
			}
		}

		@Override
		public void onSwipeComplete(MotionEvent event) {
			// TODO Auto-generated method stub
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

	public ArrayList<BaseFragment> getFragmentList() {
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

