import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class CustomiseTrackGUI extends JDialog{
    private Race race;
    private JPanel changeTrackPanel;
    private JPanel trackFieldPanel;
    private JLabel trackTypeLabel;
    private JComboBox<String> trackTypeComboBox;
    private JLabel lengthLabel;
    private JTextArea lengthTextField;
    private JPanel titlePanel;
    private JPanel changeButtonPanel;
    private JButton changeButton;
    private JLabel changeTrackLabel;

    public CustomiseTrackGUI(JFrame parent, Race race) {
        super(parent, "Add Track", true);
        this.race = race;

        //changeTrackPanel
        changeTrackPanel = new JPanel(new GridBagLayout());
        GridBagConstraints changeTrackPanelGbc = new GridBagConstraints();
        changeTrackPanelGbc.insets = new Insets(5, 30, 5, 30);

        //title Panel
        titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints titlePanelGbc = new GridBagConstraints();

        //Add changeTrackLabel to titlePanel
        changeTrackLabel = new JLabel("Change Track");
        changeTrackLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        titlePanelGbc.gridx = 0;
        titlePanelGbc.gridy = 0;
        titlePanel.add(changeTrackLabel, titlePanelGbc);

        //add titlePanel to changeTrackPanel
        changeTrackPanelGbc.gridx = 0;
        changeTrackPanelGbc.gridy = 0;
        changeTrackPanel.add(titlePanel, changeTrackPanelGbc);

        //TrackField Panel
        trackFieldPanel = new JPanel(new GridBagLayout());
        GridBagConstraints trackFieldPanelGbc = new GridBagConstraints();
        trackFieldPanelGbc.fill = GridBagConstraints.HORIZONTAL;

        //add lengthLabel and textfield to trackFieldPanel
        lengthLabel = new JLabel("Length: ");
        trackFieldPanelGbc.gridx = 0;
        trackFieldPanelGbc.gridy = 0;
        trackFieldPanel.add(lengthLabel, trackFieldPanelGbc);

        lengthTextField = new JTextArea();
        trackFieldPanelGbc.gridx = 1;
        trackFieldPanelGbc.gridy = 0;
        trackFieldPanel.add(lengthTextField, trackFieldPanelGbc);

        //add trackTypeLabel and comboBox to trackFieldPanel
        trackTypeLabel = new JLabel("Track Type: ");
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

        //add trackFieldPanel to changeTrackPanel
        changeTrackPanelGbc.gridx = 0;
        changeTrackPanelGbc.gridy = 1;
        changeTrackPanel.add(trackFieldPanel, changeTrackPanelGbc);

        //changeButtonPanel
        changeButtonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints changeButtonPanelGbc = new GridBagConstraints();
        changeButtonPanelGbc.fill = GridBagConstraints.HORIZONTAL;

        //add changeButton
        changeButton = new JButton("Change Track");
        changeButtonPanelGbc.gridx = 0;
        changeButtonPanelGbc.gridy = 0;
        changeButtonPanel.add(changeButton, changeButtonPanelGbc);

        //add changeButtonPanel to changeTrackPanel
        changeTrackPanelGbc.gridx = 0;
        changeTrackPanelGbc.gridy = 2;
        changeTrackPanel.add(changeButtonPanel, changeTrackPanelGbc);

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!trackTypeValidate())
                {
                    JOptionPane.showMessageDialog(null, "Please select a track type", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (!lengthValidate())
                {
                    JOptionPane.showMessageDialog(null, "Please enter a valid length between 1 and 60 inclusive", "Error", JOptionPane.ERROR_MESSAGE);
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

        RaceFrameHandler.initialiseDisposableDialog(this, changeTrackPanel);
    }

    public static void main(String[] args) {
        new AddTrackGUI(new JFrame());
    }

    private boolean lengthValidate()
    {
        try
        {
            int length = Integer.parseInt(lengthTextField.getText());
            if (length < 1 || length > 60)
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

