package ChessCore;

import java.util.HashMap;
import java.util.Map;


public class Bishop extends Piece {
	public Bishop(Color color, Board.Coords coords) {
		super(color, coords);

		setType(Type.BISHOP);
		setImageFN("bishop.png");
		setValue(3);
	}

	public HashMap<Integer, Move> calculateLegalMoves() {
        System.out.println(getColor().toString() + this + " at " + getCoords().Notation() + " called calculateLegalMoves()");
		HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();

		Move moveDiag = new Move();
		Move.Rules rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		moveDiag.setRules(rules);

		Move.Descriptor descriptor = moveDiag.new Descriptor();
		descriptor.playingColor(getColor());
		descriptor.setSrcCoords(getCoords());
		descriptor.setDestCoords(new Board.Coords('A', 64));
		moveDiag.setDescriptor(descriptor);
		for (Map.Entry<Integer, Move> entry : moveDiag.expandUndefinedMoves().entrySet()) {
            Move move = entry.getValue();
            rules = move.getRules();

            if (rules.check(move)) {
                legalMoves.put(move.getDescriptor().getDestCoords().keyable(), move);
            }
        }

		return legalMoves;
	}
}
