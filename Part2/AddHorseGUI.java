import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddHorseGUI extends JDialog{
    private Horse horse;
    private JFrame frame;
    private JPanel addPanel;
    private JPanel horseFieldLabelContainer;
    private JLabel confidenceLabel;
    private JLabel symbolLabel;
    private JLabel nameLabel;
    private JLabel breedLabel;
    private JLabel coatColourLabel;
    private JTextField symbolTextField;
    private JTextField nameTextField;
    private JSlider confidenceSlider;
    private JPanel titleContainer;
    private JLabel addHorseLabel;
    private JButton addButton;
    private JComboBox<String> breedComboBox;
    private JPanel addContainer;
    private JColorChooser horseCoatColorChooser;
    private JLabel accessoryLabel;
    private JComboBox<String> accessoryComboBox;

    AddHorseGUI(JFrame parent)
    {
        super(parent, "Add Horse", true);

        breedComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
                if (value instanceof String item) {
                    label.setText(item);
                    label.setIcon(Horse.breedIcons.get(item));
                }
                return label;
            }
        });

        breedComboBox.addItem(" ");
        for (String item : Horse.breedIcons.keySet()) {
            breedComboBox.addItem(item);
        }

        accessoryComboBox.addItem(" ");
        for (String item : Horse.accessoryList) {
            accessoryComboBox.addItem(item);
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!symbolValidate()) {
                    symbolTextField.revalidate();
                    JOptionPane.showMessageDialog(null, "Symbol must be a single character", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (!nameValidate())
                {
                    symbolTextField.revalidate();
                    JOptionPane.showMessageDialog(null, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (!breedValidate())
                {
                    symbolTextField.revalidate();
                    JOptionPane.showMessageDialog(null, "Breed cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    char Symbol = symbolTextField.getText().charAt(0);
                    String name = nameTextField.getText();
                    double confidence = calculateConfidence(confidenceSlider.getValue());
                    String breed = breedComboBox.getSelectedItem().toString();
                    String accessory = accessoryComboBox.getSelectedItem().toString();
                    Color color = horseCoatColorChooser.getColor();
                    horse = new Horse(Symbol, name, confidence, breed, accessory, color);
                    dispose();
                }
            }
        });
        addPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        }, KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        RaceFrameHandler.initialiseDialog(this,addPanel);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public Horse getHorse()
    {
        return horse;
    }

    private boolean symbolValidate()
    {
        return symbolTextField.getText().length() == 1 && !(symbolTextField == null);
    }

    private boolean nameValidate()
    {
        return !nameTextField.getText().isEmpty();
    }

    private boolean breedValidate()
    {
        return !breedComboBox.getSelectedItem().toString().isEmpty();
    }

    private double calculateConfidence(int confidence)
    {
        return (double)confidence / 100;
    }
    private void setHorse(Horse horse)
    {
        this.horse = horse;
    }
}

