package com.fivetrue.timeattack.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class TimeAttackDBHelper extends BaseSQLiteOpenHelper {
	
	public TimeAttackDBHelper(Context context, CursorFactory factory) {
		super(context, factory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<String> createTableQuery() {
		// TODO Auto-generated method stub
		ArrayList<String> createQueries = new ArrayList<String>();
		createQueries.add(DatabaseConstants.NetworkResultTableInfo.CREATE_QUERY);
		createQueries.add(DatabaseConstants.SubwayStationTableInfo.CREATE_QUERY);
		return  createQueries;
	}

	@Override
	public ArrayList<String> dropTableQuery() {
		// TODO Auto-generated method stub
		ArrayList<String> dropQueries = new ArrayList<String>();
		dropQueries.add(DatabaseConstants.NetworkResultTableInfo.DROP_QUERY);
		dropQueries.add(DatabaseConstants.SubwayStationTableInfo.DROP_QUERY);
		return dropQueries;
	}

	@Override
	public boolean upgradeTable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	synchronized public SQLiteDatabase getReadableDatabase() {
		// TODO Auto-generated method stub
		return super.getReadableDatabase();
	}
	
	@Override
	synchronized public SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		return super.getWritableDatabase();
	}
}
