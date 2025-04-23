import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class CustomiseTrackGUI extends JDialog{
    private JPanel changeTrackPanel;
    private JPanel trackFieldContainer;
    private JLabel trackTypeLabel;
    private JComboBox<String> trackTypeComboBox;
    private JLabel lengthLabel;
    private JTextArea lengthTextField;
    private JPanel titleContainer;
    private JPanel changeContainer;
    private JButton changeButton;

    public CustomiseTrackGUI(JFrame parent, Race race) {
        super(parent, "Add Track", true);

        trackTypeComboBox.addItem(" ");

        for(String types: raceEffects.trackTypesList)
        {
            trackTypeComboBox.addItem(types);
        }

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!trackTypeValidate())
                {
                    JOptionPane.showMessageDialog(null, "Please select a track type", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (!lengthValidate())
                {
                    JOptionPane.showMessageDialog(null, "Please enter a valid length", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    int length = Integer.parseInt(lengthTextField.getText());
                    String trackType = trackTypeComboBox.getSelectedItem().toString();
                    race.changeTrack(length, trackType);
                    dispose();
                }
            }
        });

        changeTrackPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        RaceFrameHandler.initialiseDialog(this, changeTrackPanel);
    }

    public static void main(String[] args) {
        new AddTrackGUI(new JFrame());
    }

    private boolean lengthValidate()
    {
        try
        {
            int length = Integer.parseInt(lengthTextField.getText());
            if (length <= 0 || length >= 1000)
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    private boolean trackTypeValidate()
    {
        return trackTypeComboBox.getSelectedItem() != null && !trackTypeComboBox.getSelectedItem().toString().equals(" ");
    }
}

