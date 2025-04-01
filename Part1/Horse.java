
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
    private int distanceTravelled;
    private boolean hasFallen;



    /**
     * Constructor for Horse class objects
     * Horse distance travelled is initially set to 0
     * Horse has not fallen initially
     *
     * @param horseSymbol the symbol that represents the horse
     * @param horseName the name of the horse
     * @param horseConfidence the confidence of the horse, the higher the confidence the more likely the horse is to move forward, but also a higher chance of falling
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
        this.distanceTravelled = 0;
        this.hasFallen = false;
    }

    //Getters

    public double getConfidence()
    {
        return horseConfidence;
    }

    public int getDistanceTravelled()
    {
        return distanceTravelled;
    }

    public String getName()
    {
        return horseName;
    }

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

    /**
     * Set the horse to fallen so it cannot move forward
     */
    public void fall()
    {
        hasFallen = true;
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
        final int DISTANCE = 1;
        distanceTravelled += DISTANCE;
    }

    public void setConfidence(double newConfidence)
    {
        horseConfidence = newConfidence;
    }

    public void setSymbol(char newSymbol)
    {
        horseSymbol = newSymbol;
    }

}
