package ChessCli;

import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ChessCore.Board;
import ChessCore.Piece;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class BoardView extends JPanel implements MouseListener {
    private static final int SQUARE_SIZE = 60;
    private static final int BORDER_SIZE = 30;
    private static final int MARGIN_SIZE = 6;
    private static final int BOARD_SIZE = BORDER_SIZE + SQUARE_SIZE * 8;
    
    private static final String epdInitArray = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq ­-";
    private static Piece selectedPiece = null;
    private static Match match = new Match();
    
    private JPanel movePanel = new JPanel(new BorderLayout());
    private static DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> moveList = new JList<>(model);
    private JLabel lblWhite = new JLabel();
    private JLabel lblBlack = new JLabel();
    private final JButton button = new JButton("Export Match");

    protected BoardView(String white, String black) {
        setLayout(null);
        this.addMouseListener(this);
        
        movePanel.setBounds(540, 30, 188, 520);
        movePanel.add(moveList);
        add(movePanel);
        
        JCheckBox chckbxRotateBoard = new JCheckBox("Rotate Board");
        chckbxRotateBoard.setBounds(22, 563, 129, 23);
        add(chckbxRotateBoard);
        
        JButton btnUndoMove = new JButton("Undo Move");
        btnUndoMove.setBounds(611, 562, 117, 25);
        add(btnUndoMove);
        
        lblWhite.setText("White: " + white);
        lblWhite.setBounds(12, 535, 139, 15);
        add(lblWhite);
        
        lblBlack.setText("Black: " + black);
        lblBlack.setBounds(392, 535, 130, 15);
        add(lblBlack);
        button.setBounds(237, 562, 139, 25);
        
        add(button);
        Board.initBoard(epdInitArray);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);
    }
    
    protected static Piece getSelectedPiece() {
        return selectedPiece;
    }
    
    protected static void setSelectedPiece(Piece piece) {
        selectedPiece = piece;
    }

    @SuppressWarnings("incomplete-switch")
    private void drawBoard(Graphics g) {
        Board.Coords coords = new Board.Coords('a', 1);

        if (Match.getCurrentPlayer() == Piece.Color.WHITE) {
            lblWhite.setForeground(Color.RED);
            lblBlack.setForeground(null);
        } else {
            lblBlack.setForeground(Color.RED);
            lblWhite.setForeground(null);
        }
        
        // Draw Board and Algebraic Notation
        for (coords.file = 'a'; coords.file <= 'h'; coords.file++) {
            g.setColor(Color.BLUE);
            g.drawString("" + Character.toUpperCase(coords.file),
                        SQUARE_SIZE * (coords.file - 'a') + BORDER_SIZE + SQUARE_SIZE / 2, 20);
            g.drawString(Integer.toString(8 - (coords.file - 'a')),
                        10, SQUARE_SIZE * (coords.file - 'a') + BORDER_SIZE + SQUARE_SIZE / 2);

            for (coords.rank = 1; coords.rank <= 8; coords.rank++) {

                if ((((coords.file - 'a') % 2) == 1 && (coords.rank % 2) == 1) ||
                    (((coords.file - 'a') % 2) == 0 && (coords.rank % 2) == 0)) {
                    g.setColor(Color.darkGray);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(SQUARE_SIZE * (coords.rank - 1) + BORDER_SIZE,
                           SQUARE_SIZE * (coords.file - 'a') + BORDER_SIZE,
                           SQUARE_SIZE, SQUARE_SIZE);
            }
        }

        // Iterate through Boards Squares
        for (coords.file = 'a'; coords.file <= 'h'; coords.file++) {
            for (coords.rank = 1; coords.rank <= 8; coords.rank++) {
                // Draw Colors on Squares To Represent Status
                Board.Square.SelectionType type = Board.isSquareSelected(coords);
                switch (type) {
                    case SELECTED_PIECE:
                        g.setColor(Color.ORANGE);
                        break;
                    case LEGAL_MOVE:
                        g.setColor(Color.GREEN);
                        break;
                    case CAPTURE:
                        g.setColor(Color.RED);
                        break;
                }

                if (type != Board.Square.SelectionType.NONE) {
                    g.fillRect(SQUARE_SIZE  * (coords.file - 'a') + BORDER_SIZE + MARGIN_SIZE/2,
                                SQUARE_SIZE * (8 - coords.rank)   + BORDER_SIZE + MARGIN_SIZE/2,
                                SQUARE_SIZE - MARGIN_SIZE, SQUARE_SIZE - MARGIN_SIZE);
                }

                // Draw Pieces
                Piece piece;
                if ((piece = Board.getPieceAtSquare(coords)) != null) {
                    String imagePath = "resources/";
                    imagePath += (piece.getColor() == Piece.Color.WHITE)
                                        ? "white/"
                                        : "black/";
                    imagePath += piece.getImageFN();

                    try {
                        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
                        g.drawImage(bufferedImage,
                                    SQUARE_SIZE * (coords.file - 'a') + BORDER_SIZE,
                                    SQUARE_SIZE * (8 - coords.rank) + BORDER_SIZE, 60, 60, this);
                    } catch (IOException ex) {
                        System.out.println(ex.toString());
                    }
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        return;
    }

    public void mouseReleased(MouseEvent e) {
        return;
    }

    public void mouseEntered(MouseEvent e) {
        return;
    }

    public void mouseExited(MouseEvent e) {
        return;
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // Check if Click Inside Board
        if ((x > BORDER_SIZE && x < BOARD_SIZE) &&
            (y > BORDER_SIZE && y < BOARD_SIZE)) {
            Board.Coords coords = new Board.Coords('a', 1);
            // Get x & y coordinates of Square clicked
            x -= BORDER_SIZE;
            y -= BORDER_SIZE;

            x /= SQUARE_SIZE;
            y /= SQUARE_SIZE;

            coords.file = (char) ('a' + x);
            coords.rank = 8 - y;

            match.squareSelected(coords);
            super.repaint();
        }
    }
    
    public static void pushMove(String notation) {
        if (Match.getCurrentPlayer() == Piece.Color.WHITE) {
            notation =  match.getMoveCount() + ". " + notation;
        } else {
            notation = model.remove(model.size()-1) + "    " + notation;
        }
        model.addElement(notation);
    }
    
    public static String popMove() {
        return null; // TODO
    }
}
