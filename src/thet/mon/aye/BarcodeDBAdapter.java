package thet.mon.aye;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BarcodeDBAdapter {
    
    private static final String KEY_ROWID = "_id";
    static final String KEY_BARCODE_TYPE = "barcodeType";
    private static final String KEY_NAME = "productName";
    private static final String KEY_PRICE = "productPrice";
    
    private static final String TAG = "BarCodeDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "notes";
    private static final int DATABASE_VERSION = 5;
    private final Context mCtx;
    
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" + 
                                                        KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                                                        KEY_BARCODE_TYPE + " TEXT NOT NULL, " +
                                                        KEY_NAME + " TEXT NOT NULL, " +
                                                        KEY_PRICE + " INTEGER NOT NULL)";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + DATABASE_NAME;
    private static final String DATABASE_TOTAL = "SELECT SUM(" + KEY_PRICE + ") FROM " + DATABASE_TABLE;

    private static class DatabaseHelper extends SQLiteOpenHelper {
    
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL(DATABASE_DROP);
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public BarcodeDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public BarcodeDBAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     */
    public long createNote(String type, String name, double tescoPrice) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BARCODE_TYPE, type);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_PRICE, tescoPrice);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
 
    /**
     * Delete the note with the given rowId
     * 
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteNote(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchAllNotes() {

        return mDb.query(DATABASE_TABLE, 
                         new String[] {KEY_ROWID, KEY_BARCODE_TYPE, KEY_NAME, KEY_PRICE}, 
                         null, null, null, null, null);
    }
  

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchNote(long rowId) throws SQLException {
        Cursor mCursor =
            mDb.query(true, DATABASE_TABLE, 
                      new String[] {KEY_ROWID, KEY_BARCODE_TYPE, KEY_NAME, KEY_PRICE}, 
                      KEY_ROWID + "=" + rowId, 
                      null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateNote(long rowId, String type, String name, int price) { 
        ContentValues args = new ContentValues();
        args.put(KEY_BARCODE_TYPE, type);
        args.put(KEY_PRICE, price);
        args.put(KEY_NAME, name);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public int getTotal() {
        Cursor cursor = mDb.rawQuery(DATABASE_TOTAL, null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
		return cursor.getInt(0);
    }
}
