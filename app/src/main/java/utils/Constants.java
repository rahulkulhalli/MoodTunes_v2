package utils;

import com.moodtunes.moodtunes_v2.activities.FaceScanner;
import com.moodtunes.moodtunes_v2.activities.HomeScreenActivity;


/**
 * A class for maintaining application level constants.
 */
public class Constants {

    /** =================
     * LOGGING CONSTANTS
     * ==================
     */

    /**
     * A constant for the SongScanner class.
     */
    public static final String SONG_SCANNER_CLASS = SongScanner.class
            .getSimpleName();

    /**
     * A constant for the HomeScreenActivity class.
     */
    public static final String HOME_SCREEN_CLASS = HomeScreenActivity.class
            .getSimpleName();

    /**
     * A constant for the GraceNoteHelper class.
     */
    public static final String GRACENOTE_CLASS = GracenoteHelper.class
            .getSimpleName();

    /**
     * A constant for the MetadataCleaner class.
     */
    public static final String METADATA_CLASS = MetadataCleaner.class
            .getSimpleName();

    /**
     * A constant for the DbHelper class.
     */
    public static final String DB_CLASS = DbHelper.class.getSimpleName();

    /**
     * A constant for the FaceScanner class.
     */
    public static final String FACE_SCANNER_CLASS = FaceScanner.class
            .getSimpleName();

    /**
     * =====================
     * APPLICATION CONSTANTS
     * =====================
     */

    /**
     * A constant for defining the extra padding offset.
     */
    public static final int BITMAP_EXTRA_PADDING = 10;

    /**
     * A constant for defining a glova
     */
    public static final int INTENT_REQUEST_CODE = 1;

    /**
     * A constant for maintaining the MP3 file extension.
     */
    public static final String MP3_EXTENSION = ".mp3";

    /**
     * A constant for the global application-level permission request code
     * constant.
     */
    public static final int PERMISSION_REQUEST_CODE = 1;

    /**
     * A constant for 'null'.
     */
    public static final String NULL = "null";

    /**
     * A constant for user ID key.
     */
    public static final String USER_ID = "user_id";

    /**
     * A constant for SharedPreferences.
     */
    public static final String MOOD_CONSTANT = "mood";

    /**
     * File separator constant.
     */
    public static final String FILE_SEPARATOR = "/";

    /**
     * Constant for image file name.
     */
    public static final String IMAGE_FILE_NAME = "MoodTunesImage.jpg";

    /**
     * Constant for first run key.
     */
    public static final String FIRST_TIME_RUN = "first_time_run";

    /**
     * Constant for first time run status.
     */
    public static final String FIRST_TIME_STATUS = "status";

    /**
     * Level 1 variance key.
     */
    public static final String LEVEL_ONE_VARIANCE = "lvl_1";

    /**
     * Level 2 variance key.
     */
    public static final String LEVEL_TWO_VARIANCE = "lvl_2";

    /**
     * Level 3 variance key.
     */
    public static final String LEVEL_THREE_VARIANCE = "lvl_3";

    /**
     * Level 4 variance key.
     */
    public static final String LEVEL_FOUR_VARIANCE = "lvl_4";

    /**
     * Level 5 variance key.
     */
    public static final String LEVEL_FIVE_VARIANCE = "lvl_5";

    /**
     * Level 6 variance key.
     */
    public static final String LEVEL_SIX_VARIANCE = "lvl_6";

    /**
     * Level 7 variance key.
     */
    public static final String LEVEL_SEVEN_VARIANCE = "lvl_7";

    /**
     * Level 8 variance key.
     */
    public static final String LEVEL_EIGHT_VARIANCE = "lvl_8";

    /**
     * Level 9 variance key.
     */
    public static final String LEVEL_NINE_VARIANCE = "lvl_9";

    /**
     * Level 10 variance key.
     */
    public static final String LEVEL_TEN_VARIANCE = "lvl_10";

    /**
     * ===================
     * DATABASE CONSTANTS
     * ===================
     */

    /**
     * DB name.
     */
    public static final String DB_NAME = "MoodTunes";

    /**
     * Table name.
     */
    public static final String TABLE_NAME = "MoodTunes_Table";

    /**
     * DB version.
     */
    public static final int DB_VERSION = 1;

    /**
     * Column 1 name.
     */
    public static final String COLUMN_1 = "SONG_NAME";

    /**
     * Column 2 name.
     */
    public static final String COLUMN_2 = "ORIGINAL_SONG_NAME";

    /**
     * Column 3 name.
     */
    public static final String COLUMN_3 = "ARTIST_NAME";

    /**
     * Column 4 name.
     */
    public static final String COLUMN_4 = "SONG_PATH";

    /**
     * Column 5 name.
     */
    public static final String COLUMN_5 = "LEVEL_ONE_MOOD";

    /**
     * Column 6 name.
     */
    public static final String COLUMN_6 = "LEVEL_TWO_MOOD";


    /**
     * =============================
     * GRACENOTE SPECIFIC CONSTANTS
     * =============================
     */

    /**
     * Gracenote Application name.
     */
    public static final String APP_NAME = "MoodTunes";

    /**
     * Client ID.
     */
    public static final String CLIENT_ID = "1778390009";

    /**
     * Client Tag.
     */
    public static final String CLIENT_TAG = "BE1D405F29BC86994F2FE8EE2374C384";
}
