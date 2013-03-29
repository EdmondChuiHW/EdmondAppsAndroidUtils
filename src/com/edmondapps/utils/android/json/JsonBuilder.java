package com.edmondapps.utils.android.json;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

/**
 * Builds an object with the given ID and {@link JsonReader}.
 * 
 * @author Edmond
 * 
 * @param <T>
 *            the type of the object built
 */
public interface JsonBuilder<T> {
	/**
	 * You will need to call {@link JsonReader#beginObject()} and
	 * {@link JsonReader#endObject()} yourself.
	 * 
	 * @param id
	 *            a negative number if no ID is applicable for the object
	 * @param reader
	 *            a non-null instance of {@link JsonReader}
	 * @return the object built
	 * @throws IOException
	 *             usually thrown by the {@link JsonReader}
	 */
	T build(long id, JsonReader reader) throws IOException;
}
