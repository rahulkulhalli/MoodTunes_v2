package utils;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import pojos.Mp3Song;

/**
 * A class to help with the database operations.<br>
 *     Operations supported - <br>
 *     1) Create <br>
 *     2) Update <br>
 *     3) Delete <br>
 */
public class DbHelper extends SQLiteOpenHelper {

    /**
     * Application context.
     */
    private Context mContext;

    /**
     * Constructor.
     *
     * @param context Application context.
     */
    public DbHelper(final Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        mContext = context;
    }

    /**
     * Triggered when the database is created.
     *
     * @param sqLiteDatabase Reference to database.
     */
    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        String createQuery = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.COLUMN_1 + "text,"
                + Constants.COLUMN_2 + "text,"
                + Constants.COLUMN_3 + "text,"
                + Constants.COLUMN_4 + "text,"
                + Constants.COLUMN_5 + "text"
                + ")";

        sqLiteDatabase.execSQL(createQuery);
        Log.d(Constants.DB_CLASS, "Created database successfully.");
    }

    /**
     *
     * A getter for getting an MP3 song based on title (and/or artist).
     *
     * @param songTitle Song title.
     * @param songArtist Song artist (optional).
     * @return Mp3 song.
     */
    public Mp3Song getMp3Song(final String songTitle,
                              final @Nullable String songArtist) {

        SQLiteDatabase db = this.getReadableDatabase();

        Mp3Song mp3Song;
        String dbQuery;

        if (songArtist == null) {
            dbQuery = "SELECT * FROM " + Constants.TABLE_NAME
                    + "WHERE " + Constants.COLUMN_1 + " = " + songTitle;

            Cursor cursor = db.rawQuery(dbQuery, null);

            if (cursor.moveToFirst()) {
                return new Mp3Song(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getString(4));
            }

            cursor.close();
        }

        dbQuery = "SELECT * FROM " + Constants.TABLE_NAME
                + "WHERE " + Constants.COLUMN_1 + " = '" + songTitle
                + "' AND " + Constants.COLUMN_2 + " = '" + songArtist + "'";

        Cursor c = db.rawQuery(dbQuery, null);

        if (c.moveToFirst()) {
            return new Mp3Song(c.getString(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4));
        }

        c.close();

        return null;
    }

    /**
     * A method to clear database after operations.
     * Call this after every session to avoid data duplication.
     */
    public void clearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + Constants.TABLE_NAME;
        db.execSQL(query);

        Log.d(Constants.DB_CLASS, "Deleted all data from database.");
    }

    /**
     * A method to add an {@link Mp3Song} to the database.
     *
     * @param mp3Song {@link Mp3Song}.
     */
    public void addMp3Song(final Mp3Song mp3Song) {
        SQLiteDatabase db = this.getWritableDatabase();

        String insertQuery = "INSERT INTO "
                + Constants.TABLE_NAME + " VALUES ('"
                + mp3Song.getSongName() + "','"
                + mp3Song.getSongArtist() + "','"
                + mp3Song.getSongPath() + "','"
                + mp3Song.getLevelOneMood() + "','"
                + mp3Song.getLevelTwoMood()
                + "')";

        db.execSQL(insertQuery);

        Log.d(Constants.DB_CLASS, "Inserted " + mp3Song.getSongName()
                + " into the database.");

    }

    /**
     * Triggered when database is upgraded.
     *
     * @param sqLiteDatabase Database.
     * @param i Previous version.
     * @param i1 New version.
     */
    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase,
                          final int i, final int i1) {
        if (i != i1) {
            Log.d(Constants.DB_CLASS, "Upgrading database.");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "
                    + Constants.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
