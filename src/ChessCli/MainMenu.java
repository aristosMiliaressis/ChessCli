package ChessCli;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainMenu extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    /**
     * Create the panel.
     */
    protected MainMenu() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        BoxLayout bl_contentPane = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(bl_contentPane);
        
        Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
        add(rigidArea_2);
        
        /* New Game Button */
        JButton btnNewGame = new JButton("New Game");
        btnNewGame.addActionListener(this);
        btnNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(btnNewGame);
        
        Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
        add(rigidArea);
        
        /* Settings Button */
        JButton btnSettings = new JButton("Settings");
        btnSettings.addActionListener(this);
        btnSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(btnSettings);
        
        
        Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
        add(rigidArea_1);
        
        /* Exit Button */
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(this);
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(btnExit);
    }

    public void actionPerformed(ActionEvent e) {
        JComponent source = (JComponent) e.getSource();

        // loop over parents until you find JFrame
        Container container = source.getParent();
        while (!(container instanceof ChessCli))
            container = container.getParent();

        // call JFrames Event Listener
        ((ActionListener) container).actionPerformed(e);
    }

}
