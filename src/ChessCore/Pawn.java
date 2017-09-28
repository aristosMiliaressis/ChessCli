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
        System.out.println(getColor().toString() + this + " at " + getCoords().Notation() + " called calculateLegalMoves()");
		HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();

		Move advance = new Move();
		Move.Rules rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.NO_PIECE;
		advance.setRules(rules);

		Move.Descriptor descriptor = advance.new Descriptor();
		Board.Coords tmpCoords = new Board.Coords(getCoords());
		tmpCoords.rank += (getColor() == Color.WHITE) ? 1 : -1;
		descriptor.setDestCoords(tmpCoords);
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.promotes(true);
		advance.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(advance)) {
			legalMoves.put(tmpCoords.keyable(), advance);
		}

		Move double_advance = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.NO_PIECE;
		rules.srcRank = (getColor() == Color.WHITE) ? 2 : 7;
		double_advance.setRules(rules);

		descriptor = double_advance.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		tmpCoords.rank += (getColor() == Color.WHITE) ? 2 : -2;
		descriptor.setDestCoords(tmpCoords);
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		double_advance.setDescriptor(descriptor);
		if (rules.check(double_advance)) {
			legalMoves.put(tmpCoords.keyable(), double_advance);
		}
		
		
		Move capture_left = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.OPPOSING_PIECE;
		capture_left.setRules(rules);

		descriptor = capture_left.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		tmpCoords.file -= 1;
		tmpCoords.rank += (getColor() == Color.WHITE) ? 1 : -1;
		descriptor.setDestCoords(tmpCoords);
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.promotes(true);
		capture_left.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(capture_left)) {
			legalMoves.put(tmpCoords.keyable(), capture_left);
		}

		Move capture_rigth = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.OPPOSING_PIECE;
		capture_rigth.setRules(rules);

		descriptor = capture_rigth.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		tmpCoords.file += 1;
		tmpCoords.rank += (getColor() == Color.WHITE) ? 1 : -1;
		descriptor.setDestCoords(tmpCoords);
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.promotes(true);
		capture_rigth.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(capture_rigth)) {
			legalMoves.put(tmpCoords.keyable(), capture_rigth);
		}

		Move en_passant_left = new Move();
		rules = new Move.Rules();
		rules.en_passant = true;
		rules.destState = Move.Rules.SquareState.NO_PIECE;
		rules.srcRank = (getColor() == Color.WHITE) ? 5 : 4;
		en_passant_left.setRules(rules);

		descriptor = en_passant_left.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		tmpCoords.file -= 1;
		tmpCoords.rank += (getColor() == Color.WHITE) ? 1 : -1;
		descriptor.setDestCoords(tmpCoords);
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.en_passant(true);
		descriptor.captures(Type.PAWN);
		en_passant_left.setDescriptor(descriptor);
		if (rules.check(en_passant_left)) {
			legalMoves.put(tmpCoords.keyable(), en_passant_left);
		}

		Move en_passant_right = new Move();
		rules = new Move.Rules();
		rules.en_passant = true;
		rules.destState = Move.Rules.SquareState.NO_PIECE;
		rules.srcRank = (getColor() == Color.WHITE) ? 5 : 4;
		en_passant_right.setRules(rules);

		descriptor = en_passant_right.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		tmpCoords.file += 1;
		tmpCoords.rank += (getColor() == Color.WHITE) ? 1 : -1;
		descriptor.setDestCoords(tmpCoords);
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.en_passant(true);
		descriptor.captures(Type.PAWN);
		en_passant_right.setDescriptor(descriptor);
		if (rules.check(en_passant_right)) {
			legalMoves.put(tmpCoords.keyable(), en_passant_right);
		}

		return legalMoves;
	}
}
