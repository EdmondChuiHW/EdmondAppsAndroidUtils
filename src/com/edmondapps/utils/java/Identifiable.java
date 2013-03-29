package com.edmondapps.utils.java;

import com.edmondapps.utils.android.database.Database;

/**
 * Simple interface for database operations.
 * 
 * @see IdUtils
 * @see Database
 * @author Edmond
 * 
 */
public interface Identifiable {
	/**
	 * 
	 * @return an unique identifier of the object
	 */
	long getId();
}
