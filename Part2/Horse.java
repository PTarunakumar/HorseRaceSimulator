import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A model of the horse to race with for the program HorseRaceSimulator.
 *
 * @author Parth Tarunakumar
 * @version 1.0
 */
public class Horse
{
    //Fields of class Horse
    private char horseSymbol;
    private String horseName;
    private double horseConfidence;
    private String horseBreed;
    private Color horseCoatColor;
    private String horseAccessory;
    private double distanceTravelled;
    private boolean hasFallen;
    private int totalWins;
    private int totalRaces;
    private double horseSpeed;
    private double horseFallRateFactor;
    private List<Long> raceTimes;
    private List<Double> distancesTravelledList;
    private List<Double> confidenceList;


    public static HashMap<String, ImageIcon> breedIcons = new HashMap<>()
    {
        {
            put("Horse", new ImageIcon("Part2/Icons/horse.png"));
            put("Unicorn", new ImageIcon("Part2/Icons/unicorn.png"));
            put("Pegasus", new ImageIcon("Part2/Icons/pegasus.png"));
        }
    };

    public static HashMap<String, Double> breedSpeed = new HashMap<String, Double>()
    {
        {
            put("Horse", 1.8);
            put("Unicorn",1.6);
            put("Pegasus", 1.4);
        }
    };

    public static ArrayList<String> accessoryList = new ArrayList<>()
    {
        {
            add("Horseshoes");
            add("Saddle");
        }
    };

    public static HashMap<String, Double> breedFallRateFactor = new HashMap<>()
    {
        {
            put("Horse", 1.0);
            put("Pegasus", 0.9);
            put("Unicorn", 0.8);
        }
    };



    /**
     * Constructor for Horse class objects
     * Horse distance travelled is initially set to 0
     * Horse has not fallen initially
     *
     * @param horseSymbol the symbol that represents the horse
     * @param horseName the name of the horse
     * @param horseConfidence the confidence of the horse. Must be between 0 exclusive and 1 inclusive, if outside range then sets to 0.1 and 1 respectively.
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence, String horseBreed, String horseAccessory, Color horseCoatColor)
    {
        //initialise instance variables
        this.confidenceList = new LinkedList<>();
        this.distancesTravelledList = new LinkedList<>();
        this.raceTimes = new LinkedList<>();
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = confidenceValidate(horseConfidence);
        this.horseBreed = horseBreed;
        this.horseCoatColor = horseCoatColor;
        this.horseAccessory = horseAccessory;
        this.distanceTravelled = 0;
        this.hasFallen = false;
        this.totalWins = 0;
        this.totalRaces = 0;
        this.horseSpeed = breedSpeed.get(horseBreed);
        this.horseFallRateFactor = breedFallRateFactor.get(horseBreed);
    }

    //Getters
    public List<Double> getConfidenceList()
    {
        return confidenceList;
    }
    public List<Double> getDistancesTravelledList()
    {
        return distancesTravelledList;
    }

    public LinkedList<Long> getRaceTimes()
    {
        return (LinkedList<Long>) raceTimes;
    }


    public int getTotalWins()
    {
        return totalWins;
    }
    public int getTotalRaces()
    {
        return totalRaces;
    }
    public double getConfidence()
    {
        return horseConfidence;
    }

    public double getDistanceTravelled()
    {
        return distanceTravelled;
    }

    public String getName()
    {
        return horseName;
    }

    public String getBreed()
    {
        return horseBreed;
    }
    public String getAccessory()
    {
        return horseAccessory;
    }
    public Color getCoatColor()
    {
        return horseCoatColor;
    }
    public ImageIcon getBreedIcon()
    {
        return breedIcons.get(horseBreed);
    }

    public ImageIcon getColouredBreedIcon()
    {
        BufferedImage image = new BufferedImage(getBreedIcon().getIconWidth(), getBreedIcon().getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(getBreedIcon().getImage(), 0, 0, null);
        for (int i = 0; i < getBreedIcon().getIconWidth(); i++)
        {
            for (int j = 0; j < getBreedIcon().getIconHeight(); j++)
            {
                //If it's not transparent
                if (image.getRGB(i, j) != 0)
                {
                    image.setRGB(i, j, horseCoatColor.getRGB());
                }
                else
                {
                    image.setRGB(i, j, image.getRGB(i, j));
                }

            }
        }

        g2d.dispose();
        return new ImageIcon(image);
    }

    public double getHorseSpeed() { return horseSpeed; }

    public double getFallRateFactor() { return horseFallRateFactor; }

    public char getSymbol()
    {
        return horseSymbol;
    }

    /**
     * @return true if the horse has fallen, false otherwise
     */
    public boolean hasFallen()
    {
        return hasFallen;
    }

    //Setters
    public void addConfidence(double horseConfidence)
    {
        this.horseConfidence = confidenceValidate(horseConfidence);
        confidenceList.add(horseConfidence);
    }
    public void addDistanceTravelled(double distanceTravelled)
    {
        distancesTravelledList.add(distanceTravelled);
    }

    public void addraceTime(long time)
    {
        raceTimes.add(time);
    }
    public void setHorseFallRateFactor(double horseFallRateFactor) { this.horseFallRateFactor = horseFallRateFactor; }

    public void setHorseSpeed (double horseSpeed) { this.horseSpeed = horseSpeed; }
    public void setDistanceTravelled(int distanceTravelled)
    {
        this.distanceTravelled = distanceTravelled;
    }

    public void setTotalRaces(int totalRaces)
    {
        this.totalRaces = totalRaces;
    }

    public void setTotalWins(int winCount)
    {
        this.totalWins = winCount;
    }
    /**
     * Set the horse to fallen so it cannot move forward
     */
    public void fall()
    {
        hasFallen = true;
    }

    /**
     * Set the horse to not fallen so it can move forward
     */
    public void rise()
    {
        hasFallen = false;
    }

    /**
     * Puts the horse to the start.
     */
    public void goBackToStart()
    {
        distanceTravelled = 0;
    }

    /**
     * Moves the horse forward by 1 unit
     */
    public void moveForward()
    {
        distanceTravelled += this.getHorseSpeed();
    }

    public void setConfidence(double newConfidence)
    {
        horseConfidence = confidenceValidate(newConfidence);
    }

    public void setSymbol(char newSymbol)
    {
        horseSymbol = newSymbol;
    }

    //Other methods

    public double confidenceValidate(double confidence)
    {
        if (confidence <= 0)
        {
            return 0.1;
        }
        else if (confidence > 1)
        {
            return 1;
        }
        else
        {
            return confidence;
        }
    }

    @Override
    public String toString()
    {
        return horseName;
    }
}
