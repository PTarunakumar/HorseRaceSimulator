import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RaceGUI {
    private Race race;
    private JFrame frame;
    private JPanel racePanel;
    private JButton startRaceButton;
    private JButton bettingButton;
    private JButton statisticsButton;
    private JButton customiseTrackButton;
    private JPanel buttonPanel;
    private JPanel trackPanel;
    private volatile Thread raceThread;
    private volatile boolean interrupt;
    public static LinkedList<String> trackHistory = new LinkedList<>();

    final static int TRACK_DISTANCE = 600;
    final static int ICON_SIZE = 60;


    private List<JLabel> horseLabels;

    RaceGUI(Race race)
    {
        this.race = race;

        //Race Panel
        racePanel = new JPanel(new GridBagLayout());
        GridBagConstraints racePanelGbc = new GridBagConstraints();

        //Track Panel
        trackPanel = new JPanel(new GridLayout(race.getLaneCount(), 1));
        horseLabels = new ArrayList<>(race.getLaneCount());

        //Generate Lanes for Horses
        generateTrack();

        //Add trackPanel to racePanel
        racePanelGbc.gridx = 0;
        racePanelGbc.gridy = 0;
        racePanel.add(trackPanel, racePanelGbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

        //Start Button
        startRaceButton = new JButton("Start Race");
        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //If a race is running, stop the race, else start a new race
                if (raceThread != null && raceThread.isAlive())
                {
                    raceStop();
                }
                else
                {
                    raceThread = new Thread(() -> startRace(race));
                    raceThread.start();
                }
            }
        });

        //Betting button
        bettingButton = new JButton("Betting");
        bettingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BettingGUI(race, new User("PT", 1100));
            }
        });

        //Statistics Button
        statisticsButton = new JButton("Statistics");
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatisticsGUI(race);
            }
        });

        //Customise Track Button
        customiseTrackButton = new JButton("Customise Track");
        customiseTrackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (raceThread != null && raceThread.isAlive())
                {
                    raceStop();
                }
                new CustomiseTrackGUI(new JFrame(), race);
                generateTrack();
            }
        });

        //add all buttons to buttonPanel
        buttonPanel.add(startRaceButton);
        buttonPanel.add(bettingButton);
        buttonPanel.add(statisticsButton);
        buttonPanel.add(customiseTrackButton);

        //add buttonPanel to racePanel
        racePanelGbc.gridx = 0;
        racePanelGbc.gridy = 1;
        racePanelGbc.fill = GridBagConstraints.BOTH;
        racePanel.add(buttonPanel, racePanelGbc);


        RaceFrameHandler.initialiseFrame(new JFrame(), racePanel);
    }

    private void addTrackToHistory(String track)
    {
        if (raceEffects.trackTypesList.contains(track))
        {
            trackHistory.add(track);
        }
    }
    private void generateTrack()
    {
        //Generate Lanes for Horses
        for (Horse horse : race.getHorses())
        {
            JPanel horsePanel = new JPanel(null);
            horsePanel.setBackground(Color.WHITE);
            horsePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

            horsePanel.setPreferredSize(new Dimension(TRACK_DISTANCE + ICON_SIZE, ICON_SIZE));
            JLabel horseLabel = new JLabel(scaleImage(horse.getColouredBreedIcon()));
            horseLabel.setBounds(0, 0, ICON_SIZE, ICON_SIZE);
            horseLabel.setPreferredSize(new Dimension(ICON_SIZE, ICON_SIZE));

            JLabel horseSymbolLabel = new JLabel(String.valueOf(horse.getSymbol()));
            horseSymbolLabel.setBounds(TRACK_DISTANCE - 20, 0, ICON_SIZE , ICON_SIZE);
            horseSymbolLabel.setPreferredSize(new Dimension(ICON_SIZE, ICON_SIZE));
            horseSymbolLabel.setFont(new Font("Arial", Font.BOLD, 20));

            horsePanel.add(horseLabel);
            horsePanel.add(horseSymbolLabel);
            trackPanel.add(horsePanel);
            horseLabels.add(horseLabel);
        }

        racePanel.add(trackPanel);
    }
    private void startRace(Race race)
    {
        race.setFinished(false);
        for (Horse horse : race.getHorses())
        {
            horse.setTotalRaces(horse.getTotalRaces() + 1);
            horse.goBackToStart();
            horse.rise();
        }

        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        while (!race.getFinished())
        {
            if (interrupt)
            {
                race.setFinished(true);
            }
            //move each horse
            for (Horse horse : race.getHorses())
            {
                if (horse != null)
                {
                    moveGUIHorse(horse, startTime, endTime);
                }
            }
            //print the race positions
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    printRace(race);
                }
            });

            //if any of the three horses has won the race is finished
            //if a horse wins, its confidence is increased by 20%, and it is displayed as the winner
            for (Horse horse : race.getHorses())
            {
                if (horse != null && race.raceWonBy(horse)) {
                    JOptionPane.showMessageDialog(null, horse.getName() + " wins!");
                    horse.setTotalWins(horse.getTotalWins() + 1);
                    horse.setConfidence(horse.getConfidence() * 1.2);
                    race.setFinished(true);
                }
            }

            //if all horses have fallen
            if (race.allFallen())
            {
                JOptionPane.showMessageDialog(null, "All horses have fallen");
                race.setFinished(true);
            }

            //wait for 100 milliseconds
            try{
                TimeUnit.MILLISECONDS.sleep(100);
                endTime += 100;
            }catch(Exception e){}
        }

        for (Horse horse : race.getHorses())
        {
            if (!horse.hasFallen())
            {
                horse.addConfidence(horse.getConfidence());
                horse.addraceTime(endTime - startTime);
                horse.addDistanceTravelled(horse.getDistanceTravelled());
            }
        }
        addTrackToHistory(race.getTrackType());

        for (Horse horse: race.getHorses())
        {
            horse.goBackToStart();
        }

        SwingUtilities.invokeLater(() -> printRace(race));

        race.setFinished(false);
    }

    private void moveGUIHorse(Horse theHorse, long startTime, long endTime)
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

            if (theHorse.getDistanceTravelled() > race.getRaceLength())
            {
                theHorse.setDistanceTravelled(race.getRaceLength());
            }
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence
            //so if you double the confidence, the probability that it will fall is *2
            //when the horse falls, its confidence is reduced by 20%
            if (Math.random() < Race.calculateFallRate(theHorse))
            {
                theHorse.setConfidence(theHorse.getConfidence() * 0.8);
                theHorse.addConfidence(theHorse.getConfidence());
                theHorse.addraceTime(endTime - startTime);
                theHorse.addDistanceTravelled(theHorse.getDistanceTravelled());
                theHorse.fall();
            }
        }
    }

    void printRace(Race race)
    {
        for (int i = 0; i < race.getLaneCount(); i++)
        {
            int xPosition = (int) (TRACK_DISTANCE / (double) race.getRaceLength()) * (int) race.getHorses().get(i).getDistanceTravelled();
            int yPosition = horseLabels.get(i).getLocation().y;
            horseLabels.get(i).setLocation(xPosition, yPosition);
            horseLabels.get(i).repaint();
        }
    }
    static ImageIcon scaleImage(ImageIcon icon)
    {
        return new ImageIcon(icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
    }

    void raceStop()
    {
        interrupt = true;
        try
        {
            JOptionPane.showMessageDialog(null, "Race Stopped");
            raceThread.join();
        }
        catch (InterruptedException e) {}

        interrupt = false;
    }
}
