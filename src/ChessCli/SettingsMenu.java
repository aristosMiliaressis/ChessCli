package ChessCli;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SettingsMenu extends JPanel {
    private static final long serialVersionUID = 6438395677349244859L;

    /**
     * Create the panel.
     */
    public SettingsMenu() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        BoxLayout bl_contentPane = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(bl_contentPane);
    }

}
