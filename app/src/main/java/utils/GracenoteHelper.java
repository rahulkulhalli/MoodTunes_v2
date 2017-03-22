package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pojos.Mp3Song;
import radams.gracenote.webapi.GracenoteException;
import radams.gracenote.webapi.GracenoteMetadata;
import radams.gracenote.webapi.GracenoteMetadataOET;
import radams.gracenote.webapi.GracenoteWebAPI;

/**
 * A class that queries the final {@link List} of {@link Mp3Song songs} using
 * the Gracenote API (Credits - Richard Adams).<br>
 *
 * <hr>
 *
 * This class is designed with the intention of executing it's main method
 * via an {@link android.os.AsyncTask AsyncTask}.
 *
 * <hr>
 *
 * Repetitive network calls are <i>extremely</i> time consuming when run on the
 * main UI thread. Hence, this class' entry method is executing from within an
 * {@link android.os.AsyncTask AsyncTask}.
 */
public class GracenoteHelper {
    /**
     * {@link List} of {@link Mp3Song songs}.
     */
    private List<Mp3Song> thisMap;

    /**
     * Application {@link Context}.
     */
    private Context context;

    /**
     * Gracenote User ID.
     */
    private String userId;

    /**
     * {@link List} of {@link Mp3Song songs} that captures the incoming list.
     */
    private List<Mp3Song> mapper = new ArrayList<>();

    /**
     * The 2-arg constructor.
     *
     * @param songsMap The {@link List} of {@link Mp3Song songs} from
     *                 {@link com.moodtunes.moodtunes_v2.HomeScreenActivity}.
     * @param appContext The Application {@link Context}.
     */
    public GracenoteHelper(final List<Mp3Song> songsMap,
                           final Context appContext) {
        this.thisMap = songsMap;
        context = appContext;
    }

    /**
     * A method to check if the application is on its first run. If it is, the
     * user ID is queried <b>ONLY ONCE</b>. If it's not the first run, the
     * user ID is simply retrieved.
     *
     * @return {@link Boolean} indicating status of run.
     */
    private boolean isFirstRun() {
        SharedPreferences prefs = context.getSharedPreferences(Constants
                .FIRST_TIME_RUN, Context.MODE_PRIVATE);

        return prefs.getBoolean(Constants.FIRST_TIME_STATUS, true);
    }

    /**
     * A method for registering with the Gracenote servers. This method is
     * triggered <b>ONLY</b> if this is the application's first run.
     *
     * <hr>
     *
     * Upon triggering this method, the user ID is generated and written to
     * the {@link SharedPreferences}, along with a boolean that indicates that
     * the first run of the application has been marked.
     *
     */
    private void registerGraceNote() {
        GracenoteWebAPI webApi;
        try {
            webApi = new GracenoteWebAPI(Constants.CLIENT_ID,
                    Constants.CLIENT_TAG);
            userId = webApi.register();
            Log.d(Constants.GRACENOTE_CLASS, "UserID is " + userId);
        } catch (GracenoteException exc) {
            exc.printStackTrace();
        }

        SharedPreferences.Editor editor = context
                .getSharedPreferences(Constants.FIRST_TIME_RUN,
                        Context.MODE_PRIVATE).edit();
        // Add indication that first run is done.
        editor.putBoolean(Constants.FIRST_TIME_RUN, false);

        // Add user ID.
        editor.putString(Constants.USER_ID, userId);
        editor.apply();
    }

    /**
     * The main entry point of this class.
     *
     * <hr>
     *
     * This method will first check whether the application is on its first
     * run.<br>
     * If <b>yes</b>, the {@code registerGracenote} method is called, and the
     * user is registered.<br>
     * If <b>no</b>, the {@code user ID} is retrieved from the
     * {@link SharedPreferences}.<br>
     *
     * <i>This is done primarily to minimize API usage.</i><br>
     *
     * <hr>
     *
     * The second objective of this method is to query each song with the
     * Gracenote API and retrieve moods. Each song can have 0, 1, or 2 moods.
     * <br>
     *
     * <hr>
     *
     * Upon receiving these moods, the method will accordingly update the
     * {@link Mp3Song} objects and return the {@link List}.
     *
     * @return The updated {@link List} of {@link Mp3Song songs}.
     */
    public List<Mp3Song> initGracenote() {

        if (isFirstRun()) {
            Log.d(Constants.GRACENOTE_CLASS, "First Run!");
            registerGraceNote();
        } else {
            Log.d(Constants.GRACENOTE_CLASS, "Not first run!");

            // Get a reference to SharedPreferences.
            SharedPreferences myPrefs = context
                    .getSharedPreferences(Constants.FIRST_TIME_RUN,
                            Context.MODE_PRIVATE);

            // Retrieve user ID from SharedPreferences.
            userId = myPrefs.getString(Constants.USER_ID, Constants.NULL);

        }

        GracenoteWebAPI webAPI;

        try {
            // Instantiate the API.
            webAPI = new GracenoteWebAPI(Constants.CLIENT_ID,
                    Constants.CLIENT_TAG, userId);
            for (Mp3Song song : thisMap) {
                // Query each song in the list.
                GracenoteMetadata data = webAPI
                        .searchTrack(song.getSongName(), "",
                                song.getSongArtist());

                // Get the list of moods for each song (Explicit cast required).
                ArrayList<GracenoteMetadataOET> moods =
                        (ArrayList<GracenoteMetadataOET>)
                                data.getAlbumData(0, Constants.MOOD_CONSTANT);

                // Switch on the number of moods detected.
                switch (moods.size()) {
                    case 0:
                        Log.d(Constants.GRACENOTE_CLASS, song.getSongName()
                                + " has no moods associated.");
                        break;

                    case 1:
                        Log.d(Constants.GRACENOTE_CLASS, song.getSongName()
                                + " has one mood associated.");
                        song.setLevelOneMood(moods.get(0).getText());
                        break;

                    case 2:
                        Log.d(Constants.GRACENOTE_CLASS, song.getSongName()
                                + " has two moods associated.");
                        song.setLevelOneMood(moods.get(0).getText());
                        song.setLevelTwoMood(moods.get(0).getText());
                        break;

                    default:
                        Log.d(Constants.GRACENOTE_CLASS, "Unexpected no. of "
                                + "moods detected for " + song.getSongName());
                }

                // Add the final song to the list.
                mapper.add(song);
            }


        } catch (GracenoteException gne) {
            gne.printStackTrace();
        }

        // Return the list.
        return mapper;
    }
}
