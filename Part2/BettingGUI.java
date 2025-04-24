import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BettingGUI {
    private JPanel bettingPanel;
    private JPanel titleContainer;
    private JPanel moneyContainer;
    private JPanel bettingContainer;
    private JComboBox<Horse> horseSelectComboBox;
    private JLabel betAmountLabel;
    private JLabel moneyDisplayLabel;
    private JLabel moneyAvailableLabel;
    private JLabel horseSelectLabel;
    private JLabel betAmountCounter;
    private JSlider betAmountSlider;
    private JTextField betAmountTextField;
    private JPanel submitPanel;
    private JButton submitButton;
    private JLabel bettingOddsLabel;
    private JLabel oddsLabel;
    private JTextArea bettingHistory;


    BettingGUI(Race race, User user)
    {
        moneyDisplayLabel.setText(Integer.toString(user.getMoney()));

        for(Horse horse: race.getHorses())
        {
            horseSelectComboBox.addItem(horse);
        }

        horseSelectComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
                Horse horse = (Horse) value;
                label.setText(horse.getName());
                return label;
            }
        });

        betAmountSlider.setMaximum(user.getMoney());
        betAmountSlider.setMinimum(0);
        betAmountSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                betAmountCounter.setText(Integer.toString(betAmountSlider.getValue()));
            }
        });

        RaceFrameHandler.initialiseDisposableFrame(new JFrame(), bettingPanel);
        betAmountTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (betAmountSlider.getValue() > user.getMoney() || betAmountSlider.getValue() < 0) {
                        JOptionPane.showMessageDialog(bettingPanel, "Bet amount must be between 0 and " + user.getMoney());
                        betAmountTextField.setText(Integer.toString(betAmountSlider.getValue()));
                        betAmountSlider.setValue(betAmountSlider.getValue());
                    }
                    else {
                            betAmountSlider.setValue(Integer.parseInt(betAmountTextField.getText()));
                    }
                } catch (Exception f) {
                    JOptionPane.showMessageDialog(bettingPanel, "Invalid input. Please enter a valid number.");
                    betAmountTextField.setText("0");
                }
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Check1");
                        Horse horseBettedOn = (Horse) horseSelectComboBox.getSelectedItem();
                        int betAmount = betAmountSlider.getValue();

                        System.out.println("Check2");
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Check3");
                                submitButton.setEnabled(false);
                                user.setMoney(user.getMoney() - betAmount);
                                moneyDisplayLabel.setText(Integer.toString(user.getMoney()));
                                System.out.println("Check4");
                            }
                        });

                        System.out.println("Check5");
                        while (!race.getFinished())
                        {
                            System.out.println("Check6: " + race.getFinished());
                            try{
                                Thread.sleep(100);
                            }catch(Exception f)
                            {
                                JOptionPane.showMessageDialog(null, "Thread Error in BetingGUI");
                                return;
                            }
                        }
                        System.out.println("Check7");
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                if (race.raceWonBy(horseBettedOn)) {
                                    user.setMoney((int) (user.getMoney() + betAmount * calculateBettingOdds(horseBettedOn)));
                                    moneyDisplayLabel.setText(Integer.toString(user.getMoney()));
                                    System.out.println("You win");
                                    JOptionPane.showMessageDialog(null, "You won the bet on " + horseBettedOn.getName() + "!");
                                } else {
                                    System.out.println("you lose");
                                    JOptionPane.showMessageDialog(null, "You lost the bet on " + horseBettedOn.getName() + ".");
                                }
                                submitButton.setEnabled(true);
                            }
                        });
                    }
                }).start();
            }
        });
        horseSelectComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Horse selectedHorse = (Horse) horseSelectComboBox.getSelectedItem();
                if (selectedHorse != null) {
                    System.out.println(calculateBettingOdds(selectedHorse));
                    oddsLabel.setText("1:" + calculateBettingOdds(selectedHorse));
                }
            }
        });
    }

    public static double calculateBettingOdds(Horse horse)
    {
        double score = horse.getHorseSpeed() * (1- horse.getConfidence()) * (1 - Race.calculateFallRate(horse));

        return (score > 1) ? 1 : toTwoDecimalPlace(1/score);
    }

    public static double toTwoDecimalPlace(double num)
    {
        return Math.round(num * 100.0) / 100.0;
    }
}
