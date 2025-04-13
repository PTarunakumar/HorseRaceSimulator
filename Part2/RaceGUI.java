import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RaceGUI {
    private JFrame frame;
    private JPanel racePanel;
    private JButton button1;
    private JPanel trackPanel;
    final static int TRACK_DISTANCE = 600;
    final static int ICON_SIZE = 60;

    private List<JLabel> horseLabels;

    RaceGUI(Race race)
    {
        //Generate Track to contain lanes
        JPanel trackPanel = new JPanel(new GridLayout(race.getLaneCount(), 1));
        horseLabels = new ArrayList<>(race.getLaneCount());
        //Generate Lanes for Horses
        for (Horse horse : race.getHorses())
        {
            JPanel horsePanel = new JPanel(null);
            horsePanel.setBackground(Color.WHITE);
            horsePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

            horsePanel.setPreferredSize(new Dimension(TRACK_DISTANCE + ICON_SIZE, ICON_SIZE));
            JLabel horseLabel = new JLabel(scaleImage(horse.getBreedIcon()));
            horseLabel.setBounds(0, 0, ICON_SIZE, ICON_SIZE);
            horseLabel.setPreferredSize(new Dimension(ICON_SIZE, ICON_SIZE));
            horsePanel.add(horseLabel);
            trackPanel.add(horsePanel);
            horseLabels.add(horseLabel);
        }

        racePanel.add(trackPanel);
        RaceFrameHandler.initialiseFrame(new JFrame(), racePanel);

        while (true)
        {
            //move each horse
            for (Horse horse : race.getHorses())
            {
                if (horse != null)
                {
                    race.moveHorse(horse);
                }
            }
            //print the race positions
            printRace(race);

            //if any of the three horses has won the race is finished
            //if a horse wins, its confidence is increased by 20%, and it is displayed as the winner
            for (Horse horse : race.getHorses())
            {
                if (horse != null && race.raceWonBy(horse)) {
                    JOptionPane.showMessageDialog(null, horse.getName() + " wins!");
                    horse.setConfidence(horse.getConfidence() * 1.2);
                    System.exit(0);
                }
            }

            //if all horses have fallen
            if (race.allFallen())
            {
                JOptionPane.showMessageDialog(null, "All horses have fallen");
                System.exit(0);
            }

            //wait for 100 milliseconds
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
    }

    void startRace(Race race)
    {
        for (Horse horse : race.getHorses())
        {
            horse.goBackToStart();
            horse.rise();
        }

        while (true)
        {
            //move each horse
            for (Horse horse : race.getHorses())
            {
                if (horse != null)
                {
                    race.moveHorse(horse);
                }
            }
            //print the race positions
            printRace(race);

            //if any of the three horses has won the race is finished
            //if a horse wins, its confidence is increased by 20%, and it is displayed as the winner
            for (Horse horse : race.getHorses())
            {
                if (horse != null && race.raceWonBy(horse)) {
                    JOptionPane.showMessageDialog(null, horse.getName() + " wins!");
                    horse.setConfidence(horse.getConfidence() * 1.2);
                    System.exit(0);
                }
            }

            //if all horses have fallen
            if (race.allFallen())
            {
                JOptionPane.showMessageDialog(null, "All horses have fallen");
                System.exit(0);
            }

            //wait for 100 milliseconds
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
    }
    void printRace(Race race)
    {
        for (int i = 0; i < race.getLaneCount(); i++)
        {
            int xPosition = (int) (TRACK_DISTANCE / (double) race.getRaceLength()) * race.getHorses().get(i).getDistanceTravelled();
            int yPosition = horseLabels.get(i).getLocation().y;
            horseLabels.get(i).setLocation(xPosition, yPosition);
            horseLabels.get(i).repaint();
        }
    }
    static ImageIcon scaleImage(ImageIcon icon)
    {
        return new ImageIcon(icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
    }
}
