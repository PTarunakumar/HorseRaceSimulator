import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JPanel trackPanel;
    private volatile Thread raceThread;
    private volatile boolean interrupt;

    final static int TRACK_DISTANCE = 600;
    final static int ICON_SIZE = 60;


    private List<JLabel> horseLabels;

    RaceGUI(Race race)
    {
        this.race = race;
        //Generate Track to contain lanes
        trackPanel = new JPanel(new GridLayout(race.getLaneCount(), 1));
        horseLabels = new ArrayList<>(race.getLaneCount());
        //Generate Lanes for Horses
        generateTrack();

        racePanel.add(trackPanel);
        RaceFrameHandler.initialiseFrame(new JFrame(), racePanel);

        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatisticsGUI(race);
            }
        });

        bettingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BettingGUI(race, new User("PT", 1100));
            }
        });
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
    }

    void generateTrack()
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
            horsePanel.add(horseLabel);
            trackPanel.add(horsePanel);
            horseLabels.add(horseLabel);
        }

        racePanel.add(trackPanel);
    }
    void startRace(Race race)
    {
        for (Horse horse : race.getHorses())
        {
            race.applyTrackEffects();
            race.setFinished(false);
            horse.setTotalRaces(horse.getTotalRaces() + 1);
            horse.goBackToStart();
            horse.rise();
        }

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
                    race.moveHorse(horse);
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
            }catch(Exception e){}
        }
        race.setFinished(false);
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
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        interrupt = false;
    }
}
