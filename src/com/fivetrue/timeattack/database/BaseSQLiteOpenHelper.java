package com.fivetrue.timeattack.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// http://kuroikuma.tistory.com/entry/Android-DB-SQLite-%EC%98%88%EC%A0%9C
abstract public class BaseSQLiteOpenHelper extends SQLiteOpenHelper{
	
	private Context context = null;
	private CursorFactory cursorFactory = null;
	

	public BaseSQLiteOpenHelper(Context context,
			CursorFactory factory) {
		super(context, DatabaseConstants.DB_FILE_NAME, factory, DatabaseConstants.DB_VERSION);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.cursorFactory = factory;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(createTableQuery());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(!upgradeTable()){
			db.execSQL(dropTableQuery());
			onCreate(db);
		}else{
			Log.e(getDatabaseName(), "upgrade Database");
		}
	}
	
	
	/**
	 * @return Create Table Query
	 */
	abstract public String createTableQuery();
	
	/**
	 * @return Drop table Query
	 */
	abstract public String dropTableQuery();
	
	/**
	 * @return 업그래이드가 필요 할 시 업그레이드 후 true를 리턴
	 */
	abstract public boolean upgradeTable();

	public Context getContext() {
		return context;
	}

	public String getDataBaseFileName() {
		return DatabaseConstants.DB_FILE_NAME;
	}

	public int getDatabaseVerison() {
		return  DatabaseConstants.DB_VERSION;
	}

	public CursorFactory getCursorFactory() {
		return cursorFactory;
	}

}
