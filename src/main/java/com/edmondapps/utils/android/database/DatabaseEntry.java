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
