import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

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
    private Horse[] horses;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        horses = new Horse[3];

    }

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber < 4 && laneNumber > 0)
        {
            horses[laneNumber-1] = theHorse;
        }
        else
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
    public void startRace()
    {

        Horse horse1 = new Horse('1', "Horse1", 0.9);
        Horse horse2 = new Horse('2', "Horse2", 0.7);
        Horse horse3 = new Horse('3', "horse3", 0.8);

        //Adding horses to the race
        addHorse(horse1, 1);
        addHorse(horse2, 2);
        addHorse(horse3, 3);


        do {
            raceLoop();
        }
        while(raceRepeat());


    }

    private boolean raceRepeat()
    {
        System.out.println("Do you want to do another race? (y/n)");
        return new Scanner(System.in).nextLine().equals("y");
    }
    private void raceLoop()
    {
        //reset all the lanes (all horses not fallen and back to 0).
        for(int i = 0; i < 3; i++)
        {
            if (horses[i] != null)
            {
                horses[i].rise();
                horses[i].goBackToStart();
            }
        }

        while (true)
        {
            //move each horse
            for (int i = 0; i < 3; i++)
            {
                if (horses[i] != null)
                {
                    moveHorse(horses[i]);
                }
            }
            //print the race positions
            printRace();

            //if any of the three horses has won the race is finished
            //if a horse wins, its confidence is increased by 20%, and it is displayed as the winner
            for (int i = 0; i < 3; i++)
            {
                if (horses[i] != null && raceWonBy(horses[i])) {
                    System.out.println(horses[i].getName() + " wins!");
                    horses[i].setConfidence(horses[i].getConfidence() * 1.2);
                    return;
                }
            }

            //if all horses have fallen
            if (allFallen())
            {
                System.out.println("All horses have fallen!");
                return;
            }

            //wait for 100 milliseconds
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
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

            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence
            //so if you double the confidence, the probability that it will fall is *2
            //when the horse falls, its confidence is reduced by 20%
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
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
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window

        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

        for (int i = 0; i < 3; i++)
        {
            printLane(horses[i]);
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
            spacesBefore = theHorse.getDistanceTravelled();
            spacesAfter = raceLength - theHorse.getDistanceTravelled();
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
    private boolean allFallen()
    {
        boolean allFallen = true;
        for (int i = 0; i < 3; i++)
        {
            if (horses[i] != null && !horses[i].hasFallen())
            {
                allFallen = false;
            }
        }
        return allFallen;
    }
}
