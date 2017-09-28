package ChessCore;

import java.util.HashMap;

import ChessCli.Match;

public final class Move {
	public static class Rules {
		protected static enum SquareState {
			OPPOSING_PIECE,
			NO_PIECE,
			EITHER
		}

		Rules() {
			jumps = false;
			destState = null;
			en_passant = false;
			castling = false;
			srcRank = -1;
		}

		Rules(Rules rules) {
			this.jumps = rules.jumps;
			this.destState = rules.destState;
			this.en_passant = rules.en_passant;
			this.castling = rules.castling;
			this.srcRank = rules.srcRank;
		}

		public boolean check(Move move) {
			Descriptor descriptor = move.getDescriptor();
			System.out.println("Checking Move: " + descriptor.getDestCoords().Notation());
			
			if (en_passant) {
			    return checkEnPassant(descriptor);
			} else if (castling) {
			    return checkCastling(descriptor);
			} else if (!jumps) {
			   if (!checkPathForPieces(descriptor.getSrcCoords(), descriptor.getDestCoords())) {
			       return false;
			   }
			}
			
			if (!checkDestinationSquare(descriptor) || !checkSourceSquare(descriptor)) {
			    return false;
			}

			move.setDescriptor(descriptor);
			return !checkKingPinned(descriptor);
		}
		
		private boolean checkEnPassant(Descriptor descriptor) {
		    System.out.println("\t[*] Move is En Passant");
		    Board.Coords srcCoords = descriptor.getSrcCoords();
		    Board.Coords destCoords = descriptor.getDestCoords();
            
            Move prevMove = new Move();
            Descriptor prevMoveDesc = prevMove.new Descriptor(Match.getPrevMove());
            Board.Coords prevDestCoords = prevMoveDesc.getDestCoords();
            Board.Coords prevSrcCoords = prevMoveDesc.getSrcCoords();
            
            if (prevDestCoords.rank == srcCoords.rank && prevDestCoords.file == destCoords.file && prevSrcCoords.rank != destCoords.rank) {
                Piece piece = Board.getPieceAtSquare(prevDestCoords);
                if (piece != null && piece.getColor() != descriptor.playingColor() && piece.getType() == Piece.Type.PAWN) {
                    System.out.println("\t[+] Passed En Passant initial test");
                    return !checkKingPinned(descriptor);
                }
            }

            return false;
        }
		
		private boolean checkCastling(Descriptor descriptor) {
		    System.out.println("\t[*] Move is Castling");
		    Board.Coords srcCoords = descriptor.getSrcCoords();
            Board.Coords destCoords = descriptor.getDestCoords();
            Board.Coords rockCoords = null;
            
            if (destCoords.rank == 1 && destCoords.file == 'c') {
                rockCoords = new Board.Coords('a', 1); // white queen-side
            } else if (destCoords.rank == 1 && destCoords.file == 'g') {
                rockCoords = new Board.Coords('h', 1); // white king-side
            } else if (destCoords.rank == 8 && destCoords.file == 'c') {
                rockCoords = new Board.Coords('a', 8); // black queen-side
            } else if (destCoords.rank == 8 && destCoords.file == 'g') {
                rockCoords = new Board.Coords('h', 8); // black king-side
            } else {
                return false;
            }
            
            if (!checkIfPathPinned(descriptor) || !checkPathForPieces(srcCoords, rockCoords)) {
                return false;
            }
            
            System.out.println("\t[+] Passed Castling initial test");
            return true;
        }
		
		private boolean checkPathForPieces(Board.Coords src, Board.Coords dest) {
            System.out.println("\t[*] Move is NOT Knights");
            do {
                if (src.rank > dest.rank) {
                    src.rank--;
                } else if (src.rank < dest.rank) {
                    src.rank++;
                }

                if (src.file > dest.file)  {
                    src.file--;
                } else if (src.file < dest.file) {
                    src.file++;
                }

                if (dest.equals(src)) {
                    break;
                }

                if (Board.getPieceAtSquare(src) != null) {
                    return false;
                }
            }
            while (!dest.equals(src));
            
            System.out.println("\t[+] No Pieces in Path");
            return true;
        }
		
		private boolean checkIfPathPinned(Descriptor descriptor) {
		    Board.Coords src = descriptor.getSrcCoords();
            Board.Coords dest = descriptor.getDestCoords();
            
		    do {
		        if (src.isPinned(descriptor.playingColor())) {
                    return false;
                }
		        
                if (src.rank > dest.rank) {
                    src.rank--;
                } else if (src.rank < dest.rank) {
                    src.rank++;
                }

                if (src.file > dest.file) {
                    src.file--;
                } else if (src.file < dest.file) {
                    src.file++;
                }
            }
            while (!dest.equals(src));
		    
            return !src.isPinned(descriptor.playingColor());
		}
		
		private boolean checkDestinationSquare(Descriptor descriptor) {
		    if (descriptor.promotes()) {
		        Board.Coords destCoords  = descriptor.getDestCoords();
		        Piece.Color playingColor = descriptor.playingColor();
		        if (playingColor == Piece.Color.WHITE && destCoords.rank != 8 ||
		            playingColor == Piece.Color.BLACK && destCoords.rank != 1) {
		            descriptor.promotes(false);
		        }
		    }
		    
		    Piece destPiece = Board.getPieceAtSquare(descriptor.getDestCoords());
            if (destPiece == null) {
                System.out.println("\t [*] No Piece at Dest");
                if (destState == SquareState.OPPOSING_PIECE) {
                    return false;
                }

                descriptor.captures(null);
            } else {
                System.out.println("\t [*] Piece Found at Dest");
                if (destPiece.getColor() == Match.getCurrentPlayer()) {
                    System.out.println("\t [-] Ally Piece at Dest");
                    return false;
                }

                System.out.println("\t [*] Opposing Piece at Dest");
                if (destState == SquareState.NO_PIECE) {
                    return false;
                }

                switch (destPiece.getType()) {
                case PAWN:
                    descriptor.captures(Piece.Type.PAWN);
                    break;
                case ROCK:
                    descriptor.captures(Piece.Type.ROCK);
                    break;
                case KNIGHT:
                    descriptor.captures(Piece.Type.KNIGHT);
                    break;
                case BISHOP:
                    descriptor.captures(Piece.Type.BISHOP);
                    break;
                case QUEEN:
                    descriptor.captures(Piece.Type.QUEEN);
                    break;
                case KING:
                    descriptor.captures(Piece.Type.KING);
                    break;
                }
            }
            
            System.out.println("\t [+] Dest Square Test Passed");
            return true;
		}
		
		private boolean checkSourceSquare(Descriptor descriptor) {
		    if (srcRank != -1) {
		        System.out.println("\t [*] Source Rank Rule");
                if (srcRank != descriptor.getSrcCoords().rank) {
                    return false;
                }
                System.out.println("\t [+] Passed Source Rank Rule");
            }
		    
		    return true;
		}
		
		private boolean checkKingPinned(Descriptor descriptor) {
            Board.makeMove(descriptor.playingColor(), descriptor);
            
            Board.Coords kingCoords = Board.findKing(descriptor.playingColor());
            boolean isKingPinned = kingCoords.isPinned(descriptor.playingColor());
            
            Board.unmakeMove(descriptor);
            
            System.out.println("\t[+] Is King Pinned: " + isKingPinned);
            return isKingPinned;
		}

		public boolean jumps;
		public SquareState destState;
		public boolean en_passant;
		public boolean castling;
		public int srcRank;
	};

	public class Descriptor {
		private static final int EN_PASSANT  =         0b01;
		private static final int CASTLING    =         0b10;
		private static final int PROMOTION   =         0b11;
		private static final int CAP_NO      =      0b00000;
		private static final int CAP_PAWN    =      0b00100;
		private static final int CAP_ROCK    =      0b01000;
		private static final int CAP_BISHOP  =      0b01100;
		private static final int CAP_KNIGHT  =      0b10000;
		private static final int CAP_QUEEN   =      0b10100;
		private static final int CAP_KING    =      0b11100;
		private static final int PROM_QUEEN  =    0b0000000;
		private static final int PROM_ROCK   =    0b0100000;
		private static final int PROM_KNIGTH =    0b1000000;
		private static final int PROM_BISHOP =    0b1100000;
		private static final int EXP_RANK    =  0b010000000;
		private static final int EXP_FILE    =  0b100000000;
		private static final int EXP_DIAG    =  0b110000000;
		private static final int P_WHITE     = 0b0000000000;
		private static final int P_BLACK     = 0b1000000000;

		private int value;

		protected Descriptor() {}

		public Descriptor(Descriptor descriptor) {
            value = descriptor.getValue();
        }
		
		public Descriptor(int value) {
		    this.value = value;
        }
		
		public void printMove() {
		    System.out.println("----- [ Printing Move ] -----");
		    System.out.println("Src Square: " + getSrcCoords().Notation() + ", Dest Square: " + getDestCoords().Notation());
		    if (captures())
		        System.out.println("Captures: " + captureType().toString());
		    System.out.println("Castling: " + castling());
		    System.out.println("En Passant: " + en_passant());
		    System.out.println("Promotes: " + promotionType());
		    System.out.println("Playing Color: " + playingColor().toString());
		    System.out.println("Value: " + Integer.toBinaryString(value));
		}

		public int getValue() {
			return value;
		}

		public int setSrcCoords(Board.Coords srcCoords) {
		    value &= 0b1111110000001111111111;
			if (srcCoords.rank == 64 && srcCoords.file == 'A') {
			    value |= EXP_DIAG;
			} else if (srcCoords.rank == 64) {
				int src = srcCoords.file - 'a';
				value |= (src << 13);
				value |= EXP_RANK;
			} else if (srcCoords.file == 'A') {
				int src = (srcCoords.rank-1);
				value |= (src << 10);
				value |= EXP_FILE;
			} else {
				int src = (srcCoords.rank-1) + ((srcCoords.file - 'a') << 3);
				value |= (src << 10);
			}

			return value;
		}

		public Board.Coords getSrcCoords() {
			int sRank = (value & 0b1110000000000) >> 10;
			int sFile = (value & 0b1110000000000000) >> 13;

			return new Board.Coords((char) ('a' + sFile), sRank+1);
		}

		public int setDestCoords(Board.Coords destCoords) {
		    value &= 0b0000001111111111111111;
			if (destCoords.rank == 64 && destCoords.file == 'A') {
			    value |= EXP_DIAG;
			} else if (destCoords.rank == 64) {
				int dest = destCoords.file - 'a';
				value |= (dest << 19);
				value |= EXP_RANK;
			} else if (destCoords.file == 'A') {
				int dest = (destCoords.rank-1);
				value |= (dest << 16);
				value |= EXP_FILE;
			} else {
				int dest = (destCoords.rank-1) + ((destCoords.file - 'a') << 3);
				value |= (dest << 16);
			}

			return value;
		}

		public Board.Coords getDestCoords() {
			int dRank = (value &    0b1110000000000000000) >> 16;
			int dFile = (value & 0b1110000000000000000000) >> 19;

			return new Board.Coords((char) ('a' + dFile), dRank+1);
		}

		public boolean captures() {
			return !((value & 0b11100) == 0);
		}

		public void captures(Piece.Type pieceType) {
			if (pieceType == null) {
			    value |= CAP_NO;
				return;
			}

			switch (pieceType) {
			case PAWN:
			    value |= CAP_PAWN;
				break;
			case ROCK:
			    value |= CAP_ROCK;
				break;
			case BISHOP:
			    value |= CAP_BISHOP;
				break;
			case KNIGHT:
			    value |= CAP_KNIGHT;
				break;
			case QUEEN:
			    value |= CAP_QUEEN;
				break;
			case KING:
			    value |= CAP_KING;
				break;
			}
		}

		public Piece.Type captureType() {
			return ((value & 0b11100) ^ CAP_PAWN) == 0
					? Piece.Type.PAWN
					: ((value & 0b11100) ^ CAP_ROCK) == 0
					? Piece.Type.ROCK
					: ((value & 0b11100) ^ CAP_BISHOP) == 0
					? Piece.Type.BISHOP
					: ((value & 0b11100) ^ CAP_KNIGHT) == 0
					? Piece.Type.KNIGHT
					: ((value & 0b11100) ^ CAP_QUEEN) == 0
					? Piece.Type.QUEEN
					: ((value & 0b11100) ^ CAP_KING) == 0
					? Piece.Type.KING : null;
		}

		public boolean castling() {
			return ((value & 0b11) ^ CASTLING) == 0;
		}

		public void castling(boolean castle) {
			if (castle) {
                value |= CASTLING;
            } else {
                value -= CASTLING;
            }
		}

		public boolean en_passant() {
			return ((value & 0b11) ^ EN_PASSANT) == 0;
		}

		public void en_passant(boolean en_passant) {
			if (en_passant) {
                value |= EN_PASSANT;
            } else {
                value -= EN_PASSANT;
            }
		}

		public boolean promotes() {
			return ((value & 0b11) ^ PROMOTION) == 0;
		}

		public void promotes(boolean promotes) {
			if (promotes) {
			    value |= PROMOTION;
			} else {
			    value -= PROMOTION;
			}
		}
		
		public Piece.Type promotionType() {
			return ((value & 0b1100000) ^ PROM_QUEEN) == 0
					? Piece.Type.QUEEN
					: ((value & 0b1100000) ^ PROM_ROCK) == 0
					? Piece.Type.ROCK
					: ((value & 0b1100000) ^ PROM_KNIGTH) == 0
					? Piece.Type.KNIGHT
					: ((value & 0b1100000) ^ PROM_BISHOP) == 0
					? Piece.Type.BISHOP : null;
		}
		
		public void promotionType(Piece.Type promoType) {
			switch (promoType) {
			case QUEEN:
			    value |= PROM_QUEEN;
				break;
			case KNIGHT:
			    value |= PROM_KNIGTH;
				break;
			case ROCK:
			    value |= PROM_ROCK;
				break;
			case BISHOP:
			    value |= PROM_BISHOP;
				break;
			default: 
				return;
			}
		}

		public boolean expandsDiagonaly() {
			return ((value & 0b110000000) ^ EXP_DIAG) == 0;
		}

		public boolean expandsFile() {
			return ((value & 0b110000000) ^ EXP_FILE) == 0;
		}

		public boolean expandsRank() {
			return ((value & 0b110000000) ^ EXP_RANK) == 0;
		}

		public Piece.Color playingColor() {
			return ((value & 0b1000000000) ^ P_WHITE) == 0
					? Piece.Color.WHITE : Piece.Color.BLACK;
		}

		public void playingColor(Piece.Color color) {
		    value |= (color == Piece.Color.WHITE) ? P_WHITE : P_BLACK;
		}
	}

	private Rules rules = null;
	private String notation = "";
	private Descriptor descriptor = new Descriptor();

	public Move() {}

	public Move(Descriptor descriptor) {
		descriptor = new Descriptor(descriptor);
		notation = generateNotation(descriptor);
	}

	public Move(String notation) {
		this.notation = notation;
		//descriptor = new Move.Descriptor(notation);
	}
	
	protected HashMap<Integer, Move> expandUndefinedMoves() {
        HashMap<Integer, Move> expandedMoves = new HashMap<Integer, Move>();
        Move tmpMove = new Move();
        
        Move.Descriptor tmpDescriptor = tmpMove.new Descriptor();
        Board.Coords srcCoord  = descriptor.getSrcCoords ();
        Board.Coords destCoord = descriptor.getDestCoords();

        // Move an Undefined Number of Squares Diagonaly to Source Square
        if (descriptor.expandsDiagonaly()) {
            destCoord.set(srcCoord);
            for (destCoord.file = (char) (srcCoord.file + 1),
                 destCoord.rank = srcCoord.rank + 1;
                 destCoord.file <= 'h' && destCoord.rank <= 8;
                 destCoord.file++, destCoord.rank++) {
                tmpMove = new Move();
                tmpMove.setRules(rules);
                tmpDescriptor = new Descriptor(descriptor);
                tmpDescriptor.setDestCoords(destCoord);
                tmpMove.setDescriptor(tmpDescriptor);
                expandedMoves.put(destCoord.keyable(), tmpMove);
            }

            destCoord.set(srcCoord);
            for (destCoord.file = (char) (srcCoord.file + 1),
                 destCoord.rank = srcCoord.rank - 1;
                 destCoord.file <= 'h' && destCoord.rank >= 1;
                 destCoord.file++, destCoord.rank--) {
                tmpMove = new Move();
                tmpMove.setRules(rules);
                tmpDescriptor = new Descriptor(descriptor);
                tmpDescriptor.setDestCoords(destCoord);
                tmpMove.setDescriptor(tmpDescriptor);
                expandedMoves.put(destCoord.keyable(), tmpMove);
            }

            destCoord.set(srcCoord);
            for (destCoord.file = (char) (srcCoord.file - 1),
                 destCoord.rank = srcCoord.rank + 1;
                 destCoord.file >= 'a' && destCoord.rank <= 8;
                 destCoord.file--, destCoord.rank++) {
                tmpMove = new Move();
                tmpMove.setRules(rules);
                tmpDescriptor = new Descriptor(descriptor);
                tmpDescriptor.setDestCoords(destCoord);
                tmpMove.setDescriptor(tmpDescriptor);
                expandedMoves.put(destCoord.keyable(), tmpMove);
            }

            destCoord.set(srcCoord);
            for (destCoord.file = (char) (srcCoord.file - 1),
                 destCoord.rank = srcCoord.rank - 1;
                 destCoord.file >= 'a' && destCoord.rank >= 1;
                 destCoord.file--, destCoord.rank--) {
                tmpMove = new Move();
                tmpMove.setRules(rules);
                tmpDescriptor = new Descriptor(descriptor);
                tmpDescriptor.setDestCoords(destCoord);
                tmpMove.setDescriptor(tmpDescriptor);
                expandedMoves.put(destCoord.keyable(), tmpMove);
            }
        } else if (descriptor.expandsRank()) {
            // Move an Undefined Number of Squares on Source File
            for (destCoord.rank = 1; destCoord.rank <= 8; destCoord.rank++) {
                tmpMove = new Move();
                if (destCoord.equals(srcCoord)) {
                    continue;
                }

                tmpMove.setRules(rules);
                tmpDescriptor = new Descriptor(descriptor);
                tmpDescriptor.setDestCoords(destCoord);
                tmpMove.setDescriptor(tmpDescriptor);
                expandedMoves.put(destCoord.keyable(), tmpMove);
            }
        } else if (descriptor.expandsFile()) {
            // Move an Undefined Number of Squares on Source Rank
            for (destCoord.file = 'a'; destCoord.file <= 'h'; destCoord.file++) {
                tmpMove = new Move();
                if (destCoord.equals(srcCoord)) {
                    continue;
                }

                tmpMove.setRules(rules);
                tmpDescriptor = new Descriptor(descriptor);
                tmpDescriptor.setDestCoords(destCoord);
                tmpMove.setDescriptor(tmpDescriptor);
                expandedMoves.put(destCoord.keyable(), tmpMove);
            }
        }

        return expandedMoves;
    }

	protected void setRules(Rules rules) {
		this.rules = new Rules(rules);
	}

	public Rules getRules() {
		return rules;
	}

	protected void setNotation(String notation) {
		//descriptor = generateDescriptor(notation);
		this.notation = notation;
	}

	public String getNotation() {
		return notation;
	}

	public void setDescriptor(Descriptor descriptor) {
		notation = generateNotation(descriptor);
		this.descriptor = new Descriptor(descriptor);
	}

	public Descriptor getDescriptor() {
		return descriptor;
	}

	@SuppressWarnings("incomplete-switch")
	private static String generateNotation(Descriptor descriptor) { // FIXME: fix & test
		String notation = "";

		Board.Coords srcCoords  = descriptor.getSrcCoords ();
		Board.Coords destCoords = descriptor.getDestCoords();

		if (descriptor.en_passant()) {
			notation = srcCoords.file + "x" + Board.Coords.Notation(destCoords) + "e.p.";
		} else if (descriptor.castling()) {
			if (destCoords.file == 'G') {
				notation = "O-O";
			} else {
				notation = "O-O-O";
			}
		} else if (descriptor.promotes()) {
			if (descriptor.captures()) {
				notation = srcCoords.file + "x";
			}
			notation += Board.Coords.Notation(destCoords) + "=";

			Piece.Type promoType = descriptor.promotionType();
			switch (promoType) {
			case QUEEN:
				notation += "Q";
				break;
			case KNIGHT:
				notation += "N";
				break;
			case BISHOP:
				notation += "B";
				break;
			case ROCK:
				notation += "R";
				break;
			}
		} else {
			Piece.Type pieceType = Board.getPieceAtSquare(srcCoords).getType();
			if (pieceType != null) {
				switch (pieceType) {
				case PAWN:
					if (descriptor.captures()) {
						notation = "" + srcCoords.file;
					}
					break;
				case BISHOP:
					notation = "B";
					break;
				case KNIGHT:
					notation = "N";
					break;
				case ROCK:
					notation = "R";
					break;
				case QUEEN:
					notation = "Q";
					break;
				case KING:
					notation = "K";
					break;
				}
			}

			if (descriptor.captures()) {
				notation += "x";
			}
			notation += Board.Coords.Notation(destCoords);
		}

		System.out.println("Move Notation: " + notation);
		return notation;
	}
}

