import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BettingGUI {
    private JFrame frame = new JFrame();
    private JPanel bettingPanel;
    private JPanel titlePanel;
    private JPanel moneyPanel;
    private JPanel bettingFieldsPanel;
    private JComboBox<Horse> horseSelectComboBox;
    private JLabel betAmountLabel;
    private JLabel moneyDisplayLabel;
    private JLabel moneyAvailableLabel;
    private JLabel horseSelectLabel;
    private JLabel betAmountCounter;
    private JSlider betAmountSlider;
    private JTextField betAmountTextField;
    private JPanel submitButtonPanel;
    private JButton submitButton;
    private JLabel bettingOddsLabel;
    private JLabel oddsLabel;
    private JTextArea bettingHistory;

    BettingGUI(Race race, User user)
    {

        //Betting Panel
        bettingPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bettingPanelGbc = new GridBagConstraints();
        bettingPanelGbc.insets = new Insets(5, 30, 5, 30);



        //Title Panel
        titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints titlePanelGbc = new GridBagConstraints();

        //Add titleLabel to titlePanel
        JLabel titleLabel = new JLabel("Betting");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        titlePanelGbc.gridx = 0;
        titlePanelGbc.gridy = 0;
        titlePanel.add(titleLabel, titlePanelGbc);

        //Add titlePanel to bettingPanel
        bettingPanelGbc.gridx = 0;
        bettingPanelGbc.gridy = 0;
        bettingPanel.add(titlePanel, bettingPanelGbc);

        //Money Panel
        moneyPanel = new JPanel(new GridBagLayout());
        GridBagConstraints moneyPanelGbc = new GridBagConstraints();


        //Add moneyAvailableLabel and display to moneyPanel
        moneyAvailableLabel = new JLabel("Money Available: ");
        moneyPanelGbc.gridx = 0;
        moneyPanelGbc.gridy = 0;
        moneyPanel.add(moneyAvailableLabel, moneyPanelGbc);

        moneyDisplayLabel = new JLabel(Integer.toString(user.getMoney()));
        moneyPanelGbc.gridx = 1;
        moneyPanel.add(moneyDisplayLabel, moneyPanelGbc);

        //Add moneyPanel to bettingPanel
        bettingPanelGbc.gridx = 0;
        bettingPanelGbc.gridy = 1;
        bettingPanel.add(moneyPanel, bettingPanelGbc);

        //Betting Fields Panel
        bettingFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bettingFieldsPanelGbc = new GridBagConstraints();
        bettingFieldsPanelGbc.fill = GridBagConstraints.HORIZONTAL;
        bettingFieldsPanelGbc.insets = new Insets(5, 0,5, 0);

        //Add horseSelectLabel and comboBox to bettingFieldsPanel
        horseSelectLabel = new JLabel("Select Horse: ");
        bettingFieldsPanelGbc.gridx = 0;
        bettingFieldsPanelGbc.gridy = 0;
        bettingFieldsPanel.add(horseSelectLabel, bettingFieldsPanelGbc);

        horseSelectComboBox = new JComboBox<>();
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
                label.setIcon(horse.getColouredBreedIcon());
                return label;
            }
        });
        bettingFieldsPanelGbc.gridx = 1;
        bettingFieldsPanelGbc.gridy = 0;
        bettingFieldsPanel.add(horseSelectComboBox, bettingFieldsPanelGbc);

        //Add betAmountTextField to bettingFieldsPanel
        bettingOddsLabel = new JLabel("Betting Odds: ");
        bettingFieldsPanelGbc.gridx = 0;
        bettingFieldsPanelGbc.gridy = 1;
        bettingFieldsPanel.add(bettingOddsLabel, bettingFieldsPanelGbc);

        oddsLabel = new JLabel("1:1"); // Default odds
        oddsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bettingFieldsPanelGbc.gridx = 1;
        bettingFieldsPanelGbc.gridy = 1;
        bettingFieldsPanel.add(oddsLabel, bettingFieldsPanelGbc);


        //Add betAmountLabel and slider and counter to bettingFieldsPanel
        betAmountLabel = new JLabel("Bet Amount: ");
        bettingFieldsPanelGbc.gridx = 0;
        bettingFieldsPanelGbc.gridy = 2;
        bettingFieldsPanel.add(betAmountLabel, bettingFieldsPanelGbc);

        betAmountSlider = new JSlider(0, user.getMoney(), 0);
        betAmountSlider.setMajorTickSpacing(100);
        betAmountSlider.setPaintTicks(true);
        bettingFieldsPanelGbc.gridx = 1;
        bettingFieldsPanelGbc.gridy = 2;
        bettingFieldsPanel.add(betAmountSlider, bettingFieldsPanelGbc);

        betAmountCounter = new JLabel(Integer.toString(betAmountSlider.getValue()));
        betAmountSlider.setPreferredSize(new Dimension(50, betAmountCounter.getPreferredSize().height));
        bettingFieldsPanelGbc.gridx = 2;
        bettingFieldsPanelGbc.gridy = 2;
        bettingFieldsPanel.add(betAmountCounter, bettingFieldsPanelGbc);

        //Add bettingFieldsPanel to bettingPanel
        bettingPanelGbc.gridx = 0;
        bettingPanelGbc.gridy = 2;
        bettingPanel.add(bettingFieldsPanel, bettingPanelGbc);

        //submitButton Panel
        submitButtonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints submitButtonPanelGbc = new GridBagConstraints();

        //add submitButton to submitButtonPanel
        submitButton = new JButton("Submit");
        submitButtonPanelGbc.gridx = 0;
        submitButtonPanelGbc.gridy = 0;
        submitButtonPanel.add(submitButton, submitButtonPanelGbc);

        //add changeButtonPanel to changeTrackPanel
        bettingPanelGbc.gridx = 0;
        bettingPanelGbc.gridy = 3;
        bettingPanel.add(submitButtonPanel, bettingPanelGbc);

        //add history
        bettingHistory = new JTextArea(createHistory(user));
        bettingPanelGbc.gridx = 0;
        bettingPanelGbc.gridy = 4;
        bettingPanel.add(bettingHistory, bettingPanelGbc);

        //Creating Custom Components
        horseSelectComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Horse selectedHorse = (Horse) horseSelectComboBox.getSelectedItem();
                if (selectedHorse != null) {
                    oddsLabel.setText("1:" + calculateBettingOdds(selectedHorse));
                }
            }
        });

        betAmountSlider.addChangeListener(new ChangeListener() {
            @Override
            //Display for slider
            public void stateChanged(ChangeEvent e) {
                betAmountCounter.setText(Integer.toString(betAmountSlider.getValue()));
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
                        JOptionPane.showMessageDialog(null, "You have placed a bet of " + betAmount + " on " + horseBettedOn.getName() + " with potential winnings of " + toTwoDecimalPlace(betAmount * bettingOdds));
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
                        betAmountSlider.setMaximum(user.getMoney());
                        bettingHistory.setText(createHistory(user));
                        frame.pack();
                        submitButton.setEnabled(true);

                    });
                }).start();
            }
        });

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
                history += "Bet amount: " + user.getBetAmountHistory().get(i) + " - Outcome: Lose - Money: " + user.getMoneyHistory().get(i) + "\n";
            }
        }
        return history;
    }
}
