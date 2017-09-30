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
		HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();
		Board.Coords coords = this.getCoords();
		Color color = this.getColor();
		
		Move.Rules rules;
		Move.Descriptor descriptor;
		Board.Coords destCoords;
		Board.Coords srcCoords;
		
		Move up = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		up.setRules(rules);

		descriptor = up.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank++;
		descriptor.setDestCoords(destCoords);
		up.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), up);
		}


		Move up_left = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		up_left.setRules(rules);

		descriptor = up_left.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank++;
		destCoords.file--;
		descriptor.setDestCoords(destCoords);
		up_left.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), up_left);
		}


		Move up_rigth = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		up_rigth.setRules(rules);

		descriptor = up_rigth.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.file++;
		destCoords.rank++;
		descriptor.setDestCoords(destCoords);
		up_rigth.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), up_rigth);
		}


		Move left = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		left.setRules(rules);

		descriptor = left.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.file--;
		descriptor.setDestCoords(destCoords);
		left.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), left);
		}


		Move rigth = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		rigth.setRules(rules);

		descriptor = rigth.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.file++;
		descriptor.setDestCoords(destCoords);
		rigth.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), rigth);
		}


		Move down = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		down.setRules(rules);

		descriptor = down.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank--;
		descriptor.setDestCoords(destCoords);
		down.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), down);
		}


		Move down_left = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		down_left.setRules(rules);

		descriptor = down_left.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank--;
		destCoords.file--;
		descriptor.setDestCoords(destCoords);
		down_left.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), down_left);
		}


		Move down_rigth = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		down_rigth.setRules(rules);

		descriptor = down_rigth.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank--;
		destCoords.file++;
		descriptor.setDestCoords(destCoords);
		down_rigth.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), down_rigth);
		}
		
		
		Move queen_castling = new Move();
		rules = new Move.Rules();
		rules.castling = true;
		queen_castling.setRules(rules);

		srcCoords = new Board.Coords('e', color == Color.WHITE ? 1 : 8);
        destCoords = new Board.Coords(coords);
        destCoords.file = 'c';
        destCoords.rank = srcCoords.rank;
		descriptor = queen_castling.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(srcCoords);
        descriptor.setDestCoords(destCoords);
        descriptor.castling(true);
		queen_castling.setDescriptor(descriptor);
		if (destCoords.valid() && coords.equals(srcCoords)) {
			legalMoves.put(destCoords.keyable(), queen_castling);
		}


		Move king_castling = new Move();
		rules = new Move.Rules();
		rules.castling = true;
		king_castling.setRules(rules);

		destCoords = new Board.Coords(coords);
		destCoords.file = 'g';
		destCoords.rank = srcCoords.rank;
		descriptor = king_castling.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(srcCoords);
		descriptor.setDestCoords(destCoords);
		descriptor.castling(true);
		king_castling.setDescriptor(descriptor);
		if (destCoords.valid() && coords.equals(srcCoords)) {
			legalMoves.put(destCoords.keyable(), king_castling);
		}

		return legalMoves;
	}
}
