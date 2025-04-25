import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsGUI {
    private Race race;
    private JFrame frame = new JFrame();
    private JPanel statisticsPanel;
    private JPanel horseDetailsPanel;
    private JLabel noOfRacesTextField;
    private JLabel noOfWinsTextField;
    private JLabel noOfWinsLabel;
    private JLabel noOfRacesLabel;
    private JComboBox<Horse> horseSelectorComboBox;
    private JPanel horseSelectorPanel;
    private JTextArea historyTextField;
    private JLabel averageSpeedLabel;
    private JLabel averageSpeedTextField;
    private JPanel titlePanel;
    private JLabel statisticsLabel;

    void setUI(Race race)
    {

    }

    StatisticsGUI(Race race)
    {
        this.race = race;


        //Statistics Panel
        statisticsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints statisticsPanelGbc = new GridBagConstraints();
        statisticsPanelGbc.insets = new Insets(5, 30, 5, 30);
        statisticsPanelGbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Panel
        titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints titlePanelGbc = new GridBagConstraints();

        // Add statisticsLabel to titlePanel
        statisticsLabel = new JLabel("Statistics");
        statisticsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanelGbc.gridx = 0;
        titlePanelGbc.gridy = 0;
        titlePanel.add(statisticsLabel, titlePanelGbc);

        // Add titlePanel to statisticsPanel
        statisticsPanelGbc.gridx = 0;
        statisticsPanelGbc.gridy = 0;
        statisticsPanel.add(titlePanel, statisticsPanelGbc);

        // Horse Selector Panel
        horseSelectorPanel = new JPanel(new GridBagLayout());
        GridBagConstraints horseSelectorPanelGbc = new GridBagConstraints();
        horseSelectorPanelGbc.gridx = 0;
        horseSelectorPanelGbc.gridy = 0;

        // Add horseSelectorComboBox to horseSelectorPanel
        horseSelectorComboBox = new JComboBox<>();
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
        horseSelectorPanel.add(horseSelectorComboBox, horseSelectorPanelGbc);

        // Add horseSelectorPanel to statisticsPanel
        statisticsPanelGbc.gridx = 0;
        statisticsPanelGbc.gridy = 1;
        statisticsPanel.add(horseSelectorPanel, statisticsPanelGbc);

        // Horse Details Panel
        horseDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints horseDetailsPanelGbc = new GridBagConstraints();

        //add noOfRacesLabel and textField to horseDetailsPanel
        noOfRacesLabel = new JLabel("No of Races:");
        horseDetailsPanelGbc.gridx = 0;
        horseDetailsPanelGbc.gridy = 0;
        horseDetailsPanel.add(noOfRacesLabel, horseDetailsPanelGbc);

        noOfRacesTextField = new JLabel("Races");
        horseDetailsPanelGbc.gridx = 1;
        horseDetailsPanel.add(noOfRacesTextField, horseDetailsPanelGbc);

        //add noOfWinsLabel and textField to horseDetails Panel
        noOfWinsLabel = new JLabel("No of wins:");
        horseDetailsPanelGbc.gridx = 0;
        horseDetailsPanelGbc.gridy = 1;
        horseDetailsPanel.add(noOfWinsLabel, horseDetailsPanelGbc);

        noOfWinsTextField = new JLabel("Wins");
        horseDetailsPanelGbc.gridx = 1;
        horseDetailsPanelGbc.gridy = 1;
        horseDetailsPanel.add(noOfWinsTextField, horseDetailsPanelGbc);

        //add averageSpeedLabel and textField to horseDetailsPanel
        averageSpeedLabel = new JLabel("Average Speed:");
        horseDetailsPanelGbc.gridx = 0;
        horseDetailsPanelGbc.gridy = 2;
        horseDetailsPanel.add(averageSpeedLabel, horseDetailsPanelGbc);

        averageSpeedTextField = new JLabel("Speed");
        horseDetailsPanelGbc.gridx = 1;
        horseDetailsPanelGbc.gridy = 2;
        horseDetailsPanel.add(averageSpeedTextField, horseDetailsPanelGbc);

        // add horseDetailsPanel to statisticsPanel
        statisticsPanelGbc.gridx = 0;
        statisticsPanelGbc.gridy = 2;
        statisticsPanel.add(horseDetailsPanel, statisticsPanelGbc);

        //add statsHistory to statisticsPanel;
        historyTextField = new JTextArea("Stats");
        statisticsPanelGbc.gridx = 0;
        statisticsPanelGbc.gridy = 3;
        statisticsPanel.add(historyTextField,statisticsPanelGbc);

        //Listeners

        horseSelectorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Horse selectedHorse = (Horse) horseSelectorComboBox.getSelectedItem();
                if (selectedHorse != null) {
                    noOfRacesTextField.setText(String.valueOf(selectedHorse.getTotalRaces()));
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
