package utils;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LevelTwoMoods {
    /**
     * A Map for storing variances.
     */
    private static Map<String, List<String>> levelTwoMoods = new LinkedHashMap<>();

    /**
     * Getter method.
     *
     * @return Map of variances.
     */
    public static Map<String, List<String>> getLevelTwoMoods() {
        addIntoMap();
        return levelTwoMoods;
    }

    /**
     * Method to add into map.
     */
    private static void addIntoMap() {
        //level one variance

        List<String> list = new ArrayList<>();

        list.add("Dark Cosmic");
        list.add("Creepy / Ominous");
        list.add("Depressed / Lonely");
        list.add("Gritty / Soulful");
        list.add("Serious / Cerebral");
        list.add("Thrilling");
        list.add("Dreamy Brooding");
        list.add("Alienated / Brooding");
        list.add("Chaotic / Intense");
        list.add("Aggressive Power");
        levelTwoMoods.put(Constants.LEVEL_ONE_VARIANCE, list);

        list.clear();

        //level two variance
        List<String> lvl2 = new ArrayList<>();
        list.add("Solemn / Spiritual");
        list.add("Enigmatic / Mysterious");
        list.add("Sober / Determined");
        list.add("Strumming Yearning");
        list.add("Melodramatic");
        list.add("Hypnotic Rhythm");
        list.add("Evocative / Intriguing");
        list.add("Energetic Melancholy");
        list.add("Dark Hard Beat");
        list.add("Heavy Triumphant");
        levelTwoMoods.put(Constants.LEVEL_TWO_VARIANCE, list);

        list.clear();

        //level three variance
        list.add("Wistful / Forlorn");
        list.add("Sad / Soulful");
        list.add("Cool Confidence");
        list.add("Dark Groovy");
        list.add("Sensitive / Exploring");
        list.add("Energetic Dreaming");
        list.add("Dark Urgent");
        list.add("Energetic Anxious");
        list.add("Attitude / Defiant");
        list.add("Hard Dark Excitement");
        levelTwoMoods.put(Constants.LEVEL_THREE_VARIANCE, list);

        list.clear();

        //level four variance
        list.add("Mysterious / Dreamy");
        list.add("Light Melancholy");
        list.add("Casual Groove");
        list.add("Wary / Defiant");
        list.add("Bittersweet Pop");
        list.add("Energetic Yearning");
        list.add("Dark Pop");
        list.add("Dark Pop Intensity");
        list.add("Heavy Brooding");
        list.add("Hard Positive Excitement");
        levelTwoMoods.put(Constants.LEVEL_FOUR_VARIANCE, list);

        list.clear();

        //level five variance
        list.add("Lyrical Sentimental");
        list.add("Cool Melancholy");
        list.add("Intimate Bittersweet");
        list.add("Smoky / Romantic");
        list.add("Dreamy Pulse");
        list.add("Intimate");
        list.add("Passionate Rhythm");
        list.add("Energy Abstract Groove");
        list.add("Edgy / Sexy");
        list.add("Abstract Beat");
        levelTwoMoods.put(Constants.LEVEL_FIVE_VARIANCE, list);

        list.clear();

        //level six variance
        list.add("Tender / Sincere");
        list.add("Gentle Bittersweet");
        list.add("Suave / Sultry");
        list.add("Dark Playful");
        list.add("Soft Soulful");
        list.add("Sensual Groove");
        list.add("Dark Sparkling Lyrical");
        list.add("Fiery Groove");
        list.add("Arousing");
        list.add("Heavy Beat");
        levelTwoMoods.put(Constants.LEVEL_SIX_VARIANCE, list);

        list.clear();

        //level seven variance
        list.add("Romantic / Lyrical");
        list.add("Light Groovy");
        list.add("Dramatic / Romantic");
        list.add("Lush / Romantic");
        list.add("Dramatic Emotion");
        list.add("idealistic / Stirring");
        list.add("Focused Sparkling");
        list.add("Triumphant / Rousing");
        list.add("Confident / Tough");
        list.add("Driving Dark Groove");
        levelTwoMoods.put(Constants.LEVEL_SEVEN_VARIANCE, list);

        list.clear();

        //level eight variance
        list.add("Refined / Mannered");
        list.add("Awakening / Stately");
        list.add("Sweet / Sincere");
        list.add("Heartfelt Passion");
        list.add("Strong / Stable");
        list.add("Powerful / Heroic");
        list.add("Invigorating / Joyous");
        list.add("Jubilant / Soulful");
        list.add("Ramshackle / Rollicking");
        list.add("Wild / Rowdy");
        levelTwoMoods.put(Constants.LEVEL_EIGHT_VARIANCE, list);

        list.clear();

        //level nine variance
        list.add("Reverent / Healing");
        list.add("Quiet / Introspective");
        list.add("Friendly");
        list.add("Charming / Easygoing");
        list.add("Soulful / Easygoing");
        list.add("Happy / Soulful");
        list.add("Playful / Swingin'");
        list.add("Exuberant / Festive");
        list.add("Upbeat Pop Groove");
        list.add("Happy Excitement");
        levelTwoMoods.put(Constants.LEVEL_NINE_VARIANCE, list);

        list.clear();

        //level ten variance
        list.add("Pastoral / Serene");
        list.add("Delicate / Tranquil");
        list.add("Hopeful / Breezy");
        list.add("Cheerful / Playful");
        list.add("Carefree Pop");
        list.add("Party / Fun");
        list.add("Showy / Rousing");
        list.add("Lusty / Jaunty");
        list.add("Loud Celebratory");
        list.add("Euphoric Energy");
        levelTwoMoods.put(Constants.LEVEL_TEN_VARIANCE, list);
    }
}