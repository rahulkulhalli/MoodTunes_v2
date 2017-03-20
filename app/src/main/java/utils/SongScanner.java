package utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * A dedicated class to recurse over all the songs in the phone
 * and return the list of all available ones.
 *
 * Do NOT do any UI-related work here!
 *
 */
public class SongScanner {

    private Map<String, String> songsMap;

    private void scanSongs(final File root) {

        String[] currentLevelFiles = root.list();

        if (currentLevelFiles.length > 0) {
            for (int i = 0; i < currentLevelFiles.length; i++) {
                File file = new File(root
                        + Constants.FILE_SEPARATOR + currentLevelFiles[i]);

                if (file.isFile())
                {
                    if (file.getName().endsWith(Constants.MP3_EXTENSION)
                            || file.getName().endsWith(Constants
                            .MP3_EXTENSION.toUpperCase())) {
                        songsMap.put(file.getName(), file.getAbsolutePath());
                    }
                }

                else if (file.isDirectory()) {
                    scanSongs(file);
                }
            }
        }
    }

    public Map<String, String> init() {
        Log.d(Constants.SONG_SCANNER_CLASS, "In SongScanner.");

        songsMap = new HashMap<>();

        File root = Environment.getExternalStorageDirectory();

        scanSongs(root);

        return songsMap;
    }
}
