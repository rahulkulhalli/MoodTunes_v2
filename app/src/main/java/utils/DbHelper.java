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
     * Column no. 1
     */
    private static final int COLUMN_1 = 0;

    /**
     * Column no. 2
     */
    private static final int COLUMN_2 = 1;

    /**
     * Column no. 3
     */
    private static final int COLUMN_3 = 2;

    /**
     * Column no. 4
     */
    private static final int COLUMN_4 = 3;

    /**
     * Column no. 5
     */
    private static final int COLUMN_5 = 4;

    /**
     * Column no. 6
     */
    private static final int COLUMN_6 = 5;


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
        // Table create query.
        String createQuery = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.COLUMN_1 + " text,"
                + Constants.COLUMN_2 + " text,"
                + Constants.COLUMN_3 + " text,"
                + Constants.COLUMN_4 + " text,"
                + Constants.COLUMN_5 + " text,"
                + Constants.COLUMN_6 + " text"
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

        String dbQuery;

        if (songArtist == null) {
            dbQuery = "SELECT * FROM " + Constants.TABLE_NAME
                    + "WHERE " + Constants.COLUMN_1 + " = '" + songTitle + "'";

            Cursor cursor = db.rawQuery(dbQuery, null);

            if (cursor.moveToFirst()) {
                return new Mp3Song(cursor.getString(COLUMN_1),
                        cursor.getString(COLUMN_2),
                        cursor.getString(COLUMN_3),
                        cursor.getString(COLUMN_4),
                        cursor.getString(COLUMN_5),
                        cursor.getString(COLUMN_6));
            }

            cursor.close();
        }

        dbQuery = "SELECT * FROM " + Constants.TABLE_NAME
                + "WHERE " + Constants.COLUMN_1 + " = '" + songTitle
                + "' AND " + Constants.COLUMN_3 + " = '" + songArtist + "'";

        Cursor c = db.rawQuery(dbQuery, null);

        if (c.moveToFirst()) {
            return new Mp3Song(c.getString(COLUMN_1),
                    c.getString(COLUMN_2),
                    c.getString(COLUMN_3),
                    c.getString(COLUMN_4),
                    c.getString(COLUMN_5),
                    c.getString(COLUMN_6));
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
                + mp3Song.getOriginalSongName() + "','"
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
