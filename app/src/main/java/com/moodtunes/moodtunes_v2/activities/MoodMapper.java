package com.moodtunes.moodtunes_v2.activities;

import java.util.List;
import java.util.Map;

import utils.LevelOneMoods;

public class MoodMapper {

    public static final String DOT_DELIMITER = ".";
    public static final int LEVEL_ONE_DIVIDING_CONSTANT = 20;
    public static final int LEVEL_TWO_DIVIDING_CONSTANT = 10;

    public static String mapLevelOneMood(final double smilingProbability) {
        // Get the map
        Map<String, List<String>> levelOneMoods = LevelOneMoods
                .getLevelOneMoods();

        // E.g., 83.46 -> 83.46/20 -> 4.173 -> [4, 1]

        // Parse double to string
        String[] tokens = String
                .valueOf(smilingProbability / LEVEL_ONE_DIVIDING_CONSTANT)
                .split(DOT_DELIMITER);

        int[] delimiters = new int[]{Integer.parseInt(tokens[0]),
                Integer.parseInt(String.valueOf(tokens[1].charAt(0)))};

        int counter = 0;
        for (Map.Entry<String, List<String>> entries
                : levelOneMoods.entrySet()) {
            if (counter == delimiters[0]) {
                return entries.getValue().get(delimiters[1]);
            } else{
                counter++;
            }
        }

        return null;
    }

    public static String mapLevelTwoMood(final double smilingProbability) {

        String[] tokens = String
                .valueOf(smilingProbability / LEVEL_TWO_DIVIDING_CONSTANT)
                .split(DOT_DELIMITER);



        return null;
    }
}
