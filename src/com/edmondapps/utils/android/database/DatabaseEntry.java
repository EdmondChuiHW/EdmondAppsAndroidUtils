package com.edmondapps.utils.android.database;

import android.content.ContentValues;

import com.edmondapps.utils.CollectionUtils.Identifiable;

public interface DatabaseEntry<D extends DatabaseTable<?>> extends Identifiable {
	ContentValues toContentValues(D db);
}
