import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsGUI {
    private JPanel statisticsPanel;
    private JPanel horseDetailsContainer;
    private JLabel raceParticipationCountTextField;
    private JLabel noOfWinsTextField;
    private JLabel noOfWinsLabel;
    private JLabel raceParticipationCountLabel;
    private JComboBox<Horse> horseSelectorComboBox;
    private JPanel horseSelectorContainer;

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
                }
            }
        });

        RaceFrameHandler.initialiseDisposableFrame(new JFrame(), statisticsPanel);
    }
}
