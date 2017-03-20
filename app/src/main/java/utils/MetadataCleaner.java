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

public class MetadataCleaner extends IntentService {

    private List<Mp3Song> songsMap;

    public MetadataCleaner() {
        super("MetadataCleaner");
    }

    private final String REGEX = "([\\d\\s\\-]+)?([\\w\\s\\.\\&\\d]+)([\\-\\(\\)a-zA-Z0-9\\.\\[\\]\\{\\}]+)?";

    private Pattern pattern = Pattern.compile(REGEX);

    private List<Mp3Song> validateSongs(final List<Mp3Song> songs) {
        File f = null;
        AudioFile audioFile = null;
        Tag tag = null;
        List<Mp3Song> filteredSongs = new ArrayList<>();

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

    @Override
    protected void onHandleIntent(Intent intent) {

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
        sendBroadcast(newIntent);
    }

    private String[] isOk(String artist, String title) {

        Matcher artistMatcher = pattern.matcher(artist);
        Matcher titleMatcher = pattern.matcher(title);

        if (artistMatcher.matches() && titleMatcher.matches()) {
            if (!(Constants.NULL).equals(artistMatcher.group(2))
                    && !(Constants.NULL).equals(titleMatcher.group(2))) {
                return new String[]{artistMatcher.group(2), titleMatcher.group(2)};
            }
        }

        return null;
    }

    private List<Mp3Song> cleanMetadata(List<Mp3Song> result) {
        List<Mp3Song> filteredList = new ArrayList<>();

        for (Mp3Song song : result) {
            if (!song.getSongArtist().isEmpty() && !song.getSongName().isEmpty()) {

                String[] resultant = isOk(song.getSongArtist(), song.getSongName());
                if (resultant != null) {
                    filteredList.add(song);
                }
            }
        }

        return filteredList;
    }
}
