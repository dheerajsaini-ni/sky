package api.parental.control.service;


import java.util.HashMap;
import java.util.Map;

public enum ParentalControlLevels {

    MOVIE_U(0, "U"), MOVIE_PG(1, "PG"), MOVIE_12(2, "12"), MOVIE_15(3, "15"), MOVIE_18(4, "18");

    public final static Map<String, ParentalControlLevels> ratings = new HashMap<>();

    static {
        for (ParentalControlLevels rating : ParentalControlLevels.values()) {
            ratings.put(rating.stringValue, rating);
        }

    }

    private final int value;
    private final String stringValue;

    ParentalControlLevels(int intValue, String stringValue) {
        this.value = intValue;
        this.stringValue = stringValue;
    }

    public static ParentalControlLevels getByString(String rating) {
        return ratings.get(rating);
    }

    public int getValue() {
        return value;
    }
}
