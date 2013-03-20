package com.edmondapps.utils.android.json;

import com.google.gson.JsonArray;

public final class JsonUtils {
	private JsonUtils() {
		throw new AssertionError("nice try");
	}

	public static JsonArray arrayOf(Iterable<? extends Jsonable> c) {
		JsonArray array = new JsonArray();
		for (Jsonable j : c) {
			array.add(j.toJson());
		}
		return array;
	}
}
