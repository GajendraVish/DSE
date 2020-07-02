package com.bhaskar.sql;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
@Deprecated
public interface TableInterface {

	String LIMIT = " limit ";
    String VARCHAR = " varchar2 ";
    String INTEGER = " integer ";
    String AUTOINCREMENT = " AUTOINCREMENT ";
    String PRIMARY_KEY = " primary key ";
    String FOREIGN_KEY = " foreign key ";
    String REFERENCES = " references ";
    String NOT_NULL = " not null ";
    String UNIQUE = " unique ";
    String DEFAULT = " DEFAULT ";

	/***
	 * Do not include "create table IF NOT EXISTS tableName" (Only Start your
	 * columns name and condition in ());
	 * 
	 * @param sqlDB
	 */
	public void createTable(SQLiteDatabase sqlDB);

	public long insertDataMap(SQLiteDatabase sqlDB,
                              HashMap<String, String> dataMap);

	public long insertDataList(SQLiteDatabase sqlDB,
                               ArrayList<HashMap<String, String>> dataMapList);

	public int deleteTableData(SQLiteDatabase sqlDB);

	public ArrayList<HashMap<String, String>> getAllDataFromTable(
            SQLiteDatabase sqlDB);

	public void insertOrUpdateDataMap(SQLiteDatabase sqlDB,
                                      HashMap<String, String> dataMap);

	public ArrayList<HashMap<String, String>> getAllDataFromTableWithAscendingOrder(
            SQLiteDatabase sqlDB, final String columnNameToOrderBy);
}
