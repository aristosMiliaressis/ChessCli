package ChessCore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import ChessCore.Move.Descriptor;

public final class Board
{
	public static class Coords
	{
	    public char file;
        public int  rank;
        
		public Coords(char f, int r) {
			set(f, r);
		}
		
		public Coords(Coords coord) {
			this(coord.file, coord.rank);
		}

		public Coords(String notation) {
			this(notation.charAt(0), (int) notation.charAt(1));
		}

		public Coords(int keyable) {
			this((char) ('a' + (keyable & 0b111)), keyable >> 3);
		}
		
		protected Coords set(char f, int r) {
			file = f;
			rank = r;
			return this;
		}

		protected Coords set(Coords coords) {
			return set(coords.file, coords.rank);
		}

		protected boolean valid() {
            return !(file > 'h' || file < 'a' || rank > 8 || rank < 1);
        }
		
		public boolean equals(Coords coord) {
			return equals(coord.file, coord.rank);
		}
		
		public boolean equals(char f, int r) {
			return (file == f && rank == r);
		}

		public int keyable() {
            return (file - 'a') + (rank << 3);
        }
		
		public String Notation() {
            return Notation(file, rank);
        }
		
		public static String Notation(char file, int rank) {
			return new String("" + file + rank);
		}

		public static String Notation(Coords coord) {
			return Coords.Notation(coord.file, coord.rank);
		}
	}

	public static class Square {
		public static enum SelectionType {
			SELECTED_PIECE,
			LEGAL_MOVE,
			CAPTURE,
			NONE
		};

		private Piece piece = null;
		private SelectionType selectType = SelectionType.NONE;
		private Coords coords = null;

		Square(Coords coords) {
			this.coords = new Coords(coords);
		}

		private SelectionType getSelectType() {
			return selectType;
		}

		public void select(SelectionType type) {
			selectType = type;
		}

		public void deselect() {
			selectType = SelectionType.NONE;
		}

		private Piece getPiece() {
			return piece;
		}

		private void setPiece(Piece newPiece) {
			piece = newPiece;
			if (piece != null) {
				piece.setCoords(coords);
			}
		}

		private void promote(Piece.Type type) {
			Piece.Color color = piece.getColor();
	    	if (piece != null) {
	    		switch(type) {
	    		case KNIGHT:
	    			piece = new Knight(color, piece.getCoords());
	    			break;
	    		case BISHOP:
	    			piece = new Bishop(color, piece.getCoords());
	    			break;
	    		case ROCK:
	    			piece = new Rock(color, piece.getCoords());
	    			break;
	    		case QUEEN:
	    			piece = new Queen(color, piece.getCoords());
	    			break;
	    		default: return;
	    		}
	    	}
	    }
		
		public boolean isPinned(Piece.Color color) {
            Coords coord = new Coords('a', 1);
            for (coord.rank = 1; coord.rank <= 8; coord.rank++) {
                for (coord.file = 'a'; coord.file <= 'h'; coord.file++) {
                    Piece piece = getPieceAtSquare(coord);
                    if (piece != null && piece.getColor() != color) {
                        HashMap<Integer, Move> moves = piece.calculateLegalMoves();
                        Iterator<Entry<Integer, Move>> it = moves.entrySet().iterator();
                        while (it.hasNext()) {
                            Entry<Integer, Move> entry = (Entry<Integer, Move>) it.next();
                            Coords destCoord = new Coords(entry.getKey());
                            Move move = entry.getValue();
                            Move.Rules rules = move.getRules();
                            
                            if (rules.check(move)) {
                                if (this.coords.equals(destCoord)) {
                                    return true;
                                }
                                System.out.println("\t-Move: " + move.getNotation() + " NOT Pinning King");
                            }
                        }
                    }
                }
            }
            
            return false;
        }
	}

	private static HashMap<Integer, Square> board = new HashMap<Integer, Square>();
	private static String epdNotation = "";

	public static void initBoard(String epdNotation) {
		Board.Coords coords = new Board.Coords('a', 1);
		for (coords.file = 'a'; coords.file <= 'h'; coords.file++) {
			for (coords.rank = 1; coords.rank <= 8; coords.rank++) {
				board.put(coords.keyable(), new Square(coords));
			}
		}

		Board.epdNotation = epdNotation;
		loadEPD(epdNotation);

		//** TODO: Replace Code (use an EPD parser to setup board) **//
		getSquare(coords.set('a', 8)).setPiece(new Rock(Piece.Color.BLACK, coords.set('a', 8)));
		getSquare(coords.set('b', 8)).setPiece(new Knight(Piece.Color.BLACK, coords.set('b', 8)));
		getSquare(coords.set('c', 8)).setPiece(new Bishop(Piece.Color.BLACK, coords.set('c', 8)));
		getSquare(coords.set('d', 8)).setPiece(new Queen(Piece.Color.BLACK, coords.set('d', 8)));
		getSquare(coords.set('e', 8)).setPiece(new King(Piece.Color.BLACK, coords.set('e', 8)));
		getSquare(coords.set('f', 8)).setPiece(new Bishop(Piece.Color.BLACK, coords.set('f', 8)));
		getSquare(coords.set('g', 8)).setPiece(new Knight(Piece.Color.BLACK, coords.set('g', 8)));
		getSquare(coords.set('h', 8)).setPiece(new Rock(Piece.Color.BLACK, coords.set('h', 8)));
		for (coords.rank = 7, coords.file = 'a'; coords.file <= 'h'; coords.file++) {
            getSquare(coords).setPiece(new Pawn(Piece.Color.BLACK, coords));
		}

		getSquare(coords.set('a', 1)).setPiece(new Rock(Piece.Color.WHITE, coords.set('a', 1)));
		getSquare(coords.set('b', 1)).setPiece(new Knight(Piece.Color.WHITE, coords.set('b', 1)));
		getSquare(coords.set('c', 1)).setPiece(new Bishop(Piece.Color.WHITE, coords.set('c', 1)));
		getSquare(coords.set('d', 1)).setPiece(new Queen(Piece.Color.WHITE, coords.set('d', 1)));
		getSquare(coords.set('e', 1)).setPiece(new King(Piece.Color.WHITE, coords.set('e', 1)));
		getSquare(coords.set('f', 1)).setPiece(new Bishop(Piece.Color.WHITE, coords.set('f', 1)));
		getSquare(coords.set('g', 1)).setPiece(new Knight(Piece.Color.WHITE, coords.set('g', 1)));
		getSquare(coords.set('h', 1)).setPiece(new Rock(Piece.Color.WHITE, coords.set('h', 1)));
		for (coords.rank = 2, coords.file = 'a'; coords.file <= 'h'; coords.file++) {
            getSquare(coords).setPiece(new Pawn(Piece.Color.WHITE, coords));
		}
		//** Replace Code **//
	}

	public static Square getSquare(Board.Coords coords) {
		return board.get(coords.keyable());
	}
	
	public static Piece getPieceAtSquare(Board.Coords coords) {
		return getSquare(coords).getPiece();
	}

	public static Square.SelectionType isSquareSelected(Board.Coords coords) {
		return getSquare(coords).getSelectType();
	}

	public static void selectSquare(Board.Coords coords, Square.SelectionType type) {
		getSquare(coords).select(type);
	}

	public static void deselectAll() {
		for (Square square: board.values()) {
		    square.deselect();
		}
	}

	public static void promotePiece(Move.Descriptor descriptor) {
		getSquare(descriptor.getSrcCoords()).promote(descriptor.promotionType());
	}

	public static void makeMove(Piece.Color playingColor, Move.Descriptor descriptor) {
		Coords srcCoords = new Coords(descriptor.getSrcCoords());
		Coords destCoords = new Coords(descriptor.getDestCoords());

		movePiece(srcCoords, destCoords);
		if (descriptor.en_passant()) {
			destCoords.rank += (descriptor.playingColor() == Piece.Color.WHITE) ? -1 : 1;
			getSquare(destCoords).setPiece(null);
		} else if (descriptor.castling()) {
			Coords rockSrc = null, rockDest = null;
			if (destCoords.rank == 1 && destCoords.file == 'c') {
			    rockSrc  = new Coords('a', 1);
				rockDest = new Coords('d', 1);
			} else if (destCoords.rank == 1 && destCoords.file == 'g') {
			    rockSrc  = new Coords('h', 1);
				rockDest = new Coords('f', 1);
			} else if (destCoords.rank == 8 && destCoords.file == 'c') {
			    rockSrc  = new Coords('a', 8);
				rockDest = new Coords('d', 8);
			} else if (destCoords.rank == 8 && destCoords.file == 'g') {
			    rockSrc  = new Coords('h', 8);
				rockDest = new Coords('f', 8);
			}
			movePiece(rockSrc, rockDest);
		}
	}

	public static void unmakeMove(Move.Descriptor descriptor) {
		Coords srcCoords = descriptor.getSrcCoords();
		Coords destCoords = descriptor.getDestCoords();

		movePiece(destCoords, srcCoords);
		
		if (descriptor.castling()) {
			Coords rockDest = null, rockSrc = null;
			if (destCoords.rank == 1 && destCoords.file == 'c') {
			    rockSrc = new Coords('d', 1);
				rockDest = new Coords('a', 1);
			} else if (destCoords.rank == 1 && destCoords.file == 'g') {
			    rockSrc = new Coords('f', 1);
				rockDest = new Coords('h', 1);
			} else if (destCoords.rank == 8 && destCoords.file == 'c') {
			    rockSrc = new Coords('d', 8);
				rockDest = new Coords('a', 8);
			} else if (destCoords.rank == 8 && destCoords.file == 'g') {
			    rockSrc = new Coords('f', 8);
				rockDest = new Coords('h', 8);
			}
			
			movePiece(rockSrc, rockDest);
		} else if (descriptor.en_passant()) {
			if (descriptor.playingColor() == Piece.Color.WHITE) {
				destCoords.rank--;
				getSquare(destCoords).setPiece(new Pawn(Piece.Color.BLACK, destCoords));
			}
			else {
				destCoords.rank++;
				getSquare(destCoords).setPiece(new Pawn(Piece.Color.WHITE, destCoords));
			}
			return;
		} else if (descriptor.captures()) {
			Square destSquare = getSquare(destCoords);
			Piece.Type destPieceType = descriptor.captureType();
			Piece.Color destPieceColor = (descriptor.playingColor() == Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
			
			switch (destPieceType) {
			case PAWN:
				destSquare.setPiece(new Pawn(destPieceColor, destCoords));
				break;
			case ROCK:
				destSquare.setPiece(new Rock(destPieceColor, destCoords));
				break;
			case BISHOP:
				destSquare.setPiece(new Bishop(destPieceColor, destCoords));
				break;
			case KNIGHT:
				destSquare.setPiece(new Knight(destPieceColor, destCoords));
				break;
			case QUEEN:
				destSquare.setPiece(new Queen(destPieceColor, destCoords));
				break;
			case KING:
				destSquare.setPiece(new King(destPieceColor, destCoords));
				break;
			}
		}
		
		if (descriptor.promotes()) {
		    System.out.println("Move Promotes");
			getSquare(srcCoords).setPiece(new Pawn(descriptor.playingColor(), srcCoords));
		}
	}
	
	private static void movePiece(Coords src, Coords dest) {
		Square srcSquare = getSquare(src);
		Square destSquare = getSquare(dest);
		
		destSquare.setPiece(srcSquare.getPiece());
		srcSquare.setPiece(null);
	}
	
	public static boolean isKingPinned(Descriptor descriptor) {
        Board.makeMove(descriptor.playingColor(), descriptor);
        
        Board.Square kingSquare = Board.findKing(descriptor.playingColor());
        boolean isKingPinned = kingSquare.isPinned(descriptor.playingColor());
        
        Board.unmakeMove(descriptor);
        
        return isKingPinned;
    }
	
	public static Square findKing(Piece.Color color) {
		Coords coord = new Coords('a', 1);
		
		for (; coord.file <= 'h'; coord.file++) {
			for (coord.rank = 1; coord.rank <= 8; coord.rank++) {
				Piece piece = getPieceAtSquare(coord);
				
				if (piece != null && piece.getType() == Piece.Type.KING && piece.getColor() == color) {
					return getSquare(coord);
				}
			}
		}
		
		return null;
	}

	private static void loadEPD(String notation)
	{
		// TODO: Implement
	}

	@SuppressWarnings("unused")
	private static String generateEPD()
	{
		// TODO: Implement

		return epdNotation;
	}
}
