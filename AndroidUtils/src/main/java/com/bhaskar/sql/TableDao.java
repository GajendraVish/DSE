package com.bhaskar.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
@Deprecated
public final class TableDao {

	/**
	 * @param sqlDB
	 * @param dataMap
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	protected static long insertDataMap(SQLiteDatabase sqlDB, String tableName,
			HashMap<String, String> dataMap) {

		ContentValues values = new ContentValues();

		Set<String> keySet = dataMap.keySet();

		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String keyForColumn = iterator.next();
			String value = dataMap.get(keyForColumn);
			values.put(keyForColumn, value);
		}
		return sqlDB.insert(tableName, null, values);
	}

	/***
	 * @param tableName
	 * @param sqlDB
	 * @return ArrayList<HashMap<String, String>>
	 */
	protected static ArrayList<HashMap<String, String>> getAllDataFromTable(
			String tableName, SQLiteDatabase sqlDB) {

		ArrayList<HashMap<String, String>> newsDataList = new ArrayList<HashMap<String, String>>();

		if (!sqlDB.isOpen())
			return newsDataList;

		Cursor c = sqlDB.query(tableName, null, null, null, null, null, null);

		try {
			if (c.getCount() == 0) {
				c.close();
				return newsDataList;
			}
			c.moveToFirst();

			do {
				HashMap<String, String> newsData = new HashMap<String, String>();
				for (int columnIndex = 0; columnIndex < c.getColumnCount(); columnIndex++) {
					String columnName = c.getColumnName(columnIndex);
					String columnValue = c.getString(columnIndex);

					newsData.put(columnName, columnValue);
				}
				newsDataList.add(newsData);
			} while (c.moveToNext());
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsDataList;
	}

	/**
	 * 
	 * @param sqlDB
	 * @param tableName
	 * @return int, the number of rows affected if a whereClause is passed in, 0
	 *         otherwise. To remove all rows and get a count pass "1" as the
	 *         whereClause.
	 */
	protected static int deleteTableData(SQLiteDatabase sqlDB, String tableName) {
		return sqlDB.delete(tableName, null, null);
	}

	/***
	 * @param sqlDB
	 * @param tableName
	 * @param dataMap
	 */
	protected static void insertOrUpdateDataMap(SQLiteDatabase sqlDB,
			final String tableName, HashMap<String, String> dataMap) {

		Set<String> keySet = dataMap.keySet();
		StringBuffer columnNames = new StringBuffer();
		columnNames.append("( ");

		StringBuffer values = new StringBuffer();
		values.append(" ( ");
		for (String columnName : keySet) {
			columnNames.append(columnName + ",");
			String columnValue = dataMap.get(columnName);
			columnValue = columnValue.replace("'", "''");
			values.append("'" + columnValue + "'" + ",");
		}
		columnNames.deleteCharAt(columnNames.lastIndexOf(","));
		values.deleteCharAt(values.lastIndexOf(","));

		columnNames.append(" ) ");
		values.append(" ) ");

		String rawQuery = "INSERT OR REPLACE INTO " + tableName + columnNames
				+ " VALUES " + values;
		////Log.i("insertOrUpdateDataMap, rawQuery", rawQuery);
		sqlDB.execSQL(rawQuery);
	}
	
	/***
	 * 
	 * @param sqlDB
	 * @param tableName
	 * @param columnNameToOrderBy
	 * @return ArrayList<HashMap<String, String>>
	 */
	protected static ArrayList<HashMap<String, String>> getAllDataFromTableWithAscendingOrder(
			SQLiteDatabase sqlDB,final String tableName, final String columnNameToOrderBy) {

		ArrayList<HashMap<String, String>> newsDataList = new ArrayList<HashMap<String, String>>();

		if (!sqlDB.isOpen())
			return newsDataList;

		Cursor c = sqlDB.query(tableName, null, null, null, null, null,
				columnNameToOrderBy);

		try {
			if (c.getCount() == 0) {
				c.close();
				return newsDataList;
			}
			c.moveToFirst();

			do {
				HashMap<String, String> newsData = new HashMap<String, String>();
				for (int columnIndex = 0; columnIndex < c.getColumnCount(); columnIndex++) {
					String columnName = c.getColumnName(columnIndex);
					String columnValue = c.getString(columnIndex);

					newsData.put(columnName, columnValue);
				}
				newsDataList.add(newsData);
			} while (c.moveToNext());
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsDataList;
	}
}
