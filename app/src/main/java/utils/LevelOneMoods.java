package utils;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LevelOneMoods {
    /**
     * A Map to store the variance mappings.
     */
    private static Map<String, List<String>> levelOneMoods = new LinkedHashMap<>();

    /**
     * Getter method.
     *
     * @return Map of variances.
     */
    public static Map<String, List<String>> getLevelOneMoods() {
        addIntoMap();
        return levelOneMoods;
    }

    /**
     * Method to add into map.
     */
    private static void addIntoMap() {
        // level 1 valiance
        List<String> levelOneValiance = new ArrayList<>();
        levelOneValiance.add("Somber");
        levelOneValiance.add("Gritty");
        levelOneValiance.add("Serious");
        levelOneValiance.add("Brooding");
        levelOneValiance.add("Aggressive");
        levelOneMoods.put(Constants.LEVEL_ONE_VARIANCE, levelOneValiance);

        // level 2 valiance
        List<String> levelTwoValiance = new ArrayList<>();
        levelTwoValiance.add("Melancholy");
        levelTwoValiance.add("Cool");
        levelTwoValiance.add("Yearning");
        levelTwoValiance.add("Urgent");
        levelTwoValiance.add("Defiant");
        levelOneMoods.put(Constants.LEVEL_TWO_VARIANCE, levelTwoValiance);

        // level 3 valiance
        List<String> levelThreeValiance = new ArrayList<>();
        levelThreeValiance.add("Sentimental");
        levelThreeValiance.add("Sophisticated");
        levelThreeValiance.add("Sensual");
        levelThreeValiance.add("Fiery");
        levelThreeValiance.add("Energizing");
        levelOneMoods.put(Constants.LEVEL_THREE_VARIANCE, levelThreeValiance);

        // level 4 valiance
        List<String> levelFourValiance = new ArrayList<>();
        levelFourValiance.add("Tender");
        levelFourValiance.add("Romantic");
        levelFourValiance.add("Empowering");
        levelFourValiance.add("Stirring");
        levelFourValiance.add("Rowdy");
        levelOneMoods.put(Constants.LEVEL_FOUR_VARIANCE, levelFourValiance);

        // level 5 valiance
        List<String> levelFiveValiance = new ArrayList<>();
        levelFiveValiance.add("Peaceful");
        levelFiveValiance.add("Easygoing");
        levelFiveValiance.add("Upbeat");
        levelFiveValiance.add("Lively");
        levelFiveValiance.add("Excited");
        levelOneMoods.put(Constants.LEVEL_FIVE_VARIANCE, levelFiveValiance);
    }
}
