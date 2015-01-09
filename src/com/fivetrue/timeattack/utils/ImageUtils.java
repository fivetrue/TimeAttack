package com.fivetrue.timeattack.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.fivetrue.network.VolleyInstance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Path.Direction;
import android.graphics.BitmapFactory;

public class ImageUtils extends BaseUtils{
	
	static private ImageUtils instance = null;
	private final int STROKE_WIDTH = 3;
	private String PNG = ".png";
	private int QUALITY = 50;
	
	private String mCacheDirPath = null;
	private float mDensity = 0;

	public void saveBitmap(Bitmap bitmap, String key){
		
		VolleyInstance.getLruCache().put(key, bitmap);
		File fileCacheItem = new File(mCacheDirPath + key + PNG);
		if(!fileCacheItem.exists()){
			OutputStream out = null;
			try{
				fileCacheItem.createNewFile();
				out = new FileOutputStream(fileCacheItem);
				bitmap.compress(CompressFormat.PNG, QUALITY, out);
			}catch (Exception e){
				e.printStackTrace();
			}finally{
				try{
					if(out != null){
						out.close();
					}
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public Bitmap scaleBitmap(Bitmap bitmap, int width, int height){
		return Bitmap.createScaledBitmap(bitmap, width, height, false);
	}
	
	public Bitmap getImageBitmap(String key){
		Bitmap bm = VolleyInstance.getLruCache().get(key);
		if(bm != null){
			return bm;
		}else{
			File file = new File(mCacheDirPath + key + PNG);
			if(file.exists()){
				bm = BitmapFactory.decodeFile(file.getAbsolutePath());
				if(bm != null){
					VolleyInstance.getLruCache().put(key, bm);
				}
				return bm;
			}
		}
		return bm;
	}
	
	public Bitmap circleBitmap(Bitmap bitmap, boolean drawStroke, int strokeWidth){
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		Bitmap target = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		
		Path path = new Path();
		path.addCircle(width / 2 , height / 2, width / 2 , Direction.CW);
		
		Canvas canvas = new Canvas(target);
		canvas.clipPath(path);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		
		if(drawStroke){
			Paint strokePaint = new Paint();
			strokePaint.setAntiAlias(true);
			strokePaint.setStrokeWidth(mDensity * strokeWidth);
			strokePaint.setStyle(Style.STROKE);
			strokePaint.setColor(0xFFFFFFFF);
			canvas.drawCircle(width / 2 , height / 2, (width / 2) - ((mDensity * strokeWidth) / 2), strokePaint);
		}
		
		return target;
	}
	
	public Bitmap circleBitmap(Bitmap bitmap){
		return circleBitmap(bitmap, false, STROKE_WIDTH);
	}
	
	public Bitmap circleBitmap(Bitmap bitmap, boolean drawStroke){
		return circleBitmap(bitmap, drawStroke, STROKE_WIDTH);
	}
	
	static public ImageUtils getInstance(Context context){
		if(instance != null){
			return instance;
		}else{
			instance = new ImageUtils(context);
			return instance;
		}
	}
	
	private ImageUtils(Context context){
		super(context);
		VolleyInstance.init(context);
		mCacheDirPath = mContext.getCacheDir().getAbsolutePath() + "/";
		mDensity = context.getResources().getDisplayMetrics().density;
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return ImageUtils.class.getSimpleName();
	}
	
}
