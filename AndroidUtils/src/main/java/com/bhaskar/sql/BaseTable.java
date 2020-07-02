package com.bhaskar.sql;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
@Deprecated
public class BaseTable { // implements TableInterface {

	/*******************************************************************/
	// public static final String VARCHAR = " varchar2 ";
	// public static final String PRIMARY_KEY = " primary key ";
	// public static final String FOREIGN_KEY = " foreign key ";
	// public static final String REFERENCES = " references ";
	// public static final String NOT_NULL = " not null ";
	// public static final String UNIQUE = " unique ";
	// public static final String DEFAULT = " DEFAULT ";

	private String tableName;

	public BaseTable(final String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Do not include "create table IF NOT EXISTS tableName" (Only Start your
	 * columns name and condition in ());
	 * 
	 * @param sqlDB
	 * @param tableCreationQuery
	 */
	protected final void createSqlTable(SQLiteDatabase sqlDB,
			final String tableCreationQuery) {
		try {			
			sqlDB.execSQL(tableCreationQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected final long insertAllRows(SQLiteDatabase sqlDB,

	ArrayList<HashMap<String, String>> dataMapList) {

		for (HashMap<String, String> data : dataMapList) {

			TableDao.insertDataMap(sqlDB, tableName, data);
		}
		return dataMapList.size();
	}

	protected final int deleteTableAllRows(SQLiteDatabase sqlDB) {
		return TableDao.deleteTableData(sqlDB, tableName);
	}

	protected final ArrayList<HashMap<String, String>> getAllRowsFromTable(
			SQLiteDatabase sqlDB) {

		return TableDao.getAllDataFromTable(tableName, sqlDB);
	}

	protected final long insertSingleRow(SQLiteDatabase sqlDB,
			HashMap<String, String> dataMap) {
		return TableDao.insertDataMap(sqlDB, tableName, dataMap);
	}

	protected final void insertOrUpdateSingleRow(SQLiteDatabase sqlDB,
			HashMap<String, String> dataMap) {
		TableDao.insertOrUpdateDataMap(sqlDB, tableName, dataMap);
	}

	protected final ArrayList<HashMap<String, String>> getAllRowsFromTableWithAscendingOrder(
			SQLiteDatabase sqlDB, final String columnNameToOrderBy) {
		return TableDao.getAllDataFromTableWithAscendingOrder(sqlDB,
				tableName, columnNameToOrderBy);
	}

	// /*******************************************************************/
	//
	// /***
	// *
	// * @param sqlDB
	// * @param dataMapList
	// * @return long
	// */
	// protected abstract long insertDataList(SQLiteDatabase sqlDB,
	// ArrayList<HashMap<String, String>> dataMapList);
	//
	// protected abstract int deleteTableData(SQLiteDatabase sqlDB);
	//
	// /**
	// *
	// * @param sqlDB
	// * @return ArrayList<HashMap<String, String>>
	// */
	// protected abstract ArrayList<HashMap<String, String>>
	// getAllDataFromTable(
	// SQLiteDatabase sqlDB);
	//
	// /***
	// *
	// * @param sqlDB
	// * @param dataMap
	// */
	// protected abstract void insertDataMap(SQLiteDatabase sqlDB,
	// HashMap<String, String> dataMap);
	//
	// protected abstract void createTable(SQLiteDatabase sqlDB);
	//
	// /**
	// *
	// * @param sqlDB
	// * @param dataMap
	// */
	// protected abstract void insertOrUpdateDataMap(SQLiteDatabase sqlDB,
	// HashMap<String, String> dataMap);
}