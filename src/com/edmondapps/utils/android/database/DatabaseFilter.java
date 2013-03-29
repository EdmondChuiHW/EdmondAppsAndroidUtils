package com.edmondapps.utils.android.database;

import java.io.Closeable;

import android.database.Cursor;
import android.text.TextUtils;
import android.widget.Filter;

public abstract class DatabaseFilter extends Filter implements Closeable {
	private static final String WHAT = "?";
	private static final String OR = " OR ";
	private static final String LIKE = " LIKE ";

	private final Database<?> mDatabase;
	private final String[] mColumns;

	protected DatabaseFilter(Database<?> database, String... columns) {
		if ( (columns == null) || (columns.length <= 0)) {
			throw new NullPointerException("columns must not be null");
		}
		mDatabase = database;
		mColumns = columns;
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
