/*
 * Copyright 2013 Edmond Chui
 * 
 * Licenseimport java.util.ArrayList;
 * import java.util.Collection;
 * import java.util.List;
 * 
 * import android.content.ContentValues;
 * import android.content.Context;
 * import android.database.Cursor;
 * import android.database.DatabaseUtils;
 * import android.database.sqlite.SQLiteDatabase;
 * import android.database.sqlite.SQLiteOpenHelper;
 * import android.provider.BaseColumns;
 * S,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.edmondapps.utils.android.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * A helper class to help implementing a SQLite Database.
 * </p>
 * All write operations are performed in a transaction.
 * 
 * @author Edmond
 * 
 * @param <T>
 *            type of DatabaseEntry
 * 
 * @see DatabaseEntry
 * @see DatabaseTable
 */
public abstract class Database<T extends DatabaseEntry> extends SQLiteOpenHelper {
    private static final String SELECT_ID = BaseColumns._ID + " = ?";

    private final String mName;
    private DatabaseTable mTable;
    private final Context mContext;
    private String mOrderBy;

    /**
     * Create a {@code Database} with the given {@link DatabaseTable}.
     * The table name will be SQL-escaped.
     * 
     * @param context
     *            to open or create the database
     * @param table
     *            a non-null {@link DatabaseTable}
     */
    public Database(Context context, DatabaseTable table) {
        this(context, DatabaseUtils.sqlEscapeString(table.getTableName()), table.getTableVersion());
        mTable = table;
    }

    private Database(Context context, String escapedName, int version) {
        super(context, escapedName, null, version);
        mContext = context;
        mName = escapedName;
    }

    /**
     * It is safe to call this method prior to API 14.
     * 
     * @return the SQL-escaped {@code String} given by
     *         the {@link DatabaseTable} in the constructor.
     */
    @Override
    public final String getDatabaseName() {
        return mName;
    }

    public final DatabaseTable getDatabaseTable() {
        return mTable;
    }

    public final Context getContext() {
        return mContext;
    }

    public final String getOrderBy() {
        return mOrderBy;
    }

    /**
     * {@link SQLiteDatabase#execSQL(String)} will be executed with the
     * {@code String} returned by {@link DatabaseTable#onCreateTableCommand()}.
     * The {@link BaseColumns#_ID} column must exist for all the methods in this
     * class to function properly.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(mTable.onCreateTableCommand());
    }

    /**
     * Does nothing by default.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * Query the database and count the length of all entries.
     * 
     * @return the length of entries
     */
    public final int size() {
        return countSize(getReadableDatabase(), true);
    }

    private final int countSize(SQLiteDatabase db, boolean autoClose) {
        // new String[0] because null means all columns, which we don't need
        Cursor cursor = db.query(mName, new String[0], null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (autoClose) {
            db.close();
        }
        return count;
    }

    /**
     * 
     * @return a {@code long[]} that contains all the IDs in the database.
     */
    public final long[] getIds() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(mName, new String[] {BaseColumns._ID}, null, null, null, null, null);

        long[] ids = new long[cursor.getCount()];
        for (int i = 0; cursor.moveToNext(); i++) {
            ids[i] = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
        }

        cursor.close();
        database.close();

        return ids;
    }

    /**
     * Performs a query with the given ID. The {@link BaseColumns#_ID} column
     * must exist for this method to function properly.
     * 
     * @param id
     *            the ID to search for
     * @return the {@code Cursor} as specified by
     *         {@link SQLiteDatabase#query(String, String[], String, String[], String, String, String)}
     */
    public final Cursor query(long id) {
        return query(SELECT_ID, String.valueOf(id));
    }

    /**
     * Updates the {@link Database} using its ID as selection.
     * 
     * @param t
     *            a non-null instance of {@link DatabaseEntry}
     * @return number of updated rows
     */
    public final int update(T t) {
        return performUpdate(getWritableDatabase(), t, true);
    }

    /**
     * Updates the {@link Database} using the given selection.
     * 
     * @param t
     *            a non-null instance of {@link DatabaseEntry}
     * @see SQLiteDatabase#update(String, ContentValues, String, String[])
     * @return number of updated rows
     */
    public final int update(T t, String whereClause, String... whereArgs) {
        return performUpdate(getReadableDatabase(), t, true, whereClause, whereArgs);
    }

    /**
     * @see #delete(long)
     */
    public final int delete(T t) {
        return delete(t.getId());
    }

    /**
     * Deletes the entry associated with the given ID.
     * 
     * @param id
     *            id of the entry
     * @return number of rows deleted
     */
    public final int delete(long id) {
        return delete(SELECT_ID, String.valueOf(id));
    }

    /**
     * Deletes the entry by the given selection.
     * 
     * @see SQLiteDatabase#delete(String, String, String[])
     * @return number of rows deleted
     */
    public final int delete(String selection, String... selectionArgs) {
        SQLiteDatabase db = getWritableDatabase();
        int deleted = db.delete(mName, selection, selectionArgs);
        db.close();
        return deleted;
    }

    /**
     * Used by all the query operations.
     * 
     * @see SQLiteDatabase#query(String, String[], String, String[], String,
     *      String, String)
     */
    public final void setOrderBy(String orderBy) {
        mOrderBy = orderBy;
    }

    /**
     * Query all the rows in the database, ordered by {@link #getOrderBy()}.
     * 
     * @see SQLiteDatabase#query(String, String[], String, String[], String,
     *      String, String)
     */
    public final Cursor query() {
        return query(null, (String[])null);
    }

    /**
     * Query the database with the given selections.
     * 
     * @see SQLiteDatabase#query(String, String[], String, String[], String,
     *      String, String)
     */
    public final Cursor query(String selection, String... selectionArgs) {
        return getReadableDatabase().query(mName, null, selection, selectionArgs, null, null, mOrderBy);
    }

    /**
     * @see SQLiteDatabase#insert(String, String, ContentValues)
     */
    public final long insert(T t) {
        return performInsert(getWritableDatabase(), t, true);
    }

    /**
     * First attempt to update the database by ID, then inserts the entry if it
     * fails to update (a new entry).
     * 
     * @see SQLiteDatabase#update(String, ContentValues, String, String[])
     * @see SQLiteDatabase#insert(String, String, ContentValues)
     */
    public final long insertOrUpdate(T t) {
        return insertOrUpdate(t, SELECT_ID, wrapId(t));
    }

    /**
     * First attempt to update the database by the given selection, then inserts
     * the entry if it fails to update (a new entry).
     * 
     * @see SQLiteDatabase#update(String, ContentValues, String, String[])
     * @see SQLiteDatabase#insert(String, String, ContentValues)
     */
    public final long insertOrUpdate(T t, String whereClause, String... whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        long id = -1;
        try {
            ContentValues values = t.toContentValues(mTable);
            int updated = db.update(mName, values, whereClause, whereArgs);
            if (updated <= 0) {
                id = db.insert(mName, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        return id;
    }

    /**
     * Insert a collection of entries to the database. A null entry is ignored
     * and is represented by a null element in the returned {@code List<Long>}.
     * A inserted entry is represented by an ID.
     * 
     * @param items
     *            usually a {@link Collection} that contains the entries
     * @return a {@code List<Long>} that contains all the IDs in the same order
     *         of the {@code Iterator}.
     * @see SQLiteDatabase#insert(String, String, ContentValues)
     */
    public final List<Long> insertBunch(Iterable<? extends T> items) {
        List<Long> ids = new ArrayList<Long>();
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (T t : items) {
                if (t == null) {
                    ids.add(null);
                } else {
                    ids.add(Long.valueOf(db.insert(mName, null, t.toContentValues(mTable))));
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        return ids;
    }

    /**
     * Insert a collection of entries to the database. Each entry is updated
     * by ID first, if it fails, an insertion is performed.
     * </p>
     * A null entry is not inserted/updated and is represented by a null element
     * in the returned {@code List<Long>}. An updated entry is represented by a
     * zero. An inserted entry is represented by an ID.
     * 
     * @param items
     *            usually a {@link Collection} that contains the entries
     * @return a {@code List<Long>} that contains all the IDs in the same order
     *         of the {@code Iterator}.
     * @see SQLiteDatabase#insert(String, String, ContentValues)
     * @see SQLiteDatabase#update(String, ContentValues, String, String[])
     */
    public final List<Long> insertOrUpdateBunch(Iterable<? extends T> items) {
        List<Long> ids = new ArrayList<Long>();
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (T t : items) {
                if (t == null) {
                    ids.add(null);
                    continue;
                }

                ContentValues values = t.toContentValues(mTable);
                int updated = db.update(mName, values, SELECT_ID, wrapId(t));
                if (updated > 0) {
                    ids.add(Long.valueOf(0L));// updated
                } else {
                    ids.add(db.insert(mName, null, values));// insert
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        return ids;
    }

    private final long performInsert(SQLiteDatabase db, T t, boolean autoClose) {
        db.beginTransaction();
        long id;
        try {
            id = db.insert(mName, null, t.toContentValues(mTable));
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            if (autoClose) {
                db.close();
            }
        }
        return id;
    }

    private final int performUpdate(SQLiteDatabase db, T t, boolean autoClose) {
        return performUpdate(db, t, autoClose, SELECT_ID, wrapId(t));
    }

    private final int performUpdate(SQLiteDatabase db, T t, boolean autoClose, String whereClause, String... whereArgs) {
        db.beginTransaction();
        int updated;
        try {
            updated = db.update(mName, t.toContentValues(mTable), whereClause, whereArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            if (autoClose) {
                db.close();
            }
        }
        return updated;
    }

    private final String[] wrapId(T t) {
        return new String[] {String.valueOf(t.getId())};
    }
}
