package ChessCore;

import java.util.HashMap;
import java.util.Map;

public class Queen extends Piece {
	public Queen(Color color, Board.Coords coords) {
		super(color, coords);

		setType(Type.QUEEN);
		setImageFN("queen.png");
		setValue(9);
	}

	public HashMap<Integer, Move> calculateLegalMoves() {
        System.out.println(getColor().toString() + this + " at " + getCoords().Notation() + " called calculateLegalMoves()");
		HashMap<Integer, Move> legalMoves = new HashMap<Integer, Move>();

		Move moveRank = new Move();
		Move.Rules rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		moveRank.setRules(rules);

		Move.Descriptor descriptor = moveRank.new Descriptor();
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.setDestCoords(new Board.Coords(getCoords().file, 64));
		moveRank.setDescriptor(descriptor);
        for (Map.Entry<Integer, Move> entry : moveRank.expandUndefinedMoves().entrySet()) {
            Move move = entry.getValue();
            rules = move.getRules();
            descriptor = move.getDescriptor();
            if (rules.check(move)) {
                legalMoves.put(descriptor.getDestCoords().keyable(), move);
            }
        }
		

		Move moveFile = new Move();
		descriptor = moveFile.new Descriptor();
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.setDestCoords(new Board.Coords('A', getCoords().rank));
		moveFile.setDescriptor(descriptor);

		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		moveFile.setRules(rules);
		for (Map.Entry<Integer, Move> entry : moveFile.expandUndefinedMoves().entrySet()) {
            Move move = entry.getValue();
            rules = move.getRules();
            descriptor = move.getDescriptor();
            if (rules.check(move)) {
                legalMoves.put(descriptor.getDestCoords().keyable(), move);
            }
        }


		Move moveDiag = new Move();
		descriptor = moveDiag.new Descriptor();
		descriptor.setSrcCoords(getCoords());
		descriptor.playingColor(getColor());
		descriptor.setDestCoords(new Board.Coords('A', 64));
		moveDiag.setDescriptor(descriptor);

		rules = new Move.Rules();
		rules.destState = Move.Rules.SquareState.EITHER;
		moveDiag.setRules(rules);
		for (Map.Entry<Integer, Move> entry : moveDiag.expandUndefinedMoves().entrySet()) {
            Move move = entry.getValue();
            rules = move.getRules();
            descriptor = move.getDescriptor();
            if (rules.check(move)) {
                legalMoves.put(descriptor.getDestCoords().keyable(), move);
            }
        }

		return legalMoves;
	}
}
