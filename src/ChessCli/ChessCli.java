package ChessCli;
/*
 * Bugs:
 *   -
 * 
 * To Implement:
 *   undoMove
 *   
 *   display option for promotion type
 *   matchConcluded
 *   MatchController option to Rotate Board
 *   Timed Matches
 *   load matches from EPD files
 *   
 * Test:
 *    -
 * */

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessCli extends JFrame implements ActionListener {

    private static JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChessCli window = new ChessCli();
                    window.setVisible(true);
                    
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
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setResizable(false);
        setSize(WND_WIDTH, WND_HEIGHT);
        setTitle(WND_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        String actnString = e.getActionCommand();

        if (actnString.equals("New Game")) {
            createBoardView();
        } else if (actnString.equals("Settings")) {
            createSettingsMenu();
        } else if (actnString.equals("Exit")) {
            exit();
        }
    }

    private void createMenu() {
        contentPane = new MainMenu();

        setContentPane(contentPane);
        setVisible(true);
    }

    private void createBoardView() {
        contentPane = new BoardView("Bob", "Alice");

        setContentPane(contentPane);
        setVisible(true);
    }

    private void createSettingsMenu() {
        contentPane = new SettingsMenu();

        setContentPane(contentPane);
        setVisible(true);
    }

    private void exit() {
        System.exit(0);
    }
}
