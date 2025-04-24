import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BettingGUI {
    private JFrame frame = new JFrame();
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
        //Creating Custom Components
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
        horseSelectComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Horse selectedHorse = (Horse) horseSelectComboBox.getSelectedItem();
                if (selectedHorse != null) {
                    oddsLabel.setText("1:" + calculateBettingOdds(selectedHorse));
                }
            }
        });

        betAmountSlider.setMaximum(user.getMoney());
        betAmountSlider.setMinimum(0);
        betAmountSlider.addChangeListener(new ChangeListener() {
            @Override
            //Display for slider
            public void stateChanged(ChangeEvent e) {
                betAmountCounter.setText(Integer.toString(betAmountSlider.getValue()));
            }
        });

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
                new Thread(() -> {
                    Horse horseBettedOn = (Horse) horseSelectComboBox.getSelectedItem();

                    int betAmount = betAmountSlider.getValue();
                    double bettingOdds = calculateBettingOdds(horseBettedOn);

                    //Disable submit button and reduce money
                    SwingUtilities.invokeLater(() -> {
                        submitButton.setEnabled(false);
                        user.setMoney(user.getMoney() - betAmount);
                        moneyDisplayLabel.setText(Integer.toString(user.getMoney()));
                    });

                    //Wait for race to finish to see result
                    while (!race.getFinished())
                    {
                        try{
                            Thread.sleep(100);
                        }catch(Exception f)
                        {
                            JOptionPane.showMessageDialog(null, "Thread Error in BetingGUI");
                            return;
                        }
                    }


                    SwingUtilities.invokeLater(() -> {
                        //If user wins, add money and display new value
                        //If user loses, do nothing
                        //For both cases, add to betting history and display a message
                        if (race.raceWonBy(horseBettedOn)) {
                            user.setMoney((int) (user.getMoney() + betAmount * bettingOdds));
                            moneyDisplayLabel.setText(Integer.toString(user.getMoney()));

                            user.updateOutcome(betAmount, true, user.getMoney());
                            JOptionPane.showMessageDialog(null, "You won the bet on " + horseBettedOn.getName() + "!");
                        }
                        else
                        {
                            user.updateOutcome(betAmount, false, user.getMoney());
                            JOptionPane.showMessageDialog(null, "You lost the bet on " + horseBettedOn.getName() + ".");
                        }

                        //Update bettingHistory and allow betting for the next race
                        bettingHistory.setText(createHistory(user));
                        frame.pack();
                        submitButton.setEnabled(true);

                    });
                }).start();
            }
        });

        bettingHistory.setText(createHistory(user));

        //Initialise Frame
        RaceFrameHandler.initialiseDisposableFrame(frame, bettingPanel);
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

    public String createHistory(User user)
    {
        String history = "Initial money : " + user.getInitialMoney() + "\n";
        for (int i = 0; i < user.getBetOutcomeHistory().size(); i++)
        {
            if (user.getBetOutcomeHistory().get(i))
            {
                history += "Bet amount: " + user.getBetAmountHistory().get(i) + " - Outcome: Win - Money: " + user.getMoneyHistory().get(i) + "\n";
            }
            else
            {
                history += "Bet amount: " + user.getMoneyHistory().get(i) + " - Outcome: Lose - Money: " + user.getMoneyHistory().get(i) + "\n";
            }
        }
        return history;
    }
}
