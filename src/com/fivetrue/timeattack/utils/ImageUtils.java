package com.fivetrue.timeattack.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.fivetrue.network.VolleyInstance;
import com.fivetrue.utils.Logger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class ImageUtils {
	
	static private ImageUtils instance = null;
	private String PNG = ".png";
	private int QUALITY = 90;
	
	private Context mContext = null;
	private String mCacheDirPath = null;

	public void saveBitmap(Bitmap bitmap, String key){
		
		Logger.e("ojkwon", mCacheDirPath + key + PNG);
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
					out.close();
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
		Logger.e("ojkwon",  mCacheDirPath + key + PNG);
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
	
	
	static public ImageUtils getInstance(Context context){
		if(instance != null){
			return instance;
		}else{
			instance = new ImageUtils(context);
			return instance;
		}
	}
	
	private ImageUtils(Context context){
		mContext = context;
		VolleyInstance.init(context);
		mCacheDirPath = mContext.getCacheDir().getAbsolutePath() + "/";
	}
	
}
