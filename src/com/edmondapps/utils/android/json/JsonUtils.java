/*
 * Copyright 2013 Edmond Chui
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
