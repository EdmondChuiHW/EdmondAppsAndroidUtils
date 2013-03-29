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
