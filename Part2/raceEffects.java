import java.util.HashMap;
import java.util.HashSet;

public class raceEffects {
    public static HashMap<String, Double> defaultHorseSpeed = new HashMap<>()
    {
        {
            put("Horse", 1.0);
            put("Pegasus", 1.0);
            put("Unicorn", 1.0);
        }
    };

    public static HashMap<String, Double> windyHorseSpeed = new HashMap<>()
    {
        {
            put("Horse", 1.2);
            put("Pegasus", 0.95);
            put("Unicorn", 0.8);
        }
    };

    public static HashMap<String, Double> wetHorseSpeed = new HashMap<>()
    {
        {
            put("Horse", 0.8);
            put("Pegasus", 1.0);
            put("Unicorn", 1.0);
        }
    };

    public static HashMap<String, Double> windyFallRate = new HashMap<>()
    {
        {
            put("Horse", 0.9);
            put("Pegasus", 0.65);
            put("Unicorn", 0.75);
        }
    };

    public static HashMap<String, Double> defaultFallRate = new HashMap<>()
    {
        {
            put("Horse", 1.0);
            put("Pegasus", 1.0);
            put("Unicorn", 1.0);
        }
    };

    public static HashMap<String, Double> wetFallRate = new HashMap<>()
    {
        {
            put("Horse", 0.7);
            put("Pegasus", 1.0);
            put("Unicorn", 1.0);
        }
    };

    public static HashSet<String> trackTypesList = new HashSet<>()
    {
        {
            add("default");
            add("windy");
            add("wet");
        }
    };
    public static HashMap<String, HashMap<String, Double>> trackSpeedEffects = new HashMap<>()
    {
        {
            put("default", defaultHorseSpeed);
            put("windy", windyHorseSpeed);
            put("wet", wetHorseSpeed);
        }
    };

    public static HashMap<String, HashMap<String, Double>> trackFallFactorEffects = new HashMap<>()
    {
        {
            put("default", defaultFallRate);
            put("windy", windyFallRate);
            put("wet", wetFallRate);
        }
    };
}
