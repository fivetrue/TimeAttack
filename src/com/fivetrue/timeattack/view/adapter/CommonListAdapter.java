package com.fivetrue.timeattack.view.adapter;

import java.util.ArrayList;

import com.fivetrue.timeattack.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

abstract public class CommonListAdapter<T> extends MyBaseAdapter<T>{
	
	private ViewHolder holder = null;
	protected int[] mColorList;
	protected int mColor = R.color.main_primary_color;
	protected int mColorLight = R.color.main_primary_light_color;
	protected int mColorDark = R.color.main_primary_dark_color;
	
	protected long ANI_DURATION = 400L;
	
	/**
	 * @param context
	 * @param layoutResourceId
	 * @param arrayList
	 * @param colorResourceList primary, primary soft, primary dark, primary accent colors
	 */
	public CommonListAdapter(Context context, ArrayList<T> arrayList, int[] colorResourceList) {
		super(context, R.layout.item_common_list, arrayList);
		// TODO Auto-generated constructor stub
		mColorList = colorResourceList;
		if(colorResourceList.length >= 3){
			setColorList(mColorList);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		holder = new ViewHolder();

		if (convertView == null) {
			convertView = mLayoutInflater.inflate(mResourceId, null);

			holder.headerBackground = convertView.findViewById(R.id.layout_common_item_header);
			holder.headerTitle = (TextView) convertView.findViewById(R.id.tv_common_item_header);
			holder.arrowImage = (ImageView) convertView.findViewById(R.id.iv_common_item_arrow);
			
			holder.bodyBackground = convertView.findViewById(R.id.layout_common_item_body);
			holder.mainImage =  (ImageView) convertView.findViewById(R.id.iv_common_item_main_image);
			
			holder.thumbImage = (ImageView) convertView.findViewById(R.id.iv_common_item_image);
			holder.thumbBackImage = (ImageView) convertView.findViewById(R.id.iv_common_item_back_image);
			
			holder.Title = (TextView) convertView.findViewById(R.id.tv_common_item_title);
			holder.subTitle = (TextView) convertView.findViewById(R.id.tv_common_item_sub_title);
			
			holder.contentText = (TextView) convertView.findViewById(R.id.tv_common_item_content);
			holder.aboveBodyShadow = convertView.findViewById(R.id.above_body_shadow);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.headerBackground.setBackgroundColor(mContext.getResources().getColor(mColor));
		holder.headerTitle.setTextColor(mContext.getResources().getColor(mColorLight));
		
		holder.Title.setTextColor(mContext.getResources().getColor(mColorDark));
		holder.subTitle.setTextColor(mContext.getResources().getColor(mColor));
		
		holder.arrowImage.setBackground(mContext.getResources().getDrawable(mSelector));
		
		holder.bodyBackground.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
		convertView.setBackgroundColor(mContext.getResources().getColor(mColorLight));
		holder.contentText.setTextColor(mContext.getResources().getColor(mColorDark));
		if(parent != null){
			parent.setBackgroundColor(mContext.getResources().getColor(mColorLight));
		}
		return getView(position, convertView, parent, holder);
	}
	
	abstract public View getView(int position, View convertView, ViewGroup parent, ViewHolder holder);

	protected class ViewHolder{
		public View headerBackground;
		public TextView headerTitle;
		public ImageView arrowImage;
		
		public View bodyBackground;
		public ImageView mainImage;
		
		public ImageView thumbImage;
		public ImageView thumbBackImage;
		
		public TextView Title;
		public TextView subTitle;
		
		public TextView contentText;
		
		public View aboveBodyShadow;
	}
	
	protected void setColorList(int[] colorList){
		
		if(colorList != null && colorList.length > 0){
			mColor = colorList[0];
			mColorLight = colorList[1];
			mColorDark = colorList[2];
		}else{
			mColor = R.color.main_primary_color;
			mColorLight = R.color.main_primary_light_color;
			mColorDark = R.color.main_primary_dark_color;
		}
	}
}
