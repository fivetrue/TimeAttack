package com.fivetrue.timeattack.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class NetworkTransactionOpenHelper extends BaseSQLiteOpenHelper {

	public NetworkTransactionOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String createTableQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dropTableQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean upgradeTable() {
		// TODO Auto-generated method stub
		return false;
	}

}
