package com.fivetrue.timeattack.fragment.tab;

import java.util.ArrayList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.BaseFragment;
import com.fivetrue.timeattack.view.pager.CustomViewPager;
import com.fivetrue.timeattack.view.pager.adapter.CustomFragmentPagerAdapter;

public class TabFragment extends BaseFragment {

	private final int INVALID_VALUE = -1;

	private LinearLayout mContentView = null;
	private LayoutParams mContentViewLayoutParam = null;

	private LinearLayout mTabLayout = null;
	private LayoutParams mTabLayoutParam = null;

	private FrameLayout mContentLayout = null;
	private LayoutParams mContentLayoutParam = null;

	private View mAnimationTab = null;
	private LayoutParams mAnimaitonTabLayoutParam = null;

	private int mTabWidth = INVALID_VALUE;
	private int mTabHeight = INVALID_VALUE;

	private int mLayoutWidth = INVALID_VALUE;
	private int mLayoutHeight = INVALID_VALUE;


	private CustomViewPager mViewPager = null;
	private CustomFragmentPagerAdapter mPagerAdapter = null;

	private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
	private ArrayList<String> mTitleList = new ArrayList<String>();
	private ArrayList<TextView> mTextViewList = new ArrayList<TextView>();

	private Typeface roboto_bold = null;
	private Typeface roboto_light = null;

	public TabFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initAssets();
		initViewData();
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
		mLayoutHeight = getResources().getDisplayMetrics().heightPixels;

		mTabWidth = getResources().getDisplayMetrics().widthPixels;
		mTabHeight = (int) getResources().getDimension(R.dimen.tab_height);
	}

	private void initView(){
		mContentView = new LinearLayout(getActivity());
		mContentView.setOrientation(LinearLayout.VERTICAL);
		mContentViewLayoutParam = new LayoutParams(mLayoutWidth, mLayoutHeight);
		mContentView.setLayoutParams(mContentViewLayoutParam);
		mContentView.setBackgroundColor(getResources().getColor(R.color.drawer_item_background_n));

		mTabLayout = new LinearLayout(getActivity());
		mTabLayout.setOrientation(LinearLayout.HORIZONTAL);
		mTabLayout.setBackgroundColor(getResources().getColor(R.color.drawer_item_background_n));
		mTabLayoutParam = new LayoutParams(mTabWidth, mTabHeight);
		mTabLayout.setLayoutParams(mTabLayoutParam);
		mTabLayout.setPadding((int) getResources().getDimension(R.dimen.tab_side_padding),
				0, (int) getResources().getDimension(R.dimen.tab_side_padding), 0);

		mContentLayout = new FrameLayout(getActivity());
		mContentLayoutParam = new LayoutParams(mLayoutWidth, mLayoutHeight - mTabHeight);
		mContentLayout.setLayoutParams(mContentLayoutParam);

		mAnimationTab = new View(getActivity());
		mAnimationTab.setBackground(getResources().getDrawable(R.drawable.tab_sel_white));
		mAnimaitonTabLayoutParam = new LayoutParams((int)getResources().getDimension(R.dimen.tab_height), (int)getResources().getDimension(R.dimen.tab_animation_height));
		mAnimationTab.setLayoutParams(mAnimaitonTabLayoutParam);
		
		mViewPager = new CustomViewPager(getActivity());
		mViewPager.setId(0x009900);

		View shadowView = new View(getActivity());
		shadowView.setBackground(getResources().getDrawable(R.drawable.tab_img_shadow_pattern));

		mContentLayout.addView(mViewPager, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));

		mContentLayout.addView(shadowView, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				3 * (int)getResources().getDisplayMetrics().density));

		mContentView.addView(mTabLayout);
		mContentView.addView(mAnimationTab);
		mContentView.addView(mContentLayout);
	}

	private void initModel(){

		makeTabLayoutTitle(mTabLayout);
		mPagerAdapter = new CustomFragmentPagerAdapter(getFragmentManager(), mFragmentList);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
	}

	private void makeTabLayoutTitle(ViewGroup parent){
		if(mTitleList != null && parent != null){
			for(int i = 0 ; i < mTitleList.size() ; i ++){
				addTextView(parent, mTitleList.get(i));

				if(i != mTitleList.size() - 1){
					View line = new View(getActivity());
					line.setBackgroundColor(getResources().getColor(R.color.drawer_text_color_n));
					LayoutParams param = new LayoutParams(1 , LayoutParams.MATCH_PARENT);
					param.bottomMargin = (int) getResources().getDimension(R.dimen.tab_divider_margin);
					param.topMargin = (int) getResources().getDimension(R.dimen.tab_divider_margin);
					line.setLayoutParams(param);
					parent.addView(line);
				}
			}
		}

		mTextViewList.get(0).setSelected(true);
		mAnimationTab.setX(mTextViewList.get(0).getX());

		for(int i = 0 ; i < mTextViewList.size() ; i ++){
			final int position = i;
			mTextViewList.get(position).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mViewPager.setCurrentItem(position, true);

				}
			});
		}

	}

	private void addTextView(ViewGroup parent, String text){
		if(getActivity() == null || parent == null || TextUtils.isEmpty(text))
			return;

		TextView tv = new TextView(getActivity());
		tv.setText(text);
		tv.setTextSize(getResources().getDimension(R.dimen.tab_text_size));
		tv.setTextColor(getResources().getColorStateList(R.color.selector_drawer_text_color));
		tv.setTypeface(roboto_light);
		tv.setGravity(Gravity.CENTER);
		tv.setPadding((int) getResources().getDimension(R.dimen.tab_text_side_padding),
				0, (int) getResources().getDimension(R.dimen.tab_text_side_padding), 0);
		tv.setBackground(getResources().getDrawable(R.drawable.selector_drawer_layout));
		mTextViewList.add(tv);
		parent.addView(tv, new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
	}

	public void addFragment(Fragment f, String title){
		mTitleList.add(title);
		mFragmentList.add(f);
	}


	private boolean nowDragging = false;
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			if(mTextViewList != null && mTextViewList.size() > 0){
				for(TextView view : mTextViewList){
					view.setSelected(false);
				}
				mTextViewList.get(position).setSelected(true);
				mAnimationTab.setX(mTextViewList.get(position).getX());
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub
			if(mTextViewList != null && mTextViewList.size() > 0 && mViewPager != null){
				int currentPosition = mViewPager.getCurrentItem();
				TextView tv = mTextViewList.get(position);

				if(tv == null)
					return ;

				if(nowDragging){
					if(position == currentPosition && positionOffset > 0){
						//swipe right
						mAnimationTab.setX(mAnimationTab.getX() + positionOffset);
					}else if (positionOffset > 0){
						//swipe left
						mAnimationTab.setX(mAnimationTab.getX() - positionOffset);
					}
					mAnimaitonTabLayoutParam.width = tv.getWidth();
				}
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			
			if(mViewPager == null || mTextViewList == null || mTextViewList.size() <= 0)
				return;
			TextView tv = mTextViewList.get(mViewPager.getCurrentItem());
			switch(state){
			case CustomViewPager.SCROLL_STATE_IDLE :
				nowDragging = false;
				mAnimationTab.setX(tv.getX());
				mAnimaitonTabLayoutParam.width = tv.getWidth();
				break;

			case CustomViewPager.SCROLL_STATE_DRAGGING :
				nowDragging = true;
				break;

			case CustomViewPager.SCROLL_STATE_SETTLING :
				nowDragging = false;
				mAnimationTab.setX(tv.getX());
				mAnimaitonTabLayoutParam.width = tv.getWidth();
				break;
			}
		}

	};
}
