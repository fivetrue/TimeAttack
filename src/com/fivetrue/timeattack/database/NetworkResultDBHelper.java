package com.fivetrue.timeattack.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class NetworkResultDBHelper extends BaseSQLiteOpenHelper {
	
	
	public String TABLE = "network_result";
	public String FIELD_ID = "_id";
	public String FIELD_URL = "_url";
	public String FIELD_RESULT = "_result";
	public String FIELD_TIMESTAMP = "_timestamp";
	
	public String CREATE_QUERY = "create table " + TABLE + "(" +
			FIELD_ID + " integer primary key autoincrement, " +
			FIELD_URL + " text, " +
			FIELD_RESULT + " text, " +
			FIELD_TIMESTAMP + " text);";
	
	public  String DROP_QUERY = "drop table if exists " + TABLE; 
	
	public NetworkResultDBHelper(Context context, CursorFactory factory) {
		super(context, factory);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public String createTableQuery() {
		// TODO Auto-generated method stub
		return CREATE_QUERY;
	}

	@Override
	public String dropTableQuery() {
		// TODO Auto-generated method stub
		return DROP_QUERY;
	}

	@Override
	public boolean upgradeTable() {
		// TODO Auto-generated method stub
		return false;
	}

}
