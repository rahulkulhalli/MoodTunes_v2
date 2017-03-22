package utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * A dedicated class to recurse over all the songs in the phone
 * and return the list of all available ones.
 *
 * <hr>
 *
 * This class is designed from an {@link android.os.AsyncTask AsyncTask}
 * perspective, i.e., the class' main entry point is triggered in an
 * {@link android.os.AsyncTask AsyncTask}.
 *
 * <hr>
 *
 * Do <b>NOT</b> do any UI-related work here!
 *
 */
public class SongScanner {

    /**
     * A {@link Map} for storing the Song name and Path.
     */
    private Map<String, String> songsMap;

    /**
     * A <i>recursive</i> method that scans the directories from the specified
     * starting point (In our case, the root).
     *
     * @param root The starting point of recursion.
     */
    private void scanSongs(final File root) {

        String[] currentLevelFiles = root.list();

        if (currentLevelFiles.length > 0) {
            for (int i = 0; i < currentLevelFiles.length; i++) {
                File file = new File(root
                        + Constants.FILE_SEPARATOR + currentLevelFiles[i]);

                if (file.isFile()) {
                    if (file.getName().endsWith(Constants.MP3_EXTENSION)
                            || file.getName().endsWith(Constants
                            .MP3_EXTENSION.toUpperCase())) {
                        songsMap.put(file.getName(), file.getAbsolutePath());
                    }
                } else if (file.isDirectory()) {
                    scanSongs(file);
                }
            }
        }
    }

    /**
     * This is the entry point of the class.
     *
     * <hr>
     *
     * It defines the root of the recursive scan, calls the {@code scanSongs}
     * method, and returns the final {@link Map} of songs.
     *
     * @return The final {@link Map} of songs.
     */
    public Map<String, String> init() {
        Log.d(Constants.SONG_SCANNER_CLASS, "In SongScanner.");

        songsMap = new HashMap<>();

        File root = Environment.getExternalStorageDirectory();

        scanSongs(root);

        return songsMap;
    }
}
