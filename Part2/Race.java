import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 *
 * @author McRaceface
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private String trackType;
    private List<Horse> horses;
    private boolean finished;

    private HashMap<String, Double> windyHorseSpeed = new HashMap<>()
    {
        {
            put("Horse", 1.2);
            put("Pegasus", 0.95);
            put("Unicorn", 0.8);
        }
    };

    private HashMap<String, Double> wetHorseSpeed = new HashMap<>()
    {
        {
            put("Horse", 0.8);
            put("Pegasus", 1.0);
            put("Unicorn", 1.0);
        }
    };

    private HashMap<String, Double> windyFallRate = new HashMap<>()
    {
        {
            put("Horse", 0.9);
            put("Pegasus", 0.65);
            put("Unicorn", 0.75);
        }
    };

    private HashMap<String, Double> wetFallRate = new HashMap<>()
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
    private HashMap<String, HashMap<String, Double>> trackSpeedEffects = new HashMap<>()
    {
        {
            put("windy", windyHorseSpeed);
            put("wet", wetHorseSpeed);
        }
    };

    private HashMap<String, HashMap<String, Double>> trackFallFactorEffects = new HashMap<>()
    {
        {
            put("windy", windyFallRate);
            put("wet", wetFallRate);
        }
    };
    public void applyTrackEffects()
    {
        for (Horse horse: horses)
        {
            horse.setHorseSpeed(horse.getHorseSpeed() * trackSpeedEffects.get(trackType).get(horse.getBreed()));
            horse.setHorseFallRateFactor(horse.getFallRateFactor() * trackFallFactorEffects.get(trackType).get(horse.getBreed()));
        }
    }

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, int laneCount, String trackType) {
        // initialise instance variables
        finished = false;
        raceLength = distance;
        horses = new ArrayList<Horse>(laneCount);
        this.trackType = trackType;

        // Initially, all lanes are empty
        for (int i = 0; i < laneCount; i++) {
            horses.add(null);
        }
    }

    //Getters
    public boolean getFinished()
    {
        return finished;
    }
    public int getRaceLength()
    {
        return raceLength;
    }

    public int getLaneCount()
    {
        return horses.size();
    }

    public List<Horse> getHorses()
    {
        return horses;
    }

    public String getTrackType()
    {
        return trackType;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        try
        {
            horses.set(laneNumber-1, theHorse);
            theHorse.setTotalRaces(theHorse.getTotalRaces() + 1);
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */

    public static void startGUIRace()
    {
        IntroGUI introGUI = new IntroGUI(new JFrame());
        if (introGUI.getReturnedValue() == 1)
        {
            System.exit(0);
        }

        AddTrackGUI addTrackGUI = new AddTrackGUI(new JFrame());
        Race race = addTrackGUI.getRace();

        for (int i = 1; i <= race.getLaneCount(); i++)
        {
            AddHorseGUI theHorse = new AddHorseGUI(new JFrame());
            race.addHorse(theHorse.getHorse(), i);
        }

        RaceGUI raceGUI = new RaceGUI(race);
    }

    public void startRace()
    {
        //declare a local variable to tell us when the race is finished

        //reset all the lanes (all horses not fallen and back to 0).
        for(Horse horse : horses)
        {
            if (horse != null)
            {
                horse.rise();
                horse.goBackToStart();
            }
        }

        while (!finished)
        {
            //move each horse
            for (Horse horse : horses)
            {
                if (horse != null)
                {
                    moveHorse(horse);
                }
            }
            //print the race positions
            printRace();

            //if any of the three horses has won the race is finished
            //if a horse wins, its confidence is increased by 20%, and it is displayed as the winner
            for (Horse horse : horses)
            {
                if (horse != null && raceWonBy(horse)) {
                    System.out.println(horse.getName() + " wins!");
                    horse.setTotalWins(horse.getTotalWins() + 1);
                    horse.setConfidence(horse.getConfidence() * 1.2);
                    finished = true;
                }
            }

            //if all horses have fallen
            if (allFallen())
            {
                System.out.println("All horses have fallen!");
                finished = true;
            }

            //wait for 100 milliseconds
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }

        finished = false;
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */
    public void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move,
        //so only run if it has not fallen
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
                theHorse.moveForward();
            }

            if (theHorse.getDistanceTravelled() > raceLength)
            {
                theHorse.setDistanceTravelled(raceLength);
            }
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence
            //so if you double the confidence, the probability that it will fall is *2
            //when the horse falls, its confidence is reduced by 20%
            if (Math.random() < (0.05 * theHorse.getFallRateFactor() * theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.setConfidence(theHorse.getConfidence() * 0.8);
                theHorse.fall();
            }
        }
    }

    /**
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    public boolean raceWonBy(Horse theHorse)
    {
        return theHorse.getDistanceTravelled() == raceLength;
    }

    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window

        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

        for (Horse horse : horses)
        {
            printLane(horse);
            System.out.println();
        }

        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();
    }

    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore;
        int spacesAfter;

        if (theHorse == null)
        {
            spacesBefore = 0;
            spacesAfter = raceLength;
        }
        else
        {
            spacesBefore = (int) theHorse.getDistanceTravelled();
            spacesAfter = raceLength - (int) theHorse.getDistanceTravelled();
        }

        //print a | for the beginning of the lane
        System.out.print('|');

        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);

        //if horse is null, print empty character
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if (theHorse == null)
        {
            System.out.print(' ');
        }
        else
        if(theHorse.hasFallen())
        {
            System.out.print('\u2322');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }

        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);

        //print the | for the end of the track
        System.out.print('|');
    }


    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     *
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    /**
     * Checks if all horses have fallen
     */
    public boolean allFallen()
    {
        boolean allFallen = true;
        for (Horse horse: horses)
        {
            if (horse != null && !horse.hasFallen())
            {
                allFallen = false;
            }
        }
        return allFallen;
    }


}
