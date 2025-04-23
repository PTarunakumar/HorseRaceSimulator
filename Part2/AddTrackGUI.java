import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class AddTrackGUI extends JDialog{
    private Race race;
    private JFrame frame;
    private JPanel addTrackPanel;
    private JLabel laneCountLabel;
    private JSlider laneCountSlider;
    private JLabel laneCountCounter;
    private JComboBox<String> trackTypeComboBox;
    private JLabel trackTypeLabel;
    private JPanel trackFieldContainer;
    private JPanel titleContainer;
    private JButton addButton;
    private JPanel addContainer;
    private JTextArea lengthTextField;
    private JLabel lengthLabel;

    public AddTrackGUI(JFrame parent) {
        super(parent, "Add Track", true);

        laneCountCounter.setText(String.valueOf(laneCountSlider.getValue()));
        trackTypeComboBox.addItem(" ");

        for(String types: raceEffects.trackTypesList)
        {
            trackTypeComboBox.addItem(types);
        }

        laneCountSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = laneCountSlider.getValue();
                laneCountCounter.setText(String.valueOf(value));
            }
        });
        addButton.addActionListener(new ActionListener() {
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
                else if (!laneCountValidate()) {
                    JOptionPane.showMessageDialog(null, "Please select a track type", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    int length = Integer.parseInt(lengthTextField.getText());
                    int laneCount = laneCountSlider.getValue();
                    String trackType = trackTypeComboBox.getSelectedItem().toString();
                    race = new Race(length, laneCount, trackType);
                    dispose();
                }
            }
        });

        addTrackPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        RaceFrameHandler.initialiseDialog(this, addTrackPanel);
    }

    public static void main(String[] args) {
        new AddTrackGUI(new JFrame());
    }

    public Race getRace()
    {
        return race;
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

    private boolean laneCountValidate()
    {
        return laneCountSlider.getValue() != 0;
    }

    private boolean trackTypeValidate()
    {
        return trackTypeComboBox.getSelectedItem() != null && !trackTypeComboBox.getSelectedItem().toString().equals(" ");
    }
}
