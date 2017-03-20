package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pojos.Mp3Song;
import radams.gracenote.webapi.GracenoteException;
import radams.gracenote.webapi.GracenoteMetadata;
import radams.gracenote.webapi.GracenoteMetadataOET;
import radams.gracenote.webapi.GracenoteWebAPI;

public class GracenoteHelper {

    private List<Mp3Song> thisMap;
    private Context context;
    private String userId;
    private List<Mp3Song> mapper = new ArrayList<>();

    public GracenoteHelper(final List<Mp3Song> songsMap, final Context appContext) {
        this.thisMap = songsMap;
        context = appContext;
    }

    private boolean isFirstRun() {
        SharedPreferences prefs = context.getSharedPreferences(Constants.FIRST_TIME_RUN,
                Context.MODE_PRIVATE);

        return prefs.getBoolean(Constants.FIRST_TIME_STATUS, true);
    }

    private void registerGraceNote() {
        GracenoteWebAPI webApi = null;
        try {
            webApi = new GracenoteWebAPI(Constants.CLIENT_ID, Constants.CLIENT_TAG);
            userId = webApi.register();
            Log.d(Constants.GRACENOTE_CLASS, "UserID is " + userId);
        } catch (GracenoteException exc) {
            exc.printStackTrace();
        }

        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.FIRST_TIME_RUN,
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.FIRST_TIME_RUN, false);
        editor.apply();
    }

    public List<Mp3Song> initGracenote() {

        if (isFirstRun()){
            Log.d(Constants.GRACENOTE_CLASS, "First Run!");
            registerGraceNote();
        } else {
            Log.d(Constants.GRACENOTE_CLASS, "Not first run!");
        }

        GracenoteWebAPI webAPI = null;

        try {

            webAPI = new GracenoteWebAPI(Constants.CLIENT_ID, Constants.CLIENT_TAG, userId);
            for (Mp3Song song : thisMap) {

                GracenoteMetadata data = webAPI.searchTrack(song.getSongName(), "", song.getSongArtist());

                ArrayList<GracenoteMetadataOET> moods =
                        (ArrayList<GracenoteMetadataOET>)
                                data.getAlbumData(0, Constants.MOOD_CONSTANT);

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
                }

                mapper.add(song);
            }


        } catch (GracenoteException gne) {
            gne.printStackTrace();
        }

        return mapper;
    }
}
