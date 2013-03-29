package com.edmondapps.utils.android.database;

import android.content.ContentValues;

import com.edmondapps.utils.java.Identifiable;

/**
 * Interface that works with {@link Database}.
 * </br>
 * A {@code DatabaseEntry} represents a row in a database.
 * 
 * @author Edmond
 * 
 */
public interface DatabaseEntry extends Identifiable {
	ContentValues toContentValues(DatabaseTable db);
}
