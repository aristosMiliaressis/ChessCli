package ChessCore;

import java.util.HashMap;


public class Pawn extends Piece {
	public Pawn(Color color, Board.Coords coords) {
		super(color, coords);

		setType(Type.PAWN);
		setImageFN("pawn.png");
		setValue(1);
	}

	public HashMap<Integer, Move> calculateLegalMoves() {
	    HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();
        Board.Coords coords = this.getCoords();
        Color color = this.getColor();
        
        Move.Rules rules;
        Move.Descriptor descriptor;
        Board.Coords destCoords;
        int srcRank;
        
		Move advance = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.NO_PIECE;
		advance.setRules(rules);

		descriptor = advance.new Descriptor();
		destCoords = new Board.Coords(coords);
		destCoords.rank += (color == Color.WHITE) ? 1 : -1;
		descriptor.setDestCoords(destCoords);
		descriptor.setSrcCoords(coords);
		descriptor.playingColor(color);
		if (destCoords.rank == ((color == Color.WHITE) ? 8 : 1)) {
		    descriptor.promotes(true);
		}
		advance.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), advance);
		}
		
		Move capture_left = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.OPPOSING_PIECE;
		capture_left.setRules(rules);

		descriptor = capture_left.new Descriptor();
		destCoords = new Board.Coords(coords);
		destCoords.file -= 1;
		destCoords.rank += (color == Color.WHITE) ? 1 : -1;
		descriptor.setDestCoords(destCoords);
		descriptor.setSrcCoords(coords);
		descriptor.playingColor(color);
		if (destCoords.rank == ((color == Color.WHITE) ? 8 : 1)) {
            descriptor.promotes(true);
        }
		capture_left.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), capture_left);
		}

		Move capture_rigth = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.OPPOSING_PIECE;
		capture_rigth.setRules(rules);

		descriptor = capture_rigth.new Descriptor();
		destCoords = new Board.Coords(coords);
		destCoords.file += 1;
		destCoords.rank += (color == Color.WHITE) ? 1 : -1;
		descriptor.setDestCoords(destCoords);
		descriptor.setSrcCoords(coords);
		descriptor.playingColor(color);
		if (destCoords.rank == ((color == Color.WHITE) ? 8 : 1)) {
            descriptor.promotes(true);
        }
		capture_rigth.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), capture_rigth);
		}
		
		Move double_advance = new Move();
        rules = new Move.Rules();
        rules.destCondition = Move.Rules.SquareState.NO_PIECE;
        srcRank = (color == Color.WHITE) ? 2 : 7;
        double_advance.setRules(rules);

        descriptor = double_advance.new Descriptor();
        destCoords = new Board.Coords(coords);
        destCoords.rank += (color == Color.WHITE) ? 2 : -2;
        descriptor.setDestCoords(destCoords);
        descriptor.setSrcCoords(coords);
        descriptor.playingColor(color);
        double_advance.setDescriptor(descriptor);
        if (coords.rank == srcRank) {
            legalMoves.put(destCoords.keyable(), double_advance);
        }

		Move en_passant_left = new Move();
		rules = new Move.Rules();
		rules.en_passant = true;
		rules.destCondition = Move.Rules.SquareState.NO_PIECE;
		srcRank = (color == Color.WHITE) ? 5 : 4;
		en_passant_left.setRules(rules);

		descriptor = en_passant_left.new Descriptor();
		destCoords = new Board.Coords(coords);
		destCoords.file -= 1;
		destCoords.rank += (color == Color.WHITE) ? 1 : -1;
		descriptor.setDestCoords(destCoords);
		descriptor.setSrcCoords(coords);
		descriptor.playingColor(color);
		descriptor.en_passant(true);
		descriptor.captures(Type.PAWN);
		en_passant_left.setDescriptor(descriptor);
		if (destCoords.valid() && coords.rank == srcRank) {
			legalMoves.put(destCoords.keyable(), en_passant_left);
		}

		Move en_passant_right = new Move();
		rules = new Move.Rules();
		rules.en_passant = true;
		rules.destCondition = Move.Rules.SquareState.NO_PIECE;
		srcRank = (color == Color.WHITE) ? 5 : 4;
		en_passant_right.setRules(rules);

		descriptor = en_passant_right.new Descriptor();
		destCoords = new Board.Coords(coords);
		destCoords.file += 1;
		destCoords.rank += (color == Color.WHITE) ? 1 : -1;
		descriptor.setDestCoords(destCoords);
		descriptor.setSrcCoords(coords);
		descriptor.playingColor(color);
		descriptor.en_passant(true);
		descriptor.captures(Type.PAWN);
		en_passant_right.setDescriptor(descriptor);
		if (destCoords.valid() && coords.rank == srcRank) {
			legalMoves.put(destCoords.keyable(), en_passant_right);
		}

		return legalMoves;
	}
}
