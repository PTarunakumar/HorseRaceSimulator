import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AddHorseGUI extends JDialog{
    private Horse horse;
    private JFrame frame;
    private JPanel addPanel;
    private JPanel horseFieldPanel;
    private JLabel confidenceLabel;
    private JLabel symbolLabel;
    private JLabel nameLabel;
    private JLabel breedLabel;
    private JLabel coatColourLabel;
    private JTextField symbolTextField;
    private JTextField nameTextField;
    private JSlider confidenceSlider;
    private JPanel titlePanel;
    private JLabel addHorseLabel;
    private JButton addButton;
    private JComboBox<String> breedComboBox;
    private JPanel addButtonPanel;
    private JColorChooser horseCoatColorChooser;
    private JLabel accessoryLabel;
    private JComboBox<String> accessoryComboBox;

    AddHorseGUI(JFrame parent)
    {
        super(parent, "Add Horse", true);

        // Add Panel
        addPanel = new JPanel(new GridBagLayout());
        GridBagConstraints addPanelGbc = new GridBagConstraints();

        // Title Panel
        titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints titlePanelGbc = new GridBagConstraints();

        //addHorseLabel to titlePanel
        addHorseLabel = new JLabel("Add Horse");
        addHorseLabel.setFont(new Font("Arial Black", Font.BOLD, 24));

        titlePanelGbc.gridx = 0;
        titlePanelGbc.gridy = 0;
        titlePanel.add(addHorseLabel, addPanelGbc);

        // Add titlePanel to addPanel
        addPanelGbc = new GridBagConstraints();
        addPanelGbc.gridx = 0;
        addPanelGbc.gridy = 0;
        addPanelGbc.fill = GridBagConstraints.HORIZONTAL;
        addPanel.add(titlePanel, addPanelGbc);

        // horseFieldPanel
        horseFieldPanel = new JPanel(new GridBagLayout());
        GridBagConstraints horseFieldPanelGbc = new GridBagConstraints();
        horseFieldPanelGbc.insets = new Insets(5, 5, 5, 5);
        horseFieldPanelGbc.fill = GridBagConstraints.HORIZONTAL;

        // add symbolLabel and symbolTextField to horseFieldPanel
        symbolLabel = new JLabel("Symbol:");
        horseFieldPanelGbc.gridx = 0;
        horseFieldPanelGbc.gridy = 0;
        horseFieldPanel.add(symbolLabel, horseFieldPanelGbc);

        symbolTextField = new JTextField(1);
        horseFieldPanelGbc.gridx = 1;
        horseFieldPanel.add(symbolTextField, horseFieldPanelGbc);

        // add nameLabel and nameTextField to horseFieldPanel
        nameLabel = new JLabel("Name:");
        horseFieldPanelGbc.gridx = 0;
        horseFieldPanelGbc.gridy = 1;
        horseFieldPanel.add(nameLabel, horseFieldPanelGbc);

        nameTextField = new JTextField(10);
        horseFieldPanelGbc.gridx = 1;
        horseFieldPanel.add(nameTextField, horseFieldPanelGbc);

        // add confidenceLabel and slider to horseFieldPanel
        confidenceLabel = new JLabel("Confidence:");
        horseFieldPanelGbc.gridx = 0;
        horseFieldPanelGbc.gridy = 2;
        horseFieldPanel.add(confidenceLabel, horseFieldPanelGbc);

        confidenceSlider = new JSlider(10, 100, 50);
        confidenceSlider.setMajorTickSpacing(10);
        confidenceSlider.setPaintTicks(true);
        horseFieldPanelGbc.gridx = 1;
        horseFieldPanel.add(confidenceSlider, horseFieldPanelGbc);

        JLabel confidenceCounter = new JLabel(Double.toString((double) confidenceSlider.getValue() / 100));
        confidenceCounter.setPreferredSize(new Dimension(30, confidenceCounter.getPreferredSize().getSize().height));
        horseFieldPanelGbc.gridx = 2;
        horseFieldPanel.add(confidenceCounter, horseFieldPanelGbc);

        // add breedLabel and ComboBox to horseFieldPanel
        breedLabel = new JLabel("Breed:");
        horseFieldPanelGbc.gridx = 0;
        horseFieldPanelGbc.gridy = 3;
        horseFieldPanel.add(breedLabel, horseFieldPanelGbc);

        breedComboBox = new JComboBox<>();
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
        horseFieldPanelGbc.gridx = 1;
        horseFieldPanel.add(breedComboBox, horseFieldPanelGbc);

        // add accessoryLabel and ComboBox to horseFieldPanel
        accessoryLabel = new JLabel("Accessory:");
        horseFieldPanelGbc.gridx = 0;
        horseFieldPanelGbc.gridy = 4;
        horseFieldPanel.add(accessoryLabel, horseFieldPanelGbc);

        accessoryComboBox = new JComboBox<>();
        accessoryComboBox.addItem(" ");
        for (String item : Horse.accessoryList) {
            accessoryComboBox.addItem(item);
        }

        horseFieldPanelGbc.gridx = 1;
        horseFieldPanel.add(accessoryComboBox, horseFieldPanelGbc);

        // add accessoryLabel and ComboBox to horseFieldPanel
        coatColourLabel = new JLabel("Coat Colour:");
        horseFieldPanelGbc.gridx = 0;
        horseFieldPanelGbc.gridy = 5;
        horseFieldPanel.add(coatColourLabel, horseFieldPanelGbc);

        horseCoatColorChooser = new JColorChooser();
        horseFieldPanelGbc.gridx = 1;
        horseFieldPanel.add(horseCoatColorChooser, horseFieldPanelGbc);

        // Add horseFieldLabelContainer to addPanel
        addPanelGbc.gridy = 1;
        addPanel.add(horseFieldPanel, addPanelGbc);

        // addButtonPanel
        addButtonPanel = new JPanel(new GridBagLayout());

        //Since both is used in similar ways and have only one item centered, may be changed in the future
        GridBagConstraints addButtonPanelGbc = titlePanelGbc;

        //Add addButton to addButtonPanel
        addButton = new JButton("Add");
        addButtonPanel.add(addButton, addButtonPanelGbc);

        // Add addContainer to main panel
        addPanelGbc.gridx = 0;
        addPanelGbc.gridy = 2;
        addPanel.add(addButtonPanel, addPanelGbc);

        confidenceSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                confidenceCounter.setText(Double.toString((double) confidenceSlider.getValue() / 100));
            }
        });

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
                else if (!accessoryValidate())
                {
                    accessoryComboBox.revalidate();
                    JOptionPane.showMessageDialog(null, "Accessory cannot be empty");
                }
                else if (!colourValidate())
                {
                    horseCoatColorChooser.revalidate();
                    JOptionPane.showMessageDialog(null, "This color is not allowed");
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

        RaceFrameHandler.initialiseDialog(this,addPanel);
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
        return !breedComboBox.getSelectedItem().toString().equals(" ");
    }

    private boolean accessoryValidate() {return !accessoryComboBox.getSelectedItem().toString().equals(" ");}

    private boolean colourValidate() {return !horseCoatColorChooser.getColor().equals(Color.WHITE);}
    private double calculateConfidence(int confidence)
    {
        return (double)confidence / 100;
    }
    private void setHorse(Horse horse)
    {
        this.horse = horse;
    }

    public static void main(String[] args) {
        new AddHorseGUI(new JFrame());
    }
}

