package com.edmondapps.utils.android.json;

import com.google.gson.JsonObject;

/**
 * Converts a class to its JSON representation.
 * 
 * @author Edmond
 * 
 */
public interface Jsonable {
	/**
	 * The returned JSON should be descriptive enough to re-create the object
	 * with a {@link JsonBuilder}.
	 * 
	 * @return a {@link JsonObject} that represents the object
	 */
	JsonObject toJson();
}
