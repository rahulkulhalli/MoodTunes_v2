package com.moodtunes.moodtunes_v2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pojos.Mp3Song;
import utils.Constants;
import utils.DbHelper;
import utils.GracenoteHelper;
import utils.MetadataCleaner;
import utils.SongScanner;

/**
 * The entry point of the entire application.
 *
 * <hr>
 *
 * <b>Flow</b>:<br>
 *
 * 1) User scans songs in phone memory.<br>
 * 2) User updates this list by filtering songs with bad metadata.<br>
 * 3) User queries the filtered list of songs with Gracenote and updates each
 * song's mood(s).<br>
 * 4) User stores songs in the database.<br>
 * 5) User starts camera and captures his/her image<br>
 * 6) Based on the smiling probability, an appropriate SQL query is generated.
 * <br>
 * 7) Query is fired onto the database and relevant results are retrieved.<br>
 * 8) A playlist of these results is created and played.<br>
 *
 * <hr>
 *
 */
public class HomeScreenActivity extends AppCompatActivity {

    /**
     * A global {@link List} of {@link Mp3Song songs}.
     */
    private List<Mp3Song> songs = new ArrayList<>();

    /**
     * A global {@link List} of filtered {@link Mp3Song songs}.
     */
    private List<Mp3Song> filteredSongs = new ArrayList<>();

    /**
     * A global {@link List} of updated {@link Mp3Song songs}.
     */
    private List<Mp3Song> finalSongsList = new ArrayList<>();

    /**
     * A global variable for maintaining the list of songs with their
     * corresponding paths.
     */
    private Map<String, String> songsMap;

    /**
     * A global variable to keep track of execution time.
     */
    private long startTime;

    /**
     * Reference to the custom {@link BroadcastReceiver}.
     */
    private MyUiReceiver myUiReceiver;

    /**
     * A method which checks if required permissions are allowed.
     * If not, it requests for permissions.
     */
    private void checkForPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET,
                            Manifest.permission.CAMERA},
                    Constants.PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * The callback triggered after Permissions are granted.
     *
     * @param requestCode The request code.
     * @param permissions The array of permissions.
     * @param grantResults The results for each permission.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           final @NonNull String[] permissions,
                                           final @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
        if (requestCode == Constants.PERMISSION_REQUEST_CODE && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(Constants.HOME_SCREEN_CLASS, "Permissions granted.");
        }
    }

    /**
     * Triggered when Intent returns.
     *
     * @param requestCode The request code.
     * @param resultCode Result code. Can be either of RESULT_OK or
     *                   RESULT_NOT_OK.
     * @param data The incoming intent data.
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.INTENT_REQUEST_CODE
                && resultCode == RESULT_OK) {
            if (data != null) {
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory
                            .decodeStream(getContentResolver()
                                    .openInputStream(data.getData()));

                    FaceScanner scanner = new FaceScanner(this, bitmap);
                    scanner.initialize();
                    Bitmap croppedImage = scanner.cropImage();

                    double smilingProb = scanner
                            .getSmilingProbability(croppedImage)
                            * getResources().getInteger(R.integer.hundred);

                    Log.d(Constants.HOME_SCREEN_CLASS, "Smiling probability is "
                            + String.valueOf(smilingProb) + "%");

                } catch (FileNotFoundException fNfE) {
                    fNfE.printStackTrace();
                }
            }
        }
    }

    /**
     * Callback for when Activity is created.
     *
     * @param savedInstanceState Bundle of saved data.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Check permissions
        checkForPermissions();

        /**
         * Create a single reference to the {@link CustomListener}.
         */
        CustomListener customListener = new CustomListener();

        /**
         * Get reference of first button
         */
        final ImageView scanButton = (ImageView)
                findViewById(R.id.btn_scan);
        scanButton.setOnClickListener(customListener);

        final ImageView queryButton = (ImageView)
                findViewById(R.id.btn_gracenote);
        queryButton.setOnClickListener(customListener);

        final ImageView metadataButton = (ImageView)
                findViewById(R.id.btn_metadata);
        metadataButton.setOnClickListener(customListener);

        final ImageView dbButton = (ImageView)
                findViewById(R.id.btn_db);

        final ImageView cameraButton = (ImageView)
                findViewById(R.id.btn_camera);
        cameraButton.setOnClickListener(customListener);

        dbButton.setOnClickListener(customListener);
    }

    /**
     * Creates a file (if not present) in the External directory.
     * If file is present, returns pointer pointing to the file.
     *
     * @return Image file.
     */
    private File prepareFile() {
        return new File(Environment.getExternalStorageDirectory(),
                Constants.IMAGE_FILE_NAME);
    }

    /**
     * A helper method to verify the {@link Mp3Song song}'s integrity before
     * it is added to the database.
     *
     * @param song The {@link Mp3Song song} in question.
     * @return {@link Boolean} indicating the integrity of said
     * {@link Mp3Song song}.
     */
    private boolean isValidSong(final Mp3Song song) {

        if (song.getSongName() != null
                && song.getSongArtist() != null
                && song.getSongPath() != null
                && (song.getLevelOneMood() != null
                || song.getLevelTwoMood() != null)) {
            return true;
        }

        return false;
    }

    /**
     * A listener class that acts upon button clicks.
     */
    private class CustomListener implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            switch (view.getId()) {
                case R.id.btn_scan:
                    (new ScannerAsyncTask()).execute();
                    break;


                case R.id.btn_gracenote:
                    startTime = System.currentTimeMillis();
                    (new GracenoteAsyncTask()).execute();
                    break;


                case R.id.btn_metadata:
                    Intent serviceIntent = new Intent(HomeScreenActivity.this,
                            MetadataCleaner.class);
                    Gson gson = new GsonBuilder().create();
                    String flattenedSongs = gson.toJson(songs);
                    serviceIntent.putExtra("SONGS_LIST", flattenedSongs);
                    startService(serviceIntent);
                    break;


                case R.id.btn_db:

                    DbHelper helper = new
                            DbHelper(HomeScreenActivity.this);

                    // Always clear db first!
                    helper.clearAllData();

                    for (Mp3Song song : finalSongsList) {

                        Log.d(Constants.HOME_SCREEN_CLASS, "Final song : "
                         + song.toString());

                        if (song.getSongName().contains("'")) {
                            song.setSongName(song.getSongName()
                                    .replace("'", "''"));
                        }

                        if (song.getSongPath().contains("'")) {
                            song.setSongPath(song.getSongPath()
                                    .replace("'", "''"));
                        }

                        if (song.getSongArtist().contains("'")) {
                            song.setSongArtist(song.getSongArtist()
                                    .replace("'", "''"));
                        }

                        if (song.getLevelOneMood() != null && song
                                .getLevelOneMood().contains("'")) {
                            song.setLevelOneMood(song.getLevelOneMood()
                                    .replace("'", "''"));
                        }

                        if (song.getLevelTwoMood() != null && song
                                .getLevelTwoMood().contains("'")) {
                            song.setLevelTwoMood(song.getLevelTwoMood()
                                    .replace("'", "''"));
                        }

                        if (isValidSong(song)) {
                            Log.d(Constants.HOME_SCREEN_CLASS, "Adding "
                                    + song.getSongName() + " to the database.");

                            helper.addMp3Song(song);
                        } else {
                            Log.d(Constants.HOME_SCREEN_CLASS,
                                    song.getSongName() + "could not be added"
                                            + "to the database.");
                        }
                    }

                    break;

                case R.id.btn_camera:
                    Intent cameraIntent =
                            new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    Uri photoURI = FileProvider
                            .getUriForFile(HomeScreenActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            prepareFile());

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI);
                    startActivityForResult(cameraIntent,
                            Constants.INTENT_REQUEST_CODE);
                    break;

                default: break;
            }
        }
    }

    /**
     * A class extending {@link AsyncTask}.
     *
     * Handles the scanning of the songs.
     * Has a direct dependency on {@link SongScanner}.
     */
    private class ScannerAsyncTask extends AsyncTask<Void, Void, Void> {

        /**
         * A {@link ProgressDialog} to display an intermediate message.
         */
        private final ProgressDialog dialog =
                new ProgressDialog(HomeScreenActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait while we scan your song list...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            SongScanner scanner = new SongScanner();
            songsMap = scanner.init();
            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.cancel();
            Log.d(Constants.HOME_SCREEN_CLASS, "Size of song list "
                    + "== " + songsMap.size());

            for (Map.Entry<String, String> set : songsMap.entrySet()) {
                Mp3Song song = new Mp3Song();
                song.setOriginalSongName(set.getKey());
                song.setSongPath(set.getValue());
                songs.add(song);
            }

            Log.d(Constants.HOME_SCREEN_CLASS, "Added songs to list");
        }
    }

    /**
     * Triggered when Activity starts.
     */
    @Override
    protected void onStart() {
        myUiReceiver = new MyUiReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MetadataCleaner.class.getSimpleName());
        registerReceiver(myUiReceiver, intentFilter);
        super.onStart();
    }

    /**
     * Triggered when Activity stops.
     */
    @Override
    protected void onStop() {
        unregisterReceiver(myUiReceiver);
        super.onStop();
    }

    /**
     * A custom Receiver class to act up on all broadcasts received.
     */
    private class MyUiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (MetadataCleaner.class.getSimpleName()
                    .equals(intent.getAction())) {
                Log.d(Constants.HOME_SCREEN_CLASS, "Broadcast received!");

                Bundle data = intent.getExtras();
                if (data != null) {
                    Gson gson = new GsonBuilder().create();
                    Type typeOfMap = new TypeToken<List<Mp3Song>>() { }
                            .getType();

                    filteredSongs = gson.fromJson(data
                            .getString("NEW_SONGS_LIST"), typeOfMap);

                    Log.d(Constants.HOME_SCREEN_CLASS,
                            "Filtered song list size: "
                            + String.valueOf(filteredSongs.size()));

                    for (Mp3Song song : filteredSongs) {
                        Log.d(Constants.HOME_SCREEN_CLASS, song.getSongName()
                                + ":" + song.getSongArtist());
                    }
                }
            }
        }
    }

    /**
     * A class that implements Gracenote calls in Async fashion.
     */
    private class GracenoteAsyncTask extends AsyncTask<Void, Integer,
            List<Mp3Song>> {

        /**
         * A private class member to store the context.
         */
        private Context appContext;

        /**
         * A private class member to refer to the {@link ProgressDialog}.
         */
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            appContext = HomeScreenActivity.this;
            pDialog = new ProgressDialog(appContext);
            pDialog.setMessage("Please wait, querying...");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(final List<Mp3Song> aMap) {
            super.onPostExecute(aMap);
            long endTime = System.currentTimeMillis();
            Log.d(Constants.HOME_SCREEN_CLASS, "Total time for"
                    + " Gracenote querying: "
                    + String.valueOf(endTime - startTime) + "ms");
            Log.d(Constants.HOME_SCREEN_CLASS, "Received callback from "
                    + Constants.GRACENOTE_CLASS);

            finalSongsList = aMap;

            pDialog.cancel();
        }

        @Override
        protected List<Mp3Song>
        doInBackground(final Void... voids) {
            GracenoteHelper helper =
                    new GracenoteHelper(filteredSongs, appContext);
            return helper.initGracenote();
        }
    }
}
