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
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.fivetrue.timeattack.R;
import com.fivetrue.timeattack.fragment.BaseFragment;
import com.fivetrue.timeattack.view.pager.CustomViewPager;
import com.fivetrue.timeattack.view.pager.adapter.CustomFragmentPagerAdapter;

public class TabFragment extends BaseFragment {
	
	private int mViewPagerId = 0x009900;
	
	private class ViewHolder{
		ViewGroup layoutHolder = null;
		TextView tvTitle = null;
		View vUnderline = null;
	}

	private final int INVALID_VALUE = -1;
	private final int ALPHA_ANI_DURAION = 100;

	private LinearLayout mContentView = null;
	private LayoutParams mContentViewLayoutParam = null;

	private LinearLayout mTabLayout = null;
	private HorizontalScrollView mTabScrollView = null;
	private LayoutParams mTabLayoutParam = null;

	private FrameLayout mContentLayout = null;
	private LayoutParams mContentLayoutParam = null;


	private int mTabWidth = INVALID_VALUE;
	private int mTabHeight = INVALID_VALUE;
	private int mTabUnderlineHeight = INVALID_VALUE;
	private int mTabUnderlineWidth = INVALID_VALUE;

	private int mLayoutWidth = INVALID_VALUE;
	private int mLayoutHeight = INVALID_VALUE;
	private int mActionBarHeight = INVALID_VALUE;
	

	private CustomViewPager mViewPager = null;
	private CustomFragmentPagerAdapter mPagerAdapter = null;

	private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
	private ArrayList<String> mTitleList = new ArrayList<String>();
	private ArrayList<ViewHolder> mViewList = new ArrayList<ViewHolder>();

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
		mLayoutHeight = getResources().getDisplayMetrics().heightPixels - getStatusBarHeight(); 
		mActionBarHeight = (int) getResources().getDimension(R.dimen.actionbar_height);
		mTabWidth = getResources().getDisplayMetrics().widthPixels;
		mTabHeight = (int) getResources().getDimension(R.dimen.tab_height);
		mTabUnderlineHeight = (int) getResources().getDimension(R.dimen.tab_underline_height);
		mTabUnderlineWidth = (int) getResources().getDimension(R.dimen.tab_underline_width);
	}

	private void initView(){
		mContentView = new LinearLayout(getActivity());
		mContentView.setOrientation(LinearLayout.VERTICAL);
		mContentViewLayoutParam = new LayoutParams(mLayoutWidth, mLayoutHeight - mActionBarHeight);
		mContentView.setLayoutParams(mContentViewLayoutParam);
		mContentView.setBackgroundColor(getResources().getColor(R.color.drawer_item_background_n));

		
		mTabScrollView = new HorizontalScrollView(getActivity());
		mTabScrollView.setLayoutParams(new ScrollView.LayoutParams(mTabWidth, mTabHeight));
		mTabScrollView.setScrollContainer(false);
		mTabScrollView.setVerticalScrollBarEnabled(false);
		mTabScrollView.setHorizontalScrollBarEnabled(false);
		
		mTabLayout = new LinearLayout(getActivity());
		mTabLayout.setOrientation(LinearLayout.HORIZONTAL);
		mTabLayout.setBackgroundColor(getResources().getColor(R.color.drawer_item_background_n));
		mTabLayoutParam = new LayoutParams(LayoutParams.WRAP_CONTENT, mTabHeight);
		mTabLayout.setLayoutParams(mTabLayoutParam);
		mTabLayout.setPadding((int) getResources().getDimension(R.dimen.tab_side_padding),
				0, (int) getResources().getDimension(R.dimen.tab_side_padding), 0);

		mTabScrollView.addView(mTabLayout);

		mContentLayout = new FrameLayout(getActivity());
		mContentLayoutParam = new LayoutParams(mLayoutWidth, mLayoutHeight - mTabHeight - mActionBarHeight);
		mContentLayout.setLayoutParams(mContentLayoutParam);

		mViewPager = new CustomViewPager(getActivity());
		mViewPager.setId(mViewPagerId);

		View shadowView = new View(getActivity());
		shadowView.setBackground(getResources().getDrawable(R.drawable.tab_img_shadow_pattern));

		mContentLayout.addView(mViewPager, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		mContentLayout.addView(shadowView, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				3 * (int)getResources().getDisplayMetrics().density));
		
		mContentView.addView(mTabScrollView);
		mContentView.addView(mContentLayout);
	}

	private void initModel(){

		mViewPager.setOffscreenPageLimit(mFragmentList.size());
		makeTabViews(mTabLayout);
		mPagerAdapter = new CustomFragmentPagerAdapter(getFragmentManager(), mFragmentList);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
	}

	private void makeTabViews(ViewGroup parent){
		if(mTitleList != null && mTabLayout != null ){
			for(int i = 0 ; i < mTitleList.size() ; i ++){
			
				addTabViewItem(parent, mTitleList.get(i));

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

		mViewList.get(0).tvTitle.setSelected(true);
		mViewList.get(0).tvTitle.setTypeface(roboto_bold);
		mViewList.get(0).vUnderline.setVisibility(View.VISIBLE);

		for(int i = 0 ; i < mViewList.size() ; i ++){
			final int position = i;
			mViewList.get(position).layoutHolder.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mViewPager.setCurrentItem(position, true);

				}
			});
		}

	}

	private void addTabViewItem(ViewGroup parent, String text){
		if(getActivity() == null || parent == null || TextUtils.isEmpty(text))
			return ;
		ViewHolder holder = new ViewHolder();
		
		holder.layoutHolder = new RelativeLayout(getActivity());
		holder.tvTitle = new TextView(getActivity());
		holder.tvTitle.setText(text);
		holder.tvTitle.setTypeface(roboto_light);
		holder.tvTitle.setGravity(Gravity.CENTER);
		holder.tvTitle.setPadding((int) getResources().getDimension(R.dimen.tab_text_side_padding),
				0, (int) getResources().getDimension(R.dimen.tab_text_side_padding), 0);
		holder.tvTitle.setTextAppearance(getActivity(), R.style.TabTitleText);
		
		holder.vUnderline = new View(getActivity());
		holder.vUnderline.setBackground(getResources().getDrawable(R.drawable.tab_sel_white));
		holder.vUnderline.setVisibility(View.GONE);
		
		RelativeLayout.LayoutParams underLineParam = new RelativeLayout.LayoutParams(mTabUnderlineWidth, mTabUnderlineHeight);
		underLineParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		underLineParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		holder.layoutHolder.addView(holder.tvTitle, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		holder.layoutHolder.addView(holder.vUnderline, underLineParam);
		
		mViewList.add(holder);
		parent.addView(holder.layoutHolder, new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
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
			if(mViewList != null && mViewList.size() > 0){
				AlphaAnimation ani = new AlphaAnimation(1, 0);
				ani.setDuration(ALPHA_ANI_DURAION);
				
				for(ViewHolder holder : mViewList){
					holder.tvTitle.setSelected(false);
					holder.tvTitle.setTypeface(roboto_light);
					holder.vUnderline.setAnimation(ani);
					holder.vUnderline.setVisibility(View.GONE);
				}
				
				ani = new AlphaAnimation(0, 1);
				ani.setDuration(ALPHA_ANI_DURAION);
				
				mViewList.get(position).tvTitle.setSelected(true);
				mViewList.get(position).tvTitle.setTypeface(roboto_bold);
				mViewList.get(position).vUnderline.setAnimation(ani);
				mViewList.get(position).vUnderline.setVisibility(View.VISIBLE);
				mTabScrollView.smoothScrollTo((int)mViewList.get(position).layoutHolder.getX(), 0);
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub
			if(mViewList != null && mViewList.size() > 0 && mViewPager != null){
				int currentPosition = mViewPager.getCurrentItem();

				if(nowDragging){
					if(position == currentPosition && positionOffset > 0){
						//swipe right
					}else if (positionOffset > 0){
						//swipe left
					}
				}
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			
			if(mViewPager == null || mViewList == null || mViewList.size() <= 0)
				return;
			switch(state){
			case CustomViewPager.SCROLL_STATE_IDLE :
				nowDragging = false;
				break;

			case CustomViewPager.SCROLL_STATE_DRAGGING :
				nowDragging = true;
				break;

			case CustomViewPager.SCROLL_STATE_SETTLING :
				nowDragging = false;
				break;
			}
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
}

