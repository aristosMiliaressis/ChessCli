package ChessCore;

import java.util.HashMap;


public class King extends Piece {
    protected static enum CastlingType {
        W_KING_SIDE,
        W_QUEEN_SIDE,
        B_KING_SIDE,
        B_QUEEN_SIDE
    };
    
	public King(Color color, Board.Coords coords) {
		super(color, coords);

		setType(Type.KING);
		setImageFN("king.png");
		setValue(-1);
	}

	public HashMap<Integer, Move> calculateLegalMoves() {
        System.out.println(getColor().toString() + this + " at " + getCoords().Notation() + " called calculateLegalMoves()");
		HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();

		Move up = new Move();
		Move.Rules rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		up.setRules(rules);

		Move.Descriptor descriptor = up.new Descriptor();
		Board.Coords tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank++;
		descriptor.setDestCoords(tmpCoords);
		up.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(up)) {
			legalMoves.put(tmpCoords.keyable(), up);
		}


		Move up_left = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		up_left.setRules(rules);

		descriptor = up_left.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank++;
		tmpCoords.file--;
		descriptor.setDestCoords(tmpCoords);
		up_left.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(up_left)) {
			legalMoves.put(tmpCoords.keyable(), up_left);
		}


		Move up_rigth = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		up_rigth.setRules(rules);

		descriptor = up_rigth.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.file++;
		tmpCoords.rank++;
		descriptor.setDestCoords(tmpCoords);
		up_rigth.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(up_rigth)) {
			legalMoves.put(tmpCoords.keyable(), up_rigth);
		}


		Move left = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		left.setRules(rules);

		descriptor = left.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.file--;
		descriptor.setDestCoords(tmpCoords);
		left.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(left)) {
			legalMoves.put(tmpCoords.keyable(), left);
		}


		Move rigth = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		rigth.setRules(rules);

		descriptor = rigth.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.file++;
		descriptor.setDestCoords(tmpCoords);
		rigth.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(rigth)) {
			legalMoves.put(tmpCoords.keyable(), rigth);
		}


		Move down = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		down.setRules(rules);

		descriptor = down.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank--;
		descriptor.setDestCoords(tmpCoords);
		down.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(down)) {
			legalMoves.put(tmpCoords.keyable(), down);
		}


		Move down_left = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		down_left.setRules(rules);

		descriptor = down_left.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank--;
		tmpCoords.file--;
		descriptor.setDestCoords(tmpCoords);
		down_left.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(down_left)) {
			legalMoves.put(tmpCoords.keyable(), down_left);
		}


		Move down_rigth = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		down_rigth.setRules(rules);

		descriptor = down_rigth.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank--;
		tmpCoords.file++;
		descriptor.setDestCoords(tmpCoords);
		down_rigth.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(down_rigth)) {
			legalMoves.put(tmpCoords.keyable(), down_rigth);
		}
		
		
		Move queen_castling = new Move();
		rules = new Move.Rules();
		rules.castling = true;
		queen_castling.setRules(rules);

		descriptor = queen_castling.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.file -= 2;
		descriptor.setDestCoords(tmpCoords);
		descriptor.castling(true);
		queen_castling.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(queen_castling)) {
			legalMoves.put(tmpCoords.keyable(), queen_castling);
		}


		Move king_castling = new Move();
		rules = new Move.Rules();
		rules.castling = true;
		king_castling.setRules(rules);

		descriptor = king_castling.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.file += 2;
		descriptor.setDestCoords(tmpCoords);
		descriptor.castling(true);
		king_castling.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(king_castling)) {
			legalMoves.put(tmpCoords.keyable(), king_castling);
		}

		return legalMoves;
	}
}
