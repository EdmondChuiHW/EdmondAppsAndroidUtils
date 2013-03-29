package com.edmondapps.utils.android.json;

import com.google.gson.JsonArray;

public final class JsonUtils {
	private JsonUtils() {
		throw new AssertionError("nice try");
	}

	/**
	 * Converts a collection of {@linkplain Jsonable} and insert them in a
	 * {@link JsonArray}. Any null elements are skipped.
	 * 
	 * @param c
	 *            a collection of {@linkplain Jsonable}
	 * @return a {@link JsonArray} that contains all the elements.
	 */
	public static JsonArray arrayOf(Iterable<? extends Jsonable> c) {
		JsonArray array = new JsonArray();
		for (Jsonable j : c) {
			if (j == null) {
				continue;
			}
			array.add(j.toJson());
		}
		return array;
	}
}
