import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class RaceFrameHandler {
    /**
     *Initialises the GUI for the selected Frame and Panel for the class
     * Centers, packs and sets the frame to be visible
     *
     * @param dialog The frame to be initialised
     * @param startPanel The panel which needs to be displayed
     */
    static public void initialiseDialog(JDialog dialog, JPanel startPanel)
    {
        startPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        }, KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        dialog.setContentPane(startPanel);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    static public void initialiseFrame(JFrame frame, JPanel startPanel)
    {
        frame.setContentPane(startPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static public void initialiseDisposableFrame(JFrame frame, JPanel startPanel)
    {
        initialiseFrame(frame, startPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
