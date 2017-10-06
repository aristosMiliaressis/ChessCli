package ChessCore;

import java.util.HashMap;

public abstract class Piece {
	public enum Color {
		WHITE {
		    @Override public String toString() {
		        return "White";
		    }
		},
		BLACK {
	        @Override public String toString() {
	            return "Black";
	        }
	    }
	};

	public enum Type {
		PAWN {
		    @Override public String toString() {
                return "P";
            }
        },
		ROCK {
            @Override public String toString() {
                return "R";
            }
        },
		KNIGHT {
            @Override public String toString() {
                return "N";
            }
        },
		BISHOP {
            @Override public String toString() {
                return "B";
            }
        },
		QUEEN {
            @Override public String toString() {
                return "Q";
            }
        },
		KING {
            @Override public String toString() {
                return "K";
            }
        }
	};

	private Color color;
	private Type type;
	private String image;
	private float value;
	private Board.Coords coords;

	Piece(Color color, Board.Coords coords) {
		setCoords(new Board.Coords(coords));
		this.color = color;
	}

	public abstract HashMap<Integer, Move> calculateLegalMoves();
	
	protected Move createMove() {
	    Move move = new Move();
	    Move.Descriptor descriptor = move.new Descriptor();
	    
	    descriptor.setSrcCoords(getCoords());
        descriptor.playingColor(getColor());
        
        move.setDescriptor(descriptor);
        return move;
	}
    
    public Color getColor() {
        return color;
    }
    
    protected void setType(Type type) {
    	this.type = type;
    }

    public Type getType() {
    	return type;
    }

    protected void setCoords(Board.Coords coords) {
    	this.coords = coords;
    }

    public Board.Coords getCoords() {
    	return coords;
    }
    
    protected void setValue(float value) {
    	this.value = value;
    }
    
    public float getValue() {
    	return value;
    }
    
    protected void setImageFN(String image) {
        this.image = image;
    }
    
    public String getImageFN() {
        return image;
    }
}
