package com.fivetrue.timeattack.database;

public class DatabaseConstants {
	static public final String DB_FILE_NAME = "timeattack.db";
	static public final int DB_VERSION  = 1;
	
	static public class NetworkResultTableInfo{
		static public String TABLE = "network_result";
		static public String FIELD_ID = "_id";
		static public String FIELD_URL = "_url";
		static public String FIELD_RESULT = "_result";
		static public String FIELD_TIMESTAMP = "_timestamp";
		
		static public String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
				FIELD_ID + " integer primary key autoincrement, " +
				FIELD_URL + " text, " +
				FIELD_RESULT + " text, " +
				FIELD_TIMESTAMP + " text);";
		
		static public  String DROP_QUERY = "drop table if exists " + TABLE; 
	}
	
	static public class SubwayStationTableInfo{
		static public String TABLE = "station_list";
		static public String FIELD_ID = "_id";
		static public String FIELD_NAME = "_stationName";
		static public String FIELD_CODE = "_stationCode";
		static public String FIELD_FORIEGN_CODE = "_foriegnCode";
		static public String FIELD_LINE_NUMBER = "_lineNumber";
		
		static public String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
				FIELD_ID + " integer primary key autoincrement, " +
				FIELD_NAME + " text, " +
				FIELD_CODE + " text, " +
				FIELD_FORIEGN_CODE + " text, " +
				FIELD_LINE_NUMBER + " text);";

		static public  String DROP_QUERY = "drop table if exists " + TABLE; 
	}
}
