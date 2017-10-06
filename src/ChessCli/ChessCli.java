package ChessCli;
/*
 * Bugs:
 *   Fix isKingPinned
 * 
 * Refactor:
 *   -
 *      
 * To Implement:
 *   display option for promotion type (use a pop up dialog)
 *   matchConcluded (use a pop up dialog)
 *   
 *   MatchController option to Rotate Board
 *   undoMove (castling rights complication)
 *   Timed Matches
 *   load matches from EPD files
 *   
 * Test:
 *    -
 * */

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessCli extends JFrame implements ActionListener {
    private static final long serialVersionUID = 3898297581454001400L;
    
    private static JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChessCli window = new ChessCli();
                    
                    window.createMenu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static final int WND_WIDTH  = 740;
    private static final int WND_HEIGHT = 620;
    private static final String WND_TITLE = "Chess Demo";

    /**
     * Create the frame.
     */
    private ChessCli() {
        this.initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WND_WIDTH, WND_HEIGHT);
        this.setResizable(false);
        this.setTitle(WND_TITLE);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        
        int xPos = (dim.width / 2) - (this.getWidth() / 2);
        int yPos = (dim.height / 2) - (this.getHeight() / 2);
        
        this.setLocation(xPos, yPos);
    }

    public void actionPerformed(ActionEvent e) {
        String actnString = e.getActionCommand();

        if (actnString.equals("New Game")) {
            this.createBoardView();
        } else if (actnString.equals("Settings")) {
            this.createSettingsMenu();
        } else if (actnString.equals("Exit")) {
            this.exit();
        }
    }

    private void createMenu() {
        contentPane = new MainMenu();

        this.setContentPane(contentPane);
        this.setVisible(true);
    }

    private void createBoardView() {
        contentPane = new BoardView("Bob", "Alice");

        this.setContentPane(contentPane);
        this.setVisible(true);
    }

    private void createSettingsMenu() {
        contentPane = new SettingsMenu();

        this.setContentPane(contentPane);
        this.setVisible(true);
    }

    private void exit() {
        System.exit(0);
    }
}
