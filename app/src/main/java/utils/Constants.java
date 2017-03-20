package utils;

import com.moodtunes.moodtunes_v2.FaceScanner;
import com.moodtunes.moodtunes_v2.HomeScreenActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    /**
     * LOGGING CONSTANTS
     */

    public static final String SONG_SCANNER_CLASS = SongScanner.class.getSimpleName();

    public static final String HOME_SCREEN_CLASS = HomeScreenActivity.class.getSimpleName();

    public static final String GRACENOTE_CLASS = GracenoteHelper.class.getSimpleName();

    public static final String METADATA_CLASS = MetadataCleaner.class.getSimpleName();

    public static final String DB_CLASS = DbHelper.class.getSimpleName();

    public static final String FACE_SCANNER_CLASS = FaceScanner.class.getSimpleName();

    /**
     * APPLICATION CONSTANTS
     */

    public static final int BITMAP_EXTRA_PADDING = 10;

    public static final int INTENT_REQUEST_CODE = 1;

    public static final String MP3_EXTENSION = ".mp3";

    public static final int PERMISSION_REQUEST_CODE = 1;

    public static final String NULL = "null";

    public static final String MOOD_CONSTANT = "mood";

    public static final String FILE_SEPARATOR = "/";

    public static final String IMAGE_FILE_NAME = "MoodTunesImage.jpg";

    public static final String FIRST_TIME_RUN = "first_time_run";

    public static final String FIRST_TIME_STATUS = "status";

    public static final String LEVEL_ONE_VARIANCE = "lvl_1";
    public static final String LEVEL_TWO_VARIANCE = "lvl_2";
    public static final String LEVEL_THREE_VARIANCE = "lvl_3";
    public static final String LEVEL_FOUR_VARIANCE = "lvl_4";
    public static final String LEVEL_FIVE_VARIANCE = "lvl_5";
    public static final String LEVEL_SIX_VARIANCE = "lvl_6";
    public static final String LEVEL_SEVEN_VARIANCE = "lvl_7";
    public static final String LEVEL_EIGHT_VARIANCE = "lvl_8";
    public static final String LEVEL_NINE_VARIANCE = "lvl_9";
    public static final String LEVEL_TEN_VARIANCE = "lvl_10";


    public class LevelOneMoods {
        private Map<String, List<String>> levelOneMoods = new HashMap<>();

        public Map<String, List<String>> getLevelOneMoods() {
            addIntoMap();
            return levelOneMoods;
        }

        private void addIntoMap() {
            // level 1 valiance
            List<String> levelOneValiance = new ArrayList<>();
            levelOneValiance.add("Somber");
            levelOneValiance.add("Gritty");
            levelOneValiance.add("Serious");
            levelOneValiance.add("Brooding");
            levelOneValiance.add("Aggressive");
            levelOneMoods.put(LEVEL_ONE_VARIANCE, levelOneValiance);

            // level 2 valiance
            List<String> levelTwoValiance = new ArrayList<>();
            levelTwoValiance.add("Melancholy");
            levelTwoValiance.add("Cool");
            levelTwoValiance.add("Yearning");
            levelTwoValiance.add("Urgent");
            levelTwoValiance.add("Defiant");
            levelOneMoods.put(LEVEL_TWO_VARIANCE, levelTwoValiance);

            // level 3 valiance
            List<String> levelThreeValiance = new ArrayList<>();
            levelThreeValiance.add("Sentimental");
            levelThreeValiance.add("Sophisticated");
            levelThreeValiance.add("Sensual");
            levelThreeValiance.add("Fiery");
            levelThreeValiance.add("Energizing");
            levelOneMoods.put(LEVEL_THREE_VARIANCE, levelThreeValiance);

            // level 4 valiance
            List<String> levelFourValiance = new ArrayList<>();
            levelFourValiance.add("Tender");
            levelFourValiance.add("Romantic");
            levelFourValiance.add("Empowering");
            levelFourValiance.add("Stirring");
            levelFourValiance.add("Rowdy");
            levelOneMoods.put(LEVEL_FOUR_VARIANCE, levelFourValiance);

            // level 5 valiance
            List<String> levelFiveValiance = new ArrayList<>();
            levelFiveValiance.add("Peaceful");
            levelFiveValiance.add("Easygoing");
            levelFiveValiance.add("Upbeat");
            levelFiveValiance.add("Lively");
            levelFiveValiance.add("Excited");
            levelOneMoods.put(LEVEL_FIVE_VARIANCE, levelFiveValiance);
        }
    }

    public class LevelTwoMoods {
        private Map<String, List<String>> levelTwoMoods = new HashMap<>();

        private Map<String, List<String>> getLevelTwoMoods() {
            addIntoMap();
            return  levelTwoMoods;
        }

        private void addIntoMap() {
            //level one variance

            List<String> list = new ArrayList<>();

            list.add("Dark Cosmic");
            list.add("Creepy / Ominous");
            list.add("Depressed / Lonely");
            list.add("Gritty / Soulful");
            list.add("Serious / Cerebral");
            list.add("Thrilling");
            list.add("Dreamy Brooding");
            list.add("Alienated / Brooding");
            list.add("Chaotic / Intense");
            list.add("Aggressive Power");
            levelTwoMoods.put(LEVEL_ONE_VARIANCE, list);

            list.clear();

            //level two variance
            List<String> lvl2 = new ArrayList<>();
            list.add("Solemn / Spiritual");
            list.add("Enigmatic / Mysterious");
            list.add("Sober / Determined");
            list.add("Strumming Yearning");
            list.add("Melodramatic");
            list.add("Hypnotic Rhythm");
            list.add("Evocative / Intriguing");
            list.add("Energetic Melancholy");
            list.add("Dark Hard Beat");
            list.add("Heavy Triumphant");
            levelTwoMoods.put(LEVEL_TWO_VARIANCE, list);

            list.clear();

            //level three variance
            list.add("Wistful / Forlorn");
            list.add("Sad / Soulful");
            list.add("Cool Confidence");
            list.add("Dark Groovy");
            list.add("Sensitive / Exploring");
            list.add("Energetic Dreaming");
            list.add("Dark Urgent");
            list.add("Energetic Anxious");
            list.add("Attitude / Defiant");
            list.add("Hard Dark Excitement");
            levelTwoMoods.put(LEVEL_THREE_VARIANCE, list);

            list.clear();

            //level four variance
            list.add("Mysterious / Dreamy");
            list.add("Light Melancholy");
            list.add("Casual Groove");
            list.add("Wary / Defiant");
            list.add("Bittersweet Pop");
            list.add("Energetic Yearning");
            list.add("Dark Pop");
            list.add("Dark Pop Intensity");
            list.add("Heavy Brooding");
            list.add("Hard Positive Excitement");
            levelTwoMoods.put(LEVEL_FOUR_VARIANCE, list);

            list.clear();

            //level five variance
            list.add("Lyrical Sentimental");
            list.add("Cool Melancholy");
            list.add("Intimate Bittersweet");
            list.add("Smoky / Romantic");
            list.add("Dreamy Pulse");
            list.add("Intimate");
            list.add("Passionate Rhythm");
            list.add("Energy Abstract Groove");
            list.add("Edgy / Sexy");
            list.add("Abstract Beat");
            levelTwoMoods.put(LEVEL_FIVE_VARIANCE, list);

            list.clear();

            //level six variance
            list.add("Tender / Sincere");
            list.add("Gentle Bittersweet");
            list.add("Suave / Sultry");
            list.add("Dark Playful");
            list.add("Soft Soulful");
            list.add("Sensual Groove");
            list.add("Dark Sparkling Lyrical");
            list.add("Fiery Groove");
            list.add("Arousing");
            list.add("Heavy Beat");
            levelTwoMoods.put(LEVEL_SIX_VARIANCE, list);

            list.clear();

            //level seven variance
            list.add("Romantic / Lyrical");
            list.add("Light Groovy");
            list.add("Dramatic / Romantic");
            list.add("Lush / Romantic");
            list.add("Dramatic Emotion");
            list.add("idealistic / Stirring");
            list.add("Focused Sparkling");
            list.add("Triumphant / Rousing");
            list.add("Confident / Tough");
            list.add("Driving Dark Groove");
            levelTwoMoods.put(LEVEL_SEVEN_VARIANCE, list);

            list.clear();

            //level eight variance
            list.add("Refined / Mannered");
            list.add("Awakening / Stately");
            list.add("Sweet / Sincere");
            list.add("Heartfelt Passion");
            list.add("Strong / Stable");
            list.add("Powerful / Heroic");
            list.add("Invigorating / Joyous");
            list.add("Jubilant / Soulful");
            list.add("Ramshackle / Rollicking");
            list.add("Wild / Rowdy");
            levelTwoMoods.put(LEVEL_EIGHT_VARIANCE, list);

            list.clear();

            //level nine variance
            list.add("Reverent / Healing");
            list.add("Quiet / Introspective");
            list.add("Friendly");
            list.add("Charming / Easygoing");
            list.add("Soulful / Easygoing");
            list.add("Happy / Soulful");
            list.add("Playful / Swingin'");
            list.add("Exuberant / Festive");
            list.add("Upbeat Pop Groove");
            list.add("Happy Excitement");
            levelTwoMoods.put(LEVEL_NINE_VARIANCE, list);

            list.clear();

            //level ten variance
            list.add("Pastoral / Serene");
            list.add("Delicate / Tranquil");
            list.add("Hopeful / Breezy");
            list.add("Cheerful / Playful");
            list.add("Carefree Pop");
            list.add("Party / Fun");
            list.add("Showy / Rousing");
            list.add("Lusty / Jaunty");
            list.add("Loud Celebratory");
            list.add("Euphoric Energy");
            levelTwoMoods.put(LEVEL_TEN_VARIANCE, list);
        }
    }

    /**
     * DATABASE CONSTANTS
     */

    public static final String DB_NAME = "MoodTunes";

    public static final String TABLE_NAME = "MoodTunes_Table";

    public static final int DB_VERSION = 1;

    public static final String COLUMN_1 = "SONG_NAME";

    public static final String COLUMN_2 = "ARTIST_NAME";

    public static final String COLUMN_3 = "SONG_PATH";

    public static final String COLUMN_4 = "LEVEL_ONE_MOOD";

    public static final String COLUMN_5 = "LEVEL_TWO_MOOD";


    /**
     * GRACENOTE SPECIFIC CONSTANTS
     */

    public static final String APP_NAME = "MoodTunes";

    public static final String CLIENT_ID = "1778390009";

    public static final String CLIENT_TAG = "BE1D405F29BC86994F2FE8EE2374C384";
}
