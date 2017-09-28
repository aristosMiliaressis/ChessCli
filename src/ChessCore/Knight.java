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
        System.out.println(getColor().toString() + this + " at " + getCoords().Notation() + " called calculateLegalMoves()");
		HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();

		Move up_left = new Move();
		Move.Rules rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		up_left.setRules(rules);

		Move.Descriptor descriptor = up_left.new Descriptor();
		Board.Coords tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank += 2;
		tmpCoords.file -= 1;
		descriptor.setDestCoords(tmpCoords);
		up_left.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(up_left)) {
			legalMoves.put(tmpCoords.keyable(), up_left);
		}

		Move up_rigth = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		up_rigth.setRules(rules);

		descriptor = up_rigth.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank += 2;
		tmpCoords.file += 1;
		descriptor.setDestCoords(tmpCoords);
		up_rigth.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(up_rigth)) {
			legalMoves.put(tmpCoords.keyable(), up_rigth);
		}

		Move left_up = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		left_up.setRules(rules);

		descriptor = left_up.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank += 1;
		tmpCoords.file -= 2;
		descriptor.setDestCoords(tmpCoords);
		left_up.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(left_up)) {
			legalMoves.put(tmpCoords.keyable(), left_up);
		}

		Move left_down = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		left_down.setRules(rules);

		descriptor = left_down.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank -= 1;
		tmpCoords.file -= 2;
		descriptor.setDestCoords(tmpCoords);
		left_down.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(left_down)) {
			legalMoves.put(tmpCoords.keyable(), left_down);
		}

		Move down_left = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		down_left.setRules(rules);

		descriptor = down_left.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank -= 2;
		tmpCoords.file -= 1;
		descriptor.setDestCoords(tmpCoords);
		down_left.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(down_left)) {
			legalMoves.put(tmpCoords.keyable(), down_left);
		}

		Move down_rigth = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		down_rigth.setRules(rules);

		descriptor = down_rigth.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank -= 2;
		tmpCoords.file += 1;
		descriptor.setDestCoords(tmpCoords);
		down_rigth.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(down_rigth)) {
			legalMoves.put(tmpCoords.keyable(), down_rigth);
		}

		Move rigth_down = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		rigth_down.setRules(rules);

		descriptor = rigth_down.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
        descriptor.setSrcCoords(getCoords());
		tmpCoords.rank -= 1;
		tmpCoords.file += 2;
		descriptor.setDestCoords(tmpCoords);
		rigth_down.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(rigth_down)) {
			legalMoves.put(tmpCoords.keyable(), rigth_down);
		}

		Move rigth_up = new Move();
		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		rules.jumps = true;
		rigth_up.setRules(rules);

		descriptor = rigth_up.new Descriptor();
		tmpCoords = new Board.Coords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.setSrcCoords(getCoords());
		tmpCoords.rank += 1;
		tmpCoords.file += 2;
		descriptor.setDestCoords(tmpCoords);
		rigth_up.setDescriptor(descriptor);
		if (tmpCoords.valid() && rules.check(rigth_up)) {
			legalMoves.put(tmpCoords.keyable(), rigth_up);
		}

		return legalMoves;
	}
}
