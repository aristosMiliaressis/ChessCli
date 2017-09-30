package ChessCore;

import java.util.HashMap;


public class Knight extends Piece {
	public Knight(Color color, Board.Coords coords) {
		super(color, coords);

		setType(Type.KNIGHT);
		setImageFN("knight.png");
		setValue(3);
	}

	public HashMap<Integer, Move> calculateLegalMoves() {
	    HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();
        Board.Coords coords = this.getCoords();
        Color color = this.getColor();
        
        Move.Rules rules;
        Move.Descriptor descriptor;
        Board.Coords destCoords;
        
		Move up_left = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		up_left.setRules(rules);

		descriptor = up_left.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank += 2;
		destCoords.file -= 1;
		descriptor.setDestCoords(destCoords);
		up_left.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), up_left);
		}

		Move up_rigth = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		up_rigth.setRules(rules);

		descriptor = up_rigth.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank += 2;
		destCoords.file += 1;
		descriptor.setDestCoords(destCoords);
		up_rigth.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), up_rigth);
		}

		Move left_up = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		left_up.setRules(rules);

		descriptor = left_up.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank += 1;
		destCoords.file -= 2;
		descriptor.setDestCoords(destCoords);
		left_up.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), left_up);
		}

		Move left_down = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		left_down.setRules(rules);

		descriptor = left_down.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank -= 1;
		destCoords.file -= 2;
		descriptor.setDestCoords(destCoords);
		left_down.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), left_down);
		}

		Move down_left = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		down_left.setRules(rules);

		descriptor = down_left.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank -= 2;
		destCoords.file -= 1;
		descriptor.setDestCoords(destCoords);
		down_left.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), down_left);
		}

		Move down_rigth = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		down_rigth.setRules(rules);

		descriptor = down_rigth.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank -= 2;
		destCoords.file += 1;
		descriptor.setDestCoords(destCoords);
		down_rigth.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), down_rigth);
		}

		Move rigth_down = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		rigth_down.setRules(rules);

		descriptor = rigth_down.new Descriptor();
		descriptor.playingColor(color);
        descriptor.setSrcCoords(coords);
        destCoords = new Board.Coords(coords);
		destCoords.rank -= 1;
		destCoords.file += 2;
		descriptor.setDestCoords(destCoords);
		rigth_down.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), rigth_down);
		}

		Move rigth_up = new Move();
		rules = new Move.Rules();
		rules.destCondition = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		rigth_up.setRules(rules);

		descriptor = rigth_up.new Descriptor();
		descriptor.playingColor(color);
		descriptor.setSrcCoords(coords);
		destCoords = new Board.Coords(coords);
		destCoords.rank += 1;
		destCoords.file += 2;
		descriptor.setDestCoords(destCoords);
		rigth_up.setDescriptor(descriptor);
		if (destCoords.valid()) {
			legalMoves.put(destCoords.keyable(), rigth_up);
		}

		return legalMoves;
	}
}
