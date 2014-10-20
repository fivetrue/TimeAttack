package com.fivetrue.timeattack.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// http://kuroikuma.tistory.com/entry/Android-DB-SQLite-%EC%98%88%EC%A0%9C
abstract public class BaseSQLiteOpenHelper extends SQLiteOpenHelper{
	
	protected final int INVALID_VALUE = -1;
	private Context context = null;
	private String dataBaseFileName = null;
	private int databaseVerison = INVALID_VALUE;
	private CursorFactory cursorFactory = null;
	

	public BaseSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.dataBaseFileName = name;
		this.databaseVerison = version;
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
		return dataBaseFileName;
	}

	public int getDatabaseVerison() {
		return databaseVerison;
	}

	public CursorFactory getCursorFactory() {
		return cursorFactory;
	}

}
