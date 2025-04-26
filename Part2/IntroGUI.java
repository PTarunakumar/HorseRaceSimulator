import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroGUI extends JDialog{
    private int value;
    private JFrame frame;
    private JPanel introPanel;
    private JButton exitButton;
    private JButton startRaceButton;

    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JLabel authorLabel;
    private JLabel titleLabel;

    void setUI()
    {

    }

    public IntroGUI(JFrame parent) {
        super(parent, "HorseRaceSimulator", true);

        //Intro Panel
        introPanel = new JPanel(new GridBagLayout());
        GridBagConstraints introPanelGbc = new GridBagConstraints();
        introPanelGbc.insets = new Insets(10, 10, 10, 10);

        //Title Panel
        titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints titlePanelGbc = new GridBagConstraints();

        //add title and author label
        titleLabel = new JLabel("HorseRaceSimulator");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        titlePanelGbc.gridx = 0;
        titlePanelGbc.gridy = 0;
        titlePanel.add(titleLabel, titlePanelGbc);

        authorLabel = new JLabel("By PTarunakumar");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 18));
        titlePanelGbc.gridy = 1;
        titlePanel.add(authorLabel, titlePanelGbc);

        //add titlePanel to introPanel
        introPanelGbc.gridx = 0;
        introPanelGbc.gridy = 0;
        introPanel.add(titlePanel, introPanelGbc);

        //Button Panel
        buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonPanelGbc = new GridBagConstraints();
        buttonPanelGbc.insets = new Insets(5, 5, 5, 5);

        //add exitButton and startRaceButton
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value = 1;
                dispose();

            }
        });
        exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanelGbc.gridx = 0;
        buttonPanelGbc.gridy = 0;
        buttonPanel.add(exitButton, buttonPanelGbc);

        startRaceButton = new JButton("Start Race");
        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value = 0;
                dispose();
            }
        });

        startRaceButton.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanelGbc.gridx = 1;
        buttonPanel.add(startRaceButton, buttonPanelGbc);

        //add buttonPanel to introPanel
        introPanelGbc.gridy = 1;
        introPanel.add(buttonPanel, introPanelGbc);


        RaceFrameHandler.initialiseDialog(this, introPanel);
    }

    public static void main(String[] args) {
        new IntroGUI(new JFrame());
    }
    public int getReturnedValue() {
        return value;
    }
}
