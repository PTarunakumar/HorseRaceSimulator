import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class IntroGUI extends JDialog{
    private int value;
    private JFrame frame;
    private JPanel introPanel;
    private JButton exitButton;
    private JButton startRaceButton;
    private JPanel containerPanel;
    private JPanel titleContainerPanel;
    private JPanel buttonContainerPanel;

    public IntroGUI(JFrame parent) {
        super(parent, "HorseRaceSimulator", true);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value = 1;
                dispose();

            }
        });
        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value = 0;
                dispose();
            }
        });

        introPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        RaceFrameHandler.initialiseDialog(this, introPanel);
    }

    public int getReturnedValue() {
        return value;
    }
}
