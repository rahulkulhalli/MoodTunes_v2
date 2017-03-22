package pojos;

/**
 * A Plain Old Java Object for storing basic MP3 information.
 */
public class Mp3Song {
    /**
     * Song name.
     */
    private String songName;

    /**
     * Song Artist.
     */
    private String songArtist;

    /**
     * Song Path.
     */
    private String songPath;

    /**
     * Level One Mood.
     */
    private String levelOneMood;

    /**
     * Level Two Mood.
     */
    private String levelTwoMood;

    /**
     * Original song name.
     */
    private String originalSongName;

    /**
     * Blank Constructor.
     */
    public Mp3Song() {


    }

    /**
     * Setter for song name.
     *
     * @param name Name of song.
     */
    public void setSongName(final String name) {
        this.songName = name;
    }

    /**
     * Setter for song artist.
     *
     * @param artist Song artist.
     */
    public void setSongArtist(final String artist) {
        this.songArtist = artist;
    }

    /**
     * Getter for the original song name.
     *
     * @return Original song name.
     */
    public String getOriginalSongName() {
        return originalSongName;
    }

    /**
     * Setter for original song name.
     *
     * @param originalSong The original song name.
     */
    public void setOriginalSongName(final String originalSong) {
        this.originalSongName = originalSong;
    }

    /**
     * Setter for song path.
     *
     * @param path Path of song.
     */
    public void setSongPath(final String path) {
        this.songPath = path;
    }

    /**
     * Setter for lvl 1 mood.
     *
     * @param level1Mood Level 1 mood.
     */
    public void setLevelOneMood(final String level1Mood) {
        this.levelOneMood = level1Mood;
    }

    /**
     * Setter for lvl 2 mood.
     *
     * @param level2Mood Level 2 mood.
     */
    public void setLevelTwoMood(final String level2Mood) {
        this.levelTwoMood = level2Mood;
    }

    /**
     * Parameterized constructor.
     *
     * @param name Song name.
     * @param originalSong Original song name.
     * @param artist Artist name.
     * @param path Path to song.
     * @param lvl1 Level one mood.
     * @param lvl2 Level two mood.
     */
    public Mp3Song(final String name, final String originalSong,
                   final String artist, final String path,
                   final String lvl1, final String lvl2) {
        songName = name;
        originalSongName = originalSong;
        songArtist = artist;
        songPath = path;
        levelOneMood = lvl1;
        levelTwoMood = lvl2;
    }

    /**
     * Getter for song name.
     *
     * @return Song name.
     */
    public String getSongName() {
        return songName;
    }

    /**
     * Getter for song artist.
     *
     * @return Song artist.
     */
    public String getSongArtist() {
        return songArtist;
    }

    /**
     * Getter for song path.
     *
     * @return Song path.
     */
    public String getSongPath() {
        return songPath;
    }

    /**
     * Getter for song mood (level one).
     *
     * @return Level one mood.
     */
    public String getLevelOneMood() {
        return levelOneMood;
    }

    /**
     * Getter for song mood (level two).
     *
     * @return Level two mood.
     */
    public String getLevelTwoMood() {
        return levelTwoMood;
    }

    /**
     * Defining custom toString method.
     *
     * @return String representation of object.
     */
    @Override
    public String toString() {
        return "SONG = [name=" + songName + ", original_name = "
                + originalSongName + ", artist=" + songArtist + ", "
                + "path=" + songPath + ", level_one_mood=" + levelOneMood + ", "
                + "level_two_mood=" + levelTwoMood + "]";
    }
}
