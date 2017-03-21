package utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp4.atom.Mp4AlacBox;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pojos.Mp3Song;

/**
 * <hr>
 *
 * This is a class that helps remove unwanted metadata from the songs' ID3
 * tags.<br>
 *
 * <hr>
 *
 * The class is instantiated via an {@link IntentService}. After finishing the
 * respective processing, the class sends a broadcast back to the
 * {@link com.moodtunes.moodtunes_v2.HomeScreenActivity Main Activity}.<br>
 *
 * <hr>
 *
 * The main working principle of this class is the use of Regular Expressions.
 * It uses the {@link Pattern} and {@link Matcher} classes from the
 * {@link java.util.regex} package.
 */
public class MetadataCleaner extends IntentService {

    /**
     * A {@link List} of {@link Mp3Song MP3} songs.
     */
    private List<Mp3Song> songsMap;

    /**
     * Default constructor. Every IntentService must have one.
     */
    public MetadataCleaner() {
        super("MetadataCleaner");
    }

    /**
     * Regex String.
     */
    private final String mREGEX = "([\\d\\s\\-]+)?([\\w\\s\\.\\&\\d]+)([\\-\\(\\)a-zA-Z0-9\\.\\[\\]\\{\\}]+)?";

    /**
     * Compiled {@link Pattern}.
     */
    private Pattern pattern = Pattern.compile(mREGEX);

    /**
     *
     * A method extract ID3 tag values from an MP3 file and update the list of
     * {@link Mp3Song} objects.
     *
     * @param songs {@link List} of {@link Mp3Song}.
     *
     * @return {@link List} of filtered {@link Mp3Song songs}.
     */
    private List<Mp3Song> validateSongs(List<Mp3Song> songs) {
        File f = null;
        AudioFile audioFile = null;
        Tag tag = null;
        List<Mp3Song> filteredSongs = new ArrayList<>();

        Log.d(Constants.METADATA_CLASS, "--- BEGINNING OF METADATA CLASS ---");

        for (Mp3Song song : songs) {
            String path = song.getSongPath();
            try {
                audioFile = AudioFileIO.read(new File(path));
                if (audioFile != null) {
                    tag = audioFile.getTag();
                    // Get artist
                    if (tag == null) {
                        Log.d(Constants.METADATA_CLASS, path + " has no tag");
                    } else {
                        Mp3Song mp3Song = new Mp3Song();
                        mp3Song.setSongPath(song.getSongPath());
                        mp3Song.setOriginalSongName(song.getOriginalSongName());
                        mp3Song.setSongArtist(tag.getFirst(FieldKey.ARTIST));
                        mp3Song.setSongName(tag.getFirst(FieldKey.TITLE));

                        Log.d(Constants.METADATA_CLASS, "Adding to filtered"
                                + "list : " + mp3Song.toString());

                        filteredSongs.add(mp3Song);
                    }
                }
            } catch (CannotReadException cne) {
                cne.printStackTrace();
            } catch (TagException te) {
                te.printStackTrace();
            } catch (ReadOnlyFileException rofe) {
                rofe.printStackTrace();
            } catch (InvalidAudioFrameException iafe) {
                iafe.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return filteredSongs;
    }

    /**
     * Callback for entry point of IntentService.
     *
     * @param intent Incoming {@link Intent}.
     */
    @Override
    protected void onHandleIntent(final Intent intent) {

        Gson gson = new GsonBuilder().create();
        Bundle data = intent.getExtras();

        if (data != null && data.containsKey("SONGS_LIST")) {
            Type typeOfMap = new TypeToken<List<Mp3Song>>() { }.getType();
            songsMap = gson.fromJson(data.getString("SONGS_LIST"), typeOfMap);
            Log.d(Constants.METADATA_CLASS, "Received list: " + songsMap);
        } else {
            Log.d(Constants.METADATA_CLASS, "Received erroneous data.");
        }

        List<Mp3Song> result = validateSongs(songsMap);

        // here, key = artist, value = title
        List<Mp3Song> reFilteredList = cleanMetadata(result);

        Intent newIntent = new Intent();
        newIntent.setAction(getClass().getSimpleName());
        String flattenedData = gson.toJson(reFilteredList);
        newIntent.putExtra("NEW_SONGS_LIST", flattenedData);

        Log.d(Constants.METADATA_CLASS, "--- END OF METADATA CLASS ---");

        sendBroadcast(newIntent);
    }

    /**
     * A method to verify integrity of ID3 tags. The <b>Artist</b> and the
     * <b>Title</b> tags are matched against the compiled regular expression.
     * If they match, their <i>2<sup>nd</sup> group</i> is extracted and
     * returned
     *
     * @param artist Artist string.
     * @param title Title string.
     * @return Array of {@link String artist and title}<br>
     *     <i>Array[0] - filtered Artist</i><br>
     *     <i>Array[1] - filtered Title</i>
     */
    private String[] isOk(final String artist, final String title) {

        Matcher artistMatcher = pattern.matcher(artist);
        Matcher titleMatcher = pattern.matcher(title);

        if (artistMatcher.matches() && titleMatcher.matches()) {
            if (!(Constants.NULL).equals(artistMatcher.group(2))
                    && !(Constants.NULL).equals(titleMatcher.group(2))) {

                Log.d(Constants.METADATA_CLASS, "Title (Regex): "
                        + titleMatcher.group(2) + ", Artist (Regex): "
                        + artistMatcher.group(2));

                return new String[]{artistMatcher.group(2),
                        titleMatcher.group(2)};
            }
        }

        return null;
    }

    /**
     * A method that calls the {@code isOk} method to verify data integrity.
     *
     * @param result {@link List} of {@link Mp3Song songs}.
     *
     * @return Filtered {@link List} of {@link Mp3Song songs}.
     */
    private List<Mp3Song> cleanMetadata(List<Mp3Song> result) {
        List<Mp3Song> filteredList = new ArrayList<>();

        for (Mp3Song song : result) {
            if (!song.getSongArtist().isEmpty() && !song.getSongName().isEmpty()) {

                String[] resultant = isOk(song.getSongArtist(), song.getSongName());
                if (resultant != null) {

                    Log.d(Constants.METADATA_CLASS, "Before metadata cleaning:"
                            + "\nArtist: " + song.getSongArtist() + ", Title: "
                            + song.getSongName());

                    song.setSongArtist(resultant[0]);
                    song.setSongName(resultant[1]);

                    Log.d(Constants.METADATA_CLASS, "After metadata cleaning:"
                            + "\nArtist: " + song.getSongArtist() + ", Title: "
                            + song.getSongName());

                    filteredList.add(song);
                }
            }
        }

        return filteredList;
    }
}
