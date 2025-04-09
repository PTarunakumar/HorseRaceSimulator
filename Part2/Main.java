public class Main {
    public static void main(String[] args)
    {
        // Initialising race and horses
        Race race = new Race(10, 4);
        Horse horse1 = new Horse('1', "Horse1", 0.9);
        Horse horse2 = new Horse('2', "Horse2", 0.7);
        Horse horse3 = new Horse('3', "horse3", 0.8);

        //Adding horses to the race
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        race.startRace();
    }
}
