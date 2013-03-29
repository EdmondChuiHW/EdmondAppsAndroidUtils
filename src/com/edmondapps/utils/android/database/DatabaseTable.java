package com.edmondapps.utils.android.database;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * An interface that works with {@link Database}.
 * </br>
 * A {@code DatabaseTable} contains information required to create a database.
 * 
 * @author Edmond
 * 
 */
public interface DatabaseTable {
	/**
	 * The returned {@code String} will be passed to
	 * {@link DatabaseUtils#sqlEscapeString(String)} and cached by
	 * {@link Database}.
	 * 
	 * @return the name of the Database table
	 */
	String getTableName();

	int getTableVersion();

	/**
	 * Called during {@link SQLiteOpenHelper#onCreate(SQLiteDatabase)},
	 * {@link SQLiteDatabase#execSQL(String)} will be executed with this
	 * returned {@code String}.
	 * </p>
	 * You must ensure the existence of the column
	 * BaseColumns._ID.</br>
	 * A common idiom would be:
	 * 
	 * <pre>
	 * <code>
	 * return new StringBuilder()
	 * 	.append("CREATE TABLE ").append(mName)
	 * 	.append("( ")
	 * 	.append(BaseColumns._ID).append(" INTEGER PRIMARY KEY").append(", ")
	 * 	.append(YOUR_OTHER_COLUMN).append(" TEXT").append(", ")
	 * 	.append(ANOTHER_COLUMN).append(" INTEGER")
	 * 	.append(" )")
	 * 	.toString();
	 * </code>
	 * </pre>
	 */
	String onCreateTableCommand();
}
