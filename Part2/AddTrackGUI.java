import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
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
    private JPanel trackFieldPanel;
    private JPanel titlePanel;
    private JButton addButton;
    private JPanel addContainer;
    private JTextArea lengthTextField;
    private JLabel lengthLabel;

    public AddTrackGUI(JFrame parent) {
        super(parent, "Add Track", true);

        //addTrack Panel
        addTrackPanel = new JPanel(new GridBagLayout());
        GridBagConstraints addTrackPanelGbc = new GridBagConstraints();

        //title Panel
        titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints titlePanelGbc = new GridBagConstraints();

        //add addTrackLabel to titlePanel
        JLabel addTrackLabel = new JLabel("Add Track");
        addTrackLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        titlePanelGbc.gridx = 0;
        titlePanelGbc.gridy = 0;
        titlePanel.add(addTrackLabel, titlePanelGbc);

        //add titlePanel to addTrackPanel
        addTrackPanelGbc.gridx = 0;
        addTrackPanelGbc.gridy = 0;
        addTrackPanel.add(titlePanel, addTrackPanelGbc);

        //trackFieldPanel
        trackFieldPanel = new JPanel(new GridBagLayout());
        GridBagConstraints trackFieldPanelGbc = new GridBagConstraints();
        trackFieldPanelGbc.fill = GridBagConstraints.HORIZONTAL;
        trackFieldPanelGbc.insets = new Insets(5, 5, 5, 5);
        //add lengthLabel and textField
        lengthLabel = new JLabel("Length:");
        trackFieldPanelGbc.gridx = 0;
        trackFieldPanelGbc.gridy = 0;
        trackFieldPanel.add(lengthLabel, trackFieldPanelGbc);

        lengthTextField = new JTextArea(1, 10);
        trackFieldPanelGbc.gridx = 1;
        trackFieldPanel.add(lengthTextField, trackFieldPanelGbc);

        //add trackTypeLabel and comboBox
        trackTypeLabel = new JLabel("Track Type:");
        trackFieldPanelGbc.gridx = 0;
        trackFieldPanelGbc.gridy = 1;
        trackFieldPanel.add(trackTypeLabel, trackFieldPanelGbc);

        trackTypeComboBox = new JComboBox<>();
        trackTypeComboBox.addItem(" ");
        for(String types: raceEffects.trackTypesList)
        {
            trackTypeComboBox.addItem(types);
        }
        trackFieldPanelGbc.gridx = 1;
        trackFieldPanel.add(trackTypeComboBox, trackFieldPanelGbc);

        //add laneCountLabel and slider and counter
        laneCountLabel = new JLabel("Lane Count:");
        trackFieldPanelGbc.gridx = 0;
        trackFieldPanelGbc.gridy = 2;
        trackFieldPanel.add(laneCountLabel, trackFieldPanelGbc);

        laneCountSlider = new JSlider(2, 10, 2);
        laneCountSlider.setMajorTickSpacing(1);
        laneCountSlider.setPaintTicks(true);
        laneCountSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = laneCountSlider.getValue();
                laneCountCounter.setText(String.valueOf(value));
            }
        });
        trackFieldPanelGbc.gridx = 1;
        trackFieldPanel.add(laneCountSlider, trackFieldPanelGbc);

        laneCountCounter = new JLabel(Integer.toString(laneCountSlider.getValue()));
        trackFieldPanelGbc.gridx = 2;
        trackFieldPanel.add(laneCountCounter, trackFieldPanelGbc);

        //add trackFieldPanel to addTrackPanel
        addTrackPanelGbc.gridx = 0;
        addTrackPanelGbc.gridy = 1;
        addTrackPanel.add(trackFieldPanel, addTrackPanelGbc);

        //add button
        addButton = new JButton("Add Track");
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
        addTrackPanelGbc.gridx = 0;
        addTrackPanelGbc.gridy = 2;
        addTrackPanel.add(addButton, addTrackPanelGbc);

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
