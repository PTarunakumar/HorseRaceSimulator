import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsGUI {
    private JFrame frame = new JFrame();
    private JPanel statisticsPanel;
    private JPanel horseDetailsContainer;
    private JLabel raceParticipationCountTextField;
    private JLabel noOfWinsTextField;
    private JLabel noOfWinsLabel;
    private JLabel raceParticipationCountLabel;
    private JComboBox<Horse> horseSelectorComboBox;
    private JPanel horseSelectorContainer;
    private JTextArea historyTextField;
    private JLabel averageSpeedLabel;
    private JLabel averageSpeedTextField;

    StatisticsGUI(Race race)
    {
        horseSelectorComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
                if (value instanceof Horse horse) {
                    label.setText(horse.getName());
                    label.setIcon(horse.getBreedIcon());
                }
                return label;
            }
        });

        horseSelectorComboBox.addItem(null);
        for (Horse horse : race.getHorses())
        {
            horseSelectorComboBox.addItem(horse);
        }

        horseSelectorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Horse selectedHorse = (Horse) horseSelectorComboBox.getSelectedItem();
                if (selectedHorse != null) {
                    raceParticipationCountTextField.setText(String.valueOf(selectedHorse.getTotalRaces()));
                    noOfWinsTextField.setText(String.valueOf(selectedHorse.getTotalWins()));
                    averageSpeedTextField.setText(String.format("%.2f", calculateSpeed(selectedHorse)));

                    String history = "";
                    for (int i = 0; i < selectedHorse.getRaceTimes().size(); i++)
                    {
                        String distanceTravelled = String.format("%.2f", selectedHorse.getDistancesTravelledList().get(i));
                        String raceTime = String.format("%.2f", (double )selectedHorse.getRaceTimes().get(i) / 1000);
                        String confidence = String.format("%.2f", selectedHorse.getConfidenceList().get(i));
                        String trackType = RaceGUI.trackHistory.get(i);
                        history += "Track: " + trackType + " Distance: " + distanceTravelled + "m, Time: " + raceTime + "s Confidence: " + confidence + "\n";
                    }

                    historyTextField.setText(history);
                    frame.pack();
                }
            }
        });

        RaceFrameHandler.initialiseDisposableFrame(frame, statisticsPanel);
    }

    private double calculateSpeed(Horse horse)
    {
        long totalTime = 0;
        double totalDistance = 0;

        for (long Time : horse.getRaceTimes())
        {
            totalTime += Time;
        }

        for (double distance : horse.getDistancesTravelledList())
        {
            totalDistance += distance;
        }

        //return speed in m/s

        return totalDistance / ((double) totalTime / 1000) ;
    }
}
