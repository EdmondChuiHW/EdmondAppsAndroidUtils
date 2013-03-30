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

import java.io.Closeable;
import java.util.Arrays;

import android.database.Cursor;
import android.text.TextUtils;
import android.widget.Filter;

public abstract class DatabaseFilter extends Filter implements Closeable {
    private static final String WHAT = "?";
    private static final String OR = " OR ";
    private static final String LIKE = " LIKE ";

    private final Database<?> mDatabase;
    private final String[] mColumns;

    protected DatabaseFilter(Database<?> database, String col, String... columns) {
        mDatabase = database;
        mColumns = Arrays.copyOf(columns, columns.length + 1);
        mColumns[columns.length - 1] = col;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (TextUtils.isEmpty(constraint)) {
            return wrapCursor(results, mDatabase.query());
        }

        int length = mColumns.length;

        StringBuilder selection = new StringBuilder();
        String[] selectionArgs = new String[length];
        String filter = constraint.toString() + "%";

        for (int i = 0; i < length; i++) {
            if (i > 0) {
                selection.append(OR);
            }
            selection.append(mColumns[i]).append(LIKE).append(WHAT);
            selectionArgs[i] = filter;
        }

        return wrapCursor(results, mDatabase.query(selection.toString(), selectionArgs));
    }

    private FilterResults wrapCursor(FilterResults r, Cursor c) {
        r.count = c.getCount();
        r.values = c;
        return r;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (results != null) {
            publishResults(constraint, results.count, (Cursor)results.values);
        }
    }

    /**
     * Called in the UI thread after filtering the results.
     * 
     * @see #publishResults(CharSequence, FilterResults)
     */
    protected abstract void publishResults(CharSequence constraint, int count, Cursor c);

    /**
     * Closes the {@link Database} passes in the constructor.</br>
     * It is not necessary to call this method if you manage your
     * {@link Database} manually.
     */
    @Override
    public void close() {
        mDatabase.close();
    }
}
