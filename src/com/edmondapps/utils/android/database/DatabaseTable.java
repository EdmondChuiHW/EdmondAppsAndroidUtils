package com.edmondapps.utils.android.database;


public interface DatabaseTable<E extends DatabaseEntry<?>> {
	String getTableName();

	int getTableVersion();

	String onCreateTableCommand();
}
