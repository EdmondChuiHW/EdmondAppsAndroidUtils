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
