package ChessCli;

import ChessCore.Board;
import ChessCore.Move;
import ChessCore.Piece;
import ChessCli.BoardView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.HashMap;

public class Match {
	public static enum Result {
		CHECKMATE,
		STALEMATE,
		NA
	};
	
	private static class CastlingRights {
	    CastlingRights()	{
			whiteKingSide = true;
			whiteQueenSide = true;
			blackKingSide = true;
			blackQueenSide = true;
		}
		
		private boolean whiteKingSide;
		private boolean whiteQueenSide;
		private boolean blackKingSide;
		private boolean blackQueenSide;
	};

	@SuppressWarnings("unused")
	public static class State {
		private String title;
		private String event;
		private int round;
		private String site;
		private String time;
		private String white;
		private String black;
		private Result result;
		private String turnTime;
		private String remainingTime;
		private static Piece.Color playingColor = Piece.Color.WHITE;
		private static ArrayList<Integer> playedMoves = new ArrayList<Integer>();
		private int moveCount;
		
		// used to look up if castling is allowed
        // based on whether the king or a rock
        // have moved before
        private CastlingRights castlingRights = new CastlingRights();
        
        private boolean checkCastlingRights(Move.Descriptor descriptor) {
            Board.Coords destCoords = descriptor.getDestCoords();
            
            if (destCoords.rank == 1 && destCoords.file == 'c') {
                return state.castlingRights.whiteQueenSide;
            } else if (destCoords.rank == 1 && destCoords.file == 'g') {
                return state.castlingRights.whiteKingSide;
            } else if (destCoords.rank == 8 && destCoords.file == 'c') {
                return state.castlingRights.blackQueenSide;
            } else if (destCoords.rank == 8 && destCoords.file == 'g') {
                return state.castlingRights.blackKingSide;
            }
            
            return false;
        }
	};

	private static State state = new State();
	private HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();
	
	
	Match() {
		state.moveCount = 0;
	}

	public static Piece.Color getCurrentPlayer() {
		return State.playingColor;
	}

	public static ArrayList<Integer> getPlayedMoves() {
		return State.playedMoves;
	}
	
	public static int getPrevMove() {
		return (state.moveCount > 0) ? State.playedMoves.get(state.moveCount -1) : -1;
	}
	
	protected int getMoveCount() {
	    return state.moveCount;
	}
	
	protected void squareSelected(Board.Coords coords) {
		if (BoardView.getSelectedPiece() == Board.getPieceAtSquare(coords)) {
			return;
		}

		if (BoardView.getSelectedPiece() != null) {
			if (legalMoves.containsKey(coords.keyable())) {
				moveSelected(coords);
		    } else {
				pieceSelected(coords);
		    }
		} else {
			pieceSelected(coords);
		}
	}

	private void pieceSelected(Board.Coords srcCoords) {
	    System.out.println("Match::pieceSelected(" + srcCoords.file + srcCoords.rank);
		Piece piece = Board.getPieceAtSquare(srcCoords);
		if (piece == null || piece.getColor() != State.playingColor) {
			return;
		}

		Board.deselectAll();
		BoardView.setSelectedPiece(null);
		
		// Select Square
		Board.selectSquare(srcCoords, Board.Square.SelectionType.SELECTED_PIECE);

		// Calculate Pieces Moves
		HashMap<Integer, Move> legalMoves = piece.calculateLegalMoves();

	    // Select Destination Squares in Legal Moves
	    Iterator<Entry<Integer, Move>> it = legalMoves.entrySet().iterator();
	    while (it.hasNext()) {
	    	Entry<Integer, Move> entry = (Entry<Integer, Move>) it.next();
	    	Board.Square destSquare = Board.getSquare(new Board.Coords(entry.getKey()));
	    	Move.Descriptor descriptor = entry.getValue().getDescriptor();
	    	descriptor.setSrcCoords(srcCoords);
	    	
	    	// Check for castling state (has king or rock moved?)
	    	if (descriptor.castling() && !state.checkCastlingRights(descriptor)) {
	    		System.out.println("WTF");
				legalMoves.remove(entry.getKey());
				continue;
			}
	    	
			if (destSquare != null) {
				if (descriptor.captures()) {
					destSquare.select(Board.Square.SelectionType.CAPTURE);
				} else {
					destSquare.select(Board.Square.SelectionType.LEGAL_MOVE);
				}
			} else {
				it.remove();
			}
	    }
	    
	    System.out.println("--------------------------");
	    System.out.println("Legal Moves: " + legalMoves.size() + "\n");
	    
	    this.legalMoves = legalMoves;
	    BoardView.setSelectedPiece(Board.getPieceAtSquare(srcCoords));
	}

	private void moveSelected(Board.Coords coords) {
		Move move = legalMoves.get(coords.keyable());
		if (move == null) {
			return;
		}

		Move.Descriptor descriptor = move.getDescriptor();
		System.out.println("Match::moveSelected(" + coords.file + coords.rank + ")");
		
		Piece piece = Board.getPieceAtSquare(descriptor.getSrcCoords());
		if (piece.getType() == Piece.Type.KING) {
			if (piece.getColor() == Piece.Color.WHITE) {
				state.castlingRights.whiteKingSide = false;
				state.castlingRights.whiteQueenSide = false;
			} else {
				state.castlingRights.blackKingSide = false;
				state.castlingRights.blackQueenSide = false;
			}
		} else if (piece.getType() == Piece.Type.ROCK) {
			Board.Coords pieceCoords = piece.getCoords();
			if (pieceCoords.equals('a', 1)) {
				state.castlingRights.whiteQueenSide = false;
			} else if (pieceCoords.equals('h', 1)) {
				state.castlingRights.whiteKingSide = false;
			} else if (pieceCoords.equals('a', 8)) {
				state.castlingRights.blackQueenSide = false;
			} else if (pieceCoords.equals('h', 8)) {
				state.castlingRights.blackKingSide = false;
			}
		} else if (descriptor.promotes()) {
		    descriptor.promotionType(Piece.Type.QUEEN); // TODO: prompt user to chose promotion type
		    Board.promotePiece(descriptor);
		}
		
		Board.makeMove(State.playingColor, descriptor);
		BoardView.pushMove(move.getNotation());
		
		State.playingColor = (State.playingColor == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;

		state.moveCount++;
		State.playedMoves.add(descriptor.getValue());
		BoardView.setSelectedPiece(null);
		Board.deselectAll();
		Result result = checkEndGame();
		if (result != Result.NA) {
			matchConcluded(result);
		}
	}

	@SuppressWarnings("unused")
	private static void undoMove() {
		int lastMove = State.playedMoves.get(State.playedMoves.size() - 1);
		Move move = new Move();
		Move.Descriptor descriptor = move.new Descriptor(lastMove);

		// restore castling rights
		Piece piece = Board.getPieceAtSquare(descriptor.getDestCoords());
		if (piece.getType() == Piece.Type.KING) {
		    // TODO: Update state.castlingRights to restore castling rights
		} else if (piece.getType() == Piece.Type.ROCK) {
		 // TODO: Update state.castlingRights to restore castling rights
		}
		
		Board.unmakeMove(descriptor);
		State.playingColor = (State.playingColor == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
		
		state.moveCount++;
		State.playedMoves.remove(State.playedMoves.size() - 1);
	}

	@SuppressWarnings("unused")
	private static boolean loadPGN(String filename) {
	    // TODO: Implement
		// delete board
		// create new board with epd notation
		// move all moves

		return false;
	}

	@SuppressWarnings("unused")
	private static boolean savePGN(String filename) {
	 // TODO: Implement
		return false;
	}
	
	private Result checkEndGame() {
	    Board.Coords coord = new Board.Coords('a', 1);
	    for (coord.rank = 1; coord.rank <= 8; coord.rank++) {
	        for (coord.file = 'a'; coord.file <= 'h'; coord.file++) {
	            Piece piece = Board.getPieceAtSquare(coord);
	            if (piece != null && piece.getColor() == State.playingColor) {
	                if (piece.calculateLegalMoves().size() > 0) {
	                    return Result.NA;
	                }
	            }
	        }
	    }
	    
	    Board.Coords kingCoords = Board.findKing(State.playingColor);
	    if (kingCoords.isPinned(State.playingColor)) {
	        return Result.CHECKMATE;
	    } else {
	        return Result.STALEMATE;
	    }
	    
	}

	private void matchConcluded(Result result) {
	    state.result = result;
		
	    /// TODO
	    // communicate with BoardViewPanel to display result
	    // and prompt user to save match than exit to menu
	    System.out.println("Match Result: " + State.playingColor.toString() + " " + result.toString());
	}
}
